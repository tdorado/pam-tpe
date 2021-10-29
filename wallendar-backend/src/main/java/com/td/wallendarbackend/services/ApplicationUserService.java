package com.td.wallendarbackend.services;

import com.td.wallendarbackend.dtos.requests.ApplicationUserRequest;
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

    public ApplicationUserResponse findById(long id) {
        ApplicationUser applicationUser = applicationUserRepository.findById(id);
        if (applicationUser == null) {
            return null;
        }
        return new ApplicationUserResponse(applicationUser.getId(), applicationUser.getEmail(),
                applicationUser.getFirstName(), applicationUser.getLastName());
    }

    public ApplicationUserResponse createUser(ApplicationUserRequest applicationUserRequest) {
        if (applicationUserRepository.findByEmail(applicationUserRequest.getEmail()) != null) {
            return null;
        }
        ApplicationUser applicationUser = new ApplicationUser(applicationUserRequest.getEmail(),
                bCryptPasswordEncoder.encode(applicationUserRequest.getPassword()),
                applicationUserRequest.getFirstName(),
                applicationUserRequest.getLastName());
        applicationUserRepository.save(applicationUser);
        return new ApplicationUserResponse(applicationUser.getId(), applicationUser.getEmail(),
                applicationUser.getFirstName(), applicationUser.getLastName());
    }
}
