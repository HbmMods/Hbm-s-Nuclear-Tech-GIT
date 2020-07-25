package com.hbm.items.tool;

import com.hbm.blocks.ModBlocks;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBalefireMatch extends Item {

    public ItemBalefireMatch() {
        this.maxStackSize = 1;
        this.setMaxDamage(256);
        this.setCreativeTab(CreativeTabs.tabTools);
    }
    
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float fx, float fy, float fz) {
    	
        if (side == 0)
            --y;

        if (side == 1)
            ++y;

        if (side == 2)
            --z;

        if (side == 3)
            ++z;

        if (side == 4)
            --x;

        if (side == 5)
            ++x;

        if (!player.canPlayerEdit(x, y, z, side, stack)) {
            return false;
        } else {
        	
            if (world.isAirBlock(x, y, z)) {
                world.playSoundEffect((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, "fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
                world.setBlock(x, y, z, ModBlocks.balefire);
            }

            stack.damageItem(1, player);
            return true;
        }
    }
}
