package com.hbm.explosion;

import java.util.HashSet;
import java.util.List;
import java.util.Random;

import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings.GameType;
import net.minecraftforge.common.util.ForgeDirection;

import com.hbm.blocks.DecoBlockAlt;
import com.hbm.blocks.ModBlocks;
import com.hbm.entity.effect.EntityNukeCloudSmall;
import com.hbm.entity.grenade.EntityGrenadeASchrab;
import com.hbm.entity.grenade.EntityGrenadeNuclear;
import com.hbm.entity.missile.EntityMIRV;
import com.hbm.entity.projectile.EntityMiniMIRV;
import com.hbm.entity.projectile.EntityMiniNuke;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.ISource;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;

public class ExplosionNukeGeneric {

	private final static Random field_149933_a = new Random();

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
<<<<<<< HEAD
		float f = bombStartStrength;
		HashSet hashset = new HashSet();
		int i;
		int j;
		int k;
		double d5;
		double d6;
		double d7;
		double wat = bombStartStrength/** 2 */
		;
		boolean isOccupied = false;

		// bombStartStrength *= 2.0F;
		i = MathHelper.floor_double(x - wat - 1.0D);
		j = MathHelper.floor_double(x + wat + 1.0D);
		k = MathHelper.floor_double(y - wat - 1.0D);
		int i2 = MathHelper.floor_double(y + wat + 1.0D);
		int l = MathHelper.floor_double(z - wat - 1.0D);
		int j2 = MathHelper.floor_double(z + wat + 1.0D);
		List list = world.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(i, k, l, j, i2, j2));
		Vec3 vec3 = Vec3.createVectorHelper(x, y, z);

		for (int i1 = 0; i1 < list.size(); ++i1) {
			Entity entity = (Entity) list.get(i1);
			double d4 = entity.getDistance(x, y, z) / bombStartStrength;

			if (d4 <= 1.0D) {
				d5 = entity.posX - x;
				d6 = entity.posY + entity.getEyeHeight() - y;
				d7 = entity.posZ - z;
				double d9 = MathHelper.sqrt_double(d5 * d5 + d6 * d6 + d7 * d7);
				if (d9 < wat && !(entity instanceof EntityOcelot) && !(entity instanceof EntityNukeCloudSmall)
						&& !(entity instanceof EntityMIRV) && !(entity instanceof EntityMiniNuke)
						&& !(entity instanceof EntityMiniMIRV) && !(entity instanceof EntityGrenadeASchrab)
						&& !(entity instanceof EntityGrenadeNuclear)
						&& !(entity instanceof EntityPlayer
								&& Library.checkArmor((EntityPlayer) entity, ModItems.euphemium_helmet,
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
						entity.attackEntityFrom(ModDamageSource.nuclearBlast, 10F);
						entity.setFire(5);
						double d8 = EnchantmentProtection.func_92092_a(entity, d11);
						entity.motionX += d5 * d8;
						entity.motionY += d6 * d8;
						entity.motionZ += d7 * d8;
					}
				}
			}
		}

		bombStartStrength = (int) f;
=======
				float f = bombStartStrength;
		        HashSet hashset = new HashSet();
		        int i;
		        int j;
		        int k;
		        double d5;
		        double d6;
		        double d7;
		        double wat = bombStartStrength/**2*/;
		        boolean isOccupied = false;
		        

		        //bombStartStrength *= 2.0F;
		        i = MathHelper.floor_double(x - wat - 1.0D);
		        j = MathHelper.floor_double(x + wat + 1.0D);
		        k = MathHelper.floor_double(y - wat - 1.0D);
		        int i2 = MathHelper.floor_double(y + wat + 1.0D);
		        int l = MathHelper.floor_double(z - wat - 1.0D);
		        int j2 = MathHelper.floor_double(z + wat + 1.0D);
		        List list = world.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(i, k, l, j, i2, j2));
		        Vec3 vec3 = Vec3.createVectorHelper(x, y, z);

		        for (int i1 = 0; i1 < list.size(); ++i1)
		        {
		            Entity entity = (Entity)list.get(i1);
		            double d4 = entity.getDistance(x, y, z) / bombStartStrength;

		            if (d4 <= 1.0D)
		            {
		                d5 = entity.posX - x;
		                d6 = entity.posY + entity.getEyeHeight() - y;
		                d7 = entity.posZ - z;
		                double d9 = MathHelper.sqrt_double(d5 * d5 + d6 * d6 + d7 * d7);
		                if (d9 < wat && !(entity instanceof EntityOcelot) && !(entity instanceof EntityNukeCloudSmall) && !(entity instanceof EntityMirv) && !(entity instanceof EntityMiniNuke) && !(entity instanceof EntityGrenadeNuclear) && !(entity instanceof EntityPlayer && Library.checkArmor((EntityPlayer)entity, ModItems.euphemium_helmet, ModItems.euphemium_plate, ModItems.euphemium_legs, ModItems.euphemium_boots)))
		                {
		                    d5 /= d9;
		                    d6 /= d9;
		                    d7 /= d9;
		                    //double d10 = (double)world.getBlockDensity(vec3, entity.boundingBox);
		                    //if(d10 > 0) isOccupied = true;
		                    double d11 = (1.0D - d4);// * d10;
		                    if(!(entity instanceof EntityPlayerMP) || (entity instanceof EntityPlayerMP && ((EntityPlayerMP)entity).theItemInWorldManager.getGameType() != GameType.CREATIVE))
		                    {
		                    	//entity.attackEntityFrom(DamageSource.generic, ((int)((d11 * d11 + d11) / 2.0D * 8.0D * bombStartStrength + 1.0D)));
		                    	entity.attackEntityFrom(ModDamageSource.nuclearBlast, 10F);
		                    	entity.setFire(5);
		                    	double d8 = EnchantmentProtection.func_92092_a(entity, d11);
		                    	entity.motionX += d5 * d8;
		                    	entity.motionY += d6 * d8;
		                    	entity.motionZ += d7 * d8;
		                    }
		                }
		            }
		        }

		        bombStartStrength = (int)f;
>>>>>>> 5525318475377d238c79edc90a14ee8fa48397af
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

	public static void destruction(World world, int x, int y, int z) {
		int rand;
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> 5525318475377d238c79edc90a14ee8fa48397af
		if (!world.isRemote) {
			if (world.getBlock(x, y, z) != Blocks.bedrock && world.getBlock(x, y, z) != ModBlocks.reinforced_brick
					&& world.getBlock(x, y, z) != ModBlocks.reinforced_glass
					&& world.getBlock(x, y, z) != ModBlocks.reinforced_light
					&& world.getBlock(x, y, z) != ModBlocks.reinforced_sand
					&& world.getBlock(x, y, z) != ModBlocks.reinforced_lamp_off
					&& world.getBlock(x, y, z) != ModBlocks.reinforced_lamp_on
					&& world.getBlock(x, y, z) != ModBlocks.cmb_brick
					&& world.getBlock(x, y, z) != ModBlocks.cmb_brick_reinforced
					&& !(world.getBlock(x, y, z) instanceof DecoBlockAlt)) {
				if (world.getBlock(x, y, z) == ModBlocks.brick_concrete) {
					rand = field_149933_a.nextInt(8);
					if (rand == 0) {
						world.setBlock(x, y, z, Blocks.gravel, 0, 3);
					}
				} else if (world.getBlock(x, y, z) == ModBlocks.brick_light) {
					rand = field_149933_a.nextInt(2);
					if (rand == 0) {
						world.setBlock(x, y, z, ModBlocks.waste_planks, 0, 3);
					}
				} else if (world.getBlock(x, y, z) == ModBlocks.brick_obsidian) {
					rand = field_149933_a.nextInt(20);
					if (rand == 0) {
						world.setBlock(x, y, z, Blocks.obsidian, 0, 3);
					}
<<<<<<< HEAD
				} else if (world.getBlock(x, y, z) == Blocks.obsidian) {
					world.setBlock(x, y, z, ModBlocks.gravel_obsidian, 0, 3);
				} else {
					world.setBlock(x, y, z, Blocks.air, 0, 3);
=======
				} else if (world.getBlock(x, y, z) == ModBlocks.brick_obsidian) {
					world.setBlock(x, y, z, ModBlocks.gravel_obsidian, 0, 3);
				} else {
					world.setBlock(x, y, z, Blocks.air, 0, 3);
=======
		if(!world.isRemote)
		{
		if(world.getBlock(x, y, z) != Blocks.bedrock && world.getBlock(x, y, z) != ModBlocks.reinforced_brick && world.getBlock(x, y, z) != ModBlocks.reinforced_glass && world.getBlock(x, y, z) != ModBlocks.reinforced_light && world.getBlock(x, y, z) != ModBlocks.reinforced_sand && world.getBlock(x, y, z) != ModBlocks.reinforced_lamp_off && world.getBlock(x, y, z) != ModBlocks.reinforced_lamp_on && world.getBlock(x, y, z) != ModBlocks.cmb_brick && world.getBlock(x, y, z) != ModBlocks.cmb_brick_reinforced && !(world.getBlock(x, y, z) instanceof DecoBlockAlt))
		{
			if(world.getBlock(x, y, z) == ModBlocks.brick_concrete)
			{
				rand = field_149933_a.nextInt(8);
				if(rand == 0)
				{
					world.setBlock(x, y, z, Blocks.gravel, 0, 3);
				}
			}else if(world.getBlock(x, y, z) == ModBlocks.brick_light)
			{
				rand = field_149933_a.nextInt(2);
				if(rand == 0)
				{
					world.setBlock(x, y, z, ModBlocks.waste_planks, 0, 3);
				}
			}else if(world.getBlock(x, y, z) == ModBlocks.brick_obsidian)
			{
				rand = field_149933_a.nextInt(20);
				if(rand == 0)
				{
					world.setBlock(x, y, z, Blocks.obsidian, 0, 3);
>>>>>>> 540fb3d256a0f4ae6a8b1db586f8e9cfd6ed7372
>>>>>>> 5525318475377d238c79edc90a14ee8fa48397af
				}
			}
		}
	}

	public static void vaporDest(World world, int x, int y, int z) {
		if (!world.isRemote) {
			if (world.getBlock(x, y, z) == Blocks.water || world.getBlock(x, y, z) == Blocks.flowing_water
					|| world.getBlock(x, y, z) == Blocks.tallgrass || world.getBlock(x, y, z) == Blocks.leaves
					|| world.getBlock(x, y, z) == Blocks.leaves2 || world.getBlock(x, y, z) == Blocks.double_plant
					|| world.getBlock(x, y, z) == Blocks.cactus || world.getBlock(x, y, z) == Blocks.snow_layer
					|| world.getBlock(x, y, z) == Blocks.reeds || world.getBlock(x, y, z) == Blocks.glass_pane
					|| world.getBlock(x, y, z) == Blocks.stained_glass_pane || world.getBlock(x, y, z) == Blocks.carrots
					|| world.getBlock(x, y, z) == Blocks.potatoes || world.getBlock(x, y, z) == Blocks.wheat
					|| world.getBlock(x, y, z) == Blocks.ladder || world.getBlock(x, y, z) == Blocks.torch
					|| world.getBlock(x, y, z) == Blocks.redstone_torch
					|| world.getBlock(x, y, z) == Blocks.unlit_redstone_torch
					|| world.getBlock(x, y, z) == Blocks.redstone_wire
					|| world.getBlock(x, y, z) == Blocks.unpowered_repeater
					|| world.getBlock(x, y, z) == Blocks.powered_repeater
					|| world.getBlock(x, y, z) == Blocks.wooden_pressure_plate
					|| world.getBlock(x, y, z) == Blocks.stone_pressure_plate
					|| world.getBlock(x, y, z) == Blocks.wooden_button || world.getBlock(x, y, z) == Blocks.stone_button
					|| world.getBlock(x, y, z) == Blocks.lever || world.getBlock(x, y, z) == Blocks.deadbush
					|| world.getBlock(x, y, z) == ModBlocks.red_cable) {
				world.setBlock(x, y, z, Blocks.air);
			}

			if (world.getBlock(x, y, z).isFlammable(world, x, y, z, ForgeDirection.UP)
					&& world.getBlock(x, y + 1, z) == Blocks.air) {
				world.setBlock(x, y + 1, z, Blocks.fire);
			}
		}
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

			if (world.getBlock(x, y, z) == Blocks.glass || world.getBlock(x, y, z) == Blocks.stained_glass
					|| world.getBlock(x, y, z) == Blocks.wooden_door || world.getBlock(x, y, z) == Blocks.iron_door) {
				world.setBlock(x, y, z, Blocks.air);
			}

			else if (world.getBlock(x, y, z) == Blocks.grass) {
				world.setBlock(x, y, z, ModBlocks.waste_earth);
			}

			else if (world.getBlock(x, y, z) == Blocks.mycelium) {
				world.setBlock(x, y, z, ModBlocks.waste_mycelium);
			}
<<<<<<< HEAD

			else if (world.getBlock(x, y, z) == Blocks.sand) {
				rand = field_149933_a.nextInt(20);
				if (rand == 1 && world.getBlockMetadata(x, y, z) == 0) {
					world.setBlock(x, y, z, ModBlocks.waste_trinitite);
				}
				if (rand == 1 && world.getBlockMetadata(x, y, z) == 1) {
					world.setBlock(x, y, z, ModBlocks.waste_trinitite_red);
				}
=======
		}
		
		else if(world.getBlock(x, y, z) == ModBlocks.ore_nether_uranium)
		{
			rand = field_149933_a.nextInt(30);
			if(rand == 1)
			{
				world.setBlock(x, y, z, ModBlocks.ore_nether_schrabidium);
			}
		}
		
		else if(world.getBlock(x, y, z) == Blocks.brown_mushroom_block)
		{
			if(world.getBlockMetadata(x, y, z) == 10)
			{
				world.setBlock(x, y, z, ModBlocks.waste_log);
			} else {
				world.setBlock(x, y, z, Blocks.air);
>>>>>>> 5525318475377d238c79edc90a14ee8fa48397af
			}

			else if (world.getBlock(x, y, z) == Blocks.clay) {
				world.setBlock(x, y, z, Blocks.hardened_clay);
			}

			else if (world.getBlock(x, y, z) == Blocks.mossy_cobblestone) {
				world.setBlock(x, y, z, Blocks.coal_ore);
			}

			else if (world.getBlock(x, y, z) == Blocks.coal_ore) {
				rand = field_149933_a.nextInt(10);
				if (rand == 1 || rand == 2 || rand == 3) {
					world.setBlock(x, y, z, Blocks.diamond_ore);
				}
				if (rand == 9) {
					world.setBlock(x, y, z, Blocks.emerald_ore);
				}
			}

			else if (world.getBlock(x, y, z) == Blocks.log || world.getBlock(x, y, z) == Blocks.log2) {
				world.setBlock(x, y, z, ModBlocks.waste_log);
			}

			else if (world.getBlock(x, y, z) == Blocks.planks) {
				world.setBlock(x, y, z, ModBlocks.waste_planks);
			}

			else if (world.getBlock(x, y, z) == ModBlocks.ore_uranium) {
				rand = field_149933_a.nextInt(30);
				if (rand == 1) {
					world.setBlock(x, y, z, ModBlocks.ore_schrabidium);
				}
			}

			else if (world.getBlock(x, y, z) == ModBlocks.ore_nether_uranium) {
				rand = field_149933_a.nextInt(30);
				if (rand == 1) {
					world.setBlock(x, y, z, ModBlocks.ore_nether_schrabidium);
				}
			}

			else if (world.getBlock(x, y, z) == Blocks.brown_mushroom_block) {
				if (world.getBlockMetadata(x, y, z) == 10) {
					world.setBlock(x, y, z, ModBlocks.waste_log);
				} else {
					world.setBlock(x, y, z, Blocks.air);
				}
			}

			else if (world.getBlock(x, y, z) == Blocks.red_mushroom_block) {
				if (world.getBlockMetadata(x, y, z) == 10) {
					world.setBlock(x, y, z, ModBlocks.waste_log);
				} else {
					world.setBlock(x, y, z, Blocks.air);
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
				rand = field_149933_a.nextInt(20);
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
				rand = field_149933_a.nextInt(30);
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
					world.setBlock(x, y, z, Blocks.air);
				}
			}

			else if (world.getBlock(x, y, z) == Blocks.red_mushroom_block) {
				if (world.getBlockMetadata(x, y, z) == 10) {
					world.setBlock(x, y, z, ModBlocks.waste_log);
				} else {
					world.setBlock(x, y, z, Blocks.air);
				}
			}
		}
	}

	public static void emp(World world, int x, int y, int z) {
		if (!world.isRemote) {

			if (world.getTileEntity(x, y, z) != null && (world.getTileEntity(x, y, z) instanceof ISource
					|| world.getTileEntity(x, y, z) instanceof IConsumer)) {
				world.setBlock(x, y, z, ModBlocks.block_electrical_scrap);
			}

			else if (world.getBlock(x, y, z) == ModBlocks.red_wire_coated || 
					world.getBlock(x, y, z) == ModBlocks.factory_titanium_furnace || 
					world.getBlock(x, y, z) == ModBlocks.factory_titanium_conductor || 
					world.getBlock(x, y, z) == ModBlocks.factory_advanced_furnace || 
					world.getBlock(x, y, z) == ModBlocks.factory_advanced_conductor || 
					world.getBlock(x, y, z) == ModBlocks.reactor_conductor || 
					world.getBlock(x, y, z) == ModBlocks.fusion_conductor || 
					world.getBlock(x, y, z) == ModBlocks.fusion_center || 
					world.getBlock(x, y, z) == ModBlocks.fusion_motor || 
					world.getBlock(x, y, z) == ModBlocks.watz_conductor || 
					world.getBlock(x, y, z) == ModBlocks.fwatz_conductor || 
					world.getBlock(x, y, z) == ModBlocks.fwatz_hatch || 
					world.getBlock(x, y, z) == ModBlocks.fwatz_computer) {
				world.setBlock(x, y, z, ModBlocks.block_electrical_scrap);
			}

			else if (world.getBlock(x, y, z) == ModBlocks.red_cable || 
					world.getBlock(x, y, z) == Blocks.redstone_wire || 
					world.getBlock(x, y, z) == Blocks.powered_repeater || 
					world.getBlock(x, y, z) == Blocks.unpowered_repeater || 
					world.getBlock(x, y, z) == Blocks.activator_rail || 
					world.getBlock(x, y, z) == Blocks.detector_rail || 
					world.getBlock(x, y, z) == Blocks.golden_rail || 
					world.getBlock(x, y, z) == Blocks.redstone_block || 
					world.getBlock(x, y, z) == Blocks.redstone_lamp || 
					world.getBlock(x, y, z) == Blocks.redstone_ore || 
					world.getBlock(x, y, z) == Blocks.redstone_torch || 
					world.getBlock(x, y, z) == Blocks.unlit_redstone_torch || 
					world.getBlock(x, y, z) == Blocks.powered_comparator || 
					world.getBlock(x, y, z) == Blocks.unpowered_comparator) {
				world.setBlock(x, y, z, Blocks.air);
			}

			else if (world.getBlock(x, y, z) == Blocks.dispenser || 
					world.getBlock(x, y, z) == Blocks.dropper || 
					world.getBlock(x, y, z) == Blocks.piston || 
					world.getBlock(x, y, z) == Blocks.piston_extension || 
					world.getBlock(x, y, z) == Blocks.piston_head || 
					world.getBlock(x, y, z) == Blocks.sticky_piston) {
				world.setBlock(x, y, z, Blocks.gravel);
			}
		}
		//world.setBlock(x, y, z, Blocks.air);
	}
}
