package com.hbm.explosion;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings.GameType;
import net.minecraftforge.common.util.ForgeDirection;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.VersatileConfig;
import com.hbm.entity.grenade.EntityGrenadeASchrab;
import com.hbm.entity.grenade.EntityGrenadeNuclear;
import com.hbm.entity.projectile.EntityBulletBaseNT;
import com.hbm.entity.projectile.EntityExplosiveBeam;
import com.hbm.interfaces.Spaghetti;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;
import com.hbm.util.ArmorUtil;

import api.hbm.energymk2.IEnergyHandlerMK2;
import cofh.api.energy.IEnergyProvider;

@Spaghetti("this sucks ass")
public class ExplosionNukeGeneric {

	private final static Random random = new Random();

	public static void empBlast(World world, int x, int y, int z, int bombStartStrength) {
		int r = bombStartStrength;
		int r2 = r * r;
		int r22 = r2 / 2;
		for (int xx = -r; xx < r; xx++) {
			int X = xx + x;
			int XX = xx * xx;
			for (int yy = -r; yy < r; yy++) {
				int Y = yy + y;
				int YY = XX + yy * yy;
				for (int zz = -r; zz < r; zz++) {
					int Z = zz + z;
					int ZZ = YY + zz * zz;
					if (ZZ < r22) {
						emp(world, X, Y, Z);
					}
				}
			}
		}
	}
	
	public static void dealDamage(World world, double x, double y, double z, double radius) {
		dealDamage(world, x, y, z, radius, 250F);
	}
	
	public static void dealDamage(World world, double x, double y, double z, double radius, float maxDamage) {

		List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(x, y, z, x, y, z).expand(radius, radius, radius));
		
		for(Entity e : list) {
			
			double dist = e.getDistance(x, y, z);
			
			if(dist <= radius) {
				
				double entX = e.posX;
				double entY = e.posY + e.getEyeHeight();
				double entZ = e.posZ;
				
				if(!isExplosionExempt(e) && !Library.isObstructed(world, x, y, z, entX, entY, entZ)) {

					double damage = maxDamage * (radius - dist) / radius;
					e.attackEntityFrom(ModDamageSource.nuclearBlast, (float)damage);
					e.setFire(5);
					
					double knockX = e.posX - x;
					double knockY = e.posY + e.getEyeHeight() - y;
					double knockZ = e.posZ - z;
					
					Vec3 knock = Vec3.createVectorHelper(knockX, knockY, knockZ);
					knock = knock.normalize();
					
					e.motionX += knock.xCoord * 0.2D;
					e.motionY += knock.yCoord * 0.2D;
					e.motionZ += knock.zCoord * 0.2D;
				}
			}
		}
	}
	
	@Spaghetti("just look at it")
	private static boolean isExplosionExempt(Entity e) {
		
		if (e instanceof EntityOcelot ||
				e instanceof EntityGrenadeASchrab ||
				e instanceof EntityGrenadeNuclear ||
				e instanceof EntityExplosiveBeam ||
				e instanceof EntityBulletBaseNT ||
				e instanceof EntityPlayer &&
				ArmorUtil.checkArmor((EntityPlayer) e, ModItems.euphemium_helmet, ModItems.euphemium_plate, ModItems.euphemium_legs, ModItems.euphemium_boots)) {
			return true;
		}
		
		if (e instanceof EntityPlayerMP && ((EntityPlayerMP)e).theItemInWorldManager.getGameType() == GameType.CREATIVE) {
			return true;
		}
		
		return false;
	}

	public static void vapor(World world, int x, int y, int z, int bombStartStrength) {
		int r = bombStartStrength * 2;
		int r2 = r * r;
		int r22 = r2 / 2;
		for (int xx = -r; xx < r; xx++) {
			int X = xx + x;
			int XX = xx * xx;
			for (int yy = -r; yy < r; yy++) {
				int Y = yy + y;
				int YY = XX + yy * yy;
				for (int zz = -r; zz < r; zz++) {
					int Z = zz + z;
					int ZZ = YY + zz * zz;
					if (ZZ < r22)
						vaporDest(world, X, Y, Z);
				}
			}
		}
	}

	public static int destruction(World world, int x, int y, int z) {
		int rand;
		if (!world.isRemote) {
			Block b = world.getBlock(x,y,z);
			if (b.getExplosionResistance(null)>=200f) {	//500 is the resistance of liquids
				//blocks to be spared
				int protection = (int)(b.getExplosionResistance(null)/300f);
				if (b == ModBlocks.brick_concrete) {
					rand = random.nextInt(8);
					if (rand == 0) {
						world.setBlock(x, y, z, Blocks.gravel, 0, 3);
						return 0;
					}
				} else if (b == ModBlocks.brick_light) {
					rand = random.nextInt(3);
					if (rand == 0) {
						world.setBlock(x, y, z, ModBlocks.waste_planks, 0, 3);
						return 0;
					}else if (rand == 1){
						world.setBlock(x,y,z,ModBlocks.block_scrap,0,3);
						return 0;
					}
				} else if (b == ModBlocks.brick_obsidian) {
					rand = random.nextInt(20);
					if (rand == 0) {
						world.setBlock(x, y, z, Blocks.obsidian, 0, 3);
					}
				} else if (b == Blocks.obsidian) {
					world.setBlock(x, y, z, ModBlocks.gravel_obsidian, 0, 3);
					return 0;
				} else if(random.nextInt(protection+3)==0){
					world.setBlock(x, y, z, ModBlocks.block_scrap,0,3);
				}
				return protection;
			}else{//otherwise, kill the block!
				world.setBlock(x, y, z, Blocks.air,0, 2);
			}
		}
		return 0;
	}

	public static int vaporDest(World world, int x, int y, int z) {
		if (!world.isRemote) {
			Block b = world.getBlock(x,y,z);
			if (b.getExplosionResistance(null)<0.5f //most light things
					|| b == Blocks.web || b == ModBlocks.red_cable
					|| b instanceof BlockLiquid) {
				world.setBlock(x, y, z, Blocks.air,0, 2);
				return 0;
			} else if (b.getExplosionResistance(null)<=3.0f && !b.isOpaqueCube()){
				if(b != Blocks.chest && b != Blocks.farmland){
					//destroy all medium resistance blocks that aren't chests or farmland
					world.setBlock(x, y, z, Blocks.air,0,2);
					return 0;
				}
			}
			
			if (b.isFlammable(world, x, y, z, ForgeDirection.UP)
					&& world.getBlock(x, y + 1, z) == Blocks.air) {
				world.setBlock(x, y + 1, z, Blocks.fire,0,2);
			}
			return (int)( b.getExplosionResistance(null)/300f);
		}
		return 0;
	}

	public static void waste(World world, int x, int y, int z, int radius) {
		int r = radius;
		int r2 = r * r;
		int r22 = r2 / 2;
		for (int xx = -r; xx < r; xx++) {
			int X = xx + x;
			int XX = xx * xx;
			for (int yy = -r; yy < r; yy++) {
				int Y = yy + y;
				int YY = XX + yy * yy;
				for (int zz = -r; zz < r; zz++) {
					int Z = zz + z;
					int ZZ = YY + zz * zz;
					if (ZZ < r22 + world.rand.nextInt(r22 / 5)) {
						if (world.getBlock(X, Y, Z) != Blocks.air)
							wasteDest(world, X, Y, Z);
					}
				}
			}
		}
	}

	public static void wasteDest(World world, int x, int y, int z) {
		if (!world.isRemote) {
			int rand;
			Block b = world.getBlock(x,y,z);
			if (b == Blocks.wooden_door || b == Blocks.iron_door) {
				world.setBlock(x, y, z, Blocks.air,0,2);
			}

			else if (b == Blocks.grass) {
				world.setBlock(x, y, z, ModBlocks.waste_earth);
			}

			else if (b == Blocks.mycelium) {
				world.setBlock(x, y, z, ModBlocks.waste_mycelium);
			}

			else if (b == Blocks.sand) {
				rand = random.nextInt(20);
				if (rand == 1 && world.getBlockMetadata(x, y, z) == 0) {
					world.setBlock(x, y, z, ModBlocks.waste_trinitite);
				}
				if (rand == 1 && world.getBlockMetadata(x, y, z) == 1) {
					world.setBlock(x, y, z, ModBlocks.waste_trinitite_red);
				}
			}

			else if (b == Blocks.clay) {
				world.setBlock(x, y, z, Blocks.hardened_clay);
			}

			else if (b == Blocks.mossy_cobblestone) {
				world.setBlock(x, y, z, Blocks.coal_ore);
			}

			else if (b == Blocks.coal_ore) {
				rand = random.nextInt(10);
				if (rand == 1 || rand == 2 || rand == 3) {
					world.setBlock(x, y, z, Blocks.diamond_ore);
				}
				if (rand == 9) {
					world.setBlock(x, y, z, Blocks.emerald_ore);
				}
			}

			else if (b == Blocks.log || b == Blocks.log2) {
				world.setBlock(x, y, z, ModBlocks.waste_log);
			}

			else if (b == Blocks.brown_mushroom_block) {
				if (world.getBlockMetadata(x, y, z) == 10) {
					world.setBlock(x, y, z, ModBlocks.waste_log);
				} else {
					world.setBlock(x, y, z, Blocks.air,0,2);
				}
			}

			else if (b == Blocks.red_mushroom_block) {
				if (world.getBlockMetadata(x, y, z) == 10) {
					world.setBlock(x, y, z, ModBlocks.waste_log);
				} else {
					world.setBlock(x, y, z, Blocks.air,0,2);
				}
			}
			
			else if (b.getMaterial() == Material.wood && b.isOpaqueCube() && b != ModBlocks.waste_log) {
				world.setBlock(x, y, z, ModBlocks.waste_planks);
			}

			else if (b == ModBlocks.ore_uranium) {
				rand = random.nextInt(VersatileConfig.getSchrabOreChance());
				if (rand == 1) {
					world.setBlock(x, y, z, ModBlocks.ore_schrabidium);
				} else {
					world.setBlock(x, y, z, ModBlocks.ore_uranium_scorched);
				}
			}

			else if (b == ModBlocks.ore_nether_uranium) {
				rand = random.nextInt(VersatileConfig.getSchrabOreChance());
				if (rand == 1) {
					world.setBlock(x, y, z, ModBlocks.ore_nether_schrabidium);
				} else {
					world.setBlock(x, y, z, ModBlocks.ore_nether_uranium_scorched);
				}
			}

			else if (b == ModBlocks.ore_gneiss_uranium) {
				rand = random.nextInt(VersatileConfig.getSchrabOreChance());
				if (rand == 1) {
					world.setBlock(x, y, z, ModBlocks.ore_gneiss_schrabidium);
				} else {
					world.setBlock(x, y, z, ModBlocks.ore_gneiss_uranium_scorched);
				}
			}

		}
	}

	public static void wasteNoSchrab(World world, int x, int y, int z, int radius) {
		int r = radius;
		int r2 = r * r;
		int r22 = r2 / 2;
		for (int xx = -r; xx < r; xx++) {
			int X = xx + x;
			int XX = xx * xx;
			for (int yy = -r; yy < r; yy++) {
				int Y = yy + y;
				int YY = XX + yy * yy;
				for (int zz = -r; zz < r; zz++) {
					int Z = zz + z;
					int ZZ = YY + zz * zz;
					if (ZZ < r22 + world.rand.nextInt(r22 / 5)) {
						if (world.getBlock(X, Y, Z) != Blocks.air)
							wasteDestNoSchrab(world, X, Y, Z);
					}
				}
			}
		}
	}

	public static void wasteDestNoSchrab(World world, int x, int y, int z) {
		if (!world.isRemote) {
			int rand;

			if (world.getBlock(x, y, z) == Blocks.glass || world.getBlock(x, y, z) == Blocks.stained_glass
					|| world.getBlock(x, y, z) == Blocks.wooden_door || world.getBlock(x, y, z) == Blocks.iron_door
					|| world.getBlock(x, y, z) == Blocks.leaves || world.getBlock(x, y, z) == Blocks.leaves2) {
				world.setBlock(x, y, z, Blocks.air);
			}

			else if (world.getBlock(x, y, z) == Blocks.grass) {
				world.setBlock(x, y, z, ModBlocks.waste_earth);
			}

			else if (world.getBlock(x, y, z) == Blocks.mycelium) {
				world.setBlock(x, y, z, ModBlocks.waste_mycelium);
			}

			else if (world.getBlock(x, y, z) == Blocks.sand) {
				rand = random.nextInt(20);
				if (rand == 1 && world.getBlockMetadata(x, y, z) == 0) {
					world.setBlock(x, y, z, ModBlocks.waste_trinitite);
				}
				if (rand == 1 && world.getBlockMetadata(x, y, z) == 1) {
					world.setBlock(x, y, z, ModBlocks.waste_trinitite_red);
				}
			}

			else if (world.getBlock(x, y, z) == Blocks.clay) {
				world.setBlock(x, y, z, Blocks.hardened_clay);
			}

			else if (world.getBlock(x, y, z) == Blocks.mossy_cobblestone) {
				world.setBlock(x, y, z, Blocks.coal_ore);
			}

			else if (world.getBlock(x, y, z) == Blocks.coal_ore) {
				rand = random.nextInt(30);
				if (rand == 1 || rand == 2 || rand == 3) {
					world.setBlock(x, y, z, Blocks.diamond_ore);
				}
				if (rand == 29) {
					world.setBlock(x, y, z, Blocks.emerald_ore);
				}
			}

			else if (world.getBlock(x, y, z) == Blocks.log || world.getBlock(x, y, z) == Blocks.log2) {
				world.setBlock(x, y, z, ModBlocks.waste_log);
			}

			else if (world.getBlock(x, y, z) == Blocks.planks) {
				world.setBlock(x, y, z, ModBlocks.waste_planks);
			}

			else if (world.getBlock(x, y, z) == Blocks.brown_mushroom_block) {
				if (world.getBlockMetadata(x, y, z) == 10) {
					world.setBlock(x, y, z, ModBlocks.waste_log);
				} else {
					world.setBlock(x, y, z, Blocks.air,0,2);
				}
			}

			else if (world.getBlock(x, y, z) == Blocks.red_mushroom_block) {
				if (world.getBlockMetadata(x, y, z) == 10) {
					world.setBlock(x, y, z, ModBlocks.waste_log);
				} else {
					world.setBlock(x, y, z, Blocks.air,0,2);
				}
			}
		}
	}

	public static void emp(World world, int x, int y, int z) {
		if (!world.isRemote) {
			
			Block b = world.getBlock(x,y,z);
			TileEntity te = world.getTileEntity(x, y, z);
			
			if (te != null && te instanceof IEnergyHandlerMK2) {
				((IEnergyHandlerMK2)te).setPower(0);
				if(random.nextInt(5) < 1) world.setBlock(x, y, z, ModBlocks.block_electrical_scrap);
			}
			if (te != null && te instanceof IEnergyProvider) {

				((IEnergyProvider)te).extractEnergy(ForgeDirection.UP, ((IEnergyProvider)te).getEnergyStored(ForgeDirection.UP), false);
				((IEnergyProvider)te).extractEnergy(ForgeDirection.DOWN, ((IEnergyProvider)te).getEnergyStored(ForgeDirection.DOWN), false);
				((IEnergyProvider)te).extractEnergy(ForgeDirection.NORTH, ((IEnergyProvider)te).getEnergyStored(ForgeDirection.NORTH), false);
				((IEnergyProvider)te).extractEnergy(ForgeDirection.SOUTH, ((IEnergyProvider)te).getEnergyStored(ForgeDirection.SOUTH), false);
				((IEnergyProvider)te).extractEnergy(ForgeDirection.EAST, ((IEnergyProvider)te).getEnergyStored(ForgeDirection.EAST), false);
				((IEnergyProvider)te).extractEnergy(ForgeDirection.WEST, ((IEnergyProvider)te).getEnergyStored(ForgeDirection.WEST), false);
				
				if(random.nextInt(5) <= 1)
					world.setBlock(x, y, z, ModBlocks.block_electrical_scrap);
			}
			if((b == ModBlocks.fusion_conductor || b == ModBlocks.fusion_motor || b == ModBlocks.fusion_heater) && random.nextInt(10) == 0)
				world.setBlock(x, y, z, ModBlocks.block_electrical_scrap);
		}
	}

	public static void solinium(World world, int x, int y, int z) {
		if (!world.isRemote) {
			Block b = world.getBlock(x,y,z);
			Material m = b.getMaterial();
			
			if(b == Blocks.grass || b == Blocks.mycelium || b == ModBlocks.waste_earth || b == ModBlocks.waste_mycelium) {
				world.setBlock(x, y, z, Blocks.dirt);
				return;
			}
			
			if(m == Material.cactus || m == Material.coral || m == Material.leaves || m == Material.plants || m == Material.sponge || m == Material.vine || m == Material.gourd || m == Material.wood) {
				world.setBlockToAir(x, y, z);
			}
		}
	}
}
