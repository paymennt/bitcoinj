package com.paymennt.bitcoinj.data;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.paymennt.bitcoinj.lib.VarInt;
import com.paymennt.bitcoinj.sign.SigHashTypes;
import com.paymennt.crypto.lib.Hash160;
import com.paymennt.crypto.lib.Hash256;
import com.paymennt.crypto.lib.LittleEndian;

public class LegacyBitcoinTransactionInput extends BitcoinTransactionInput {

    /*******************************************************************************************************************
     * CONSTRUCTOR
     */

    public LegacyBitcoinTransactionInput(String previousTransactionId, BigInteger previousIndex, Script scriptSig,
            BigInteger sequence) {
        super(previousTransactionId, previousIndex, scriptSig, sequence);
    }

    /*******************************************************************************************************************
     * ABSTRACT IMPLEMENTATION METHODS
     */

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSegwitInput() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSignature(List<Object> commands) {
        Script script = new Script(commands);
        this.setScriptSig(script);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSigHash(BitcoinTransaction transaction, byte[] compressedPublicKey, BigInteger amount // not used in legacy transaction
    ) throws IOException {
        String hash160Pubkey = Hash160.hashToHex(compressedPublicKey);
        return this.getSigHashInternal(transaction, Script.p2pkhScript(hash160Pubkey));
    }

    /*******************************************************************************************************************
     * CLASS METHODS
     */

    /**
     * @param transaction
     * @param serializedScriptPubkey
     * @param amount
     * @return
     * @throws IOException
     */
    private String getSigHashInternal(BitcoinTransaction transaction, Script scriptPubkey) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(LittleEndian.fromUnsignedLittleEndianToHex(transaction.getVersion(), 4));

        // DO INPUTS
        stringBuilder.append(VarInt.toHex(BigInteger.valueOf(transaction.getInputs().size())));
        for (BitcoinTransactionInput input : transaction.getInputs()) {
            stringBuilder.append(
                    new LegacyBitcoinTransactionInput(input.getPreviousTransactionId(), input.getPreviousIndex(),
                            this.equals(input) ? scriptPubkey : new Script(new ArrayList<>()), input.getSequence())
                                    .serialize());
        }

        // DO OUTPUTS
        stringBuilder.append(VarInt.toHex(BigInteger.valueOf(transaction.getOutputs().size())));
        for (BitcoinTransactionOutput output : transaction.getOutputs()) {
            stringBuilder.append(output.serialize());
        }

        // Finalize
        stringBuilder.append(LittleEndian.fromUnsignedLittleEndianToHex(transaction.getLocktime(), 4));
        stringBuilder.append(LittleEndian.fromUnsignedLittleEndianToHex(SigHashTypes.SIGHASH_ALL, 4));
        String zRaw = stringBuilder.toString();
        return Hash256.hashToHex(zRaw);
    }
}
