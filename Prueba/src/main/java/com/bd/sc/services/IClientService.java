package com.bd.sc.services;

import com.bd.sc.dto.CustomResponse;
import com.bd.sc.models.entity.Client;

import java.util.List;

public interface IClientService {
    public List<Client> findAll();
    public CustomResponse<Client> findById(int id);
    public CustomResponse<Client> save(Client partner);
    public CustomResponse delete(int id);
}
