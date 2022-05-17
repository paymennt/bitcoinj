package com.paymennt.bitcoinj.data;

import static java.math.BigInteger.valueOf;
import static java.util.Objects.isNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.paymennt.bitcoinj.lib.OpCodes;
import com.paymennt.crypto.lib.Bytes;
import com.paymennt.crypto.lib.LittleEndian;

public abstract class BitcoinTransactionInput {

    protected static final String HASH_160_PUBKEY_SIZE_HEX = "14";

    private final String previousTransactionId;

    private final BigInteger previousIndex;

    private Script scriptSig;

    private final BigInteger sequence;

    /*******************************************************************************************************************
     * STATIC METHODS
     */

    public static BitcoinTransactionInput fromByteStream(boolean segwitTransaction, ByteArrayInputStream stream)
            throws IOException {
        String previousTransactionId = Bytes.reverseToHex(stream.readNBytes(32));
        BigInteger previousIndex = LittleEndian.toUnsignedLittleEndian(stream.readNBytes(4));
        Script scriptSig = Script.fromByteStream(stream);
        BigInteger sequence = LittleEndian.toUnsignedLittleEndian(stream.readNBytes(4));

        if (segwitTransaction) {
            return new SegwitBitcoinTransactionInput(previousTransactionId, previousIndex, scriptSig, sequence);
        } else {
            return new LegacyBitcoinTransactionInput(previousTransactionId, previousIndex, scriptSig, sequence);
        }
    }

    /*******************************************************************************************************************
     * ABSTRACT CONSTRUCTOR
     */

    public BitcoinTransactionInput(String previousTransactionId, BigInteger previousIndex, Script scriptSig,
            BigInteger sequence) {
        this.previousTransactionId = previousTransactionId;
        this.previousIndex = previousIndex;
        this.scriptSig = scriptSig;
        this.sequence = sequence;
    }

    /*******************************************************************************************************************
     * ABSTRACT METHODS
     */

    public abstract boolean isSegwitInput();

    public abstract void setSignature(List<Object> commands);

    public abstract String getSigHash(BitcoinTransaction transaction, byte[] compressedPublicKey, BigInteger amount)
            throws IOException;

    /*******************************************************************************************************************
     * CLASS METHODS
     */

    /**
     * @return
     * @throws IOException
     */
    public String serialize() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Bytes.reverseFromHex(previousTransactionId));
        stringBuilder.append(LittleEndian.fromUnsignedLittleEndianToHex(previousIndex, 4));
        stringBuilder.append(scriptSig.serialize());
        stringBuilder.append(LittleEndian.fromUnsignedLittleEndianToHex(sequence, 4));
        return stringBuilder.toString();
    }

    public String getPreviousTransactionId() {
        return previousTransactionId;
    }

    public BigInteger getPreviousIndex() {
        return previousIndex;
    }

    public Script getScriptSig() {
        return scriptSig;
    }

    public BigInteger getSequence() {
        return sequence;
    }

    public void setScriptSig(Script scriptSig) {
        this.scriptSig = scriptSig;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BitcoinTransactionInput))
            return false;
        BitcoinTransactionInput other = (BitcoinTransactionInput) obj;
        return this.getPreviousTransactionId().equals(other.getPreviousTransactionId())
                && this.getPreviousIndex().equals(other.getPreviousIndex());
    }

    public void appendToP2SHScriptSig(Object command) {
        if (isNull(scriptSig) || scriptSig.getCommands().size() == 0) {
            scriptSig = new Script(new ArrayList<>());
            scriptSig.appendCommand(valueOf(OpCodes.OP_0));
        }
        scriptSig.appendCommand(command);
    }

}
