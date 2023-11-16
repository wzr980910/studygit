package com.handlemessage;

import com.client.Client;
import com.commom.Parse;
import com.pojo.User;
import com.thread.ClientConServerThread;
import com.thread.ManageClientConServerThread;
import com.ui.JFrameFactory;
import com.ui.impl.Chat;
import com.ui.impl.LoginView;
import com.ui.impl.Register;

import java.util.List;
//处理接收到信息的类
public class HandleReceiveMessage {
    private static List<User> friendList;

    //处理注册的返回信息
    public static void handleRegister(String returnStr) {
        String loginInfo = Parse.parseLoginRegister(returnStr);
        Register register = (Register) JFrameFactory.getJFrame("register");
        register.collBack(null, loginInfo);
    }


    //处理登录的返回信息
    public static void handleLogin(String returnStr) {
        String loginInfo = Parse.parseLoginRegister(returnStr);
        LoginView login = (LoginView) JFrameFactory.getJFrame("login");
        //验证用户登录
        if ("Yes".equals(loginInfo)) {
            //创建该qq和服务器端保持通讯连接的线程
            ClientConServerThread readThread = new ClientConServerThread(Client.socket);
            //启动该通讯线程
            readThread.start();
            ManageClientConServerThread.setClientConServerThread(readThread);
            //请求好友列表
            HandleSendMessage.sendGetFriendListMessage(LoginView.userId);
            login.collBack(friendList, loginInfo);
        } else {
            login.collBack(null, loginInfo);
        }
    }
    //处理请求好友列表的返回信息
    public static void handleFriendList(String returnStr) {
        List<User> friendList = Parse.unpackFriendList(returnStr);
        HandleReceiveMessage.friendList = friendList;
    }

    //处理其他用户发送的消息
    public static void handleOrdinaryMessage(String receiveMessage) {
        String sender = Parse.getSender(receiveMessage);
        Chat currentChat = (Chat) JFrameFactory.getJFrame(sender);
        String message = Parse.ParseGetMessage(receiveMessage);
        if (sender.startsWith("group")) {//处理群聊消息
            String groupSender = Parse.getGroupSender(receiveMessage);
            currentChat.collBack(null, groupSender, message);
        } else {//处理普通消息
            currentChat.collBack(null, sender, message);
        }
    }
}
