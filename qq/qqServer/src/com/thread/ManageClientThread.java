package com.thread;

import java.util.HashMap;

/**
 * Created with Intellij IDEA.
 *
 * @Author: wzr
 * @Date: 2023/11/09/19:31
 * @Description:用hashMap实现对用户线程的管理
 */
public class ManageClientThread {
    public static HashMap userToThreads = new HashMap<String, ConClientThread>();

    public static void addClientThread(String userName, ConClientThread thread) {
        //向用户与线程映射hashMap中添加用户与对应的线程
        userToThreads.put(userName, thread);
    }


    public static ConClientThread getClientThread(String userName) {
        return (ConClientThread) userToThreads.get(userName);
    }
}
