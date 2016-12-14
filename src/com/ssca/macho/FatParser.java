package com.ssca.macho;

import java.io.DataInputStream;
import java.io.IOException;

import com.ssca.format.Fat;
import com.ssca.format.FatHeader;
import com.ssca.format.MachO;
import com.ssca.utils.ByteUtils;
import com.ssca.utils.InputStreamUtils;

public class FatParser {
	public static Fat getFatInfo(String filePath) throws IOException{
		Fat fat = new Fat();
		FatHeader fatHeader = new FatHeader();
		fat.header = fatHeader;
		DataInputStream dis = InputStreamUtils.getFileDis(filePath);
		dis.skipBytes(4);//this is magic 
		
		byte[]numsOfArch = new byte[4];
		dis.read(numsOfArch);
		ByteUtils.reverseFourBytes(numsOfArch);
		fatHeader.numOfArch = ByteUtils.fourBytesToInt(numsOfArch);
		
		for(int i=0;i<fatHeader.numOfArch;i++){
			dis.skipBytes(8);
			byte[]offset = new byte[4];
			byte[]size = new byte[4];
			dis.read(offset);
			dis.read(size);
			dis.skipBytes(4);
			
			ByteUtils.reverseBytes(offset);
			ByteUtils.reverseBytes(size);
			fatHeader.offsetList.add(ByteUtils.fourBytesToInt(offset));
			fatHeader.SizeList.add(ByteUtils.fourBytesToInt(size));
			
			//parse each mach-o
			MachO res = MachOParser.parseMach_O(filePath, ByteUtils.fourBytesToInt(offset));
			fat.machoList.add(res);
		}
		return fat;
	}
}
