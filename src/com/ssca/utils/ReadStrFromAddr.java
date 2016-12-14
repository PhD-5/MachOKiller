package com.ssca.utils;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.*;

public class ReadStrFromAddr {
	public static char[] read(String filePath, long addr) throws IOException{
		List<Byte> byteList = new ArrayList<Byte>();
		DataInputStream dis = InputStreamUtils.getFileDis(filePath);
		dis.skip(addr);
		byte b = dis.readByte();
		while(b!=0){
			byteList.add(b);
			b = dis.readByte();
		}
		byte[]arr = new byte[byteList.size()];
		for(int i=0;i<byteList.size();i++)
			arr[i] = byteList.get(i);
		
		return ByteUtils.getChars(arr);
	}
}
