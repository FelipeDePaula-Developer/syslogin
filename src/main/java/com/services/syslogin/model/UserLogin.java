package com.services.syslogin.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class UserLogin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int login_id;

    @ManyToOne
    @NotBlank
    private User user;
    @NotBlank
    @Column(columnDefinition ="char")
    private String remember_me;
    @NotBlank
    @Column(columnDefinition = "timestamp")
    private String dh_login;
    @NotBlank
    private String user_ip;

    public UserLogin(){

    }

    public UserLogin(String remember_me, String user_ip) {
        this.remember_me = remember_me;
        this.user_ip = user_ip;
    }

    public int getLogin_id() {
        return login_id;
    }

    public void setLogin_id(int login_id) {
        this.login_id = login_id;
    }

    public User getUser_id() {
        return user;
    }

    public void setUser_id(User user) {
        this.user = user;
    }

    public String getRemember_me() {
        return remember_me;
    }

    public void setRemember_me(String remember_me) {
        this.remember_me = remember_me;
    }

    public String getDh_login() {
        return dh_login;
    }

    public void setDh_login(String dh_login) {
        this.dh_login = dh_login;
    }

    public String getUser_ip() {
        return user_ip;
    }

    public void setUser_ip(String user_ip) {
        this.user_ip = user_ip;
    }
}
