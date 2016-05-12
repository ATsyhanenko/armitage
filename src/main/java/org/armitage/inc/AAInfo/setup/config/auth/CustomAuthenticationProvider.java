package org.armitage.inc.AAInfo.setup.config.auth;

import org.armitage.inc.AAInfo.service.UserService;
import org.armitage.inc.AAInfo.service.impl.UserDetailsWithExtraKey;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;


public class CustomAuthenticationProvider extends DaoAuthenticationProvider{
    @Autowired
    private Logger logger;
    @Autowired
    private UserService userService;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
            UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        logger.info("start");
        super.additionalAuthenticationChecks(userDetails, authentication);
        logger.info("start custom");
        if(userDetails instanceof UserDetailsWithExtraKey.CustomUserDetails){
            UserDetailsWithExtraKey.CustomUserDetails userDetailsWithKey = (UserDetailsWithExtraKey.CustomUserDetails) userDetails;
            if(authentication.getDetails() instanceof CustomAuthenticationDetails){
                CustomAuthenticationDetails customAuth = (CustomAuthenticationDetails)authentication.getDetails();
                String expectedKey = userDetailsWithKey.getSecret();
                String actualKey = customAuth.getSecretKey();
                
                if(actualKey == null || !actualKey.equals(expectedKey)){
                    logger.info("expected: "+expectedKey+", got: "+actualKey);
                    throw new BadCredentialsException("Invalid secret key");
                }
                
                long serverTime = System.currentTimeMillis();
                Long keyLifeTime = userDetailsWithKey.getKeyLifeTime();
                if(keyLifeTime == null || serverTime > keyLifeTime){
                    logger.info("key expired");
                    throw new BadCredentialsException("Secret key is expired");
                }
                
                userService.clearSecurityKey(userDetailsWithKey.getUsername());
            }else{
                logger.error("couldnt cast to CustomAuthenticationDetails");
            }
        }else{
            logger.error("couldnt cast to UserDetailsWithExtraKey");
            logger.error("details are: "+userDetails.getClass().getCanonicalName());
        }
    }
}
