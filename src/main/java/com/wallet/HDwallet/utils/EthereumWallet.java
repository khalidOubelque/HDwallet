package com.wallet.HDwallet.utils;

import com.wallet.HDwallet.common.WalletConstant;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Bip32ECKeyPair;
import org.web3j.crypto.CipherException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

@Getter
public class EthereumWallet extends Wallet{

    private static final Logger LOGGER = LoggerFactory.getLogger(EthereumWallet.class);
    private String walletDirectory;
    public EthereumWallet(String passphrase) {
        super(passphrase);
        this.walletDirectory = "/home/oubelque/wallet/";
    }

    public String createWallet(String passphrase) throws NoSuchAlgorithmException, NoSuchProviderException, CipherException, IOException {
        //Generate entropy
        byte[] bytes= createEntropy(WalletConstant.KEY_SIZE);
        //Generate mnemonics
        String mnemonics = generateMnemonicsPhrase(bytes);
        LOGGER.info("mnemonic phrase is ",mnemonics);

        //Generate seed
        byte[] seed = generateSeed(mnemonics,passphrase);

        /**********Generate master keys ************/
        Bip32ECKeyPair masterKeys = generateKeyPairs(seed);

        // Base 10
        System.out.println("Master Private keys is : "+masterKeys.getPrivateKey().toString());
        LOGGER.info("Master Private keys is : ",masterKeys.getPrivateKey().toString(10));

        //Base 16 - HEX
        System.out.println("Master Private keys is : "+masterKeys.getPrivateKey().toString(16));
        LOGGER.info("Master Public keys is : ",masterKeys.getPublicKey().toString(16));

        /**********Generate derived keys ************/
        Bip32ECKeyPair derivedKeys = generateDerivedKeyPairs(masterKeys, WalletConstant.DERIVATION_PATH);

        /********** create wallet file from existing master keys and wallet password ************/
        String walletName = generateWalletFile(passphrase, masterKeys, walletDirectory,true);
        LOGGER.info("Wallet file name is : "+walletName);

        return walletName;
    }
}
