package com.hbm.inventory.container;

import com.hbm.items.weapon.sedna.ItemGunBaseNT;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerWeaponTable extends Container {
	
	public InventoryBasic mods = new InventoryBasic("Mods", false, 7);
	public IInventory gun = new InventoryCraftResult();

	public ContainerWeaponTable(InventoryPlayer inventory) {
		
		for(int i = 0; i < 7; i++) this.addSlotToContainer(new ModSlot(mods, i, 44 + 18 * i, 108));

		this.addSlotToContainer(new Slot(gun, 0, 8, 108) {

			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() instanceof ItemGunBaseNT;
			}
		});
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18 + 22, 158 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(inventory, i, 8 + i * 18 + 22, 216));
		}
		
		this.onCraftMatrixChanged(this.mods);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
	
	public class ModSlot extends Slot {

		public ModSlot(IInventory inventory, int index, int x, int y) {
			super(inventory, index, x, y);
		}
	}
}
