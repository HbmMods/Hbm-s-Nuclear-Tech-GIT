package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.potion.HbmPotion;
import com.hbm.saveddata.RadiationSavedData;

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

	private float radIn = 0.0F;
	private float radMax = 0.0F;

	public BlockOre(Material mat) {
		super(mat);
	}

	public BlockOre(Material mat, boolean tick) {
		super(mat);
		this.setTickRandomly(tick);
	}

	public BlockOre(Material mat, float rad, float max) {
		super(mat);
		this.setTickRandomly(true);
		radIn = rad;
		radMax = max;
	}

	@Override
	public Item getItemDropped(int i, Random rand, int j) {
		if(this == ModBlocks.ore_fluorite || this == ModBlocks.basalt_fluorite) {
			return ModItems.fluorite;
		}
		if(this == ModBlocks.ore_niter) {
			return ModItems.niter;
		}
		if(this == ModBlocks.ore_sulfur || this == ModBlocks.ore_nether_sulfur || this == ModBlocks.ore_meteor_sulfur || this == ModBlocks.basalt_sulfur) {
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
		if(this == ModBlocks.block_meteor_treasure) {
			switch(rand.nextInt(36)) {
			case 0:
				return ModItems.coil_advanced_alloy;
			case 1:
				return ModItems.plate_advanced_alloy;
			case 2:
				return ModItems.powder_desh_mix;
			case 3:
				return ModItems.ingot_desh;
			case 4:
				return ModItems.battery_advanced;
			case 5:
				return ModItems.battery_lithium_cell;
			case 6:
				return ModItems.battery_advanced_cell;
			case 7:
				return ModItems.nugget_schrabidium;
			case 8:
				return ModItems.ingot_plutonium;
			case 9:
				return ModItems.ingot_thorium_fuel;
			case 10:
				return ModItems.ingot_u233;
			case 11:
				return ModItems.turbine_tungsten;
			case 12:
				return ModItems.ingot_dura_steel;
			case 13:
				return ModItems.ingot_polymer;
			case 14:
				return ModItems.ingot_tungsten;
			case 15:
				return ModItems.ingot_combine_steel;
			case 16:
				return ModItems.ingot_lanthanium;
			case 17:
				return ModItems.ingot_actinium;
			case 18:
				return Item.getItemFromBlock(ModBlocks.block_meteor);
			case 19:
				return Item.getItemFromBlock(ModBlocks.fusion_heater);
			case 20:
				return Item.getItemFromBlock(ModBlocks.fusion_core);
			case 21:
				return Item.getItemFromBlock(ModBlocks.watz_element);
			case 22:
				return Item.getItemFromBlock(ModBlocks.ore_rare);
			case 23:
				return Item.getItemFromBlock(ModBlocks.fusion_conductor);
			case 24:
				return Item.getItemFromBlock(ModBlocks.reactor_computer);
			case 25:
				return Item.getItemFromBlock(ModBlocks.machine_diesel);
			case 26:
				return Item.getItemFromBlock(ModBlocks.machine_rtg_grey);
			case 27:
				return ModItems.pellet_rtg;
			case 28:
				return ModItems.pellet_rtg_weak;
			case 29:
				return ModItems.rtg_unit;
			case 30:
				return ModItems.gun_spark_ammo;
			case 31:
				return ModItems.ammo_nuke_low;
			case 32:
				return ModItems.gun_mirv_ammo;
			case 33:
				return ModItems.gun_defabricator_ammo;
			case 34:
				return ModItems.gun_osipr_ammo2;
			case 35:
				return ModItems.glitch;
			}
		}
		if(this == ModBlocks.ore_rare || this == ModBlocks.ore_gneiss_rare) {
			switch(rand.nextInt(6)) {
			case 0:
				return ModItems.fragment_boron;
			case 1:
				return ModItems.fragment_cerium;
			case 2:
				return ModItems.fragment_cobalt;
			case 3:
				return ModItems.fragment_lanthanium;
			case 4:
				return ModItems.fragment_neodymium;
			case 5:
				return ModItems.fragment_niobium;
			}
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
		if(this == ModBlocks.ore_asbestos || this == ModBlocks.ore_gneiss_asbestos || this == ModBlocks.basalt_asbestos) {
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

		return Item.getItemFromBlock(this);
	}

	@Override
	public int quantityDropped(Random rand) {
		if(this == ModBlocks.ore_fluorite || this == ModBlocks.basalt_fluorite) {
			return 2 + rand.nextInt(3);
		}
		if(this == ModBlocks.ore_niter) {
			return 2 + rand.nextInt(3);
		}
		if(this == ModBlocks.ore_sulfur || this == ModBlocks.ore_nether_sulfur || this == ModBlocks.ore_meteor_sulfur || this == ModBlocks.basalt_sulfur) {
			return 2 + rand.nextInt(3);
		}
		if(this == ModBlocks.ore_rare || this == ModBlocks.ore_gneiss_rare) {
			return 4 + rand.nextInt(8);
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

	@Override
	public int damageDropped(int p_149692_1_) {
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
		if(entity instanceof EntityLivingBase && this == ModBlocks.sellafield_0) {
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(HbmPotion.radiation.id, 30 * 20, 0));
		}
		if(entity instanceof EntityLivingBase && this == ModBlocks.sellafield_1) {
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(HbmPotion.radiation.id, 30 * 20, 1));
		}
		if(entity instanceof EntityLivingBase && this == ModBlocks.sellafield_2) {
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(HbmPotion.radiation.id, 30 * 20, 2));
		}
		if(entity instanceof EntityLivingBase && this == ModBlocks.sellafield_3) {
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(HbmPotion.radiation.id, 30 * 20, 3));
		}
		if(entity instanceof EntityLivingBase && this == ModBlocks.sellafield_4) {
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(HbmPotion.radiation.id, 30 * 20, 4));
		}
		if(entity instanceof EntityLivingBase && this == ModBlocks.sellafield_core) {
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(HbmPotion.radiation.id, 30 * 20, 5));
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

		if(this.radIn > 0) {

			RadiationSavedData.incrementRad(world, x, z, radIn, radMax);

			world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
		}
	}

	@Override
	public int tickRate(World world) {

		if(this.radIn > 0)
			return 20;

		return 100;
	}

	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);

		if(this.radIn > 0)
			world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
	}

	@Override
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int i) {

		if(this == ModBlocks.block_meteor_molten) {
			if(!world.isRemote)
				world.setBlock(x, y, z, Blocks.lava);
		}
	}
}
