package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.interfaces.ISource;
import com.hbm.inventory.FluidTank;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemBattery;
import com.hbm.lib.Library;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.AuxGaugePacket;
import com.hbm.packet.PacketDispatcher;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityMachineSeleniumEngine extends TileEntity implements ISidedInventory, ISource, IFluidContainer, IFluidAcceptor {

	private ItemStack slots[];

	public long power;
	public int soundCycle = 0;
	public static final long maxPower = 250000;
	public long powerCap = 250000;
	public int age = 0;
	public List<IConsumer> list = new ArrayList();
	public FluidTank tank;
	public int pistonCount = 0;

	private static final int[] slots_top = new int[] { 0 };
	private static final int[] slots_bottom = new int[] { 1, 2 };
	private static final int[] slots_side = new int[] { 2 };

	private String customName;

	public TileEntityMachineSeleniumEngine() {
		slots = new ItemStack[14];
		tank = new FluidTank(FluidType.DIESEL, 16000, 0);
	}

	@Override
	public int getSizeInventory() {
		return slots.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return slots[i];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if (slots[i] != null) {
			ItemStack itemStack = slots[i];
			slots[i] = null;
			return itemStack;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemStack) {
		slots[i] = itemStack;
		if (itemStack != null && itemStack.stackSize > getInventoryStackLimit()) {
			itemStack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.machineSelenium";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return this.customName != null && this.customName.length() > 0;
	}

	public void setCustomName(String name) {
		this.customName = name;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		if (worldObj.getTileEntity(xCoord, yCoord, zCoord) != this) {
			return false;
		} else {
			return player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64;
		}
	}

	// You scrubs aren't needed for anything (right now)
	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		return false;
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (slots[i] != null) {
			if (slots[i].stackSize <= j) {
				ItemStack itemStack = slots[i];
				slots[i] = null;
				return itemStack;
			}
			ItemStack itemStack1 = slots[i].splitStack(j);
			if (slots[i].stackSize == 0) {
				slots[i] = null;
			}

			return itemStack1;
		} else {
			return null;
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("items", 10);

		this.power = nbt.getLong("powerTime");
		this.powerCap = nbt.getLong("powerCap");
		tank.readFromNBT(nbt, "fuel");
		slots = new ItemStack[getSizeInventory()];

		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound nbt1 = list.getCompoundTagAt(i);
			byte b0 = nbt1.getByte("slot");
			if (b0 >= 0 && b0 < slots.length) {
				slots[b0] = ItemStack.loadItemStackFromNBT(nbt1);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("powerTime", power);
		nbt.setLong("powerCap", powerCap);
		tank.writeToNBT(nbt, "fuel");
		NBTTagList list = new NBTTagList();

		for (int i = 0; i < slots.length; i++) {
			if (slots[i] != null) {
				NBTTagCompound nbt1 = new NBTTagCompound();
				nbt1.setByte("slot", (byte) i);
				slots[i].writeToNBT(nbt1);
				list.appendTag(nbt1);
			}
		}
		nbt.setTag("items", list);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return p_94128_1_ == 0 ? slots_bottom : (p_94128_1_ == 1 ? slots_top : slots_side);
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return this.isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		if (i == 1)
			if (itemStack.getItem() == ModItems.canister_empty || itemStack.getItem() == ModItems.tank_steel)
				return true;
		if (i == 2)
			if (itemStack.getItem() instanceof ItemBattery && ItemBattery.getCharge(itemStack) == ItemBattery.getMaxChargeStatic(itemStack))
				return true;

		return false;
	}

	public long getPowerScaled(long i) {
		return (power * i) / powerCap;
	}

	@Override
	public void updateEntity() {
		if (!worldObj.isRemote) {
			age++;
			if (age >= 20) {
				age = 0;
			}

			if (age == 9 || age == 19)
				ffgeuaInit();
			
			pistonCount = countPistons();

			//Tank Management
			tank.setType(11, 12, slots);
			tank.loadTank(9, 10, slots);
			tank.updateTank(xCoord, yCoord, zCoord);

			FluidType type = tank.getTankType();
			if(type.name().equals(FluidType.NITAN.name()))
				powerCap = maxPower * 10;
			else
				powerCap = maxPower;
			
			// Battery Item
			power = Library.chargeItemsFromTE(slots, 13, power, powerCap);

			if(this.pistonCount > 2)
				generate();

			PacketDispatcher.wrapper.sendToAll(new AuxElectricityPacket(xCoord, yCoord, zCoord, power));
			PacketDispatcher.wrapper.sendToAll(new AuxGaugePacket(xCoord, yCoord, zCoord, pistonCount, 0));
			PacketDispatcher.wrapper.sendToAll(new AuxGaugePacket(xCoord, yCoord, zCoord, (int)powerCap, 1));
		}
	}
	
	public int countPistons() {
		int count = 0;
		
		for(int i = 0; i < 9; i++) {
			if(slots[i] != null && slots[i].getItem() == ModItems.piston_selenium)
				count++;
		}
		
		return count;
	}
	
	public boolean hasAcceptableFuel() {
		return getHEFromFuel() > 0;
	}
	
	public int getHEFromFuel() {
		FluidType type = tank.getTankType();
		if(type.name().equals(FluidType.SMEAR.name()))
			return 50;
		if(type.name().equals(FluidType.HEATINGOIL.name()))
			return 75;
		if(type.name().equals(FluidType.DIESEL.name()))
			return 225;
		if(type.name().equals(FluidType.KEROSENE.name()))
			return 300;
		if(type.name().equals(FluidType.RECLAIMED.name()))
			return 100;
		if(type.name().equals(FluidType.PETROIL.name()))
			return 125;
		if(type.name().equals(FluidType.BIOFUEL.name()))
			return 200;
		if(type.name().equals(FluidType.NITAN.name()))
			return 2500;
		return 0;
	}

	public void generate() {
		if (hasAcceptableFuel()) {
			if (tank.getFill() > 0) {
				if (soundCycle == 0) {
					//if (tank.getTankType().name().equals(FluidType.) > 0)
					//	this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "fireworks.blast", 1.0F, 1.0F);
					//else
						this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "fireworks.blast", 1.0F, 0.5F);
				}
				soundCycle++;

				if (soundCycle >= 3)
					soundCycle = 0;

				tank.setFill(tank.getFill() - this.pistonCount * 5);
				if (tank.getFill() < 0)
					tank.setFill(0);

				power += getHEFromFuel() * Math.pow(this.pistonCount, 1.15D);
					
				if(power > powerCap)
					power = powerCap;
			}
		}
	}

	@Override
	public void ffgeua(int x, int y, int z, boolean newTact) {
		
		Library.ffgeua(x, y, z, newTact, this, worldObj);
	}

	@Override
	public void ffgeuaInit() {
		ffgeua(this.xCoord, this.yCoord - 1, this.zCoord, getTact());
	}

	@Override
	public boolean getTact() {
		if (age >= 0 && age < 10) {
			return true;
		}

		return false;
	}

	@Override
	public long getSPower() {
		return power;
	}

	@Override
	public void setSPower(long i) {
		this.power = i;
	}

	@Override
	public List<IConsumer> getList() {
		return list;
	}

	@Override
	public void clearList() {
		this.list.clear();
	}

	@Override
	public void setFillstate(int fill, int index) {
		tank.setFill(fill);
	}

	@Override
	public void setType(FluidType type, int index) {
		tank.setTankType(type);
	}

	@Override
	public int getMaxAFluidFill(FluidType type) {
		return type.name().equals(this.tank.getTankType().name()) ? tank.getMaxFill() : 0;
	}

	@Override
	public int getAFluidFill(FluidType type) {
		return type.name().equals(this.tank.getTankType().name()) ? tank.getFill() : 0;
	}

	@Override
	public void setAFluidFill(int i, FluidType type) {
		if(type.name().equals(tank.getTankType().name()))
			tank.setFill(i);
	}
}
