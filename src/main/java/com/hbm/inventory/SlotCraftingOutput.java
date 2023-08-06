package com.hbm.inventory;

import com.hbm.util.AchievementHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotCraftingOutput extends Slot {
	
	private EntityPlayer player;
	private int craftBuffer;

	public SlotCraftingOutput(EntityPlayer player, IInventory inventory, int i, int j, int k) {
		super(inventory, i, j, k);
		this.player = player;
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return false;
	}
	
	//ugly but nothing to be done
	public static void checkAchievements(EntityPlayer player, ItemStack stack) {
		AchievementHandler.fire(player, stack);
	}
	
	@Override
	public ItemStack decrStackSize(int amount) {
		if(this.getHasStack()) {
			this.craftBuffer += Math.min(amount, this.getStack().stackSize);
		}
		return super.decrStackSize(amount);
	}

	@Override
	public void onPickupFromSlot(EntityPlayer player, ItemStack stack) {
		this.onCrafting(stack);
		super.onPickupFromSlot(player, stack);
	}

	@Override
	protected void onCrafting(ItemStack stack, int amount) {
		this.craftBuffer += amount;
		this.onCrafting(stack);
	}
	
	@Override
	protected void onCrafting(ItemStack stack) {
		stack.onCrafting(this.player.worldObj, this.player, this.craftBuffer);
		checkAchievements(this.player, stack);
		this.craftBuffer = 0;
	}
}