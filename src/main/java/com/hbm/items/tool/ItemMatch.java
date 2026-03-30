package com.hbm.items.tool;

import com.hbm.main.NTMSounds;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemMatch extends Item {

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) return true;
		
		if(side == 0) --y;
		if(side == 1) ++y;
		if(side == 2) --z;
		if(side == 3) ++z;
		if(side == 4) --x;
		if(side == 5) ++x;

		if(!player.canPlayerEdit(x, y, z, side, stack)) {
			return false;
		} else {
			if(world.getBlock(x, y, z).getMaterial() == Material.air) {
				world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, NTMSounds.VANILLA_IGNITE, 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
				world.setBlock(x, y, z, Blocks.fire);
			}

			if(!player.capabilities.isCreativeMode) {
				--stack.stackSize;
			}
			return true;
		}
	}

}
