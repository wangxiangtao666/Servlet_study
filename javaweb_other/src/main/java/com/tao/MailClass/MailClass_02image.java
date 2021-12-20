package com.tao.MailClass;

import com.sun.mail.imap.protocol.MailboxInfo;
import com.sun.mail.util.MailSSLSocketFactory;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.security.GeneralSecurityException;
import java.util.Properties;
//发送带图片邮件
public class MailClass_02image {
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
        //需要传递传递session,将配置信息给message邮件对象
        MimeMessage message = new MimeMessage(session);
        //指明邮件的发件人
        message.setFrom(new InternetAddress("2935991305@qq.com"));
        //指明邮件的收件人，现在指定收件人和发件人一样，自己给自己发
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("2935991305@qq.com"));
        //邮件的标题
        message.setSubject("包含图片的邮件");
        //=================================================================================
        //邮件的内容
        //准备图片数据
        MimeBodyPart image = new MimeBodyPart();
            //图片需要经过数据处理，，DataHandler:数据处理
        DataHandler dh = new DataHandler(new FileDataSource("F:\\IDEAJavaFile\\javaweb_other\\src\\main\\resources\\bz.png"));
        image.setDataHandler(dh);
        image.setContentID("bz.png");//这里为MimeBodyPart对象设置Id，跟图片名称没有关系
        //准备文本数据
        MimeBodyPart text = new MimeBodyPart();
        //这是一个超文本，通过ContentID(cid)来取图片数据（cid:bz.jpg）
        text.setContent("这是一封邮件正文带图片<img src='cid:bz.png'>的邮件","text/html;charset=UTF-8");
        //描述数据关系
        MimeMultipart mm = new MimeMultipart();//将前面两个MimeBodyPart包装起来
        mm.addBodyPart(text);
        mm.addBodyPart(image);
        mm.setSubType("related");
        /*
        只有文本或者超文本，设置为alternative
        有文本，超文本，内嵌资源（图片），设置为related
        有文本，超文本，内嵌资源（图片），附件，设置为mixed
        可以无论什么内容直接设置为mixed
        * */

        //设置到消息中，保存修改
        message.setContent(mm);
        message.saveChanges();//保存修改
        //=================================================================================
        //5.发送邮件
        ts.sendMessage(message,message.getAllRecipients());
        //6.关闭连接
        ts.close();
    }
}
