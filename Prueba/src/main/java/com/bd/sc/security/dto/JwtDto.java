package com.bd.sc.security.dto;

public class JwtDto {
    private String token;
    private String bearer = "Bearer";
    private String username;
    private String name;
    private Long id;

   /* public JwtDto(String token, String bearer, String username, Long id) {
        this.token = token;
        this.bearer = bearer;
        this.username = username;
        this.id=id;
    }*/

    public JwtDto(String token, String name, String username, Long id) {
        this.token = token;
        this.username = username;
        this.name = name;
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBearer() {
        return bearer;
    }

    public void setBearer(String bearer) {
        this.bearer = bearer;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
