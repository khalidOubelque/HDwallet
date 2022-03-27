package com.wallet.HDwallet;

import com.wallet.HDwallet.service.IAccountService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class AccountTest {
    @Autowired
    IAccountService accountService;

    @Test
    public void checkBalanceEthAccount(){
        try {
            String address = "0xF0f15Cedc719B5A55470877B0710d5c7816916b1";
            BigDecimal balance = accountService.getAccountBalance(address);
            Assertions.assertThat(balance).isNotNull();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
