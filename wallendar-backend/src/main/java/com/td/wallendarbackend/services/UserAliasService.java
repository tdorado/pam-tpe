package com.td.wallendarbackend.services;

import com.td.wallendarbackend.dtos.requests.AddUserAliasRequest;
import com.td.wallendarbackend.dtos.responses.UserAliasResponse;
import com.td.wallendarbackend.models.ApplicationUser;
import com.td.wallendarbackend.models.UserAlias;
import com.td.wallendarbackend.repositories.ApplicationUserRepository;
import com.td.wallendarbackend.repositories.UserAliasRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserAliasService {
    private final ApplicationUserRepository applicationUserRepository;
    private final UserAliasRepository userAliasRepository;

    public UserAliasService(ApplicationUserRepository applicationUserRepository, UserAliasRepository userAliasRepository) {
        this.applicationUserRepository = applicationUserRepository;
        this.userAliasRepository = userAliasRepository;
    }

    public UserAliasResponse createUserAlias(long applicationUserId, AddUserAliasRequest addUserAliasRequest){
        ApplicationUser applicationUser = applicationUserRepository.findById(applicationUserId);
        if(applicationUser == null){
            return null;
        }

        UserAlias userAlias = new UserAlias(applicationUser, addUserAliasRequest.getAlias());
        userAliasRepository.save(userAlias);

        return new UserAliasResponse(userAlias);
    }

    public List<UserAliasResponse> getAllUserAliases(long applicationUserId){
        ApplicationUser applicationUser = applicationUserRepository.findById(applicationUserId);
        if(applicationUser == null){
            return null;
        }

        List<UserAlias> userAliases = userAliasRepository.findAllByApplicationUser(applicationUser);

        return userAliases.stream().map(UserAliasResponse::new).collect(Collectors.toList());
    }
}
