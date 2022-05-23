/**
 * 
 */
package com.paymennt.bitcoinj.exception;

/**
 * @author asendar
 *
 */
public class BitcoinException extends RuntimeException {
    private static final long serialVersionUID = 326864452189929895L;

    public BitcoinException(String message) {
        super(message);
    }

    public BitcoinException(Throwable cause) {
        super(cause.getMessage(), cause);
    }

    public BitcoinException(String message, Object... args) {
        super(String.format(message, args));
    }

    public BitcoinException(String message, Throwable cause, Object... args) {
        super(String.format(message, args), cause);
    }
}