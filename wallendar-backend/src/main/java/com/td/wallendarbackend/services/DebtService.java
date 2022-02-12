package com.td.wallendarbackend.services;

import com.td.wallendarbackend.models.ApplicationUser;
import com.td.wallendarbackend.models.Debt;
import com.td.wallendarbackend.models.DebtDetail;
import com.td.wallendarbackend.repositories.ApplicationUserRepository;
import com.td.wallendarbackend.repositories.DebtDetailRepository;
import com.td.wallendarbackend.repositories.DebtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DebtService {
    private final ApplicationUserRepository applicationUserRepository;
    private final DebtRepository debtRepository;
    private final DebtDetailRepository debtDetailRepository;

    @Autowired
    public DebtService(ApplicationUserRepository applicationUserRepository, DebtRepository debtRepository,
                       DebtDetailRepository debtDetailRepository) {
        this.applicationUserRepository = applicationUserRepository;
        this.debtRepository = debtRepository;
        this.debtDetailRepository = debtDetailRepository;
    }

    public List<Debt> findAllFromApplicationUserId(final long applicationUserId){
        ApplicationUser applicationUser = applicationUserRepository.findById(applicationUserId);
        if(applicationUser == null){
            return null;
        }

        return debtRepository.findAllFromApplicationUser(applicationUser);
    }

    public Set<DebtDetail> getDetailsForDebt(final long debtId) {
        return new HashSet<>(debtDetailRepository.findAllByDebtId(debtId));
    }
}
