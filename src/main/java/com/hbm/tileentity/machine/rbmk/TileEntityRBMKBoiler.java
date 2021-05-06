package com.hbm.tileentity.machine.rbmk;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.projectile.EntityRBMKDebris.DebrisType;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.FluidTank;
import com.hbm.lib.Library;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole.ColumnType;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
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
			
			double heatCap = this.getHeatFromSteam(steam.getTankType());
			double heatProvided = this.heat - heatCap;
			
			if(heatProvided > 0) {
				int waterUsed = (int)Math.floor(heatProvided / RBMKDials.getBoilerHeatConsumption(worldObj));
				waterUsed = Math.min(waterUsed, feed.getFill());
				feed.setFill(feed.getFill() - waterUsed);
				int steamProduced = (int)Math.floor((waterUsed * 100) / getFactorFromSteam(steam.getTankType()));
				steam.setFill(steam.getFill() + steamProduced);
				
				if(steam.getFill() > steam.getMaxFill()) {
					steam.setFill(steam.getMaxFill());
				}
			}
			
			fillFluidInit(steam.getTankType());
		}
		
		super.updateEntity();
	}
	
	public double getHeatFromSteam(FluidType type) {
		
		switch(type) {
		case STEAM: return 100D;
		case HOTSTEAM: return 300D;
		case SUPERHOTSTEAM: return 450D;
		case ULTRAHOTSTEAM: return 600D;
		default: return 0D;
		}
	}
	
	public double getFactorFromSteam(FluidType type) {
		
		switch(type) {
		case STEAM: return 1D;
		case HOTSTEAM: return 10D;
		case SUPERHOTSTEAM: return 100D;
		case ULTRAHOTSTEAM: return 1000D;
		default: return 0D;
		}
	}

	@Override
	public void fillFluidInit(FluidType type) {
		
		fillFluid(this.xCoord, this.yCoord + RBMKDials.getColumnHeight(worldObj) + 1, this.zCoord, getTact(), type);
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
	
	@Override
	public void onMelt(int reduce) {

		reduce = MathHelper.clamp_int(reduce, 1, 3);
		
		if(worldObj.rand.nextInt(3) == 0)
			reduce++;
		
		for(int i = 3; i >= 0; i--) {
			
			if(i <= 4 - reduce) {
				
				if(reduce > 1 && i == 4 - reduce) {
					
					//TODO: steam explosions
					worldObj.setBlock(xCoord, yCoord + i, zCoord, ModBlocks.pribris_burning);
					
				} else {
					worldObj.setBlock(xCoord, yCoord + i, zCoord, ModBlocks.pribris);
				}
				
			} else {
				worldObj.setBlock(xCoord, yCoord + i, zCoord, Blocks.air);
			}
			worldObj.markBlockForUpdate(xCoord, yCoord + i, zCoord);
		}
		
		int count = 1 + worldObj.rand.nextInt(2);
		
		for(int i = 0; i < count; i++) {
			spawnDebris(DebrisType.BLANK);
		}
		
		super.onMelt(reduce);
	}

	@Override
	public ColumnType getConsoleType() {
		return ColumnType.BOILER;
	}
}
