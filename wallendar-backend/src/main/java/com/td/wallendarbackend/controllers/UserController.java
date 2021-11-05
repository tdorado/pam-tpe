package com.td.wallendarbackend.controllers;

import com.td.wallendarbackend.dtos.requests.AddUserAliasRequest;
import com.td.wallendarbackend.dtos.requests.AddApplicationUserRequest;
import com.td.wallendarbackend.dtos.responses.ApplicationUserResponse;
import com.td.wallendarbackend.dtos.responses.DebtResponse;
import com.td.wallendarbackend.dtos.responses.UserAliasResponse;
import com.td.wallendarbackend.services.ApplicationUserService;
import com.td.wallendarbackend.services.DebtService;
import com.td.wallendarbackend.services.UserAliasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/users")
public class UserController {

    private final ApplicationUserService applicationUserService;
    private final UserAliasService userAliasService;
    private final DebtService debtService;

    @Autowired
    public UserController(ApplicationUserService applicationUserService, UserAliasService userAliasService, DebtService debtService) {
        this.applicationUserService = applicationUserService;
        this.userAliasService = userAliasService;
        this.debtService = debtService;
    }

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody @Valid AddApplicationUserRequest addApplicationUserRequest) {
        ApplicationUserResponse applicationUserResponse = applicationUserService.createUser(addApplicationUserRequest);
        if (applicationUserResponse != null) {
            return new ResponseEntity<>(applicationUserResponse, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/findByEmail")
    @ResponseBody
    public ResponseEntity<?> getUserByEmail(@RequestParam String email) {
        ApplicationUserResponse applicationUserResponse = applicationUserService.findByEmail(email);
        if (applicationUserResponse != null) {
            return new ResponseEntity<>(applicationUserResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<?> getUserById(@PathVariable long id) {
        ApplicationUserResponse applicationUserResponse = applicationUserService.findById(id);
        if (applicationUserResponse != null) {
            return new ResponseEntity<>(applicationUserResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/{id}/createAlias")
    @ResponseBody
    public ResponseEntity<?> createAlias(@PathVariable long id,
                                         @RequestBody @Valid AddUserAliasRequest addUserAliasRequest){
        UserAliasResponse userAliasResponse = userAliasService.createUserAlias(id, addUserAliasRequest);
        if(userAliasResponse != null){
            return new ResponseEntity<>(userAliasResponse, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/{id}/getAliases")
    @ResponseBody
    public ResponseEntity<?> getUserAliases(@PathVariable long id){
        List<UserAliasResponse> userAliasResponses = userAliasService.getAllUserAliases(id);
        if(userAliasResponses != null) {
            return new ResponseEntity<>(userAliasResponses, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/{id}/getDebts")
    @ResponseBody
    public ResponseEntity<?> getDebts(@PathVariable long id){
        List<DebtResponse> debtResponses = debtService.findAllFromApplicationUserId(id);
        if(debtResponses != null){
            return new ResponseEntity<>(debtResponses, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}