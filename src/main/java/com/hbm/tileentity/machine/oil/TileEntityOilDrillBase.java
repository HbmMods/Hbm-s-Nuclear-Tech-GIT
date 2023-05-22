package com.hbm.tileentity.machine.oil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.EconomyPlus.compatibility.xradar.dataStructures.TerritoryCluster;
import com.EconomyPlus.compatibility.xradar.dataStructures.TerritoryCluster.ProvinceHandler;
import com.EconomyPlus.compatibility.xradar.nodes.OilResource;
import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.UpgradeManager;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.lib.Library;
import com.hbm.tileentity.IConfigurableMachine;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.BobMathUtil;
import com.hbm.util.Tuple;
import com.hbm.util.Tuple.Triplet;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energy.IEnergyUser;
import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileEntityOilDrillBase extends TileEntityMachineBase implements IEnergyUser, IFluidSource, IFluidStandardTransceiver, IConfigurableMachine {
	
	public int indicator = 0;
	
	public long power;
	
	public List<IFluidAcceptor> list1 = new ArrayList();
	public List<IFluidAcceptor> list2 = new ArrayList();
	public FluidTank[] tanks;

	public TileEntityOilDrillBase() {
		super(8);
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(Fluids.OIL, 64_000, 0);
		tanks[1] = new FluidTank(Fluids.GAS, 64_000, 1);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		for(int i = 0; i < this.tanks.length; i++)
			this.tanks[i].readFromNBT(nbt, "t" + i);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		for(int i = 0; i < this.tanks.length; i++)
			this.tanks[i].writeToNBT(nbt, "t" + i);
	}

	public int speedLevel;
	public int energyLevel;
	public int overLevel;

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			this.updateConnections();
			
			this.tanks[0].unloadTank(1, 2, slots);
			this.tanks[1].unloadTank(3, 4, slots);
			
			UpgradeManager.eval(slots, 5, 7);
			this.speedLevel = Math.min(UpgradeManager.getLevel(UpgradeType.SPEED), 3);
			this.energyLevel = Math.min(UpgradeManager.getLevel(UpgradeType.POWER), 3);
			this.overLevel = Math.min(UpgradeManager.getLevel(UpgradeType.OVERDRIVE), 3) + 1;
			int abLevel = Math.min(UpgradeManager.getLevel(UpgradeType.AFTERBURN), 3);
			
			for(int i = 0; i < tanks.length; i++) {
				tanks[i].updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
			}
			
			int toBurn = Math.min(tanks[1].getFill(), abLevel * 10);
			
			if(toBurn > 0) {
				tanks[1].setFill(tanks[1].getFill() - toBurn);
				this.power += toBurn * 5;
				
				if(this.power > this.getMaxPower())
					this.power = this.getMaxPower();
			}
			
			power = Library.chargeTEFromItems(slots, 0, power, this.getMaxPower());

			if(this.worldObj.getTotalWorldTime() % 10 == 0)
				this.fillFluidInit(tanks[0].getTankType());
			if(this.worldObj.getTotalWorldTime() % 10 == 5)
				this.fillFluidInit(tanks[1].getTankType());
			
			for(DirPos pos : getConPos()) {
				if(tanks[0].getFill() > 0) this.sendFluid(tanks[0].getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				if(tanks[1].getFill() > 0) this.sendFluid(tanks[1].getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			}
			
			if(this.power >= this.getPowerReqEff() && this.tanks[0].getFill() < this.tanks[0].getMaxFill() && this.tanks[1].getFill() < this.tanks[1].getMaxFill()) {
				
				this.power -= this.getPowerReqEff();
				
				if(worldObj.getTotalWorldTime() % getDelayEff() == 0) {
					this.indicator = 0;
					if(canPump())
						onSuck();
				}
				
			} else {
				this.indicator = 2;
			}
			this.sendUpdate();
		}	
	}
	
	public void sendUpdate() {
		NBTTagCompound data = new NBTTagCompound();
		data.setLong("power", power);
		data.setInteger("indicator", this.indicator);
		this.networkPack(data, 25);
	}
	
	public void networkUnpack(NBTTagCompound nbt) {
		this.power = nbt.getLong("power");
		this.indicator = nbt.getInteger("indicator");
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack stack) {
		super.setInventorySlotContents(i, stack);
		
		if(stack != null && i >= 5 && i <= 7 && stack.getItem() instanceof ItemMachineUpgrade)
			worldObj.playSoundEffect(xCoord + 0.5, yCoord + 1.5, zCoord + 0.5, "hbm:item.upgradePlug", 1.0F, 1.0F);
	}
	
	public int getPowerReqEff() {
		int req = this.getPowerReq();
		return (req + (req / 4 * this.speedLevel) - (req / 4 * this.energyLevel)) * this.overLevel;
	}
	
	public int getDelayEff() {
		int delay = getDelay();
		return Math.max((delay - (delay / 4 * this.speedLevel) + (delay / 10 * this.energyLevel) / this.overLevel), 1);
	}
	
	public boolean canPump() {
		ProvinceHandler p = TerritoryCluster.instance().getProvinceFromBlockCoords(xCoord, zCoord, worldObj.provider.dimensionId);
		if(p.exists() && p.hasResource() && p.getResource() instanceof OilResource)
			return true;
		this.indicator = 1;
		return false;
	}
	public abstract int getPowerReq();
	public abstract int getDelay();
	
	protected HashSet<Tuple.Triplet<Integer, Integer, Integer>> trace = new HashSet();
	
	public abstract void onSuck();

	@Override
	public boolean getTact() {
		return this.worldObj.getTotalWorldTime() % 20 < 10;
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, worldObj, type);
	}

	@Override
	public int getFluidFill(FluidType type) {
		for(FluidTank tank : tanks) {
			if(type == tank.getTankType()) {
				return tank.getFill();
			}
		}
		
		return 0;
	}

	@Override
	public void setFluidFill(int i, FluidType type) {
		for(FluidTank tank : tanks) {
			if(type == tank.getTankType()) {
				tank.setFill(i);
				return;
			}
		}
	}

	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		if(type == tanks[0].getTankType()) return this.list1;
		if(type == tanks[1].getTankType()) return this.list2;
		return new ArrayList<IFluidAcceptor>();
	}

	@Override
	public void clearFluidList(FluidType type) {
		if(type == tanks[0].getTankType()) list1.clear();
		if(type == tanks[1].getTankType()) list2.clear();
	}

	@Override
	public void setFillForSync(int fill, int index) {
		if(index < tanks.length && tanks[index] != null)
			tanks[index].setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		if(index < tanks.length && tanks[index] != null)
			tanks[index].setTankType(type);
	}

	@Override
	public void setPower(long i) {
		this.power = i;
	}

	@Override
	public long getPower() {
		return this.power;
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

	@Override
	public FluidTank[] getSendingTanks() {
		return tanks;
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[0];
	}

	@Override
	public FluidTank[] getAllTanks() {
		return tanks;
	}
	
	public abstract DirPos[] getConPos();

	protected void updateConnections() {
		for(DirPos pos : getConPos()) {
			this.trySubscribe(worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}
}
