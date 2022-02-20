package com.wallet.HDwallet.service;

import com.wallet.HDwallet.controller.IAccountController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.web3j.crypto.RawTransaction;

import java.math.BigDecimal;

@RestController
@RequestMapping("/accounts")
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

    //create and send trx
    @PostMapping(value="/transaction/send", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void process2(@RequestBody RawTransaction trx) {
        accountController.sendTransaction(trx);
    }
}
