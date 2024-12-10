package com.bd.sc.models.dao;

import com.bd.sc.models.entity.Client;
import org.springframework.data.repository.CrudRepository;

public interface IClientDao extends CrudRepository<Client, Integer> {
}
