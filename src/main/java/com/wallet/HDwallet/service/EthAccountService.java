package com.wallet.HDwallet.service;

import com.wallet.HDwallet.controller.IAccountController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

@RestController
public class EthAccountService implements IAccountService{
    @Autowired
    IAccountController accountController;

    @GetMapping(value = "/account/{address}")
    public void getAccountBalance(@PathVariable("address") String address) {
        try {
            BigDecimal balance = accountController.getAccountBalance(address);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
