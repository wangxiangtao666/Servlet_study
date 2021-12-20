package com.tao.dao.user;

import com.mysql.jdbc.StringUtils;
import com.tao.dao.BaseDao;
import com.tao.pojo.Role;
import com.tao.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    public User getLoginUser(Connection connection, String userCode) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        User user = null;
        if(connection!=null){
            String sql = "select * from smbms_user where userCode=?";
            Object[] params = {userCode};
            rs= BaseDao.execute(connection,pstm,rs,sql,params);
            if(rs.next()){
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUserCode(rs.getString("userCode"));
                user.setUserName(rs.getString("userName"));
                user.setUserPassword(rs.getString("userPassword"));
                user.setGender(rs.getInt("gender"));
                user.setBirthday(rs.getDate("birthday"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setUserRole(rs.getInt("userRole"));
                user.setCreatedBy(rs.getInt("createdBy"));
                user.setCreationDate(rs.getTimestamp("creationDate"));
                user.setModifyBy(rs.getInt("modifyBy"));
                user.setModifyDate(rs.getTimestamp("modifyDate"));
            }
            BaseDao.closeResource(null,pstm,rs);


        }
        return user;
    }

    public int updatePwd(Connection connection, int id, String password) throws SQLException {
        PreparedStatement pstm=null;
        int execute = 0;
        if(connection!=null){
            String sql="update smbms_user set userPassword=? where id=?";
            Object[] params={password,id};
            execute = BaseDao.execute(connection, pstm, sql, params);
            BaseDao.closeResource(null,pstm,null);
        }

        return execute;
    }

    public int getUserCount(Connection connection, String username, int userRole) throws SQLException {
        PreparedStatement pstm =null;
        ResultSet rs = null;
        int count=0;
        ArrayList<Object> list = new ArrayList<Object>();//存放sql中的参数
        if(connection!=null){
            StringBuffer sql = new StringBuffer();
            sql.append("select count(1) as count from smbms_user u,smbms_role r where u.userRole=r.id");
            if(!StringUtils.isNullOrEmpty(username)){
                sql.append(" and u.userName like ?");
                list.add("%"+username+"%");//index:0
            }
            if(userRole>0){
                sql.append(" and u.userRole like ?");
                list.add(userRole);//index:1
            }
            //将list转化为数组
            Object[] params = list.toArray();
            System.out.println("UserDaoImpl->getUserCount:"+sql.toString());

            //这里需要从返回的rs(相当于流对象)中获取东西，所以如果在BaseDao中关闭，这边的rs就是BaseDao中rs的引用，这样就获取不到值了。
            //所以要在获取值之后才能关闭，由于PrepareStatement在它之后关闭，所以在这边关闭
            rs = BaseDao.execute(connection,pstm,rs,sql.toString(),params);
            if(rs.next()){//从结果集中获取最终的数量
                count = rs.getInt("count");
            }
            BaseDao.closeResource(null,pstm,rs);
        }
        return count;
    }

    public List<User> getUserList(Connection connection, String username, int userRole, int currentPageNo, int pageSize) throws SQLException {
        PreparedStatement pstm =null;
        ResultSet rs = null;
        int count=0;
        ArrayList<Object> list = new ArrayList<Object>();//存放sql中的参数
        List<User> userList = new ArrayList<User>();
        if(connection!=null){
            StringBuffer sql = new StringBuffer();
            sql.append("select u.*,r.roleName as userRoleName from smbms_user u,smbms_role r where u.userRole=r.id");
            if(!StringUtils.isNullOrEmpty(username)){
                sql.append(" and u.userName like ?");
                list.add("%"+username+"%");//index:0
            }
            if(userRole>0){
                sql.append(" and u.userRole like ?");
                list.add(userRole);//index:1
            }
            //添加分页
            sql.append(" order by creationDate DESC limit ?,?");
            currentPageNo = (currentPageNo-1)*pageSize;
            list.add(currentPageNo);
            list.add(pageSize);

            //将list转化为数组
            Object[] params = list.toArray();
            System.out.println("UserDaoImpl->getUserCount:"+sql.toString());

            //我觉得输入参数PrepareStatement和rs可以放在调用的函数中创建和关闭
            //这里的pstm是null值，没有关掉
            rs = BaseDao.execute(connection,pstm,rs,sql.toString(),params);
            while(rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUserCode(rs.getString("userCode"));
                user.setUserName(rs.getString("userName"));
                user.setGender(rs.getInt("gender"));
                user.setBirthday(rs.getDate("birthday"));
                user.setPhone(rs.getString("phone"));
                user.setUserRole(rs.getInt("userRole"));
                user.setUserRoleName(rs.getString("userRoleName"));
                userList.add(user);
            }
            BaseDao.closeResource(null,pstm,rs);
        }
        return userList;
    }



}
