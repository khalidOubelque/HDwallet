package com.wallet.HDwallet.utils;

import lombok.Data;
import org.web3j.crypto.*;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


@Data
public class Wallet {

    protected String passphrase;
    protected Bip32ECKeyPair masterKeys;

    public Wallet(String passphrase) {
        this.passphrase = passphrase;
    }

    /**
     * Method that Generate Entropy given key size in bits
     *
     * @param keySize
     * @return Entropy
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     */
    public byte[] createEntropy(int keySize) throws NoSuchAlgorithmException, NoSuchProviderException {
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
    public String generateMnemonicsPhrase(byte[] initialEntropy) {
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
    public byte[] generateSeed(String mnemonics, String passphrase) {
        return MnemonicUtils.generateSeed(mnemonics, passphrase);
    }

    /**
     * Generate Master Extended Keys given Seed
     * @param seed
     * @return Master keyPairs
     */
    public Bip32ECKeyPair generateKeyPairs(byte[] seed) {
        return Bip32ECKeyPair.generateKeyPair(seed);
    }

    /**
     * Generate a new wallet from scarth
     * @param walletPassword
     * @param walletDirectory
     * @return Wallet name file
     * @throws InvalidAlgorithmParameterException
     * @throws CipherException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws NoSuchProviderException
     */
    public String generateWalletFile(String walletPassword, String walletDirectory) throws InvalidAlgorithmParameterException, CipherException, NoSuchAlgorithmException, IOException, NoSuchProviderException  {
        String walletName = WalletUtils.generateNewWalletFile(walletPassword, new File(walletDirectory));
        return walletName;
    }

    /**
     * Generate DerivedKeyPairs given master keys
     * @param masterKey
     * @param path
     * @return Master keyPairs
     */
    public Bip32ECKeyPair generateDerivedKeyPairs(Bip32ECKeyPair masterKey, int[] path) {
        return Bip32ECKeyPair.deriveKeyPair(masterKey, path);
    }

    /**
     * Load Existing wallet From a Mnemonic and passphrase
     * @param passphrase
     * @param mnemonic
     * @return Credentials
     */
    public Credentials loadExistingWallet(String passphrase, String mnemonic) {
        return WalletUtils.loadBip39Credentials(passphrase, mnemonic);
    }

    public String generateWalletFile(String password, ECKeyPair ecKeyPair, String walletDirectory, boolean useFullScrypt) throws CipherException, IOException {
        return WalletUtils.generateWalletFile(password, ecKeyPair, new File(walletDirectory), useFullScrypt);
    }
}
