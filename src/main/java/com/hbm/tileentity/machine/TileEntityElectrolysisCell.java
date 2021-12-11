package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.FluidTank;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityElectrolysisCell extends TileEntityMachineBase implements IConsumer, IFluidAcceptor, IFluidSource {
	
	public long power;
	public static final long maxPower = 10000000;
	public static final int powerReq = 500;
	
	public FluidTank[] tanks;
	public int age = 0;
	public List<IFluidAcceptor> list = new ArrayList();
	
	public int meltingProgress;
	public static final int meltingDuration = 100;
	
	public int hydroProgress;
	public static final int hydroDuration = 20;
	
	public boolean casting = false;
	
	public int niterTank;
	public static final int maxNiter = 16000;
	
	public static final int niterReq = 500;
	public static final int acidReq = 1000;
	
	public int primaryCastingTank;
	public int secondaryCastingTank;
	

	public TileEntityElectrolysisCell() {
		super(18);
		tanks = new FluidTank[4];
		tanks[0] = new FluidTank(FluidType.WATER, 32000, 0);
		tanks[1] = new FluidTank(FluidType.HYDROGEN, 16000, 1);
		tanks[2] = new FluidTank(FluidType.OXYGEN, 16000, 2);
		
		tanks[3] = new FluidTank(FluidType.HIACID, 8000, 3);
	}

	@Override
	public String getName() {
		return "container.machineElectrolysisCell";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			this.power = Library.chargeTEFromItems(slots, 0, power, maxPower);
			tanks[0].loadTank(2, 3, slots);
			tanks[3].loadTank(7, 8, slots);
			
			tanks[0].setType(2, 3, slots);
			updateTanks();
			
			processWater();
			fillNiter();
			
			for(int i = 0; i < tanks.length; i++)
				tanks[i].updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", power);
			data.setBoolean("casting", casting);
			data.setInteger("hydroProgress", hydroProgress);
			data.setInteger("niterTank", niterTank);
			this.networkPack(data, 25);
		}
		
	}
	
	public void updateTanks() {
		if(tanks[0].getTankType() == FluidType.WATER) {
			tanks[1].setTankType(FluidType.HYDROGEN);
			tanks[2].setTankType(FluidType.OXYGEN);
			
		} else if(tanks[0].getTankType() == FluidType.HEAVYWATER) {
			tanks[1].setTankType(FluidType.DEUTERIUM);
			tanks[2].setTankType(FluidType.OXYGEN);
		} else {
			tanks[1].setTankType(FluidType.NONE);
			tanks[2].setTankType(FluidType.NONE);
		}
	}
	
	public void fillNiter() {
		if(slots[9] != null) {
			if(slots[9].getItem() == ModItems.niter && maxNiter - niterTank >= 100) {
				niterTank += 100;
				System.out.println(niterTank);
				//slots[9] = slots[9].splitStack(slots[9].stackSize - 1);
				this.decrStackSize(9, 1);
			}
		}
		if(niterTank < 0) {
			niterTank = 0;
		} else if (niterTank > maxNiter) {
			niterTank = maxNiter;
		}
	}
	
	public boolean canProcessWater() {
		if((!this.casting && tanks[0].getFill() >=  10000) && (tanks[0].getTankType() == FluidType.WATER || tanks[0].getTankType() == FluidType.HEAVYWATER)) {
			return true;
		}
		return false;
	}
	
	public void processWater() {
			int maxConv = 1000;
			
			int convert = tanks[0].getFill();
			int outTank = Math.max(tanks[1].getFill(), tanks[2].getFill());
			convert = Math.min(convert, (tanks[1].getMaxFill() - outTank));
			convert = Math.min(convert, maxConv);
			convert = (int) Math.min(convert, power / powerReq);
			convert = Math.max(0, convert);
			
			if(canProcessWater() && convert > 0) {
				if(hydroProgress >= hydroDuration) {
					tanks[1].setFill(tanks[1].getFill() + convert);
					tanks[2].setFill(tanks[2].getFill() + convert);
					
					tanks[0].setFill(tanks[0].getFill() - convert * 10);
					power -= convert * powerReq;
					
					this.markDirty();
					hydroProgress = 0;
				}
				hydroProgress++;
			} else {
				hydroProgress = 0;
			}
			
			
	}
	
public float getFreeChance() {
		
		float chance = 0.0F;
		
		for(int i = 5; i <= 6; i++) {

			if(slots[i] != null && slots[i].getItem() == ModItems.upgrade_effect_1)
				chance += 0.05F;
			if(slots[i] != null && slots[i].getItem() == ModItems.upgrade_effect_2)
				chance += 0.1F;
			if(slots[i] != null && slots[i].getItem() == ModItems.upgrade_effect_3)
				chance += 0.15F;
		}
		
		return Math.min(chance, 0.15F);
	}
	
	public int getDuration() {
		
		float durationMod = 1;
		
		for(int i = 5; i <= 6; i++) {

			if(slots[i] != null && slots[i].getItem() == ModItems.upgrade_speed_1)
				durationMod -= 0.25F;
			if(slots[i] != null && slots[i].getItem() == ModItems.upgrade_speed_2)
				durationMod -= 0.50F;
			if(slots[i] != null && slots[i].getItem() == ModItems.upgrade_speed_3)
				durationMod -= 0.75F;
		}
		int duration = casting ? meltingDuration : hydroDuration;
		return (int) (duration * Math.max(durationMod, 0.25F));
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
	
	public void networkUnpack(NBTTagCompound data) {
		this.power = data.getLong("power");
		this.casting = data.getBoolean("casting");
		this.hydroProgress = data.getInteger("hydroProgress");
		this.niterTank = data.getInteger("niterTank");
	}

	@Override
	public void setFillstate(int fill, int index) {
		if (index < 4 && tanks[index] != null)
			tanks[index].setFill(fill);
		
	}

	@Override
	public void setFluidFill(int fill, FluidType type) {
		for(int i = 0; i < tanks.length; i++) {
			if (type.name().equals(tanks[i].getTankType().name()))
				tanks[i].setFill(i);
		}
	}

	@Override
	public void setType(FluidType type, int index) {
		if (index < 4 && tanks[index] != null)
			tanks[index].setTankType(type);
		
	}

	@Override
	public List<FluidTank> getTanks() {
		List<FluidTank> list = new ArrayList();
		for(int i = 0; i < tanks.length; i++) {
			list.add(tanks[i]);
		}
		
		return list;
	}

	@Override
	public int getFluidFill(FluidType type) {
		for(int i = 0; i < tanks.length; i++) {
			if (type.name().equals(tanks[i].getTankType().name()))
				return tanks[i].getFill();
		}
		return 0;
	}
	
	@Override
	public void handleButtonPacket(int value, int meta) {
		if(meta == 0) {
			this.casting = !this.casting;
		}
	}	

	@Override
	public void fillFluidInit(FluidType type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, worldObj, type);
		
	}

	@Override
	public boolean getTact() {
		if (age >= 0 && age < 10) {
			return true;
		}

		return false;
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
	public int getMaxFluidFill(FluidType type) {
		for(int i = 0; i < tanks.length; i++) {
			if(tanks[i].getTankType().getName() == type.getName())
				return tanks[i].getMaxFill();
		}
		return 0;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.power = nbt.getLong("power");
		this.casting = nbt.getBoolean("casting");
		this.hydroProgress = nbt.getInteger("hydroProgress");
		this.niterTank = nbt.getInteger("niterTank");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setLong("power", power);
		nbt.setBoolean("casting", casting);
		nbt.setInteger("hydroProgress", hydroProgress);
		nbt.setInteger("niterTank", niterTank);
	}
	
	
	public long getPowerScaled(long i) {
		return (this.power * i) / maxPower;
	}

	public long getHydroProgressScaled(long i) {
		return (hydroProgress * i) / hydroDuration;
	}
	
	public long getMeltingProgressScaled(long i) {
		return (meltingProgress *i) / meltingDuration;
	}

	@Override
	public void setPower(long i) {
		this.power = i;
		
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public long getMaxPower() {
		return maxPower;
	}

}
