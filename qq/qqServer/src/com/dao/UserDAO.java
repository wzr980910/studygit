package com.dao;

import com.pojo.User;

import java.sql.Connection;
import java.util.List;

/**
 * ClassName: UserDAO
 * Package: com.dao
 * Description:
 *
 * @Author:
 * @Create: 2023/11/13 -19:04
 * @Version: v1.0
 */
public interface UserDAO {
    List<User> getGroupUser(Connection conn, String groupName);

    List<User> getFriendList(Connection conn, String uid);

    User getUser(Connection conn, String uid, String pwd);

    User getUserById(Connection conn, String uid);

    Integer register(Connection conn, String uid, String pwd);
}
