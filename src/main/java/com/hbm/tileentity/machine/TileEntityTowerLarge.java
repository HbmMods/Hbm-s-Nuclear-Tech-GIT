package com.hbm.tileentity.machine;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityTowerLarge extends TileEntityCondenser {
	
	public TileEntityTowerLarge() {
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(Fluids.SPENTSTEAM, 10000, 0);
		tanks[1] = new FluidTank(Fluids.WATER, 10000, 1);
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if(worldObj.isRemote) {
			
			if(this.waterTimer > 0 && this.worldObj.getTotalWorldTime() % 4 == 0) {
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "tower");
				data.setFloat("lift", 0.5F);
				data.setFloat("base", 1F);
				data.setFloat("max", 10F);
				data.setInteger("life", 750 + worldObj.rand.nextInt(250));
	
				data.setDouble("posX", xCoord + 0.5 + worldObj.rand.nextDouble() * 3 - 1.5);
				data.setDouble("posZ", zCoord + 0.5 + worldObj.rand.nextDouble() * 3 - 1.5);
				data.setDouble("posY", yCoord + 1);
				
				MainRegistry.proxy.effectNT(data);
			}
		}
	}

	@Override
	public void fillFluidInit(FluidType type) {
		
		for(int i = 2; i <= 6; i++) {
			ForgeDirection dir = ForgeDirection.getOrientation(i);
			ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
			fillFluid(xCoord + dir.offsetX * 5, yCoord, zCoord + dir.offsetZ * 5, getTact(), type);
			fillFluid(xCoord + dir.offsetX * 5 + rot.offsetX * 3, yCoord, zCoord + dir.offsetZ * 5 + rot.offsetZ * 3, getTact(), type);
			fillFluid(xCoord + dir.offsetX * 5 + rot.offsetX * -3, yCoord, zCoord + dir.offsetZ * 5 + rot.offsetZ * -3, getTact(), type);
		}
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 4,
					yCoord,
					zCoord - 4,
					xCoord + 5,
					yCoord + 13,
					zCoord + 5
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
