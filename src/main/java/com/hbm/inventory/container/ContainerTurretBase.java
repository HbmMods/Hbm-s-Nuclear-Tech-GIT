package com.hbm.inventory.container;

import com.hbm.items.ModItems;
import com.hbm.tileentity.turret.TileEntityTurretBaseNT;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerTurretBase extends Container {

	private TileEntityTurretBaseNT turret;

	public ContainerTurretBase(InventoryPlayer invPlayer, TileEntityTurretBaseNT te) {
		turret = te;
		turret.openInventory();
		
		this.addSlotToContainer(new Slot(te, 0, 98, 27));

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				this.addSlotToContainer(new Slot(te, 1 + i * 3 + j, 80 + j * 18, 63 + i * 18));
			}
		}
		
		this.addSlotToContainer(new Slot(te, 10, 152, 99));
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 + (18 * 3) + 2));
			}
		}
		
		for(int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 142 + (18 * 3) + 2));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2) {
		ItemStack var3 = null;
		Slot var4 = (Slot) this.inventorySlots.get(par2);

		if(var4 != null && var4.getHasStack()) {
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();

			if(par2 <= turret.getSizeInventory() - 1) {
				if(!this.mergeItemStack(var5, turret.getSizeInventory(), this.inventorySlots.size(), true)) {
					return null;
				}
			} else if(var5.getItem() == ModItems.turret_chip) {
				
				if(!this.mergeItemStack(var5, 0, 1, false))
					return null;
				
			} else if(!this.mergeItemStack(var5, 1, turret.getSizeInventory(), false)) {
				return null;
			}

			if(var5.stackSize == 0) {
				var4.putStack((ItemStack) null);
			} else {
				var4.onSlotChanged();
			}

			var4.onPickupFromSlot(p_82846_1_, var5);
		}

		return var3;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return turret.isUseableByPlayer(player);
	}
	
	@Override
	public void onContainerClosed(EntityPlayer p_75134_1_) {
		super.onContainerClosed(p_75134_1_);
		this.turret.closeInventory();
	}
}
