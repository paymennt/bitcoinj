/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.bitcoinj.data;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import com.paymennt.bitcoinj.data.mapper.SegwitBitcoinTransactionMapper;


/**
 * @author paymennt
 * 
 */
public class SegwitBitcoinTransaction extends BitcoinTransaction {

    /**
     * 
     *
     * @param version 
     * @param inputs 
     * @param outputs 
     * @param locktime 
     */

    public SegwitBitcoinTransaction(BigInteger version, List<BitcoinTransactionInput> inputs,
            List<BitcoinTransactionOutput> outputs, BigInteger locktime) {
        super(version, inputs, outputs, locktime);
    }

    /**
     * 
     *
     * @param version 
     * @param inputs 
     * @param outputs 
     * @param locktime 
     * @param doubleSpend 
     * @param confirmations 
     */
    public SegwitBitcoinTransaction(BigInteger version, List<BitcoinTransactionInput> inputs,
            List<BitcoinTransactionOutput> outputs, BigInteger locktime, boolean doubleSpend, int confirmations) {
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
        return SegwitBitcoinTransactionMapper.INSTANCE.serialize(this);
    }

    /**
     * 
     *
     * @return 
     * @throws IOException 
     */
    @Override
    public String getTransactionId() throws IOException {
        return SegwitBitcoinTransactionMapper.INSTANCE.getTransactionId(this);
    }

    /*******************************************************************************************************************
     * INTERNAL PRIVATE METHODS
     */

}
