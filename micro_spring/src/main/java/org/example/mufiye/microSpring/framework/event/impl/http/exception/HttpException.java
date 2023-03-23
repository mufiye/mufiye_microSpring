package org.example.mufiye.microSpring.framework.event.impl.http.exception;

/**
 * @Date: 2023/1/14
 * @Author: mufiye
 * @Class: HttpException
 * @Description: description...
 */
public class HttpException extends RuntimeException {

	public HttpException(String message) {
		super(message);
	}

	public HttpException(String message, Exception e) {
		super(message, e);
	}
}
