package com.ssca.macho;

import java.io.IOException;

import com.ssca.format.LoadCommand;
import com.ssca.format.MachO;
import com.ssca.format.Section;
import com.ssca.format.SegmentLC;


public class MachOParser {
	public static void parseMach_O(String filePath, int machOff) throws IOException{
		MachO macho = new MachO();

		MachOHeaderParser.getMachOHeaderInfo(filePath, machOff,macho);
		ClassListParser.getClassListItem(filePath, machOff, macho);
//		System.out.println(macho.header.magic);
//		System.out.println(macho.header.cputype);
//		System.out.println(macho.header.cpusubtype);
//		System.out.println(macho.header.filetype);
//		System.out.println(macho.header.lcNumbers);
//		System.out.println(macho.header.lcSize);
//		System.out.println(macho.header.Flags);
//		for(int i=0;i<macho.header.lcNumbers;i++){
//			LoadCommand lc = macho.lcList.get(i);
//			System.out.println(lc.command);
//			if( lc instanceof SegmentLC){
//				System.out.println("  "+((SegmentLC)lc).segment);
//				for(int j=0;j<((SegmentLC)lc).sections.size();j++){
//					Section sec = ((SegmentLC)lc).sections.get(j);
//					System.out.println("    "+sec.sectname);
//					System.out.println("    	vm:"+sec.addr+"   off:"+sec.offset);
//				}
//			}
//		}
	}


	public static void main(String[] args) {
		try {
			MachOParser.parseMach_O("/Users/konghaohao/Desktop/iOStest/DamnVulnerableIOSApp", 16384);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
