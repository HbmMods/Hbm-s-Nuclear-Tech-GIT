package com.hbm.blocks.fluid;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.mob.EntityNuclearCreeper;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class MudBlock extends BlockFluidClassic {

	@SideOnly(Side.CLIENT)
	public static IIcon stillIcon;
	@SideOnly(Side.CLIENT)
	public static IIcon flowingIcon;
	public Random rand = new Random();

	public static DamageSource damageSource;

	public MudBlock(Fluid fluid, Material material, DamageSource damage) {
		super(fluid, material);
		damageSource = damage;
		setQuantaPerBlock(4);
		setCreativeTab(null);
		displacements.put(this, false);
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return (side == 0 || side == 1) ? stillIcon : flowingIcon;
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		stillIcon = register.registerIcon(RefStrings.MODID + ":mud_still");
		flowingIcon = register.registerIcon(RefStrings.MODID + ":mud_flowing");
	}

	@Override
	public boolean canDisplace(IBlockAccess world, int x, int y, int z) {

		if (world.getBlock(x, y, z).getMaterial().isLiquid()) {
			return false;
		}
		return super.canDisplace(world, x, y, z);
	}

	@Override
	public boolean displaceIfPossible(World world, int x, int y, int z) {

		if (world.getBlock(x, y, z).getMaterial().isLiquid()) {
			return false;
		}
		return super.displaceIfPossible(world, x, y, z);
	}

	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		entity.setInWeb();
		// if(entity instanceof EntityLivingBase)
		// {
		// entity.attackEntityFrom(ModDamageSource.mudPoisoning, 8);
		// }
		if (entity instanceof EntityPlayer && Library.checkForHazmat((EntityPlayer) entity)) {
			/*
			 * Library.damageSuit(((EntityPlayer)entity), 0);
			 * Library.damageSuit(((EntityPlayer)entity), 1);
			 * Library.damageSuit(((EntityPlayer)entity), 2);
			 * Library.damageSuit(((EntityPlayer)entity), 3);
			 */

		} else if (entity instanceof EntityCreeper) {
			EntityNuclearCreeper creep = new EntityNuclearCreeper(world);
			creep.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
			if (!entity.isDead)
				if (!world.isRemote)
					world.spawnEntityInWorld(creep);
			entity.setDead();
		} else if (entity instanceof EntityVillager) {
			EntityZombie creep = new EntityZombie(world);
			creep.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
			entity.setDead();
			if (!world.isRemote)
				world.spawnEntityInWorld(creep);
		} else if (entity instanceof EntityLivingBase && !(entity instanceof EntityNuclearCreeper)
				&& !(entity instanceof EntityMooshroom) && !(entity instanceof EntityZombie)) {
			entity.attackEntityFrom(ModDamageSource.mudPoisoning, 8);
		}
	}

	public void updateTick(World world, int x, int y, int z, Random rand) {
		super.updateTick(world, x, y, z, rand);
		
		reactToBlocks2(world, x + 1, y, z);
		reactToBlocks2(world, x - 1, y, z);
		reactToBlocks2(world, x, y + 1, z);
		reactToBlocks2(world, x, y - 1, z);
		reactToBlocks2(world, x, y, z + 1);
		reactToBlocks2(world, x, y, z - 1);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
		super.onNeighborBlockChange(world, x, y, z, block);
		
		reactToBlocks(world, x + 1, y, z);
		reactToBlocks(world, x - 1, y, z);
		reactToBlocks(world, x, y + 1, z);
		reactToBlocks(world, x, y - 1, z);
		reactToBlocks(world, x, y, z + 1);
		reactToBlocks(world, x, y, z - 1);
    }
	
	public void reactToBlocks(World world, int x, int y, int z) {
		if(world.getBlock(x, y, z).getMaterial() != ModBlocks.fluidmud) {
			Block block = world.getBlock(x, y, z);
			
			if(block.getMaterial().isLiquid()) {
				world.setBlock(x, y, z, Blocks.air);
			}
		}
	}
	
	public void reactToBlocks2(World world, int x, int y, int z) {
		if(world.getBlock(x, y, z).getMaterial() != ModBlocks.fluidmud) {
			Block block = world.getBlock(x, y, z);

			if (block == Blocks.stone || 
					block == Blocks.stone_brick_stairs || 
					block == Blocks.stonebrick || 
					block == Blocks.stone_slab || 
					block == Blocks.stone) {
				if(rand.nextInt(20) == 0)
					world.setBlock(x, y, z, Blocks.cobblestone);
			} else if (block == Blocks.cobblestone) {
				if(rand.nextInt(15) == 0)
					world.setBlock(x, y, z, Blocks.gravel);
			} else if (block == Blocks.sandstone) {
				if(rand.nextInt(5) == 0)
					world.setBlock(x, y, z, Blocks.sand);
			} else if (block == Blocks.hardened_clay || 
					block == Blocks.stained_hardened_clay) {
				if(rand.nextInt(10) == 0)
					world.setBlock(x, y, z, Blocks.clay);
			} else if (block.getMaterial() == Material.wood) {
				world.setBlock(x, y, z, Blocks.air);
			} else if (block.getMaterial() == Material.cactus) {
				world.setBlock(x, y, z, Blocks.air);
			} else if (block.getMaterial() == Material.cake) {
				world.setBlock(x, y, z, Blocks.air);
			} else if (block.getMaterial() == Material.circuits) {
				world.setBlock(x, y, z, Blocks.air);
			} else if (block.getMaterial() == Material.cloth) {
				world.setBlock(x, y, z, Blocks.air);
			} else if (block.getMaterial() == Material.coral) {
				world.setBlock(x, y, z, Blocks.air);
			} else if (block.getMaterial() == Material.craftedSnow) {
				world.setBlock(x, y, z, Blocks.air);
			} else if (block.getMaterial() == Material.glass) {
				world.setBlock(x, y, z, Blocks.air);
			} else if (block.getMaterial() == Material.gourd) {
				world.setBlock(x, y, z, Blocks.air);
			} else if (block.getMaterial() == Material.ice) {
				world.setBlock(x, y, z, Blocks.air);
			} else if (block.getMaterial() == Material.leaves) {
				world.setBlock(x, y, z, Blocks.air);
			} else if (block.getMaterial() == Material.packedIce) {
				world.setBlock(x, y, z, Blocks.air);
			} else if (block.getMaterial() == Material.piston) {
				world.setBlock(x, y, z, Blocks.air);
			} else if (block.getMaterial() == Material.plants) {
				world.setBlock(x, y, z, Blocks.air);
			} else if (block.getMaterial() == Material.portal) {
				world.setBlock(x, y, z, Blocks.air);
			} else if (block.getMaterial() == Material.redstoneLight) {
				world.setBlock(x, y, z, Blocks.air);
			} else if (block.getMaterial() == Material.snow) {
				world.setBlock(x, y, z, Blocks.air);
			} else if (block.getMaterial() == Material.sponge) {
				world.setBlock(x, y, z, Blocks.air);
			} else if (block.getMaterial() == Material.vine) {
				world.setBlock(x, y, z, Blocks.air);
			} else if (block.getMaterial() == Material.web) {
				world.setBlock(x, y, z, Blocks.air);
			} else if (block.getExplosionResistance(null) < 1.2F) {
				world.setBlock(x, y, z, Blocks.air);
			}
		}
	}

	public int tickRate(World p_149738_1_) {
		return 15;
	}

}
