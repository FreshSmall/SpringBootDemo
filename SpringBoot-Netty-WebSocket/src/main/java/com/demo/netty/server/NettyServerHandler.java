/*
 * Copyright (C) GSX Techedu Inc. All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.demo.netty.server;

import cn.hutool.json.JSONUtil;
import com.demo.netty.dto.ClientMsgProto;
import com.demo.netty.util.ChannelHandler;
import com.demo.netty.util.MsgUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;

/**
 * @author yinchao
 * @description
 * @team wuhan operational dev.
 * @date 2021/12/23 17:35
 **/
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    private WebSocketServerHandshaker socketHandshaker;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel socketChannel = (SocketChannel) ctx.channel();
        System.out.println("address:" + socketChannel.localAddress().getHostString() + ",链接已经建立！");
        ChannelHandler.channelGroup.add(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("链接已经中断");
        ChannelHandler.channelGroup.remove(ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // http
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest httpRequest = (FullHttpRequest) msg;
            if (!httpRequest.decoderResult().isSuccess()) {
                DefaultFullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST);
                // 返回应答给客户端
                if (httpResponse.status().code() != 200) {
                    ByteBuf buf = Unpooled.copiedBuffer(httpResponse.status().toString(), CharsetUtil.UTF_8);
                    httpResponse.content().writeBytes(buf);
                    buf.release();
                }
                // 如果是非Keep-Alive 关闭链接
                ChannelFuture future = ctx.channel().writeAndFlush(httpResponse);
                if (httpResponse.status().code() != 200) {
                    future.addListener(ChannelFutureListener.CLOSE);
                }
                return;
            }
            WebSocketServerHandshakerFactory webSocket = new WebSocketServerHandshakerFactory("ws:/" + ctx.channel() + "/websocket", null, false);
            socketHandshaker = webSocket.newHandshaker(httpRequest);
            if (null == socketHandshaker) {
                WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
            } else {
                socketHandshaker.handshake(ctx.channel(), httpRequest);
            }
            return;

        }
        // ws
        if (msg instanceof WebSocketFrame) {
            WebSocketFrame webSocketFrame = (WebSocketFrame) msg;
            // 关闭请求
            if (webSocketFrame instanceof CloseWebSocketFrame) {
                socketHandshaker.close(ctx.channel(), (CloseWebSocketFrame) webSocketFrame.retain());
                return;
            }

            // ping 请求
            if (webSocketFrame instanceof PingWebSocketFrame) {
                ctx.channel().write(new PongWebSocketFrame(webSocketFrame.content().retain()));
                return;
            }

            // 只支持文本格式，不支持二进制消息
            if (!(webSocketFrame instanceof TextWebSocketFrame)) {
                throw new Exception("仅支持文本格式");
            }

            String text = ((TextWebSocketFrame) webSocketFrame).text();
            System.out.println("服务端收到请求:" + text);

            ClientMsgProto clientMsgProto = JSONUtil.toBean(text, ClientMsgProto.class);
            // 1:请求个人信息
            if (1 == clientMsgProto.getType()) {
                ctx.channel().writeAndFlush(MsgUtil.buildOwnerImg(ctx.channel().id().toString()));
                return;
            }
            // 2:群发消息
            if (2 == clientMsgProto.getType()) {
                TextWebSocketFrame textWebSocketFrame = MsgUtil.buildAllImg(ctx.channel().id().toString(), clientMsgProto.getMsgInfo());
                ChannelHandler.channelGroup.writeAndFlush(textWebSocketFrame);

            }
        }


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        System.out.println(cause.getMessage());
    }
}
