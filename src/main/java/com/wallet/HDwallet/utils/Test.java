package com.wallet.HDwallet.utils;


import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;


public class Test {

    public static void main(String[] args) {
        String passphrase = "toto";
        EthereumWallet ethereumWallet = new EthereumWallet(passphrase);
        try {
            String walletFileName = ethereumWallet.createWallet(passphrase);
            System.out.println("Wallet filename is : "+walletFileName);


            // Decrypt and open the wallet into a Credential object
            StringBuilder source = new StringBuilder();
            source.append(ethereumWallet.getWalletDirectory());
            source.append(walletFileName);
            Credentials credentials = WalletUtils.loadCredentials(passphrase, source.toString());
            System.out.println("Wallet address is : "+credentials.getAddress());

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
