package com.ssca.macho;

import java.io.IOException;

import javax.crypto.Mac;

import com.ssca.format.LoadCommand;
import com.ssca.format.MachO;
import com.ssca.format.Section;
import com.ssca.format.SegmentLC;


public class MachOParser {
	private MachO macho;
	private String filePath;
	private int machOff;
	
	public MachOParser(String filePath, int machOff){
		this.filePath = filePath;
		this.machOff = machOff;
	}
	public  MachO parseMach_O() throws IOException{
		macho = new MachO();
		
		MachOHeaderParser machoParser = new MachOHeaderParser(filePath, machOff,macho);
		machoParser.getMachOHeaderInfo();
		
		ClassListParser classListParser = new ClassListParser(filePath, machOff, macho);
		classListParser.getClassListItem();

		return macho;
	}

}
