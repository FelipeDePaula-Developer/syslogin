package com.services.syslogin.model.entities;

import com.services.syslogin.model.logic.EncryptDecryptPassword;
import com.services.syslogin.model.validations.UserDataValidation;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.security.GeneralSecurityException;

@Entity
public class User {


    @Transient
    private final EncryptDecryptPassword edPassword = new EncryptDecryptPassword();

    @Transient
    private final UserDataValidation dataValidation = new UserDataValidation();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String nome;
    @NotBlank
    private String cpf;
    @NotBlank
    private String email;
    @NotBlank
    private String password;

    public User() throws IOException {

    }

    public User(String nome, String cpf, String email, String password) {
        super();
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = dataValidation.emailValidate(email);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws GeneralSecurityException, IOException {
        this.password = edPassword.encryptPassword(password);
    }
}
