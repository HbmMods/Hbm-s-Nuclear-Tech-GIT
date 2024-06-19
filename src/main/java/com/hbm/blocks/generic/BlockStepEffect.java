package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
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

public class BlockStepEffect extends Block {

    // Fucking BlockOre replacement because who needs good naming
    
	public BlockStepEffect(Material mat) {
        super(mat);
    }

    public BlockStepEffect(Material mat, boolean tick) {
        super(mat);
		this.setTickRandomly(tick);
    }
	
	public boolean allowFortune = true;
	
	public BlockStepEffect noFortune() {
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
	public Item getItemDropped(int i, Random rand, int j) {
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
		if(this == ModBlocks.block_meteor_molten) {
			return null;
		}
		if(this == ModBlocks.ore_nether_cobalt) {
			return ModItems.fragment_cobalt;
		}
		if(this == ModBlocks.ore_nether_sulfur) {
			return ModItems.sulfur;
		}
		if(this == ModBlocks.ore_gneiss_rare) {
			return ModItems.chunk_ore;
		}
		if(this == ModBlocks.ore_gneiss_asbestos) {
			return ModItems.ingot_asbestos;
		}

		return Item.getItemFromBlock(this);
	}

	@Override
	public int quantityDropped(Random rand) {
		if(this == ModBlocks.ore_nether_sulfur) {
			return 2 + rand.nextInt(3);
		}
		if(this == ModBlocks.block_meteor_broken) {
			return 1 + rand.nextInt(3);
		}
		if(this == ModBlocks.block_meteor_treasure) {
			return 1 + rand.nextInt(3);
		}
		if(this == ModBlocks.ore_nether_cobalt) {
			return 5 + rand.nextInt(8);
		}
		return 1;
	}

    @Override
	public void onEntityWalking(World p_149724_1_, int p_149724_2_, int p_149724_3_, int p_149724_4_, Entity entity) {
		if(entity instanceof EntityLivingBase) {
			if(this == ModBlocks.frozen_dirt) {
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 2 * 60 * 20, 2));
			}
			if(this == ModBlocks.block_trinitite) {
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(HbmPotion.radiation.id, 30 * 20, 2));
			}
			if(this == ModBlocks.block_waste) {
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(HbmPotion.radiation.id, 30 * 20, 2));
			}
			if((this == ModBlocks.waste_trinitite || this == ModBlocks.waste_trinitite_red)) {
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(HbmPotion.radiation.id, 30 * 20, 0));
			}
			if(this == ModBlocks.brick_jungle_ooze) {
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(HbmPotion.radiation.id, 15 * 20, 9));
			}
			if(this == ModBlocks.brick_jungle_mystic) {
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(HbmPotion.taint.id, 15 * 20, 2));
			}
		}

		if(this == ModBlocks.block_meteor_molten)
			entity.setFire(5);
	}

	@Override
	public int damageDropped(int meta) {
		if(this == ModBlocks.ore_gneiss_rare) return EnumChunkType.RARE.ordinal();
		return this == ModBlocks.waste_planks ? 1 : meta;
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
	public void updateTick(World world, int x, int y, int z, Random rand) {
		if(this == ModBlocks.block_meteor_molten) {
			if(!world.isRemote)
				world.setBlock(x, y, z, ModBlocks.block_meteor_cobble);
			world.playSoundEffect((double) ((float) x + 0.5F), (double) ((float) y + 0.5F), (double) ((float) z + 0.5F), "random.fizz", 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
			return;
		}
	}

	@Override
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int i) {

		if(this == ModBlocks.block_meteor_molten) {
			if(!world.isRemote) world.setBlock(x, y, z, Blocks.lava);
		}
	}

}
