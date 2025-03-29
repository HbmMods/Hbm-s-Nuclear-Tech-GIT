package com.hbm.hazard.transformer;

import java.util.List;

import com.hbm.blocks.generic.BlockStorageCrate;
import com.hbm.hazard.HazardEntry;
import com.hbm.hazard.HazardRegistry;
import com.hbm.hazard.HazardSystem;
import com.hbm.items.ModItems;
import com.hbm.util.BobMathUtil;
import com.hbm.util.ItemStackUtil;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class HazardTransformerRadiationContainer extends HazardTransformerBase {

	@Override
	public void transformPre(ItemStack stack, List<HazardEntry> entries) { }

	@Override
	public void transformPost(ItemStack stack, List<HazardEntry> entries) {

		boolean isCrate = Block.getBlockFromItem(stack.getItem()) instanceof BlockStorageCrate;
		boolean isBox = stack.getItem() == ModItems.containment_box;
		boolean isBag = stack.getItem() == ModItems.plastic_bag;

		boolean isContainer = stack.getItem() == ModItems.toolbox; // For anything using the standard ItemInventory shit.

		if(!isCrate && !isBox && !isBag && !isContainer) return;
		if(!stack.hasTagCompound()) return;

		float radiation = 0;

		if(isContainer) {
			ItemStack[] items = ItemStackUtil.readStacksFromNBT(stack, 24); // Biggest container: toolbox; 24 slots.

			if(items != null)
				for(ItemStack held : items)
					if(held != null)
						radiation += HazardSystem.getHazardLevelFromStack(held, HazardRegistry.RADIATION) * held.stackSize;
			// this indentation is killing me man
		}

		if(isCrate) {

			for(int i = 0; i < 104; i++) {
				ItemStack held = ItemStack.loadItemStackFromNBT(stack.stackTagCompound.getCompoundTag("slot" + i));

				if(held != null) {
					radiation += HazardSystem.getHazardLevelFromStack(held, HazardRegistry.RADIATION) * held.stackSize;
				}
			}
		}

		if(isBox) {

			ItemStack[] fromNBT = ItemStackUtil.readStacksFromNBT(stack, 20);
			if(fromNBT == null) return;

			for(ItemStack held : fromNBT) {
				if(held != null) {
					radiation += HazardSystem.getHazardLevelFromStack(held, HazardRegistry.RADIATION) * held.stackSize;
				}
			}

			radiation = (float) BobMathUtil.squirt(radiation);
		}

		if(isBag) {

			ItemStack[] fromNBT = ItemStackUtil.readStacksFromNBT(stack, 1);
			if(fromNBT == null) return;

			for(ItemStack held : fromNBT) {
				if(held != null) {
					radiation += HazardSystem.getHazardLevelFromStack(held, HazardRegistry.RADIATION) * held.stackSize;
				}
			}

			radiation *= 2F;
		}

		if(radiation > 0) {
			entries.add(new HazardEntry(HazardRegistry.RADIATION, radiation));
		}
	}
}
