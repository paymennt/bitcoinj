package com.paymennt.bitcoinj.sign;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import org.bouncycastle.util.encoders.Hex;

import com.paymennt.bitcoinj.data.BitcoinTransaction;
import com.paymennt.bitcoinj.data.BitcoinTransactionInput;
import com.paymennt.crypto.bip32.wallet.key.HdPrivateKey;
import com.paymennt.crypto.bip32.wallet.key.HdPublicKey;

public class TransactionSigner {
    
    /**
     * @param transaction
     * @param privateKey
     * @param inputIndex
     * @param amount
     * @param isSegwitInput
     * @throws IOException
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
     * @param transaction
     * @param privateKey
     * @param index
     * @param amount
     * @param isSegwitInput
     * @return
     * @throws IOException
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
