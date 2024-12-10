package com.bd.sc.models.dao;

import com.bd.sc.models.entity.Order;
import org.springframework.data.repository.CrudRepository;

public interface IOrderDao extends CrudRepository<Order, Integer> {
    Order findById(long id);
    Order findAllBy();
}
