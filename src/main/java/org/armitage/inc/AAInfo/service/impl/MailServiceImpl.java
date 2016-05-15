package org.armitage.inc.AAInfo.service.impl;

import org.armitage.inc.AAInfo.service.MailService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService{
    @Autowired
    private Logger logger;
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Override
    @Async
    public void sendMessage(String to, String topic, String text){
        logger.info("begin");
        logger.info("sending message to "+to);
        logger.info("thread: "+Thread.currentThread());
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(topic);
        message.setText(text);
        logger.info("sending");
        mailSender.send(message);
        logger.info("end");
    }
    
}
