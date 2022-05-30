/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.bitcoinj.sign;

import org.bouncycastle.util.encoders.Hex;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;


/**
 * @author paymennt
 * 
 */
public class Signature {
    
    /**  */
    private final BigInteger r;
    
    /**  */
    private final BigInteger s;

    /**
     * 
     *
     * @param r 
     * @param s 
     */
    public Signature(BigInteger r, BigInteger s) {
        this.r = r;
        this.s = s;
    }

    /**
     * 
     *
     * @return 
     */
    public BigInteger getS() {
        return s;
    }

    /**
     * 
     *
     * @return 
     */
    public BigInteger getR() {
        return r;
    }

    /**
     * 
     *
     * @return 
     * @throws IOException 
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
     * 
     *
     * @return 
     * @throws IOException 
     */
    public String derHex() throws IOException {
        return Hex.toHexString(der());
    }

    /**
     * 
     *
     * @return 
     */
    @Override
    public String toString() {
        return "src.Signature{" +
            "r=" + r +
            ", s=" + s +
            '}';
    }

}
