package com.hbm.inventory;

import com.hbm.items.ItemVOTVdrive;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemCustomMissilePart;
import com.hbm.items.weapon.ItemCustomRocket;
import com.hbm.items.weapon.ItemCustomMissilePart.PartType;
import com.hbm.items.weapon.ItemCustomMissilePart.WarheadType;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotRocket extends Slot {
	
	public SlotRocket(IInventory inventory, int id, int x, int y) {
		super(inventory, id, x, y);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		if(stack == null) return false;
		return stack.getItem() instanceof ItemCustomRocket;
	}

	public static class SlotRocketPart extends SlotLayer {

		private PartType type;
	
		public SlotRocketPart(IInventory inventory, int id, int x, int y, int layer, PartType type) {
			super(inventory, id, x, y, layer);
			this.type = type;
		}
	
		@Override
		public boolean isItemValid(ItemStack stack) {
			if(!super.isItemValid(stack)) return false;
			if(stack == null) return false;
			if(!(stack.getItem() instanceof ItemCustomMissilePart)) return false;
	
			ItemCustomMissilePart part = (ItemCustomMissilePart) stack.getItem();
	
			return part.type == type;
		}

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

			if(item.type != PartType.WARHEAD) return false;
			if(item == ModItems.rp_pod_20) return false;
	
			return item.attributes[0] == WarheadType.APOLLO || item.attributes[0] == WarheadType.SATELLITE;
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
