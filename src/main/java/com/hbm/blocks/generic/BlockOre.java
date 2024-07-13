package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.interfaces.Spaghetti;
import com.hbm.items.ItemEnums.EnumChunkType;
import com.hbm.items.ModItems;
import com.hbm.potion.HbmPotion;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class BlockOre extends Block {

	private float rad = 0.0F;

	public BlockOre(Material mat) {
		super(mat);
	}

	public BlockOre(Material mat, boolean tick) {
		super(mat);
		this.setTickRandomly(tick);
	}

	@Deprecated() //use hazard module for this
	public BlockOre(Material mat, float rad, float max) {
		super(mat);
		this.setTickRandomly(true);
		this.rad = rad;
	}

	@Spaghetti("*throws up*")
	@Override
	public Item getItemDropped(int i, Random rand, int j) {
		if(this == ModBlocks.ore_fluorite) {
			return ModItems.fluorite;
		}
		if(this == ModBlocks.ore_niter) {
			return ModItems.niter;
		}
		if(this == ModBlocks.ore_sulfur || this == ModBlocks.ore_nether_sulfur) {
			return ModItems.sulfur;
		}
		if(this == ModBlocks.waste_trinitite || this == ModBlocks.waste_trinitite_red) {
			return ModItems.trinitite;
		}
		if(this == ModBlocks.waste_planks) {
			return Items.coal;
		}
		if(this == ModBlocks.frozen_dirt) {
			return Items.snowball;
		}
		if(this == ModBlocks.frozen_planks) {
			return Items.snowball;
		}
		if(this == ModBlocks.ore_nether_fire) {
			return rand.nextInt(10) == 0 ? ModItems.ingot_phosphorus : ModItems.powder_fire;
		}
		if(this == ModBlocks.block_meteor) {
			return rand.nextInt(10) == 0 ? ModItems.plate_dalekanium : Item.getItemFromBlock(ModBlocks.block_meteor);
		}
		if(this == ModBlocks.block_meteor_cobble) {
			return ModItems.fragment_meteorite;
		}
		if(this == ModBlocks.block_meteor_broken) {
			return ModItems.fragment_meteorite;
		}
		if(this == ModBlocks.ore_rare || this == ModBlocks.ore_gneiss_rare) {
			return ModItems.chunk_ore;
		}
		if(this == ModBlocks.deco_aluminium) {
			return ModItems.ingot_aluminium;
		}
		if(this == ModBlocks.deco_beryllium) {
			return ModItems.ingot_beryllium;
		}
		if(this == ModBlocks.deco_lead) {
			return ModItems.ingot_lead;
		}
		if(this == ModBlocks.deco_red_copper) {
			return ModItems.ingot_red_copper;
		}
		if(this == ModBlocks.deco_steel) {
			return ModItems.ingot_steel;
		}
		if(this == ModBlocks.deco_titanium) {
			return ModItems.ingot_titanium;
		}
		if(this == ModBlocks.deco_tungsten) {
			return ModItems.ingot_tungsten;
		}
		if(this == ModBlocks.deco_asbestos) {
			return ModItems.ingot_asbestos;
		}
		if(this == ModBlocks.ore_asbestos || this == ModBlocks.ore_gneiss_asbestos) {
			return ModItems.ingot_asbestos;
		}
		if(this == ModBlocks.ore_lignite) {
			return ModItems.lignite;
		}
		if(this == ModBlocks.ore_cinnebar) {
			return ModItems.cinnebar;
		}
		if(this == ModBlocks.ore_coltan) {
			return ModItems.fragment_coltan;
		}
		if(this == ModBlocks.ore_cobalt || this == ModBlocks.ore_nether_cobalt) {
			return ModItems.fragment_cobalt;
		}
		if(this == ModBlocks.block_meteor_molten) {
			return null;
		}

		return Item.getItemFromBlock(this);
	}

	@Override
	public int quantityDropped(Random rand) {
		if(this == ModBlocks.ore_fluorite) {
			return 2 + rand.nextInt(3);
		}
		if(this == ModBlocks.ore_niter) {
			return 2 + rand.nextInt(3);
		}
		if(this == ModBlocks.ore_sulfur || this == ModBlocks.ore_nether_sulfur) {
			return 2 + rand.nextInt(3);
		}
		if(this == ModBlocks.block_meteor_broken) {
			return 1 + rand.nextInt(3);
		}
		if(this == ModBlocks.block_meteor_treasure) {
			return 1 + rand.nextInt(3);
		}
		if(this == ModBlocks.ore_cobalt) {
			return 4 + rand.nextInt(6);
		}
		if(this == ModBlocks.ore_nether_cobalt) {
			return 5 + rand.nextInt(8);
		}

		return 1;
	}
	
	public boolean allowFortune = true;
	
	public BlockOre noFortune() {
		this.allowFortune = false;
		return this;
	}
	
	@Override
	public int quantityDroppedWithBonus(int fortune, Random rand) {
		
		if(fortune > 0 && Item.getItemFromBlock(this) != this.getItemDropped(0, rand, fortune) && allowFortune) {
			int mult = rand.nextInt(fortune + 2) - 1;

			if(mult < 0) {
				mult = 0;
			}

			return this.quantityDropped(rand) * (mult + 1);
		} else {
			return this.quantityDropped(rand);
		}
	}

	@Override
	public int damageDropped(int meta) {
		if(this == ModBlocks.ore_rare || this == ModBlocks.ore_gneiss_rare) return EnumChunkType.RARE.ordinal();
		return this == ModBlocks.waste_planks ? 1 : 0;
	}

	@Override
	public void onEntityWalking(World p_149724_1_, int p_149724_2_, int p_149724_3_, int p_149724_4_, Entity entity) {
		if(entity instanceof EntityLivingBase && this == ModBlocks.frozen_dirt) {
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 2 * 60 * 20, 2));
		}
		if(entity instanceof EntityLivingBase && this == ModBlocks.block_trinitite) {
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(HbmPotion.radiation.id, 30 * 20, 2));
		}
		if(entity instanceof EntityLivingBase && this == ModBlocks.block_waste) {
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(HbmPotion.radiation.id, 30 * 20, 2));
		}
		if(entity instanceof EntityLivingBase && (this == ModBlocks.waste_trinitite || this == ModBlocks.waste_trinitite_red)) {
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(HbmPotion.radiation.id, 30 * 20, 0));
		}
		if(entity instanceof EntityLivingBase && this == ModBlocks.brick_jungle_ooze) {
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(HbmPotion.radiation.id, 15 * 20, 9));
		}
		if(entity instanceof EntityLivingBase && this == ModBlocks.brick_jungle_mystic) {
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(HbmPotion.taint.id, 15 * 20, 2));
		}

		if(this == ModBlocks.block_meteor_molten)
			entity.setFire(5);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_) {
		super.randomDisplayTick(p_149734_1_, p_149734_2_, p_149734_3_, p_149734_4_, p_149734_5_);

		if(this == ModBlocks.waste_trinitite || this == ModBlocks.waste_trinitite_red || this == ModBlocks.block_trinitite || this == ModBlocks.block_waste) {
			p_149734_1_.spawnParticle("townaura", p_149734_2_ + p_149734_5_.nextFloat(), p_149734_3_ + 1.1F, p_149734_4_ + p_149734_5_.nextFloat(), 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		if(world.getBlock(x, y - 1, z) == ModBlocks.ore_oil_empty) {
			world.setBlock(x, y, z, ModBlocks.ore_oil_empty);
			world.setBlock(x, y - 1, z, ModBlocks.ore_oil);
		}
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		if(this == ModBlocks.block_meteor_molten) {
			if(!world.isRemote)
				world.setBlock(x, y, z, ModBlocks.block_meteor_cobble);
			world.playSoundEffect((double) ((float) x + 0.5F), (double) ((float) y + 0.5F), (double) ((float) z + 0.5F), "random.fizz", 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
			return;
		}

		if(this.rad > 0) {
			ChunkRadiationManager.proxy.incrementRad(world, x, y, z, rad);
			world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
		}
	}

	@Override
	public int tickRate(World world) {

		if(this.rad > 0)
			return 20;

		return 100;
	}

	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);

		if(this.rad > 0)
			world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
	}

	@Override
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int i) {

		if(this == ModBlocks.block_meteor_molten) {
			if(!world.isRemote) world.setBlock(x, y, z, Blocks.lava);
		}
	}
}
