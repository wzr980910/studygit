package com.commom;

import com.pojo.User;

import java.util.ArrayList;
import java.util.List;

//解析类
public class Parse {
    //处理好友列表
    public static List<User> unpackFriendList(String friendStr) {
        String[] friends = friendStr.split("@");
        List<User> friendList = new ArrayList<>();
        for (int i = 0; i < friends.length; i++) {
            User user = new User();
            user.setUserId(friends[i]);
            friendList.add(user);
        }
        return friendList;
    }

    //处理登录和注册的返回信息
    public static String parseLoginRegister(String returnStr) {
        return returnStr.split("@")[0];
    }

    //获取用户发送信息的内容
    public static String ParseGetMessage(String receiveString) {
        try {
            return (receiveString.split("@"))[1].split("&")[2];
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    //获取普通发送者
    public static String getSender(String receiveString) {
        return (receiveString.split("@"))[1].split("&")[0];
    }

    //获取群聊消息的发送者
    public static String getGroupSender(String receiveString) {
        return (((receiveString.split("@"))[1].split("&"))[0].split("#"))[1];
    }
}
