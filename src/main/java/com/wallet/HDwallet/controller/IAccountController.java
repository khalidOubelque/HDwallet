package com.wallet.HDwallet.controller;

import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.WalletFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

public interface IAccountController {
    BigDecimal getAccountBalance(String walletPassword, String walletPath) throws ExecutionException, InterruptedException;
    BigDecimal getAccountBalance(String address) throws ExecutionException, InterruptedException;
    BigInteger getTransactionNonce(String address) throws IOException;
    String sendTransaction(RawTransaction trx);

    }
