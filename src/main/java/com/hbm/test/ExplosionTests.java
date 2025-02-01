package com.hbm.test;

import com.hbm.test.MK5Frame.BufferArray;
import com.hbm.test.MK5Frame.BufferMap;
import com.hbm.test.MK5Frame.BufferNone;
import com.hbm.util.TimeAnalyzer;
import cpw.mods.fml.common.FMLCommonHandler;

public class ExplosionTests {

	private static ExplosionWorld world = new ExplosionWorld();
	public static double BUFFER_THRESHOLD = 0.25D;

	public static void runTest() {

		int standardSpeed = (int)Math.ceil(100000 / 300);

		double[] thresholds = new double[] {0.25, 0.5};
		int[] radii = new int[] {100, 250};

		int x = 200;
		int y = 70;
		int z = 200;
		long mem = 0;

		for(int radius : radii) {

			int strength = radius * 2;
			int length = radius;

			System.gc();
			mem = getMem();
			System.out.println("#### STARTING TEST WITH NO PROXIMITY BUFFER " + radius + " ####");
			MK5Frame noBuf = new MK5Frame(world, x, y, z, strength, length).setBuffer(new BufferNone());
			while(!noBuf.isCollectionComplete) noBuf.collectTip(standardSpeed * 10);
			while(noBuf.perChunk.size() > 0) noBuf.processChunk();
			TimeAnalyzer.endCount();
			TimeAnalyzer.dump();
			System.out.println("Mem diff: " + ((getMem() - mem) / 1_048_576) + "MB");

			for(double threshold : thresholds) {
				BUFFER_THRESHOLD = threshold;

				System.gc();
				mem = getMem();
				System.out.println("#### STARTING TEST WITH MAP-BASED PROXIMITY BUFFER " + radius + " / " + threshold + " ####");
				MK5Frame mapBuf = new MK5Frame(world, x, y, z, strength, length).setBuffer(new BufferMap());
				while(!mapBuf.isCollectionComplete) mapBuf.collectTip(standardSpeed * 10);
				while(mapBuf.perChunk.size() > 0) mapBuf.processChunk();
				TimeAnalyzer.endCount();
				TimeAnalyzer.dump();
				System.out.println("Mem diff: " + ((getMem() - mem) / 1_048_576) + "MB");

				System.gc();
				mem = getMem();
				System.out.println("#### STARTING TEST WITH ARRAY PROXIMITY BUFFER " + radius + " / " + threshold + " ####");
				MK5Frame arrayBuf = new MK5Frame(world, x, y, z, strength, length).setBuffer(new BufferArray(x, y, z, (int) (length)));
				while(!arrayBuf.isCollectionComplete) arrayBuf.collectTip(standardSpeed * 10);
				while(arrayBuf.perChunk.size() > 0) arrayBuf.processChunk();
				TimeAnalyzer.endCount();
				TimeAnalyzer.dump();
				System.out.println("Mem diff: " + ((getMem() - mem) / 1_048_576) + "MB");
			}
		}

		FMLCommonHandler.instance().exitJava(0, true);
	}

	public static long getMem() {
		return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
	}
}
