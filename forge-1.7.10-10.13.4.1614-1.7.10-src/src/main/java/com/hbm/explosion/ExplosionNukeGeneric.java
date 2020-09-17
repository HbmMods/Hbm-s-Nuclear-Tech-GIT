package com.hbm.explosion;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings.GameType;
import net.minecraftforge.common.util.ForgeDirection;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.effect.EntityBlackHole;
import com.hbm.entity.effect.EntityNukeCloudSmall;
import com.hbm.entity.grenade.EntityGrenadeASchrab;
import com.hbm.entity.grenade.EntityGrenadeNuclear;
import com.hbm.entity.missile.EntityMIRV;
import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.entity.projectile.EntityExplosiveBeam;
import com.hbm.entity.projectile.EntityMiniMIRV;
import com.hbm.entity.projectile.EntityMiniNuke;
import com.hbm.handler.ArmorUtil;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.ISource;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.bomb.TileEntityTurretBase;

import cofh.api.energy.IEnergyProvider;

public class ExplosionNukeGeneric {

	private final static Random random = new Random();

	public static void detonateTestBomb(World world, int x, int y, int z, int bombStartStrength) {
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
					if (r22 >= 25) {
						if (ZZ < r22 + world.rand.nextInt(r22 / 25)) {
							if (Y >= y)
								destruction(world, X, Y, Z);
						}
					} else {
						if (ZZ < r22) {
							if (Y >= y)
								destruction(world, X, Y, Z);
						}
					}
				}
			}
		}

		for (int xx = -r; xx < r; xx++) {
			int X = xx + x;
			int XX = xx * xx;
			for (int yy = -r; yy < r; yy++) {
				int Y = yy + y;
				int YY = XX + yy * yy * 50;
				for (int zz = -r; zz < r; zz++) {
					int Z = zz + z;
					int ZZ = YY + zz * zz;
					if (ZZ < r22/* +world.rand.nextInt(r22) */) {
						if (Y < y)
							destruction(world, X, Y, Z);
					}
				}
			}
		}
	}

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

	public static void dealDamage(World world, int x, int y, int z, int bombStartStrength) {
		float f = bombStartStrength;
		int i;
		int j;
		int k;
		double d5;
		double d6;
		double d7;
		double wat = bombStartStrength/** 2 */
		;

		// bombStartStrength *= 2.0F;
		i = MathHelper.floor_double(x - wat - 1.0D);
		j = MathHelper.floor_double(x + wat + 1.0D);
		k = MathHelper.floor_double(y - wat - 1.0D);
		int i2 = MathHelper.floor_double(y + wat + 1.0D);
		int l = MathHelper.floor_double(z - wat - 1.0D);
		int j2 = MathHelper.floor_double(z + wat + 1.0D);
		List list = world.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(i, k, l, j, i2, j2));

		for (int i1 = 0; i1 < list.size(); ++i1) {
			Entity entity = (Entity) list.get(i1);
			double d4 = entity.getDistance(x, y, z) / bombStartStrength;

			if (d4 <= 1.0D) {
				d5 = entity.posX - x;
				d6 = entity.posY + entity.getEyeHeight() - y;
				d7 = entity.posZ - z;
				double d9 = MathHelper.sqrt_double(d5 * d5 + d6 * d6 + d7 * d7);
				if(!Library.isObstructed(world, x, y, z, entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ))
				if (d9 < wat && !(entity instanceof EntityOcelot) && !(entity instanceof EntityNukeCloudSmall)
						&& !(entity instanceof EntityMIRV) && !(entity instanceof EntityMiniNuke)
						&& !(entity instanceof EntityMiniMIRV) && !(entity instanceof EntityGrenadeASchrab)
						&& !(entity instanceof EntityGrenadeNuclear) && !(entity instanceof EntityExplosiveBeam)
						&& !(entity instanceof EntityBulletBase) && !(entity instanceof EntityPlayer
								&& ArmorUtil.checkArmor((EntityPlayer) entity, ModItems.euphemium_helmet,
										ModItems.euphemium_plate, ModItems.euphemium_legs, ModItems.euphemium_boots))) {
					d5 /= d9;
					d6 /= d9;
					d7 /= d9;
					// double d10 = (double)world.getBlockDensity(vec3,
					// entity.boundingBox);
					// if(d10 > 0) isOccupied = true;
					double d11 = (1.0D - d4);// * d10;
					if (!(entity instanceof EntityPlayerMP) || (entity instanceof EntityPlayerMP
							&& ((EntityPlayerMP) entity).theItemInWorldManager.getGameType() != GameType.CREATIVE)) {
						// entity.attackEntityFrom(DamageSource.generic,
						// ((int)((d11 * d11 + d11) / 2.0D * 8.0D *
						// bombStartStrength + 1.0D)));
						double damage = entity.getDistance(x, y, z) / bombStartStrength * 250;
						entity.attackEntityFrom(ModDamageSource.nuclearBlast, (float)damage);
						entity.setFire(5);
						double d8 = EnchantmentProtection.func_92092_a(entity, d11);
						entity.motionX += d5 * d8 * 0.2D;
						entity.motionY += d6 * d8 * 0.2D;
						entity.motionZ += d7 * d8 * 0.2D;
					}
				}
			}
		}

		bombStartStrength = (int) f;
	}

	public static void succ(World world, int x, int y, int z, int radius) {
		int i;
		int j;
		int k;
		double d5;
		double d6;
		double d7;
		double wat = radius/** 2 */
		;

		// bombStartStrength *= 2.0F;
		i = MathHelper.floor_double(x - wat - 1.0D);
		j = MathHelper.floor_double(x + wat + 1.0D);
		k = MathHelper.floor_double(y - wat - 1.0D);
		int i2 = MathHelper.floor_double(y + wat + 1.0D);
		int l = MathHelper.floor_double(z - wat - 1.0D);
		int j2 = MathHelper.floor_double(z + wat + 1.0D);
		List list = world.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(i, k, l, j, i2, j2));

		for (int i1 = 0; i1 < list.size(); ++i1) {
			Entity entity = (Entity) list.get(i1);
			
			if(entity instanceof EntityBlackHole)
				continue;
			
			double d4 = entity.getDistance(x, y, z) / radius;

			if (d4 <= 1.0D) {
				d5 = entity.posX - x;
				d6 = entity.posY + entity.getEyeHeight() - y;
				d7 = entity.posZ - z;
				double d9 = MathHelper.sqrt_double(d5 * d5 + d6 * d6 + d7 * d7);
				if (d9 < wat && !(entity instanceof EntityPlayer
								&& ArmorUtil.checkArmor((EntityPlayer) entity, ModItems.euphemium_helmet,
										ModItems.euphemium_plate, ModItems.euphemium_legs, ModItems.euphemium_boots))) {
					d5 /= d9;
					d6 /= d9;
					d7 /= d9;
					
					if (!(entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.isCreativeMode)) {
						double d8 = 0.125 + (random.nextDouble() * 0.25);
						entity.motionX -= d5 * d8;
						entity.motionY -= d6 * d8;
						entity.motionZ -= d7 * d8;
					}
				}
			}
		}
	}

	public static boolean dedify(World world, int x, int y, int z, int radius) {
		int i;
		int j;
		int k;
		double d5;
		double d6;
		double d7;
		double wat = radius/** 2 */
		;

		// bombStartStrength *= 2.0F;
		i = MathHelper.floor_double(x - wat - 1.0D);
		j = MathHelper.floor_double(x + wat + 1.0D);
		k = MathHelper.floor_double(y - wat - 1.0D);
		int i2 = MathHelper.floor_double(y + wat + 1.0D);
		int l = MathHelper.floor_double(z - wat - 1.0D);
		int j2 = MathHelper.floor_double(z + wat + 1.0D);
		List list = world.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(i, k, l, j, i2, j2));

		for (int i1 = 0; i1 < list.size(); ++i1) {
			Entity entity = (Entity) list.get(i1);
			double d4 = entity.getDistance(x, y, z) / radius;

			if (d4 <= 1.0D) {
				d5 = entity.posX - x;
				d6 = entity.posY + entity.getEyeHeight() - y;
				d7 = entity.posZ - z;
				double d9 = MathHelper.sqrt_double(d5 * d5 + d6 * d6 + d7 * d7);
				if (d9 < wat && !(entity instanceof EntityPlayer
								&& ArmorUtil.checkArmor((EntityPlayer) entity, ModItems.euphemium_helmet,
										ModItems.euphemium_plate, ModItems.euphemium_legs, ModItems.euphemium_boots))) {
					d5 /= d9;
					d6 /= d9;
					d7 /= d9;
					// double d10 = (double)world.getBlockDensity(vec3,
					// entity.boundingBox);
					// if(d10 > 0) isOccupied = true;

					if(entity instanceof EntityItem && ((EntityItem)entity).getEntityItem().getItem() == ModItems.flame_pony) {
						entity.setDead();
						return true;
					}
					if(entity instanceof EntityItem && ((EntityItem)entity).getEntityItem().getItem() == ModItems.pellet_antimatter) {
						entity.setDead();
						return true;
					}
						
					if (!(entity instanceof EntityPlayerMP
							&& ((EntityPlayerMP) entity).theItemInWorldManager.getGameType() == GameType.CREATIVE)) {
						entity.attackEntityFrom(ModDamageSource.blackhole, 1000F);
					}
					
					if(!(entity instanceof EntityLivingBase) && !(entity instanceof EntityPlayerMP) && !(entity instanceof EntityBlackHole)) {
						if(random.nextInt(8) == 0)
							entity.setDead();
					}
				}
			}
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
				rand = random.nextInt(MainRegistry.schrabFromUraniumChance);
				if (rand == 1) {
					world.setBlock(x, y, z, ModBlocks.ore_schrabidium);
				} else {
					world.setBlock(x, y, z, ModBlocks.ore_uranium_scorched);
				}
			}

			else if (b == ModBlocks.ore_nether_uranium) {
				rand = random.nextInt(MainRegistry.schrabFromUraniumChance);
				if (rand == 1) {
					world.setBlock(x, y, z, ModBlocks.ore_nether_schrabidium);
				} else {
					world.setBlock(x, y, z, ModBlocks.ore_nether_uranium_scorched);
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
			
			if (te != null && te instanceof ISource) {
				
				((ISource)te).setSPower(0);
				
				if(random.nextInt(5) < 1)
					world.setBlock(x, y, z, ModBlocks.block_electrical_scrap);
			}
			if (te != null && te instanceof IConsumer) {
				
				((IConsumer)te).setPower(0);
				
				if(random.nextInt(5) < 1)
					world.setBlock(x, y, z, ModBlocks.block_electrical_scrap);
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
			if (te != null && te instanceof TileEntityTurretBase) {

				((TileEntityTurretBase)te).ammo = 0;
			}
			if((b == ModBlocks.fusion_conductor || b == ModBlocks.fwatz_conductor || b == ModBlocks.fusion_motor || b == ModBlocks.fusion_heater || b == ModBlocks.fwatz_computer) && random.nextInt(10) == 0)
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
