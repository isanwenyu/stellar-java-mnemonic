package com.soneso.stellarmnemonics;

import com.soneso.stellarmnemonics.derivation.Ed25519Derivation;
import com.soneso.stellarmnemonics.mnemonic.Mnemonic;
import com.soneso.stellarmnemonics.mnemonic.Strength;
import com.soneso.stellarmnemonics.mnemonic.WordList;
import org.stellar.sdk.KeyPair;

import javax.annotation.Nullable;

/**
 * Generates Mnemonic with corresponding Stellar Keypair.
 * Created by cristi.paval on 3/14/18.
 */

public class Wallet {

    public static char[] generate12WordMnemonic() throws WalletException {
        return Mnemonic.create(Strength.NORMAL, WordList.ENGLISH);
    }

    public static char[] generate24WordMnemonic() throws WalletException {
        return Mnemonic.create(Strength.HIGH, WordList.ENGLISH);
    }

    public static KeyPair createKeyPair(char[] mnemonic, @Nullable char[] passphrase, int index) throws WalletException {
        byte[] bip39Seed = Mnemonic.createSeed(mnemonic, passphrase);

        Ed25519Derivation masterPrivateKey = Ed25519Derivation.fromSecretSeed(bip39Seed);
        Ed25519Derivation purpose = masterPrivateKey.derived(44);
        Ed25519Derivation coinType = purpose.derived(148);
        Ed25519Derivation account = coinType.derived(index);
        return KeyPair.fromSecretSeed(account.getPrivateKey());
    }
}
