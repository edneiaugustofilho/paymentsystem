package com.edneiaugusto.paymentsystem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class GeneralConfig {

    public JavaMailSender javaMailSender() {
        return new JavaMailSenderImpl();
    }

}
