package com.wallet.HDwallet.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

public interface IAccountController {
    BigDecimal getAccountBalance(String address) throws ExecutionException, InterruptedException;
    BigInteger getTransactionNonce(String address) throws IOException;
    }
