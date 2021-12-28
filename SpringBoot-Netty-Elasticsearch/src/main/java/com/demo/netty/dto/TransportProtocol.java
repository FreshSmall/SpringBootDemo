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
 * @date 2021/12/28 09:56
 **/
public class TransportProtocol {
    // 传输类型
    private Integer type;
    // 传输对象
    private Object obj;

    public TransportProtocol(Integer type, Object obj) {
        this.type = type;
        this.obj = obj;
    }

    public TransportProtocol() {
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
