/************************************************************************ 
 * Copyright PointCheckout, Ltd.
 * 
 */
package com.paymennt.bitcoinj.lib;


/**
 * @author paymennt
 * 
 */
public class OpCodes {
    
    /**  */
    public static final int OP_0 = 0;
    
    /**  */
    public static final int OP_FALSE = 0;
    
    /**  */
    public static final int OP_PUSHDATA1 = 76;
    
    /**  */
    public static final int OP_PUSHDATA2 = 77;
    
    /**  */
    public static final int OP_PUSHDATA4 = 78;
    
    /**  */
    public static final int OP_1NEGATE = 79;
    
    /**  */
    public static final int OP_RESERVED = 80;
    
    /**  */
    public static final int OP_1 = 81;
    
    /**  */
    public static final int OP_TRUE = 81;
    
    /**  */
    public static final int OP_2 = 82;
    
    /**  */
    public static final int OP_3 = 83;
    
    /**  */
    public static final int OP_4 = 84;
    
    /**  */
    public static final int OP_5 = 85;
    
    /**  */
    public static final int OP_6 = 86;
    
    /**  */
    public static final int OP_7 = 87;
    
    /**  */
    public static final int OP_8 = 88;
    
    /**  */
    public static final int OP_9 = 89;
    
    /**  */
    public static final int OP_10 = 90;
    
    /**  */
    public static final int OP_11 = 91;
    
    /**  */
    public static final int OP_12 = 92;
    
    /**  */
    public static final int OP_13 = 93;
    
    /**  */
    public static final int OP_14 = 94;
    
    /**  */
    public static final int OP_15 = 95;
    
    /**  */
    public static final int OP_16 = 96;
    
    /**  */
    public static final int OP_NOP = 97;
    
    /**  */
    public static final int OP_VER = 98;
    
    /**  */
    public static final int OP_IF = 99;
    
    /**  */
    public static final int OP_NOTIF = 100;
    
    /**  */
    public static final int OP_VERIF = 101;
    
    /**  */
    public static final int OP_VERNOTIF = 102;
    
    /**  */
    public static final int OP_ELSE = 103;
    
    /**  */
    public static final int OP_ENDIF = 104;
    
    /**  */
    public static final int OP_VERIFY = 105;
    
    /**  */
    public static final int OP_RETURN = 106;
    
    /**  */
    public static final int OP_TOALTSTACK = 107;
    
    /**  */
    public static final int OP_FROMALTSTACK = 108;
    
    /**  */
    public static final int OP_2DROP = 109;
    
    /**  */
    public static final int OP_2DUP = 110;
    
    /**  */
    public static final int OP_3DUP = 111;
    
    /**  */
    public static final int OP_2OVER = 112;
    
    /**  */
    public static final int OP_2ROT = 113;
    
    /**  */
    public static final int OP_2SWAP = 114;
    
    /**  */
    public static final int OP_IFDUP = 115;
    
    /**  */
    public static final int OP_DEPTH = 116;
    
    /**  */
    public static final int OP_DROP = 117;
    
    /**  */
    public static final int OP_DUP = 118;
    
    /**  */
    public static final int OP_NIP = 119;
    
    /**  */
    public static final int OP_OVER = 120;
    
    /**  */
    public static final int OP_PICK = 121;
    
    /**  */
    public static final int OP_ROLL = 122;
    
    /**  */
    public static final int OP_ROT = 123;
    
    /**  */
    public static final int OP_SWAP = 124;
    
    /**  */
    public static final int OP_TUCK = 125;
    
    /**  */
    public static final int OP_CAT = 126;
    
    /**  */
    public static final int OP_SUBSTR = 127;
    
    /**  */
    public static final int OP_LEFT = 128;
    
    /**  */
    public static final int OP_RIGHT = 129;
    
    /**  */
    public static final int OP_SIZE = 130;
    
    /**  */
    public static final int OP_INVERT = 131;
    
    /**  */
    public static final int OP_AND = 132;
    
    /**  */
    public static final int OP_OR = 133;
    
    /**  */
    public static final int OP_XOR = 134;
    
    /**  */
    public static final int OP_EQUAL = 135;
    
    /**  */
    public static final int OP_EQUALVERIFY = 136;
    
    /**  */
    public static final int OP_RESERVED1 = 137;
    
    /**  */
    public static final int OP_RESERVED2 = 138;
    
    /**  */
    public static final int OP_1ADD = 139;
    
    /**  */
    public static final int OP_1SUB = 140;
    
    /**  */
    public static final int OP_2MUL = 141;
    
    /**  */
    public static final int OP_2DIV = 142;
    
    /**  */
    public static final int OP_NEGATE = 143;
    
    /**  */
    public static final int OP_ABS = 144;
    
    /**  */
    public static final int OP_NOT = 145;
    
    /**  */
    public static final int OP_0NOTEQUAL = 146;
    
    /**  */
    public static final int OP_ADD = 147;
    
    /**  */
    public static final int OP_SUB = 148;
    
    /**  */
    public static final int OP_MUL = 149;
    
    /**  */
    public static final int OP_DIV = 150;
    
    /**  */
    public static final int OP_MOD = 151;
    
    /**  */
    public static final int OP_LSHIFT = 152;
    
    /**  */
    public static final int OP_RSHIFT = 153;
    
    /**  */
    public static final int OP_BOOLAND = 154;
    
    /**  */
    public static final int OP_BOOLOR = 155;
    
    /**  */
    public static final int OP_NUMEQUAL = 156;
    
    /**  */
    public static final int OP_NUMEQUALVERIFY = 157;
    
    /**  */
    public static final int OP_NUMNOTEQUAL = 158;
    
    /**  */
    public static final int OP_LESSTHAN = 159;
    
    /**  */
    public static final int OP_GREATERTHAN = 160;
    
    /**  */
    public static final int OP_LESSTHANOREQUAL = 161;
    
    /**  */
    public static final int OP_GREATERTHANOREQUAL = 162;
    
    /**  */
    public static final int OP_MIN = 163;
    
    /**  */
    public static final int OP_MAX = 164;
    
    /**  */
    public static final int OP_WITHIN = 165;
    
    /**  */
    public static final int OP_RIPEMD160 = 166;
    
    /**  */
    public static final int OP_SHA1 = 167;
    
    /**  */
    public static final int OP_SHA256 = 168;
    
    /**  */
    public static final int OP_HASH160 = 169;
    
    /**  */
    public static final int OP_HASH256 = 170;
    
    /**  */
    public static final int OP_CODESEPARATOR = 171;
    
    /**  */
    public static final int OP_CHECKSIG = 172;
    
    /**  */
    public static final int OP_CHECKSIGVERIFY = 173;
    
    /**  */
    public static final int OP_CHECKMULTISIG = 174;
    
    /**  */
    public static final int OP_CHECKMULTISIGVERIFY = 175;
    
    /**  */
    public static final int OP_NOP1 = 176;
    
    /**  */
    public static final int OP_CHECKLOCKTIMEVERIFY = 177;
    
    /**  */
    public static final int OP_NOP2 = 177;
    
    /**  */
    public static final int OP_CHECKSEQUENCEVERIFY = 178;
    
    /**  */
    public static final int OP_NOP3 = 178;
    
    /**  */
    public static final int OP_NOP4 = 179;
    
    /**  */
    public static final int OP_NOP5 = 180;
    
    /**  */
    public static final int OP_NOP6 = 181;
    
    /**  */
    public static final int OP_NOP7 = 182;
    
    /**  */
    public static final int OP_NOP8 = 183;
    
    /**  */
    public static final int OP_NOP9 = 184;
    
    /**  */
    public static final int OP_NOP10 = 185;
    
    /**  */
    public static final int OP_INVALIDOPCODE = 255;
}
