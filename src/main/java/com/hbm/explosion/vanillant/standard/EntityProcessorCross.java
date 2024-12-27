package com.hbm.explosion.vanillant.standard;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.hbm.entity.projectile.EntityBulletBaseMK4;
import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.interfaces.ICustomDamageHandler;
import com.hbm.explosion.vanillant.interfaces.IEntityProcessor;
import com.hbm.explosion.vanillant.interfaces.IEntityRangeMutator;

import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.ForgeEventFactory;

/** The amount of good decisions in NTM is few and far between, but the VNT explosion surely is one of them. */
public class EntityProcessorCross implements IEntityProcessor {

	protected double nodeDist = 2D;
	protected IEntityRangeMutator range;
	protected ICustomDamageHandler damage;
	protected boolean allowSelfDamage = false;
	
	public EntityProcessorCross(double nodeDist) {
		this.nodeDist = nodeDist;
	}
	
	public EntityProcessorCross setAllowSelfDamage() {
		this.allowSelfDamage = true;
		return this;
	}

	@Override
	public HashMap<EntityPlayer, Vec3> process(ExplosionVNT explosion, World world, double x, double y, double z, float size) {

		HashMap<EntityPlayer, Vec3> affectedPlayers = new HashMap();

		size *= 2.0F;
		
		if(range != null) {
			size = range.mutateRange(explosion, size);
		}
		
		double minX = x - (double) size - 1.0D;
		double maxX = x + (double) size + 1.0D;
		double minY = y - (double) size - 1.0D;
		double maxY = y + (double) size + 1.0D;
		double minZ = z - (double) size - 1.0D;
		double maxZ = z + (double) size + 1.0D;
		
		List list = world.getEntitiesWithinAABBExcludingEntity(allowSelfDamage ? null : explosion.exploder, AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ));
		
		ForgeEventFactory.onExplosionDetonate(world, explosion.compat, list, size);
		
		Vec3[] nodes = new Vec3[7];
		
		for(int i = 0; i < 7; i++) {
			ForgeDirection dir = ForgeDirection.getOrientation(i);
			nodes[i] = Vec3.createVectorHelper(x + dir.offsetX * nodeDist, y + dir.offsetY * nodeDist, z + dir.offsetZ * nodeDist);
		}
		
		HashMap<Entity, Float> damageMap = new HashMap();

		for(int index = 0; index < list.size(); ++index) {
			
			Entity entity = (Entity) list.get(index);
			double distanceScaled = entity.getDistance(x, y, z) / size;

			if(distanceScaled <= 1.0D) {
				
				double deltaX = entity.posX - x;
				double deltaY = entity.posY + entity.getEyeHeight() - y;
				double deltaZ = entity.posZ - z;
				double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);

				if(distance != 0.0D) {
					
					deltaX /= distance;
					deltaY /= distance;
					deltaZ /= distance;
					
					double density = 0;
					
					for(Vec3 vec : nodes) {
						double d = world.getBlockDensity(vec, entity.boundingBox);
						if(d > density) {
							density = d;
						}
					}
					
					double knockback = (1.0D - distanceScaled) * density;

					float dmg = calculateDamage(distanceScaled, density, knockback, size);
					if(!damageMap.containsKey(entity) || damageMap.get(entity) < dmg) damageMap.put(entity, dmg);
					double enchKnockback = EnchantmentProtection.func_92092_a(entity, knockback);
					
					if(!(entity instanceof EntityBulletBaseMK4)) {
						entity.motionX += deltaX * enchKnockback;
						entity.motionY += deltaY * enchKnockback;
						entity.motionZ += deltaZ * enchKnockback;
					}

					if(entity instanceof EntityPlayer) {
						affectedPlayers.put((EntityPlayer) entity, Vec3.createVectorHelper(deltaX * knockback, deltaY * knockback, deltaZ * knockback));
					}
				}
			}
		}
		
		for(Entry<Entity, Float> entry : damageMap.entrySet()) {
			
			Entity entity = entry.getKey();
			attackEntity(entity, explosion, entry.getValue());
			
			if(damage != null) {
				double distanceScaled = entity.getDistance(x, y, z) / size;
				damage.handleAttack(explosion, entity, distanceScaled);
			}
		}
		
		return affectedPlayers;
	}
	
	public void attackEntity(Entity entity, ExplosionVNT source, float amount) {
		entity.attackEntityFrom(setExplosionSource(source.compat), amount);
	}
	
	public float calculateDamage(double distanceScaled, double density, double knockback, float size) {
		return (float) ((int) ((knockback * knockback + knockback) / 2.0D * 8.0D * size + 1.0D));
	}

	public static DamageSource setExplosionSource(Explosion explosion) {
		return explosion != null && explosion.getExplosivePlacedBy() != null ?
				(new EntityDamageSource("explosion.player", explosion.getExplosivePlacedBy())).setExplosion() :
					(new DamageSource("explosion")).setExplosion();
	}
	
	public EntityProcessorCross withRangeMod(float mod) {
		range = new IEntityRangeMutator() {
			@Override
			public float mutateRange(ExplosionVNT explosion, float range) {
				return range * mod;
			}
		};
		return this;
	}
	
	public EntityProcessorCross withDamageMod(ICustomDamageHandler damage) {
		this.damage = damage;
		return this;
	}
}
