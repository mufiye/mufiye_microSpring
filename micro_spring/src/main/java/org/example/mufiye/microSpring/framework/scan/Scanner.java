package org.example.mufiye.microSpring.framework.scan;

import org.example.mufiye.microSpring.framework.ApplicationContext;
import org.example.mufiye.microSpring.framework.annotation.Controller;
import org.example.mufiye.microSpring.framework.annotation.Mapper;
import org.example.mufiye.microSpring.framework.annotation.Scan;
import org.example.mufiye.microSpring.framework.annotation.Service;
import org.example.mufiye.microSpring.framework.util.InstanceUtil;
import org.example.mufiye.microSpring.framework.util.ScanUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Date: 2022/12/11
 * @Author: mufiye
 * @Class: Scanner
 * @Description: description...
 */
@Slf4j
public class Scanner {

	List<Class<?>> classes;

	public void scan() {
		ApplicationContext context = InstanceUtil.getInstance(ApplicationContext.class);
		Class<?> appClass = context.getAppClass();

		Scan scan = appClass.getAnnotation(Scan.class);
		if (scan == null) {
			throw new RuntimeException("启动类未配置Scan注解");
		}

		String basePackage = scan.value();

		classes = new ArrayList<>(ScanUtil.getClasses(basePackage));
		log.debug("正在扫描...");
	}

	public <A extends Annotation> List<Class<?>> getComponent(Class<A> componentType){
		return classes.stream().filter(c -> c.getAnnotation(componentType) != null).collect(Collectors.toList());
	}

	public List<Class<?>> getControllers(){
		return classes.stream().filter(c -> c.getAnnotation(Controller.class) != null).collect(Collectors.toList());
	}

	public List<Class<?>> getService(){
		return classes.stream().filter(c -> c.getAnnotation(Service.class) != null).collect(Collectors.toList());
	}

	public List<Class<?>> getMapper(){
		return classes.stream().filter(c -> c.getAnnotation(Mapper.class) != null).collect(Collectors.toList());
	}
}
