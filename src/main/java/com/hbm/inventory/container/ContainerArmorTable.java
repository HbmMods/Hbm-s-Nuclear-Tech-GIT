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
		this.addSlotToContainer(new UpgradeSlot(upgrades, ArmorModHandler.plating, 26, 99));		//explosive/heavy plating

		this.addSlotToContainer(new Slot(armor, 0, 44, 63) {

			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() instanceof ItemArmor;
			}
			
			public void onSlotChanged() {
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
	
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);

		if(!player.worldObj.isRemote) {
			for(int i = 0; i < this.upgrades.getSizeInventory(); ++i) {
				ItemStack itemstack = this.upgrades.getStackInSlotOnClosing(i);

				if(itemstack != null) {
					player.dropPlayerItemWithRandomChoice(itemstack, false);
				}
			}
		}
	}
	
	public class UpgradeSlot extends Slot {

		public UpgradeSlot(IInventory inventory, int index, int x, int y) {
			super(inventory, index, x, y);
		}

		public boolean isItemValid(ItemStack stack) {
			return armor.getStackInSlot(0) != null && stack.getItem() instanceof ItemArmorMod && ((ItemArmorMod)stack.getItem()).type == this.slotNumber;
		}
	}
}
