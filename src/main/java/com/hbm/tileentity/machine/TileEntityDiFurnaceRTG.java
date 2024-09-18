package com.hbm.tileentity.machine;

import com.hbm.blocks.machine.MachineDiFurnaceRTG;
import com.hbm.inventory.container.ContainerMachineDiFurnaceRTG;
import com.hbm.inventory.gui.GUIMachineDiFurnaceRTG;
import com.hbm.inventory.recipes.BlastFurnaceRecipes;
import com.hbm.items.machine.ItemRTGPellet;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.CompatEnergyControl;
import com.hbm.util.RTGUtil;

import api.hbm.tile.IInfoProviderEC;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class TileEntityDiFurnaceRTG extends TileEntityMachineBase implements IGUIProvider, IInfoProviderEC {
	
	public short progress;
	private short processSpeed = 0;
	// Edit as needed
	private static final short timeRequired = 1200;
	private static final int[] rtgIn = new int[] {3, 4, 5, 6, 7, 8};
	private String name;
	public byte sideUpper = 1;
	public byte sideLower = 1;
	
	public TileEntityDiFurnaceRTG() {
		super(9);
	}

	public boolean canProcess() {
		if ((slots[0] == null || slots[1] == null) && !hasPower())
			return false;
		
		ItemStack recipeResult = BlastFurnaceRecipes.getOutput(slots[0], slots[1]);
		if (recipeResult == null)
			return false;
		else if (slots[2] == null)
			return true;
		else if (!slots[2].isItemEqual(recipeResult))
			return false;
		else if (slots[2].stackSize + recipeResult.stackSize > getInventoryStackLimit())
			return false;
		else if (slots[2].stackSize < getInventoryStackLimit() && slots[2].stackSize < slots[2].getMaxStackSize())
			return true;
		else
			return slots[2].stackSize < recipeResult.getMaxStackSize();
	}
	
	@Override
	public void updateEntity() {
		
		if(worldObj.isRemote)
			return;
		
		if(canProcess() && hasPower()) {
			progress += processSpeed;
			if(progress >= timeRequired) {
				processItem();
				progress = 0;
			}
		} else {
			progress = 0;
		}
		
		MachineDiFurnaceRTG.updateBlockState(isProcessing() || (canProcess() && hasPower()), getWorldObj(), xCoord, yCoord, zCoord);

		NBTTagCompound data = new NBTTagCompound();
		data.setShort("progress", progress);
		data.setShort("speed", processSpeed);
		data.setByteArray("modes", new byte[] {(byte) sideUpper, (byte) sideLower});
		networkPack(data, 10);
	}
	
	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		super.networkUnpack(nbt);
		
		progress = nbt.getShort("progress");
		processSpeed = nbt.getShort("speed");
		byte[] modes = nbt.getByteArray("modes");
		this.sideUpper = modes[0];
		this.sideLower = modes[1];
	}
	
	private void processItem() {
		
		if(canProcess()) {
			ItemStack recipeOut = BlastFurnaceRecipes.getOutput(slots[0], slots[1]);
			if(slots[2] == null)
				slots[2] = recipeOut.copy();
			else if(slots[2].isItemEqual(recipeOut))
				slots[2].stackSize += recipeOut.stackSize;

			for(int i = 0; i < 2; i++) {
				if(slots[i].stackSize <= 0)
					slots[i] = new ItemStack(slots[i].getItem().setFull3D());
				else
					slots[i].stackSize--;
				if(slots[i].stackSize <= 0)
					slots[i] = null;
			}
			markDirty();
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		progress = nbt.getShort("progress");
		processSpeed = nbt.getShort("speed");
		
		byte[] modes = nbt.getByteArray("modes");
		this.sideUpper = modes[0];
		this.sideLower = modes[1];
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setShort("progress", progress);
		nbt.setShort("speed", processSpeed);
		nbt.setByteArray("modes", new byte[] {(byte) sideUpper, (byte) sideLower});
	}

	public int getDiFurnaceProgressScaled(int i) {
		return (progress * i) / timeRequired;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack stack) {
		slots[i] = stack;
		if(stack != null && stack.stackSize > getInventoryStackLimit()) {
			stack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public void setCustomName(String name) {
		this.name = name;
	}

	public boolean hasPower() {
		processSpeed = (short) RTGUtil.updateRTGs(slots, rtgIn);
		return processSpeed >= 15;
	}

	public int getPower() {
		return processSpeed;
	}

	public boolean isProcessing() {
		return progress > 0;
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.name : "container.diFurnaceRTG";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return this.name != null && this.name.length() > 0;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		if(i == 0 && this.sideUpper != j) return false;
		if(i == 1 && this.sideLower != j) return false;
		
		return this.isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		if(i == 2) return false;
		if(stack.getItem() instanceof ItemRTGPellet) return i > 2;
		return !(stack.getItem() instanceof ItemRTGPellet);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8};
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		
		if(slot > 2) {
			return !(stack.getItem() instanceof ItemRTGPellet);
		}
		
		return slot == 2;
	}

	@Override
	public String getName() {
		return "container.diFurnaceRTG";
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineDiFurnaceRTG(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineDiFurnaceRTG(player.inventory, this);
	}

	@Override
	public void provideExtraInfo(NBTTagCompound data) {
		data.setLong(CompatEnergyControl.L_FUEL, this.getPower());
		data.setInteger(CompatEnergyControl.I_PROGRESS, this.progress);
	}
}
