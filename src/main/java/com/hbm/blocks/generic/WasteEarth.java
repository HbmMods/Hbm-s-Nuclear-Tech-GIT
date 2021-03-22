package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.potion.HbmPotion;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockMushroom;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class WasteEarth extends Block {

	@SideOnly(Side.CLIENT)
	private IIcon iconTop;
	@SideOnly(Side.CLIENT)
	private IIcon iconBottom;

	public WasteEarth(Material mat, boolean tick) {
		super(mat);
		this.setTickRandomly(tick);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.iconTop = iconRegister.registerIcon(RefStrings.MODID + (this == ModBlocks.waste_earth ? ":waste_grass_top" : (this == ModBlocks.waste_mycelium ? ":waste_mycelium_top" : ":frozen_grass_top")));
		this.iconBottom = iconRegister.registerIcon(RefStrings.MODID + (this == ModBlocks.waste_earth ? ":waste_earth_bottom" : (this == ModBlocks.waste_mycelium ? ":waste_earth_bottom" : ":frozen_dirt")));
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + (this == ModBlocks.waste_earth ? ":waste_grass_side" : (this == ModBlocks.waste_mycelium ? ":waste_mycelium_side" : ":frozen_grass_side")));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return side == 1 ? this.iconTop : (side == 0 ? this.iconBottom : this.blockIcon);
	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		if(this == ModBlocks.waste_earth || this == ModBlocks.waste_mycelium) {
			return Item.getItemFromBlock(Blocks.dirt);
		}

		if(this == ModBlocks.frozen_grass) {
			return Items.snowball;
		}

		return Item.getItemFromBlock(this);
	}

	@Override
	public int quantityDropped(Random p_149745_1_) {
		return 1;
	}

	@Override
	public void onEntityWalking(World p_149724_1_, int p_149724_2_, int p_149724_3_, int p_149724_4_, Entity entity) {
		if(entity instanceof EntityLivingBase && this == ModBlocks.frozen_grass) {

			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 2 * 60 * 20, 2));
		}
		if(entity instanceof EntityLivingBase && this == ModBlocks.waste_mycelium) {

			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(HbmPotion.radiation.id, 30 * 20, 3));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_) {
		super.randomDisplayTick(p_149734_1_, p_149734_2_, p_149734_3_, p_149734_4_, p_149734_5_);
		if(this == ModBlocks.waste_mycelium) {
			p_149734_1_.spawnParticle("townaura", p_149734_2_ + p_149734_5_.nextFloat(), p_149734_3_ + 1.1F, p_149734_4_ + p_149734_5_.nextFloat(), 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {

		if((this == ModBlocks.waste_earth || this == ModBlocks.waste_mycelium) && world.getBlock(x, y + 1, z) == Blocks.air && rand.nextInt(1000) == 0) {
			Block b0;
			int count = 0;
			for(int i = -5; i < 5; i++) {
				for(int j = -5; j < 6; j++) {
					for(int k = -5; k < 5; k++) {
						b0 = world.getBlock(x + i, y + j, z + k);
						if((b0 instanceof BlockMushroom) || b0 == ModBlocks.mush) {
							count++;
						}
					}
				}
			}
			if(count > 0 && count < 5)
				world.setBlock(x, y + 1, z, ModBlocks.mush);
		}

		if(this == ModBlocks.waste_mycelium && GeneralConfig.enableMycelium) {
			for(int i = -1; i < 2; i++) {
				for(int j = -1; j < 2; j++) {
					for(int k = -1; k < 2; k++) {
						Block b0 = world.getBlock(x + i, y + j, z + k);
						Block b1 = world.getBlock(x + i, y + j + 1, z + k);
						if(!b1.isOpaqueCube() && (b0 == Blocks.dirt || b0 == Blocks.grass || b0 == Blocks.mycelium || b0 == ModBlocks.waste_earth)) {
							world.setBlock(x + i, y + j, z + k, ModBlocks.waste_mycelium);
						}
					}
				}
			}

			if(rand.nextInt(10) == 0) {
				Block b0;
				int count = 0;
				for(int i = -5; i < 5; i++) {
					for(int j = -5; j < 6; j++) {
						for(int k = -5; k < 5; k++) {
							b0 = world.getBlock(x + i, y + j, z + k);
							if(b0 == ModBlocks.mush) {
								count++;
							}
						}
					}
				}
				if(count < 5)
					world.setBlock(x, y + 1, z, ModBlocks.mush);
			}
		}

		if(this == ModBlocks.waste_earth || this == ModBlocks.waste_mycelium) {
			if(!world.isRemote) {
				if(world.getBlockLightValue(x, y + 1, z) < 4 && world.getBlockLightOpacity(x, y + 1, z) > 2) {
					world.setBlock(x, y, z, Blocks.dirt);
				}
			}
		}

		if(GeneralConfig.enableAutoCleanup && (this == ModBlocks.waste_earth | this == ModBlocks.waste_mycelium))
			if(!world.isRemote)
				world.setBlock(x, y, z, Blocks.dirt);
	}
}
