package org.armitage.inc.AAInfo.service.impl;

import java.util.Date;
import java.util.UUID;

import javax.transaction.Transactional;

import org.armitage.inc.AAInfo.dao.UserRepository;
import org.armitage.inc.AAInfo.entity.User;
import org.armitage.inc.AAInfo.service.MailService;
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
        Date expireDate = new Date(keyLifeTime);
        logger.info("key will expire: "+expireDate.toString());
        user.setKeyPeriod(keyLifeTime);
        
        logger.info("pushing secret key info to user entity");
        userRepository.save(user);
        
        String message = "Your security key is: "+securityString+"\r\nThe key will expire on: "+expireDate;
        logger.info("sending message");
        mailService.sendMessage(user.getEmail(), "Security key ("+expireDate+")", message);
    }
    
    @Override
    @Transactional
    public void clearSecurityKey(String userName){
        User user = userRepository.getByLogin(userName);
        if(user != null){
            user.setSecret("");
            userRepository.save(user);
        }
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
        if(user != null){
            if(passwordEncoder.matches(password, user.getPassword())){
               createSecurityKey(user);
               return true;
            }
        }
        return false;
    }
}
