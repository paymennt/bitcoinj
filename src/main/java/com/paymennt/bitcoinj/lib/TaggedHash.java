package com.paymennt.bitcoinj.lib;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

import org.bouncycastle.util.Arrays;

import com.paymennt.crypto.lib.Sha256;

public class TaggedHash {
    public static byte[] hash(String tag, byte[] key) {
        byte[] shaTag = Sha256.hash(tag.getBytes(StandardCharsets.UTF_8));
        byte[] shaTags = Arrays.concatenate(shaTag, shaTag);
        return Sha256.hash(Arrays.concatenate(shaTags, key));
    }

    public static BigInteger hashToBigInteger(String tag, byte[] key) {
        return new BigInteger(1, hash(tag, key));
    }
}
