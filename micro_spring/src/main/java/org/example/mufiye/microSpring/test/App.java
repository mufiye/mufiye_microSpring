package org.example.mufiye.microSpring.test;

import org.example.mufiye.microSpring.framework.ApplicationContext;
import org.example.mufiye.microSpring.framework.annotation.Scan;

/**
 * @Date: 2022/12/11
 * @Author: mufiye
 * @Class: App
 * @Description: description...
 */
@Scan("org.example.mufiye.microSpring")
public class App {
	public static void main(String[] args) {
		ApplicationContext.run(App.class, args);
	}
}
