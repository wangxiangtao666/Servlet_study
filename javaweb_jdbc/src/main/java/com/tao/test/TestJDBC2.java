package com.tao.test;

import java.sql.*;

public class TestJDBC2 {
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
        //3、编写SQL
        String sql="insert into people(username,age,address) value (?,?,?)";
        //4、预编译（类似管子中的小管子,这个管子先放东西）
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,"xiao");//给第一个占位符赋值
        preparedStatement.setInt(2,12);//给第二个占位符赋值
        preparedStatement.setString(3,"zheshidizhi");

        //5、执行查询SQL，返回影响行数
        int i = preparedStatement.executeUpdate();//这边没有参数
        if(i>0){
            System.out.println("插入成功");
        }

        //6、关闭连接，释放资源（一定做）先开后关
        preparedStatement.close();
        connection.close();


    }

}
