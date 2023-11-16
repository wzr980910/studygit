package com.handlemessage;

import com.client.Client;
import com.commom.Protocol;
import com.thread.ClientConServerThread;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

//处理发送信息的类

public class HandleSendMessage {
    //处理要发送的注册信息
    public static void sendRegisterMessage(String userId, String password) {
        Client.sendVerifyInfo(Protocol.registeredMessage(userId, password));
    }

    //处理要发送的登录信息
    public static void sendLoginMessage(String userId, String password) {
        Client.sendVerifyInfo(Protocol.loginMessage(userId, password));
    }

    //处理要发送的请求好友列表
    public static void sendGetFriendListMessage(String userId) {
        Client.sendVerifyInfo(Protocol.protocolGetFriendList(userId));
    }

    //处理要发送出去的信息
    public static void sendMessageTo(String senderId, String message, String friendName) {
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ClientConServerThread.socket.getOutputStream()));
            String protocoledMessage = "";
            if (friendName.startsWith("group")) {
                protocoledMessage = Protocol.sendGroupMessageTo(senderId, message, friendName);
            } else {
                protocoledMessage = Protocol.sendMessageTo(senderId, message, friendName);
            }
            writer.write(protocoledMessage);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("信息发送失败");
        }
    }
}
