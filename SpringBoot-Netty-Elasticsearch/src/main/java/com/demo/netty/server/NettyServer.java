/*
 * Copyright (C) GSX Techedu Inc. All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.demo.netty.server;

import com.demo.netty.codec.ObjDecoder;
import com.demo.netty.codec.ObjEncoder;
import com.demo.netty.dto.TransportProtocol;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yinchao
 * @description
 * @team wuhan operational dev.
 * @date 2021/12/23 17:34
 **/
@Service
public class NettyServer {

    //配置服务端的NIO线程组
    private final EventLoopGroup boosGroup = new NioEventLoopGroup();
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();
    private Channel channel;

    @Autowired
    private NettyServerHandler nettyServerHandler;

    public ChannelFuture bind(int port) {
        ServerBootstrap b = new ServerBootstrap();
        ChannelFuture f = null;
        b.group(boosGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        System.out.println(Thread.currentThread().getName() + ",服务器初始化通道.....");
                        socketChannel.pipeline().addLast(new ObjDecoder(TransportProtocol.class));
                        socketChannel.pipeline().addLast(new ObjEncoder(TransportProtocol.class));
                        socketChannel.pipeline().addLast(nettyServerHandler);
                    }
                });

        try {
            //绑定端口等待同步成功
            f = b.bind(port).syncUninterruptibly();
            channel = f.channel();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != f && f.isSuccess()) {
                System.out.println("netty server start success");
            } else {
                System.out.println("netty server stop fail");
            }
        }
        return f;
    }

    public void destory() {
        if (null == channel) {
            return;
        }
        channel.close();
        //优雅退出，释放线程资源
        boosGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

    public Channel getChannel() {
        return this.channel;
    }
}
