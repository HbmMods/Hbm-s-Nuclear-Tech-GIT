package com.hbm.tileentity;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.calc.UnionOfTileEntitiesAndBooleans;
import com.hbm.interfaces.IConductor;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.ISource;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemBattery;
import com.hbm.lib.Library;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityMachineDiesel extends TileEntity implements ISidedInventory, ISource {

	private ItemStack slots[];

	public int power;
	public int diesel;
	public int soundCycle = 0;
	public static final int maxPower = 10000;
	public int powerCap = 10000;
	public int superTimer;
	public static final int maxDiesel = 10000;
	public int age = 0;
	public List<IConsumer> list = new ArrayList();

	private static final int[] slots_top = new int[] { 0 };
	private static final int[] slots_bottom = new int[] { 1, 2 };
	private static final int[] slots_side = new int[] { 2 };

	private String customName;

	public TileEntityMachineDiesel() {
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
		return this.hasCustomInventoryName() ? this.customName : "container.machineDiesel";
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
		if (i == 0)
			if (stack.getItem() == ModItems.canister_fuel || stack.getItem() == ModItems.canister_NITAN
					|| stack.getItem() == Item.getItemFromBlock(ModBlocks.red_barrel))
				return true;
		if (i == 2)
			if (stack.getItem() instanceof ItemBattery)
				return true;

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

		this.power = nbt.getInteger("powerTime");
		this.diesel = nbt.getShort("diesel");
		this.powerCap = nbt.getInteger("powerCap");
		this.superTimer = nbt.getInteger("superTimer");
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
		nbt.setInteger("powerTime", power);
		nbt.setShort("diesel", (short) diesel);
		nbt.setInteger("powerCap", powerCap);
		nbt.setInteger("superTimer", superTimer);
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
			if (itemStack.getItemDamage() == 0)
				return true;

		return false;
	}

	public int getDieselScaled(int i) {
		return (diesel * i) / maxDiesel;
	}

	public int getPowerScaled(int i) {
		return (power * i) / powerCap;
	}

	@Override
	public void updateEntity() {
		if (!worldObj.isRemote) {
			age++;
			if (age >= 20) {
				age = 0;
			}
			
			if(superTimer > 0)
			{
				superTimer--;
				powerCap = 1000000000;
			}
			
			if(superTimer <= 0 && powerCap != maxPower)
			{
				powerCap = maxPower;
				
				if(worldObj.getBlock(this.xCoord, this.yCoord + 1, this.zCoord) == Blocks.air)
					worldObj.setBlock(this.xCoord, this.yCoord + 1, this.zCoord, Blocks.fire);
			}

			if (age == 9 || age == 19)
				ffgeuaInit();

			if (slots[0] != null && slots[0].getItem() == ModItems.inf_diesel) {
				diesel = maxDiesel;
			}

			if (slots[0] != null && slots[0].getItem() == ModItems.canister_fuel && diesel + 625 <= maxDiesel) {
				if (slots[1] == null || slots[1] != null && slots[1].getItem() == slots[0].getItem().getContainerItem()
						&& slots[1].stackSize < slots[1].getMaxStackSize()) {
					if (slots[1] == null)
						slots[1] = new ItemStack(slots[0].getItem().getContainerItem());
					else
						slots[1].stackSize++;

					slots[0].stackSize--;
					if (slots[0].stackSize <= 0)
						slots[0] = null;

					diesel += 625;
				}
			}

			if (slots[0] != null && slots[0].getItem() == ModItems.canister_NITAN && diesel + 625 <= maxDiesel) {
				if (slots[1] == null || slots[1] != null && slots[1].getItem() == slots[0].getItem().getContainerItem()
						&& slots[1].stackSize < slots[1].getMaxStackSize()) {
					if (slots[1] == null)
						slots[1] = new ItemStack(slots[0].getItem().getContainerItem());
					else
						slots[1].stackSize++;

					slots[0].stackSize--;
					if (slots[0].stackSize <= 0)
						slots[0] = null;

					diesel += 625;
					superTimer += 200;
				}
			}

			if (slots[0] != null && slots[0].getItem() == Item.getItemFromBlock(ModBlocks.red_barrel)
					&& diesel + 5000 <= maxDiesel) {
				if (slots[1] == null || slots[1] != null && slots[1].getItem() == ModItems.tank_steel
						&& slots[1].stackSize < slots[1].getMaxStackSize()) {
					if (slots[1] == null)
						slots[1] = new ItemStack(ModItems.tank_steel);
					else
						slots[1].stackSize++;

					slots[0].stackSize--;
					if (slots[0].stackSize <= 0)
						slots[0] = null;

					diesel += 5000;
				}
			}

			// Battery Item
			if (power - 100 >= 0 && slots[2] != null && slots[2].getItem() == ModItems.battery_generic
					&& slots[2].getItemDamage() > 0) {
				power -= 100;
				slots[2].setItemDamage(slots[2].getItemDamage() - 1);
			}
			if (power - 100 >= 0 && slots[2] != null && slots[2].getItem() == ModItems.battery_advanced
					&& slots[2].getItemDamage() > 0) {
				power -= 100;
				slots[2].setItemDamage(slots[2].getItemDamage() - 1);
			}
			if (power - 100 >= 0 && slots[2] != null && slots[2].getItem() == ModItems.battery_schrabidium
					&& slots[2].getItemDamage() > 0) {
				power -= 100;
				slots[2].setItemDamage(slots[2].getItemDamage() - 1);
			}
			if (power - 100 >= 0 && slots[2] != null && slots[2].getItem() == ModItems.factory_core_titanium
					&& slots[2].getItemDamage() > 0) {
				power -= 100;
				slots[2].setItemDamage(slots[2].getItemDamage() - 1);
			}
			if (power - 100 >= 0 && slots[2] != null && slots[2].getItem() == ModItems.factory_core_advanced
					&& slots[2].getItemDamage() > 0) {
				power -= 100;
				slots[2].setItemDamage(slots[2].getItemDamage() - 1);
			}

			generate();
		}
	}

	public void generate() {
		if (diesel > 0) {
			if (soundCycle == 0) {
				if(this.superTimer > 0)
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "fireworks.blast", 1.0F, 1.0F);
				else
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "fireworks.blast", 1.0F, 0.5F);
			}
			soundCycle++;

			if (soundCycle >= 3 && this.superTimer <= 0)
				soundCycle = 0;
			if(this.superTimer > 0)
				soundCycle = 0;

			diesel -= 10;
			if (diesel < 0)
				diesel = 0;

			if (power + 25 <= powerCap && this.superTimer <= 0) {
				power += 25;
			} else if (power + 1000000000 <= powerCap && this.superTimer > 0) {
				power += 1000000000;
			} else {
				power = powerCap;
			}
		}
	}

	@Override
	public void ffgeua(int x, int y, int z, boolean newTact) {
		Block block = this.worldObj.getBlock(x, y, z);
		TileEntity tileentity = this.worldObj.getTileEntity(x, y, z);

		if (block == ModBlocks.factory_titanium_conductor
				&& this.worldObj.getBlock(x, y + 1, z) == ModBlocks.factory_titanium_core) {
			tileentity = this.worldObj.getTileEntity(x, y + 1, z);
		}
		if (block == ModBlocks.factory_titanium_conductor
				&& this.worldObj.getBlock(x, y - 1, z) == ModBlocks.factory_titanium_core) {
			tileentity = this.worldObj.getTileEntity(x, y - 1, z);
		}
		if (block == ModBlocks.factory_advanced_conductor
				&& this.worldObj.getBlock(x, y + 1, z) == ModBlocks.factory_advanced_core) {
			tileentity = this.worldObj.getTileEntity(x, y + 1, z);
		}
		if (block == ModBlocks.factory_advanced_conductor
				&& this.worldObj.getBlock(x, y - 1, z) == ModBlocks.factory_advanced_core) {
			tileentity = this.worldObj.getTileEntity(x, y - 1, z);
		}

		if (tileentity instanceof IConductor) {
			if (tileentity instanceof TileEntityCable) {
				if (Library.checkUnionList(((TileEntityCable) tileentity).uoteab, this)) {
					for (int i = 0; i < ((TileEntityCable) tileentity).uoteab.size(); i++) {
						if (((TileEntityCable) tileentity).uoteab.get(i).source == this) {
							if (((TileEntityCable) tileentity).uoteab.get(i).ticked != newTact) {
								((TileEntityCable) tileentity).uoteab.get(i).ticked = newTact;
								ffgeua(x, y + 1, z, getTact());
								ffgeua(x, y - 1, z, getTact());
								ffgeua(x - 1, y, z, getTact());
								ffgeua(x + 1, y, z, getTact());
								ffgeua(x, y, z - 1, getTact());
								ffgeua(x, y, z + 1, getTact());
							}
						}
					}
				} else {
					((TileEntityCable) tileentity).uoteab.add(new UnionOfTileEntitiesAndBooleans(this, newTact));
				}
			}
			if (tileentity instanceof TileEntityWireCoated) {
				if (Library.checkUnionList(((TileEntityWireCoated) tileentity).uoteab, this)) {
					for (int i = 0; i < ((TileEntityWireCoated) tileentity).uoteab.size(); i++) {
						if (((TileEntityWireCoated) tileentity).uoteab.get(i).source == this) {
							if (((TileEntityWireCoated) tileentity).uoteab.get(i).ticked != newTact) {
								((TileEntityWireCoated) tileentity).uoteab.get(i).ticked = newTact;
								ffgeua(x, y + 1, z, getTact());
								ffgeua(x, y - 1, z, getTact());
								ffgeua(x - 1, y, z, getTact());
								ffgeua(x + 1, y, z, getTact());
								ffgeua(x, y, z - 1, getTact());
								ffgeua(x, y, z + 1, getTact());
							}
						}
					}
				} else {
					((TileEntityWireCoated) tileentity).uoteab.add(new UnionOfTileEntitiesAndBooleans(this, newTact));
				}
			}
		}

		if (tileentity instanceof IConsumer && newTact && !(tileentity instanceof TileEntityMachineBattery
				&& ((TileEntityMachineBattery) tileentity).conducts)) {
			list.add((IConsumer) tileentity);
		}

		if (!newTact) {
			int size = list.size();
			if (size > 0) {
				int part = this.power / size;
				for (IConsumer consume : list) {
					if (consume.getPower() < consume.getMaxPower()) {
						if (consume.getMaxPower() - consume.getPower() >= part) {
							this.power -= part;
							consume.setPower(consume.getPower() + part);
						} else {
							this.power -= consume.getMaxPower() - consume.getPower();
							consume.setPower(consume.getMaxPower());
						}
					}
				}
			}
			list.clear();
		}
	}

	@Override
	public void ffgeuaInit() {
		ffgeua(this.xCoord, this.yCoord + 1, this.zCoord, getTact());
		ffgeua(this.xCoord, this.yCoord - 1, this.zCoord, getTact());
		ffgeua(this.xCoord - 1, this.yCoord, this.zCoord, getTact());
		ffgeua(this.xCoord + 1, this.yCoord, this.zCoord, getTact());
		ffgeua(this.xCoord, this.yCoord, this.zCoord - 1, getTact());
		ffgeua(this.xCoord, this.yCoord, this.zCoord + 1, getTact());
	}

	public boolean getTact() {
		if (age >= 0 && age < 10) {
			return true;
		}

		return false;
	}
}
