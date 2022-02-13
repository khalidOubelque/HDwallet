package com.wallet.HDwallet;

import com.wallet.HDwallet.controller.IAccountController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

@SpringBootTest
public class AccountTest {
    @Autowired
    IAccountController accountController;

    @Test
    public void checkBalanceEthAccount(){
        try {
            String address = "0xF0f15Cedc719B5A55470877B0710d5c7816916b1";
            BigDecimal balance = accountController.getAccountBalance(address);
            Assertions.assertThat(balance).isNotNull();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
