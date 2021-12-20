package com.tao.dao.user;

import com.tao.pojo.Role;
import com.tao.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    //获取登录的用户
    User getLoginUser(Connection connection, String userCode) throws SQLException;
    //修改当前用户密码
    int updatePwd(Connection connection,int id,String password) throws SQLException;
    //查询用户总数
    int getUserCount(Connection connection,String username,int userRole) throws SQLException;
    //获取一页用户
    List<User> getUserList(Connection connection,String username,int userRole,int currentPageNo,int pageSize)throws SQLException;


}
