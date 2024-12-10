package com.bd.sc.services.impl;

import com.bd.sc.dto.CustomResponse;
import com.bd.sc.models.dao.IClientDao;
import com.bd.sc.models.entity.Client;
import com.bd.sc.services.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements IClientService {
    @Autowired
    private IClientDao clientDao;

    @Override
    public List<Client> findAll() {
        List<Client> list =   new ArrayList<Client>();
        Iterable<Client> resp = clientDao.findAll();
        resp.forEach(list::add);
        return list;
    }

    @Override
    public CustomResponse<Client> findById(int id) {
        CustomResponse<Client> resp = new CustomResponse<>();
        resp.setOk(true);
        Optional<Client> oItem = clientDao.findById(id);
        if(!oItem.isPresent()){
            resp.setOk(false);
            resp.setMessage("Cliente inválido.");
        }else{
            resp.setData(oItem.get());
        }
        return resp;
    }


    @Override
    public CustomResponse<Client> save(Client client) {
        CustomResponse<Client> resp = new CustomResponse();


        try {
            if(client.getId() == 0){
                client = clientDao.save(client);
            }else {

                Optional<Client> cli = clientDao.findById(client.getId());
                if(cli.isPresent()){

                    cli.get().setName(client.getName());
                    cli.get().setNit(client.getNit());
                    cli.get().setAddress(client.getAddress());
                    cli.get().setPhone(client.getPhone());
                    cli.get().setEmail(client.getEmail());
                    cli.get().setActive(client.isActive());

                    clientDao.save(cli.get());
                    client = cli.get();
                }
            }
            if (client.getId() != null) {
                resp.setOk(true);
                resp.setData(client);
            } else {
                resp.setOk(false);
                resp.setMessage("Se ha producido un error al guardar.");
            }
        }catch (Exception  e) {
            resp.setOk(false);
            resp.setMessage(e.getMessage());
        }
        finally {
            client=null;
            return resp;
        }
    }

    @Override
    public CustomResponse delete(int id) {

        CustomResponse<Client> resp = new CustomResponse();
        try{
            Optional<Client> oBrand = clientDao.findById(id);
            if(oBrand.isPresent()){
                clientDao.delete(oBrand.get());
                resp.setOk(true);
            }else{
                resp.setOk(false);
                resp.setMessage("El cliente no existe");
            }
        }catch(Exception e){
            resp.setOk(false);
            resp.setMessage("Se ha producido un error, favor intente despues.");
            if(e.getMessage().contains("constraint")){
                resp.setMessage("El cliente está siendo utilizado, por lo que no se puede eliminar.");
            }
        }
        return resp;
    }


}
