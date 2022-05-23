/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.bitcoinj.sign;

import static java.math.BigInteger.valueOf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.bouncycastle.util.encoders.Hex;

import com.paymennt.bitcoinj.lib.VarInt;
import com.paymennt.crypto.lib.LittleEndian;

// TODO: Auto-generated Javadoc
/**
 * The Class Witness.
 *
 * @author payemnnt
 */
public class Witness {
    
    /** The items. */
    private List<Object> items;
    
    /**
     * *****************************************************************************************************************
     * CONSTRUCTOR.
     *
     * @param items the items
     */
    
    /**
     * @param items
     */
    public Witness(List<Object> items) {
        this.items = items;
    }
    
    /**
     * *****************************************************************************************************************
     * STATIC BUILDERS.
     *
     * @param stream the stream
     * @return the witness
     * @throws IOException Signals that an I/O exception has occurred.
     */
    
    /**
     * @param stream
     * @return
     * @throws IOException
     */
    public static Witness fromByteStream(ByteArrayInputStream stream) throws IOException {
        BigInteger numItems = VarInt.fromByteStream(stream);
        ArrayList<Object> items = new ArrayList<>();
        for (int i = 0; i < numItems.intValueExact(); i++) {
            BigInteger itemLength = VarInt.fromByteStream(stream);
            if (itemLength.equals(BigInteger.ZERO)) {
                items.add(BigInteger.ZERO);
                continue;
            }
            items.add(Hex.toHexString(stream.readNBytes(itemLength.intValueExact())));
        }
        return new Witness(items);
    }
    
    /**
     * *****************************************************************************************************************
     * PUBLIC METHODS.
     *
     * @param item the item
     */
    
    /**
     * @param item
     */
    public void appendItem(Object item) {
        items.add(item);
    }
    
    /**
     * Gets the items.
     *
     * @return the items
     */
    public List<Object> getItems() {
        return items;
    }
    
    /**
     * Serialize.
     *
     * @return the string
     */
    public String serialize() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.writeBytes(LittleEndian.fromUnsignedLittleEndian(valueOf(items.size()), 1));
        items.forEach(item -> {
            if (item instanceof BigInteger) {
                byteArrayOutputStream.writeBytes(LittleEndian.fromUnsignedLittleEndian((BigInteger) item, 1));
                return;
            }
            byteArrayOutputStream.writeBytes(
                VarInt.toByteStream(
                    valueOf(
                        ((String) item).length()
                    ).divide(BigInteger.TWO)
                ).readAllBytes()
            );
            byteArrayOutputStream.writeBytes(Hex.decodeStrict((String) item));
        });
        return Hex.toHexString(byteArrayOutputStream.toByteArray());
    }
    
}
