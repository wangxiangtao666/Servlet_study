package com.tao.dao.role;

import com.tao.dao.BaseDao;
import com.tao.pojo.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDaoImpl implements RoleDao {


    public List<Role> getRoleList(Connection connection) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        ArrayList<Role> roleList = new ArrayList<Role>();
        if(connection!=null){
            String sql = "select * from smbms_role";
            Object[] params = {};
            rs = BaseDao.execute(connection,pstm,rs,sql,params);
        }
        while(rs.next()){
            Role role = new Role();
            role.setId(rs.getInt("id"));
            role.setRoleCode(rs.getString("roleCode"));
            role.setRoleName(rs.getString("roleName"));
            roleList.add(role);
        }
        BaseDao.closeResource(null,pstm,rs);

        return roleList;
    }
}
