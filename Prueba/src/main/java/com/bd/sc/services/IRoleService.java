package com.bd.sc.services;

import com.bd.sc.models.entity.Role;

import java.util.Optional;

public interface IRoleService {
    public Optional<Role> getByName(String name);
}
