package com.abc.customer.onboarding.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class BeanConfiguration {

    @Bean
    public JavaMailSenderImpl mailSender() {
        return new JavaMailSenderImpl();
    }
}
