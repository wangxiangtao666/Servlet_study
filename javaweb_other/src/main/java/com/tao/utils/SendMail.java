package com.tao.utils;


import com.sun.mail.util.MailSSLSocketFactory;
import com.tao.pojo.User;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

//多线程
public class SendMail extends Thread{


    private User user;
    public  SendMail(User user){
        this.user= user;
    }

    @Override
    public void run() {
        try {
            Properties prop = new Properties();
            prop.setProperty("mail.host","smtp.qq.com");//设置qq邮件服务器
            prop.setProperty("mail.transport.protocol","smtp");//邮件发送协议
            prop.setProperty("mail.smtp.auth","true");//需要验证用户名密码

            //关于qq邮箱，还要设置SSL加密，其他邮箱不需要
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            prop.put("mail.smtp.ssl.enable","true");
            prop.put("mail.smtp.ssl.socketFactory",sf);

            //使用javaMail发送邮件的5个步骤
            //1.创建环境信息的Session对象
            //QQ才有，其他邮箱不用
            Session session = Session.getDefaultInstance(prop, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("2935991305@qq.com","cuxyfkdorrnudeei");
                }
            });
            //开启session的debug模式，这样就可以查看到程序发送email的运行状态
            session.setDebug(true);
            //2.通过session得到transport对象,负责发送邮件
            Transport ts = session.getTransport();

            //3.使用邮箱的用户名和授权码连上邮件服务器
            ts.connect("smtp.qq.com","2935991305@qq.com","cuxyfkdorrnudeei");
            //4.创建邮件，写邮件
            //需要传递传递session,将配置信息给message邮件对象
            MimeMessage message = new MimeMessage(session);
            //指明邮件的发件人
            message.setFrom(new InternetAddress("2935991305@qq.com"));
            //指明邮件的收件人，现在指定收件人和发件人一样，自己给自己发
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
            //邮件的标题
            message.setSubject("用户注册邮件");
            //邮件的文本内容
            String info = "恭喜您注册成功，您的用户名："+user.getUsername()+",您的密码："+user.getPassword();
            message.setContent(info,"text/html;charset=UTF-8");
            //5.发送邮件
            ts.sendMessage(message,message.getAllRecipients());
            //6.关闭连接
            ts.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
