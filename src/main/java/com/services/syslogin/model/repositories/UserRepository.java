package com.services.syslogin.model.repositories;

import com.services.syslogin.model.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    @Query(value = "select u.id, u.userName ,u.email, u.password  from User u where u.email = ?1")
    String searchUserPerEmail(String email);

    @Query(value = "select u.id from User u where u.userName = ?1")
    String verifyUsernameExists(String userName);

    @Query(value = "select u.id from User u where u.email = ?1")
    String verifyEmailExists(String userName);
}
