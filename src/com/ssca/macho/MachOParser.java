package com.ssca.macho;

import java.io.IOException;

import com.ssca.format.LoadCommand;
import com.ssca.format.MachO;
import com.ssca.format.Section;
import com.ssca.format.SegmentLC;


public class MachOParser {
	public static MachO parseMach_O(String filePath, int machOff) throws IOException{
		MachO macho = new MachO();

		MachOHeaderParser.getMachOHeaderInfo(filePath, machOff,macho);
		ClassListParser.getClassListItem(filePath, machOff, macho);

		return macho;
	}


	public static void main(String[] args) {
		try {
			MachOParser.parseMach_O("/Users/konghaohao/Desktop/iOStest/DamnVulnerableIOSApp", 16384);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
