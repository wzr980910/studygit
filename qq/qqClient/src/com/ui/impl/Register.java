package com.ui.impl;

import com.pojo.User;
import com.handlemessage.HandleSendMessage;
import com.ui.UiController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Register extends JFrame implements UiController {
    private JPasswordField pwdFld;
    private JPasswordField pwd2Fld;
    private JTextField nickname;
    private JComboBox head;
    private JRadioButton sex0;
    private JRadioButton sex1;
    private JButton ok;
    private JButton reset;
    private JButton cancel;

    public Register() {
        ImageIcon imageIcon = new ImageIcon("qqClient/image/logo.png");
        setIconImage(imageIcon.getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT));
        this.setTitle("注册账号");//设置标题

        getContentPane().setLayout(null);
        setResizable(false);

        setSize(380, 260);
        setLocationRelativeTo(null);


        JLabel label = new JLabel("昵称:"); //label显示
        label.setBounds(24, 36, 59, 17);
        getContentPane().add(label);

        nickname = new JTextField(); //昵称
        nickname.setBounds(90, 34, 110, 22);
        getContentPane().add(nickname);

        JLabel label5 = new JLabel("密码:*");
        label5.setBounds(24, 72, 50, 17);
        getContentPane().add(label5);

        JLabel label3 = new JLabel("确认密码:*");
        label3.setBounds(24, 107, 65, 17);
        getContentPane().add(label3);

        pwdFld = new JPasswordField();//密码框
        pwdFld.setBounds(90, 70, 110, 22);
        getContentPane().add(pwdFld);

        pwd2Fld = new JPasswordField();
        pwd2Fld.setBounds(90, 105, 110, 22);
        getContentPane().add(pwd2Fld);

        JLabel label4 = new JLabel("性别:");
        label4.setBounds(230, 36, 31, 17);
        getContentPane().add(label4);

        sex1 = new JRadioButton("男", true);
        sex1.setBounds(268, 31, 44, 25);
        getContentPane().add(sex1);
        sex0 = new JRadioButton("女");
        sex0.setBounds(310, 31, 44, 25);
        getContentPane().add(sex0);
        ButtonGroup buttonGroup = new ButtonGroup();//单选按钮组
        buttonGroup.add(sex0);
        buttonGroup.add(sex1);


        //按钮
        ok = new JButton("确认");
        ok.setBounds(27, 176, 60, 28);
        getContentPane().add(ok);

        reset = new JButton("重填");
        reset.setBounds(123, 176, 60, 28);
        getContentPane().add(reset);

        cancel = new JButton("取消");
        cancel.setBounds(268, 176, 60, 28);
        getContentPane().add(cancel);

        this.setVisible(true);


        //注册按钮操作
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = nickname.getText();
                String password = pwdFld.getText();
                HandleSendMessage.sendRegisterMessage(user, password);
            }
        });

    }


    @Override
    public void collBack(List<User> friendList, String... returnInfos) {
        if ("Yes".equals(returnInfos[0])) {
            JOptionPane.showMessageDialog(this, "注册成功");
            this.dispose();
            new LoginView();
        } else if ("No".equals(returnInfos[0])) {
            JOptionPane.showMessageDialog(this, "注册失败");
        } else {
            throw new RuntimeException("注册时返回值为空或错误");
        }
    }
}
