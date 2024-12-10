package com.bd.sc.services.impl;

import com.bd.sc.dto.Product;
import com.bd.sc.services.IProductService;
import com.bd.sc.utils.HttpRequestPdv;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements IProductService {


    @Value("https://fakestoreapi.com/products/")
    private String urlApi;


    @Override
    public List<Product> getProducts(Product product) {
        System.out.println("Products");
        String responseJson = null;
        HashMap<String,String> headers=new HashMap<String,String>();
        headers.put("Content-Type","application/json; charset=UTF-8");

        List<Product> resp=(List<Product>) HttpRequestPdv.getList(urlApi ,responseJson,headers);

        return resp;

    }

    @Override
    public Product getProductsById(Product product) {
        System.out.println("Products");
        HashMap<String,String> headers=new HashMap<String,String>();
        headers.put("Content-Type","application/json; charset=UTF-8");
       // headers.put("Authorization",token);

        ObjectMapper mapper = new ObjectMapper();
        String responseJson = null;
        try {
            responseJson = mapper.writeValueAsString(product);
            //Se desactiva transaction porque no se utiliza en la peticion
            responseJson = responseJson.replace("Transaction","TransactionDisabled");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Product resp=(Product) HttpRequestPdv.get(urlApi + product.getId(),responseJson,headers);

        return resp;

    }




}


