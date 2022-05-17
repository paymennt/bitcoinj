package com.paymennt.bitcoinj.data;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import com.paymennt.bitcoinj.data.mapper.SegwitBitcoinTransactionMapper;

public class SegwitBitcoinTransaction extends BitcoinTransaction {

    /*******************************************************************************************************************
     * CONSTRUCTOR
     */

    public SegwitBitcoinTransaction(BigInteger version, List<BitcoinTransactionInput> inputs,
            List<BitcoinTransactionOutput> outputs, BigInteger locktime) {
        super(version, inputs, outputs, locktime);
    }

    public SegwitBitcoinTransaction(BigInteger version, List<BitcoinTransactionInput> inputs,
            List<BitcoinTransactionOutput> outputs, BigInteger locktime, boolean doubleSpend, int confirmations) {
        super(version, inputs, outputs, locktime);
    }

    /*******************************************************************************************************************
     * ABSTRACT METHOD IMPLEMENTATION
     */

    /**
     * {@inheritDoc}
     */
    @Override
    public String serialize() throws IOException {
        return SegwitBitcoinTransactionMapper.INSTANCE.serialize(this);
    }

    @Override
    public String getTransactionId() throws IOException {
        return SegwitBitcoinTransactionMapper.INSTANCE.getTransactionId(this);
    }

    /*******************************************************************************************************************
     * INTERNAL PRIVATE METHODS
     */

}
