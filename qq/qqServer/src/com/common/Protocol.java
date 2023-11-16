package com.common;

import com.pojo.User;

import java.util.List;

/**
 * ClassName: Protocol
 * Package: com.common
 * Description:
 *
 * @Author: 邓桂材
 * @Create: 2023/11/15 -22:33
 * @Version: v1.0
 */
public class Protocol {
    //登录及注册验证成功，打包信息
    public static String loginYes() {
        return "Yes@\r\n\r\n";
    }

    //登录及注册验证失败，打包信息
    public static String loginNo() {
        return "No@\r\n\r\n";
    }

    //打包好友列表信息
    public static String protocolFriendList(List<User> friendList) {
        String str = "";
        for (User friend : friendList) {
            str += friend.getUserId() + "@";
        }
        if (!("".equals(str))) {
            str.substring(0, str.lastIndexOf("@"));
        }
        return str + "\r\n";
    }


    //群聊消息需要更改消息的发送者
    public static String changeMessage(String senderGroupId, String getterId, String receiveMessage) {
        return "groupMessage" + "@" + senderGroupId + "#" + Parser.getSender(receiveMessage) + "&" + getterId + "&" + Parser.getMessage(receiveMessage);
    }

    //打包要发送的普通信息
    public static String protocolMessage(String str) {
        return str + "\r\n";
    }
}
