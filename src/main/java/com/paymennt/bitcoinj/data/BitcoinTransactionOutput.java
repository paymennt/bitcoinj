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


/**
 * @author paymennt
 * 
 */
public class BitcoinTransactionOutput {
    
    /**  */
    private final BigInteger amount;

    /**  */
    private final Script scriptPubkey;

    /**
     * 
     *
     * @param amount 
     * @param scriptPubkey 
     */
    public BitcoinTransactionOutput(BigInteger amount, Script scriptPubkey) {
        this.amount = amount;
        this.scriptPubkey = scriptPubkey;
    }

    /**
     * 
     *
     * @param stream 
     * @return 
     * @throws IOException 
     */
    public static BitcoinTransactionOutput fromByteStream(ByteArrayInputStream stream) throws IOException {
        BigInteger amount = LittleEndian.toUnsignedLittleEndian(stream.readNBytes(8));
        Script scriptPubkey = Script.fromByteStream(stream);
        return new BitcoinTransactionOutput(amount, scriptPubkey);
    }

    /**
     * 
     *
     * @return 
     * @throws IOException 
     */
    public String serialize() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(LittleEndian.fromUnsignedLittleEndianToHex(amount, 8));
        stringBuilder.append(scriptPubkey.serialize());
        return stringBuilder.toString();
    }

    /**
     * 
     *
     * @return 
     */
    public BigInteger getAmount() {
        return amount;
    }

    /**
     * 
     *
     * @return 
     */
    public Script getScriptPubkey() {
        return scriptPubkey;
    }

    /**
     * 
     *
     * @param network 
     * @param format 
     * @return 
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
