/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */

package com.paymennt.bitcoinj.data.mapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import com.paymennt.bitcoinj.data.BitcoinTransaction;

// TODO: Auto-generated Javadoc
/**
 * The Interface BitcoinTransactionMapper.
 *
 * @author bashar
 * @param <T> the generic type
 */
public interface BitcoinTransactionMapper<T extends BitcoinTransaction> {

    /**
     * Checks if is supported.
     *
     * @param stream the stream
     * @return true, if is supported
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public boolean isSupported(ByteArrayInputStream stream) throws IOException;

    /**
     * Parses the.
     *
     * @param stream the stream
     * @return the t
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public T parse(ByteArrayInputStream stream) throws IOException;

    /**
     * Gets the transaction id.
     *
     * @param transaction the transaction
     * @return the transaction id
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public String getTransactionId(T transaction) throws IOException;

    /**
     * Serialize.
     *
     * @param transaction the transaction
     * @return the string
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public String serialize(T transaction) throws IOException;

}
