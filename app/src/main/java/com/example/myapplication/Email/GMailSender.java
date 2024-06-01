package com.example.myapplication.Email;

import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Security;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.*;
import javax.mail.internet.MimeMultipart;

public class GMailSender extends Authenticator {
    private String mailhost = "smtp.gmail.com";
    private String user = "";
    private String password = "";
    private Session session = null;

    static {
        Security.addProvider(new JSSEProvider());
    }

    public GMailSender(String user, String password) {
        this.user = user;
        this.password = password;

//        Properties props = new Properties();
//        props.setProperty("mail.transport.protocol", "smtp");
//        props.setProperty("mail.host", mailhost);
//        props.put("mail.smtp.port", "587");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.isSSL", "false");
//        props.put("mail.smtp.socketFactory.port", "587");
//        props.put("mail.smtp.socketFactory.class",
//                "javax.net.ssl.SSLSocketFactory");
//        props.put("mail.smtp.socketFactory.fallback", "false");
//        props.setProperty("mail.smtp.quitwait", "false");
//
//        session = Session.getDefaultInstance(props, this);
//        Log.i("mail info", session.toString());
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(user, password);
    }

    private Session getSession() {
        //Gmail Host
        String host = mailhost;
        String u = "exceptionkindom@gmail.com";
        String p = "blccnbhlmncpfkhr";

        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", host);
        prop.put("mail.smtp.port", 587);
        prop.put("mail.smtp.ssl.trust", host);

        return Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(u, p);
            }
        });
    }
//    public synchronized void sendMail(String subject, String body, String sender, String recipients) throws Exception {
//        try{
//            MimeMessage message = new MimeMessage(session);
//            DataHandler handler = new DataHandler(new ByteArrayDataSource(body.getBytes(), "text/plain"));
//            message.setSender(new InternetAddress(sender));
//            message.setSubject(subject);
//            message.setDataHandler(handler);
//            if (recipients.indexOf(',') > 0)
//                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));
//            else
//                message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipients));
//            Transport.send(message);
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }

    public String sendMail() {
        String result;
        try {
            Log.i("Sending Email to {}", "trihuynh1811@gmail.com");
            Session session = getSession();
            MimeMessage message = new MimeMessage(session);
            Log.i("sending mail", "1");
            message.setFrom(new InternetAddress("exceptionkindom@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress("trihuynh1811@gmail.com"));
            message.setSubject("test mail lmao");
            message.setText("gay");
            Log.i("sending mail", "2");
//            MimeBodyPart mimeBodyPart = new MimeBodyPart();
//            mimeBodyPart.setContent("gay nigga", "text/html");
//            Log.i("sending mail", "3");
//            Multipart multipart = new MimeMultipart();
//            multipart.addBodyPart(mimeBodyPart);
//            Log.i("sending mail", "4");
//            message.setContent(multipart);
            Transport.send(message);
            Log.i("success send mail", "send mail success");
            result = "send mail success";
        } catch (Exception e) {
            e.printStackTrace();
            result = "something went wrong";
        }
        return result;
    }

    public class ByteArrayDataSource implements DataSource {
        private byte[] data;
        private String type;

        public ByteArrayDataSource(byte[] data, String type) {
            super();
            this.data = data;
            this.type = type;
        }

        public ByteArrayDataSource(byte[] data) {
            super();
            this.data = data;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContentType() {
            if (type == null)
                return "application/octet-stream";
            else
                return type;
        }

        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(data);
        }

        public String getName() {
            return "ByteArrayDataSource";
        }

        public OutputStream getOutputStream() throws IOException {
            throw new IOException("Not Supported");
        }
    }
}