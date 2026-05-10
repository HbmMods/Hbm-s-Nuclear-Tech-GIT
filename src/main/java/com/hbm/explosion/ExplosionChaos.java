package com.hbm.explosion;

import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.particle.EntityCloudFX;
import com.hbm.entity.particle.EntityModFX;
import com.hbm.entity.particle.EntityOrangeFX;
import com.hbm.entity.particle.EntityPinkCloudFX;
import com.hbm.entity.projectile.EntityBulletBaseMK4;
import com.hbm.interfaces.Spaghetti;
import com.hbm.items.weapon.sedna.factory.XFactoryCatapult;
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

	public static void igniteFlammableBlocks(World world, int x, int y, int z, int bound) {

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
						if (world.getBlock(X, Y, Z).isFlammable(world, XX, YY, ZZ, ForgeDirection.UP) && world.getBlock(X, Y + 1, Z) == Blocks.air) {
							world.setBlock(X, Y + 1, Z, Blocks.fire);
						}
					}
				}
			}
		}

	}

	public static void igniteAllBlocks(World world, int x, int y, int z, int bound) {

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
						if ((world.getBlock(X, Y + 1, Z) == Blocks.air || world.getBlock(X, Y + 1, Z) == Blocks.snow_layer) && world.getBlock(X, Y, Z) != Blocks.air) {
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

	public static void cluster(World world, double x, double y, double z, int count, float yaw, float pitch, float yawRand, float pitchRand, float speed) {

		for (int i = 0; i < count; i++) {
			EntityBulletBaseMK4 bullet = new EntityBulletBaseMK4(world, XFactoryCatapult.cluster_submunition, 50F, 0F, yaw + (float) (yawRand * world.rand.nextGaussian()), pitch + (float) (pitchRand * world.rand.nextGaussian()));
			bullet.setPosition(x, y, z);
			bullet.motionX *= speed;
			bullet.motionY *= speed;
			bullet.motionZ *= speed;
			world.spawnEntityInWorld(bullet);
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
}
