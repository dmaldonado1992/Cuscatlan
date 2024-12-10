package com.bd.sc.services;

import com.bd.sc.dto.CustomResponse;
import com.bd.sc.models.entity.Order;

import java.util.List;
import java.util.Optional;

public interface IOrderService {

    public Optional<Order> findById(Long id);
    public List<Order> findAll();
    public CustomResponse<Order> save(Order order);
    public CustomResponse<Order> applyPay(Order order);

}
