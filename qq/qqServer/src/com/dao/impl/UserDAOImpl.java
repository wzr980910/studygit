package com.dao.impl;

import com.pojo.User;
import com.dao.DAO;
import com.dao.UserDAO;

import java.sql.Connection;
import java.util.List;

/**
 * ClassName: UserDAOImpl
 * Package: com.dao
 * Description:
 *
 * @Author:
 * @Create: 2023/11/13 -19:05
 * @Version: v1.0
 */
public class UserDAOImpl extends DAO<User> implements UserDAO {
    @Override
    public List<User> getGroupUser(Connection conn, String groupName) {
        return super.getForList(conn, "SELECT t2.uid userId FROM t_group t1 JOIN t_group_user t2 ON t1.groupName=t2.groupName WHERE t1.groupName=?", groupName);
    }

    public List<User> getFriendList(Connection conn, String uid) {
        return super.getForList(conn, "SELECT u2.uid userId,u2.pwd userPassword FROM t_user u1 JOIN t_friend f1 ON u1.uid=f1.uid JOIN t_user u2 ON f1.fid=u2.uid WHERE u1.uid=?", uid);
    }

    public User getUser(Connection conn, String uid, String pwd) {
        return super.getInstance(conn, "select uid userId,pwd userPassword from t_user where uid=? and pwd=?", uid, pwd);
    }

    public User getUserById(Connection conn, String uid) {
        return super.getInstance(conn, "select uid userId,pwd userPassword from t_user where uid=?", uid);
    }

    @Override
    public Integer register(Connection conn, String uid, String pwd) {
        return super.Update(conn, "insert into t_user(uid,pwd) values(?,?)", uid, pwd);
    }
}
