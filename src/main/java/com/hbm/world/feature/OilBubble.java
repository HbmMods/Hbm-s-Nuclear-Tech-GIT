package com.hbm.world.feature;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public class OilBubble {

	public static void spawnOil(World world, int x, int y, int z, int radius, Block block, int meta, Block target) {
		int r = radius;
		int r2 = r * r;
		int r22 = r2 / 2;

		for (int xx = -r; xx < r; xx++) {
			int X = xx + x;
			int XX = xx * xx;
			for (int yy = -r; yy < r; yy++) {
				int Y = yy + y;
				int YY = XX + yy * yy * 3;
				for (int zz = -r; zz < r; zz++) {
					int Z = zz + z;
					int ZZ = YY + zz * zz;
					if (ZZ < r22) {
						if(world.getBlock(X, Y, Z) == target)
							world.setBlock(X, Y, Z, block, meta, 2);
					}
				}
			}
		}
	}
	
}
