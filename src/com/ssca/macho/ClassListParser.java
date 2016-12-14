package com.ssca.macho;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.*;

import com.ssca.format.LoadCommand;
import com.ssca.format.MachO;
import com.ssca.format.Section;
import com.ssca.format.SegmentLC;
import com.ssca.utils.ByteUtils;
import com.ssca.utils.InputStreamUtils;
import com.ssca.utils.ReadStrFromAddr;

public class ClassListParser {
	public static void getClassListItem(String filePath,int machOff, MachO macho) throws IOException{
		DataInputStream dis = InputStreamUtils.getFileDis(filePath);
		//get _TEXT,__objc_classlist file off
		Section sec = ((SegmentLC)(macho.lcMap.get("LC_SEGMENT_64__DATA"))).sections.get("__objc_classlist");
		long classListOffset=sec.offset;
		long secSize = sec.size;
		if(macho.header.arch==64)
			secSize/=8;
		else
			secSize/=4;
		
		dis.skip(machOff+classListOffset);
		
		for(int classId =0; classId<secSize;classId++){
			if(macho.header.arch==64){
				byte[]data = new byte[8];
				dis.read(data);
				long vm = ByteUtils.eightBytesToLong(data);
				// calculate file offset of data(which is vm addr)
				Section _DATA__data = ((SegmentLC)(macho.lcMap.get("LC_SEGMENT_64__DATA"))).sections.get("__data");
				long dataSectionVM = _DATA__data.addr;
				int dataSectionOff = _DATA__data.offset;
				long classOff = vm - dataSectionVM + dataSectionOff;
				
				//parse data
				parseDataSection(filePath, machOff, macho, classOff);
				
			}
		}
	}
	
	public static void parseDataSection(String filePath, int machOff,MachO macho, long dataOffset) throws IOException{
		DataInputStream dis = InputStreamUtils.getFileDis(filePath);
		dis.skip(machOff+dataOffset);
		
		if(macho.header.arch==64){
			dis.skipBytes(32);
			byte[]data = new byte[8];
			dis.read(data);
			Section _DATA__const = ((SegmentLC)(macho.lcMap.get("LC_SEGMENT_64__DATA"))).sections.get("__objc_const");
			long constSectionVM = _DATA__const.addr;
			int constSectionOff = _DATA__const.offset;
			long constOffset = ByteUtils.eightBytesToLong(data) - constSectionVM + constSectionOff;
			parseConstSection(filePath, machOff, macho, constOffset);
			
		}else if(macho.header.arch==32){//TODO 32
			dis.skip(16);
		}
		
	}
	
	public static void parseConstSection(String filePath, int machOff,MachO macho, long constOffset) throws IOException{
		DataInputStream dis = InputStreamUtils.getFileDis(filePath);
		dis.skip(machOff+constOffset);
		
		if(macho.header.arch==64){
			dis.skipBytes(24);
			byte[]name = new byte[8];
			dis.read(name);
			byte[]baseMethods = new byte[8];
			dis.read(baseMethods);
			
			//parse name
			Section _TEXT__classname = ((SegmentLC)(macho.lcMap.get("LC_SEGMENT_64__TEXT"))).sections.get("__objc_classname");
			long classnameSectionVM = _TEXT__classname.addr;
			int classnameSectionOff = _TEXT__classname.offset;
			long classOff = ByteUtils.eightBytesToLong(name) - classnameSectionVM + classnameSectionOff;
			String className = new String(ReadStrFromAddr.read(filePath, classOff+machOff));
			System.out.println(className);
//			parseClassName(filePath, machOff, macho, classOff);
			
			//parse basemethods
			if(ByteUtils.eightBytesToLong(baseMethods)==0){
				System.out.println("  has no instance methods");
				return;
			}
			Section _DATA__const = ((SegmentLC)(macho.lcMap.get("LC_SEGMENT_64__DATA"))).sections.get("__objc_const");
			long constSectionVM = _DATA__const.addr;
			int constSectionOff = _DATA__const.offset;
			long baseMethOff = ByteUtils.eightBytesToLong(baseMethods) - constSectionVM + constSectionOff;
			parseBaseMethods(filePath, machOff, macho, baseMethOff);
			
		}else if(macho.header.arch==32){//TODO 32
			
		}
	}
	public static void parseBaseMethods(String filePath, int machOff,MachO macho, long baseMethOffset) throws IOException{
		DataInputStream dis = InputStreamUtils.getFileDis(filePath);
		dis.skip(machOff+baseMethOffset);
		
		if(macho.header.arch==64){
			byte[]ensize = new byte[4];
			byte[]count = new byte[4];
			dis.read(ensize);
			dis.read(count);
			
			for(int i=0;i<ByteUtils.fourBytesToInt(count);i++){
				byte[]name = new byte[8];
				byte[]type = new byte[8];
				byte[]imp  = new byte[8];
				dis.read(name);
				dis.read(type);
				dis.read(imp);
				
				Section _TEXT__methname = ((SegmentLC)(macho.lcMap.get("LC_SEGMENT_64__TEXT"))).sections.get("__objc_methname");
				long methSectionVM = _TEXT__methname.addr;
				int methSectionOff = _TEXT__methname.offset;
				long baseMethOff = ByteUtils.eightBytesToLong(name) - methSectionVM + methSectionOff;
				String methName =new String(ReadStrFromAddr.read(filePath, baseMethOff+machOff));
				System.out.println("  "+methName);
			}
			
			
		}else if(macho.header.arch==32){
			
		}
	}
}
