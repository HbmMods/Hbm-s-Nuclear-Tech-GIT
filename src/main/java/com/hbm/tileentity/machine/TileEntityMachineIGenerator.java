package com.hbm.tileentity.machine;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.inventory.FluidTank;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.types.FluidTypeFlammable;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemRTGPellet;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.RTGUtil;

import api.hbm.energy.IEnergyGenerator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineIGenerator extends TileEntityMachineBase implements IFluidAcceptor, IEnergyGenerator {
	
	public long power;
	public static final long maxPower = 1000000;
	public int spin;
	public int[] burn = new int[4];
	public boolean hasRTG = false;
	public int[] RTGSlots = new int[]{ 11, 12, 13, 14, 15, 16, 17, 18, 19, 20 };

	@SideOnly(Side.CLIENT)
	public float rotation;
	@SideOnly(Side.CLIENT)
	public float prevRotation;
	
	public FluidTank[] tanks;
	
	public int age = 0;

	public TileEntityMachineIGenerator() {
		super(21);
		tanks = new FluidTank[3];
		tanks[0] = new FluidTank(Fluids.WATER, 16000, 0);
		tanks[1] = new FluidTank(Fluids.HEATINGOIL, 16000, 1);
		tanks[2] = new FluidTank(Fluids.LUBRICANT, 4000, 2);
	}

	@Override
	public String getName() {
		return "container.iGenerator";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			power = Library.chargeItemsFromTE(slots, 0, power, maxPower);
			
			ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
			this.sendPower(worldObj, xCoord + dir.offsetX * -4, yCoord, zCoord + dir.offsetZ * -4, dir.getOpposite());
			this.sendPower(worldObj, xCoord + dir.offsetX * 3, yCoord, zCoord + dir.offsetZ * 3, dir);
			
			tanks[1].setType(9, 10, slots);
			tanks[0].loadTank(1, 2, slots);
			tanks[1].loadTank(9, 10, slots);
			tanks[2].loadTank(7, 8, slots);
			
			this.spin = 0;
			
			/// LIQUID FUEL ///
			if(tanks[1].getFill() > 0) {
				int pow = this.getPowerFromFuel();
				
				if(pow > 0) {
					tanks[1].setFill(tanks[1].getFill() - 1);
					this.spin += pow;
				}
			}
			
			///SOLID FUEL ///
			for(int i = 0; i < 4; i++) {
				
				// POWER GEN //
				if(burn[i] > 0) {
					burn[i]--;
					this.spin += coalGenRate;
					
				// REFUELING //
				} else {
					int slot = i + 3;
					
					if(slots[slot] != null) {
						ItemStack fuel = slots[slot];
						int burnTime = TileEntityFurnace.getItemBurnTime(fuel) / 2;
						
						if(burnTime > 0) {
							
							if(fuel.getItem() == Items.coal)
								burnTime *= 1.5;
							if(fuel.getItem() == ModItems.solid_fuel)
								burnTime *= 2;
							if(fuel.getItem() == ModItems.solid_fuel_presto)
								burnTime *= 4;
							if(fuel.getItem() == ModItems.solid_fuel_presto_triplet)
								burnTime *= 10;
							
							burn[i] = burnTime;
							
							slots[slot].stackSize--;
							
							if(slots[slot].stackSize <= 0) {
								
								if(slots[slot].getItem().hasContainerItem(slots[slot])) {
									slots[slot] = slots[slot].getItem().getContainerItem(slots[slot]);
								} else {
									slots[slot] = null;
								}
							}
						}
					}
				}
			}
			
			// RTG ///
			this.hasRTG = RTGUtil.hasHeat(slots, RTGSlots);
			this.spin += RTGUtil.updateRTGs(slots, RTGSlots) * 0.2;
			
			if(this.spin > 0) {
				
				int powerGen = this.spin;
				
				if(this.tanks[0].getFill() >= 10) {
					powerGen += this.spin;
					this.tanks[0].setFill(this.tanks[0].getFill() - 10);
				}
				
				if(this.tanks[2].getFill() >= 1) {
					powerGen += this.spin * 3;
					this.tanks[2].setFill(this.tanks[2].getFill() - 1);
				}
				
				this.power += Math.pow(powerGen, 1.1D);
				
				if(this.power > this.maxPower)
					this.power = this.maxPower;
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", power);
			data.setInteger("spin", spin);
			data.setIntArray("burn", burn);
			data.setBoolean("hasRTG", hasRTG);
			this.networkPack(data, 150);
			
			for(int i = 0; i < 3; i++)
				tanks[i].updateTank(xCoord, yCoord, zCoord, this.worldObj.provider.dimensionId);
			
		} else {
			
			this.prevRotation = this.rotation;
			
			if(this.spin > 0) {
				this.rotation += 15;
			}
			
			if(this.rotation >= 360) {
				this.rotation -= 360;
				this.prevRotation -= 360;
			}
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.power = nbt.getLong("power");
		this.spin = nbt.getInteger("spin");
		this.burn = nbt.getIntArray("burn");
		this.hasRTG = nbt.getBoolean("hasRTG");
	}
	
	public static final int coalGenRate = 75;
	
	public int getPowerFromFuel() {
		FluidType type = tanks[1].getTankType();
		return type instanceof FluidTypeFlammable ? (int)(((FluidTypeFlammable) type).getHeatEnergy() / 1000L) : 0;
	}

	@Override
	public void setFillForSync(int fill, int index) {
		tanks[index].setFill(fill);
	}

	@Override
	public void setFluidFill(int fill, FluidType type) {
		
		if(type == Fluids.WATER)
			tanks[0].setFill(fill);
		else if(type == Fluids.LUBRICANT)
			tanks[2].setFill(fill);
		else if(tanks[1].getTankType() == type)
			tanks[1].setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		tanks[index].setTankType(type);
	}

	@Override
	public int getFluidFill(FluidType type) {
		
		for(int i = 0; i < 3; i++)
			if(tanks[i].getTankType() == type)
				return tanks[i].getFill();
		
		return 0;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		
		for(int i = 0; i < 3; i++)
			if(tanks[i].getTankType() == type)
				return tanks[i].getMaxFill();
		
		return 0;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		for(int i = 0; i < 3; i++)
			tanks[i].readFromNBT(nbt, "tank_" + i);
		
		this.power = nbt.getLong("power");
		this.burn = nbt.getIntArray("burn");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		for(int i = 0; i < 3; i++)
			tanks[i].writeToNBT(nbt, "tank_" + i);
		
		nbt.setLong("power", power);
		nbt.setIntArray("burn", burn);
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}

	@Override
	public long getPower() {
		return this.power;
	}

	@Override
	public long getMaxPower() {
		return this.maxPower;
	}
}
