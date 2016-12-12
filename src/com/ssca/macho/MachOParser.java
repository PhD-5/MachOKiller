package com.ssca.macho;

import java.io.IOException;

import com.ssca.format.MachO;


public class MachOParser {
	public static void parseMach_O(String filePath, int machOff) throws IOException{
		MachO macho = new MachO();
		
		MachOHeaderParser.getMachOHeaderInfo(filePath, machOff,macho);
		System.out.println(macho.header.magic);
		System.out.println(macho.header.cputype);
		System.out.println(macho.header.cpusubtype);
		System.out.println(macho.header.filetype);
		System.out.println(macho.header.lcNumbers);
		System.out.println(macho.header.lcSize);
		System.out.println(macho.header.Flags);
	}
	
	
	public static void main(String[] args) {
		try {
			MachOParser.parseMach_O("/Users/konghaohao/Desktop/iOStest/DamnVulnerableIOSApp", 16384);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
