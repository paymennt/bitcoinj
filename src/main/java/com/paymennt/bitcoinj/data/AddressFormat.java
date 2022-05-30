/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.bitcoinj.data;

import com.paymennt.crypto.bip32.Network;
import com.paymennt.crypto.bip32.wallet.AbstractWallet.Purpose;


/**
 * @author paymennt
 * 
 */
public enum AddressFormat {
    
    /**  */
    P2PKH(Purpose.BIP44, 148, 34, 10),
    
    /**  */
    P2SH(Purpose.BIP49, 297, 32, 10),
    
    /**  */
    P2WPKH(Purpose.BIP84, 68, 31, 10.5);

    /**  */
    private final Purpose purpose;

    /**  */
    private final double inputSize;
    
    /**  */
    private final double outputSize;
    
    /**  */
    private final double overheadSize;

    /**
     * 
     *
     * @param purpose 
     * @param inputSize 
     * @param outputSize 
     * @param overheadSize 
     */
    private AddressFormat(Purpose purpose, double inputSize, double outputSize, double overheadSize) {
        this.purpose = purpose;
        this.inputSize = inputSize;
        this.outputSize = outputSize;
        this.overheadSize = overheadSize;
    }

    /**
     * 
     *
     * @return 
     */
    public Purpose getPurpose() {
        return this.purpose;
    }

    /**
     * 
     *
     * @return 
     */
    public double getInputSize() {
        return this.inputSize;
    }

    /**
     * 
     *
     * @return 
     */
    public double getOutputSize() {
        return this.outputSize;
    }

    /**
     * 
     *
     * @return 
     */
    public double getOverheadSize() {
        return this.overheadSize;
    }

    /**
     * 
     *
     * @param network 
     * @return 
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
