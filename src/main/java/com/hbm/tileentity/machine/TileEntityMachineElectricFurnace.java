package com.hbm.tileentity.machine;

import com.hbm.blocks.machine.MachineElectricFurnace;
import com.hbm.inventory.UpgradeManager;
import com.hbm.inventory.container.ContainerElectricFurnace;
import com.hbm.inventory.gui.GUIMachineElectricFurnace;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.lib.Library;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.energy.IBatteryItem;
import api.hbm.energy.IEnergyUser;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineElectricFurnace extends TileEntityMachineBase implements ISidedInventory, IEnergyUser, IGUIProvider {

	// HOLY FUCKING SHIT I SPENT 5 DAYS ON THIS SHITFUCK CLASS FILE
	// thanks Martin, vaer and Bob for the help
	public int progress;
	public long power;
	public static final long maxPower = 100000;
	public int maxProgress = 100;
	public int consumption = 50;
	private int cooldown = 0;

	private static final int[] slots_io = new int[] { 0, 1, 2 };

	public TileEntityMachineElectricFurnace() {
		super(4);
	}

	@Override
	public String getName() {
		return "container.electricFurnace";
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		if(i == 0) {
			return itemStack.getItem() instanceof IBatteryItem;
		}

		if(i == 1) {
			return FurnaceRecipes.smelting().getSmeltingResult(itemStack) != null;
		}

		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.power = nbt.getLong("power");
		this.progress = nbt.getInteger("progress");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("power", power);
		nbt.setInteger("progress", progress);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return slots_io;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		if(i == 0)
			if(itemStack.getItem() instanceof IBatteryItem && ((IBatteryItem) itemStack.getItem()).getCharge(itemStack) == 0)
				return true;
		if(i == 2)
			return true;

		return false;
	}

	public int getProgressScaled(int i) {
		return (progress * i) / maxProgress;
	}

	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}

	public boolean hasPower() {
		return power >= consumption;
	}

	public boolean isProcessing() {
		return this.progress > 0;
	}

	public boolean canProcess() {
		
		if(slots[1] == null || cooldown > 0) {
			return false;
		}
		ItemStack itemStack = FurnaceRecipes.smelting().getSmeltingResult(this.slots[1]);

		if(itemStack == null) {
			return false;
		}

		if(slots[2] == null) {
			return true;
		}

		if(!slots[2].isItemEqual(itemStack)) {
			return false;
		}

		if(slots[2].stackSize < getInventoryStackLimit() && slots[2].stackSize < slots[2].getMaxStackSize()) {
			return true;
		} else {
			return slots[2].stackSize < itemStack.getMaxStackSize();
		}
	}

	private void processItem() {
		if(canProcess()) {
			ItemStack itemStack = FurnaceRecipes.smelting().getSmeltingResult(this.slots[1]);

			if(slots[2] == null) {
				slots[2] = itemStack.copy();
			} else if(slots[2].isItemEqual(itemStack)) {
				slots[2].stackSize += itemStack.stackSize;
			}

			for(int i = 1; i < 2; i++) {
				if(slots[i].stackSize <= 0) {
					slots[i] = new ItemStack(slots[i].getItem().setFull3D());
				} else {
					slots[i].stackSize--;
				}
				if(slots[i].stackSize <= 0) {
					slots[i] = null;
				}
			}
		}
	}

	@Override
	public void updateEntity() {
		boolean markDirty = false;

		if(!worldObj.isRemote) {
			
			if(cooldown > 0) {
				cooldown--;
			}

			power = Library.chargeTEFromItems(slots, 0, power, maxPower);

			if(worldObj.getTotalWorldTime() % 40 == 0) this.updateConnections();

			this.consumption = 50;
			this.maxProgress = 100;

			UpgradeManager.eval(slots, 3, 3);

			int speedLevel = UpgradeManager.getLevel(UpgradeType.SPEED);
			int powerLevel = UpgradeManager.getLevel(UpgradeType.POWER);

			maxProgress -= speedLevel * 25;
			consumption += speedLevel * 50;
			maxProgress += powerLevel * 10;
			consumption -= powerLevel * 15;
			
			if(!hasPower()) {
				cooldown = 20;
			}

			if(hasPower() && canProcess()) {
				progress++;

				power -= consumption;

				if(this.progress >= maxProgress) {
					this.progress = 0;
					this.processItem();
					markDirty = true;
				}
			} else {
				progress = 0;
			}

			boolean trigger = true;

			if(hasPower() && canProcess() && this.progress == 0) {
				trigger = false;
			}

			if(trigger) {
				markDirty = true;
				MachineElectricFurnace.updateBlockState(this.progress > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			}

			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", this.power);
			data.setInteger("MaxProgress", this.maxProgress);
			data.setInteger("progress", this.progress);
			this.networkPack(data, 50);


			if(markDirty) {
				this.markDirty();
			}
		}
	}

	private void updateConnections() {

		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
			this.trySubscribe(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir);
	}

	public void networkUnpack(NBTTagCompound nbt) {
		this.power = nbt.getLong("power");
		this.maxProgress = nbt.getInteger("MaxProgress");
		this.progress = nbt.getInteger("progress");

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

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerElectricFurnace(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineElectricFurnace(player.inventory, this);
	}
}