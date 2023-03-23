package org.example.mufiye.microSpring.framework;

//import com.sun.istack.internal.NotNull;
import org.example.mufiye.microSpring.framework.annotation.AutoWare;
import org.example.mufiye.microSpring.framework.annotation.Bean;
import org.example.mufiye.microSpring.framework.annotation.Config;
import org.example.mufiye.microSpring.framework.annotation.Controller;
import org.example.mufiye.microSpring.framework.annotation.Mapper;
import org.example.mufiye.microSpring.framework.annotation.Service;
import org.example.mufiye.microSpring.framework.annotation.Value;
import org.example.mufiye.microSpring.framework.event.RegeditEvent;
import org.example.mufiye.microSpring.framework.event.impl.ConsoleInputEvent;
import org.example.mufiye.microSpring.framework.event.impl.HttpInputEvent;
import org.example.mufiye.microSpring.framework.factory.ComponentFactory;
import org.example.mufiye.microSpring.framework.scan.Scanner;
import org.example.mufiye.microSpring.framework.util.InstanceUtil;
import org.example.mufiye.microSpring.framework.util.SignConst;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@Slf4j
public class ApplicationContext {

	private final Properties properties;
	private final Map<String, Method> controllerMapper;
	private Class<?> appClass;

	private ApplicationContext(Class<?> clazz) {  // @NotNull
		InstanceUtil.INSTANCES.put(ApplicationContext.class, this);
		appClass = clazz;
		properties = new Properties();
		controllerMapper = new HashMap<>();
		try {
			properties.load(appClass.getResourceAsStream("/framework.properties"));
		} catch (IOException e) {
			log.warn("加载配置文件时出错...", e);
		}
	}

	public static void run(Class<?> clazz, String[] args) {
		ApplicationContext context = new ApplicationContext(clazz);
		context.doStart();
	}

	private void doStart() {
		Scanner scanner = InstanceUtil.getInstance(Scanner.class);
		scanner.scan();
		loadComponent(Config.class);
		loadComponent(Mapper.class);
		loadComponent(Service.class);
		loadComponent(Controller.class);
		wareComponent();
		addControllerMapper();
		regeditControllers(new HttpInputEvent());   // web
		// regeditControllers(new ConsoleInputEvent());  // console
	}

	private <A extends Annotation> void loadComponent(Class<A> componentType) {
		Scanner scanner = InstanceUtil.getInstance(Scanner.class);
		List<Class<?>> components = scanner.getComponent(componentType);
		ComponentFactory factory = InstanceUtil.getInstance(ComponentFactory.class);

		for (Class<?> component : components) {
			try {
				Object o = component.newInstance();
				factory.putBean(component, o);

				for (Field declaredField : component.getDeclaredFields()) {
					Value value = declaredField.getAnnotation(Value.class);
					if (value == null) {
						continue;
					}
					String key = value.value();

					String v = properties.getProperty(key);
					if (v == null) {
						log.error("'{}' not found in 'framework.properties'", key);
						throw new RuntimeException("key值未找到！");
					}
					declaredField.setAccessible(true);
					declaredField.set(o, v);
				}

				//为config组件中的bean创建对象
				if (componentType == Config.class) {
					for (Method declaredMethod : component.getDeclaredMethods()) {
						Bean b = declaredMethod.getAnnotation(Bean.class);
						if (b == null) {
							continue;
						}
						Class<?> returnType = declaredMethod.getReturnType();
						Object bean = declaredMethod.invoke(o);
						factory.putBean(returnType, bean);
					}
				}
			} catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
				throw new RuntimeException(e);
			}
		}

		log.info("组件{}初始化完成, list = {}", componentType, components);
	}


	private void wareComponent() {
		ComponentFactory factory = InstanceUtil.getInstance(ComponentFactory.class);
		Collection<Object> components = factory.components();

		for (Object component : components) {
			Class<?> c = component.getClass();
			for (Field declaredField : c.getDeclaredFields()) {
				AutoWare auto = declaredField.getAnnotation(AutoWare.class);
				if (auto == null) {
					continue;
				}
				Class<?> ft = declaredField.getType();
				Object bean = factory.getBean(ft);
				if (bean == null) {
					log.error("AutoWare的组件不存在, component = {}, filed = {}", c, declaredField);
					throw new RuntimeException("AutoWare的组件不存在!");
				}
				try {
					declaredField.setAccessible(true);
					declaredField.set(component, bean);
				} catch (IllegalAccessException e) {
					log.error(e.getMessage(), e);
				}
			}
		}

		log.info("组件注入成功...");
	}

	public void addControllerMapper() {
		Scanner scanner = InstanceUtil.getInstance(Scanner.class);
		List<Class<?>> controllers = scanner.getComponent(Controller.class);

		for (Class<?> controller : controllers) {
			Method[] declaredMethods = controller.getDeclaredMethods();
			for (Method declaredMethod : declaredMethods) {
				// public
				if (declaredMethod.getModifiers() == 1) {
					Controller c = controller.getAnnotation(Controller.class);
					String controllerMap = c.value();

					String methodName = declaredMethod.getName();
					controllerMapper.put(SignConst.MAPPER_SEPARATOR + controllerMap + SignConst.MAPPER_SEPARATOR + methodName, declaredMethod);
				}
			}
		}
	}

	public void regeditControllers(RegeditEvent event) {
		new Thread(() -> event.regedit(controllerMapper),
				"Thread-RegeditEvent").start();
	}

	public Class<?> getAppClass() {
		return appClass;
	}
}
