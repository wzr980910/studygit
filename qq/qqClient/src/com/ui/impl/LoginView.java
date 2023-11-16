package com.ui.impl;

import com.pojo.User;
import com.handlemessage.HandleSendMessage;
import com.ui.JFrameFactory;
import com.ui.UiController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

/**
 * Created with Intellij IDEA.
 *
 * @Author: wzr
 * @Date: 2023/11/08/8:59
 * @Description:登录界面
 */
public class LoginView extends JFrame implements WindowListener, UiController {

    //定义logo
    JLabel logo;

    //定义账号、密码文本框
    JPanel fieldPanel;
    JLabel userLabel, passwordLabel;
    JTextField userField;
    JPasswordField passwordField;

    //定义注册登录按钮
    JPanel buttonPanel;
    JButton login, exit, registered;
    public static String userId;

    public LoginView() {
        ImageIcon imageIcon = new ImageIcon("qqClient/image/logo.png");
        setIconImage(imageIcon.getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT));
        setTitle("登录");
        //创建logo展示在登录界面
        logo = new JLabel(new ImageIcon("qqClient/image/tou.gif"));
        //创建账号密码文本框
        fieldPanel = new JPanel(new GridBagLayout());
        userLabel = new JLabel("账号:");
        passwordLabel = new JLabel("密码:");
        userField = new JTextField(8);
        passwordField = new JPasswordField(8);
        fieldPanel.add(userLabel);
        fieldPanel.add(userField);
        fieldPanel.add(passwordLabel);
        fieldPanel.add(passwordField);

        //创建注册登录操作按钮
        buttonPanel = new JPanel();
        login = new JButton("登录");
        exit = new JButton("退出");
        registered = new JButton("注册");
        buttonPanel.add(login);
        buttonPanel.add(exit);
        buttonPanel.add(registered);

        setSize(300, 240);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.add(logo, "North");
        this.add(fieldPanel, "Center");
        this.add(buttonPanel, "South");

        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //退出按钮操作
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("退出")) {
                    System.exit(0);
                }
            }
        });

        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userId = userField.getText();
                String password = passwordField.getText();
                HandleSendMessage.sendLoginMessage(userId,password);

            }
        });
        registered.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("注册")) {
                    JFrameFactory.getJFrame("register");
                    dispose();
                }
            }
        });

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    public static void main(String[] args) {
        JFrameFactory.getJFrame("login");
    }

    @Override
    public void collBack(List<User> friendList,String...returnInfos) {
        if ("Yes".equals(returnInfos[0])) {
            //设置当前账号用户名
            MainView.setUserId(userId);
            //设置当前账号好友列表
            MainView.friendList = friendList;
            JFrameFactory.getJFrame("main");
            this.dispose();
        } else if ("No".equals(returnInfos[0])) {
            JOptionPane.showMessageDialog(this, "登录失败!");
        } else {
            throw new RuntimeException("登录返回信息错误");
        }
    }
}
