package com.ssca.utils;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class InputStreamUtils {
	public static DataInputStream getFileDis(String filePath){
		DataInputStream dis = null;
		try {
			dis = new DataInputStream(new BufferedInputStream(new FileInputStream(filePath)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return dis;
	}
}
