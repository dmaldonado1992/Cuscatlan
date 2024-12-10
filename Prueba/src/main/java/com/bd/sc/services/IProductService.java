package com.bd.sc.services;

import com.bd.sc.dto.Product;

import java.util.List;

public interface IProductService {

    public List<Product> getProducts(Product product);
    public Product getProductsById(Product product);
}
