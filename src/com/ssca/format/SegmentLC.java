package com.ssca.format;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SegmentLC extends LoadCommand{
	public  String segment;
	public  String vm_addr_str;
	public  long vm_addr;
	public  long vm_size;
	public  long file_off;
	public  long file_size;
	public  String max_pro;
	public  String init_pro;
	public  int sec_num;
	public  String flags;
	
//	public List<Section> sections = new ArrayList<Section>();
	public Map<String,Section> sections = new HashMap<>();
	
}
