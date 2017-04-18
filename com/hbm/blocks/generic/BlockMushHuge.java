package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockMushHuge extends Block {
	
	@SideOnly(Side.CLIENT)
	private IIcon iconTop;

	public BlockMushHuge(Material p_i45394_1_) {
		super(p_i45394_1_);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.iconTop = iconRegister.registerIcon(RefStrings.MODID + (this == ModBlocks.mush_block ? ":mush_block_skin" : ":mush_block_inside"));
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + (this == ModBlocks.mush_block ? ":mush_block_skin" : ":mush_block_stem"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return side == 1 ? this.iconTop : (side == 0 ? this.iconTop : this.blockIcon);
	}
	
    @Override
	public int quantityDropped(Random p_149745_1_)
    {
        int i = p_149745_1_.nextInt(10) - 7;

        if (i < 0)
        {
            i = 0;
        }

        return i;
    }

    @Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return Item.getItemFromBlock(ModBlocks.mush);
    }

    @Override
	@SideOnly(Side.CLIENT)
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
    {
        return Item.getItemFromBlock(ModBlocks.mush);
    }

}
