package com.services.syslogin.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity(name = "user_login")
public class UserLogin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_login;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id_user", nullable = false)
    private User user;

    @NotBlank
    @Column(columnDefinition = "char")
    private String remember_me;
    @NotBlank
    @Column(columnDefinition = "timestamp")
    private String dh_login;
    @NotBlank
    private String user_ip;
    @NotBlank
    @Column(columnDefinition = "char")
    private String logged;
    @NotBlank
    private String user_key;

    public UserLogin() {

    }

    public UserLogin(User user, String remember_me, String user_ip, String logged, String user_key) {
        this.user = user;
        this.remember_me = remember_me;
        this.user_ip = user_ip;
        this.logged = logged;
        this.user_key = user_key;
    }

    public int getId_login() {
        return id_login;
    }

    public void setId_login(int id_login) {
        this.id_login = id_login;
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

    public String getLogged() {
        return logged;
    }

    public void setLogged(String logged) {
        this.logged = logged;
    }

    public String getKey() {
        return user_key;
    }

    public void setKey(String user_key) {
        this.user_key = user_key;
    }

}
