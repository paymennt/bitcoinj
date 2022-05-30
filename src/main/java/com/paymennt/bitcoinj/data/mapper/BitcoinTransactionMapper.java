/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */

package com.paymennt.bitcoinj.data.mapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import com.paymennt.bitcoinj.data.BitcoinTransaction;


/**
 * @author paymennt
 * 
 */
public interface BitcoinTransactionMapper<T extends BitcoinTransaction> {

    /**
     * 
     *
     * @param stream 
     * @return 
     * @throws IOException 
     */
    public boolean isSupported(ByteArrayInputStream stream) throws IOException;

    /**
     * 
     *
     * @param stream 
     * @return 
     * @throws IOException 
     */
    public T parse(ByteArrayInputStream stream) throws IOException;

    /**
     * 
     *
     * @param transaction 
     * @return 
     * @throws IOException 
     */
    public String getTransactionId(T transaction) throws IOException;

    /**
     * 
     *
     * @param transaction 
     * @return 
     * @throws IOException 
     */
    public String serialize(T transaction) throws IOException;

}
