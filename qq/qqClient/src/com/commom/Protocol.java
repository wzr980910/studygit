package com.commom;

//打包类
public class Protocol {
    //打包登录信息
    public static String loginMessage(String name, String password) {
        return "login" + "@" + name + "&" + password + "\r\n\r\n";
    }

    //打包注册信息
    public static String registeredMessage(String name, String password) {
        return "registered" + "@" + name + "&" + password + "\r\n\r\n";
    }

    //打包获取好友列表信息
    public static String protocolGetFriendList(String userId) {
        return "friend" + "@" + userId + "\r\n";
    }

    //打包发送信息
    public static String sendMessageTo(String sender, String message, String geter) {
        return "message" + "@" + sender + "&" + geter + "&" + message + "\r\n\r\n";
    }

    //打包发送群聊信息
    public static String sendGroupMessageTo(String sender, String message, String geter) {
        return "groupMessage" + "@" + sender + "&" + geter + "&" + message + "\r\n\r\n";
    }

}
