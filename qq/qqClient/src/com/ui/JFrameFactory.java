package com.ui;

import com.ui.impl.Chat;
import com.ui.impl.LoginView;
import com.ui.impl.MainView;
import com.ui.impl.Register;

import javax.swing.*;
import java.util.HashMap;

//管理所有界面的类
public class JFrameFactory {
    private static HashMap<String, JFrame> manageJFrames= new HashMap<>();
        public static void addJFrame(String key,JFrame jFrame){
            manageJFrames.put(key,jFrame);
    }

    public static JFrame getJFrame(String key){
        JFrame jFrame = manageJFrames.get(key);
        if (null != jFrame) {
            jFrame.show();
            return jFrame;
        }
        if("login".equals(key)){
            LoginView loginView = new LoginView();
            addJFrame("login",loginView);
            loginView.show();
            return loginView;
        }else if("register".equals(key)){
            Register register = new Register();
            addJFrame("register",register);
            register.show();
            return register;
        }else if("main".equals(key)){
            MainView mainView = new MainView();
            addJFrame("main",mainView);
            mainView.show();
            return mainView;
        }else{
            Chat chat = new Chat(key);
            addJFrame(key,chat);
            chat.show();
            return chat;
        }
    }
}
