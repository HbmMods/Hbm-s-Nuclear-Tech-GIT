package com.hbm.tileentity.machine;

import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.fluid.IFluidUser;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityChimneyBrick extends TileEntityLoadedBase implements IFluidUser, INBTPacketReceiver {
	
	public int onTicks;
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(worldObj.getTotalWorldTime() % 20 == 0) {
				FluidType[] types = new FluidType[] {Fluids.SMOKE, Fluids.SMOKE_LEADED, Fluids.SMOKE_POISON};
				
				for(FluidType type : types) {
					this.trySubscribe(type, worldObj, xCoord + 2, yCoord, zCoord, Library.POS_X);
					this.trySubscribe(type, worldObj, xCoord - 2, yCoord, zCoord, Library.NEG_X);
					this.trySubscribe(type, worldObj, xCoord, yCoord, zCoord + 2, Library.POS_Z);
					this.trySubscribe(type, worldObj, xCoord, yCoord, zCoord - 2, Library.NEG_Z);
				}
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("onTicks", onTicks);
			INBTPacketReceiver.networkPack(this, data, 150);
			
			if(onTicks > 0) onTicks--;
			
		} else {
			
			if(onTicks > 0) {

				if(worldObj.getTotalWorldTime() % 2 == 0) {
					NBTTagCompound fx = new NBTTagCompound();
					fx.setString("type", "tower");
					fx.setFloat("lift", 10F);
					fx.setFloat("base", 0.5F);
					fx.setFloat("max", 3F);
					fx.setInteger("life", 250 + worldObj.rand.nextInt(50));
					fx.setInteger("color",0x404040);
					fx.setDouble("posX", xCoord + 0.5);
					fx.setDouble("posY", yCoord + 12);
					fx.setDouble("posZ", zCoord + 0.5);
					MainRegistry.proxy.effectNT(fx);
				}
			}
		}
	}
	
	public void networkUnpack(NBTTagCompound nbt) {
		this.onTicks = nbt.getInteger("onTicks");
	}

	@Override
	public boolean canConnect(FluidType type, ForgeDirection dir) {
		return (dir == ForgeDirection.NORTH || dir == ForgeDirection.SOUTH || dir == ForgeDirection.EAST || dir == ForgeDirection.WEST) &&
				(type == Fluids.SMOKE || type == Fluids.SMOKE_LEADED || type == Fluids.SMOKE_POISON);
	}

	@Override
	public long transferFluid(FluidType type, int pressure, long fluid) {
		onTicks = 20;
		
		fluid *= 0.25;

		if(type == Fluids.SMOKE) PollutionHandler.incrementPollution(worldObj, xCoord, yCoord, zCoord, PollutionType.SOOT, fluid / 100F);
		if(type == Fluids.SMOKE_LEADED) PollutionHandler.incrementPollution(worldObj, xCoord, yCoord, zCoord, PollutionType.HEAVYMETAL, fluid / 100F);
		if(type == Fluids.SMOKE_POISON) PollutionHandler.incrementPollution(worldObj, xCoord, yCoord, zCoord, PollutionType.POISON, fluid / 100F);
		
		return 0;
	}

	@Override
	public long getDemand(FluidType type, int pressure) {
		return 1_000_000;
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] {};
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 1,
					yCoord,
					zCoord - 1,
					xCoord + 2,
					yCoord + 13,
					zCoord + 2
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
