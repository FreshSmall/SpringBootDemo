/*
 * Copyright (C) GSX Techedu Inc. All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.demo.netty.util;

import cn.hutool.json.JSONUtil;
import com.demo.netty.dto.ServerMsgProto;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @author yinchao
 * @description
 * @team wuhan operational dev.
 * @date 2021/12/23 19:26
 **/
public class MsgUtil {

    public static TextWebSocketFrame buildAllImg(String channelId, String msgInfo) {
        // 模拟头像
        int i = Math.abs(channelId.hashCode()) % 10;
        ServerMsgProto proto = new ServerMsgProto();
        proto.setType(2);
        proto.setMsgInfo(msgInfo);
        proto.setChannelId(channelId);
        proto.setChannelId("head" + i + ".jpg");
        return new TextWebSocketFrame(JSONUtil.toJsonStr(proto));
    }

    public static TextWebSocketFrame buildOwnerImg(String channelId) {
        ServerMsgProto proto = new ServerMsgProto();
        proto.setType(1);
        proto.setChannelId(channelId);
        return new TextWebSocketFrame(JSONUtil.toJsonStr(proto));
    }
}
