package com.hbm.tileentity.machine;

import java.util.List;

import com.google.common.collect.HashBiMap;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.ISource;
import com.hbm.items.ModItems;
import com.hbm.tileentity.TileEntityMachineBase;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;

public class TileEntityMachineIGenerator extends TileEntityMachineBase implements ISource {
	
	public long power;
	public static final long maxPower = 1000000;
	public int lastBurnTime;
	public int burnTime;
	public int temperature;
	public static final int maxTemperature = 1000;
	public int torque;
	public static final int maxTorque = 10000;
	
	public IGenRTG[] pellets = new IGenRTG[12];

	public TileEntityMachineIGenerator() {
		super(15);
	}

	@Override
	public String getName() {
		return "container.iGenerator";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			loadFuel();
			rtgAction();
			
			if(burnTime > 0)
				burnTime --;
			
			NBTTagCompound data = new NBTTagCompound();
			int[] rtgs = new int[pellets.length];
			
			for(int i = 0; i < pellets.length; i++) {
				if(pellets[i] != null)
					rtgs[i] = pellets[i].ordinal();
				else
					rtgs[i] = -1;
			}
			
			data.setIntArray("rtgs", rtgs);
			data.setShort("burn", (short) burnTime);
			data.setShort("lastBurn", (short) lastBurnTime);
			this.networkPack(data, 250);
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {

		int[] rtgs = nbt.getIntArray("rtgs");
		
		if(rtgs != null) {
			for(int i = 0; i < pellets.length; i++) {
				
				int pellet = rtgs[i];
				if(pellet >= 0 && pellet < IGenRTG.values().length) {
					pellets[i] = IGenRTG.values()[pellet];
				} else {
					pellets[i] = null;
				}
			}
		}

		this.burnTime = nbt.getShort("burn");
		this.lastBurnTime = nbt.getShort("lastBurn");
	}

	@Override
	public void handleButtonPacket(int value, int meta) {

		if(meta == 0)
			pushPellet();
		if(meta == 1)
			popPellet();
	}
	
	/**
	 * Checks for solid fuel and burns it
	 */
	private void loadFuel() {
		
		if(this.burnTime <= 0 && slots[0] != null) {
			
			int time = TileEntityFurnace.getItemBurnTime(slots[0]) / 2;
			
			if(time > 0) {
				
				if(slots[0].getItem().hasContainerItem(slots[0]) && slots[0].stackSize == 1) {
					slots[0] = slots[0].getItem().getContainerItem(slots[0]);
				} else {
					this.decrStackSize(0, 1);
				}
				
				this.burnTime = time;
				this.lastBurnTime = time;
				
				this.markDirty();
			}
		}
	}
	
	/**
	 * does the thing with the thermo elements
	 */
	private void rtgAction() {
		
		int rtg = 0;
		
		for(int i = 3; i <= 5; i++) {
			
			if(slots[i] != null && slots[i].getItem() == ModItems.thermo_element)
				rtg += 15;
		}
		
		int pow = Math.min(this.temperature, rtg);
		
		this.temperature -= pow;
		this.power += pow;
	}
	
	/**
	 * Adds a pellet onto the pile
	 */
	private void pushPellet() {
		
		if(pellets[11] != null)
			return;
		
		if(slots[1] != null) {
			
			IGenRTG pellet = IGenRTG.getPellet(slots[1].getItem());
			
			if(pellet != null) {
				
				for(int i = 0; i < pellets.length; i++) {
					
					if(pellets[i] == null) {
						pellets[i] = pellet;
						this.decrStackSize(1, 1);
						
						this.markDirty();
						return;
					}
				}
			}
		}
	}
	
	/**
	 * Removes a pellet from the bottom of the pile
	 */
	private void popPellet() {
		
		if(slots[2] != null)
			return;
		
		if(pellets[0] == null)
			return;
		
		//i don't feel like adding null checks because they won't trigger anyway
		slots[2] = new ItemStack(this.rtgPellets.inverse().get(pellets[0]));
		
		for(int i = 0; i < pellets.length - 1; i++) {
			pellets[i] = pellets[i + 1];
		}
		
		pellets[pellets.length - 1] = null;
		
		this.markDirty();
	}
	
	public double getSolidGauge() {
		return (double) burnTime / (double) lastBurnTime;
	}
	
	public double getPowerGauge() {
		return (double) power / (double) maxPower;
	}
	
	public double getTempGauge() {
		return (double) temperature / (double) maxTemperature;
	}
	
	public double getTorqueGauge() {
		return (double) torque / (double) maxTorque;
	}

	@Override
	public void ffgeuaInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ffgeua(int x, int y, int z, boolean newTact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getTact() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long getSPower() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setSPower(long i) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<IConsumer> getList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearList() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		for(int i = 0; i < pellets.length; i++) {
			
			short s = nbt.getShort("pellet" + i);
			
			if(s >= 0 && s < IGenRTG.values().length) {
				pellets[i] = IGenRTG.values()[s];
			} else {
				pellets[i] = null;
			}
		}

		this.burnTime = nbt.getInteger("burn");
		this.lastBurnTime = nbt.getInteger("lastBurn");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		for(int i = 0; i < pellets.length; i++) {
			
			if(pellets[i] != null) {
				nbt.setShort("pellet" + i, (short) pellets[i].ordinal());
			} else {
				nbt.setShort("pellet" + i, (short)-1);
			}
		}

		nbt.setInteger("burn", burnTime);
		nbt.setInteger("lastBurn", lastBurnTime);
	}
	
	private static HashBiMap<Item, IGenRTG> rtgPellets = HashBiMap.create();
	
	public static enum IGenRTG {
		PLUTONIUM(ModItems.pellet_rtg, 0, 5),
		URANIUM(ModItems.pellet_rtg_weak, 9, 3),
		POLONIUM(ModItems.pellet_rtg_polonium, 18, 25);
		
		public int offset;
		public int heat;
		
		private IGenRTG(Item item, int offset, int heat) {
			rtgPellets.put(item, this);
			this.offset = offset;
			this.heat = heat;
		}
		
		public static IGenRTG getPellet(Item item) {
			return rtgPellets.get(item);
		}
	}
}
