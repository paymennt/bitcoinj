/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */

package com.paymennt.bitcoinj.data.mapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import com.paymennt.bitcoinj.data.BitcoinTransaction;

/**
 * @author bashar
 *
 */
public interface BitcoinTransactionMapper<T extends BitcoinTransaction> {

    public boolean isSupported(ByteArrayInputStream stream) throws IOException;

    public T parse(ByteArrayInputStream stream) throws IOException;

    public String getTransactionId(T transaction) throws IOException;

    public String serialize(T transaction) throws IOException;

}
