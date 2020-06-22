package com.hbm.blocks.generic;

import java.util.ArrayList;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class WasteLog extends Block {
	
	@SideOnly(Side.CLIENT)
	private IIcon iconTop;

	public WasteLog(Material p_i45394_1_) {
		super(p_i45394_1_);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.iconTop = iconRegister.registerIcon(RefStrings.MODID + (this == ModBlocks.waste_log ? ":waste_log_top" : ":frozen_log_top"));
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + (this == ModBlocks.waste_log ? ":waste_log_side" : ":frozen_log"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return side == 1 ? this.iconTop : (side == 0 ? this.iconTop : this.blockIcon);
	}

	@Override
	public Item getItemDropped(int i, Random rand, int j)
    {
		if(this == ModBlocks.waste_log)
		{
			return Items.coal;
		}
		if(this == ModBlocks.frozen_log)
		{
			return Items.snowball;
		}
		
		return null;
    }

    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
    	
    	if(this == ModBlocks.waste_log && world.rand.nextInt(1000) == 0) {
	        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
	        ret.add(new ItemStack(ModItems.burnt_bark));
	        return ret;
    	}
    	
    	return super.getDrops(world, x, y, z, metadata, fortune);
    }
    
    @Override
	public int quantityDropped(Random p_149745_1_)
    {
    	return 2 + p_149745_1_.nextInt(3);
    }
    
    @Override
	public int damageDropped(int p_149692_1_)
    {
        return 1;
    }

}