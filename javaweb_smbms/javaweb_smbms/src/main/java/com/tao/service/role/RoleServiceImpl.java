package com.tao.service.role;

import com.tao.dao.BaseDao;
import com.tao.dao.role.RoleDao;
import com.tao.dao.role.RoleDaoImpl;
import com.tao.pojo.Role;
import com.tao.pojo.User;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class RoleServiceImpl implements RoleService {
    //直接创建的时候引入dao层对象
    private RoleDao roleDao;

    public RoleServiceImpl() {
        this.roleDao = new RoleDaoImpl();
    }

    public List<Role> getRoleList() {
        Connection connection = null;
        List<Role> roleList = null;
        try {
            connection = BaseDao.getConnection();
            roleList = roleDao.getRoleList(connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }
        return roleList;
    }

    @Test
    public void testGetRoleList(){
        RoleServiceImpl roleService = new RoleServiceImpl();
        List<Role> roleList = roleService.getRoleList();
        for (Role role : roleList) {
            System.out.println(role);
        }

    }
}
