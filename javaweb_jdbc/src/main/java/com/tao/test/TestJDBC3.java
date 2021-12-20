package com.tao.test;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestJDBC3 {
    @Test
    public void test(){
        //配置信息
        //useUnicode=true&characterEncoding=utf-8解决中文乱码，搜一下jdbc  url参数
        String url = "jdbc:mysql://localhost:3306/jsp_test?useUnicode=true&characterEncoding=utf-8";
        String username="root";
        String password="taotao";

        Connection connection =null;
        PreparedStatement preparedStatement=null;
        PreparedStatement preparedStatement2=null;

        try {
            //1、加载驱动
            Class.forName("com.mysql.jdbc.Driver");

            //2、连接数据库，产生一个连接对象，（类似流对象，像一个管子，指定了目的地
            connection = DriverManager.getConnection(url,username,password);
            //关闭自动提交，开启手动提交，开启事务
            connection.setAutoCommit(false);
            //编写SQL
            String sql="update account set money=money-100 where username='A'";
            //预编译（类似管子中的小管子,这个管子先放东西）
            preparedStatement = connection.prepareStatement(sql);
            //执行查询SQL，返回影响行数
            preparedStatement.executeUpdate();

            //制造错误
            //int i=1/0;

            //编写SQL
            String sql2="update account set money=money+100 where username='B'";
            //预编译（类似管子中的小管子,这个管子先放东西）
            preparedStatement2 = connection.prepareStatement(sql2);
            //执行查询SQL，返回影响行数
            preparedStatement2.executeUpdate();

            connection.commit();//以上两条sql都执行成功了，就提交事务
            System.out.println("success");

        } catch (Exception e) {
            try {
                //如果捕获到异常，通知数据库回滚事务
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }finally{
            //关闭连接，释放资源（一定做）先开后关
            try {
                preparedStatement.close();
                preparedStatement2.close();
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }




    }

}
