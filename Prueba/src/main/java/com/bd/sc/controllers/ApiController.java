package com.bd.sc.controllers;

import com.bd.sc.dto.CustomResponse;
import com.bd.sc.dto.Product;
import com.bd.sc.models.entity.Client;
import com.bd.sc.models.entity.Order;
import com.bd.sc.services.IProductService;
import com.bd.sc.services.IClientService;
import com.bd.sc.services.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private IProductService productService;

    @Autowired
    private IOrderService ordenService;

    @Autowired
    private IClientService clientService;

    @GetMapping("/products")
    public ResponseEntity<CustomResponse<List<Product>>> products(){

        List<Product> resp = productService.getProducts(null);
        CustomResponse<List<Product>> response = new CustomResponse<>();
        response.setOk(true);
        response.setData((List<Product>) resp);

        if (resp == null) {
            response.setOk(false);
            response.setMessage("No se encontraron registros");
            response.setData(null);  // No hay datos ya que no se encontró el producto
            return new ResponseEntity(response, HttpStatus.NOT_FOUND);  // 404 - Not Found
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<CustomResponse<List<Product>>> getById(@PathVariable("id") int id){
        Product product = new Product();
        product.setId(String.valueOf(id));
        Product resp = productService.getProductsById(product);

        CustomResponse<Product> response = new CustomResponse<>();
        response.setOk(true);
        response.setData(resp);

        if (resp == null) {
            response.setOk(false);
            response.setMessage("Product not found");
            response.setData(null);  // No hay datos ya que no se encontró el producto
            return new ResponseEntity(response, HttpStatus.NOT_FOUND);  // 404 - Not Found
        }

        return new ResponseEntity(response, HttpStatus.OK);  // 404 - Not Found
    }

    @GetMapping("/order/{id}")
    @ResponseBody
    public CustomResponse<Order> getByID(@PathVariable("id") long id){
        Optional<Order> resp = ordenService.findById(id);
        CustomResponse<Order> response = new CustomResponse<>();

        if (resp.isPresent()) {
            response.setOk(true);
            response.setMessage("Order found");
            response.setData(resp.get());  // Asigna el valor de Order

        } else {
            response.setOk(false);
            response.setMessage("Order not found");
        }
        return response;
    }

    @GetMapping("/order")
    public ResponseEntity<List<Order>> getAll(){
        List<Order> resp = ordenService.findAll();
        return new ResponseEntity(resp, HttpStatus.OK);
    }

    @PostMapping("/order")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        CustomResponse<com.bd.sc.models.entity.Order> resp = ordenService.save(order);
        return new ResponseEntity(resp, HttpStatus.OK);
    }

    @PutMapping("/order")
    public ResponseEntity<Order> updateOrder(@RequestBody Order order) {
        CustomResponse<com.bd.sc.models.entity.Order> resp = ordenService.save(order);
        return new ResponseEntity(resp, HttpStatus.OK);
    }

    @PostMapping("/order/applyPay")
    public ResponseEntity<CustomResponse<List<Client>>> applyPay(@RequestBody Order order){
        CustomResponse<com.bd.sc.models.entity.Order> resp = ordenService.applyPay(order);
        return new ResponseEntity(resp, HttpStatus.OK);
    }

    @GetMapping("/client/{id}")
    @ResponseBody
    public ResponseEntity<CustomResponse<List<Client>>> getByID(@PathVariable("id") int id){
        return new ResponseEntity(clientService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/client")
    public ResponseEntity<List<Client>> getClients(){
        List<Client> resp = clientService.findAll();
        return new ResponseEntity(resp, HttpStatus.OK);
    }

}
