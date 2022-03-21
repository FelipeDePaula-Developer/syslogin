package com.services.syslogin.model.repositories;

import com.services.syslogin.model.entities.User;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Integer> {

}
