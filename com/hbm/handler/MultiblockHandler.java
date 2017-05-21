package com.hbm.handler;

import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IDummy;
import com.hbm.tileentity.TileEntityDummy;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MultiblockHandler {
	
	public enum EnumDirection { North, East, South, West };
	//                            2      5     3      4
	//                           -z     +x    +z     -x

	public static int EnumToInt(EnumDirection dir) {
		if(dir == EnumDirection.North)
			return 2;
		if(dir == EnumDirection.East)
			return 5;
		if(dir == EnumDirection.South)
			return 3;
		if(dir == EnumDirection.West)
			return 4;
		return 0;
	}
	public static EnumDirection IntToEnum(int dir) {
		if(dir == 2)
			return EnumDirection.North;
		if(dir == 5)
			return EnumDirection.East;
		if(dir == 3)
			return EnumDirection.South;
		if(dir == 4)
			return EnumDirection.West;
		return EnumDirection.North;
	}

	//Approved!
	public static final int[] iGenDimensionNorth = new int[] { 1, 1, 2, 0, 3, 2 };
	public static final int[] iGenDimensionEast  = new int[] { 2, 3, 2, 0, 1, 1 };
	public static final int[] iGenDimensionSouth = new int[] { 1, 1, 2, 0, 2, 3 };
	public static final int[] iGenDimensionWest  = new int[] { 3, 2, 2, 0, 1, 1 };
	public static final int[] centDimension  = new int[] { 0, 0, 2, 0, 0, 0 };
	public static final int[] cyclDimension  = new int[] { 1, 1, 5, 0, 1, 1 };
	public static final int[] wellDimension  = new int[] { 1, 1, 5, 0, 1, 1 };
	
	//Approved!
	public static boolean checkSpace(World world, int x, int y, int z, int[] i) {
		boolean placable = true;
		
		for(int a = x - i[1]; a <= x + i[0]; a++) {
			for(int b = y - i[3]; b <= y + i[2]; b++) {
				for(int c = z - i[5]; c <= z + i[4]; c++) {
					if(!(a == x && b == y && c == z)) {
						Block block = world.getBlock(a, b, c);
						if(block != Blocks.air && !block.isReplaceable(world, a, b, c)) {
							placable = false;
						}
					}
				}
			}
		}
		
		return placable;
	}
	
	public static boolean fillUp(World world, int x, int y, int z, int[] i, Block block) {
		boolean placable = true;
		
		for(int a = x - i[1]; a <= x + i[0]; a++) {
			for(int b = y - i[3]; b <= y + i[2]; b++) {
				for(int c = z - i[5]; c <= z + i[4]; c++) {
					if(!(a == x && b == y && c == z)) {
						if(!world.isRemote)
							world.setBlock(a, b, c, block);
						TileEntity te = world.getTileEntity(a, b, c);
						if(te instanceof TileEntityDummy) {
							TileEntityDummy dummy = (TileEntityDummy)te;
							dummy.targetX = x;
							dummy.targetY = y;
							dummy.targetZ = z;
						}
					}
				}
			}
		}
		
		return placable;
	}
	
	public static boolean removeAll(World world, int x, int y, int z, int[] i) {
		boolean placable = true;
		
		for(int a = x - i[1]; a <= x + i[0]; a++) {
			for(int b = y - i[3]; b <= y + i[2]; b++) {
				for(int c = z - i[5]; c <= z + i[4]; c++) {
					if(!(a == x && b == y && c == z)) {
						if(world.getBlock(a, b, c) instanceof IDummy)
							if(!world.isRemote) {
								world.func_147480_a(a, b, c, false);
							}
					}
				}
			}
		}
		
		return placable;
	}
}
