package com.hbm.tileentity.machine;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityTowerSmall extends TileEntityCondenser {
	
	public TileEntityTowerSmall() {
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(Fluids.SPENTSTEAM, 1000, 0);
		tanks[1] = new FluidTank(Fluids.WATER, 1000, 1);
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
	public void subscribeToAllAround(FluidType type, TileEntity te) {
		this.trySubscribe(this.tanks[0].getTankType(), worldObj, xCoord + 3, yCoord, zCoord, Library.POS_X);
		this.trySubscribe(this.tanks[0].getTankType(), worldObj, xCoord - 3, yCoord, zCoord, Library.NEG_X);
		this.trySubscribe(this.tanks[0].getTankType(), worldObj, xCoord, yCoord, zCoord + 3, Library.POS_Z);
		this.trySubscribe(this.tanks[0].getTankType(), worldObj, xCoord, yCoord, zCoord - 3, Library.NEG_Z);
	}

	@Override
	public void sendFluidToAll(FluidType type, TileEntity te) {
		this.sendFluid(this.tanks[1].getTankType(), worldObj, xCoord + 3, yCoord, zCoord, Library.POS_X);
		this.sendFluid(this.tanks[1].getTankType(), worldObj, xCoord - 3, yCoord, zCoord, Library.NEG_X);
		this.sendFluid(this.tanks[1].getTankType(), worldObj, xCoord, yCoord, zCoord + 3, Library.POS_Z);
		this.sendFluid(this.tanks[1].getTankType(), worldObj, xCoord, yCoord, zCoord - 3, Library.NEG_Z);
	}

	@Override
	public void fillFluidInit(FluidType type) {
		
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
