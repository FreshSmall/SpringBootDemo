/*
 * Copyright (C) GSX Techedu Inc. All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.demo.netty.web;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yinchao
 * @description
 * @team wuhan operational dev.
 * @date 2021/12/23 19:22
 **/
@RestController
public class NettyController {

    @RequestMapping("/index")
    public String index(Model model) {
        model.addAttribute("name", "xiaohao");
        return "index";
    }
}
