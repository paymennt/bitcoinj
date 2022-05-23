/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.bitcoinj.data;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import com.paymennt.bitcoinj.data.mapper.LegacyBitcoinTransactionMapper;

// TODO: Auto-generated Javadoc
/**
 * The Class LegacyBitcoinTransaction.
 *
 * @author payemnnt
 */
public class LegacyBitcoinTransaction extends BitcoinTransaction {
    
    /**
     * *****************************************************************************************************************
     * CONSTRUCTOR.
     *
     * @param version the version
     * @param inputs the inputs
     * @param outputs the outputs
     * @param locktime the locktime
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
     * *****************************************************************************************************************
     * ABSTRACT METHOD IMPLEMENTATION.
     *
     * @return the string
     * @throws IOException Signals that an I/O exception has occurred.
     */

    /**
     * {@inheritDoc}
     */
    @Override
    public String serialize() throws IOException {
        return LegacyBitcoinTransactionMapper.INSTANCE.serialize(this);
    }

    /**
     * Gets the transaction id.
     *
     * @return the transaction id
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public String getTransactionId() throws IOException {
        return LegacyBitcoinTransactionMapper.INSTANCE.getTransactionId(this);
    }

    /*******************************************************************************************************************
     * INTERNAL PRIVATE METHODS
     */
    
}
