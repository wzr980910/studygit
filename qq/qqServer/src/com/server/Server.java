package com.server;

import com.common.Parser;
import com.common.Protocol;
import com.pojo.User;
import com.dao.UserDAO;
import com.dao.impl.UserDAOImpl;
import com.dao.jdbcutils.JDBCUtils;
import com.thread.ConClientThread;
import com.thread.ManageClientThread;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.util.List;

/**
 * Created with Intellij IDEA.
 *
 * @Author: wzr
 * @Date: 2023/11/09/9:06
 * @Description:服务端
 */

public class Server {
    public static void main(String[] args) {
        new Server();
    }
    private UserDAO userDAO=new UserDAOImpl();
    public Server(){
        try {
            ServerSocket serverSocket = new ServerSocket(3365);
            while (true) {
                Socket socket = serverSocket.accept();
                //定义BufferedWriter，将数据发送给客户端
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                //接收客户端发送信息
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String receiveString = reader.readLine();
                Connection conn = JDBCUtils.getConnection();
                String sendString = "";
                String index = Parser.parseOperate(receiveString);
                switch (index){
                    case "login":
                        User user = userDAO.getUser(conn, Parser.getUserId(receiveString), Parser.getPassword(receiveString));
                        if (null!=user) {
                            //返回成功成功信息
                            sendString = Protocol.loginYes();
                            writer.write(sendString);
                            writer.flush();
                            //在此开启线程，将客户端与服务器连接
                            ConClientThread thread = new ConClientThread(socket);
                            //将启动的用户和线程放入hashMap，为实现服务器数据转发做准备
                            ManageClientThread.addClientThread(Parser.getUserId(receiveString),thread);
                            //启动与该客户端通讯的线程
                            thread.start();
                        } else {
                            //返回登录失败信息
                            sendString = Protocol.loginNo();
                            writer.write(sendString);
                            writer.flush();
                            //关闭连接、使while循环可尝试重新连接
                            socket.close();
                        }
                        break;
                    case "registered":
                        User userById = userDAO.getUserById(conn, Parser.getUserId(receiveString));
                        if (null ==userById ) {
                            Integer result = userDAO.register(conn, Parser.getUserId(receiveString), Parser.getPassword(receiveString));
                            if(0!=result){
                            //返回成功信息
                            sendString = Protocol.loginYes();
                            writer.write(sendString);
                            writer.flush();
                            }
                            socket.close();
                            break;
                        }
                        //返回失败信息
                        sendString = Protocol.loginNo();
                        writer.write(sendString);
                        writer.flush();
                        //关闭连接、使while循环可尝试重新连接
                        socket.close();
                        break;
                    case "friend":
                        String userId = Parser.getUserId(receiveString);
                        List<User> friendList = userDAO.getFriendList(conn, userId);
                        sendString = Protocol.protocolFriendList(friendList);
                        writer.write(sendString);
                        writer.flush();
                        //关闭连接、使while循环可尝试重新连接
                        socket.close();
                }
            JDBCUtils.closeResourse(conn,null);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
