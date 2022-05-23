/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.bitcoinj.data;

import static java.math.BigInteger.valueOf;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.paymennt.bitcoinj.lib.OpCodes;
import com.paymennt.bitcoinj.sign.SigHashTypes;
import com.paymennt.bitcoinj.sign.Witness;
import com.paymennt.crypto.lib.Bytes;
import com.paymennt.crypto.lib.Hash160;
import com.paymennt.crypto.lib.Hash256;
import com.paymennt.crypto.lib.LittleEndian;

// TODO: Auto-generated Javadoc
/**
 * The Class SegwitBitcoinTransactionInput.
 *
 * @author payemnnt
 */
public class SegwitBitcoinTransactionInput extends BitcoinTransactionInput {
    
    /** The witness. */
    private Witness witness = new Witness(new ArrayList<>());
    
    /**
     * *****************************************************************************************************************
     * CONSTRUCTOR.
     *
     * @param previousTransactionId the previous transaction id
     * @param previousIndex the previous index
     * @param scriptSig the script sig
     * @param sequence the sequence
     */
    
    /**
     * @param previousTransactionId
     * @param previousIndex
     * @param scriptSig
     * @param sequence
     */
    public SegwitBitcoinTransactionInput(
        String previousTransactionId,
        BigInteger previousIndex,
        Script scriptSig,
        BigInteger sequence
    ) {
        super(previousTransactionId, previousIndex, scriptSig, sequence);
    }
    
    /**
     * *****************************************************************************************************************
     * ABSTRACT IMPLEMENTATION METHODS.
     *
     * @return true, if is segwit input
     */
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSegwitInput() {
        return true;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setSignature(List<Object> commands) {
        Witness witness = new Witness(commands);
        this.setWitness(witness);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getSigHash(
        BitcoinTransaction transaction,
        byte[] compressedPublicKey,
        BigInteger amount
    ) throws IOException {
        String hash160Pubkey = Hash160.hashToHex(compressedPublicKey);
        return this.getSigHashInternal(
            transaction,
            Script.p2pkhScript(HASH_160_PUBKEY_SIZE_HEX.concat(hash160Pubkey)).serializeForSegwitSigHash(),
            amount
        );
    }
    
    /**
     * *****************************************************************************************************************
     * CLASS METHODS.
     *
     * @return the witness
     */
    
    public Witness getWitness() {
        return witness;
    }
    
    /**
     * Sets the witness.
     *
     * @param witness the new witness
     */
    public void setWitness(Witness witness) {
        this.witness = witness;
    }
    
    /**
     * {@inheritDoc}
     */
    public String serializeWitness() {
        return witness.serialize();
    }
    
    /**
     * {@inheritDoc}
     */
    public void setWitnessFromByteStream(ByteArrayInputStream stream) throws IOException {
        witness = Witness.fromByteStream(stream);
    }
    
    /**
     * Append to witness.
     *
     * @param command the command
     */
    public void appendToWitness(Object command) {
        if (witness.getItems().size() == 0) {
            witness.appendItem(valueOf(OpCodes.OP_0));
        }
        witness.appendItem(command);
    }
    
    /**
     * Gets the sig hash internal.
     *
     * @param transaction the transaction
     * @param serializedScriptPubkey the serialized script pubkey
     * @param amount the amount
     * @return the sig hash internal
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private String getSigHashInternal(
        BitcoinTransaction transaction,
        String serializedScriptPubkey,
        BigInteger amount
    ) throws IOException {
        // PrevOuts & Sequences
        String hashPrevOuts;
        String hashSequence;
        {
            StringBuilder allPrevOuts = new StringBuilder();
            StringBuilder allSequences = new StringBuilder();
            transaction.getInputs().forEach(input -> {
                allPrevOuts.append(Bytes.reverseFromHex(input.getPreviousTransactionId()));
                allPrevOuts.append(LittleEndian.fromUnsignedLittleEndianToHex(input.getPreviousIndex(), 4));
                allSequences.append(LittleEndian.fromUnsignedLittleEndianToHex(input.getSequence(), 4));
            });
            hashPrevOuts = Hash256.hashToHex(allPrevOuts.toString());
            hashSequence = Hash256.hashToHex(allSequences.toString());
        }
        
        // hash outputs
        String hashOutputs;
        {
            StringBuilder allOutputs = new StringBuilder();
            for (BitcoinTransactionOutput output : transaction.getOutputs()) {
                allOutputs.append(output.serialize());
            }
            hashOutputs = Hash256.hashToHex(allOutputs.toString());
        }
        
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(LittleEndian.fromUnsignedLittleEndianToHex(transaction.getVersion(), 4));
        
        stringBuilder.append(hashPrevOuts);
        stringBuilder.append(hashSequence);
        stringBuilder.append(Bytes.reverseFromHex(this.getPreviousTransactionId()));
        stringBuilder.append(LittleEndian.fromUnsignedLittleEndianToHex(this.getPreviousIndex(), 4));
        stringBuilder.append(serializedScriptPubkey);
        stringBuilder.append(LittleEndian.fromUnsignedLittleEndianToHex(amount, 8));
        stringBuilder.append(LittleEndian.fromUnsignedLittleEndianToHex(this.getSequence(), 4));
        
        stringBuilder.append(hashOutputs);
        stringBuilder.append(LittleEndian.fromUnsignedLittleEndianToHex(transaction.getLocktime(), 4));
        stringBuilder.append(LittleEndian.fromUnsignedLittleEndianToHex(SigHashTypes.SIGHASH_ALL, 4));
        String zRaw = stringBuilder.toString();
        return Hash256.hashToHex(zRaw);
    }
}
