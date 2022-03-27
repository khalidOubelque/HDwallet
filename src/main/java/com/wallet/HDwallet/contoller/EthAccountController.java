package com.wallet.HDwallet.contoller;

import com.wallet.HDwallet.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.web3j.crypto.RawTransaction;

import java.math.BigDecimal;

@RestController
@RequestMapping("api/v1/accounts")
public class EthAccountController implements IAccountContoller{
    @Autowired
    IAccountService accountService;

    @GetMapping(value = "/{address}")
    public void getAccountBalance(@PathVariable("address") String address) {
        try {
            BigDecimal balance = accountService.getAccountBalance(address);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *     create and send trx
     */
    @PostMapping(value="/transaction/send", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> sendTransaction(@RequestBody RawTransaction trx) {
        String trxHash = accountService.sendTransaction(trx);
        return !trxHash.isEmpty() ? new ResponseEntity<>(trxHash, HttpStatus.OK) : new ResponseEntity<>(trxHash, HttpStatus.BAD_REQUEST);
    }
}
