package com.bd.sc.utils;

import com.bd.sc.dto.Product;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class HttpRequestPdv {

    public static <T> T post(String url, String body, HashMap<String,String> headers){
       try{
        RestTemplate http=new RestTemplate();
        MultiValueMap<String, String> h = new LinkedMultiValueMap<>();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            h.put(entry.getKey(), Arrays.asList(entry.getValue()));
        }
        HttpEntity<String> request = new HttpEntity<String>(body, h);
        ResponseEntity rest=http.postForEntity(url,request, Product.class);
        return (T) rest.getBody();
       }
       catch (Exception e){
           System.out.println(e.getMessage());
       }
       return (T) "";
    }

    public static <T> T get(String url, String body, HashMap<String,String> headers){
        try{
            RestTemplate http=new RestTemplate();
            MultiValueMap<String, String> h = new LinkedMultiValueMap<>();
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                h.put(entry.getKey(), Arrays.asList(entry.getValue()));
            }
            HttpEntity<String> request = new HttpEntity<String>(body, h);
            ResponseEntity rest=http.getForEntity(url, Product.class);

            return (T) rest.getBody();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return (T) "";
    }

    public static <T> T getList(String url, String body, HashMap<String, String> headers) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            // Crear las cabeceras HTTP
            HttpHeaders httpHeaders = new HttpHeaders();
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpHeaders.add(entry.getKey(), entry.getValue());
            }

            // Crear la entidad HTTP con el cuerpo y las cabeceras
            HttpEntity<String> requestEntity = new HttpEntity<>(body, httpHeaders);

            // Realizar la solicitud GET y obtener la respuesta como una lista de objetos Pts
            ResponseEntity<List<Product>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<List<Product>>() {}
            );

            // Retornar el resultado deserializado
            return (T) response.getBody();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;  // En caso de error, retornar null
    }
}
