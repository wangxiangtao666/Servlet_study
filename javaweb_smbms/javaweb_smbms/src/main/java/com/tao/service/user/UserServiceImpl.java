package com.tao.service.user;

import com.tao.dao.BaseDao;
import com.tao.dao.user.UserDao;
import com.tao.dao.user.UserDaoImpl;
import com.tao.pojo.User;
import org.junit.Test;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService{
    private UserDao userDao;
    public UserServiceImpl(){
        userDao =new UserDaoImpl();

    }

    public User login(String userCode, String password) {
        Connection connection=null;
        User user = null;
        try {
            connection= BaseDao.getConnection();
            user = userDao.getLoginUser(connection,userCode);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }
        return user.getUserPassword().equals(password)?user:null;
    }

    @Test
    public void test(){
        UserServiceImpl userService=new UserServiceImpl();
        User admin = userService.login("admin", "123");
        if(admin!=null){
            System.out.println(admin.getUserPassword());
        }else{
            System.out.println("none");
        }



    }

    public boolean updatePwd(int id, String password) {
        Connection connection = null;
        boolean flag = false;

        try {
            //修改密码
            connection = BaseDao.getConnection();
            if(userDao.updatePwd(connection,id,password)>0){
                flag = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }
        return flag;
    }
    //查询记录数
    public int getUserCount(String username, int userRole) {
        Connection connection = null;
        int count = 0;
        try {
            connection = BaseDao.getConnection();
            count=userDao.getUserCount(connection,username,userRole);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }
        return count;
    }
    @Test
    public void testGetUserCount(){
        UserServiceImpl userService = new UserServiceImpl();
        int userCount = userService.getUserCount(null, 1);
        System.out.println(userCount);
    }

    public List<User> getUserList(String username, int userRole, int currentPageNo, int pageSize) {
        Connection connection = null;
        List<User> userList = null;
        try {
            connection = BaseDao.getConnection();
            userList=userDao.getUserList(connection,username,userRole,currentPageNo,pageSize);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }
        return userList;
    }

    @Test
    public void testGetUserList(){
        UserServiceImpl userService = new UserServiceImpl();
        List<User> userList = userService.getUserList(null, 3,2,3);
        for (User user : userList) {
            System.out.println(user);
        }

    }
}
