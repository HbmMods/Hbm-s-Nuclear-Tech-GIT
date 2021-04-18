package com.hbm.tileentity.machine.rbmk;

import java.util.ArrayList;
import java.util.List;

import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.FluidTank;
import com.hbm.lib.Library;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;

public class TileEntityRBMKBoiler extends TileEntityRBMKSlottedBase implements IFluidAcceptor, IFluidSource, IControlReceiver {
	
	public FluidTank feed;
	public FluidTank steam;
	public List<IFluidAcceptor> list = new ArrayList();
	
	public TileEntityRBMKBoiler() {
		super(0);

		feed = new FluidTank(FluidType.WATER, 10000, 0);
		steam = new FluidTank(FluidType.STEAM, 1000000, 1);
	}

	@Override
	public String getName() {
		return "container.rbmkBoiler";
	}
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			feed.updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
			steam.updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
		}
		
		super.updateEntity();
	}

	@Override
	public void fillFluidInit(FluidType type) {
		
		fillFluid(this.xCoord, this.yCoord + 5, this.zCoord, getTact(), type);
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, worldObj, type);
	}
	
	@Override
	@Deprecated //why are we still doing this?
	public boolean getTact() { return false; }

	@Override
	public void setFluidFill(int i, FluidType type) {
		
		if(type == feed.getTankType())
			feed.setFill(i);
		else if(type == steam.getTankType())
			steam.setFill(i);
	}

	@Override
	public int getFluidFill(FluidType type) {
		
		if(type == feed.getTankType())
			return feed.getFill();
		else if(type == steam.getTankType())
			return steam.getFill();
		
		return 0;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		
		if(type == feed.getTankType())
			return feed.getMaxFill();
		else if(type == steam.getTankType())
			return steam.getMaxFill();
		
		return 0;
	}

	@Override
	public void setFillstate(int fill, int index) {

		if(index == 0)
			feed.setFill(fill);
		else if(index == 1)
			steam.setFill(fill);
	}

	@Override
	public void setType(FluidType type, int index) {

		if(index == 0)
			feed.setTankType(type);
		else if(index == 1)
			steam.setTankType(type);
	}

	@Override
	public List<FluidTank> getTanks() {
		List<FluidTank> list = new ArrayList();
		list.add(feed);
		list.add(steam);
		
		return list;
	}
	
	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		return list;
	}
	
	@Override
	public void clearFluidList(FluidType type) {
		list.clear();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		feed.readFromNBT(nbt, "feed");
		steam.readFromNBT(nbt, "steam");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		feed.writeToNBT(nbt, "feed");
		steam.writeToNBT(nbt, "steam");
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return Vec3.createVectorHelper(xCoord - player.posX, yCoord - player.posY, zCoord - player.posZ).lengthVector() < 20;
	}

	@SuppressWarnings("incomplete-switch") //shut the up
	@Override
	public void receiveControl(NBTTagCompound data) {
		
		if(data.hasKey("compression")) {
			
			switch(steam.getTankType()) {
			case STEAM: steam.setTankType(FluidType.HOTSTEAM); steam.setFill(steam.getFill() / 10); break;
			case HOTSTEAM: steam.setTankType(FluidType.SUPERHOTSTEAM); steam.setFill(steam.getFill() / 10); break;
			case SUPERHOTSTEAM: steam.setTankType(FluidType.ULTRAHOTSTEAM); steam.setFill(steam.getFill() / 10); break;
			case ULTRAHOTSTEAM: steam.setTankType(FluidType.STEAM); steam.setFill(Math.min(steam.getFill() * 1000, steam.getMaxFill())); break;
			}
			
			this.markDirty();
		}
	}
}
