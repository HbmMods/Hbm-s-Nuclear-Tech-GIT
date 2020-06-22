package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.SoyuzLauncher;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntitySoyuzStruct extends TileEntity {
	
	int age;
	
	@Override
	public void updateEntity() {
		
		if(worldObj.isRemote)
			return;
		
		age++;
		
		if(age < 20)
			return;
		
		age = 0;

		/// CHECK PAD ///
		for(int i = -6; i <= 6; i++)
			for(int j = 3; j <= 4; j++)
				for(int k = -6; k <= 6; k++)
					if(worldObj.getBlock(xCoord + i, yCoord + j, zCoord + k) != ModBlocks.struct_launcher)
						return;
		
		for(int i = -1; i <= 1; i++)
			for(int j = 3; j <= 4; j++)
				for(int k = -8; k <= -7; k++)
					if(worldObj.getBlock(xCoord + i, yCoord + j, zCoord + k) != ModBlocks.struct_launcher)
						return;
		
		for(int i = -2; i <= 2; i++)
			for(int j = 3; j <= 4; j++)
				for(int k = 7; k <= 9; k++)
					if(worldObj.getBlock(xCoord + i, yCoord + j, zCoord + k) != ModBlocks.struct_launcher)
						return;
		
		for(int i = -2; i <= 2; i++)
			for(int k = 5; k <= 9; k++)
				if(worldObj.getBlock(xCoord + i, yCoord + 51, zCoord + k) != ModBlocks.struct_launcher)
					return;
		
		for(int i = -1; i <= 1; i++)
			for(int k = -8; k <= -6; k++)
				if(worldObj.getBlock(xCoord + i, yCoord + 38, zCoord + k) != ModBlocks.struct_launcher)
					return;
		
		/// CHECK LEGS ///
		for(int i = 3; i <= 6; i++)
			for(int j = 0; j <= 2; j++)
				for(int k = 3; k <= 6; k++)
					if(worldObj.getBlock(xCoord + i, yCoord + j, zCoord + k) != ModBlocks.concrete &&
							worldObj.getBlock(xCoord + i, yCoord + j, zCoord + k) != ModBlocks.concrete_smooth)
						return;
		
		for(int i = -6; i <= -3; i++)
			for(int j = 0; j <= 2; j++)
				for(int k = 3; k <= 6; k++)
					if(worldObj.getBlock(xCoord + i, yCoord + j, zCoord + k) != ModBlocks.concrete &&
					worldObj.getBlock(xCoord + i, yCoord + j, zCoord + k) != ModBlocks.concrete_smooth)
				return;
		
		for(int i = -6; i <= -3; i++)
			for(int j = 0; j <= 2; j++)
				for(int k = -6; k <= -3; k++)
					if(worldObj.getBlock(xCoord + i, yCoord + j, zCoord + k) != ModBlocks.concrete &&
					worldObj.getBlock(xCoord + i, yCoord + j, zCoord + k) != ModBlocks.concrete_smooth)
				return;
		
		for(int i = 3; i <= 6; i++)
			for(int j = 0; j <= 2; j++)
				for(int k = -6; k <= -3; k++)
					if(worldObj.getBlock(xCoord + i, yCoord + j, zCoord + k) != ModBlocks.concrete &&
					worldObj.getBlock(xCoord + i, yCoord + j, zCoord + k) != ModBlocks.concrete_smooth)
				return;
		
		for(int i = -1; i <= 1; i++)
			for(int j = 0; j <= 2; j++)
				for(int k = -8; k <= -6; k++)
					if(worldObj.getBlock(xCoord + i, yCoord + j, zCoord + k) != ModBlocks.concrete &&
					worldObj.getBlock(xCoord + i, yCoord + j, zCoord + k) != ModBlocks.concrete_smooth)
				return;
		
		for(int i = -2; i <= 2; i++)
			for(int j = 0; j <= 2; j++)
				for(int k = 5; k <= 9; k++)
					if(worldObj.getBlock(xCoord + i, yCoord + j, zCoord + k) != ModBlocks.concrete &&
					worldObj.getBlock(xCoord + i, yCoord + j, zCoord + k) != ModBlocks.concrete_smooth)
				return;
		
		/// CHECK SCAFFOLDING ///
		for(int i = -1; i <= 1; i++)
			for(int j = 5; j <= 50; j++)
				for(int k = 6; k <= 8; k++)
					if(worldObj.getBlock(xCoord + i, yCoord + j, zCoord + k) != ModBlocks.struct_scaffold)
						return;
		
		for(int j = 5; j <= 37; j++)
			if(worldObj.getBlock(xCoord, yCoord + j, zCoord - 7) != ModBlocks.struct_scaffold)
				return;
		/// CHECKS COMPLETE ///
		
		/// DELETE SCAFFOLDING ///
		
		for(int i = -2; i <= 2; i++)
			for(int k = 5; k <= 9; k++)
				worldObj.setBlock(xCoord + i, yCoord + 51, zCoord + k, Blocks.air);
		
		for(int i = -1; i <= 1; i++)
			for(int k = -8; k <= -6; k++)
				worldObj.setBlock(xCoord + i, yCoord + 38, zCoord + k, Blocks.air);
		
		for(int i = -2; i <= 2; i++)
			for(int j = 0; j <= 2; j++)
				for(int k = 5; k <= 9; k++)
					worldObj.setBlock(xCoord + i, yCoord + j, zCoord + k, Blocks.air);
		
		for(int i = -1; i <= 1; i++)
			for(int j = 5; j <= 50; j++)
				for(int k = 6; k <= 8; k++)
					worldObj.setBlock(xCoord + i, yCoord + j, zCoord + k, Blocks.air);
		
		for(int j = 5; j <= 37; j++)
			worldObj.setBlock(xCoord, yCoord + j, zCoord - 7, Blocks.air);

		/// GENERATE LAUNCHER ///
		
		ForgeDirection dir = ForgeDirection.EAST;
		
		worldObj.setBlock(xCoord, yCoord, zCoord, Blocks.air);
		worldObj.setBlock(xCoord , yCoord + SoyuzLauncher.height, zCoord, ModBlocks.soyuz_launcher, dir.ordinal() + SoyuzLauncher.offset, 3);
		((SoyuzLauncher)ModBlocks.soyuz_launcher).fillSpace(worldObj, xCoord, yCoord, zCoord, dir, 0);
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
