package com.thread;

import com.handlemessage.HandleReceiveMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created with Intellij IDEA.
 *
 * @Author: wzr
 * @Date: 2023/11/11/13:58
 * @Description:客户端和服务端保持通讯的线程
 */
public class ClientConServerThread extends Thread {
    public static Socket socket;

    public ClientConServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        while (true) {
            //不停读取从服务器端发来的消息
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String receiveMessage = reader.readLine();
                //读取之后交给处理信息的类处理
                HandleReceiveMessage.handleOrdinaryMessage(receiveMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
