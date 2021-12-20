package com.tao.MailClass;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.security.GeneralSecurityException;
import java.util.Properties;

public class MailClass_03file {
    public static void main(String[] args) throws GeneralSecurityException, MessagingException {
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
        MimeMessage mimeMessage = imageMail(session);

        //=================================================================================
        //5.发送邮件
        ts.sendMessage(mimeMessage,mimeMessage.getAllRecipients());
        //6.关闭连接
        ts.close();
    }

    public static MimeMessage imageMail(Session session) throws MessagingException {
        //需要传递传递session,将配置信息给message邮件对象
        MimeMessage message = new MimeMessage(session);
        //指明邮件的发件人
        message.setFrom(new InternetAddress("2935991305@qq.com"));
        //指明邮件的收件人，现在指定收件人和发件人一样，自己给自己发
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("2935991305@qq.com"));
        //邮件的标题
        message.setSubject("包含附件的邮件");
        //=================================================================================
        //邮件的内容
        //图片数据
        MimeBodyPart imageBody = new MimeBodyPart();
        //图片需要经过数据处理，，DataHandler:数据处理
        DataHandler dh = new DataHandler(new FileDataSource("F:\\IDEAJavaFile\\javaweb_other\\src\\main\\resources\\bz.png"));
        imageBody.setDataHandler(dh);
        imageBody.setContentID("bz.png");//这里为MimeBodyPart对象设置Id，跟图片名称没有关系
        //文本数据
        MimeBodyPart textBody = new MimeBodyPart();
        //这是一个超文本，通过ContentID(cid)来取图片数据（cid:bz.jpg）
        textBody.setContent("这是一封邮件正文带图片和附件<img src='cid:bz.png'>的邮件","text/html;charset=UTF-8");
        //附件数据
        MimeBodyPart fileBody = new MimeBodyPart();
        fileBody.setDataHandler(new DataHandler(new FileDataSource("F:\\IDEAJavaFile\\javaweb_other\\src\\main\\resources\\1.txt")));
        fileBody.setFileName("hh.txt");//给附件设置名字

        //描述数据关系
            //正文内容
        MimeMultipart mm1 = new MimeMultipart();//将前面两个MimeBodyPart包装起来
        mm1.addBodyPart(textBody);
        mm1.addBodyPart(imageBody);
        mm1.setSubType("related");
        //将正文内容再放进一个body对象中
        MimeBodyPart contextBody = new MimeBodyPart();
        contextBody.setContent(mm1);
            //将正文和附件拼接
        MimeMultipart allFile = new MimeMultipart();
        allFile.addBodyPart(contextBody);
        allFile.addBodyPart(fileBody);
        allFile.setSubType("mixed");



        //设置到消息中，保存修改
        message.setContent(allFile);
        message.saveChanges();//保存修改
        return message;
    }
}
