package com.ssca.format;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MachO {
	public MachOHeader header = new MachOHeader();
//	public List<LoadCommand> lcList= new ArrayList<>();
	
	public Map<String,LoadCommand> lcMap = new HashMap<String, LoadCommand>();
	
	public Map<String,List<String>> classAndMethods = new HashMap<String, List<String>>();
}
