package com.hbm.tileentity.machine;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityITERStruct extends TileEntity {
	
	public static int[][][] layout = new int[][][] {

		new int[][] {
			new int[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			new int[] {0,0,0,0,0,1,1,1,1,1,0,0,0,0,0},
			new int[] {0,0,0,0,1,1,1,1,1,1,1,0,0,0,0},
			new int[] {0,0,0,1,1,0,0,0,0,0,1,1,0,0,0},
			new int[] {0,0,1,1,0,0,0,0,0,0,0,1,1,0,0},
			new int[] {0,1,1,0,0,0,0,0,0,0,0,0,1,1,0},
			new int[] {0,1,1,0,0,0,0,0,0,0,0,0,1,1,0},
			new int[] {0,1,1,0,0,0,0,3,0,0,0,0,1,1,0},
			new int[] {0,1,1,0,0,0,0,0,0,0,0,0,1,1,0},
			new int[] {0,1,1,0,0,0,0,0,0,0,0,0,1,1,0},
			new int[] {0,0,1,1,0,0,0,0,0,0,0,1,1,0,0},
			new int[] {0,0,0,1,1,0,0,0,0,0,1,1,0,0,0},
			new int[] {0,0,0,0,1,1,1,1,1,1,1,0,0,0,0},
			new int[] {0,0,0,0,0,1,1,1,1,1,0,0,0,0,0},
			new int[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		},
		new int[][] {
			new int[] {0,0,0,0,0,0,1,1,1,0,0,0,0,0,0},
			new int[] {0,0,0,0,1,1,0,0,0,1,1,0,0,0,0},
			new int[] {0,0,0,1,0,0,0,0,0,0,0,1,0,0,0},
			new int[] {0,0,1,0,0,1,1,1,1,1,0,0,1,0,0},
			new int[] {0,1,0,0,1,0,2,2,2,0,1,0,0,1,0},
			new int[] {0,1,0,1,0,2,0,2,0,2,0,1,0,1,0},
			new int[] {1,0,0,1,2,0,0,2,0,0,2,1,0,0,1},
			new int[] {1,0,0,1,2,2,2,3,2,2,2,1,0,0,1},
			new int[] {1,0,0,1,2,0,0,2,0,0,2,1,0,0,1},
			new int[] {0,1,0,1,0,2,0,2,0,2,0,1,0,1,0},
			new int[] {0,1,0,0,1,0,2,2,2,0,1,0,0,1,0},
			new int[] {0,0,1,0,0,1,1,1,1,1,0,0,1,0,0},
			new int[] {0,0,0,1,0,0,0,0,0,0,0,1,0,0,0},
			new int[] {0,0,0,0,1,1,0,0,0,1,1,0,0,0,0},
			new int[] {0,0,0,0,0,0,1,1,1,0,0,0,0,0,0}
		},
		new int[][] {
			new int[] {0,0,0,0,0,0,1,1,1,0,0,0,0,0,0},
			new int[] {0,0,0,0,1,1,0,0,0,1,1,0,0,0,0},
			new int[] {0,0,0,4,0,0,0,0,0,0,0,4,0,0,0},
			new int[] {0,0,4,0,0,1,1,1,1,1,0,0,4,0,0},
			new int[] {0,1,0,0,1,0,2,2,2,0,1,0,0,1,0},
			new int[] {0,1,0,1,0,2,0,0,0,2,0,1,0,1,0},
			new int[] {1,0,0,1,2,0,0,0,0,0,2,1,0,0,1},
			new int[] {1,0,0,1,2,0,0,3,0,0,2,1,0,0,1},
			new int[] {1,0,0,1,2,0,0,0,0,0,2,1,0,0,1},
			new int[] {0,1,0,1,0,2,0,0,0,2,0,1,0,1,0},
			new int[] {0,1,0,0,1,0,2,2,2,0,1,0,0,1,0},
			new int[] {0,0,4,0,0,1,1,1,1,1,0,0,4,0,0},
			new int[] {0,0,0,4,0,0,0,0,0,0,0,4,0,0,0},
			new int[] {0,0,0,0,1,1,0,0,0,1,1,0,0,0,0},
			new int[] {0,0,0,0,0,0,1,1,1,0,0,0,0,0,0}
		}
	};
	
	int age;
	
	@Override
	public void updateEntity() {
		
		if(worldObj.isRemote)
			return;
		
		age++;
		
		if(age < 20)
			return;
		
		age = 0;
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
}
