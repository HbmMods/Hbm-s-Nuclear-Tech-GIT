package com.hbm.items.tool;

import com.hbm.inventory.container.ContainerAmmoBag;
import com.hbm.inventory.gui.GUIAmmoBag;
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

public class ItemAmmoBag extends Item implements IGUIProvider {

	public ItemAmmoBag() {
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
		return new ContainerAmmoBag(player.inventory, new InventoryAmmoBag(player.getHeldItem()));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIAmmoBag(player.inventory, new InventoryAmmoBag(player.getHeldItem()));
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return !stack.hasTagCompound() || getDurabilityForDisplay(stack) != 0;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		if(!stack.hasTagCompound()) return 1D;
		
		InventoryAmmoBag inv = new InventoryAmmoBag(stack);
		int capacity = 0;
		int bullets = 0;
		for(int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack slot = inv.getStackInSlot(i);
			if(slot == null) {
				capacity += 64;
			} else {
				capacity += slot.getMaxStackSize();
				bullets += slot.stackSize;
			}
		}
		return 1D - (double) bullets / (double) capacity;
	}
	
	public static class InventoryAmmoBag implements IInventory {
		
		public final ItemStack box;
		public ItemStack[] slots;
		
		public InventoryAmmoBag(ItemStack bag) {
			this.box = bag;
			slots = new ItemStack[this.getSizeInventory()];
			
			if(!bag.hasTagCompound())
				bag.setTagCompound(new NBTTagCompound());
			
			ItemStack[] fromNBT = ItemStackUtil.readStacksFromNBT(bag, slots.length);
			
			if(fromNBT != null) {
				for(int i = 0; i < slots.length; i++) {
					slots[i] = fromNBT[i];
				}
			}
		}

		@Override public int getSizeInventory() { return 8; }
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

		@Override public String getInventoryName() { return "container.ammoBag"; }
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
		@Override public boolean isItemValidForSlot(int slot, ItemStack stack) { return !stack.hasTagCompound(); }
	}
}
