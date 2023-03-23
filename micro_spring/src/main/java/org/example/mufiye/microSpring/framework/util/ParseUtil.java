package org.example.mufiye.microSpring.framework.util;

import lombok.extern.slf4j.Slf4j;

/**
 * @Date: 2022/12/11
 * @Author: mufiye
 * @Class: parseUtil
 * @Description: description...
 */
@Slf4j
public class ParseUtil {
	public static Object parseObject(Class<?> parameterType, String para) {
		Object passPara;
		if (parameterType == int.class) {
			log.info("into parse int");
			log.info("para: {}", para);
			passPara = Integer.parseInt(para);
			log.info("end parse int");
		}
		else if (parameterType == double.class) {
			passPara = Double.parseDouble(para);
		}
		else if (parameterType == float.class) {
			passPara = Float.parseFloat(para);
		}
		else if (parameterType == long.class) {
			passPara = Long.parseLong(para);
		}
		else if (parameterType == boolean.class) {
			passPara = Boolean.parseBoolean(para);
		}
		else if (parameterType == short.class) {
			passPara = Short.parseShort(para);
		}
		else if (parameterType == char.class) {
			passPara = para.charAt(0);
		}
		else {
			passPara = para;
		}
		return passPara;
	}
}
