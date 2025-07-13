package com.hbm.blocks;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.world.IBlockAccess;

public interface IBlockSideRotation {

	public int getRotationFromSide(IBlockAccess world, int x, int y, int z, int side);

	public static int renderID = RenderingRegistry.getNextAvailableRenderId();
	public static int getRenderType() {
		return renderID;
	}

	// 0 1 3 2 becomes 0 2 3 1
	// I want to smoke that swedish kush because it clearly makes you fucking stupid
	public static int topToBottom(int topRotation) {
		switch(topRotation) {
		case 1: return 2;
		case 2: return 1;
		default: return topRotation;
		}
	}

	public static boolean isOpposite(int from, int to) {
		switch(from) {
			case 0: return to == 1;
			case 1: return to == 0;
			case 2: return to == 3;
			case 3: return to == 2;
			case 4: return to == 5;
			case 5: return to == 4;
			default: return false;
		}
	}

}
