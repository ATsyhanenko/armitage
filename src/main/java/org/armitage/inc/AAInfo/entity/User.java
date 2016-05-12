package org.armitage.inc.AAInfo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;
    
    @Column(name="username", nullable=false, unique = true)
    private String userName;
    
    @Column(name="password ", nullable=false)
    private String password;
    
    @Column(name="enabled", columnDefinition="int default 1")
    private Integer enabled;
    
    @Column(name="secret", columnDefinition="varchar(64) default ''")
    private String secret;
    
    @Column(name="key_period")
    private Long keyPeriod;
    
    @Column(name="email", columnDefinition="varchar(64) default ''")
    private String email;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Long getKeyPeriod() {
        return keyPeriod;
    }

    public void setKeyPeriod(Long keyPeriod) {
        this.keyPeriod = keyPeriod;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }
}
