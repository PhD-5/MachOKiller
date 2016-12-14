package com.ssca.main;

import java.io.DataInputStream;
import java.io.IOException;

import com.ssca.format.Fat;
import com.ssca.format.MachO;
import com.ssca.macho.FatParser;
import com.ssca.macho.MachOParser;
import com.ssca.utils.ByteUtils;
import com.ssca.utils.InputStreamUtils;

public class ParseFile {
	public static Object parse(String filePath){
		DataInputStream dis = InputStreamUtils.getFileDis(filePath);
		byte[]magic = new byte[4];
		try {
			dis.read(magic);
			String magicStr = ByteUtils.bytesToHexString(magic);
			if(magicStr.equals("cafebabe")){
				// this is a fat file
				Fat fat = FatParser.getFatInfo(filePath);
				return fat;
			}else{
				MachO macho = new MachOParser(filePath, 0).parseMach_O();
				return macho;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		Object o = parse("/Users/konghaohao/Desktop/iOStest/VulApp");
		if(o instanceof Fat){
			System.out.println("this is a fat");
			Fat fat = (Fat)o;
			//get every from fat
			System.out.println(fat.header.magic);
		}else{
			System.out.println("this is a macho");
			MachO macho = (MachO)o;
			//get every from fat
			System.out.println(macho.header.magic);
		}
	}
}
