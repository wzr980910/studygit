package com.ui;

import com.pojo.User;

import java.util.List;

//为客户端调用提供的接口
public interface UiController {
    void collBack( List<User> friendList,String... returnInfos);
}
