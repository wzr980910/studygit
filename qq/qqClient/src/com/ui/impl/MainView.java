package com.ui.impl;

import com.pojo.User;
import com.ui.JFrameFactory;
import com.ui.UiController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

/**
 * Created with Intellij IDEA.
 *
 * @Author: wzr
 * @Date: 2023/11/08/10:04
 * @Description:登录成功进去好友界面，点击好友打开聊天界面。
 */
public class MainView extends JFrame implements MouseListener, UiController {
    //创建顶部信息按钮
    JPanel friendTop;
    JLabel friend;
    //创建展示好友信息
    JPanel friendListPanel;
    //创建展示好友信息
    JPanel friendListJPanel;
    JScrollPane jScrollPane;
    //创建底部退出按钮
    JPanel friendButton;
    JButton exit;
    //获取用户自己的名称，并展示在好友列表名单和聊天界面
    public static String userId;
    public static List<User> friendList;

    public MainView() {
        //展示顶部信息
        friend = new JLabel("我的好友");
        friendTop = new JPanel();
        friendTop.add(friend);

        //初始化并展示好友信息
        friendListPanel = new JPanel(new GridLayout(20, 1, 4, 4));
        JLabel[] friends = new JLabel[50];
        for (int i = 0; i < friendList.size(); i++) {
            friends[i] = new JLabel(friendList.get(i).getUserId());
            friends[i].addMouseListener(this);
            friendListPanel.add(friends[i]);
        }
        //先写一个qq群
        JLabel group1 = new JLabel("group1");
        group1.addMouseListener(this);
        friendListPanel.add(group1);
        jScrollPane = new JScrollPane(friendListPanel);

        //展示底部信息
        friendButton = new JPanel();
        exit = new JButton("退出");
        friendButton.add(exit);


        setSize(400, 800);
        setTitle(userId);
        this.add(friendTop, "North");
        this.add(jScrollPane, "Center");
        this.add(friendButton, "South");
        setVisible(true);


        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("退出")) {
                    System.exit(0);
                }
            }
        });
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //双击响应，获得好友信息
        if (e.getClickCount() == 2) {
            String friendName = ((JLabel) e.getSource()).getText();
            //由于Chat界面引用runnable接口，此时它也是线程，因此需要在此处启动
            Chat chat = (Chat) JFrameFactory.getJFrame(friendName);
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


    public static void setUserId(String userId) {
        MainView.userId = userId;
    }

    @Override
    public void collBack(List<User> friendList, String... returnINfos) {

    }
}
