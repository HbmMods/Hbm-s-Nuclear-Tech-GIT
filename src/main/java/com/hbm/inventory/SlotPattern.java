package com.hbm.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class SlotPattern extends Slot {

	protected boolean canHover = true;
	
	public SlotPattern(IInventory inv, int index, int x, int y) {
		super(inv, index, x, y);
	}
	
	@Override
	public boolean canTakeStack(EntityPlayer player) {
		return false;
	}
	
	@Override
	public int getSlotStackLimit() {
		return 1;
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
