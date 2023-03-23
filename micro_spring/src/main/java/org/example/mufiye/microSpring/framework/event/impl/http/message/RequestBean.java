package org.example.mufiye.microSpring.framework.event.impl.http.message;

import io.netty.handler.codec.http.DefaultHttpRequest;
import lombok.Data;

/**
 * @Date: 2023/1/14
 * @Author: mufiye
 * @Class: MapperMessage
 * @Description: description...
 */

@Data
public class RequestBean {
	private String id;
	private DefaultHttpRequest request;
}
