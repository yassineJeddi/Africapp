/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.service.Mensuel;
import java.util.Date;
import java.util.Properties;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
/**
 *
 * @author j.allali
 */
public class CronJob implements Job{

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        System.out.println("La fiche déclaratif !!!!!!!!!!!!!! "+new Date());
        
        Properties props = new Properties();
   props.put("mail.smtp.host", "mail.bservices.ma");
   props.put("mail.from", "jal@novway.com");
   Session session = Session.getInstance(props,null);
   try {
       MimeMessage msg = new MimeMessage(session);

       msg.setFrom();
        msg.setRecipients(Message.RecipientType.TO,"jal@novway.com");
       msg.setSubject("Message de rappel pour la fiche déclaratif");
       msg.setSentDate(new Date());
       msg.setText("Prière de remplir la fiche du pointage déclaratif. Merci\n");
       Transport.send(msg);
   }
   catch (MessagingException mex)
   {



               FacesMessage facesMessage = new FacesMessage();
               facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
               facesMessage.setSummary("" + mex);
               FacesContext facesContext = FacesContext.getCurrentInstance();
               facesContext.addMessage("null", facesMessage);
   }
//     return"";
    }
    
}
