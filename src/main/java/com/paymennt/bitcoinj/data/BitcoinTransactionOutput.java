/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.bitcoinj.data;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;

import com.paymennt.crypto.bip32.Network;
import com.paymennt.crypto.lib.LittleEndian;

// TODO: Auto-generated Javadoc
/**
 * The Class BitcoinTransactionOutput.
 *
 * @author payemnnt
 */
public class BitcoinTransactionOutput {
    
    /** The amount. */
    private final BigInteger amount;

    /** The script pubkey. */
    private final Script scriptPubkey;

    /**
     * Instantiates a new bitcoin transaction output.
     *
     * @param amount the amount
     * @param scriptPubkey the script pubkey
     */
    public BitcoinTransactionOutput(BigInteger amount, Script scriptPubkey) {
        this.amount = amount;
        this.scriptPubkey = scriptPubkey;
    }

    /**
     * From byte stream.
     *
     * @param stream the stream
     * @return the bitcoin transaction output
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static BitcoinTransactionOutput fromByteStream(ByteArrayInputStream stream) throws IOException {
        BigInteger amount = LittleEndian.toUnsignedLittleEndian(stream.readNBytes(8));
        Script scriptPubkey = Script.fromByteStream(stream);
        return new BitcoinTransactionOutput(amount, scriptPubkey);
    }

    /**
     * Serialize.
     *
     * @return the string
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public String serialize() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(LittleEndian.fromUnsignedLittleEndianToHex(amount, 8));
        stringBuilder.append(scriptPubkey.serialize());
        return stringBuilder.toString();
    }

    /**
     * Gets the amount.
     *
     * @return the amount
     */
    public BigInteger getAmount() {
        return amount;
    }

    /**
     * Gets the script pubkey.
     *
     * @return the script pubkey
     */
    public Script getScriptPubkey() {
        return scriptPubkey;
    }

    /**
     * Gets the address.
     *
     * @param network the network
     * @param format the format
     * @return the address
     */
    public String getAddress(Network network, AddressFormat format) {
        String prefix = format.getPrefix(network);
        if (format.equals(AddressFormat.P2PKH))
            return this.getScriptPubkey().p2pkhAddress(prefix);

        if (format.equals(AddressFormat.P2SH))
            return this.getScriptPubkey().p2shAddress(prefix);

        if (format.equals(AddressFormat.P2WPKH))
            return this.getScriptPubkey().p2wpkhAddress(prefix);
        return null;
    }

}
