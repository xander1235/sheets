package com.triffy.sheet.controllers;


import com.triffy.sheet.model.User;
import com.triffy.sheet.services.base.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.security.GeneralSecurityException;


@RestController
@Validated
@RequestMapping(value = "/v1")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping(value = "/sheet")
    public ResponseEntity add(@RequestBody @Valid User user) throws GeneralSecurityException, IOException {
        service.addUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
