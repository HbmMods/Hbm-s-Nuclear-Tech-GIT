package com.hbm.tileentity.machine;

import com.hbm.config.VersatileConfig;
import com.hbm.interfaces.IConsumer;
import com.hbm.inventory.MachineRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemCapacitor;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.sound.AudioWrapper;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.energy.IBatteryItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityMachineSchrabidiumTransmutator extends TileEntityMachineBase implements IConsumer {

	public long power = 0;
	public int process = 0;
	public static final long maxPower = 5000000;
	public static final int processSpeed = 600;
	
	private AudioWrapper audio;

	private static final int[] slots_top = new int[] { 0 };
	private static final int[] slots_bottom = new int[] { 1, 2 };
	private static final int[] slots_side = new int[] { 3, 2 };

	public TileEntityMachineSchrabidiumTransmutator() {
		super(4);
	}

	@Override
	public String getName() {
		return "container.machine_schrabidium_transmutator";
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		switch (i) {
		case 0:
			if (MachineRecipes.mODE(stack, "ingotUranium"))
				return true;
			break;
		case 2:
			if (stack.getItem() == ModItems.redcoil_capacitor)
				return true;
			break;
		case 3:
			if (stack.getItem() instanceof IBatteryItem)
				return true;
			break;
		}
		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		power = nbt.getLong("power");
		process = nbt.getInteger("process");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("power", power);
		nbt.setInteger("process", process);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return p_94128_1_ == 0 ? slots_bottom : (p_94128_1_ == 1 ? slots_top : slots_side);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack stack, int j) {
		
		if (i == 2 && stack.getItem() != null && stack.getItem() == ModItems.redcoil_capacitor && ItemCapacitor.getDura(stack) <= 0) {
			return true;
		}

		if (i == 1) {
			return true;
		}

		if (i == 3) {
			if (stack.getItem() instanceof IBatteryItem && ((IBatteryItem)stack.getItem()).getCharge(stack) == 0)
				return true;
		}

		return false;
	}

	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}

	public int getProgressScaled(int i) {
		return (process * i) / processSpeed;
	}

	public boolean canProcess() {
		if (power >= 4990000 && slots[0] != null && MachineRecipes.mODE(slots[0], "ingotUranium") && slots[2] != null
				&& slots[2].getItem() == ModItems.redcoil_capacitor
				&& ItemCapacitor.getDura(slots[2]) > 0
				&& (slots[1] == null || (slots[1] != null && slots[1].getItem() == VersatileConfig.getTransmutatorItem()
						&& slots[1].stackSize < slots[1].getMaxStackSize()))) {
			return true;
		}
		return false;
	}

	public boolean isProcessing() {
		return process > 0;
	}

	public void process() {
		process++;

		if (process >= processSpeed) {

			power = 0;
			process = 0;

			slots[0].stackSize--;
			if (slots[0].stackSize <= 0) {
				slots[0] = null;
			}

			if (slots[1] == null) {
				slots[1] = new ItemStack(VersatileConfig.getTransmutatorItem());
			} else {
				slots[1].stackSize++;
			}
			if (slots[2] != null) {
				ItemCapacitor.setDura(slots[2], ItemCapacitor.getDura(slots[2]) - 1);
			}

			this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "ambient.weather.thunder", 10000.0F,
					0.8F + this.worldObj.rand.nextFloat() * 0.2F);
		}
	}

	@Override
	public void updateEntity() {

		if (!worldObj.isRemote) {
			
			power = Library.chargeTEFromItems(slots, 3, power, maxPower);

			if(canProcess()) {
				process();
			} else {
				process = 0;
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", power);
			data.setInteger("progress", process);
			this.networkPack(data, 50);
			
		} else {

			if(process > 0) {
				
				if(audio == null) {
					audio = MainRegistry.proxy.getLoopedSound("hbm:weapon.tauChargeLoop", xCoord, yCoord, zCoord, 1.0F, 1.0F);
					audio.startSound();
				}
			} else {
				
				if(audio != null) {
					audio.stopSound();
					audio = null;
				}
			}
		}
	}
	
    public void onChunkUnload() {
    	
    	if(audio != null) {
			audio.stopSound();
			audio = null;
    	}
    }
	
    public void invalidate() {
    	
    	super.invalidate();
    	
    	if(audio != null) {
			audio.stopSound();
			audio = null;
    	}
    }
	
	@Override
	public void networkUnpack(NBTTagCompound data) {

		this.power = data.getLong("power");
		this.process = data.getInteger("progress");
	}

	@Override
	public void setPower(long i) {
		power = i;
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
