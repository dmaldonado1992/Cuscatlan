package com.bd.sc.services.impl;

import com.bd.sc.dto.CustomResponse;
import com.bd.sc.dto.Product;
import com.bd.sc.models.dao.IClientDao;
import com.bd.sc.models.dao.IOrderDao;
import com.bd.sc.models.dao.IOrderDetalleDao;
import com.bd.sc.models.entity.Client;
import com.bd.sc.models.entity.Order;
import com.bd.sc.models.entity.OrderDetail;
import com.bd.sc.services.IOrderService;
import com.bd.sc.utils.HttpRequestPdv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class OrderServiceImpl implements IOrderService {

    @Value("https://fakestoreapi.com/products/")
    private String urlApi;

    @Autowired
    private IOrderDao orderDao;

    @Autowired
    private IOrderDetalleDao orderDetailDao;

    @Autowired
    private IClientDao clientDao;

    public HashMap<String,String> headers=new HashMap<String,String>();


    @Override
    public Optional<Order> findById(Long id) {

        Optional<Order> order = Optional.ofNullable(orderDao.findById(id));
        if (!order.isEmpty()){
            for (OrderDetail detail : order.get().getOrderDetail()) {

                headers.put("Content-Type","application/json; charset=UTF-8");
                com.bd.sc.dto.Product respProd=(Product) HttpRequestPdv.get(urlApi + detail.getProductId(),null,headers);
                detail.setTitle(respProd.getTitle());
                detail.setDescription(respProd.getDescription());
                detail.setCategory(respProd.getCategory());
                detail.setImage(respProd.getImage());
            }

        order.get().setClientDetail(clientDao.findById(order.get().getClientId()));


        }
        return order;
    }

    public List<Order> findAll(){
        List<Order> orders = (List<Order>) orderDao.findAll();
        Iterator<Order> iterator = orders.iterator();
        Optional<Client> client = null;
        while (iterator.hasNext()) {
            Order order = iterator.next();

            for (OrderDetail detail : order.getOrderDetail()) {
                headers.put("Content-Type","application/json; charset=UTF-8");
                com.bd.sc.dto.Product respProd=(Product) HttpRequestPdv.get(urlApi + detail.getProductId(),null,headers);
                detail.setTitle(respProd.getTitle());
                detail.setDescription(respProd.getDescription());
                detail.setCategory(respProd.getCategory());
                detail.setImage(respProd.getImage());
                detail.setTotal((float) (respProd.getPrice()*detail.getQuantity()));
                detail.setOrderId(order);
                client = clientDao.findById(order.getClientId());
            }
            order.setClientDetail(Optional.of(client.get()));
        }


        return orders;
    }

    @Override
    public CustomResponse<Order> save(Order order) {
        CustomResponse<Order> resp = new CustomResponse();
        try {
            Optional<Client> client = null;
            order.setTotal(0);
            order.setBalance(0);
            Order orderUpdate = orderDao.save(order);

            if(order.getId() != null) {
                resp.setOk(true);
                resp.setData(order);
                resp.setMessage("Registro grabado correctamente.");

                for (OrderDetail detail : order.getOrderDetail()) {
                    detail.setOrderId(order);

                    com.bd.sc.dto.Product respProd=(Product) HttpRequestPdv.get(urlApi + detail.getProductId(),null,headers);
                    detail.setTitle(respProd.getTitle());
                    detail.setDescription(respProd.getDescription());
                    detail.setCategory(respProd.getCategory());
                    detail.setImage(respProd.getImage());
                    detail.setTotal((float) (respProd.getPrice()*detail.getQuantity()));
                    detail = orderDetailDao.save(detail);
                    order.setTotal(order.getTotal()+detail.getTotal());
                    order.setBalance(order.getTotal());


                }
                order.setClientDetail(clientDao.findById(order.getClientId()));
                orderUpdate = orderDao.save(order);

            }else {
                resp.setOk(false);
                resp.setMessage("Se ha producido un error al guardar.");
                resp.setData(orderUpdate);
            }
        }catch (Exception e) {
            resp.setOk(false);
            resp.setMessage(e.getMessage());
        }
        return resp;
    }

    public CustomResponse<Order> applyPay(Order order) {

        CustomResponse<Order> resp = new CustomResponse();
        try {
            Optional<Client> client = null;

            Order orderUpdate = orderDao.findById(order.getId());
            if(orderUpdate != null) {
                orderUpdate.setPaymentstatus(order.getPaymentstatus());
                orderUpdate.setBalance(order.getBalance());
                order = orderDao.save(orderUpdate);
                order.setClientDetail(clientDao.findById(order.getClientId()));
                resp.setOk(true);
                resp.setMessage("Pago aplicado correctamente");
                resp.setData(order);
            }else {
                resp.setOk(false);
                resp.setMessage("Order not found.");
            }
        }catch (Exception e) {
            resp.setOk(false);
            resp.setMessage(e.getMessage());
        }
        return resp;
    }

}
