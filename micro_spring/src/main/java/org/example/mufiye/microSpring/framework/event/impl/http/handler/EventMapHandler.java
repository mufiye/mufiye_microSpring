package org.example.mufiye.microSpring.framework.event.impl.http.handler;

import org.example.mufiye.microSpring.framework.event.impl.http.factory.HttpFactory;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * @Date: 2023/1/14
 * @Author: mufiye
 * @Class: EventMapHandler
 * @Description: description...
 */
@Slf4j
public class EventMapHandler extends SimpleChannelInboundHandler<DefaultHttpRequest> {
	@Override
	protected void channelRead0(ChannelHandlerContext channelHandlerContext, DefaultHttpRequest request) throws Exception {
		String uri = request.uri();
		HttpMethod method = request.method();
		HttpVersion httpVersion = request.protocolVersion();
		HttpHeaders headers = request.headers();
		log.info("uri = {}, method = {}, httpVersion = {}", uri, method, httpVersion);

		String id = HttpFactory.putRequest(request);
		DefaultHttpResponse response = HttpFactory.getResponse(id, 30 * 1000);
		if (response == null) {
			log.error("获取响应超时");
			response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.REQUEST_TIMEOUT);
			ByteBuf content = Unpooled.wrappedBuffer("408 REQUEST TIMEOUT!".getBytes(StandardCharsets.UTF_8));
			response.headers().set("content-length", content.readableBytes());
		}

		channelHandlerContext.writeAndFlush(response);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		super.channelRead(ctx, msg);
	}
}
