package com.hbm.inventory;

import com.hbm.items.ItemVOTVdrive;
import com.hbm.items.weapon.ItemCustomMissilePart;
import com.hbm.items.weapon.ItemCustomMissilePart.PartType;
import com.hbm.items.weapon.ItemCustomMissilePart.WarheadType;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotRocket extends SlotLayer {

	private PartType type;

	public SlotRocket(IInventory inventory, int id, int x, int y, int layer, PartType type) {
		super(inventory, id, x, y, layer);
		this.type = type;
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		if(stack == null) return false;
		if(!(stack.getItem() instanceof ItemCustomMissilePart)) return false;

		ItemCustomMissilePart part = (ItemCustomMissilePart) stack.getItem();

		return part.type == type;
	}

	public static class SlotCapsule extends Slot {

		public SlotCapsule(IInventory inventory, int id, int x, int y) {
			super(inventory, id, x, y);
		}

		@Override
		public boolean isItemValid(ItemStack stack) {
			if(stack == null) return false;
			if(!(stack.getItem() instanceof ItemCustomMissilePart)) return false;
	
			ItemCustomMissilePart item = (ItemCustomMissilePart) stack.getItem();
	
			return item.type == PartType.WARHEAD && item.attributes[0] == WarheadType.APOLLO;
		}
		
	}

	public static class SlotDrive extends Slot {

		public SlotDrive(IInventory inventory, int id, int x, int y) {
			super(inventory, id, x, y);
		}

		@Override
		public boolean isItemValid(ItemStack stack) {
			if(stack == null) return false;
			return stack.getItem() instanceof ItemVOTVdrive;
		}

	}
	
}
