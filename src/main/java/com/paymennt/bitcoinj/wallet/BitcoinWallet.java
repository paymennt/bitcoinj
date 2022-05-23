/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.bitcoinj.wallet;

import com.paymennt.bitcoinj.data.AddressFormat;
import com.paymennt.crypto.CoinType;
import com.paymennt.crypto.bip32.Network;
import com.paymennt.crypto.bip32.wallet.AbstractWallet;
import com.paymennt.crypto.lib.Bech32;
import com.paymennt.crypto.lib.Hash160;

// TODO: Auto-generated Javadoc
/**
 * The Class BitcoinWallet.
 *
 * @author asendar
 */
public class BitcoinWallet extends AbstractWallet {

    /** The address format. */
    private final AddressFormat addressFormat;
    
    /** The network. */
    private final Network network;

    /**
     * Instantiates a new bitcoin wallet.
     *
     * @param words the words
     * @param passphrase the passphrase
     * @param network the network
     * @param addressFormat the address format
     */
    public BitcoinWallet(String words, String passphrase, Network network, AddressFormat addressFormat) {
        super(words, passphrase, addressFormat.getPurpose(), network, CoinType.BITCOIN);
        this.addressFormat = addressFormat;
        this.network = network;
    }

    /**
     * Gets the address.
     *
     * @param account the account
     * @param chain the chain
     * @param index the index
     * @return the address
     */
    @Override
    public String getAddress(int account, Chain chain, Integer index) {
        byte[] hash160 = Hash160.hash(getPublicKey(account, chain, index).getPublicKey());
        return Bech32.encode(addressFormat.getPrefix(network), 0, hash160);
    }

}
