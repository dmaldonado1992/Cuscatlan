package com.bd.sc.services.impl;

import com.bd.sc.models.dao.IUserDao;
import com.bd.sc.models.entity.Role;
import com.bd.sc.models.entity.User;
import com.bd.sc.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserDao userDao;

    public Optional<User> getByUsername(String username){
        return userDao.findByUsername(username);
    }
    public Optional<User> getById(long id){
        return userDao.findById(id);
    }

    public boolean existsUsername(String username){
        return userDao.existsByUsername(username);
    }

    public User save(User user){
        return userDao.save(user);
    }

    public User delete(User user){
       userDao.delete(user);
       return user;
    }

    public List<User> findAll(){
        List<User> users = userDao.findAll();
        return users;
    }

    public List<User> findAllVendors(){
        List<User> result = new ArrayList<>();
        List<User> users = userDao.findAll();

        for(User user: users){
            boolean isVendor = false;
            for(Role role : user.getRoles()){
                if("SALE_FUEL".equals(role.getName()) || "SALE_STORE".equals(role.getName())){
                    isVendor = true;
                    break;
                }
            }
        }
        return result;
    }
}
