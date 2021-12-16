/*
 * Copyright (C) GSX Techedu Inc. All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.demo.config;

import com.demo.server.NettyServer;
import io.netty.channel.ChannelFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author yinchao
 * @description
 * @team wuhan operational dev.
 * @date 2021/12/16 14:48
 **/
@Configuration
public class NettyServerConfig {

    @Value("${netty.port}")
    private int port;

    @Autowired
    private NettyServer nettyServer;

    @PostConstruct
    public void init() {
        System.out.println("NettyServerConfig is init.");
        ChannelFuture channelFuture = nettyServer.bind(port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> nettyServer.destory()));
    }

}
