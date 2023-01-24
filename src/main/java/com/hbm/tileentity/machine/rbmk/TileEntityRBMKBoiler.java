package com.hbm.tileentity.machine.rbmk;

import java.util.ArrayList;
import java.util.List;

import api.hbm.fluid.IFluidStandardTransceiver;
import api.hbm.fluid.IFluidUser;
import api.hbm.fluid.IPipeNet;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.projectile.EntityRBMKDebris.DebrisType;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.lib.Library;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole.ColumnType;
import com.hbm.util.fauxpointtwelve.DirPos;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;

public class TileEntityRBMKBoiler extends TileEntityRBMKSlottedBase implements IFluidAcceptor, IFluidSource, IControlReceiver, IFluidStandardTransceiver {
	
	public FluidTank feed;
	public FluidTank steam;
	public List<IFluidAcceptor> list = new ArrayList();
	
	public TileEntityRBMKBoiler() {
		super(0);

		feed = new FluidTank(Fluids.WATER, 10000, 0);
		steam = new FluidTank(Fluids.STEAM, 1000000, 1);
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
				double HEAT_PER_MB_WATER = RBMKDials.getBoilerHeatConsumption(worldObj);
				double steamFactor = getFactorFromSteam(steam.getTankType());
				int waterUsed;
				int steamProduced;
				
				if(steam.getTankType() == Fluids.ULTRAHOTSTEAM) {
					steamProduced = (int)Math.floor((heatProvided / HEAT_PER_MB_WATER) * 100D / steamFactor);
					waterUsed = (int)Math.floor(steamProduced / 100D * steamFactor);
					
					if(feed.getFill() < waterUsed) {
						steamProduced = (int)Math.floor(feed.getFill() * 100D / steamFactor);
						waterUsed = (int)Math.floor(steamProduced / 100D * steamFactor);
					}
				} else {
					waterUsed = (int)Math.floor(heatProvided / HEAT_PER_MB_WATER);
					waterUsed = Math.min(waterUsed, feed.getFill());
					steamProduced = (int)Math.floor((waterUsed * 100D) / steamFactor);
				}
				
				feed.setFill(feed.getFill() - waterUsed);
				steam.setFill(steam.getFill() + steamProduced);
				
				if(steam.getFill() > steam.getMaxFill())
					steam.setFill(steam.getMaxFill());
				
				this.heat -= waterUsed * HEAT_PER_MB_WATER;
			}
			
			fillFluidInit(steam.getTankType());
			
			this.trySubscribe(feed.getTankType(), worldObj, xCoord, yCoord - 1, zCoord, Library.NEG_Y);
			for(DirPos pos : getOutputPos()) {
				if(this.steam.getFill() > 0) this.sendFluid(steam.getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			}
		}
		
		super.updateEntity();
	}
	
	public static double getHeatFromSteam(FluidType type) {
		if(type == Fluids.STEAM) return 100D;
		if(type == Fluids.HOTSTEAM) return 300D;
		if(type == Fluids.SUPERHOTSTEAM) return 450D;
		if(type == Fluids.ULTRAHOTSTEAM) return 600D;
		return 0D;
	}
	
	public static double getFactorFromSteam(FluidType type) {
		if(type == Fluids.STEAM) return 1D;
		if(type == Fluids.HOTSTEAM) return 10D;
		if(type == Fluids.SUPERHOTSTEAM) return 100D;
		if(type == Fluids.ULTRAHOTSTEAM) return 1000D;
		return 0D;
	}

	@Override
	public void fillFluidInit(FluidType type) {

		fillFluid(this.xCoord, this.yCoord + RBMKDials.getColumnHeight(worldObj) + 1, this.zCoord, getTact(), type);
		
		if(worldObj.getBlock(xCoord, yCoord - 1, zCoord) == ModBlocks.rbmk_loader) {

			fillFluid(this.xCoord + 1, this.yCoord - 1, this.zCoord, getTact(), type);
			fillFluid(this.xCoord - 1, this.yCoord - 1, this.zCoord, getTact(), type);
			fillFluid(this.xCoord, this.yCoord - 1, this.zCoord + 1, getTact(), type);
			fillFluid(this.xCoord, this.yCoord - 1, this.zCoord - 1, getTact(), type);
			fillFluid(this.xCoord, this.yCoord - 2, this.zCoord, getTact(), type);
		}
		
		if(worldObj.getBlock(xCoord, yCoord - 2, zCoord) == ModBlocks.rbmk_loader) {

			fillFluid(this.xCoord + 1, this.yCoord - 2, this.zCoord, getTact(), type);
			fillFluid(this.xCoord - 1, this.yCoord - 2, this.zCoord, getTact(), type);
			fillFluid(this.xCoord, this.yCoord - 2, this.zCoord + 1, getTact(), type);
			fillFluid(this.xCoord, this.yCoord - 2, this.zCoord - 1, getTact(), type);
			fillFluid(this.xCoord, this.yCoord - 1, this.zCoord, getTact(), type);
			fillFluid(this.xCoord, this.yCoord - 3, this.zCoord, getTact(), type);
		}
	}
	
	protected DirPos[] getOutputPos() {
		
		if(worldObj.getBlock(xCoord, yCoord - 1, zCoord) == ModBlocks.rbmk_loader) {
			return new DirPos[] {
					new DirPos(this.xCoord, this.yCoord + RBMKDials.getColumnHeight(worldObj) + 1, this.zCoord, Library.POS_Y),
					new DirPos(this.xCoord + 1, this.yCoord - 1, this.zCoord, Library.POS_X),
					new DirPos(this.xCoord - 1, this.yCoord - 1, this.zCoord, Library.NEG_X),
					new DirPos(this.xCoord, this.yCoord - 1, this.zCoord + 1, Library.POS_Z),
					new DirPos(this.xCoord, this.yCoord - 1, this.zCoord - 1, Library.NEG_Z),
					new DirPos(this.xCoord, this.yCoord - 2, this.zCoord, Library.NEG_Y)
			};
		} else if(worldObj.getBlock(xCoord, yCoord - 2, zCoord) == ModBlocks.rbmk_loader) {
			return new DirPos[] {
					new DirPos(this.xCoord, this.yCoord + RBMKDials.getColumnHeight(worldObj) + 1, this.zCoord, Library.POS_Y),
					new DirPos(this.xCoord + 1, this.yCoord - 2, this.zCoord, Library.POS_X),
					new DirPos(this.xCoord - 1, this.yCoord - 2, this.zCoord, Library.NEG_X),
					new DirPos(this.xCoord, this.yCoord - 2, this.zCoord + 1, Library.POS_Z),
					new DirPos(this.xCoord, this.yCoord - 2, this.zCoord - 1, Library.NEG_Z),
					new DirPos(this.xCoord, this.yCoord - 3, this.zCoord, Library.NEG_Y)
			};
		} else {
			return new DirPos[] {
					new DirPos(this.xCoord, this.yCoord + RBMKDials.getColumnHeight(worldObj) + 1, this.zCoord, Library.POS_Y)
			};
		}
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, worldObj, type);
	}
	
	@Override
	@Deprecated //why are we still doing this?
	public boolean getTact() { return worldObj.getTotalWorldTime() % 2 == 0; }

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
	public void setFillForSync(int fill, int index) {

		if(index == 0)
			feed.setFill(fill);
		else if(index == 1)
			steam.setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {

		if(index == 0)
			feed.setTankType(type);
		else if(index == 1)
			steam.setTankType(type);
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

	@Override
	public void receiveControl(NBTTagCompound data) {
		
		if(data.hasKey("compression")) {
			
			FluidType type = steam.getTankType();
			if(type == Fluids.STEAM) {			steam.setTankType(Fluids.HOTSTEAM);			steam.setFill(steam.getFill() / 10); }
			if(type == Fluids.HOTSTEAM) {		steam.setTankType(Fluids.SUPERHOTSTEAM);	steam.setFill(steam.getFill() / 10); }
			if(type == Fluids.SUPERHOTSTEAM) {	steam.setTankType(Fluids.ULTRAHOTSTEAM);	steam.setFill(steam.getFill() / 10); }
			if(type == Fluids.ULTRAHOTSTEAM) {	steam.setTankType(Fluids.STEAM);			steam.setFill(Math.min(steam.getFill() * 1000, steam.getMaxFill())); }
			
			this.markDirty();
		}
	}
	
	@Override
	public void onMelt(int reduce) {
		
		int count = 1 + worldObj.rand.nextInt(2);
		
		for(int i = 0; i < count; i++) {
			spawnDebris(DebrisType.BLANK);
		}
		
		if(RBMKDials.getOverpressure(worldObj)) {
			for(DirPos pos : getOutputPos()) {
				IPipeNet net = IFluidUser.getPipeNet(worldObj, pos.getX(), pos.getY(), pos.getZ(), steam.getTankType());
				if(net != null) {
					this.pipes.add(net);
				}
			}
		}
		
		super.onMelt(reduce);
	}

	@Override
	public ColumnType getConsoleType() {
		return ColumnType.BOILER;
	}

	@Override
	public NBTTagCompound getNBTForConsole() {
		NBTTagCompound data = new NBTTagCompound();
		data.setInteger("water", this.feed.getFill());
		data.setInteger("maxWater", this.feed.getMaxFill());
		data.setInteger("steam", this.steam.getFill());
		data.setInteger("maxSteam", this.steam.getMaxFill());
		data.setShort("type", (short)this.steam.getTankType().getID());
		return data;
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] {feed, steam};
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] {steam};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {feed};
	}
}
