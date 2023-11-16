package com.ui.impl;

import com.pojo.User;
import com.handlemessage.HandleSendMessage;
import com.ui.JFrameFactory;
import com.ui.UiController;
import com.ui.componet.JFrameShaker;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.*;


/**
 * 聊天窗体
 */
public class Chat extends JFrame implements MouseListener, UiController {
    /**
     * 聊天对方的信息Label
     */
    private JLabel otherInfoLbl;
    /**
     * 当前用户信息Lbl
     */
    private JLabel currentUserLbl;
    /**
     * 聊天信息列表区域
     */
    public JTextArea msgListArea;
    /**
     * 要发送的信息区域
     */
    public JTextArea sendArea;

    /**
     * 在线用户数统计Lbl
     */
    public static JLabel onlineCountLbl;
    public static String userId;
    public List<User> friendList;
    JPanel friendListPanel;


    public Chat(String friendName) {
        userId = MainView.userId;
        this.setTitle(userId);
        ImageIcon imageIcon = new ImageIcon("qqClient/image/logo.png");
        setIconImage(imageIcon.getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT));
        this.setTitle("QQ聊天室");
        this.setSize(550, 500);
        this.setResizable(false);

        //设置默认窗体在屏幕中央
        this.setLocationRelativeTo(null);
        //左边主面板
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        //右边用户面板
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BorderLayout());

        // 创建一个分隔窗格
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mainPanel, userPanel);
        splitPane.setDividerLocation(380);
        splitPane.setDividerSize(10);
        splitPane.setOneTouchExpandable(true);
        this.add(splitPane, BorderLayout.CENTER);

        //左上边信息显示面板
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BorderLayout());
        //右下连发送消息面板
        JPanel sendPanel = new JPanel();
        sendPanel.setLayout(new BorderLayout());

        // 创建一个分隔窗格
        JSplitPane splitpane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, infoPanel, sendPanel);
        splitpane.setDividerLocation(300);
        splitpane.setDividerSize(1);
        mainPanel.add(splitpane, BorderLayout.CENTER);

        otherInfoLbl = new JLabel(userId + "正在和" + friendName + "聊天");
        infoPanel.add(otherInfoLbl, BorderLayout.NORTH);

        msgListArea = new JTextArea();
        msgListArea.setLineWrap(true);
        msgListArea.setEditable(false);
        infoPanel.add(new JScrollPane(msgListArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));

        JPanel tempPanel = new JPanel();
        tempPanel.setLayout(new BorderLayout());
        sendPanel.add(tempPanel, BorderLayout.NORTH);

        // 聊天按钮面板
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        tempPanel.add(btnPanel, BorderLayout.CENTER);

        //字体按钮
        JButton fontBtn = new JButton(new ImageIcon("qqClient/image/font.png"));
        fontBtn.setMargin(new Insets(0, 0, 0, 0));
        fontBtn.setToolTipText("设置字体和格式");
        btnPanel.add(fontBtn);

        //表情按钮
        JButton faceBtn = new JButton(new ImageIcon("qqClient/image/sendFace.png"));
        faceBtn.setMargin(new Insets(0, 0, 0, 0));
        faceBtn.setToolTipText("选择表情");
        btnPanel.add(faceBtn);

        //发送文件按钮
        JButton shakeBtn = new JButton(new ImageIcon("qqClient/image/shake.png"));
        shakeBtn.setMargin(new Insets(0, 0, 0, 0));
        shakeBtn.setToolTipText("向对方发送窗口振动");
        btnPanel.add(shakeBtn);

        //发送文件按钮
        JButton sendFileBtn = new JButton(new ImageIcon("qqClient/image/sendPic.png"));
        sendFileBtn.setMargin(new Insets(0, 0, 0, 0));
        sendFileBtn.setToolTipText("向对方发送文件");
        btnPanel.add(sendFileBtn);


        //要发送的信息的区域
        sendArea = new JTextArea();
        sendArea.setLineWrap(true);
        sendPanel.add(new JScrollPane(sendArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));

        // 聊天按钮面板
        JPanel btn2Panel = new JPanel();
        btn2Panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        this.add(btn2Panel, BorderLayout.SOUTH);
        JButton closeBtn = new JButton("关闭");
        closeBtn.setToolTipText("退出整个程序");
        btn2Panel.add(closeBtn);
        JButton submitBtn = new JButton("发送");
        submitBtn.setToolTipText("按Enter键发送消息");
        btn2Panel.add(submitBtn);
        sendPanel.add(btn2Panel, BorderLayout.SOUTH);


        //发送震动
        shakeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new JFrameShaker(Chat.this).startShake();
                String message = userId + "向你发送窗口抖动";
                HandleSendMessage.sendMessageTo(userId, message, friendName);
                msgListArea.append(message);
            }
        });


        //发送按钮监听
        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //将文本框数据发送给服务器
                String message = sendArea.getText() + "\r\n";
                HandleSendMessage.sendMessageTo(userId, message, friendName);
                msgListArea.append("\r\n" + userId + ":\r\n" + message);
                sendArea.setText("");
            }
        });


        //在线用户列表面板
        JPanel onlineListPane = new JPanel();
        onlineListPane.setLayout(new BorderLayout());
        onlineCountLbl = new JLabel("好友列表");
        //初始化并展示好友信息
        this.friendList = MainView.friendList;
        friendListPanel = new JPanel(new GridLayout(20, 1, 4, 4));
        JLabel[] friends = new JLabel[50];
        for (int i = 0; i < friendList.size(); i++) {
            if (friendName.equals(friendList.get(i).getUserId())) {//不显示本人信息，仅显示其他好友信息
                continue;
            }
            friends[i] = new JLabel(friendList.get(i).getUserId());
            friends[i].addMouseListener(this);
            friendListPanel.add(friends[i]);
        }

        onlineListPane.add(onlineCountLbl, BorderLayout.NORTH);
        onlineListPane.add(friendListPanel);

        //当前用户面板
        JPanel currentUserPane = new JPanel();
        currentUserPane.setLayout(new BorderLayout());
        currentUserPane.add(new JLabel("当前用户"), BorderLayout.NORTH);

        // 右边用户列表创建一个分隔窗格
        JSplitPane splitPane3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, onlineListPane, currentUserPane);
        splitPane3.setDividerLocation(340);
        splitPane3.setDividerSize(1);
        userPanel.add(splitPane3, BorderLayout.CENTER);


        //当前用户信息Label
        currentUserLbl = new JLabel();
        currentUserPane.add(currentUserLbl);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        //双击响应，获得好友信息
        if (e.getClickCount() == 2) {
            String friendName = ((JLabel) e.getSource()).getText();
            JFrameFactory.getJFrame(friendName);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        JLabel jLabel = (JLabel) e.getSource();
        jLabel.setForeground(Color.red);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        JLabel jLabel = (JLabel) e.getSource();
        jLabel.setForeground(Color.black);
    }


    @Override
    public void collBack(List<User> friendList, String... returnInfos) {
        this.msgListArea.append("\r\n" + returnInfos[0] + ":\r\n" + returnInfos[1]);
    }
}