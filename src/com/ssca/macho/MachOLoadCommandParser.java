package com.ssca.macho;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.ssca.finalvalue.LcName;
import com.ssca.format.SegmentLC;
import com.ssca.format.LoadCommand;
import com.ssca.format.MachO;
import com.ssca.format.Section;
import com.ssca.utils.ByteUtils;

public class MachOLoadCommandParser {
	private DataInputStream dis;
	private MachO macho;
	
	public MachOLoadCommandParser(DataInputStream dis, MachO macho){
		this.dis = dis;
		this.macho = macho;
	}

	public void getLCInfo() throws IOException{
		for(int i=0;i<macho.header.lcNumbers;i++){
			LoadCommand lc ;
			//read lc name
			byte[]cmdByte = new byte[4];
			dis.read(cmdByte);
			int cmdInt = ByteUtils.fourBytesToInt(cmdByte);
			switch (cmdInt) {
			case LcName.LC_SEGEMENT:
				lc = new SegmentLC();
				lc.command = "LC_SEGMENT";
				break;
			case LcName.LC_SEGMENT_64:
				lc = new SegmentLC();
				lc.command = "LC_SEGMENT_64";
				break;
			case LcName.LC_LOAD_DYLINKER:
				lc = new LoadCommand();
				lc.command = "LC_LOAD_DYLINKER";
				break;
			case LcName.LC_UUID:
				lc = new LoadCommand();
				lc.command = "LC_UUID";
				break;
			case LcName.LC_THREAD:
				lc = new LoadCommand();
				lc.command = "LC_THREAD";
				break;
			case LcName.LC_UNIXTHREAD:
				lc = new LoadCommand();
				lc.command = "LC_UNIXTHREAD";
				break;
			case LcName.LC_CODE_SIGNATURE:
				lc = new LoadCommand();
				lc.command = "LC_CODE_SIGNATURE";
				break;
			case LcName.LC_ENCRPTION_INFO:
				lc = new LoadCommand();
				lc.command = "LC_ENCRPTION_INFO";
				break;
			default:
				lc = new LoadCommand();
				lc.command = "others";
				break;
			}

			//read lc size
			byte[]lcSizeByte = new byte[4];
			dis.read(lcSizeByte);
			int lcSize = ByteUtils.fourBytesToInt(lcSizeByte);
			lc.cmdSize = lcSize;

			if(lc.command.equals("LC_SEGMENT") || lc.command.equals("LC_SEGMENT_64")){
				// parse LC_SEGEMENT command
				load_segment_parse((SegmentLC)lc,macho.header.arch);
			}else if(lc.command.equals("LC_LOAD_DYLINKER")){
				dis.skipBytes(lcSize-8);
			}else if(lc.command.equals("LC_UUID")){
				dis.skipBytes(lcSize-8);
			}else{
				dis.skipBytes(lcSize-8);
			}


			//add to Mach-O instance
//			macho.lcList.add(lc);
			if(lc.name==null||lc.name.equals(""))
				macho.lcMap.put(lc.command.trim(), lc);
			else
				macho.lcMap.put(lc.name.trim(), lc);
				

		}


	}

	private  void load_segment_parse(SegmentLC lc, int arch) throws IOException{
		//		Map<String, String> lcValue = new HashMap<>();
		//		SegmentLC value = new SegmentLC();
		//read segment name
		byte[] segmentByte = new byte[16];
		dis.read(segmentByte);
		String segment = new String(ByteUtils.getChars(segmentByte));
		//		lcValue.put("segment", segment);
		lc.segment = segment;
		lc.name = lc.command+lc.segment;
		
		//read vm addr
		if(arch == 32){
			byte[]vm_addr = new byte[4];
			dis.read(vm_addr);
			//		lcValue.put("VM Address", ByteUtils.bytesToHexString(ByteUtils.reverseFourBytes(vm_addr)));
			lc.vm_addr_str = ByteUtils.bytesToHexString(ByteUtils.reverseFourBytes(vm_addr));

			//read vm size
			byte[]vm_size = new byte[4];
			dis.read(vm_size);
			//		lcValue.put("VM Size", ByteUtils.fourBytesToInt(vm_size)+"");
			lc.vm_size = ByteUtils.fourBytesToInt(vm_size);

			//read file offset
			byte[]file_offset = new byte[4];
			dis.read(file_offset);
			//		lcValue.put("File Offset", ByteUtils.fourBytesToInt(file_offset)+"");
			lc.file_off = ByteUtils.fourBytesToInt(file_offset);

			//read file size
			byte[]file_size = new byte[4];
			dis.read(file_size);
			//		lcValue.put("File Size", ByteUtils.fourBytesToInt(file_size)+"");
			lc.file_size = ByteUtils.fourBytesToInt(file_size);

		}else if(arch==64){
			byte[]vm_addr = new byte[8];
			dis.read(vm_addr);
			//		lcValue.put("VM Address", ByteUtils.bytesToHexString(ByteUtils.reverseFourBytes(vm_addr)));
			lc.vm_addr_str = ByteUtils.bytesToHexString(ByteUtils.reverseBytes(vm_addr));

			//read vm size
			byte[]vm_size = new byte[8];
			dis.read(vm_size);
			//		lcValue.put("VM Size", ByteUtils.fourBytesToInt(vm_size)+"");
			lc.vm_size = ByteUtils.eightBytesToLong(vm_size);

			//read file offset
			byte[]file_offset = new byte[8];
			dis.read(file_offset);
			//		lcValue.put("File Offset", ByteUtils.fourBytesToInt(file_offset)+"");
			lc.file_off = ByteUtils.eightBytesToLong(file_offset);

			//read file size
			byte[]file_size = new byte[8];
			dis.read(file_size);
			//		lcValue.put("File Size", ByteUtils.fourBytesToInt(file_size)+"");
			lc.file_size = ByteUtils.eightBytesToLong(file_size);

		}
		//read max protection   TODO
		byte[]max_pro = new byte[4];
		dis.read(max_pro);
		//		lcValue.put("Maximum Protection", ByteUtils.bytesToHexString(ByteUtils.reverseFourBytes(max_pro)));
		lc.max_pro = ByteUtils.bytesToHexString(ByteUtils.reverseFourBytes(max_pro));

		//read max protection TODO
		byte[]init_pro = new byte[4];
		dis.read(init_pro);
		//		lcValue.put("Maximum Protection", ByteUtils.bytesToHexString(ByteUtils.reverseFourBytes(init_pro)));
		lc.init_pro = ByteUtils.bytesToHexString(ByteUtils.reverseFourBytes(init_pro));


		//read numbers of section TODO
		byte[]sec_num = new byte[4];
		dis.read(sec_num);
		//		lcValue.put("Sections Number", ByteUtils.fourBytesToInt(sec_num)+"");
		lc.sec_num = ByteUtils.fourBytesToInt(sec_num);


		//read flags
		byte[]flag = new byte[4];
		dis.read(flag);
		//		lcValue.put("Flags", ByteUtils.bytesToHexString(ByteUtils.reverseFourBytes(flag)));
		lc.flags = ByteUtils.bytesToHexString(ByteUtils.reverseFourBytes(flag));

		//store map value
		//		lc.lcValue = lcValue;
		//		lc.lcValue = value;

		//parse sections if need
		for(int j=0;j<lc.sec_num;j++){
			section_parse( lc, arch);
		}

	}

	private  void section_parse(SegmentLC lc, int arch) throws IOException{
		Section section = new Section();

		//read section name
		byte[]sectionNameByte = new byte[16];
		dis.read(sectionNameByte);
		section.sectname = new String(ByteUtils.getChars(sectionNameByte));

		//read segment name
		byte[]segmentNameByte = new byte[16];
		dis.read(segmentNameByte);
		section.segment = new String(ByteUtils.getChars(segmentNameByte));

		if(arch == 32){
			//read addr
			byte[]vm_addr = new byte[4];
			dis.read(vm_addr);
			section.addr = ByteUtils.fourBytesToInt(vm_addr);
			section.addr_str = new String(ByteUtils.bytesToHexString(ByteUtils.reverseFourBytes(vm_addr)));

			//read size 
			byte[]size = new byte[4];
			dis.read(size);
			section.size = ByteUtils.fourBytesToInt(size);
		}else if(arch == 64){
			//read addr
			byte[]vm_addr = new byte[8];
			dis.read(vm_addr);
			section.addr = ByteUtils.eightBytesToLong(vm_addr);
			section.addr_str = new String(ByteUtils.bytesToHexString(ByteUtils.reverseBytes(vm_addr)));

			//read size 
			byte[]size = new byte[8];
			dis.read(size);
			section.size = ByteUtils.eightBytesToLong(size);
		}
		//read offset
		byte[]offset = new byte[4];
		dis.read(offset);
		section.offset = ByteUtils.fourBytesToInt(offset);

		//skip others info TODO
		dis.skipBytes(24);

		if(arch==64)
			dis.skipBytes(4);

		lc.sections.put(section.sectname.trim(), section);
//		lc.sections.add(section);
	}
}
