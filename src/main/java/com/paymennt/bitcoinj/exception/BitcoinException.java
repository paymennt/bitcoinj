/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.bitcoinj.exception;

// TODO: Auto-generated Javadoc
/**
 * The Class BitcoinException.
 *
 * @author asendar
 */
public class BitcoinException extends RuntimeException {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 326864452189929895L;

    /**
     * Instantiates a new bitcoin exception.
     *
     * @param message the message
     */
    public BitcoinException(String message) {
        super(message);
    }

    /**
     * Instantiates a new bitcoin exception.
     *
     * @param cause the cause
     */
    public BitcoinException(Throwable cause) {
        super(cause.getMessage(), cause);
    }

    /**
     * Instantiates a new bitcoin exception.
     *
     * @param message the message
     * @param args the args
     */
    public BitcoinException(String message, Object... args) {
        super(String.format(message, args));
    }

    /**
     * Instantiates a new bitcoin exception.
     *
     * @param message the message
     * @param cause the cause
     * @param args the args
     */
    public BitcoinException(String message, Throwable cause, Object... args) {
        super(String.format(message, args), cause);
    }
}