package com.wallet.HDwallet.utils;

import org.web3j.crypto.Bip32ECKeyPair;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.MnemonicUtils;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Arrays;

public interface IWallet {

    /**
     * Method that Generate Entropy given key size in bits
     *
     * @param keySize
     * @return Entropy
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     */
    default byte[] createEntropy(int keySize) throws NoSuchAlgorithmException, NoSuchProviderException {
        SecureRandom secureRandomGenerator = SecureRandom.getInstance("SHA1PRNG", "SUN");
        byte[] randomBytes = new byte[keySize / 8];
        secureRandomGenerator.nextBytes(randomBytes);
        return randomBytes;
    }

    /**
     * Method that Generate Mnemonics Phrase given Entropy
     *
     * @param initialEntropy
     * @return Mnemonics Phrase
     * @throws Exception
     */
    default String generateMnemonicsPhrase(byte[] initialEntropy) throws Exception {
        String mnemonic = MnemonicUtils.generateMnemonic(initialEntropy);
        return Arrays.asList(mnemonic.split(" ")).toString();
    }

    /**
     * Method that Generate Seed given Mnemonics Phrase and passphrase
     *
     * @param mnemonics
     * @param passphrase
     * @return seed
     */
    default byte[] generateSeed(String mnemonics, String passphrase) {
        return MnemonicUtils.generateSeed(mnemonics, passphrase);
    }

    /**
     * Generate Master Extended Keys given Seed
     * @param seed
     * @return Master keyPairs
     */
    default Bip32ECKeyPair generateKeyPairs(byte[] seed) {
        return Bip32ECKeyPair.generateKeyPair(seed);
    }

    /**
     * Generate DerivedKeyPairs given master keys
     * @param masterKey
     * @param path
     * @return Master keyPairs
     */
    Bip32ECKeyPair generateDerivedKeyPairs(Bip32ECKeyPair masterKey, int[] path);

    /**
     * Load Existing wallet From a Mnemonic and passphrase
     * @param passphrase
     * @param mnemonic
     * @return Credentials
     */
    Credentials loadExistingWallet(String passphrase, String mnemonic);


}
