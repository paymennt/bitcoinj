/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.bitcoinj.data;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.paymennt.bitcoinj.data.mapper.BitcoinTransactionMapper;
import com.paymennt.bitcoinj.data.mapper.LegacyBitcoinTransactionMapper;
import com.paymennt.bitcoinj.data.mapper.SegwitBitcoinTransactionMapper;


/**
 * @author paymennt
 * 
 */
public abstract class BitcoinTransaction {

    /**  */
    private static final List<BitcoinTransactionMapper<?>> transactionMapperList = new ArrayList<>();

    /**  */
    protected final BigInteger version;

    /**  */
    protected final List<BitcoinTransactionInput> inputs;

    /**  */
    protected final List<BitcoinTransactionOutput> outputs;

    /**  */
    protected final BigInteger locktime;

    /*******************************************************************************************************************
     * STATIC METHODS
     */

    static {
        BitcoinTransaction.transactionMapperList.add(SegwitBitcoinTransactionMapper.INSTANCE);
        BitcoinTransaction.transactionMapperList.add(LegacyBitcoinTransactionMapper.INSTANCE);
    }

    /**
     * 
     *
     * @param stream 
     * @return 
     * @throws IOException 
     */
    public static BitcoinTransaction fromByteStream(ByteArrayInputStream stream) throws IOException {
        BitcoinTransactionMapper<?> mapper = getMapperForInputStream(stream);
        if (mapper == null)
            throw new RuntimeException("Unsupported trnasaction format");
        return mapper.parse(stream);
    }

    /**
     * 
     *
     * @param stream 
     * @return 
     * @throws IOException 
     */
    private static BitcoinTransactionMapper<?> getMapperForInputStream(ByteArrayInputStream stream) throws IOException {
        for (BitcoinTransactionMapper<?> mapper : transactionMapperList) {
            if (mapper.isSupported(stream))
                return mapper;
        }
        return null;
    }

    /**
     * 
     *
     * @param version 
     * @param inputs 
     * @param outputs 
     * @param locktime 
     */

    /**
     * @param version
     * @param inputs
     * @param outputs
     * @param locktime
     */
    public BitcoinTransaction(//
            BigInteger version, //
            List<BitcoinTransactionInput> inputs, //
            List<BitcoinTransactionOutput> outputs, //
            BigInteger locktime) {
        this.version = version;
        this.inputs = inputs;
        this.outputs = outputs;
        this.locktime = locktime;
    }

    /**
     * 
     *
     * @return 
     * @throws IOException 
     */

    public abstract String serialize() throws IOException;

    /**
     * 
     *
     * @return 
     * @throws IOException 
     */
    public abstract String getTransactionId() throws IOException;

    /**
     * 
     *
     * @return 
     */

    public BigInteger getVersion() {
        return this.version;
    }

    /**
     * 
     *
     * @return 
     */
    public List<BitcoinTransactionInput> getInputs() {
        return this.inputs;
    }

    /**
     * 
     *
     * @return 
     */
    public List<BitcoinTransactionOutput> getOutputs() {
        return this.outputs;
    }

    /**
     * 
     *
     * @return 
     */
    public BigInteger getLocktime() {
        return this.locktime;
    }

    /**
     * 
     *
     * @param inputIndex 
     * @return 
     */
    public BitcoinTransactionInput getInput(int inputIndex) {
        return this.inputs.get(inputIndex);
    }

    /*******************************************************************************************************************
     * MAIN FOR TESTING
     * @throws IOException 
     */

    //    public static void main(String[] args) throws IOException {
    //        String txHex;
    //        txHex =
    //            "01000000000101076b57644e155af90f5d9f416b44a3794e0b982c2c427f0845c0e0c62fbb346f0000000000fdffffff0198eb100"
    //                + "000000000160014934478b061fa4b5b4dba4f314fb380f3ef77e21902483045022100b7fcf54ae5d7c645b5b44ef7f846e9"
    //                + "5de9a97a099a447bf8daf14a46f5e3d464022025e709d6794a6fd5b69a7d271fc9a93fcc170b38cfbe5640b6c5d6ec88f02"
    //                + "1240121025330a1df68c516d32a87ea8ea3da573fa9d86b1b173875beecbf0bdbe45cba8cea7c0a00";
    //        txHex =
    //            "0100000001813f79011acb80925dfe69b3def355fe914bd1d96a3f5f71bf8303c6a989c7d1000000006b483045022100ed81ff192"
    //                + "e75a3fd2304004dcadb746fa5e24c5031ccfcf21320b0277457c98f02207a986d955c6e0cb35d446a89d3f56100f4d7f678"
    //                + "01c31967743a9c8e10615bed01210349fc4e631e3624a545de3f89f5d8684c7b8138bd94bdd531d2e213bf016b278afefff"
    //                + "fff02a135ef01000000001976a914bc3b654dca7e56b04dca18f2566cdaf02e8d9ada88ac99c39800000000001976a9141c"
    //                + "4bc762dd5423e332166702cb75f40df79fea1288ac19430600";
    //        txHex =
    //            "01000000000101ad8fd43713cb5703ee901106981a710090e6534cc4d388f864bad8d50a5adfbc0000000000ffffffff0158a0070"
    //                + "00000000016001447fb90212e3a27e7dd4b92f4f2d178c5fadb8f3702483045022100f4fd2c583c6d352b8c3f1da3b3afd2"
    //                + "17f50dcd83d7cadaddbef549fa30e1a28b0220709b37a415a2f1c29dd37bf4558122f369d9907e492c52e259e62275c1a8f"
    //                + "2ae01210237ca41ef818d63610a25800812eeb40216e22cd99546ed0675bde716d6115bc000000000";
    //        BitcoinTransaction transaction = BitcoinTransaction.fromByteStream(new ByteArrayInputStream(Hex.decode(txHex)));
    //        System.out.println(transaction.serialize());
    //    }

}
