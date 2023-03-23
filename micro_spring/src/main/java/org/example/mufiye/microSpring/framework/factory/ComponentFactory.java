package org.example.mufiye.microSpring.framework.factory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Date: 2022/12/11
 * @Author: mufiye
 * @Class: ComponentFactory
 * @Description: description...
 */
public class ComponentFactory {

	private Map<Class<?>, Object> beans = new HashMap<>();

	public <T> T getBean(Class<T> clazz) {
		Optional<Class<?>> first = beans.keySet().stream().filter(clazz::isAssignableFrom).findFirst();
		return (T) beans.get(first.get());
	}

	public Object getBean(String clazz) {
		try {
			Class<?> aClass = Class.forName(clazz);
			return getBean(aClass);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public Object putBean(Class<?> clazz, Object bean){
		return beans.put(clazz, bean);
	}

	public Collection<Object> components(){
		return beans.values();
	}
}
