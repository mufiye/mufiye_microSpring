package org.example.mufiye.microSpring.framework.event;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @Date: 2022/12/11
 * @Author: mufiye
 * @Class: RegeditEvent
 * @Description: description...
 */
public interface RegeditEvent {
	void regedit(Map<String, Method> controllerMapper);
}
