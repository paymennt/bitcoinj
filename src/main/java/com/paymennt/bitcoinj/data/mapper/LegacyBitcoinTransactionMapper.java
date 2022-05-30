/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */

package com.paymennt.bitcoinj.data.mapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

import org.bouncycastle.util.encoders.Hex;

import com.paymennt.bitcoinj.data.BitcoinTransactionInput;
import com.paymennt.bitcoinj.data.BitcoinTransactionOutput;
import com.paymennt.bitcoinj.data.LegacyBitcoinTransaction;
import com.paymennt.bitcoinj.lib.VarInt;
import com.paymennt.crypto.lib.LittleEndian;


/**
 * @author paymennt
 * 
 */
public class LegacyBitcoinTransactionMapper extends AbstractBitcoinTransactionMapper<LegacyBitcoinTransaction> {

    /**  */
    public static final LegacyBitcoinTransactionMapper INSTANCE = new LegacyBitcoinTransactionMapper();

    /**
     * 
     */

    private LegacyBitcoinTransactionMapper() {
    }

    /**
     * 
     *
     * @param stream 
     * @return 
     * @throws IOException 
     */

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSupported(ByteArrayInputStream stream) throws IOException {
        // Basically not a segwit transaction
        stream.readNBytes(4);
        byte[] marker = stream.readNBytes(1);
        stream.reset();
        return !Hex.toHexString(marker).equals(SegwitBitcoinTransactionMapper.SEGWIT_MARKER);
    }

    /**
     * 
     *
     * @param stream 
     * @return 
     * @throws IOException 
     */
    @Override
    public LegacyBitcoinTransaction parse(ByteArrayInputStream stream) throws IOException {
        BigInteger version = LittleEndian.toUnsignedLittleEndian(stream.readNBytes(4));
        BigInteger numInputs = VarInt.fromByteStream(stream);
        ArrayList<BitcoinTransactionInput> inputs = new ArrayList<>();
        for (BigInteger i = BigInteger.ZERO; i.compareTo(numInputs) < 0; i = i.add(BigInteger.ONE)) {
            inputs.add(BitcoinTransactionInput.fromByteStream(false, stream));
        }
        BigInteger numOutputs = VarInt.fromByteStream(stream);
        ArrayList<BitcoinTransactionOutput> outputs = new ArrayList<>();
        for (BigInteger i = BigInteger.ZERO; i.compareTo(numOutputs) < 0; i = i.add(BigInteger.ONE)) {
            outputs.add(BitcoinTransactionOutput.fromByteStream(stream));
        }
        BigInteger locktime = LittleEndian.toUnsignedLittleEndian(stream.readNBytes(4));
        return new LegacyBitcoinTransaction(version, inputs, outputs, locktime);
    }

    /**
     * 
     *
     * @param transaction 
     * @return 
     * @throws IOException 
     */
    @Override
    public String serialize(LegacyBitcoinTransaction transaction) throws IOException {
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
        return stringBuilder.toString();
    }

}
