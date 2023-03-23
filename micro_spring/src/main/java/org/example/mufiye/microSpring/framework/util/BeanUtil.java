package org.example.mufiye.microSpring.framework.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date: 2022/12/11
 * @Author: mufiye
 * @Class: BeanFactory
 * @Description: description...
 */
public class BeanUtil {

	private final static Map<Class<?>, Method[]> cache = new HashMap<>();

	public static <T> List<T> mapperToList(ResultSet rs, Class<T> clazz) {
		List<T> list = new ArrayList<>();
		try {
			Method[] declaredMethods;
			if (cache.containsKey(clazz)) {
				declaredMethods = cache.get(clazz);
			}
			else {
				declaredMethods = clazz.getDeclaredMethods();
				cache.put(clazz, declaredMethods);
			}
			while (rs.next()) {
				T t = createBean(rs, clazz, declaredMethods);
				list.add(t);
			}
		} catch (InstantiationException | IllegalAccessException | SQLException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static <T> T mapperToBean(ResultSet rs, Class<T> clazz){
		Method[] declaredMethods = clazz.getDeclaredMethods();
		try {
			rs.next();
			return createBean(rs, clazz, declaredMethods);
		} catch (SQLException | IllegalAccessException | InstantiationException | InvocationTargetException throwables) {
			throwables.printStackTrace();
		}
		return null;
	}

	private static <T> T createBean(ResultSet rs, Class<T> clazz, Method[] declaredMethods) throws InstantiationException, IllegalAccessException, SQLException, InvocationTargetException {
		T t = clazz.newInstance();
		for (Method declaredMethod : declaredMethods) {
			String methodName = declaredMethod.getName();
			if (methodName.length() <= 3 || !methodName.startsWith("set")) {
				continue;
			}
			String noPrefix = methodName.substring(3);
			String filed = Character.toLowerCase(noPrefix.charAt(0)) + methodName.substring(4) ;

			Class<?> parameterType = declaredMethod.getParameterTypes()[0];
			Object val = rs.getObject(filed, parameterType);
			declaredMethod.invoke(t, val);
		}
		return t;
	}

}
