package com.hbm.items.tool;

import com.hbm.main.MainRegistry;

import api.hbm.block.IToolable;
import api.hbm.block.IToolable.ToolType;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemTooling extends ItemCraftingDegradation {
	
	protected ToolType type;
	
	public ItemTooling(ToolType type, int durability) {
		super(durability);
		this.type = type;
		this.setFull3D();
		this.setCreativeTab(MainRegistry.controlTab);
		
		type.register(new ItemStack(this));
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float fX, float fY, float fZ) {
		
		Block b = world.getBlock(x, y, z);
		
		if(b instanceof IToolable) {
			if(((IToolable)b).onScrew(world, player, x, y, z, side, fX, fY, fZ, this.type)) {
				
				if(this.getMaxDamage() > 0)
					stack.damageItem(1, player);
				
				return true;
			}
		}
		
		return false;
	}
}
