package com.abc.customer.onboarding.email;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderSevice {
    private final JavaMailSender mailSender;

    public EmailSenderSevice(JavaMailSenderImpl mailSender) {
        this.mailSender = mailSender;
    }


    /**
     * Sends the mail to the customer with all the onboarding information
     *
     * @param email
     */
    public void sendSimpleEmail(String email, String lastname, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@customer-onboarding.com");
        message.setTo(email);
        message.setSubject("onboarding information for bank ABC");
        message.setText(createEmailText(lastname, email, password));

        mailSender.send(message);
    }

    private String createEmailText(String lastname, String email, String password) {
        return "Dear " + lastname + ",\n\n" +
                "Thank you for choosing bank ABC. We are excited to have you as a customer.\n\n" +
                "Please find below the onboarding information:\n\n" +
                "1. Account opening process\n" +
                "2. Online banking access\n" +
                "3. Contact information\n\n" +
                "To login to your account, please use the following credentials:\n" +
                "Email: " + email + "\n" +
                "Password: " + password + "\n\n" +
                "If you have any questions, please don't hesitate to contact us.\n\n" +
                "Best regards,\n" +
                "Bank ABC Team";
    }
}
