package org.armitage.inc.AAInfo.service.impl;

import java.util.Date;
import java.util.UUID;

import javax.transaction.Transactional;

import org.armitage.inc.AAInfo.dao.UserRepository;
import org.armitage.inc.AAInfo.entity.User;
import org.armitage.inc.AAInfo.service.MailService;
import org.armitage.inc.AAInfo.service.PushService;
import org.armitage.inc.AAInfo.service.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private MailService mailService;
    
    @Autowired
    private PushService pushService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private Logger logger;
    
    private static final long KEY_LIFETIME = 600;
    
    @Override
    @Transactional
    public void createSecurityKey(User user){
        logger.info("begin");
        
        UUID securityKey = UUID.randomUUID();
        String securityString = securityKey.toString().substring(0, 12);
        logger.info("security key is: "+securityString);
        user.setSecret(securityString);
        
        logger.info("setting up key lifetime");
        long keyLifeTime = System.currentTimeMillis()+(KEY_LIFETIME * 1000);
        user.setKeyPeriod(keyLifeTime);
        
        logger.info("pushing secret key info to user entity");
        userRepository.save(user);
       
        sendSecurityKey(user);
    }
    
    @Override
    public void sendSecurityKey(User user){
    	long keyPeriod = user.getKeyPeriod();
        Date expireDate = new Date(keyPeriod);
        String secret = user.getSecret();
        
        String message = String.format("Your security key is: %s\r\nThe key will expire on: %s", secret, expireDate);
        String topic = String.format("Security key (%s)", expireDate);
        String email = user.getEmail();
        logger.info("sending message");
        mailService.sendMessage(email, topic, message);
        logger.info("sending push message");
        pushService.sendPushMessage(user, message);
    }
       
    @Override
    @Transactional
    public User getUserByLogin(String login){
        User user = userRepository.getByLogin(login);
        return user;
    }
    
    @Override
    public boolean preAuthUserCheck(String username, String password){
        User user = getUserByLogin(username);
        if(user != null && passwordEncoder.matches(password, user.getPassword())){
           createSecurityKey(user);
           return true;
        }
        return false;
    }
}
