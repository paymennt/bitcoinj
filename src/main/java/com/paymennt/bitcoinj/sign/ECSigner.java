/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.bitcoinj.sign;

import java.math.BigInteger;

import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.signers.ECDSASigner;
import org.bouncycastle.crypto.signers.HMacDSAKCalculator;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.math.ec.custom.sec.SecP256K1Curve;
import org.bouncycastle.util.encoders.Hex;

import com.paymennt.crypto.bip32.wallet.key.HdPrivateKey;

// TODO: Auto-generated Javadoc
/**
 * The Class ECSigner.
 *
 * @author payemnnt
 */
public class ECSigner {

    /** The Constant order. */
    private static final BigInteger order =
            new BigInteger(1, Hex.decodeStrict("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEBAAEDCE6AF48A03BBFD25E8CD0364141"));
    
    /** The Constant curve. */
    private static final SecP256K1Curve curve = new SecP256K1Curve();
    
    /** The Constant G. */
    private static final ECPoint G = curve.createPoint(
            new BigInteger(1, Hex.decodeStrict("79be667ef9dcbbac55a06295ce870b07029bfcdb2dce28d959f2815b16f81798")),
            new BigInteger(1, Hex.decodeStrict("483ada7726a3c4655da4fbfc0e1108a8fd17b448a68554199c47d08ffb10d4b8")));
    
    /** The Constant ecDomainParameters. */
    private static final ECDomainParameters ecDomainParameters =
            new ECDomainParameters(ECSigner.curve, ECSigner.G, ECSigner.order);

    /**
     * Sign.
     *
     * @param privateKey the private key
     * @param message the message
     * @return the signature
     */
    public static Signature sign(HdPrivateKey privateKey, byte[] message) {

        ECDSASigner ecdsaSigner = new ECDSASigner(new HMacDSAKCalculator(new SHA256Digest()));
        ecdsaSigner.init(true,
                new ECPrivateKeyParameters(new BigInteger(1, privateKey.getPrivateKey()), ecDomainParameters));
        BigInteger[] signature = ecdsaSigner.generateSignature(message);
        if (signature[1].compareTo(order.divide(BigInteger.TWO)) > 0) {
            signature[1] = order.subtract(signature[1]);
        }
        return new Signature(signature[0], signature[1]);
    }
}
