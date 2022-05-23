/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.bitcoinj.sign;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import org.bouncycastle.util.encoders.Hex;

import com.paymennt.bitcoinj.data.BitcoinTransaction;
import com.paymennt.bitcoinj.data.BitcoinTransactionInput;
import com.paymennt.crypto.bip32.wallet.key.HdPrivateKey;
import com.paymennt.crypto.bip32.wallet.key.HdPublicKey;

// TODO: Auto-generated Javadoc
/**
 * The Class TransactionSigner.
 *
 * @author payemnnt
 */
public class TransactionSigner {
    
    /**
     * Sign.
     *
     * @param transaction the transaction
     * @param privateKey the private key
     * @param publicKey the public key
     * @param inputIndex the input index
     * @param amount the amount
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void sign(
        BitcoinTransaction transaction,
        HdPrivateKey privateKey,
        HdPublicKey publicKey,
        int inputIndex,
        BigInteger amount
    ) throws IOException {
        String sigHash = getSigHash(transaction, privateKey, publicKey, inputIndex, amount);
        String derSignature = ECSigner.sign(privateKey, Hex.decode(sigHash)).derHex();
        String signature = derSignature.concat(Hex.toHexString(SigHashTypes.SIGHASH_ALL.toByteArray()));
        transaction.getInput(inputIndex).setSignature(
            List.of(
                signature,
                Hex.toHexString(publicKey.getPublicKey())
            )
        );
    }
    
    /**
     * Gets the sig hash.
     *
     * @param transaction the transaction
     * @param privateKey the private key
     * @param publicKey the public key
     * @param inputIndex the input index
     * @param amount the amount
     * @return the sig hash
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private static String getSigHash(
        BitcoinTransaction transaction,
        HdPrivateKey privateKey,
        HdPublicKey publicKey,
        int inputIndex,
        BigInteger amount
    ) throws IOException {
        BitcoinTransactionInput input = transaction.getInput(inputIndex);
        return input.getSigHash(
            transaction,
            publicKey.getPublicKey(),
            amount
        );
    }
}
