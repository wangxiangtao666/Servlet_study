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
        ArrayList<Object> list = new ArrayList<Object>();//??????sql????????????
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
            //???list???????????????
            Object[] params = list.toArray();
            System.out.println("UserDaoImpl->getUserCount:"+sql.toString());

            //????????????????????????rs(??????????????????)?????????????????????????????????BaseDao?????????????????????rs??????BaseDao???rs??????????????????????????????????????????
            //????????????????????????????????????????????????PrepareStatement??????????????????????????????????????????
            rs = BaseDao.execute(connection,pstm,rs,sql.toString(),params);
            if(rs.next()){//????????????????????????????????????
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
        ArrayList<Object> list = new ArrayList<Object>();//??????sql????????????
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
            //????????????
            sql.append(" order by creationDate DESC limit ?,?");
            currentPageNo = (currentPageNo-1)*pageSize;
            list.add(currentPageNo);
            list.add(pageSize);

            //???list???????????????
            Object[] params = list.toArray();
            System.out.println("UserDaoImpl->getUserCount:"+sql.toString());

            //?????????????????????PrepareStatement???rs?????????????????????????????????????????????
            //?????????pstm???null??????????????????
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
