package com.wallet.HDwallet.service;

import org.web3j.crypto.RawTransaction;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

public interface IAccountService {
    BigDecimal getAccountBalance(String walletPassword, String walletPath) throws ExecutionException, InterruptedException;
    BigDecimal getAccountBalance(String address) throws ExecutionException, InterruptedException;
    BigInteger getTransactionNonce(String address) throws IOException;
    String sendTransaction(RawTransaction trx);

    }
