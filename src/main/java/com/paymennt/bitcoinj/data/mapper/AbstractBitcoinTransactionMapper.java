/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.bitcoinj.data.mapper;

import java.io.IOException;
import java.math.BigInteger;

import org.bouncycastle.util.encoders.Hex;

import com.paymennt.bitcoinj.data.BitcoinTransaction;
import com.paymennt.bitcoinj.data.BitcoinTransactionInput;
import com.paymennt.bitcoinj.data.BitcoinTransactionOutput;
import com.paymennt.bitcoinj.lib.VarInt;
import com.paymennt.crypto.lib.Hash256;
import com.paymennt.crypto.lib.LittleEndian;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractBitcoinTransactionMapper.
 *
 * @author asendar
 * @param <T> the generic type
 */
public abstract class AbstractBitcoinTransactionMapper<T extends BitcoinTransaction>
        implements BitcoinTransactionMapper<T> {

    /**
     * Gets the transaction id.
     *
     * @param transaction the transaction
     * @return the transaction id
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public String getTransactionId(T transaction) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(LittleEndian.fromUnsignedLittleEndianToHex(transaction.getVersion(), 4));
        stringBuilder.append(VarInt.toHex(BigInteger.valueOf(transaction.getInputs().size())));
        for (BitcoinTransactionInput input : transaction.getInputs()) {
            stringBuilder.append(input.serialize());
        }
        stringBuilder.append(VarInt.toHex(BigInteger.valueOf(transaction.getOutputs().size())));
        for (BitcoinTransactionOutput output : transaction.getOutputs()) {
            stringBuilder.append(output.serialize());
        }
        stringBuilder.append(LittleEndian.fromUnsignedLittleEndianToHex(transaction.getLocktime(), 4));

        return LittleEndian.fromUnsignedLittleEndianToHex( //
                new BigInteger(1, Hash256.hash(Hex.decode(stringBuilder.toString()))), 32);
    }

}
