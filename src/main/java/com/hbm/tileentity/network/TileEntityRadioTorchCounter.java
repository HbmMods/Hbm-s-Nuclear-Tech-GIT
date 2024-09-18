package com.hbm.tileentity.network;

import com.hbm.module.ModulePatternMatcher;
import com.hbm.tileentity.IControlReceiverFilter;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.Compat;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityRadioTorchCounter extends TileEntityMachineBase implements IControlReceiverFilter {
	
	public String[] channel;
	public int[] lastCount;
	public boolean polling = false;
	public ModulePatternMatcher matcher;

	public TileEntityRadioTorchCounter() {
		super(3);
		this.channel = new String[3];
		for(int i = 0; i < 3; i++) this.channel[i] = "";
		this.lastCount = new int[3];
		this.matcher = new ModulePatternMatcher(3);
	}

	@Override
	public String getName() {
		return "container.rttyCounter";
	}
	@Override
	public void nextMode(int i) {
		this.matcher.nextMode(worldObj, slots[i], i);
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata()).getOpposite();
			
			TileEntity tile = Compat.getTileStandard(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
			if(tile instanceof IInventory) {
				IInventory inv = (IInventory) tile;
				ItemStack[] invSlots = new ItemStack[inv.getSizeInventory()];
				for(int i = 0; i < invSlots.length; i++) invSlots[i] = inv.getStackInSlot(i);
				
				for(int i = 0; i < 3; i++) {
					if(channel[i].isEmpty()) continue;
					if(slots[i] == null) continue;
					ItemStack pattern = slots[i];
					
					int count = 0;

					for(int j = 0; j < invSlots.length; j++) {
						if(invSlots[j] != null && matcher.isValidForFilter(pattern, i, invSlots[j])) {
							count += invSlots[j].stackSize;
						}
					}
					
					if(this.polling || this.lastCount[i] != count) {
						RTTYSystem.broadcast(worldObj, this.channel[i], count);
					}
					
					this.lastCount[i] = count;
				}
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("polling", polling);
			data.setIntArray("last", lastCount);
			this.matcher.writeToNBT(data);
			for(int i = 0; i < 3; i++) if(channel[i] != null) data.setString("c" + i, channel[i]);
			this.networkPack(data, 15);
		}
	}
	
	public void networkUnpack(NBTTagCompound nbt) {
		super.networkUnpack(nbt);
		
		this.polling = nbt.getBoolean("polling");
		this.lastCount = nbt.getIntArray("last");
		this.matcher.modes = new String[this.matcher.modes.length];
		this.matcher.readFromNBT(nbt);
		for(int i = 0; i < 3; i++) this.channel[i] = nbt.getString("c" + i);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.polling = nbt.getBoolean("p");
		for(int i = 0; i < 3; i++) {
			this.channel[i] = nbt.getString("c" + i);
			this.lastCount[i] = nbt.getInteger("l" + i);
		}
		this.matcher.readFromNBT(nbt);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("p", polling);
		for(int i = 0; i < 3; i++) {
			if(channel[i] != null) nbt.setString("c" + i, channel[i]);
			nbt.setInteger("l" + i, lastCount[i]);
		}
		this.matcher.writeToNBT(nbt);
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return this.isUseableByPlayer(player);
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("polling")) {
			this.polling = !this.polling;
			this.markChanged();
		} else {
			System.out.println("guh");
			for(int i = 0; i < 3; i++) {
				this.channel[i] = data.getString("c" + i);
			}
			this.markChanged();
		}
		if(data.hasKey("slot")){
			setFilterContents(data);
		}
	}

	@Override
	public int[] getFilterSlots() {
		return new int[]{0, slots.length};
	}
}
