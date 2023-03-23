package org.example.mufiye.microSpring.framework.event.impl;

import org.example.mufiye.microSpring.framework.event.RegeditEvent;
import org.example.mufiye.microSpring.framework.event.impl.http.HandlerServer;
import org.example.mufiye.microSpring.framework.event.impl.http.exception.HttpException;
import org.example.mufiye.microSpring.framework.event.impl.http.factory.HttpFactory;
import org.example.mufiye.microSpring.framework.event.impl.http.message.RequestBean;
import org.example.mufiye.microSpring.framework.factory.ComponentFactory;
import org.example.mufiye.microSpring.framework.util.InstanceUtil;
import org.example.mufiye.microSpring.framework.util.ParseUtil;
import org.example.mufiye.microSpring.framework.util.SignConst;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Date: 2023/1/14
 * @Author: mufiye
 * @Class: HttpInputEvent
 * @Description: description...
 */
@Slf4j
public class HttpInputEvent implements RegeditEvent {
	@Override
	public void regedit(Map<String, Method> controllerMapper) {
		new HandlerServer().startUp();

		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 4,
				60L, TimeUnit.SECONDS,
				new SynchronousQueue<>());

		while (true) {
			RequestBean requestBean = HttpFactory.getRequest();
			threadPoolExecutor.submit(() -> {
				String requestId = requestBean.getId();
				DefaultHttpRequest request = requestBean.getRequest();
				String uri = request.uri();
				HttpMethod method = request.method();
				HttpVersion httpVersion = request.protocolVersion();
				log.info("正在处理请求 requestId = {}, uri = {}, method = {}, httpVersion = {}", requestId, uri, method, httpVersion);

				String[] uriParam = uri.split(SignConst.URI_PARAM_SEPARATOR);
				Map<String, String> paramsMap = new HashMap<>();
				DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.INTERNAL_SERVER_ERROR);
				switch (uriParam.length) {
					case 2:
						String param = uriParam[1];
						log.info("uri Param[1]: {}", uriParam[1]);
						String[] params = param.split(SignConst.PARAMETER_SEPARATOR);

						for (String kv : params) {
							log.info("kv of params: {}", kv);
							String[] KV = kv.split(SignConst.KEY_VALUE_SEPARATOR);
							log.info("KV[0]: {}, KV[1]: {}", KV[0], KV[1]);
							paramsMap.put(KV[0], KV[1]);
						}
					case 1:
						log.info("parasMap: {}", paramsMap);
						log.info("uri Param[0]: {}", uriParam[0]);
						String mapper = uriParam[0];
						Method m = controllerMapper.get(mapper);
						log.info("method's name: {}", m.getName());
						if (m == null) {
							HttpException httpException = new HttpException("未找到对应的映射方法，uri = " + uri);
							log.warn(httpException.getMessage(), httpException);
							ByteBuf content = Unpooled.wrappedBuffer("404 NOT FOUND!".getBytes(StandardCharsets.UTF_8));
							response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND, content);
							response.headers().set("content-length", content.readableBytes());
							break;
						}

						Parameter[] parameters = m.getParameters();
						Object[] passParams = new Object[parameters.length];
						int i = 0;
						for (Parameter parameter : parameters) {
							log.info("parameter.getName(): {}", parameter.getName());
							String value = paramsMap.get(parameter.getName());
							log.info("parsing parameter");
							log.info("value: {}", value);
							Object o = ParseUtil.parseObject(parameter.getType(), value);
							log.info("end parsing parameter");
							passParams[i++] = o;
						}
						log.info("out of parsing parameter");
						Class<?> declaringClass = m.getDeclaringClass();
						ComponentFactory factory = InstanceUtil.getInstance(ComponentFactory.class);
						Object controller = factory.getBean(declaringClass);
						try {
							log.info("调用m.invoke");
							Object returnValue = m.invoke(controller, passParams);
							String rv = (String) returnValue;
							ByteBuf content = Unpooled.wrappedBuffer(rv.getBytes(StandardCharsets.UTF_8));
							response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
							response.headers().set("content-length", content.readableBytes());
						} catch (IllegalAccessException | InvocationTargetException e) {
							e.printStackTrace();
						}
						break;
					default:
						HttpException httpException = new HttpException("输入参数有误，uri = " + uri);
						log.warn(httpException.getMessage(), httpException);
						ByteBuf content = Unpooled.wrappedBuffer("BAD REQUEST!".getBytes(StandardCharsets.UTF_8));
						response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST, content);
						response.headers().set("content-length", content.readableBytes());
				}

				HttpFactory.putResponse(requestId, response);
			});
		}
	}
}
