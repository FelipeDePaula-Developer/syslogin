package com.services.syslogin.model.entities;

import com.services.syslogin.model.logic.EDPassword;
import com.services.syslogin.model.logic.EncryptDecryptPassword;
import com.services.syslogin.model.utilities.WriteLog;
import com.services.syslogin.model.validations.UserDataValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.security.GeneralSecurityException;

@Entity
@Component
public class User {

    /*todo
       Adicionar injeção de dependencia ao projeto
     */

    @Transient
    private final WriteLog writeLog = new WriteLog();

    @Transient
    @Autowired
    private EDPassword edPassword;

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

    public User(String nome, String cpf, String email, String password) throws IOException {
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
        UserDataValidation udv = new UserDataValidation();
        if (udv.emailValidate(email)){

            this.email = email;

        }


    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws GeneralSecurityException, IOException {

        this.writeLog.writeLogDebug("Teste","teste");
        this.password = edPassword.encryptPassword(password);
    }
}
