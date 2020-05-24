package com.hbm.items.tool;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

public class RedstoneSword extends ItemSword {
	
	//Pridenauer you damn bastard.

	public RedstoneSword(ToolMaterial p_i45356_1_) {
		super(p_i45356_1_);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        return true;
    }
	
	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
        if (p_77648_7_ == 0)
        {
            --y;
        }

        if (p_77648_7_ == 1)
        {
            ++y;
        }

        if (p_77648_7_ == 2)
        {
            --z;
        }

        if (p_77648_7_ == 3)
        {
            ++z;
        }

        if (p_77648_7_ == 4)
        {
            --x;
        }

        if (p_77648_7_ == 5)
        {
            ++x;
        }

        if (!player.canPlayerEdit(x, y, z, p_77648_7_, itemStack))
        {
            return false;
        }
        else
        {
            if (world.isAirBlock(x, y, z))
            {
                world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "random.break", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
                world.setBlock(x, y, z, Blocks.redstone_wire);
            }

            itemStack.damageItem(14, player);
            return true;
        }
    }
}
