package com.hbm.tileentity.machine;

import com.hbm.handler.FluidTypeHandler.FluidTypeTheOldOne;
import com.hbm.inventory.FluidTank;
import com.hbm.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityTowerSmall extends TileEntityCondenser {
	
	public TileEntityTowerSmall() {
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(FluidTypeTheOldOne.SPENTSTEAM, 1000, 0);
		tanks[1] = new FluidTank(FluidTypeTheOldOne.WATER, 1000, 1);
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if(worldObj.isRemote) {
			
			if(this.waterTimer > 0 && this.worldObj.getTotalWorldTime() % 2 == 0) {
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "tower");
				data.setFloat("lift", 1F);
				data.setFloat("base", 0.5F);
				data.setFloat("max", 4F);
				data.setInteger("life", 250 + worldObj.rand.nextInt(250));
	
				data.setDouble("posX", xCoord + 0.5);
				data.setDouble("posZ", zCoord + 0.5);
				data.setDouble("posY", yCoord + 18);
				
				MainRegistry.proxy.effectNT(data);
			}
		}
	}

	@Override
	public void fillFluidInit(FluidTypeTheOldOne type) {
		
		for(int i = 2; i <= 6; i++) {
			ForgeDirection dir = ForgeDirection.getOrientation(i);
			fillFluid(xCoord + dir.offsetX * 3, yCoord, zCoord + dir.offsetZ * 3, getTact(), type);
		}
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 2,
					yCoord,
					zCoord - 2,
					xCoord + 3,
					yCoord + 20,
					zCoord + 3
					);
		}
		
		return bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
}
