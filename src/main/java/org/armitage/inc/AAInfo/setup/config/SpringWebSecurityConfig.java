package org.armitage.inc.AAInfo.setup.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringWebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DriverManagerDataSource dataSource;
    
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/res/**"); // #3
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth,
            DaoAuthenticationProvider authenicationProvider) throws Exception {
        auth.authenticationProvider(authenicationProvider);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPoint() {

            @Override
            public void commence(HttpServletRequest request, HttpServletResponse response,
                    AuthenticationException authException) throws IOException, ServletException {
                if (authException != null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().print("Unauthorizated....");
                }
            }
        });

        http.formLogin().successHandler(savedRequestAwareAuthenticationSuccessHandler()).loginPage("/authenticate").usernameParameter("userLogin")
                .passwordParameter("password").defaultSuccessUrl("/").failureUrl("/?error");
        
        http.logout().logoutUrl("/authenticate/logout")
                .logoutSuccessUrl("/");
        
        http.rememberMe().tokenRepository(persistentTokenRepository())
        .tokenValiditySeconds(1209600);

        http.exceptionHandling().accessDeniedPage("/");
        // http.csrf().disable();

        http.antMatcher("/**").authorizeRequests().anyRequest().permitAll();
    }
    
    @Override
    protected UserDetailsService userDetailsService() {
        return userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Autowired
    public DaoAuthenticationProvider authProvider(UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(dataSource);
        return db;
    }
    
    @Bean
    public SavedRequestAwareAuthenticationSuccessHandler 
                savedRequestAwareAuthenticationSuccessHandler() {
        
               SavedRequestAwareAuthenticationSuccessHandler auth 
                    = new SavedRequestAwareAuthenticationSuccessHandler();
        auth.setTargetUrlParameter("targetUrl");
        return auth;
    }
}
