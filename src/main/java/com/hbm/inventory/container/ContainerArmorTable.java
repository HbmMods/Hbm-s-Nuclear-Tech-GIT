package com.hbm.inventory.container;

import com.hbm.handler.ArmorModHandler;
import com.hbm.items.armor.ItemArmorMod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ContainerArmorTable extends Container {
	
	public InventoryBasic upgrades = new InventoryBasic("Upgrades", false, 8);
	public IInventory armor = new InventoryCraftResult();

	public ContainerArmorTable(InventoryPlayer inventory) {
		
		this.addSlotToContainer(new UpgradeSlot(upgrades, ArmorModHandler.helmet_only, 26, 27));	// helmet only
		this.addSlotToContainer(new UpgradeSlot(upgrades, ArmorModHandler.plate_only, 62, 27));		// chestplate only
		this.addSlotToContainer(new UpgradeSlot(upgrades, ArmorModHandler.legs_only, 98, 27));		// leggins only
		this.addSlotToContainer(new UpgradeSlot(upgrades, ArmorModHandler.boots_only, 134, 45));	// boots only
		this.addSlotToContainer(new UpgradeSlot(upgrades, ArmorModHandler.servos, 134, 81));		//servos/frame
		this.addSlotToContainer(new UpgradeSlot(upgrades, ArmorModHandler.cladding, 98, 99));		//radiation cladding
		this.addSlotToContainer(new UpgradeSlot(upgrades, ArmorModHandler.kevlar, 62, 99));			//kevlar/sapi/(ERA? :) )
		this.addSlotToContainer(new UpgradeSlot(upgrades, ArmorModHandler.extra, 26, 99));			//special parts

		this.addSlotToContainer(new Slot(armor, 0, 44, 63) {

			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() instanceof ItemArmor;
			}

			@Override
			public void putStack(ItemStack stack) {
				
				//when inserting a new armor piece, unload all mods to display
				if(stack != null) {
					ItemStack[] mods = ArmorModHandler.pryMods(stack);
					
					for(int i = 0; i < 8; i++) {
						
						if(mods != null)
							upgrades.setInventorySlotContents(i, mods[i]);
					}
					
				}
				
				super.putStack(stack);
			}

			@Override
			public void onPickupFromSlot(EntityPlayer player, ItemStack stack) {
				super.onPickupFromSlot(player, stack);
				
				//if the armor piece is taken, absorb all armor pieces
				
				for(int i = 0; i < 8; i++) {
					
					ItemStack mod = upgrades.getStackInSlot(i);
					
					//ideally, this should always return true so long as the mod slot is not null due to the insert restriction
					if(ArmorModHandler.isApplicable(stack, mod)) {
						upgrades.setInventorySlotContents(i, null);
					}
				}
			}
		});
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 + 56));
			}
		}
		
		for(int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 142 + 56));
		}
		
		this.onCraftMatrixChanged(this.upgrades);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2) {
		ItemStack var3 = null;
		Slot var4 = (Slot) this.inventorySlots.get(par2);

		if(var4 != null && var4.getHasStack()) {
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();

			if(par2 <= 8) {
				if(!this.mergeItemStack(var5, 9, this.inventorySlots.size(), true)) {
					return null;
				} else {
					var4.onPickupFromSlot(p_82846_1_, var5);
				}
			} else {
				
				if(var5.getItem() instanceof ItemArmor) {
					if(!this.mergeItemStack(var5, 8, 9, false))
						return null;
					
				} else if(this.inventorySlots.get(8) != null && var5.getItem() instanceof ItemArmorMod) {
					
					ItemArmorMod mod = (ItemArmorMod)var5.getItem();
					int slot = mod.type;
					
					if(((Slot) this.inventorySlots.get(slot)).isItemValid(var5)) {
						if(!this.mergeItemStack(var5, slot, slot + 1, false))
							return null;
					} else {
						return null;
					}
				} else {
					return null;
				}
			}

			if(var5.stackSize == 0) {
				var4.putStack((ItemStack) null);
			} else {
				var4.onSlotChanged();
			}
		}

		return var3;
	}

	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);

		if(!player.worldObj.isRemote) {
			for(int i = 0; i < this.upgrades.getSizeInventory(); ++i) {
				ItemStack itemstack = this.upgrades.getStackInSlotOnClosing(i);

				if(itemstack != null) {
					player.dropPlayerItemWithRandomChoice(itemstack, false);
					ArmorModHandler.removeMod(armor.getStackInSlot(0), i);
				}
			}
			
			ItemStack itemstack = this.armor.getStackInSlotOnClosing(0);
			
			if(itemstack != null) {
				player.dropPlayerItemWithRandomChoice(itemstack, false);
			}
		}
	}
	
	public class UpgradeSlot extends Slot {

		public UpgradeSlot(IInventory inventory, int index, int x, int y) {
			super(inventory, index, x, y);
		}

		@Override
		public boolean isItemValid(ItemStack stack) {
			return armor.getStackInSlot(0) != null && ArmorModHandler.isApplicable(armor.getStackInSlot(0), stack) && ((ItemArmorMod)stack.getItem()).type == this.slotNumber;
		}
		
		@Override
		public void putStack(ItemStack stack) {
			super.putStack(stack);
			
			if(stack != null) {
				if(ArmorModHandler.isApplicable(armor.getStackInSlot(0), stack))
					ArmorModHandler.applyMod(armor.getStackInSlot(0), stack);
			}
		}

		public void onPickupFromSlot(EntityPlayer player, ItemStack stack) {
			super.onPickupFromSlot(player, stack);
			
			ArmorModHandler.removeMod(armor.getStackInSlot(0), this.slotNumber);
		}
	}
}
