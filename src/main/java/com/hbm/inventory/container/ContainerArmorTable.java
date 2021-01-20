package com.hbm.inventory.container;

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

        this.addSlotToContainer(new Slot(upgrades, 0, 26, 27));		//helmet only
        this.addSlotToContainer(new Slot(upgrades, 1, 62, 27));		//chestplate only
        this.addSlotToContainer(new Slot(upgrades, 2, 98, 27));		//leggins only
        this.addSlotToContainer(new Slot(upgrades, 3, 134, 45));	//boots only
        this.addSlotToContainer(new Slot(upgrades, 4, 134, 81));	//servos/frame
        this.addSlotToContainer(new Slot(upgrades, 5, 98, 99));		//radiation cladding
        this.addSlotToContainer(new Slot(upgrades, 6, 62, 99));		//kevlar/sapi/(ERA? :) )
        this.addSlotToContainer(new Slot(upgrades, 7, 26, 99));		//explosive/heavy plating
    	
        this.addSlotToContainer(new Slot(armor, 0, 44, 36) {
        	
            public boolean isItemValid(ItemStack stack) {
                return stack.getItem() instanceof ItemArmor;
            }
        });

        this.onCraftMatrixChanged(this.upgrades);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

}
