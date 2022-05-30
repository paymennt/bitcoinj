/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.bitcoinj.exception;


/**
 * @author paymennt
 * 
 */
public class BitcoinException extends RuntimeException {
    
    /**  */
    private static final long serialVersionUID = 326864452189929895L;

    /**
     * 
     *
     * @param message 
     */
    public BitcoinException(String message) {
        super(message);
    }

    /**
     * 
     *
     * @param cause 
     */
    public BitcoinException(Throwable cause) {
        super(cause.getMessage(), cause);
    }

    /**
     * 
     *
     * @param message 
     * @param args 
     */
    public BitcoinException(String message, Object... args) {
        super(String.format(message, args));
    }

    /**
     * 
     *
     * @param message 
     * @param cause 
     * @param args 
     */
    public BitcoinException(String message, Throwable cause, Object... args) {
        super(String.format(message, args), cause);
    }
}