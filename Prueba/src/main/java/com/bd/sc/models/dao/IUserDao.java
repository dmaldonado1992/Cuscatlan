package com.bd.sc.models.dao;

import com.bd.sc.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserDao extends JpaRepository<User, Long> {
    public Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    public Optional<User> findById(int id);

}
