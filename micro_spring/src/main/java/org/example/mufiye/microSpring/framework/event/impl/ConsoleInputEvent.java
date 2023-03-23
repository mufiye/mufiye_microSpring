package org.example.mufiye.microSpring.framework.event.impl;

import org.example.mufiye.microSpring.framework.event.RegeditEvent;
import org.example.mufiye.microSpring.framework.factory.ComponentFactory;
import org.example.mufiye.microSpring.framework.util.InstanceUtil;
import org.example.mufiye.microSpring.framework.util.ParseUtil;
import org.example.mufiye.microSpring.framework.util.SignConst;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Scanner;

/**
 * @Date: 2022/12/11
 * @Author: mufiye
 * @Class: ConsoleInputEvent
 * @Description: description...
 */
@Slf4j
public class ConsoleInputEvent implements RegeditEvent {
    @Override
    public void regedit(Map<String, Method> controllerMapper) {
        Scanner scanner = new Scanner(System.in);
        String command;
        // command格式: mapper para1,para2,...
        while ((command = scanner.nextLine()) != null) {
            String[] commands = command.split(SignConst.COMMAND_SEPARATOR);
            String mapper;
            String[] paras;
            mapper = commands[0];
            if (commands.length == 1) {
                paras = new String[] {};
            } else {
                paras = commands[1].split(SignConst.PARAMETER_SEPARATOR);
            }
            Method method = controllerMapper.get(mapper);
            if (method == null) {
                log.error("404 METHOD NOT FOUND!");
                continue;
            }

            Class<?>[] parameterTypes = method.getParameterTypes();

            if (paras.length != parameterTypes.length) {
                log.error("传入参数数量与预期不符， provide = {}, expect = {}", paras.length, parameterTypes.length);
                continue;
            }
            Object[] passParas = new Object[parameterTypes.length];

            int count = 0;
            for (Class<?> parameterType : parameterTypes) {
                String para = paras[count];
                Object passPara;
                passPara = ParseUtil.parseObject(parameterType, para);
                passParas[count++] = passPara;
            }

            ComponentFactory factory = InstanceUtil.getInstance(ComponentFactory.class);
            Object controller = factory.getBean(method.getDeclaringClass());
            try {
                Object invoke = method.invoke(controller, passParas);
                log.info("result = {}", invoke);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

}
