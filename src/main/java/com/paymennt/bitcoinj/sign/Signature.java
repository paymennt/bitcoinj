/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.bitcoinj.sign;

import org.bouncycastle.util.encoders.Hex;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;

// TODO: Auto-generated Javadoc
/**
 * The Class Signature.
 *
 * @author payemnnt
 */
public class Signature {
    
    /** The r. */
    private final BigInteger r;
    
    /** The s. */
    private final BigInteger s;

    /**
     * Instantiates a new signature.
     *
     * @param r the r
     * @param s the s
     */
    public Signature(BigInteger r, BigInteger s) {
        this.r = r;
        this.s = s;
    }

    /**
     * Gets the s.
     *
     * @return the s
     */
    public BigInteger getS() {
        return s;
    }

    /**
     * Gets the r.
     *
     * @return the r
     */
    public BigInteger getR() {
        return r;
    }

    /**
     * Der.
     *
     * @return the byte[]
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public byte[] der() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] rBytes = r.toByteArray();
        if (rBytes[0] == Hex.decode("80")[0]) {
            byteArrayOutputStream.write(0);
        }
        byteArrayOutputStream.writeBytes(new byte[]{2, ((byte) r.toByteArray().length)});
        byteArrayOutputStream.writeBytes(rBytes);

        byte[] sBytes = s.toByteArray();
        if (sBytes[0] == Hex.decode("80")[0]) {
            byteArrayOutputStream.write(0);
        }
        byteArrayOutputStream.writeBytes(new byte[]{2, ((byte) s.toByteArray().length)});
        byteArrayOutputStream.writeBytes(sBytes);

        ByteArrayOutputStream result = new ByteArrayOutputStream();
        result.writeBytes(new byte[]{Hex.decode("30")[0], ((byte) byteArrayOutputStream.size())});
        byteArrayOutputStream.writeTo(result);
        return result.toByteArray();
    }

    /**
     * Der hex.
     *
     * @return the string
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public String derHex() throws IOException {
        return Hex.toHexString(der());
    }

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "src.Signature{" +
            "r=" + r +
            ", s=" + s +
            '}';
    }

}
