package com.td.wallendarbackend.services;

import com.td.wallendarbackend.dtos.requests.AddApplicationUserRequest;
import com.td.wallendarbackend.dtos.responses.ApplicationUserResponse;
import com.td.wallendarbackend.models.ApplicationUser;
import com.td.wallendarbackend.repositories.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserService {
    private final ApplicationUserRepository applicationUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public ApplicationUserService(ApplicationUserRepository applicationUserRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.applicationUserRepository = applicationUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public ApplicationUserResponse findByEmail(String email) {
        ApplicationUser applicationUser = applicationUserRepository.findByEmail(email);
        if (applicationUser == null) {
            return null;
        }
        return new ApplicationUserResponse(applicationUser.getId(), applicationUser.getEmail(),
                applicationUser.getFirstName(), applicationUser.getLastName(), applicationUser.getPhoneNumber());
    }

    public ApplicationUserResponse findById(long id) {
        ApplicationUser applicationUser = applicationUserRepository.findById(id);
        if (applicationUser == null) {
            return null;
        }
        return new ApplicationUserResponse(applicationUser.getId(), applicationUser.getEmail(),
                applicationUser.getFirstName(), applicationUser.getLastName(), applicationUser.getPhoneNumber());
    }

    public ApplicationUserResponse createUser(AddApplicationUserRequest addApplicationUserRequest) {
        if (applicationUserRepository.findByEmail(addApplicationUserRequest.getEmail()) != null) {
            return null;
        }
        ApplicationUser applicationUser = new ApplicationUser(addApplicationUserRequest.getEmail(),
                bCryptPasswordEncoder.encode(addApplicationUserRequest.getPassword()),
                addApplicationUserRequest.getFirstName(),
                addApplicationUserRequest.getLastName(), addApplicationUserRequest.getPhoneNumber());
        applicationUserRepository.save(applicationUser);
        return new ApplicationUserResponse(applicationUser.getId(), applicationUser.getEmail(),
                applicationUser.getFirstName(), applicationUser.getLastName(), applicationUser.getPhoneNumber());
    }
}
