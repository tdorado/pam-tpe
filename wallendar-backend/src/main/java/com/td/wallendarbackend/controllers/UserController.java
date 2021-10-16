package com.td.wallendarbackend.controllers;

import com.td.wallendarbackend.dtos.requests.ApplicationUserRequest;
import com.td.wallendarbackend.dtos.responses.ApplicationUserResponse;
import com.td.wallendarbackend.models.Group;
import com.td.wallendarbackend.services.ApplicationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final ApplicationUserService applicationUserService;

    @Autowired
    public UserController(ApplicationUserService applicationUserService) {
        this.applicationUserService = applicationUserService;
    }

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<?> signUp(@RequestBody ApplicationUserRequest applicationUserRequest) {
        if(applicationUserService.createUser(applicationUserRequest)){
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        ApplicationUserResponse applicationUserResponse = applicationUserService.findById(id);
        if(applicationUserResponse != null){
            return new ResponseEntity<>(applicationUserResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}