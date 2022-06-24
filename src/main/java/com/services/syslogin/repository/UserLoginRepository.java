package com.services.syslogin.repository;

import com.services.syslogin.model.UserLogin;
import org.springframework.data.repository.CrudRepository;

public interface UserLoginRepository extends CrudRepository<UserLogin, Integer> {
    UserLogin findUserLoginByKey(String loginKey);
}
