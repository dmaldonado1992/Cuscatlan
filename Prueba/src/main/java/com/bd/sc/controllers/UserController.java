package com.bd.sc.controllers;

import com.bd.sc.dto.*;
import com.bd.sc.models.entity.User;
import com.bd.sc.security.dto.JwtDto;
import com.bd.sc.security.jwt.JwtProvider;
import com.bd.sc.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.bd.sc.security.*;

import javax.validation.Valid;
import java.util.List;



@RestController
@RequestMapping("/api")
@CrossOrigin
public class UserController {
    @Autowired
    private IUserService service;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private MainSecutiry mainSecutiry=new MainSecutiry();

    @GetMapping("/user")
    public ResponseEntity<CustomResponse<List<User>>> getAll(){
        return new ResponseEntity(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    @ResponseBody
    public ResponseEntity<CustomResponse<List<User>>> getByID(@PathVariable("id") int id){
        return new ResponseEntity(service.getById(id), HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<JwtDto> login(@RequestHeader("Authorization") String token,@Valid @RequestBody User userDetails, BindingResult bindingResult){

        try{
            if(bindingResult.hasErrors()){
                return new ResponseEntity(new Message("invalid data"), HttpStatus.BAD_REQUEST);
            }
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = bCryptPasswordEncoder.encode(userDetails.getPassword());
            userDetails.setPassword(encodedPassword);
            userDetails = service.save(userDetails);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(userDetails, HttpStatus.OK);
    }

    @DeleteMapping("/user")
    public ResponseEntity<JwtDto> delete(@RequestHeader("Authorization") String token,@Valid @RequestBody User userDetails, BindingResult bindingResult){

        try{
            if(bindingResult.hasErrors()){
                return new ResponseEntity(new Message("invalid data"), HttpStatus.BAD_REQUEST);
            }
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = bCryptPasswordEncoder.encode(userDetails.getPassword());
            userDetails.setPassword(encodedPassword);
            userDetails = service.save(userDetails);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(userDetails, HttpStatus.OK);
    }
}
