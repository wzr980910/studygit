package com.thread;

/**
 * Created with Intellij IDEA.
 *
 * @Author: wzr
 * @Date: 2023/11/11/14:03
 * @Description:管理客户端和服务器保持通讯的线程类
 */
public class ManageClientConServerThread {
    private static ClientConServerThread clientConServerThread;
    public static void setClientConServerThread(ClientConServerThread clientConServerThread) {
        ManageClientConServerThread.clientConServerThread = clientConServerThread;
    }
}
