package com.services.syslogin.repository;

import com.services.syslogin.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findUserByEmail(String email);
//    User findUserByEmailAndUserName(String email, String username);

    @Query(value = "select u.id_user from user u where u.userName = ?1")
    String verifyUsernameExists(String userName);

    @Query(value = "select u.id_user from user u where u.email = ?1")
    String verifyEmailExists(String userName);
}
