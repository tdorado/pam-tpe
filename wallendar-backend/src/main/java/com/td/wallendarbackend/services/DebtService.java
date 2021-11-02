package com.td.wallendarbackend.services;

import com.td.wallendarbackend.dtos.responses.DebtResponse;
import com.td.wallendarbackend.models.ApplicationUser;
import com.td.wallendarbackend.models.Debt;
import com.td.wallendarbackend.repositories.ApplicationUserRepository;
import com.td.wallendarbackend.repositories.DebtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DebtService {
    private final ApplicationUserRepository applicationUserRepository;
    private final DebtRepository debtRepository;

    @Autowired
    public DebtService(ApplicationUserRepository applicationUserRepository, DebtRepository debtRepository) {
        this.applicationUserRepository = applicationUserRepository;
        this.debtRepository = debtRepository;
    }

    public List<DebtResponse> findAllFromApplicationUserId(final long applicationUserId){
        ApplicationUser applicationUser = applicationUserRepository.findById(applicationUserId);
        if(applicationUser == null){
            return null;
        }

        List<Debt> debts = debtRepository.findAllFromApplicationUser(applicationUser);
        return debts.stream().map(DebtResponse::new).collect(Collectors.toList());
    }
}
