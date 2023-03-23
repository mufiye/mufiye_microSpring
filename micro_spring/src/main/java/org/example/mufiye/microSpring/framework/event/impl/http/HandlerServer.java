package org.example.mufiye.microSpring.framework.event.impl.http;

import org.example.mufiye.microSpring.framework.event.impl.http.handler.EventMapHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import lombok.extern.slf4j.Slf4j;

/**
 * @Date: 2023/1/14
 * @Author: mufiye
 * @Class: HttpServer
 * @Description: description...
 */
@Slf4j
public class HandlerServer {

    private static final int PORT = 80;

    private final NioEventLoopGroup group = new NioEventLoopGroup();

    private int clientCount = 0;

    public static void main(String[] args) {
        new HandlerServer().startUp();
    }

    public void startUp() {
        ChannelFuture start = new ServerBootstrap()
            .group(group)
            .channel(NioServerSocketChannel.class)
            .childHandler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel channel) throws Exception {

                    ChannelPipeline pipeline = channel.pipeline();
                    pipeline.addLast(new HttpServerCodec());

                    pipeline.addLast(new ChannelInboundHandlerAdapter() {
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    clientCount++;
                                    log.info("新连接建立,当前连接数：{}", clientCount);
                                    super.channelActive(ctx);
                                }

                                @Override
                                public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                                    clientCount--;
                                    log.info("客户端连接断开,当前连接数：{}", clientCount);
                                    super.channelInactive(ctx);
                                }
                            })
                            .addLast(new ChannelOutboundHandlerAdapter() {
                                @Override
                                public void write(ChannelHandlerContext ctx,
                                                  Object msg,
                                                  ChannelPromise promise) throws Exception {
                                    log.info(" response = {}", msg);
                                    super.write(ctx, msg, promise);
                                }
                            })
                            .addLast(new EventMapHandler());
                }

            })
            .bind(PORT);
        start.addListener((f) -> {
            if (f.isSuccess()) {
                log.info("服务器启动成功...");
            } else {
                log.error("服务器启动失败...", f.cause());
            }
        });
    }
}
