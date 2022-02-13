package com.wallet.HDwallet.common;

import org.web3j.crypto.Bip32ECKeyPair;

public class WalletConstant {

    public static final int KEY_SIZE = 128;
    public static final String NODE_API = "https://api.myetherapi.com/eth";

    //Derivation path wanted: // m/44'/60'/0'/0
    public static final int[] DERIVATION_PATH = {44 | Bip32ECKeyPair.HARDENED_BIT, 60 | Bip32ECKeyPair.HARDENED_BIT, 0 | Bip32ECKeyPair.HARDENED_BIT, 0,0};

    enum WalletType {
        bitcoin,
        ethereum;
    }


}
