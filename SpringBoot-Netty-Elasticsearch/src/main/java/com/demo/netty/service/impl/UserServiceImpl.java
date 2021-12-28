/*
 * Copyright (C) GSX Techedu Inc. All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.demo.netty.service.impl;

import com.demo.netty.dto.User;
import com.demo.netty.service.UserRepository;
import com.demo.netty.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author yinchao
 * @description
 * @team wuhan operational dev.
 * @date 2021/12/28 10:03
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteById(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public User queryUserById(String id) {
        Optional<User> optional = userRepository.findById(id);
        return optional.get();
    }

    @Override
    public Iterable<User> queryAll() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> findByName(String name, PageRequest request) {
        return userRepository.findByName(name, request);
    }
}
