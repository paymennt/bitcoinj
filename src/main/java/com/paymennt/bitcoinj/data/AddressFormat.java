/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.bitcoinj.data;

import com.paymennt.crypto.bip32.Network;
import com.paymennt.crypto.bip32.wallet.AbstractWallet.Purpose;

// TODO: Auto-generated Javadoc
/**
 * The Enum AddressFormat.
 *
 * @author asendar
 */
public enum AddressFormat {
    
    /** The p2pkh. */
    P2PKH(Purpose.BIP44, 148, 34, 10),
    
    /** The p2sh. */
    P2SH(Purpose.BIP49, 297, 32, 10),
    
    /** The p2wpkh. */
    P2WPKH(Purpose.BIP84, 68, 31, 10.5);

    /** The purpose. */
    private final Purpose purpose;

    /**
     * reference
     * https://bitcoinops.org/en/tools/calc-size/
     */
    private final double inputSize;
    
    /** The output size. */
    private final double outputSize;
    
    /** The overhead size. */
    private final double overheadSize;

    /**
     * Instantiates a new address format.
     *
     * @param purpose the purpose
     * @param inputSize the input size
     * @param outputSize the output size
     * @param overheadSize the overhead size
     */
    private AddressFormat(Purpose purpose, double inputSize, double outputSize, double overheadSize) {
        this.purpose = purpose;
        this.inputSize = inputSize;
        this.outputSize = outputSize;
        this.overheadSize = overheadSize;
    }

    /**
     * Gets the purpose.
     *
     * @return the purpose
     */
    public Purpose getPurpose() {
        return this.purpose;
    }

    /**
     * Gets the input size.
     *
     * @return the input size
     */
    public double getInputSize() {
        return this.inputSize;
    }

    /**
     * Gets the output size.
     *
     * @return the output size
     */
    public double getOutputSize() {
        return this.outputSize;
    }

    /**
     * Gets the overhead size.
     *
     * @return the overhead size
     */
    public double getOverheadSize() {
        return this.overheadSize;
    }

    /**
     * Gets the prefix.
     *
     * @param network the network
     * @return the prefix
     */
    public String getPrefix(Network network) {
        switch (network) {
        case MAINNET:
            switch (this) {
            case P2PKH:
                return "00";
            case P2SH:
                return "05";
            case P2WPKH:
            default:
                return "bc";
            }
        case TESTNET:
        default:
            switch (this) {
            case P2PKH:
                return "6f";
            case P2SH:
                return "c4";
            case P2WPKH:
            default:
                return "tb";
            }
        }
    }
}
