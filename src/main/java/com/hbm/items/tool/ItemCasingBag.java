package com.hbm.items.tool;

import com.hbm.inventory.container.ContainerCasingBag;
import com.hbm.inventory.gui.GUICasingBag;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.util.ItemStackUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemCasingBag extends Item implements IGUIProvider {

	public ItemCasingBag() {
		this.setMaxStackSize(1);
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 1;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if(!world.isRemote) player.openGui(MainRegistry.instance, 0, world, 0, 0, 0);
		return stack;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerCasingBag(player.inventory, new InventoryCasingBag(player.getHeldItem()));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUICasingBag(player.inventory, new InventoryCasingBag(player.getHeldItem()));
	}
	
	/**
	 * Returns true if ammo was able to be added
	 * @param bag
	 * @param casing
	 * @param amount
	 * @return
	 */
	public static boolean pushCasing(ItemStack bag, ItemStack casing, float amount) {
		if(!bag.hasTagCompound()) bag.stackTagCompound = new NBTTagCompound();
		String name = casing.getUnlocalizedName() + "@" + casing.getItemDamage();
		boolean ret = false;
		//only add if the previous number did not exceed 1 (i.e. the bag ran full, and may have been emptied, we don't know)
		//this may also cause minor loss, which evens out the dupe mentioned below. not that it matters, casings only have a 50% recovery rate!
		if(bag.stackTagCompound.getFloat(name) < 1) {
			ret = true;
			bag.stackTagCompound.setFloat(name, bag.stackTagCompound.getFloat(name) + amount);
		}
		if(bag.stackTagCompound.getFloat(name) >= 1) {
			InventoryCasingBag inv = new InventoryCasingBag(bag);
			ItemStack toAdd = casing.copy();
			while(bag.stackTagCompound.getFloat(name) >= 1) {
				
				boolean didSomething = false;
				
				for(int i = 0; i < inv.getSizeInventory(); i++) {
					if(toAdd.stackSize <= 0) break;
					ItemStack slot = inv.getStackInSlot(i);
					if(slot != null && slot.getItem() == toAdd.getItem() && slot.getItemDamage() == toAdd.getItemDamage()) {
						int am = Math.min(toAdd.stackSize, slot.getMaxStackSize() - slot.stackSize);
						toAdd.stackSize -= am;
						slot.stackSize += am;
						if(am > 0) didSomething = true;
					}
				}
				
				for(int i = 0; i < inv.getSizeInventory(); i++) {
					if(toAdd.stackSize <= 0) break;
					ItemStack slot = inv.getStackInSlot(i);
					if(slot == null) {
						inv.setInventorySlotContents(i, toAdd);
						didSomething = true;
						break;
					}
				}
				
				if(didSomething) {
					bag.stackTagCompound.setFloat(name, bag.stackTagCompound.getFloat(name) - 1F);
					ret = true;
				} else {
					break;
				}
			}
			inv.markDirty();
		}
		return ret;
	}
	
	public static class InventoryCasingBag implements IInventory {
		
		public final ItemStack box;
		public ItemStack[] slots;
		
		public InventoryCasingBag(ItemStack box) {
			this.box = box;
			slots = new ItemStack[this.getSizeInventory()];
			
			if(!box.hasTagCompound())
				box.setTagCompound(new NBTTagCompound());
			
			ItemStack[] fromNBT = ItemStackUtil.readStacksFromNBT(box, slots.length);
			
			if(fromNBT != null) {
				for(int i = 0; i < slots.length; i++) {
					slots[i] = fromNBT[i];
				}
			}
		}

		@Override public int getSizeInventory() { return 15; }
		@Override public ItemStack getStackInSlot(int slot) { return slots[slot]; }

		@Override
		public ItemStack decrStackSize(int slot, int amount) {
			ItemStack stack = getStackInSlot(slot);
			if (stack != null) {
				if (stack.stackSize > amount) {
					stack = stack.splitStack(amount);
					markDirty();
				} else {
					setInventorySlotContents(slot, null);
				}
			}
			return stack;
		}

		@Override
		public ItemStack getStackInSlotOnClosing(int slot) {
			ItemStack stack = getStackInSlot(slot);
			setInventorySlotContents(slot, null);
			return stack;
		}

		@Override
		public void setInventorySlotContents(int slot, ItemStack stack) {
			
			if(stack != null) {
				stack.stackSize = Math.min(stack.stackSize, this.getInventoryStackLimit());
			}
			
			slots[slot] = stack;
			markDirty();
		}

		@Override public String getInventoryName() { return "container.casingBag"; }
		@Override public boolean hasCustomInventoryName() { return box.hasDisplayName(); }
		@Override public int getInventoryStackLimit() { return 64; }

		@Override
		public void markDirty() {
			for(int i = 0; i < getSizeInventory(); ++i) {
				if(getStackInSlot(i) != null && getStackInSlot(i).stackSize == 0) {
					slots[i] = null;
				}
			}
			
			ItemStackUtil.addStacksToNBT(box, slots);
		}

		@Override public boolean isUseableByPlayer(EntityPlayer player) { return true; }
		@Override public void openInventory() { }
		@Override public void closeInventory() { }
		@Override public boolean isItemValidForSlot(int slot, ItemStack stack) { return false; }
	}
}
