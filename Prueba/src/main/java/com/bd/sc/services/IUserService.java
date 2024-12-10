package com.bd.sc.services;

import com.bd.sc.models.entity.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    public Optional<User> getByUsername(String username);
    public Optional<User> getById(long id);
    public boolean existsUsername(String username);
    public User save(User user);
    public User delete(User user);
    public List<User> findAll();
    public List<User> findAllVendors();

}
