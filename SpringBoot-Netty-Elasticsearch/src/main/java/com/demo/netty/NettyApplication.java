/*
 * Copyright (C) GSX Techedu Inc. All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.demo.netty;

import com.demo.netty.server.NettyServer;
import io.netty.channel.ChannelFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yinchao
 * @description
 * @team wuhan operational dev.
 * @date 2021/12/27 19:25
 **/
@SpringBootApplication
public class NettyApplication implements CommandLineRunner {

    @Value("${netty.port}")
    private int port;
    @Autowired
    private NettyServer nettyServer;

    public static void main(String[] args) {
        SpringApplication.run(NettyApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        ChannelFuture channelFuture = nettyServer.bind(port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> nettyServer.destory()));
        channelFuture.channel().closeFuture().syncUninterruptibly();
    }
}
