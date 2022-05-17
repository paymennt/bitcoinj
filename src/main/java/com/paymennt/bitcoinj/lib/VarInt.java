package com.paymennt.bitcoinj.lib;

import static java.math.BigInteger.valueOf;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;

import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.BigIntegers;
import org.bouncycastle.util.encoders.Hex;

import com.paymennt.crypto.lib.LittleEndian;

public class VarInt {

    public static BigInteger fromByteStream(ByteArrayInputStream bytes) throws IOException {
        BigInteger firstByte = new BigInteger(1, bytes.readNBytes(1));
        if (firstByte.equals(valueOf(253))) {
            return LittleEndian.toUnsignedLittleEndian(bytes.readNBytes(2));
        } else if (firstByte.equals(valueOf(254))) {
            return LittleEndian.toUnsignedLittleEndian(bytes.readNBytes(4));
        } else if (firstByte.equals(valueOf(255))) {
            return LittleEndian.toUnsignedLittleEndian(bytes.readNBytes(8));
        }
        return firstByte;
    }

    public static ByteArrayInputStream toByteStream(BigInteger bigInteger) {
        if (bigInteger.equals(BigInteger.ZERO)) {
            return new ByteArrayInputStream(new byte[] { 0 });
        } else if (bigInteger.compareTo(new BigInteger(1, Hex.decodeStrict("fd"))) < 0) {
            return new ByteArrayInputStream(BigIntegers.asUnsignedByteArray(bigInteger));
        } else if (bigInteger.compareTo(new BigInteger(1, Hex.decodeStrict("010000"))) < 0) {
            return new ByteArrayInputStream(
                    Arrays.concatenate(Hex.decodeStrict("fd"), LittleEndian.fromUnsignedLittleEndian(bigInteger, 2)));
        } else if (bigInteger.compareTo(new BigInteger(1, Hex.decodeStrict("0100000000"))) < 0) {
            return new ByteArrayInputStream(
                    Arrays.concatenate(Hex.decodeStrict("fe"), LittleEndian.fromUnsignedLittleEndian(bigInteger, 4)));
        } else if (bigInteger.compareTo(new BigInteger(1, Hex.decodeStrict("010000000000000000"))) < 0) {
            return new ByteArrayInputStream(
                    Arrays.concatenate(Hex.decodeStrict("ff"), LittleEndian.fromUnsignedLittleEndian(bigInteger, 8)));
        }
        throw new IllegalArgumentException("Number too large: ".concat(bigInteger.toString()));
    }

    public static String toHex(BigInteger bigInteger) {
        return Hex.toHexString(toByteStream(bigInteger).readAllBytes());
    }
}
