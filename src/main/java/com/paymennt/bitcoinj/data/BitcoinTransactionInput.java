/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
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

/**
 * @author paymennt
 * 
 */
public abstract class BitcoinTransactionInput {

    /**  */
    protected static final String HASH_160_PUBKEY_SIZE_HEX = "14";

    /**  */
    private final String previousTransactionId;

    /**  */
    private final BigInteger previousIndex;

    /**  */
    private Script scriptSig;

    /**  */
    private final BigInteger sequence;

    /**
     * 
     *
     * @param segwitTransaction 
     * @param stream 
     * @return 
     * @throws IOException 
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

    /**
     * 
     *
     * @param previousTransactionId 
     * @param previousIndex 
     * @param scriptSig 
     * @param sequence 
     */

    public BitcoinTransactionInput(String previousTransactionId, BigInteger previousIndex, Script scriptSig,
            BigInteger sequence) {
        this.previousTransactionId = previousTransactionId;
        this.previousIndex = previousIndex;
        this.scriptSig = scriptSig;
        this.sequence = sequence;
    }

    /**
     * 
     *
     * @return 
     */

    public abstract boolean isSegwitInput();

    /**
     * 
     *
     * @param commands 
     */
    public abstract void setSignature(List<Object> commands);

    /**
     * 
     *
     * @param transaction 
     * @param compressedPublicKey 
     * @param amount 
     * @return 
     * @throws IOException 
     */
    public abstract String getSigHash(BitcoinTransaction transaction, byte[] compressedPublicKey, BigInteger amount)
            throws IOException;

    /**
     * 
     *
     * @return 
     * @throws IOException 
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

    /**
     * 
     *
     * @return 
     */
    public String getPreviousTransactionId() {
        return previousTransactionId;
    }

    /**
     * 
     *
     * @return 
     */
    public BigInteger getPreviousIndex() {
        return previousIndex;
    }

    /**
     * 
     *
     * @return 
     */
    public Script getScriptSig() {
        return scriptSig;
    }

    /**
     * 
     *
     * @return 
     */
    public BigInteger getSequence() {
        return sequence;
    }

    /**
     * 
     *
     * @param scriptSig 
     */
    public void setScriptSig(Script scriptSig) {
        this.scriptSig = scriptSig;
    }

    /**
     * 
     *
     * @param obj 
     * @return 
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BitcoinTransactionInput))
            return false;
        BitcoinTransactionInput other = (BitcoinTransactionInput) obj;
        return this.getPreviousTransactionId().equals(other.getPreviousTransactionId())
                && this.getPreviousIndex().equals(other.getPreviousIndex());
    }

    /**
     * 
     *
     * @param command 
     */
    public void appendToP2SHScriptSig(Object command) {
        if (isNull(scriptSig) || scriptSig.getCommands().size() == 0) {
            scriptSig = new Script(new ArrayList<>());
            scriptSig.appendCommand(valueOf(OpCodes.OP_0));
        }
        scriptSig.appendCommand(command);
    }

}
