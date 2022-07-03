package com.services.syslogin.repository;

import com.services.syslogin.model.UserLogin;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLoginRepository extends CrudRepository<UserLogin, Integer> {
    @Query(value = "select ul from user_login ul where ul.user_key = ?1 and ul.logged = 'T' and ul.user.id_user = ?2 and ul.remember_me = 'T'")
    UserLogin getUserLoginByUser_KeyAndId_user(String key, Integer userID);
}
