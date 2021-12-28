/*
 * Copyright (C) GSX Techedu Inc. All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.demo.netty.web;

import com.demo.netty.server.NettyServer;
import com.demo.netty.server.NettyServerHandler;
import com.demo.netty.service.UserService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yinchao
 * @description
 * @team wuhan operational dev.
 * @date 2021/12/28 10:21
 **/
@RestController
public class NettyController {

    @Autowired
    private NettyServer nettyServer;

    @Autowired
    private UserService userService;

    @Autowired
    private NettyServerHandler nettyServerHandler;


    @RequestMapping("/localAddress")
    public String localAddress() {
        return "nettyServer localAddress " + nettyServer.getChannel().localAddress();
    }
}
