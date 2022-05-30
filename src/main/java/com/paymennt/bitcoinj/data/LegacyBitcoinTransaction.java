/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.bitcoinj.data;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import com.paymennt.bitcoinj.data.mapper.LegacyBitcoinTransactionMapper;


/**
 * @author paymennt
 * 
 */
public class LegacyBitcoinTransaction extends BitcoinTransaction {
    
    /**
     * 
     *
     * @param version 
     * @param inputs 
     * @param outputs 
     * @param locktime 
     */
    
    public LegacyBitcoinTransaction(
        BigInteger version,
        List<BitcoinTransactionInput> inputs,
        List<BitcoinTransactionOutput> outputs,
        BigInteger locktime
    ) {
        super(version, inputs, outputs, locktime);
    }
    
    /**
     * 
     *
     * @return 
     * @throws IOException 
     */

    /**
     * {@inheritDoc}
     */
    @Override
    public String serialize() throws IOException {
        return LegacyBitcoinTransactionMapper.INSTANCE.serialize(this);
    }

    /**
     * 
     *
     * @return 
     * @throws IOException 
     */
    @Override
    public String getTransactionId() throws IOException {
        return LegacyBitcoinTransactionMapper.INSTANCE.getTransactionId(this);
    }

    /*******************************************************************************************************************
     * INTERNAL PRIVATE METHODS
     */
    
}
