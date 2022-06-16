package com.study.resumemanager.repository;

import com.study.resumemanager.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u LEFT JOIN FETCH u.roles where u.username = ?1")
    User findByUsername(String username);

    @Override
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles")
    List<User> findAll();
}
