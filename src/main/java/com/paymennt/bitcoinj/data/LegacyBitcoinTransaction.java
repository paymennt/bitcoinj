package com.paymennt.bitcoinj.data;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import com.paymennt.bitcoinj.data.mapper.LegacyBitcoinTransactionMapper;

public class LegacyBitcoinTransaction extends BitcoinTransaction {
    
    /*******************************************************************************************************************
     * CONSTRUCTOR
     */
    
    public LegacyBitcoinTransaction(
        BigInteger version,
        List<BitcoinTransactionInput> inputs,
        List<BitcoinTransactionOutput> outputs,
        BigInteger locktime
    ) {
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
        return LegacyBitcoinTransactionMapper.INSTANCE.serialize(this);
    }

    @Override
    public String getTransactionId() throws IOException {
        return LegacyBitcoinTransactionMapper.INSTANCE.getTransactionId(this);
    }

    /*******************************************************************************************************************
     * INTERNAL PRIVATE METHODS
     */
    
}
