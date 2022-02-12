package com.td.wallendarbackend.controllers;


import com.td.wallendarbackend.dtos.responses.DebtDetailResponse;
import com.td.wallendarbackend.services.DebtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/debts")
public class DebtController {

    private final DebtService debtService;

    public DebtController(final DebtService debtService) {
        this.debtService = debtService;
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<?> getDetails(@PathVariable long id) {
        return new ResponseEntity<>(debtService.getDetailsForDebt(id).stream().map(DebtDetailResponse::new), HttpStatus.OK);
    }
}
