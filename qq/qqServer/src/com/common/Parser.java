package com.common;

/**
 * Created with Intellij IDEA.
 *
 * @Author: wzr
 * @Date: 2023/11/09/9:22
 * @Description:
 */
public class Parser {
    //解析
    //判断客户端发来信息是什么用途
    public static String parseOperate(String receiveString){
        return receiveString.split("@")[0];
    }
    //获取用户名
    public static String getUserId(String receiveString){
        return ( (receiveString.split("@"))[1].split("&"))[0];
    }
    //获取密码
    public static String getPassword(String receiveString){
        return ( (receiveString.split("@"))[1].split("&"))[1];
    }
    //获取用户发送信息
    public static String getMessage(String receiveString){
        return (receiveString.split("@"))[1].split("&")[2];
    }
    //获取发送用户
    public static String getSender(String receiveString){
        return (receiveString.split("@"))[1].split("&")[0];
    }
    //获取接收用户
    public static String getGeter(String receiveString){
        return (receiveString.split("@"))[1].split("&")[1];
    }




}
