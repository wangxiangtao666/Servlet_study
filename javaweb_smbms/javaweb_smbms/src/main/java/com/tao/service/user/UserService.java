package com.tao.service.user;

import com.tao.pojo.User;

import java.util.List;

public interface UserService {
    //用户登录
    public User login(String userCode, String password);
    //根据用户Id修改密码
    public boolean updatePwd(int id, String password);
    //查询用户总数
    int getUserCount(String username,int userRole);
    //获取一页用户
    List<User> getUserList(String username,int userRole,int currentPageNo,int pageSize);
}
