package com.ssca.format;

import java.util.*;

public class FatHeader {
	public final String magic = "cafebabe";
	public int numOfArch;
	public List<Integer> offsetList = new ArrayList<>();
	public List<Integer> SizeList = new ArrayList<>();
}
