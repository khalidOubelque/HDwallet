package com.wallet.HDwallet.controller;

import com.wallet.HDwallet.common.WalletConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ChainId;
import org.web3j.tx.ChainIdLong;
import org.web3j.tx.TransactionManager;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

@Service
public class EthAccountController implements IAccountController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EthAccountController.class);

    //    @Autowired
//    Web3j web3j;
    private Web3j web3j = Web3j.build(new HttpService(WalletConstant.NODE_API));

    @Override
    public BigDecimal getAccountBalance(String address) throws ExecutionException, InterruptedException {
        EthGetBalance balanceWei = web3j.ethGetBalance(address,
                        DefaultBlockParameterName.LATEST)
                .sendAsync()
                .get();

        BigDecimal balanceInEther = Convert.fromWei(balanceWei.getBalance().toString(), Convert.Unit.ETHER);
        LOGGER.info("balance in ether: " + balanceInEther);
        return balanceInEther;
    }

    @Override
    public BigInteger getTransactionNonce(String address) throws IOException {
        EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(address, DefaultBlockParameterName.LATEST).send();
        return ethGetTransactionCount.getTransactionCount();
    }

    public void sendTransaction(String fromAddress, String toAddress, BigDecimal amount, String password, WalletFile walletfile) throws Exception {
        TransactionReceipt trx = new TransactionReceipt();
        BigInteger mNonce = getTransactionNonce(fromAddress);
        BigInteger gasPrice = getGasPrice();
        BigInteger value = Convert.toWei(amount, Convert.Unit.ETHER).toBigInteger();
        Transaction transaction = Transaction.createEtherTransaction(fromAddress, null,
                null, null, toAddress, value);
        BigInteger gasLimit = getGasLimit(transaction);
        String sign = signedEthTransactionData(toAddress, mNonce, gasPrice, gasLimit, amount, walletfile, password);
       // sendTransaction(sign);

    }

/*    public static void sendTransaction(String fromAddress, String toAddress, BigDecimal amount,
                                       String password, WalletFile walletfile) throws Exception {
        new SendTransaction().sendTransaction(fromAddress, toAddress, amount, password, walletfile);
    }*/

    private BigInteger getGasPrice() {
        BigInteger gasPrice = BigInteger.ONE;
        try {
            Request<?, EthGasPrice> rs = web3j.ethGasPrice();
            EthGasPrice eGasPrice = rs.sendAsync().get();
            gasPrice = eGasPrice.getGasPrice();
        } catch (Exception e) {
            LOGGER.error("Error Cannot get gas price", e);
        }
        return gasPrice;
    }

    private BigInteger getGasLimit(Transaction transaction) {
        BigInteger gasLimit = BigInteger.ONE;
        try {
            Request<?, EthEstimateGas> rs = web3j.ethEstimateGas(transaction);
            EthEstimateGas eGasLimit = rs.sendAsync().get();
            gasLimit = eGasLimit.getAmountUsed();
        } catch (Exception e) {
            LOGGER.error("Error Cannot get gas limit", e);
        }
        return gasLimit;
    }

// to refactor
    public String signedEthTransactionData(String to, BigInteger nonce, BigInteger gasPrice,
                                           BigInteger gasLimit, BigDecimal amount, WalletFile walletfile,
                                           String password) throws Exception {
        // 1ETH = 10^18 Wei
        BigDecimal amountInWei = Convert.toWei(amount.toString(), Convert.Unit.ETHER);
        RawTransaction rawTransaction =
                RawTransaction.createEtherTransaction(nonce, gasPrice, gasLimit, to,
                        amountInWei.toBigInteger());
        return signData(rawTransaction, walletfile, password);
    }

    public String signData(RawTransaction rawTransaction, WalletFile walletfile, String password)
            throws Exception {
        Credentials credentials = Credentials.create(Wallet.decrypt(password, walletfile));
        byte[] signMessage =
                TransactionEncoder.signMessage(rawTransaction, ChainIdLong.ROPSTEN, credentials);
        return Numeric.toHexString(signMessage);

    }

/*    public String signContractTransaction(String contractAddress,
                                          String to,
                                          BigInteger nonce,
                                          BigInteger gasPrice,
                                          BigInteger gasLimit,
                                          BigDecimal amount,
                                          BigDecimal decimal,
                                          WalletFile walletfile,
                                          String password) throws Exception {
        BigDecimal realValue = amount.multiply(decimal);
        Function function = new Function("transfer",
                Arrays.asList(new Address(to), new Uint256(realValue.toBigInteger())),
                Collections.emptyList());
        String data = FunctionEncoder.encode(function);
        RawTransaction rawTransaction = RawTransaction.createTransaction(
                nonce,
                gasPrice,
                gasLimit,
                contractAddress,
                data);
        return signData(rawTransaction, walletfile, password);
    }*/

}
