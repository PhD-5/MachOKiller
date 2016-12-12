package com.ssca.utils;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

public class ByteUtils {
	public static byte[] reverseFourBytes(byte[] res){
		if(res.length!=4){
			System.err.println("input is not four bytes");
			return null;
		}
		byte tmp1 = res[0];
		byte tmp2 = res[1];
		res[0] = res[3];
		res[1] = res[2];
		res[3] = tmp1;
		res[2] = tmp2;
		return res;
	}
	
	
	public static byte[] exchangeBytes(byte[] input){
		if(input.length%2!=0){
			System.err.println("cannot exchange bytes");
			return null;
		}else {
			for(int i=0;i<input.length;i+=2){
				byte tmp = input[i];
				input[i] = input[i+1];
				input[i+1] = tmp;
			}
			return input;
		}
	}
	
	public static byte[] exchangeTwoBytes(byte[] input){
		byte tmp = input[0];
		input[0]=input[1];
		input[1] = tmp;
		return input;
	}
	
	public static int fourBytesToInt(byte[] res){
		int targets = (res[0] & 0xff) | ((res[1] << 8) & 0xff00) // | 表示安位或   
				| ((res[2] << 24) >>> 8) | (res[3] << 24);   
		return targets;   
	}
	
	public static int two(byte[] res){
		int targets = (res[0] & 0xff) | ((res[1] << 8) & 0xff00);
		return targets;   
	}
	
	public static String bytesToHexString(byte[] src){  
	    StringBuilder stringBuilder = new StringBuilder("");  
	    if (src == null || src.length <= 0) {  
	        return null;  
	    }  
	    for (int i = 0; i < src.length; i++) {  
	        int v = src[i] & 0xFF;  
	        String hv = Integer.toHexString(v);  
	        if (hv.length() < 2) {  
	            stringBuilder.append(0);  
	        }  
	        stringBuilder.append(hv);  
	    }  
	    return stringBuilder.toString();  
	}
	
	public static char[] getChars (byte[] bytes) {
	      Charset cs = Charset.forName ("UTF-8");
	      ByteBuffer bb = ByteBuffer.allocate (bytes.length);
	      bb.put (bytes);
	                 bb.flip ();
	       CharBuffer cb = cs.decode (bb);
	  
	   return cb.array();
	}
}
