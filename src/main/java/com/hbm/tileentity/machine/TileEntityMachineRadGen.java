package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.ISource;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.LoopedSoundPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TEIGeneratorPacket;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityMachineRadGen extends TileEntity implements ISidedInventory, ISource {

	private ItemStack slots[];

	public long power;
	public int fuel;
	public int strength;
	public int mode;
	public int soundCycle = 0;
	public float rotation;
	public static final long maxPower = 100000;
	public static final int maxFuel = 10000;
	public static final int maxStrength = 1000;
	public int age = 0;
	public List<IConsumer> list = new ArrayList();

	private static final int[] slots_top = new int[] { 0 };
	private static final int[] slots_bottom = new int[] { 0, 0 };
	private static final int[] slots_side = new int[] { 0 };

	private String customName;

	public TileEntityMachineRadGen() {
		slots = new ItemStack[3];
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
		return this.hasCustomInventoryName() ? this.customName : "container.radGen";
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

		this.power = nbt.getLong("power");
		this.fuel = nbt.getInteger("fuel");
		this.strength = nbt.getInteger("strength");
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
		nbt.setLong("power", power);
		nbt.setInteger("fuel", fuel);
		nbt.setInteger("strength", strength);
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
		return false;
	}

	@Override
	public void updateEntity() {
		
		if (!worldObj.isRemote) {
			
			age++;
			if(age >= 20)
			{
				age = 0;
			}
			
			if(age == 9 || age == 19)
				ffgeuaInit();
		}
		
		if(!worldObj.isRemote) {
			
			int r = getRads(slots[0]);
			if(r > 0) {
				if(slots[0].getItem().hasContainerItem()) {
					if(slots[1] == null) {
						if(fuel + r <= maxFuel) {
							
							slots[1] = new ItemStack(slots[0].getItem().getContainerItem());
							
							slots[0].stackSize--;
							if(slots[0].stackSize <= 0)
								slots[0] = null;
							fuel += r;
						}
					} else if(slots[0].getItem().getContainerItem() == slots[1].getItem() && slots[1].stackSize < slots[1].getMaxStackSize()) {
						if(fuel + r <= maxFuel) {
							
							slots[1].stackSize++;
							
							slots[0].stackSize--;
							if(slots[0].stackSize <= 0)
								slots[0] = null;
							fuel += r;
						}
					}
				} else {
					if(fuel + r <= maxFuel) {
						slots[0].stackSize--;
						if(slots[0].stackSize <= 0)
							slots[0] = null;
						fuel += r;
					}
				}
			}
			
			if(fuel > 0) {
				fuel--;
				if(strength < maxStrength)
					strength += Math.ceil(fuel / 1000);
			} else {
				if(strength > 0)
					strength -= (strength * 0.1);
			}

			if(strength > maxStrength)
				strength = maxStrength;

			if(strength < 0)
				strength = 0;
			
			power += strength;
			
			if(power > maxPower)
				power = maxPower;
			
			mode = 0;
			if(strength > 0)
				mode = 1;
			if(strength > 800)
				mode = 2;
			
			//PacketDispatcher.wrapper.sendToAll(new TEIGeneratorPacket(xCoord, yCoord, zCoord, rotation, torque));
			PacketDispatcher.wrapper.sendToAll(new AuxElectricityPacket(xCoord, yCoord, zCoord, power));
		}
	}
	
	private int getRads(ItemStack stack) {
		if(stack == null)
			return 0;
		
		Item item = stack.getItem();

		if(item == ModItems.nuclear_waste) return 100;
		if(item == ModItems.trinitite) return 80;
		if(item == ModItems.rod_waste) return 600;
		if(item == ModItems.rod_dual_waste) return 1200;
		if(item == ModItems.rod_quad_waste) return 4800;
		
		return 0;
	}
	
	public int getFuelScaled(int i) {
		return (fuel * i) / maxFuel;
	}
	
	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}
	
	public int getStrengthScaled(int i) {
		return (strength * i) / maxStrength;
	}

	@Override
	public void ffgeua(int x, int y, int z, boolean newTact) {
		
		Library.ffgeua(x, y, z, newTact, this, worldObj);
	}

	@Override
	public void ffgeuaInit() {
		int i = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
		ffgeua(this.xCoord + 1, this.yCoord, this.zCoord, getTact());
		ffgeua(this.xCoord - 1, this.yCoord, this.zCoord, getTact());
		ffgeua(this.xCoord, this.yCoord, this.zCoord + 1, getTact());
		ffgeua(this.xCoord, this.yCoord, this.zCoord - 1, getTact());
	}

	@Override
	public boolean getTact() {
		if (age >= 0 && age < 10) {
			return true;
		}

		return false;
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
}
