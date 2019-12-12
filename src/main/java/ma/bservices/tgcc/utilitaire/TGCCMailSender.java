/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.utilitaire;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

/**
 *
 * @author airaamane
 */
public class TGCCMailSender {

    public static final String SMTP_POST = "mail.smtp.host";
    public static final String SMTP_GMAIL = "smtp.gmail.com";
    public static final String STRING_FROM = "mail.from";
    public static final String STRING_AUTH = "mail.smtp.auth";
    public static final String STRING_SMTP_ENABLE = "mail.smtp.starttls.enable";
    public static final String STRING_SMTP_PORT = "mail.smtp.port";
    public static final String PORT_SMTP = "465";
    public static final String STRING_PASSWD = "tgcc2016+";
    public static final String STRING_MAIL_FROM = "notification@tgcc.ma";

    public static void sendMail() {

        Properties props = new Properties();
        props.put(SMTP_POST, SMTP_GMAIL);
        props.put(STRING_FROM, STRING_MAIL_FROM);
        props.put(STRING_AUTH, true);
        props.put(STRING_SMTP_ENABLE, "true");
        props.put(STRING_SMTP_PORT, PORT_SMTP);
        final String mail = STRING_MAIL_FROM;

        Authenticator authenticator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mail, STRING_PASSWD);
            }
        };

        Session session = Session.getInstance(props, authenticator);

        try {
            MimeMessage msg = new MimeMessage(session);

            msg.setFrom();
            msg.setRecipients(Message.RecipientType.TO, "yassine.jeddi@tgcc.ma");

            msg.setSubject("test subject TGCC");
            msg.setSentDate(new Date());
            msg.setText("hello test email");
            Transport.send(msg);

        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

    }

    public static void send() {

        final String username = "notification@tgcc.ma";
        final String password = "tgcc2016+";

        Properties props = new Properties();

        props.put(SMTP_POST, SMTP_GMAIL);
        props.put(STRING_FROM, STRING_MAIL_FROM);
        props.put(STRING_AUTH, true);
        props.put(STRING_SMTP_ENABLE, "true");
        props.put(STRING_SMTP_PORT, PORT_SMTP);
        
        

//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom();
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("notification@tgcc.ma"));
            message.setSubject("Testing Subject");
            message.setText("Dear Mail Crawler,"
                    + "\n\n No spam to my email, please!");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendSSL() {

        final String username = "notification@tgcc.ma";
        final String password = "tgcc2016+";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("notification@tgcc.ma"));

            message.setSubject("Testing Subject");
            message.setText("Dear Mail Crawler,"
                    + "\n\n No spam to my email, please!");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    static Properties mailServerProperties;
    static Session getMailSession;
    static MimeMessage generateMailMessage;

    public static void main(String args[]) throws AddressException, MessagingException {
        sendMail();
        System.out.println("\n\n ===> Your Java Program has just sent an Email successfully. Check your email..");
    }

    public static void generateAndSendEmail() throws AddressException, MessagingException {

        // Step1
        System.out.println("\n 1st ===> setup Mail Server Properties..");
        mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.port", "465");
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.starttls.enable", "true");
        System.out.println("Mail Server Properties have been setup successfully..");

        // Step2
        System.out.println("\n\n 2nd ===> get Mail Session..");
        getMailSession = Session.getDefaultInstance(mailServerProperties, null);
        generateMailMessage = new MimeMessage(getMailSession);
        generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress("notification@tgcc.ma"));
        //generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress("test2@crunchify.com"));
        generateMailMessage.setSubject("Greetings from Crunchify..");
        String emailBody = "Test email by Crunchify.com JavaMail API example. " + "<br><br> Regards, <br>Crunchify Admin";
        generateMailMessage.setContent(emailBody, "text/html");
        System.out.println("Mail Session has been created successfully..");

        // Step3
        System.out.println("\n\n 3rd ===> Get Session and Send mail");
        Transport transport = getMailSession.getTransport("smtp");

        // Enter your correct gmail UserID and Password
        // if you have 2FA enabled then provide App Specific Password
        transport.connect("smtp.gmail.com", "notification@tgcc.ma", "tgcc2016+");
        transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
        transport.close();
    }
}
