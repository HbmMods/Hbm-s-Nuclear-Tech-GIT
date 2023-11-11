package com.hbm.hazard.transformer;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockStorageCrate;
import com.hbm.blocks.machine.MachineDischarger;
import com.hbm.hazard.HazardEntry;
import com.hbm.hazard.HazardRegistry;
import com.hbm.hazard.HazardSystem;
import com.hbm.items.ModItems;
import com.hbm.tileentity.IPersistentNBT;
import com.hbm.tileentity.machine.TileEntityMachineDischarger;
import com.hbm.util.BobMathUtil;
import com.hbm.util.ItemStackUtil;
import com.typesafe.config.ConfigException.Null;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.TempCategory;

public class HazardTransformerRadiationContainer extends HazardTransformerBase {

	@Override
	public void transformPre(ItemStack stack, List<HazardEntry> entries) { }

	@Override
	public void transformPost(ItemStack stack, List<HazardEntry> entries) {
		
		boolean isCrate = Block.getBlockFromItem(stack.getItem()) instanceof BlockStorageCrate;
		boolean isDissed = Block.getBlockFromItem(stack.getItem()) instanceof MachineDischarger;
		boolean isBox = stack.getItem() == ModItems.containment_box;
		boolean isBag = stack.getItem() == ModItems.plastic_bag;
		
		if(!isCrate && !isBox && !isBag && !isDissed) return;
		if(!stack.hasTagCompound()) return;
		
		float radiation = 0;
		float pyphor = 0;
		
		if(isCrate) {
			
			for(int i = 0; i < 54; i++) {
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
		if(isDissed) {
            ItemStack droppedItem = new ItemStack(Block.getBlockFromItem(stack.getItem()));

            if (stack.hasTagCompound()) {

                NBTTagCompound tagCompound = stack.getTagCompound().getCompoundTag(IPersistentNBT.NBT_PERSISTENT_KEY);

                if (tagCompound != null) {

                    int tempr = tagCompound.getInteger("temp");
                    int pw = tagCompound.getInteger("power");
                	if (tempr > 100) {
                    	entries.add(new HazardEntry(HazardRegistry.HOT, tempr));
                    	}
                	if(tempr == 20 && pw == 0 ) {
                		stack.setTagCompound((NBTTagCompound)null); // to prevent duplicate stacking i guess?
                		}
                }
					
	}
}
	}
	
}
	


