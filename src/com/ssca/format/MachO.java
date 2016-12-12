package com.ssca.format;
import java.util.ArrayList;
import java.util.List;


public class MachO {
	public MachOHeader header = new MachOHeader();
	public List<LoadCommand> lcList= new ArrayList<>();
}
