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

// TODO: Auto-generated Javadoc
/**
 * The Class BitcoinTransactionInput.
 *
 * @author payemnnt
 */
public abstract class BitcoinTransactionInput {

    /** The Constant HASH_160_PUBKEY_SIZE_HEX. */
    protected static final String HASH_160_PUBKEY_SIZE_HEX = "14";

    /** The previous transaction id. */
    private final String previousTransactionId;

    /** The previous index. */
    private final BigInteger previousIndex;

    /** The script sig. */
    private Script scriptSig;

    /** The sequence. */
    private final BigInteger sequence;

    /**
     * *****************************************************************************************************************
     * STATIC METHODS.
     *
     * @param segwitTransaction the segwit transaction
     * @param stream the stream
     * @return the bitcoin transaction input
     * @throws IOException Signals that an I/O exception has occurred.
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
     * *****************************************************************************************************************
     * ABSTRACT CONSTRUCTOR.
     *
     * @param previousTransactionId the previous transaction id
     * @param previousIndex the previous index
     * @param scriptSig the script sig
     * @param sequence the sequence
     */

    public BitcoinTransactionInput(String previousTransactionId, BigInteger previousIndex, Script scriptSig,
            BigInteger sequence) {
        this.previousTransactionId = previousTransactionId;
        this.previousIndex = previousIndex;
        this.scriptSig = scriptSig;
        this.sequence = sequence;
    }

    /**
     * *****************************************************************************************************************
     * ABSTRACT METHODS.
     *
     * @return true, if is segwit input
     */

    public abstract boolean isSegwitInput();

    /**
     * Sets the signature.
     *
     * @param commands the new signature
     */
    public abstract void setSignature(List<Object> commands);

    /**
     * Gets the sig hash.
     *
     * @param transaction the transaction
     * @param compressedPublicKey the compressed public key
     * @param amount the amount
     * @return the sig hash
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public abstract String getSigHash(BitcoinTransaction transaction, byte[] compressedPublicKey, BigInteger amount)
            throws IOException;

    /**
     * *****************************************************************************************************************
     * CLASS METHODS.
     *
     * @return the string
     * @throws IOException Signals that an I/O exception has occurred.
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
     * Gets the previous transaction id.
     *
     * @return the previous transaction id
     */
    public String getPreviousTransactionId() {
        return previousTransactionId;
    }

    /**
     * Gets the previous index.
     *
     * @return the previous index
     */
    public BigInteger getPreviousIndex() {
        return previousIndex;
    }

    /**
     * Gets the script sig.
     *
     * @return the script sig
     */
    public Script getScriptSig() {
        return scriptSig;
    }

    /**
     * Gets the sequence.
     *
     * @return the sequence
     */
    public BigInteger getSequence() {
        return sequence;
    }

    /**
     * Sets the script sig.
     *
     * @param scriptSig the new script sig
     */
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

    /**
     * Append to P 2 SH script sig.
     *
     * @param command the command
     */
    public void appendToP2SHScriptSig(Object command) {
        if (isNull(scriptSig) || scriptSig.getCommands().size() == 0) {
            scriptSig = new Script(new ArrayList<>());
            scriptSig.appendCommand(valueOf(OpCodes.OP_0));
        }
        scriptSig.appendCommand(command);
    }

}
