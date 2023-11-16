package com.thread;

import com.common.Parser;
import com.common.Protocol;
import com.dao.jdbcutils.JDBCUtils;
import com.pojo.User;
import com.dao.UserDAO;
import com.dao.impl.UserDAOImpl;


import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.util.List;

/**
 * Created with Intellij IDEA.
 *
 * @Author: wzr
 * @Date: 2023/11/09/15:18
 * @Description:用线程将服务器和客户端单独用一个通道连接，实现用户聊天（在此实现数据转发工作）
 */
public class ConClientThread extends Thread {
    Socket socket;
    public ConClientThread(Socket socket) {
        //将服务器和客户端的连接赋给socket
        this.socket = socket;
    }
    @Override
    public void run() {
        
        while (true) {

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String receiveMessage = reader.readLine();
                //该线程接收客户端信息
                if (Parser.parseOperate(receiveMessage).equals("message")) {
                    //完成转发
                    //取得接收人的通信线程
                    ConClientThread getterThread = ManageClientThread.getClientThread(Parser.getGeter(receiveMessage));
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(getterThread.socket.getOutputStream()));
                    writer.write(Protocol.protocolMessage(receiveMessage));
                    writer.flush();
                } else if ("groupMessage".equals(Parser.parseOperate(receiveMessage))) {//处理群聊信息
                    String senderGroupId = Parser.getGeter(receiveMessage);
                    System.out.println(senderGroupId);
                    UserDAO userDAO = new UserDAOImpl();
                    Connection conn = JDBCUtils.getConnection();
                    List<User> groupUser = userDAO.getGroupUser(conn, senderGroupId);
                    JDBCUtils.closeResourse(conn, null);
                    //发送给该群中每一个在线用户
                    for (User user : groupUser) {
                        if(user.getUserId()==Parser.getSender(receiveMessage)){
                            continue;
                        }
                        String sendMessage = Protocol.changeMessage(senderGroupId, user.getUserId(), receiveMessage);
                        ConClientThread getterThread = ManageClientThread.getClientThread(user.getUserId());
                        if (null != getterThread) {
                            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(getterThread.socket.getOutputStream()));
                            writer.write(Protocol.protocolMessage(sendMessage));
                            writer.flush();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
