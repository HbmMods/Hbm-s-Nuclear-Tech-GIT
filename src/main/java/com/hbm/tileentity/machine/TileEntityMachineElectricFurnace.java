package com.hbm.tileentity.machine;

import java.io.IOException;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.MachineElectricFurnace;
import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;
import com.hbm.inventory.UpgradeManager;
import com.hbm.inventory.container.ContainerElectricFurnace;
import com.hbm.inventory.gui.GUIMachineElectricFurnace;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.lib.Library;
import com.hbm.tileentity.IConfigurableMachine;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.IUpgradeInfoProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.I18nUtil;

import api.hbm.energymk2.IBatteryItem;
import api.hbm.energymk2.IEnergyReceiverMK2;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineElectricFurnace extends TileEntityMachineBase implements ISidedInventory, IEnergyReceiverMK2, IGUIProvider, IUpgradeInfoProvider, IConfigurableMachine {

	// HOLY FUCKING SHIT I SPENT 5 DAYS ON THIS SHITFUCK CLASS FILE
	// thanks Martin, vaer and Bob for the help
	public int progress;
	public long power;
	public int maxProgress = 100; // TODO: make configurable
	private int cooldown = 0;

	private static final int[] slots_io = new int[] { 0, 1, 2 };

	// configurable values
	public static long maxPower = 100000;
	public static int baseConsumption = 50;

	@Override
	public String getConfigName() {
		return "electricFurnace";
	}

	@Override
	public void readIfPresent(JsonObject obj) {
		maxPower = IConfigurableMachine.grab(obj, "I:maxPower", maxPower);
		baseConsumption = IConfigurableMachine.grab(obj, "I:consumption", baseConsumption);
	}

	@Override
	public void writeConfig(JsonWriter writer) throws IOException {
		writer.name("I:maxPower").value(maxPower);
		writer.name("I:consumption").value(baseConsumption);
	}

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
		return power >= baseConsumption;
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

			int consumption = baseConsumption;
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

				if(worldObj.getTotalWorldTime() % 20 == 0) PollutionHandler.incrementPollution(worldObj, xCoord, yCoord, zCoord, PollutionType.SOOT, PollutionHandler.SOOT_PER_SECOND);

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

			this.networkPackNT(50);


			if(markDirty) {
				this.markDirty();
			}
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeLong(power);
		buf.writeInt(maxProgress);
		buf.writeInt(progress);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		power = buf.readLong();
		maxProgress = buf.readInt();
		progress = buf.readInt();
	}

	private void updateConnections() {

		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
			this.trySubscribe(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir);
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
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineElectricFurnace(player.inventory, this);
	}

	@Override
	public boolean canProvideInfo(UpgradeType type, int level, boolean extendedInfo) {
		return type == UpgradeType.SPEED || type == UpgradeType.POWER;
	}

	@Override
	public void provideInfo(UpgradeType type, int level, List<String> info, boolean extendedInfo) {
		info.add(IUpgradeInfoProvider.getStandardLabel(ModBlocks.machine_electric_furnace_off));
		if(type == UpgradeType.SPEED) {
			info.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey(this.KEY_DELAY, "-" + (level * 25) + "%"));
			info.add(EnumChatFormatting.RED + I18nUtil.resolveKey(this.KEY_CONSUMPTION, "+" + (level * 100) + "%"));
		}
		if(type == UpgradeType.POWER) {
			info.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey(this.KEY_CONSUMPTION, "-" + (level * 30) + "%"));
			info.add(EnumChatFormatting.RED + I18nUtil.resolveKey(this.KEY_DELAY, "+" + (level * 10) + "%"));
		}
	}

	@Override
	public int getMaxLevel(UpgradeType type) {
		if(type == UpgradeType.SPEED) return 3;
		if(type == UpgradeType.POWER) return 3;
		return 0;
	}
}
