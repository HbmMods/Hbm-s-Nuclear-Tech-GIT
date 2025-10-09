package com.hbm.handler;

import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import com.google.common.io.Files;

import net.minecraft.util.MathHelper;

public class Identity {

	public static int value = -1;
	public static final int FALLBACK = 666;
	public static final int MIN = 0;
	public static final int MAX = 65_535;
	public static final String FILE_NAME = "identity";

	public static void init(File dir) {
		
		File idFile = new File(dir, FILE_NAME);
		
		if(idFile.exists() && idFile.isFile()) {
			try {
				String line = Files.readFirstLine(idFile, StandardCharsets.US_ASCII);
				value = MathHelper.clamp_int(Integer.parseInt(line), MIN, MAX);
			} catch(Throwable e) { }
		}
		
		if(value == -1) {
			try {
				PrintWriter printer = new PrintWriter(idFile, StandardCharsets.US_ASCII.name());
				int newValue = new Random().nextInt(MAX + 1);
				printer.write(value + "");
				printer.close();
				value = newValue;
			} catch(Throwable e) { }
		}

		if(value == -1) value = FALLBACK;
	}
}
