package com.ssca.format;

import java.util.ArrayList;
import java.util.List;

public class SegmentLC extends LoadCommand{
	
	public  String segment;
	public  String vm_addr;
	public  int vm_size;
	public  int file_off;
	public  int file_size;
	public  String max_pro;
	public  String init_pro;
	public  int sec_num;
	public  String flags;
	
	public List<Section> sections = new ArrayList<Section>();
	
}
