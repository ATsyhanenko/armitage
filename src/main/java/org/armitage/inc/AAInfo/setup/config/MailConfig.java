package org.armitage.inc.AAInfo.setup.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration 
@PropertySource("classpath:mail.properties")
public class MailConfig {

    @Autowired
    private Environment environment;

    @Bean
    public JavaMailSender mailSender() {       
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
       
        sender.setHost(environment.getProperty("mail.host"));
        sender.setPort(environment.getProperty("mail.port", Integer.class));
        sender.setUsername(environment.getProperty("mail.username"));
        sender.setPassword(environment.getProperty("mail.password"));
        sender.setProtocol(environment.getProperty("mail.protocol"));
        
        Properties mailProperties = new Properties();
        mailProperties.setProperty("mail.smtp.auth", environment.getProperty("mail.smtp.auth"));
        mailProperties.setProperty("mail.smtp.starttls.enable", environment.getProperty("mail.smtp.starttls.enable"));
        mailProperties.setProperty("mail.smtp.quitwait", environment.getProperty("mail.smtp.quitwait"));
        mailProperties.setProperty("mail.debug", environment.getProperty("mail.debug"));
        sender.setJavaMailProperties(mailProperties);
        
        return sender;
    }
}
