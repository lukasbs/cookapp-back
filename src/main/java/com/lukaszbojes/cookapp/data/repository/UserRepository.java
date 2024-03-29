package com.lukaszbojes.cookapp.data.repository;

import com.lukaszbojes.cookapp.data.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByName(String name);
    List<User> findAllByNameStartingWith(String name);
    User findByNameAndPassword(String name, String password);
    void deleteByName(String name);
}
