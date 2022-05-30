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
import com.paymennt.bitcoinj.data.SegwitBitcoinTransaction;
import com.paymennt.bitcoinj.data.SegwitBitcoinTransactionInput;
import com.paymennt.bitcoinj.lib.VarInt;
import com.paymennt.crypto.lib.LittleEndian;


/**
 * @author paymennt
 * 
 */
public class SegwitBitcoinTransactionMapper extends AbstractBitcoinTransactionMapper<SegwitBitcoinTransaction> {

    /**  */
    public static final SegwitBitcoinTransactionMapper INSTANCE = new SegwitBitcoinTransactionMapper();

    /**  */
    protected static final String SEGWIT_MARKER = "00";

    /**  */
    private static final String SEGWIT_FLAG = "01";
    
    /**  */
    private static final String LEGACY_WITNESS = "00";

    /**
     * 
     */

    private SegwitBitcoinTransactionMapper() {
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
        stream.readNBytes(4);
        byte[] marker = stream.readNBytes(1);
        stream.reset();
        return Hex.toHexString(marker).equals(SegwitBitcoinTransactionMapper.SEGWIT_MARKER);
    }

    /**
     * 
     *
     * @param stream 
     * @return 
     * @throws IOException 
     */
    @Override
    public SegwitBitcoinTransaction parse(ByteArrayInputStream stream) throws IOException {
        BigInteger version = LittleEndian.toUnsignedLittleEndian(stream.readNBytes(4));
        String markerAndFlag = Hex.toHexString(stream.readNBytes(2));
        if (!markerAndFlag.equals(SEGWIT_MARKER.concat(SEGWIT_FLAG))) {
            throw new RuntimeException("Malformed segwit transaction.");
        }
        BigInteger numInputs = VarInt.fromByteStream(stream);
        ArrayList<BitcoinTransactionInput> inputs = new ArrayList<>();
        for (BigInteger i = BigInteger.ZERO; i.compareTo(numInputs) < 0; i = i.add(BigInteger.ONE)) {
            inputs.add(BitcoinTransactionInput.fromByteStream(true, stream));
        }
        BigInteger numOutputs = VarInt.fromByteStream(stream);
        ArrayList<BitcoinTransactionOutput> outputs = new ArrayList<>();
        for (BigInteger i = BigInteger.ZERO; i.compareTo(numOutputs) < 0; i = i.add(BigInteger.ONE)) {
            outputs.add(BitcoinTransactionOutput.fromByteStream(stream));
        }
        for (BitcoinTransactionInput input : inputs) {
            if (input.isSegwitInput()) {
                ((SegwitBitcoinTransactionInput) input).setWitnessFromByteStream(stream);
            } else {
                VarInt.fromByteStream(stream); // to read the empty varint
            }
        }
        BigInteger locktime = LittleEndian.toUnsignedLittleEndian(stream.readNBytes(4));
        return new SegwitBitcoinTransaction(version, inputs, outputs, locktime);
    }

    /**
     * 
     *
     * @param transaction 
     * @return 
     * @throws IOException 
     */
    @Override
    public String serialize(SegwitBitcoinTransaction transaction) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(LittleEndian.fromUnsignedLittleEndianToHex(transaction.getVersion(), 4));
        stringBuilder.append(SEGWIT_MARKER.concat(SEGWIT_FLAG));
        stringBuilder.append(VarInt.toHex(BigInteger.valueOf(transaction.getInputs().size())));
        for (BitcoinTransactionInput input : transaction.getInputs()) {
            stringBuilder.append(input.serialize());
        }
        stringBuilder.append(VarInt.toHex(BigInteger.valueOf(transaction.getOutputs().size())));
        for (BitcoinTransactionOutput output : transaction.getOutputs()) {
            stringBuilder.append(output.serialize());
        }
        for (BitcoinTransactionInput input : transaction.getInputs()) {
            if (input.isSegwitInput()) {
                stringBuilder.append(((SegwitBitcoinTransactionInput) input).serializeWitness());
            } else {
                stringBuilder.append(LEGACY_WITNESS);
            }
        }
        stringBuilder.append(LittleEndian.fromUnsignedLittleEndianToHex(transaction.getLocktime(), 4));
        return stringBuilder.toString();
    }
}
