package com.ssca.macho;

import java.io.DataInputStream;
import java.io.IOException;

import com.ssca.finalvalue.ArmSub;
import com.ssca.finalvalue.CPU;
import com.ssca.finalvalue.FileType;
import com.ssca.finalvalue.Flags;
import com.ssca.format.MachO;
import com.ssca.format.MachOHeader;
import com.ssca.utils.ByteUtils;
import com.ssca.utils.InputStreamUtils;

public class MachOHeaderParser {
	public static void getMachOHeaderInfo(String filePath, int offset, MachO macho) throws IOException{
		MachOHeader header = new MachOHeader();
		DataInputStream dis = InputStreamUtils.getFileDis(filePath);
		dis.skipBytes(offset);
		if(dis!=null){
			//read magic info
			byte[]magicByte = new byte[4];
			dis.read(magicByte);
			ByteUtils.reverseFourBytes(magicByte);
			String magicHex = ByteUtils.bytesToHexString(magicByte);
			if(magicHex.equals("feedface")){
				header.magic = magicHex+":32bit";
				header.arch = 32;
			}
			else if(magicHex.equals("feedfacf")){
				header.magic = magicHex+":64bit";
				header.arch = 64;
			}
				
			//read cpu type
			byte[]cpuTypeByte = new byte[4];
			dis.read(cpuTypeByte);
			int cpuTypeInt = ByteUtils.fourBytesToInt(cpuTypeByte);
			switch (cpuTypeInt) {
			case CPU.ANY:
				header.cputype = "ANY";
				break;
			case CPU.X86:
				header.cputype = "X86";
				break;
			case CPU.ARM:
				header.cputype = "ARM";
				break;
			case CPU.ARM_64:
				header.cputype = "ARM_64";
				break;
			default:
				header.cputype = "unknown";
				break;
			}
			
			//read sub cpu type
			byte[]cpuSubTypeByte = new byte[4];
			dis.read(cpuSubTypeByte);
			int cpuSubTypeInt = ByteUtils.fourBytesToInt(cpuSubTypeByte);
			switch (cpuSubTypeInt) {
			case ArmSub.ALL:
				header.cpusubtype = "ALL";
				break;
			case ArmSub.V7:
				header.cpusubtype = "V7";
				break;
			default:
				header.cpusubtype = "others";
				break;
			}
			
			//read file type
			byte[]fileTypeByte = new byte[4];
			dis.read(fileTypeByte);
			int fileTpteInt = ByteUtils.fourBytesToInt(fileTypeByte);
			switch (fileTpteInt) {
			case FileType.BUNDLE:
				header.filetype = "BUNDLE";
				break;
			case FileType.CORE:
				header.filetype = "CORE";
				break;
			case FileType.DSYM:
				header.filetype = "DSYM";
				break;
			case FileType.DYLIB:
				header.filetype = "DYLIB";
				break;
			case FileType.DYLINKER:
				header.filetype = "DYLINKER";
				break;
			case FileType.EXCUTABLE:
				header.filetype = "EXCUTABLE";
				break;
			case FileType.KEXT_BUNDLE:
				header.filetype = "KEXT_BUNDLE";
				break;
			case FileType.OBJECT:
				header.filetype = "OBJECT";
				break;
			default:
				header.filetype = "others";
				break;
			}
			
			//read lc number
			byte[]lcNum = new byte[4];
			dis.read(lcNum);
			header.lcNumbers = ByteUtils.fourBytesToInt(lcNum);
			
			//read lc size
			byte[]lcSize = new byte[4];
			dis.read(lcSize);
			header.lcSize = ByteUtils.fourBytesToInt(lcSize);
			
			//read flags info
			byte[]flagByte = new byte[4];
			dis.read(flagByte);
			int thisFlags = ByteUtils.fourBytesToInt(flagByte);
			if((thisFlags&Flags.MH_ALLMODSBOUND)!=0)
				header.Flags.add("MH_ALLMODSBOUND");
			if((thisFlags&Flags.MH_ALLOW_STACK_EXECUTION)!=0)
				header.Flags.add("MH_ALLOW_STACK_EXECUTION");
			if((thisFlags&Flags.MH_BINDATLOAD)!=0)
				header.Flags.add("MH_BINDATLOAD");
			if((thisFlags&Flags.MH_BINDS_TO_WEAK)!=0)
				header.Flags.add("MH_BINDS_TO_WEAK");
			if((thisFlags&Flags.MH_CANONICAL)!=0)
				header.Flags.add("MH_CANONICAL");
			if((thisFlags&Flags.MH_DEAD_STRIPPABLE_DYLIB)!=0)
				header.Flags.add("MH_DEAD_STRIPPABLE_DYLIB");
			if((thisFlags&Flags.MH_DYLDLINK)!=0)
				header.Flags.add("MH_DYLDLINK");
			if((thisFlags&Flags.MH_FORCE_FLAT)!=0)
				header.Flags.add("MH_FORCE_FLAT");
			if((thisFlags&Flags.MH_HAS_TLV_DESCRIPTORS)!=0)
				header.Flags.add("MH_HAS_TLV_DESCRIPTORS");
			if((thisFlags&Flags.MH_INCRLINK)!=0)
				header.Flags.add("MH_INCRLINK");
			if((thisFlags&Flags.MH_LAZY_INIT)!=0)
				header.Flags.add("MH_LAZY_INIT");
			if((thisFlags&Flags.MH_NO_HEAP_EXECUTION)!=0)
				header.Flags.add("MH_NO_HEAP_EXECUTION");
			if((thisFlags&Flags.MH_NO_REEXPORTED_DYLIBS)!=0)
				header.Flags.add("MH_NO_REEXPORTED_DYLIBS");
			if((thisFlags&Flags.MH_NOFIXPREBINDING)!=0)
				header.Flags.add("MH_NOFIXPREBINDING");
			if((thisFlags&Flags.MH_NOMULTIDEFS)!=0)
				header.Flags.add("MH_NOMULTIDEFS");
			if((thisFlags&Flags.MH_NOUNDEFS)!=0)
				header.Flags.add("MH_NOUNDEFS");
			if((thisFlags&Flags.MH_PIE)!=0)
				header.Flags.add("MH_PIE");
			if((thisFlags&Flags.MH_PREBINDABLE)!=0)
				header.Flags.add("MH_PREBINDABLE");
			if((thisFlags&Flags.MH_PREBOUND)!=0)
				header.Flags.add("MH_PREBOUND");
			if((thisFlags&Flags.MH_ROOT_SAFE)!=0)
				header.Flags.add("MH_ROOT_SAFE");
			if((thisFlags&Flags.MH_SETUID_SAFE)!=0)
				header.Flags.add("MH_SETUID_SAFE");
			if((thisFlags&Flags.MH_SPLIT_SEGS)!=0)
				header.Flags.add("MH_SPLIT_SEGS");
			if((thisFlags&Flags.MH_SUBSECTIONS_VIA_SYMBOLS)!=0)
				header.Flags.add("MH_SUBSECTIONS_VIA_SYMBOLS");
			if((thisFlags&Flags.MH_TWOLEVEL)!=0)
				header.Flags.add("MH_TWOLEVEL");
			if((thisFlags&Flags.MH_WEAK_DEFINES)!=0)
				header.Flags.add("MH_WEAK_DEFINES");
			
			if(header.arch==64)
				dis.skipBytes(4);
				
			macho.header = header;
			
			//continue to parse LC
			MachOLoadCommandParser.getLCInfo(dis,macho);
		}
	}
}	
