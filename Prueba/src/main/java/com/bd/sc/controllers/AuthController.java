package com.bd.sc.controllers;

import com.bd.sc.dto.CustomResponse;
import com.bd.sc.dto.Message;
import com.bd.sc.models.entity.AuthUser;
import com.bd.sc.models.entity.Role;
import com.bd.sc.models.entity.User;
import com.bd.sc.security.dto.JwtDto;
import com.bd.sc.security.dto.LoginUser;
import com.bd.sc.security.dto.NewUser;
import com.bd.sc.security.jwt.JwtProvider;
import com.bd.sc.services.impl.RoleServiceImpl;
import com.bd.sc.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private RoleServiceImpl roleService;

    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/user")
    public ResponseEntity<?> newUser(@Valid @RequestBody NewUser newUser, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity(new Message("invalid data"), HttpStatus.BAD_REQUEST);
        }
        if(userService.existsUsername(newUser.getUsername())){
            return new ResponseEntity(new Message("existing user"), HttpStatus.BAD_REQUEST);
        }

        User user = new User(newUser.getName(), newUser.getUsername(), newUser.getDescription(), newUser.getPhone(), passwordEncoder.encode(newUser.getPassword()), newUser.getStoreId(), newUser.getCreationUser());

        Set<Role> roles = new HashSet<>();
        for(String role: newUser.getRoles()){
            System.out.println("ID ROLE: " + roleService.getByName(role).get().getId());
            roles.add(roleService.getByName(role).get());
        }

        user.setRoles(roles);
        userService.save(user);
        return new ResponseEntity(new Message("user created"), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUser loginUser, BindingResult bindingResult){
        CustomResponse<List<JwtDto>> response = new CustomResponse<>();
        AuthUser userDetails = null;
        JwtDto jwtDto=null;
        String jwt = "";

        if(bindingResult.hasErrors()){
            return new ResponseEntity(new Message("invalid data"), HttpStatus.BAD_REQUEST);
        }
            try{

                if(userService.existsUsername(loginUser.getUsername())==false){
                    response.setOk(false);
                    response.setMessage("User does not exist.");
                    return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
                }

                Authentication authentication =
                        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));

                SecurityContextHolder.getContext().setAuthentication(authentication);

                jwt = jwtProvider.generateToken(authentication);
                userDetails = (AuthUser) authentication.getPrincipal();
            }
            catch (Exception e){

                response.setOk(false);
                response.setMessage(e.getMessage());
                return new ResponseEntity(response, HttpStatus.OK);
            }

            jwtDto = new JwtDto(jwt, userDetails.getName(), loginUser.getUsername(), userDetails.getId());

        return new ResponseEntity(jwtDto, HttpStatus.OK);
    }
}
