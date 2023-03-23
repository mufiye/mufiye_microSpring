package org.example.mufiye.microSpring.framework.util;

import java.util.HashMap;
import java.util.Map;

public class InstanceUtil {

	public final static Map<Class, Object> INSTANCES = new HashMap<>();

	public static <T> T getInstance(Class<T> clazz){
		if (INSTANCES.get(clazz) == null) {
			synchronized (clazz) {
				if (INSTANCES.get(clazz) == null) {
					try {
						T t = clazz.newInstance();
						INSTANCES.put(clazz, t);
					} catch (InstantiationException | IllegalAccessException e) {
						throw new RuntimeException(e);
					}
				}
			}
		}
		return (T) INSTANCES.get(clazz);
	}
}
