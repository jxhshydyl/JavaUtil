package com.lwp.util.email;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mcin on 2017/5/18.
 * 发送企业邮箱
 */
public class SendMailUtil {
    final static String USER_NAME = "邮箱"; // 要登陆的企业邮箱账号
    final static String PASS_WORD = "密码"; //要登陆的企业邮箱密码
    public static void sendEmail(List<String> recipients, String theme, String content)throws Exception{
        //获取Session对象
        Session session = Session.getDefaultInstance(
                        ExMailUtil.setTencentExEmail(),
                        new Authenticator() {
                            //此访求返回用户和密码的对象
                            @Override
                            protected PasswordAuthentication getPasswordAuthentication() {
                                PasswordAuthentication pa = new PasswordAuthentication(USER_NAME, PASS_WORD);
                                return pa;}
                        });
        //设置session的调试模式，发布时取消
        session.setDebug(true);
          /*
          // 有循环的情况下，如果实现群发的功能  比如 收件人方可以显示到多少个收件用户的
          MimeMessage mimeMessage = new MimeMessage(session);
          mimeMessage.setFrom(new InternetAddress(userName,userName));*/

        // 有循环的情况下，实现单独发送的功能 收件人方只显示自己的邮箱
        MimeMessage mimeMessage = new MimeMessage(session);
        // 2. From: 发件人
        mimeMessage.setFrom(new InternetAddress(USER_NAME, USER_NAME));

        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        final int num = recipients.size();
        InternetAddress[] addresses = new InternetAddress[num];
        for (int i = 0; i <num; i++) {
            addresses[i] = new InternetAddress(recipients.get(i),"接受用户","UTF-8");
        }
        mimeMessage.setRecipients(Message.RecipientType.TO, addresses);
        //设置主题
        mimeMessage.setSubject(theme);
        mimeMessage.setSentDate(new Date());
        //设置内容
        mimeMessage.setText(content);
        mimeMessage.saveChanges();
        try {
            //发送
            Transport.send(mimeMessage);
        } catch (MessagingException e) {
        }
    }
    public static void main(String[] args){
        List<String> list=new ArrayList();
        list.add("1098958507@qq.com");
        try {
            SendMailUtil.sendEmail(list,"test","test");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}