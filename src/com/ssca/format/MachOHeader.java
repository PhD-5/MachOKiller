package com.ssca.format;

import java.util.ArrayList;
import java.util.List;

public class MachOHeader {
	public int arch;//32 or 64
	public String magic;
	public String cputype;
	public String cpusubtype;
	public String filetype;
	public int lcNumbers;
	public int lcSize;
	public List<String> Flags = new ArrayList<>();
	
}
