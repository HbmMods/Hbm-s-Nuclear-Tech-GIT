package com.hbm.tileentity.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.MachineITER;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityITERStruct extends TileEntity {
	
	public static final int[][][] layout = new int[][][] {

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
	
	public static final int[][][] collisionMask = new int[][][] {

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
			new int[] {0,0,0,0,1,1,1,1,1,1,1,0,0,0,0},
			new int[] {0,0,0,1,1,1,1,1,1,1,1,1,0,0,0},
			new int[] {0,0,1,1,1,1,1,1,1,1,1,1,1,0,0},
			new int[] {0,1,1,1,1,0,2,2,2,0,1,1,1,1,0},
			new int[] {0,1,1,1,0,2,0,0,0,2,0,1,1,1,0},
			new int[] {1,1,1,1,2,0,0,0,0,0,2,1,1,1,1},
			new int[] {1,1,1,1,2,0,0,3,0,0,2,1,1,1,1},
			new int[] {1,1,1,1,2,0,0,0,0,0,2,1,1,1,1},
			new int[] {0,1,1,1,0,2,0,0,0,2,0,1,1,1,0},
			new int[] {0,1,1,1,1,0,2,2,2,0,1,1,1,1,0},
			new int[] {0,0,1,1,1,1,1,1,1,1,1,1,1,0,0},
			new int[] {0,0,0,1,1,1,1,1,1,1,1,1,0,0,0},
			new int[] {0,0,0,0,1,1,1,1,1,1,1,0,0,0,0},
			new int[] {0,0,0,0,0,0,1,1,1,0,0,0,0,0,0}
		},
		new int[][] {
			new int[] {0,0,0,0,0,0,1,1,1,0,0,0,0,0,0},
			new int[] {0,0,0,0,1,1,1,1,1,1,1,0,0,0,0},
			new int[] {0,0,0,1,1,1,1,1,1,1,1,1,0,0,0},
			new int[] {0,0,1,1,1,1,1,1,1,1,1,1,1,0,0},
			new int[] {0,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
			new int[] {0,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
			new int[] {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			new int[] {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			new int[] {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			new int[] {0,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
			new int[] {0,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
			new int[] {0,0,1,1,1,1,1,1,1,1,1,1,1,0,0},
			new int[] {0,0,0,1,1,1,1,1,1,1,1,1,0,0,0},
			new int[] {0,0,0,0,1,1,1,1,1,1,1,0,0,0,0},
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
		
		for(int y = 0; y < 5; y++) {
			for(int x = 0; x < layout[0].length; x++) {
				for(int z = 0; z < layout[0][0].length; z++) {
					
					int ly = y > 2 ? 4 - y : y;
					
					int width = 7;
					
					if(x == width && y == 0 && z == width)
						continue;
					
					int b = layout[ly][x][z];
					Block block = worldObj.getBlock(xCoord + x - width, yCoord + y, zCoord + z - width);
					int meta = worldObj.getBlockMetadata(xCoord + x - width, yCoord + y, zCoord + z - width);
					
					switch(b) {
					case 1: if(block != ModBlocks.fusion_conductor || meta != 1) { return; } break;
					case 2: if(block != ModBlocks.fusion_center) { return; } break;
					case 3: if(block != ModBlocks.fusion_motor) { return; } break;
					case 4: if(block != ModBlocks.reinforced_glass) { return; } break;
					}
				}
			}
		}

		for(int x = -2; x <= 2; x++)
			for(int y = 1; y <= 3; y++)
				for(int z = -2; z <= 2; z++)
					worldObj.setBlockToAir(xCoord + x, yCoord + y, zCoord + z);
		
		BlockDummyable.safeRem = true;
		worldObj.setBlock(xCoord, yCoord + 2, zCoord, ModBlocks.iter, 12, 3);
		((MachineITER)ModBlocks.iter).fillSpace(worldObj, xCoord, yCoord, zCoord, ForgeDirection.UNKNOWN, 0);
		BlockDummyable.safeRem = false;
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
