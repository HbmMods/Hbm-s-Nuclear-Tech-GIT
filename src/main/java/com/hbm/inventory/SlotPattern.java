package com.hbm.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotPattern extends Slot {

	protected boolean canHover = true;

	protected boolean allowStackSize = false;
	
	public SlotPattern(IInventory inv, int index, int x, int y) {
		super(inv, index, x, y);
	}

	public SlotPattern(IInventory inv, int index, int x, int y, boolean allowStackSize) {
		super(inv, index, x, y);
		this.allowStackSize = allowStackSize;
	}
	
	@Override
	public boolean canTakeStack(EntityPlayer player) {
		return false;
	}
	
	@Override
	public int getSlotStackLimit() {
		return 1;
	}
	
	@Override
	public void putStack(ItemStack stack) {
		if (stack != null) {
			stack = stack.copy();
			
			if (!allowStackSize)
				stack.stackSize = 1;
		}
		super.putStack(stack);
	}

	public SlotPattern disableHover() {
		this.canHover = false;
		return this;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean func_111238_b() {
		return canHover;
	}
}
