/*
 * Copyright (C) GSX Techedu Inc. All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.demo.netty.server;

import cn.hutool.json.JSONUtil;
import com.demo.netty.dto.TransportProtocol;
import com.demo.netty.dto.User;
import com.demo.netty.service.UserService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yinchao
 * @description
 * @team wuhan operational dev.
 * @date 2021/12/23 17:35
 **/
@Service
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    private Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);

    @Autowired
    private UserService userService;

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        SocketChannel socketChannel = (SocketChannel) ctx.channel();
        System.out.println("address:" + socketChannel.localAddress().getHostString() + ",链接已经建立！");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("链接已经中断");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("接收到客户端发送的消息：" + JSONUtil.toJsonStr(msg));
        TransportProtocol protocol = (TransportProtocol) msg;
        userService.save((User) protocol.getObj());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        logger.info("异常信息：" + cause.getLocalizedMessage(), cause);
    }
}
