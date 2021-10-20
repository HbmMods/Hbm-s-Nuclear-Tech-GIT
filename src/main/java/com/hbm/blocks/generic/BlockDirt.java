package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.interfaces.Spaghetti;
import com.hbm.items.ModItems;
import com.hbm.main.ModEventHandler;
import com.hbm.potion.HbmPotion;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class BlockDirt extends Block {

	public BlockDirt(Material mat) {
		super(mat);
	}

	public BlockDirt(Material mat, boolean tick) {
		super(mat);
		this.setTickRandomly(tick);
	}

	@Override
	public Item getItemDropped(int i, Random rand, int j) {
				
		return Item.getItemFromBlock(Blocks.dirt);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		for(int i = -1; i < 2; i++) {
			for(int j = -1; j < 2; j++) {
				for(int k = -1; k < 2; k++) {
					Block b = world.getBlock(x + i, y + j, z + k);
					if(b instanceof BlockGrass)
					{
						world.setBlock(x, y, z, Blocks.dirt);
					}
				}
			}
		}
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		if(!world.isRemote)
		{
			int light = Math.max(world.getSavedLightValue(EnumSkyBlock.Block, x, y+1, z),(int)(world.getBlockLightValue(x, y + 1, z)*(1-ModEventHandler.dust)));
			if(light >= 9 && ModEventHandler.fire==0) {			
				world.setBlock(x, y, z, Blocks.grass);
			}
		}
	}
}
