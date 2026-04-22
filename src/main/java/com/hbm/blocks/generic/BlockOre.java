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
import net.minecraft.entity.player.EntityPlayer;
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
	
	@Override
	public boolean canSilkHarvest(World world, EntityPlayer player, int x, int y, int z, int meta) {
		if(this == ModBlocks.ore_oil) return false;
		return super.canSilkHarvest(world, player, x, y, z, meta);
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
		if(this == ModBlocks.block_meteor_cobble) {
			return ModItems.fragment_meteorite;
		}
		if(this == ModBlocks.block_meteor_broken) {
			return ModItems.fragment_meteorite;
		}
		if(this == ModBlocks.ore_rare || this == ModBlocks.ore_gneiss_rare) {
			return ModItems.chunk_ore;
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
		if(this == ModBlocks.ore_oil) return ModItems.oil_tar;

		return Item.getItemFromBlock(this);
	}

	@Override
	public int quantityDropped(Random rand) {
		if(this == ModBlocks.ore_fluorite) return 2 + rand.nextInt(3);
		if(this == ModBlocks.ore_niter) return 2 + rand.nextInt(3);
		if(this == ModBlocks.ore_sulfur ||
				this == ModBlocks.ore_nether_sulfur) return 2 + rand.nextInt(3);
		if(this == ModBlocks.block_meteor_broken) return 1 + rand.nextInt(3);
		if(this == ModBlocks.block_meteor_treasure) return 1 + rand.nextInt(3);
		if(this == ModBlocks.ore_cobalt) return 4 + rand.nextInt(6);
		if(this == ModBlocks.ore_nether_cobalt) return 5 + rand.nextInt(8);

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
			return this.quantityDropped(rand) * (Math.max(mult, 0) + 1);
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
	public void onEntityWalking(World world, int x, int y, int z, Entity entity) {
		if(entity instanceof EntityLivingBase) {
			EntityLivingBase living = (EntityLivingBase) entity;
			if(this == ModBlocks.frozen_dirt) {
				living.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 2 * 60 * 20, 2));
			}
			if(this == ModBlocks.block_trinitite) {
				living.addPotionEffect(new PotionEffect(HbmPotion.radiation.id, 30 * 20, 2));
			}
			if(this == ModBlocks.block_waste) {
				living.addPotionEffect(new PotionEffect(HbmPotion.radiation.id, 30 * 20, 2));
			}
			if(this == ModBlocks.waste_trinitite || this == ModBlocks.waste_trinitite_red) {
				living.addPotionEffect(new PotionEffect(HbmPotion.radiation.id, 30 * 20, 0));
			}
			if(this == ModBlocks.brick_jungle_ooze) {
				living.addPotionEffect(new PotionEffect(HbmPotion.radiation.id, 15 * 20, 9));
			}
			if(this == ModBlocks.brick_jungle_mystic) {
				living.addPotionEffect(new PotionEffect(HbmPotion.taint.id, 15 * 20, 2));
			}
		}

		if(this == ModBlocks.block_meteor_molten)
			entity.setFire(5);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		super.randomDisplayTick(world, x, y, z, rand);

		if(this == ModBlocks.waste_trinitite || this == ModBlocks.waste_trinitite_red || this == ModBlocks.block_trinitite || this == ModBlocks.block_waste) {
			world.spawnParticle("townaura", x + rand.nextFloat(), y + 1.1F, z + rand.nextFloat(), 0.0D, 0.0D, 0.0D);
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
			if(!world.isRemote) world.setBlock(x, y, z, ModBlocks.block_meteor_cobble);
			world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, "random.fizz", 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
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
