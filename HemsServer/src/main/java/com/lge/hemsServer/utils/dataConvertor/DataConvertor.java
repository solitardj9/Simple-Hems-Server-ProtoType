package com.lge.hemsServer.utils.dataConvertor;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.UUID;

import com.google.common.primitives.Longs;

public class DataConvertor {
	//
	private final static int HEX_FORMAT_SIZE = 2;
	
	private final static int HEXADECIMAL = 16;
	
	private final static int LONG_TYPE_LEN = 8;
	
	//-------------------------------------------------------------------------
	public static String bytesToHex(byte[] bytes) {
		//
	    StringBuilder builder = new StringBuilder();
	    
	    for (byte b: bytes) {
	      builder.append(String.format("%02x", b));
	    }
	    
	    return builder.toString();
	}
	
	public static byte[] hexToBytes(String hex) {
		//
		if (hex == null || hex.length() == 0) {
			return null;
		}
		
		byte[] bytes = new byte[hex.length() / HEX_FORMAT_SIZE];
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) Integer.parseInt(hex.substring(HEX_FORMAT_SIZE * i, HEX_FORMAT_SIZE * i + HEX_FORMAT_SIZE), HEXADECIMAL);
		}
		
		return bytes;
	}
	//-------------------------------------------------------------------------
	
	//-------------------------------------------------------------------------
	public static byte[] longToByteArray(Long number) {
		//
		byte[] bytes = ByteBuffer.allocate(LONG_TYPE_LEN).putLong(number).array();
		
		return bytes;
	}
	
	public static Long longToByteArray(byte[] bytes) {
		//
		ByteBuffer buf = ByteBuffer.wrap(bytes);
		long number = buf.getLong();
		
		return number;
	}
	//-------------------------------------------------------------------------
	
	//-------------------------------------------------------------------------
	// https://stackoverflow.com/questions/5399798/byte-array-and-int-conversion-in-java
	public static byte[] intToByteArray(Integer number) {
		//
	    BigInteger bigInt = BigInteger.valueOf(number);      
	    return bigInt.toByteArray();
	}
	
	public static Integer byteArrayToInt(byte[] bytes) {
		//
		return new BigInteger(bytes).intValue();
	}
	//-------------------------------------------------------------------------
	
	//-------------------------------------------------------------------------
	public static byte[] concatByteArray(byte[] a, byte[] b) {
		//
	    int lenA = a.length;
	    int lenB = b.length;
	    
	    byte[] c = Arrays.copyOf(a, lenA + lenB);
	    System.arraycopy(b, 0, c, lenA, lenB);
	    
	    return c;
	}
	
	public static byte[] subByteArray(byte[] a, Integer size) {
	    //
		int lenA = a.length;
		
		if (size > lenA)
			return null;
		
	    byte[] c = Arrays.copyOf(a, size);
	    System.arraycopy(a, 0, c, 0, size);
	    
	    return c;
	}
	//-------------------------------------------------------------------------
	
	//-------------------------------------------------------------------------
	public static byte[] uuidToByteArray(UUID uuid) {
		//
		ByteBuffer buff = ByteBuffer.wrap(new byte[HEXADECIMAL]);
		
		buff.putLong(uuid.getMostSignificantBits());
		buff.putLong(uuid.getLeastSignificantBits());
		
		return buff.array();
	}
	
	public static UUID byteArrayToUuid(byte[] bytes) {
		//
		long mostSigBits = Longs.fromByteArray(bytes);
		long leastSigBits = 0;
		for (int i = LONG_TYPE_LEN; i < HEXADECIMAL; i++) {
			leastSigBits = (leastSigBits << LONG_TYPE_LEN) | (bytes[i] & 0xff);
		}
		
		return new UUID(mostSigBits, leastSigBits);
	}
	//-------------------------------------------------------------------------
}