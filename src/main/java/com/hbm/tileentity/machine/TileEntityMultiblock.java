package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityMultiblock extends TileEntity {
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(this.getBlockType() == ModBlocks.struct_launcher_core && isCompact()) {
				buildCompact();
			}
			
			if(this.getBlockType() == ModBlocks.struct_launcher_core_large) {
				
				int meta = isTable();
				
				if(meta != -1)
					buildTable(meta);
			}
		}
	}
	
	private boolean isCompact() {
		
		for(int i = -1; i <= 1; i++)
			for(int j = -1; j <= 1; j++)
				if(!(i == 0 && j == 0))
					if(worldObj.getBlock(xCoord + i, yCoord, zCoord + j) != ModBlocks.struct_launcher)
						return false;
		
		return true;
	}
	
	private int isTable() {
		
		for(int i = -4; i <= 4; i++)
			for(int j = -4; j <= 4; j++)
				if(!(i == 0 && j == 0))
					if(worldObj.getBlock(xCoord + i, yCoord, zCoord + j) != ModBlocks.struct_launcher)
						return -1;
		
		boolean flag = true;
		
		for(int k = 1; k < 12; k++) {
			if(worldObj.getBlock(xCoord + 3, yCoord + k, zCoord) != ModBlocks.struct_scaffold)
				flag = false;
		}
		
		if(flag)
			return 0;
		flag = true;
		
		for(int k = 1; k < 12; k++) {
			if(worldObj.getBlock(xCoord - 3, yCoord + k, zCoord) != ModBlocks.struct_scaffold)
				flag = false;
		}
		
		if(flag)
			return 1;
		flag = true;
		
		for(int k = 1; k < 12; k++) {
			if(worldObj.getBlock(xCoord, yCoord + k, zCoord + 3) != ModBlocks.struct_scaffold)
				flag = false;
		}
		
		if(flag)
			return 2;
		flag = true;
		
		for(int k = 1; k < 12; k++) {
			if(worldObj.getBlock(xCoord, yCoord + k, zCoord - 3) != ModBlocks.struct_scaffold)
				flag = false;
		}
		
		if(flag)
			return 3;
		
		return -1;
		
	}
	
	private void buildCompact() {
		
		worldObj.setBlock(xCoord, yCoord, zCoord, ModBlocks.compact_launcher);

		placeDummy(xCoord + 1, yCoord, zCoord + 1, xCoord, yCoord, zCoord, ModBlocks.dummy_port_compact_launcher);
		placeDummy(xCoord + 1, yCoord, zCoord, xCoord, yCoord, zCoord, ModBlocks.dummy_plate_compact_launcher);
		placeDummy(xCoord + 1, yCoord, zCoord - 1, xCoord, yCoord, zCoord, ModBlocks.dummy_port_compact_launcher);
		placeDummy(xCoord, yCoord, zCoord - 1, xCoord, yCoord, zCoord, ModBlocks.dummy_plate_compact_launcher);
		placeDummy(xCoord - 1, yCoord, zCoord - 1, xCoord, yCoord, zCoord, ModBlocks.dummy_port_compact_launcher);
		placeDummy(xCoord - 1, yCoord, zCoord, xCoord, yCoord, zCoord, ModBlocks.dummy_plate_compact_launcher);
		placeDummy(xCoord - 1, yCoord, zCoord + 1, xCoord, yCoord, zCoord, ModBlocks.dummy_port_compact_launcher);
		placeDummy(xCoord, yCoord, zCoord + 1, xCoord, yCoord, zCoord, ModBlocks.dummy_plate_compact_launcher);
	}
	
	private void buildTable(int meta) {
		
		worldObj.setBlock(xCoord, yCoord, zCoord, ModBlocks.launch_table, meta, 2);
		
		switch(meta) {
		case 0:
			for(int i = 1; i < 12; i++)
				worldObj.setBlock(xCoord + 3, yCoord + i, zCoord, Blocks.air);
			
			for(int i = -4; i <= 4; i++)
				if(i != 0)
					placeDummy(xCoord + i, yCoord, zCoord, xCoord, yCoord, zCoord, ModBlocks.dummy_port_launch_table);
			
			for(int i = -4; i <= 4; i++)
				if(i != 0)
					placeDummy(xCoord, yCoord, zCoord + i, xCoord, yCoord, zCoord, ModBlocks.dummy_plate_launch_table);
			
			break;
			
		case 1:
			for(int i = 1; i < 12; i++)
				worldObj.setBlock(xCoord - 3, yCoord + i, zCoord, Blocks.air);
			
			for(int i = -4; i <= 4; i++)
				if(i != 0)
					placeDummy(xCoord + i, yCoord, zCoord, xCoord, yCoord, zCoord, ModBlocks.dummy_port_launch_table);
			
			for(int i = -4; i <= 4; i++)
				if(i != 0)
					placeDummy(xCoord, yCoord, zCoord + i, xCoord, yCoord, zCoord, ModBlocks.dummy_plate_launch_table);
			
			break;
			
		case 2:
			for(int i = 1; i < 12; i++)
				worldObj.setBlock(xCoord, yCoord + i, zCoord + 3, Blocks.air);
			
			for(int i = -4; i <= 4; i++)
				if(i != 0)
					placeDummy(xCoord + i, yCoord, zCoord, xCoord, yCoord, zCoord, ModBlocks.dummy_plate_launch_table);
			
			for(int i = -4; i <= 4; i++)
				if(i != 0)
					placeDummy(xCoord, yCoord, zCoord + i, xCoord, yCoord, zCoord, ModBlocks.dummy_port_launch_table);
			
			break;
			
		case 3:
			for(int i = 1; i < 12; i++)
				worldObj.setBlock(xCoord, yCoord + i, zCoord - 3, Blocks.air);
			
			for(int i = -4; i <= 4; i++)
				if(i != 0)
					placeDummy(xCoord + i, yCoord, zCoord, xCoord, yCoord, zCoord, ModBlocks.dummy_plate_launch_table);
			
			for(int i = -4; i <= 4; i++)
				if(i != 0)
					placeDummy(xCoord, yCoord, zCoord + i, xCoord, yCoord, zCoord, ModBlocks.dummy_port_launch_table);
			
			break;
			
		}

		for(int i = -4; i <= 4; i++)
			for(int j = -4; j <= 4; j++)
				if(i != 0 && j != 0)
					placeDummy(xCoord + i, yCoord, zCoord + j, xCoord, yCoord, zCoord, ModBlocks.dummy_port_launch_table);
					
		
	}
	
	private void placeDummy(int x, int y, int z, int xCoord, int yCoord, int zCoord, Block block) {
		
		worldObj.setBlock(x, y, z, block);
		
		TileEntity te = worldObj.getTileEntity(x, y, z);
		
		if(te instanceof TileEntityDummy) {
			TileEntityDummy dummy = (TileEntityDummy)te;
			dummy.targetX = xCoord;
			dummy.targetY = yCoord;
			dummy.targetZ = zCoord;
		}
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}

}
