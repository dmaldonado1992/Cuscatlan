package com.bd.sc.services.impl;

import com.bd.sc.models.dao.IRoleDao;
import com.bd.sc.models.entity.Role;
import com.bd.sc.services.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private IRoleDao roleDao;

    public Optional<Role> getByName(String name){
        return roleDao.findByName(name);
    }
}
