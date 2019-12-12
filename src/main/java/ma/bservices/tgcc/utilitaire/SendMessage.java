/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.utilitaire;

/**
 *
 * @author admin
 */

/*
 * This program is adapted from sample code provided
 * by Google Inc at the following location:
 *          https://github.com/google/gmail-oauth2-tools
 *
 */
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Session;
import javax.mail.Message;
import javax.mail.Address;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;

import java.security.Provider;
import java.security.Security;

import com.sun.mail.smtp.SMTPTransport;

public class SendMessage extends HttpServlet {

    private static final Logger logger
            = Logger.getLogger(SendMessage.class.getName());

    public static final class OAuth2Provider extends Provider {

        private static final long serialVersionUIS = 1L;

        public OAuth2Provider() {
            super("Google OAuth2 Provider", 1.0,
                    "Provides the XOAUTH2 SASL Mechanism");
            put("SaslClientFactory.XOAUTH2",
                    "ma.bservices.tgcc.utilitaire.OAuth2SaslClientFactory");
        }
    }

    public static void initialize() {
        Security.addProvider(new OAuth2Provider());
    }

    public static SMTPTransport connectToSmtp(Session session,
            String host,
            int port,
            String userEmail,
            String oauthToken,
            boolean debug) throws Exception {

        final URLName unusedUrlName = null;
        SMTPTransport transport = new SMTPTransport(session, unusedUrlName);
        // If the password is non-null, SMTP tries to do AUTH LOGIN.
        final String emptyPassword = "";
        transport.connect(host, port, userEmail, emptyPassword);

        return transport;
    }

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        String submitName = request.getParameter("name");
        String submitEmail = request.getParameter("email");
        String submitPhone = request.getParameter("phone");
        String submitMessage = request.getParameter("message");

        try {

            String host = "smtp.gmail.com";
            int port = 587;
            String userEmail = "ridin.overseas.eraman@gmail.com";
            String appEmail = "iraamane.abdellah@gmail.com";
            String oauthToken = "";

            initialize();

            //
            // Gmail access tokens are valid for 1 hour. A more sophisticated
            // implementation would store access token somewhere and reuse it
            // if it was not expired. A new access token should be generated
            // only if access token is expired. Abandoning unexpired access
            // tokens seems wasteful.
            //
            oauthToken = AccessTokenFromRefreshToken.getAccessToken();
            Properties props = new Properties();
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.starttls.required", "true");
            props.put("mail.smtp.sasl.enable", "true");
            props.put("mail.smtp.sasl.mechanisms", "XOAUTH2");
            props.put(OAuth2SaslClientFactory.OAUTH_TOKEN_PROP, oauthToken);

            Session session = Session.getInstance(props);
            session.setDebug(true);

            SMTPTransport smtpTransport = connectToSmtp(session, host, port,
                    userEmail, oauthToken, true);

            Message message = new MimeMessage(session);
            message.setSubject("Submit from somedomain.com website");
            message.setText("Name=" + submitName + "\n\nEmail=" + submitEmail
                    + "\n\nPhone=" + submitPhone + "\n\nMessage=" + submitMessage);

            Address toAddress = new InternetAddress(appEmail);
            message.setRecipient(Message.RecipientType.TO, toAddress);

            smtpTransport.sendMessage(message, message.getAllRecipients());
            smtpTransport.close();
        } catch (MessagingException e) {
            System.out.println("Messaging Exception");
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Messaging Exception");
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("SENT");

//        String url = "/thankyou.html";
//        response.sendRedirect(request.getContextPath() + url);
    }
}
