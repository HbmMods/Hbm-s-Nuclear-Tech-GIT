package com.hbm.items.block;

import java.util.List;

import com.hbm.interfaces.IItemHazard;
import com.hbm.modules.ItemHazardModule;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBlockHazard extends ItemBlock {
	
	ItemHazardModule module;

	public ItemBlockHazard(Block block) {
		super(block);
		
		if(block instanceof IItemHazard) {
			this.module = ((IItemHazard)block).getModule();
		}
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int i, boolean b) {
		
		if(entity instanceof EntityLivingBase && this.module != null)
			this.module.applyEffects((EntityLivingBase) entity, stack.stackSize, i, b);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		
		super.addInformation(stack, player, list, bool);
		
		if(this.module != null)
			this.module.addInformation(stack, player, list, bool);
	}
	
	@Override
	public boolean onEntityItemUpdate(EntityItem item) {
		
		super.onEntityItemUpdate(item);
		
		if(this.module != null)
			return this.module.onEntityItemUpdate(item);
		
		return false;
	}
}
