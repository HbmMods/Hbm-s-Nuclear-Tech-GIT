package com.hbm.explosion;

import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.grenade.EntityGrenadeTau;
import com.hbm.entity.grenade.EntityGrenadeZOMG;
import com.hbm.entity.item.EntityFallingBlockNT;
import com.hbm.entity.particle.EntityCloudFX;
import com.hbm.entity.particle.EntityModFX;
import com.hbm.entity.particle.EntityOrangeFX;
import com.hbm.entity.particle.EntityPinkCloudFX;
import com.hbm.entity.projectile.EntityBullet;
import com.hbm.entity.projectile.EntityRainbow;
import com.hbm.entity.projectile.EntityRocket;
import com.hbm.entity.projectile.EntityRubble;
import com.hbm.entity.projectile.EntitySchrab;
import com.hbm.interfaces.Spaghetti;
import com.hbm.lib.ModDamageSource;
import com.hbm.potion.HbmPotion;
import com.hbm.util.ArmorRegistry;
import com.hbm.util.ArmorUtil;
import com.hbm.util.ArmorRegistry.HazardClass;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

@Deprecated
@Spaghetti("my eyes are bleeding")
public class ExplosionChaos { //TODO: destroy this entire class

	private final static Random random = new Random();
	private static Random rand = new Random();

	public static void hardenVirus(World world, int x, int y, int z, int bombStartStrength) {

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
						if (world.getBlock(X, Y, Z) == ModBlocks.crystal_virus)
							world.setBlock(X, Y, Z, ModBlocks.crystal_hardened);
					}
				}
			}
		}
	}

	public static void spreadVirus(World world, int x, int y, int z, int bombStartStrength) {

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
						if (rand.nextInt(15) == 0 && world.getBlock(X, Y, Z) != Blocks.air)
							world.setBlock(X, Y, Z, ModBlocks.cheater_virus_seed);
					}
				}
			}
		}
	}

	public static void pulse(World world, int x, int y, int z, int bombStartStrength) {

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
						
						if(world.getBlock(X, Y, Z).getExplosionResistance(null) <= 70) {
							EntityFallingBlockNT entityfallingblock = new EntityFallingBlockNT(world, X + 0.5, Y + 0.5, Z + 0.5, world.getBlock(X, Y, Z), world.getBlockMetadata(X, Y, Z));
							world.spawnEntityInWorld(entityfallingblock);
						}
					}
				}
			}
		}
	}

	public static void explodeZOMG(World world, int x, int y, int z, int bombStartStrength) {

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
						if (!(world.getBlock(X, Y, Z) == Blocks.bedrock && Y <= 0))
							world.setBlock(X, Y, Z, Blocks.air);
					}
				}
			}
		}
	}

	public static void decon(World world, int x, int y, int z, int radius) {

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
					if (ZZ < r22) {
						decontaminate(world, X, Y, Z);
					}
				}
			}
		}
	}

	/**
	 * Sets all flammable blocks on fire
	 * 
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param bound
	 */
	public static void flameDeath(World world, int x, int y, int z, int bound) {

		int r = bound;
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
						if (world.getBlock(X, Y, Z).isFlammable(world, XX, YY, ZZ, ForgeDirection.UP)
								&& world.getBlock(X, Y + 1, Z) == Blocks.air) {
							world.setBlock(X, Y + 1, Z, Blocks.fire);
						}
					}
				}
			}
		}

	}

	/**
	 * Sets all blocks on fire
	 * 
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param bound
	 */
	public static void burn(World world, int x, int y, int z, int bound) {

		int r = bound;
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
						if ((world.getBlock(X, Y + 1, Z) == Blocks.air
								|| world.getBlock(X, Y + 1, Z) == Blocks.snow_layer)
								&& world.getBlock(X, Y, Z) != Blocks.air) {
							world.setBlock(X, Y + 1, Z, Blocks.fire);
						}
					}
				}
			}
		}

	}

	@Deprecated public static void spawnPoisonCloud(World world, double x, double y, double z, int count, double speed, int type) {
		
		for(int i = 0; i < count; i++) {
			
			EntityModFX fx = null;
			
			if(type == 1) {
				fx = new EntityCloudFX(world, x, y, z, 0.0, 0.0, 0.0);
			} else if(type == 2) {
				fx = new EntityPinkCloudFX(world, x, y, z, 0.0, 0.0, 0.0);
			} else {
				fx = new EntityOrangeFX(world, x, y, z, 0.0, 0.0, 0.0);
			}
			
			fx.motionY = rand.nextGaussian() * speed;
			fx.motionX = rand.nextGaussian() * speed;
			fx.motionZ = rand.nextGaussian() * speed;
			world.spawnEntityInWorld(fx);
		}
	}

	public static void spawnVolley(World world, double x, double y, double z, int count, double speed) {
		
		for(int i = 0; i < count; i++) {
			
			EntityModFX fx = new EntityOrangeFX(world, x, y, z, 0.0, 0.0, 0.0);
			
			fx.motionX = rand.nextGaussian() * speed;
			fx.motionZ = rand.nextGaussian() * speed;
			
			fx.motionY = rand.nextDouble() * speed * 7.5D;
			
			world.spawnEntityInWorld(fx);
		}
	}

	public static void cluster(World world, int x, int y, int z, int count, int gravity) {

		double d1 = 0;
		double d2 = 0;
		double d3 = 0;
		EntityRocket fragment;

		for (int i = 0; i < count; i++) {
			d1 = rand.nextDouble();
			d2 = rand.nextDouble();
			d3 = rand.nextDouble();

			if (rand.nextInt(2) == 0) {
				d1 *= -1;
			}

			if (rand.nextInt(2) == 0) {
				d3 *= -1;
			}

			fragment = new EntityRocket(world, x, y, z, d1, d2, d3, 0.0125D);

			world.spawnEntityInWorld(fragment);
		}
	}

	public static void schrab(World world, int x, int y, int z, int count, int gravity) {

		double d1 = 0;
		double d2 = 0;
		double d3 = 0;
		EntitySchrab fragment;

		for (int i = 0; i < count; i++) {
			d1 = rand.nextDouble();
			d2 = rand.nextDouble();
			d3 = rand.nextDouble();

			if (rand.nextInt(2) == 0) {
				d1 *= -1;
			}

			if (rand.nextInt(2) == 0) {
				d3 *= -1;
			}

			fragment = new EntitySchrab(world, x, y, z, d1, d2, d3, 0.0125D);

			world.spawnEntityInWorld(fragment);
		}
	}

	public static void frag(World world, int x, int y, int z, int count, boolean flame, Entity shooter) {

		double d1 = 0;
		double d2 = 0;
		double d3 = 0;
		EntityArrow fragment;

		for (int i = 0; i < count; i++) {
			d1 = rand.nextDouble();
			d2 = rand.nextDouble();
			d3 = rand.nextDouble();

			if (rand.nextInt(2) == 0) {
				d1 *= -1;
			}

			if (rand.nextInt(2) == 0) {
				d3 *= -1;
			}

			fragment = new EntityArrow(world, x, y, z);

			fragment.motionX = d1;
			fragment.motionY = d2;
			fragment.motionZ = d3;
			fragment.shootingEntity = shooter;

			fragment.setIsCritical(true);
			if (flame) {
				fragment.setFire(1000);
			}

			fragment.setDamage(2.5);

			world.spawnEntityInWorld(fragment);
		}
	}

	public static void poison(World world, double x, double y, double z, double range) {
		
		List<EntityLivingBase> affected = world.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(x - range, y - range, z - range, x + range, y + range, z + range));
		
		for(EntityLivingBase entity : affected) {
			
			if(entity.getDistance(x, y, z) > range)
				continue;
			
			if(ArmorRegistry.hasAnyProtection(entity, 3, HazardClass.GAS_LUNG, HazardClass.GAS_BLISTERING)) {
				ArmorUtil.damageGasMaskFilter(entity, 1);
			} else {
				entity.addPotionEffect(new PotionEffect(Potion.blindness.getId(), 5 * 20, 0));
				entity.addPotionEffect(new PotionEffect(Potion.poison.getId(), 20 * 20, 2));
				entity.addPotionEffect(new PotionEffect(Potion.wither.getId(), 1 * 20, 1));
				entity.addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), 30 * 20, 1));
				entity.addPotionEffect(new PotionEffect(Potion.digSlowdown.getId(), 30 * 20, 2));
			}
		}
	}

	public static void pc(World world, double x, double y, double z, double range) {
		
		List<EntityLivingBase> affected = world.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(x - range, y - range, z - range, x + range, y + range, z + range));
		
		for(EntityLivingBase entity : affected) {
			
			if(entity.getDistance(x, y, z) > range)
				continue;
			
			ArmorUtil.damageSuit(entity, 0, 25);
			ArmorUtil.damageSuit(entity, 1, 25);
			ArmorUtil.damageSuit(entity, 2, 25);
			ArmorUtil.damageSuit(entity, 3, 25);
			entity.attackEntityFrom(ModDamageSource.pc, 5);
		}
	}

	public static void c(World world, double x, double y, double z, double range) {
		
		List<EntityLivingBase> affected = world.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(x - range, y - range, z - range, x + range, y + range, z + range));
		
		for(EntityLivingBase entity : affected) {
			
			if(entity.getDistance(x, y, z) > range)
				continue;
			
			ArmorUtil.damageSuit(entity, 0, 25);
			ArmorUtil.damageSuit(entity, 1, 25);
			ArmorUtil.damageSuit(entity, 2, 25);
			ArmorUtil.damageSuit(entity, 3, 25);
			
			if(ArmorUtil.checkForHazmat(entity))
				continue;
			
			if(entity.isPotionActive(HbmPotion.taint.id)) {
				entity.removePotionEffect(HbmPotion.taint.id);
				entity.addPotionEffect(new PotionEffect(HbmPotion.mutation.id, 1 * 60 * 60 * 20, 0, false));
			}
			
			entity.attackEntityFrom(ModDamageSource.cloud, 5);
		}
	}

	public static void floater(World world, int x, int y, int z, int radi, int height) {

		Block save;
		int meta;

		int r = radi;
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
						save = world.getBlock(X, Y, Z);
						meta = world.getBlockMetadata(X, Y, Z);
						world.setBlock(X, Y, Z, Blocks.air);
						if (save != Blocks.air) {
							world.setBlock(X, Y + height, Z, save);
							world.setBlockMetadataWithNotify(X, Y + height, Z, meta, 2);
						}
					}
				}
			}
		}

	}

	public static void move(World world, int x, int y, int z, int radius, int a, int b, int c) {
		float f = radius;
		int i;
		int j;
		int k;
		double d5;
		double d6;
		double d7;
		double wat = radius;
		int rand = 0;

		radius *= 2.0F;
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
				if (entity instanceof EntityLiving && !(entity instanceof EntitySheep)) {
					rand = random.nextInt(2);
					if (rand == 0) {
						((EntityLiving) entity).setCustomNameTag("Dinnerbone");
					} else {
						((EntityLiving) entity).setCustomNameTag("Grumm");
					}
				}

				if (entity instanceof EntitySheep) {
					((EntityLiving) entity).setCustomNameTag("jeb_");
				}

				double d9 = MathHelper.sqrt_double(d5 * d5 + d6 * d6 + d7 * d7);
				if (d9 < wat) {
					entity.setPosition(entity.posX += a, entity.posY += b, entity.posZ += c);
				}
			}
		}

		radius = (int) f;
	}

	public static void plasma(World world, int x, int y, int z, int radius) {
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
					if (ZZ < r22 + world.rand.nextInt(r22 / 2)) {
						if(world.getBlock(X, Y, Z) != ModBlocks.statue_elb_f)
							world.setBlock(X, Y, Z, ModBlocks.plasma);
					}
				}
			}
		}
	}

	public static void tauMeSinPi(World world, double x, double y, double z, int count, Entity shooter,
			EntityGrenadeTau tau) {

		double d1 = 0;
		double d2 = 0;
		double d3 = 0;
		EntityBullet fragment;

		if (shooter != null && shooter instanceof EntityPlayer)
			for (int i = 0; i < count; i++) {
				d1 = rand.nextDouble();
				d2 = rand.nextDouble();
				d3 = rand.nextDouble();

				if (rand.nextInt(2) == 0) {
					d1 *= -1;
				}

				if (rand.nextInt(2) == 0) {
					d2 *= -1;
				}

				if (rand.nextInt(2) == 0) {
					d3 *= -1;
				}

				if (rand.nextInt(5) == 0) {
					fragment = new EntityBullet(world, (EntityPlayer) shooter, 3.0F, 35, 45, false, "tauDay", tau);
					fragment.setDamage(rand.nextInt(301) + 100);
				} else {
					fragment = new EntityBullet(world, (EntityPlayer) shooter, 3.0F, 35, 45, false, "eyyOk", tau);
					fragment.setDamage(rand.nextInt(11) + 35);
				}

				fragment.motionX = d1 * 5;
				fragment.motionY = d2 * 5;
				fragment.motionZ = d3 * 5;
				fragment.shootingEntity = shooter;

				fragment.setIsCritical(true);

				world.spawnEntityInWorld(fragment);
			}
	}

	public static void zomgMeSinPi(World world, double x, double y, double z, int count, Entity shooter,
			EntityGrenadeZOMG zomg) {

		double d1 = 0;
		double d2 = 0;
		double d3 = 0;

		// if (shooter != null && shooter instanceof EntityPlayer)
		for (int i = 0; i < count; i++) {
			d1 = rand.nextDouble();
			d2 = rand.nextDouble();
			d3 = rand.nextDouble();

			if (rand.nextInt(2) == 0) {
				d1 *= -1;
			}

			if (rand.nextInt(2) == 0) {
				d2 *= -1;
			}

			if (rand.nextInt(2) == 0) {
				d3 *= -1;
			}

			EntityRainbow entityZomg = new EntityRainbow(world, (EntityPlayer) shooter, 1F, 10000, 100000, zomg);

			entityZomg.motionX = d1;// * 5;
			entityZomg.motionY = d2;// * 5;
			entityZomg.motionZ = d3;// * 5;
			entityZomg.shootingEntity = shooter;

			world.spawnEntityInWorld(entityZomg);
			world.playSoundAtEntity(zomg, "hbm:weapon.zomgShoot", 10.0F, 0.8F + (rand.nextFloat() * 0.4F));
		}
	}

	public static void levelDown(World world, int x, int y, int z, int radius) {

		if(!world.isRemote)
		for (int i = x - radius; i <= x + radius; i++)
			for (int j = z - radius; j <= z + radius; j++) {
				
				Block b = world.getBlock(i, y, j);
				float k = b.getExplosionResistance(null);
						
				if(k < 6000 && b != Blocks.air) {
					
					EntityRubble rubble = new EntityRubble(world);
					rubble.posX = i + 0.5F;
					rubble.posY = y;
					rubble.posZ = j + 0.5F;
					
					rubble.motionY = 0.025F * 10 + 0.15F;
					rubble.setMetaBasedOnBlock(b, world.getBlockMetadata(i, y, j));
					
					world.spawnEntityInWorld(rubble);
					
					world.setBlock(i, y, j, Blocks.air);
				}
			}
	}

	public static void decontaminate(World world, int x, int y, int z) {
		
		Random random = new Random();

		if (world.getBlock(x, y, z) == ModBlocks.waste_earth && random.nextInt(3) != 0) {
			world.setBlock(x, y, z, Blocks.grass);
		}

		else if (world.getBlock(x, y, z) == ModBlocks.waste_mycelium && random.nextInt(5) == 0) {
			world.setBlock(x, y, z, Blocks.mycelium);
		}

		else if (world.getBlock(x, y, z) == ModBlocks.waste_trinitite && random.nextInt(3) == 0) {
			world.setBlock(x, y, z, Blocks.sand);
		}

		else if (world.getBlock(x, y, z) == ModBlocks.waste_trinitite_red && random.nextInt(3) == 0) {
			world.setBlock(x, y, z, Blocks.sand, 1, 2);
		}

		else if (world.getBlock(x, y, z) == ModBlocks.waste_log && random.nextInt(3) != 0) {
			world.setBlock(x, y, z, Blocks.log);
		}

		else if (world.getBlock(x, y, z) == ModBlocks.waste_planks && random.nextInt(3) != 0) {
			world.setBlock(x, y, z, Blocks.planks);
		}

		else if (world.getBlock(x, y, z) == ModBlocks.block_trinitite && random.nextInt(10) == 0) {
			world.setBlock(x, y, z, ModBlocks.block_lead);
		}

		else if (world.getBlock(x, y, z) == ModBlocks.block_waste && random.nextInt(10) == 0) {
			world.setBlock(x, y, z, ModBlocks.block_lead);
		}
		
		else if(world.getBlock(x, y, z) == ModBlocks.sellafield) {
			int meta = world.getBlockMetadata(x, y, z);
			
			if(meta > 0) {
				if(meta == 5 && random.nextInt(10) == 0)
					world.setBlockMetadataWithNotify(x, y, z, 4, 3);
				else if(random.nextInt(5) == 0)
					world.setBlockMetadataWithNotify(meta, x, y, z, meta - 1);
			} else if(random.nextInt(5) == 0)
				world.setBlock(y, z, meta, ModBlocks.sellafield_slaked);
		}
	}

}
