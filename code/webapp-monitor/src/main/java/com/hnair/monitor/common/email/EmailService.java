package com.hnair.monitor.common.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailPreparationException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailService {

    @Autowired
    private JavaMailSenderImpl mailSender;

    public void sendEmail(String email, EmailContent content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            // use the true flag to indicate you need a multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(email);
            message.setSubject(content.getSubject());
            helper.setText(content.getBody(), true);
            mailSender.send(message);
        } catch (MessagingException e) {
            // treat it as internal error.
            throw new MailPreparationException(e);
        }
    }

}
