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
 * @date 2021/12/23 17:38
 **/
public class ServerMsgProto {

    // 链接信息。1：自发消息，2：群发消息
    private int type;
    // 通信管道ID，
    private String channelId;
    // 用户头像
    private String userHeadImg;
    // 通信消息
    private String msgInfo;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getUserHeadImg() {
        return userHeadImg;
    }

    public void setUserHeadImg(String userHeadImg) {
        this.userHeadImg = userHeadImg;
    }

    public String getMsgInfo() {
        return msgInfo;
    }

    public void setMsgInfo(String msgInfo) {
        this.msgInfo = msgInfo;
    }
}
