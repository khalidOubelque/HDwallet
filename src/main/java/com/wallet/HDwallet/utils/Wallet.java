package com.wallet.HDwallet.utils;

import com.wallet.HDwallet.common.WalletConstant;
import lombok.Data;
import org.web3j.crypto.Bip32ECKeyPair;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


@Data
public class Wallet implements IWallet {

    private String passphrase;
    private Set<String> supportedCoinType;

    public Wallet(String passphrase) {
        this.passphrase = passphrase;
        this.supportedCoinType = new HashSet<>(Arrays.asList("bitcoin", "ethereum"));
    }

    /**
     * Generate Derived Key Pairs from Master keys
     *
     * @param masterKey
     * @param path
     * @return
     */
    public Bip32ECKeyPair generateDerivedKeyPairs(Bip32ECKeyPair masterKey, int[] path) {
        return Bip32ECKeyPair.deriveKeyPair(masterKey, path);
    }
}
