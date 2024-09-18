package com.hbm.items.tool;

import com.hbm.inventory.container.ContainerLeadBox;
import com.hbm.inventory.gui.GUILeadBox;
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

public class ItemLeadBox extends Item implements IGUIProvider {

	public ItemLeadBox() {
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
		return new ContainerLeadBox(player.inventory, new InventoryLeadBox(player, player.getHeldItem()));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUILeadBox(player.inventory, new InventoryLeadBox(player, player.getHeldItem()));
	}
	
	public static class InventoryLeadBox implements IInventory {
		
		public final EntityPlayer player;
		public final ItemStack box;
		public ItemStack[] slots;
		
		public InventoryLeadBox(EntityPlayer player, ItemStack box) {
			this.player = player;
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

		@Override
		public int getSizeInventory() {
			return 20;
		}

		@Override
		public ItemStack getStackInSlot(int slot) {
			return slots[slot];
		}

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

		@Override
		public String getInventoryName() {
			return "container.leadBox";
		}

		@Override
		public boolean hasCustomInventoryName() {
			return box.hasDisplayName();
		}

		@Override
		public int getInventoryStackLimit() {
			return 1;
		}

		@Override
		public void markDirty() {
			
			for(int i = 0; i < getSizeInventory(); ++i) {
				if(getStackInSlot(i) != null && getStackInSlot(i).stackSize == 0) {
					slots[i] = null;
				}
			}
			
			ItemStackUtil.addStacksToNBT(box, slots);
		}

		@Override
		public boolean isUseableByPlayer(EntityPlayer player) {
			return true;
		}

		@Override
		public void openInventory() {
			player.worldObj.playSoundEffect(player.posX, player.posY, player.posZ, "hbm:block.crateOpen", 1.0F, 0.8F);
		}

		@Override
		public void closeInventory() {
			player.worldObj.playSoundEffect(player.posX, player.posY, player.posZ, "hbm:block.crateClose", 1.0F, 0.8F);
		}

		@Override
		public boolean isItemValidForSlot(int slot, ItemStack stack) {
			return true;
		}
	}
}
