package com.hbm.tileentity.machine;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.handler.FluidTypeHandler.FluidTypeTheOldOne;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.inventory.FluidTank;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemRTGPellet;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;

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

	@SideOnly(Side.CLIENT)
	public float rotation;
	@SideOnly(Side.CLIENT)
	public float prevRotation;
	
	public FluidTank[] tanks;
	
	public int age = 0;

	public TileEntityMachineIGenerator() {
		super(21);
		tanks = new FluidTank[3];
		tanks[0] = new FluidTank(FluidTypeTheOldOne.WATER, 16000, 0);
		tanks[1] = new FluidTank(FluidTypeTheOldOne.HEATINGOIL, 16000, 1);
		tanks[2] = new FluidTank(FluidTypeTheOldOne.LUBRICANT, 4000, 2);
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
			this.hasRTG = false;
			for(int i = 11; i < 21; i++) {
				if(slots[i] != null && slots[i].getItem() instanceof ItemRTGPellet) {
					ItemRTGPellet pellet = (ItemRTGPellet) slots[i].getItem();
					this.spin += pellet.getHeat() * 10;
					this.hasRTG = true;
					
					if(slots[i].getItem() == ModItems.pellet_rtg_gold || slots[i].getItem() == ModItems.pellet_rtg_lead) {
						if(worldObj.rand.nextInt(60*60*20) == 0)
							slots[i] = null;
					}
				}
			}
			
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
				
				this.power += Math.pow(powerGen, 1.15D);
				
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
	
	public static final int coalGenRate = 50;
	public static final HashMap<FluidTypeTheOldOne, Integer> fuels = new HashMap();
	
	static {
		fuels.put(FluidTypeTheOldOne.SMEAR,		50);
		fuels.put(FluidTypeTheOldOne.HEATINGOIL,	75);
		fuels.put(FluidTypeTheOldOne.HYDROGEN,	5);
		fuels.put(FluidTypeTheOldOne.DIESEL,		225);
		fuels.put(FluidTypeTheOldOne.KEROSENE,	300);
		fuels.put(FluidTypeTheOldOne.RECLAIMED,	100);
		fuels.put(FluidTypeTheOldOne.PETROIL,	125);
		fuels.put(FluidTypeTheOldOne.BIOFUEL,	200);
		fuels.put(FluidTypeTheOldOne.GASOLINE,	700);
		fuels.put(FluidTypeTheOldOne.NITAN,		2500);
		fuels.put(FluidTypeTheOldOne.LPG,		200);
		fuels.put(FluidTypeTheOldOne.ETHANOL,	75);
	}
	
	public int getPowerFromFuel() {
		FluidTypeTheOldOne type = tanks[1].getTankType();
		Integer value = fuels.get(type);
		return value != null ? value : 0;
	}

	@Override
	public void setFillstate(int fill, int index) {
		tanks[index].setFill(fill);
	}

	@Override
	public void setFluidFill(int fill, FluidTypeTheOldOne type) {
		
		if(type == FluidTypeTheOldOne.WATER)
			tanks[0].setFill(fill);
		else if(type == FluidTypeTheOldOne.LUBRICANT)
			tanks[2].setFill(fill);
		else if(tanks[1].getTankType() == type)
			tanks[1].setFill(fill);
	}

	@Override
	public void setType(FluidTypeTheOldOne type, int index) {
		tanks[index].setTankType(type);
	}

	@Override
	public List<FluidTank> getTanks() {
		return Arrays.asList(tanks);
	}

	@Override
	public int getFluidFill(FluidTypeTheOldOne type) {
		
		for(int i = 0; i < 3; i++)
			if(tanks[i].getTankType() == type)
				return tanks[i].getFill();
		
		return 0;
	}

	@Override
	public int getMaxFluidFill(FluidTypeTheOldOne type) {
		
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
