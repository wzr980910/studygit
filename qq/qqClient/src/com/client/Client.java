package com.client;

import com.handlemessage.HandleReceiveMessage;

import java.io.*;
import java.net.*;


//发送注册、登录及请求好友列表的信息
public class Client {
    public static Socket socket;

    public static void sendVerifyInfo(String loginInfo) {
        try {
            socket = new Socket("127.0.0.1", 3365);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //将所有类型的信息发给客户端
            writer.write(loginInfo);
            writer.flush();
            String receiveMessage = reader.readLine();
            //判断发送的是什么类型的信息，再交给处理信息的类进行处理
            if (loginInfo.startsWith("register")) {//注册
                HandleReceiveMessage.handleRegister(receiveMessage);
            } else if (loginInfo.startsWith("login")) {//登录
                HandleReceiveMessage.handleLogin(receiveMessage);
            } else if (loginInfo.startsWith("friend")) {//请求好友列表
                HandleReceiveMessage.handleFriendList(receiveMessage);
            } else {
                throw new RuntimeException("发送信息种类错误");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != socket) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}