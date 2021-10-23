package com.hbm.items.block;

import com.hbm.hazard.HazardData;
import com.hbm.interfaces.IItemHazard;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBlockHazard extends ItemBlock implements IItemHazard {
	
	HazardData module;

	public ItemBlockHazard(Block block) {
		super(block);
		
		if(block instanceof IItemHazard) {
			this.module = ((IItemHazard)block).getModule();
		}
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int i, boolean b) {
		
		//superseded
	}
	
	@Override
	public HazardData getModule() {
		// TODO Auto-generated method stub
		return null;
	}
}
