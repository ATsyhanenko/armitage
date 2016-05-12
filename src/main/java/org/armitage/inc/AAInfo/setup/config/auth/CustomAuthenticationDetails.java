package org.armitage.inc.AAInfo.setup.config.auth;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class CustomAuthenticationDetails extends WebAuthenticationDetails {
    private static final long serialVersionUID = 3159928573878529961L;
    private String secretKey;

    public CustomAuthenticationDetails(HttpServletRequest request) {
        super(request);
        secretKey = request.getParameter("secret");
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
