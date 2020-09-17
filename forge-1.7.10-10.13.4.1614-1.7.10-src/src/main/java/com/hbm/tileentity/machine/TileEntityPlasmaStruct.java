package com.hbm.tileentity.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.MachinePlasmaHeater;
import com.hbm.handler.MultiblockHandlerXR;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityPlasmaStruct extends TileEntity {
	
	int age;
	
	@Override
	public void updateEntity() {
		
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata());
		
		if(worldObj.isRemote) {
			worldObj.spawnParticle("reddust",
					xCoord + 0.5 + dir.offsetX * -11 + worldObj.rand.nextGaussian() * 0.1,
					yCoord + 2.5 + worldObj.rand.nextGaussian() * 0.1,
					zCoord + 0.5 + dir.offsetZ * -11 + worldObj.rand.nextGaussian() * 0.1,
					0.9, 0.3, 0.7);
			return;
		}
		
		age++;
		
		if(age < 20)
			return;
		
		age = 0;
		
		MachinePlasmaHeater plas = (MachinePlasmaHeater)ModBlocks.plasma_heater;
		
		int[] rot = MultiblockHandlerXR.rotate(plas.getDimensions(), dir);

		for(int a = xCoord - rot[4]; a <= xCoord + rot[5]; a++) {
			for(int b = yCoord - rot[1]; b <= yCoord + rot[0]; b++) {
				for(int c = zCoord - rot[2]; c <= zCoord + rot[3]; c++) {
					
					if(a == xCoord && b == yCoord && c == zCoord)
						continue;
					
					if(worldObj.getBlock(a, b, c) != ModBlocks.fusion_heater)
						return;
				}
			}
		}
		
		rot = MultiblockHandlerXR.rotate(new int[] {4, -3, 2, 1, 1, 1}, dir);

		for(int a = xCoord - rot[4]; a <= xCoord + rot[5]; a++) {
			for(int b = yCoord - rot[1]; b <= yCoord + rot[0]; b++) {
				for(int c = zCoord - rot[2]; c <= zCoord + rot[3]; c++) {
					
					if(a == xCoord && b == yCoord && c == zCoord)
						continue;
					
					if(worldObj.getBlock(a, b, c) != ModBlocks.fusion_heater)
						return;
				}
			}
		}


        for(int i = 9; i <= 10; i++)
            for(int j = 1; j <= 2; j++)
			if(worldObj.getBlock(xCoord - dir.offsetX * i, yCoord + j, zCoord - dir.offsetZ * i) != ModBlocks.fusion_heater)
				return;
		
		BlockDummyable.safeRem = true;
		worldObj.setBlock(xCoord, yCoord, zCoord, ModBlocks.plasma_heater, this.getBlockMetadata() + BlockDummyable.offset, 3);
		plas.fillSpace(worldObj, xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ, dir, -plas.getOffset());
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
