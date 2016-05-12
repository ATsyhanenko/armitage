package org.armitage.inc.AAInfo.setup.config.auth;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

public class AuthenticationDetailsSource extends WebAuthenticationDetailsSource{
    public CustomAuthenticationDetails buildDetails(HttpServletRequest request) {
        return new CustomAuthenticationDetails(request);
      }
}
