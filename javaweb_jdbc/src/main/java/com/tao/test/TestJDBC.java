package com.tao.test;

import com.sun.deploy.uitoolkit.impl.fx.AppletStageManager;

import java.sql.*;

public class TestJDBC {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //配置信息
        //useUnicode=true&characterEncoding=utf-8解决中文乱码，搜一下jdbc  url参数
        String url = "jdbc:mysql://localhost:3306/jsp_test?useUnicode=true&characterEncoding=utf-8";
        String username="root";
        String password="taotao";
        //1、加载驱动
        Class.forName("com.mysql.jdbc.Driver");
        //2、连接数据库，产生一个连接对象，（类似流对象，像一个管子，指定了目的地）
        Connection connection = DriverManager.getConnection(url,username,password);
        //3、向数据库发送SQL的对象Statement:进行CRUD（类似管子中的小管子）
        Statement statement = connection.createStatement();
        //4、编写SQL
        String sql="select * from people";
        //5、执行查询SQL，返回一个ResultSet结果集
        ResultSet rs = statement.executeQuery(sql);//放了一个sql,在statement管子中返回一个小管子可以从中得到东西
        while(rs.next()){
            System.out.println("id="+rs.getObject("id"));
            System.out.println("name="+rs.getObject("username"));
            System.out.println("age="+rs.getObject("age"));
            System.out.println("address="+rs.getObject("address"));
        }

        //6、关闭连接，释放资源（一定做）先开后关
        rs.close();
        statement.close();
        connection.close();


    }


}
