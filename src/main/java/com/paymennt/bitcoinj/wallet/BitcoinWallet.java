/**
 * 
 */
package com.paymennt.bitcoinj.wallet;

import com.paymennt.bitcoinj.data.AddressFormat;
import com.paymennt.crypto.CoinType;
import com.paymennt.crypto.bip32.Network;
import com.paymennt.crypto.bip32.wallet.AbstractWallet;
import com.paymennt.crypto.lib.Bech32;
import com.paymennt.crypto.lib.Hash160;

/**
 * @author asendar
 *
 */
public class BitcoinWallet extends AbstractWallet {

    private final AddressFormat addressFormat;
    private final Network network;

    public BitcoinWallet(String words, String passphrase, Network network, AddressFormat addressFormat) {
        super(words, passphrase, addressFormat.getPurpose(), network, CoinType.BITCOIN);
        this.addressFormat = addressFormat;
        this.network = network;
    }

    @Override
    public String getAddress(int account, Chain chain, Integer index) {
        byte[] hash160 = Hash160.hash(getPublicKey(account, chain, index).getPublicKey());
        return Bech32.encode(addressFormat.getPrefix(network), 0, hash160);
    }

}
