/*
 * Copyright (C) GSX Techedu Inc. All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.demo.netty.dto;

/**
 * @author yinchao
 * @description
 * @team wuhan operational dev.
 * @date 2021/12/23 17:37
 **/
public class ClientMsgProto {

    // 1：请求个人信息，2：发送聊天信息
    private int type;
    // 消息
    private String msgInfo;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMsgInfo() {
        return msgInfo;
    }

    public void setMsgInfo(String msgInfo) {
        this.msgInfo = msgInfo;
    }
}
