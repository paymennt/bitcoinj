package com.paymennt.bitcoinj.data;

import static java.math.BigInteger.ZERO;
import static java.math.BigInteger.valueOf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bouncycastle.util.encoders.Hex;

import com.paymennt.bitcoinj.lib.OpCodes;
import com.paymennt.bitcoinj.lib.VarInt;
import com.paymennt.crypto.bip32.Network;
import com.paymennt.crypto.lib.Base58;
import com.paymennt.crypto.lib.Bech32;
import com.paymennt.crypto.lib.Hash160;
import com.paymennt.crypto.lib.LittleEndian;
import com.paymennt.crypto.lib.Sha256;

public class Script {

    public static final String UNKNOWN = "UNKNOWN";
    public static final String P2PKH = "P2PKH"; // PAY TO PUBLIC KEY HASH
    public static final String P2SH = "P2SH"; // PAY 2 SCRIPT HASH
    public static final String P2WPKH = "P2WPKH"; // PAY 2 WITNESS PUBLIC KEY HASH
    public static final String P2WSH = "P2WSH"; // PAY 2 WITNESS SCRIPT HASH
    public static final String P2TR = "P2TR"; // PAY 2 TAPROOT

    private final List<Object> commands;

    public Script(List<Object> commands) {
        this.commands = commands;
    }

    public static Script fromByteStream(ByteArrayInputStream stream) throws IOException {
        BigInteger length = VarInt.fromByteStream(stream);
        ArrayList<Object> commands = new ArrayList<>();
        BigInteger count = BigInteger.ZERO;
        while (count.compareTo(length) < 0) {
            BigInteger current = new BigInteger(1, stream.readNBytes(1));
            count = count.add(BigInteger.ONE);

            if (current.compareTo(BigInteger.ONE) >= 0 && current.compareTo(valueOf(75)) <= 0) {
                commands.add(Hex.toHexString(stream.readNBytes(current.intValueExact())));
                count = count.add(current);
            } else if (current.equals(valueOf(76))) {
                BigInteger dataLength = LittleEndian.toUnsignedLittleEndian(stream.readNBytes(1));
                commands.add(Hex.toHexString(stream.readNBytes(dataLength.intValueExact())));
                count = count.add(dataLength.add(BigInteger.ONE));
            } else if (current.equals(valueOf(77))) {
                BigInteger dataLength = LittleEndian.toUnsignedLittleEndian(stream.readNBytes(2));
                commands.add(Hex.toHexString(stream.readNBytes(dataLength.intValueExact())));
                count = count.add(dataLength.add(BigInteger.TWO));
            } else {
                commands.add(current);
            }
        }
        if (!count.equals(length)) {
            throw new RuntimeException("Parsing script failed");
        }
        return new Script(commands);
    }

    public String rawSerialize() {
        StringBuilder stringBuilder = new StringBuilder();
        commands.forEach(command -> {
            if (command instanceof BigInteger) {
                stringBuilder.append(LittleEndian.fromUnsignedLittleEndianToHex((BigInteger) command, 1));
                return;
            }
            int length = ((String) command).length() / 2;
            if (length < 75) {
                stringBuilder.append(LittleEndian.fromUnsignedLittleEndianToHex(valueOf(length), 1));
            } else if (length > 75 && length <= 255) {
                stringBuilder.append(LittleEndian.fromUnsignedLittleEndianToHex(valueOf(76), 1));
                stringBuilder.append(LittleEndian.fromUnsignedLittleEndianToHex(valueOf(length), 1));
            } else if (length >= 256 && length <= 520) {
                stringBuilder.append(LittleEndian.fromUnsignedLittleEndianToHex(valueOf(77), 1));
                stringBuilder.append(LittleEndian.fromUnsignedLittleEndianToHex(valueOf(length), 2));
            } else {
                throw new RuntimeException("Command too long");
            }
            stringBuilder.append(command);
        });
        return stringBuilder.toString();
    }

    public String rawSerializeForSegwitSigHash() {
        StringBuilder stringBuilder = new StringBuilder();
        commands.forEach(command -> {
            if (command instanceof BigInteger) {
                stringBuilder.append(LittleEndian.fromUnsignedLittleEndianToHex((BigInteger) command, 1));
                return;
            }
            stringBuilder.append(command);
        });
        return stringBuilder.toString();
    }

    public String serialize() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String rawSerialized = rawSerialize();
        BigInteger length = valueOf(rawSerialized.length()).divide(BigInteger.TWO);
        return stringBuilder.append(VarInt.toHex(length)).append(rawSerialized).toString();
    }

    public String serializeForSegwitSigHash() {
        StringBuilder stringBuilder = new StringBuilder();
        String rawSerialized = rawSerializeForSegwitSigHash();
        BigInteger length = valueOf(rawSerialized.length()).divide(BigInteger.TWO);
        return stringBuilder.append(VarInt.toHex(length)).append(rawSerialized).toString();
    }

    public List<Object> getCommands() {
        return commands;
    }

    public String p2pkhAddress(String prefix) {
        return Base58.encodeWithChecksumFromHex(prefix.concat((String) commands.get(2)));
    }

    public String p2wpkhAddress(String prefix) {
        return Bech32.encode(prefix, 0, Hex.decodeStrict((String) commands.get(1)));
    }

    public String p2trAddress(String prefix) {
        return Bech32.encode(prefix, 1, Hex.decodeStrict((String) commands.get(1)));
    }

    public String p2shAddress(String prefix) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.writeBytes(Hex.decodeStrict(prefix));
        byteArrayOutputStream.writeBytes(Hex.decode(Hash160.hashToHex(Hex.decode(rawSerialize()))));
        return Base58.encodeWithChecksum(byteArrayOutputStream.toByteArray());
    }

    public String p2wshAddress(String prefix) {
        return Bech32.encode(prefix, 0, Sha256.hash(Hex.decode(rawSerialize())));
    }

    public static Script p2pkhScript(String hash160Pubkey) {
        return new Script(List.of(valueOf(OpCodes.OP_DUP), valueOf(OpCodes.OP_HASH160), hash160Pubkey,
                valueOf(OpCodes.OP_EQUALVERIFY), valueOf(OpCodes.OP_CHECKSIG)));
    }

    public static Script p2wpkhScript(String hash160Pubkey) {
        return new Script(List.of(ZERO, hash160Pubkey));
    }

    public static Script p2trScript(String outputKey) {
        return new Script(List.of(valueOf(OpCodes.OP_1), outputKey));
    }

    public static Script p2wshScript(String sha256) {
        return new Script(List.of(valueOf(OpCodes.OP_0), sha256));
    }

    public static Script p2shScript(String hash160) {
        return new Script(Arrays.asList(valueOf(OpCodes.OP_HASH160), hash160, valueOf(OpCodes.OP_EQUAL)));
    }

    public void appendCommand(Object command) {
        commands.add(command);
    }

    public String getType() {
        String rawSerialized = rawSerialize();
        BigInteger length = valueOf(rawSerialized.length()).divide(BigInteger.TWO);

        switch (length.intValueExact()) {
        case 25: {
            if (isP2PKH()) {
                return P2PKH;
            }
            return UNKNOWN;
        }
        case 23: {
            if (isP2SH()) {
                return P2SH;
            }
            return UNKNOWN;
        }
        case 22: {
            if (isP2WPKH()) {
                return P2WPKH;
            }
            return UNKNOWN;
        }
        case 34: {
            if (isP2WSH()) {
                return P2WSH;
            }
            if (isP2TR()) {
                return P2TR;
            }
            return UNKNOWN;
        }
        default: {
            return UNKNOWN;
        }
        }
    }

    private boolean isP2TR() {
        return commands.get(0).equals(valueOf(OpCodes.OP_1)) && ((String) commands.get(1)).length() == 64;
    }

    private boolean isP2WSH() {
        return commands.get(0).equals(valueOf(OpCodes.OP_0)) && ((String) commands.get(1)).length() == 64;
    }

    private boolean isP2WPKH() {
        return commands.get(0).equals(valueOf(OpCodes.OP_0)) && ((String) commands.get(1)).length() == 40;
    }

    private boolean isP2SH() {
        if (commands.size() < 3)
            return false;
        return commands.get(0).equals(valueOf(OpCodes.OP_HASH160)) && ((String) commands.get(1)).length() == 40
                && commands.get(2).equals(valueOf(OpCodes.OP_EQUAL));
    }

    private boolean isP2PKH() {
        if (commands.size() < 5)
            return false;
        return commands.get(0).equals(valueOf(OpCodes.OP_DUP)) && commands.get(1).equals(valueOf(OpCodes.OP_HASH160))
                && ((String) commands.get(2)).length() == 40 && commands.get(3).equals(valueOf(OpCodes.OP_EQUALVERIFY))
                && commands.get(4).equals(valueOf(OpCodes.OP_CHECKSIG));
    }

    /**
     * TODO: review
     */
    public static Script fromAddress(String address, Network network) {
        if (address == null)
            return null;

        boolean mainNet = network.equals(Network.MAINNET);
        AddressFormat addressFormat = getAddressFormat(address, network);

        if (addressFormat.equals(AddressFormat.P2PKH))
            return Script.p2pkhScript(Base58.decodeWithChecksumToHex(address));

        if (addressFormat.equals(AddressFormat.P2SH))
            return Script.p2shScript(Base58.decodeWithChecksumToHex(address));

        if (addressFormat.equals(AddressFormat.P2WPKH))
            return Script.p2wpkhScript(Bech32.decode(mainNet ? "bc" : "tb", address)[1]);

        return null;
    }

    /**
     * TODO: review
     */
    public static AddressFormat getAddressFormat(String address, Network network) {
        if (address == null)
            return null;

        boolean mainNet = network.equals(Network.MAINNET);

        if (mainNet ? address.startsWith("1") : (address.startsWith("m") || address.startsWith("n")))
            return AddressFormat.P2PKH;

        if (address.startsWith(mainNet ? "3" : "2"))
            return AddressFormat.P2SH;

        if (address.startsWith(mainNet ? "bc" : "tb"))
            return AddressFormat.P2WPKH;

        return null;
    }

}
