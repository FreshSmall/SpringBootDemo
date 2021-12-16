/*
 * Copyright (C) GSX Techedu Inc. All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.demo.controller;

import com.demo.server.NettyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yinchao
 * @description
 * @team wuhan operational dev.
 * @date 2021/12/16 14:56
 **/
@RestController
public class NettyController {

    @Autowired
    private NettyServer nettyServer;

    @RequestMapping("/nettyServer/localAddress")
    public String localAddress() {
        return "local address:" + nettyServer.getChannel().localAddress();
    }

    @RequestMapping("/nettyServer/isOpen")
    public String isOpen() {
        return "netty server is open:" + nettyServer.getChannel().isOpen();
    }
}
