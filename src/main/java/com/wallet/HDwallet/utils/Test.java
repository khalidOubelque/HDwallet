package com.wallet.HDwallet.utils;

import com.wallet.HDwallet.common.WalletConstant;
import org.web3j.crypto.Bip32ECKeyPair;

import java.util.Arrays;

public class Test {

    public static void main(String[] args) {
        String passphrase = "toto";
        Wallet w = new Wallet(passphrase);
        try {
            byte[] bytes= w.createEntropy(WalletConstant.KEY_SIZE);
            String mnemonics = w.generateMnemonicsPhrase(bytes);
            System.out.println(mnemonics);

            byte[] seed = w.generateSeed(mnemonics,passphrase);
            System.out.println(Arrays.toString(seed));

            /**********Generate master keys ************/
            Bip32ECKeyPair masterKeys = w.generateKeyPairs(seed);
            // Base 10
            System.out.println("Master Private keys is : "+masterKeys.getPrivateKey().toString());
            System.out.println("Master Private keys is : "+masterKeys.getPrivateKey().toString(10));

            //Base 16 - HEX
            System.out.println("Master Private keys is : "+masterKeys.getPrivateKey().toString(16));
            System.out.println("Master Public keys is : "+masterKeys.getPublicKey().toString(16));

            /**********Generate derived keys ************/
            Bip32ECKeyPair derivedKeys = w.generateDerivedKeyPairs(masterKeys, WalletConstant.DERIVATION_PATH);

            //Base 16 - HEX
            System.out.println("Master Private keys is : "+derivedKeys.getPrivateKey().toString(16));
            System.out.println("Master Public keys is : "+derivedKeys.getPublicKey().toString(16));









        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
