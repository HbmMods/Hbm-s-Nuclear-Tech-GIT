package com.hbm.util;

import java.lang.reflect.Method;
import java.util.List;

import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class EntityDamageUtil {
	
	public static boolean attackEntityFromIgnoreIFrame(Entity victim, DamageSource src, float damage) {

		if(!victim.attackEntityFrom(src, damage)) {
			
			if(victim instanceof EntityLivingBase) {
				EntityLivingBase living = (EntityLivingBase) victim;
				
				if(living.hurtResistantTime > living.maxHurtResistantTime / 2.0F) {
					damage += living.lastDamage;
				}
			}
			return victim.attackEntityFrom(src, damage);
		} else {
			return true;
		}
	}
	
	public static boolean attackEntityFromNT(EntityLivingBase living, DamageSource source, float amount, boolean ignoreIFrame, boolean allowSpecialCancel, double knockbackMultiplier, float pierceDT, float pierce) {
		if(living instanceof EntityPlayerMP && source.getEntity() instanceof EntityPlayer) {
			EntityPlayerMP playerMP = (EntityPlayerMP) living;
			EntityPlayer attacker = (EntityPlayer) source.getEntity();
			if(!playerMP.canAttackPlayer(attacker)) return false; //handles wack-ass no PVP rule as well as scoreboard friendly fire
		}
		DamageResistanceHandler.setup(pierceDT, pierce);
		boolean ret = attackEntityFromNTInternal(living, source, amount, ignoreIFrame, allowSpecialCancel, knockbackMultiplier);
		//boolean ret = living.attackEntityFrom(source, amount);
		DamageResistanceHandler.reset();
		return ret;
	}
	
	private static boolean attackEntityFromNTInternal(EntityLivingBase living, DamageSource source, float amount, boolean ignoreIFrame, boolean allowSpecialCancel, double knockbackMultiplier) {
		if(ForgeHooks.onLivingAttack(living, source, amount) && allowSpecialCancel) return false;
		if(living.isEntityInvulnerable()) return false;
		if(living.worldObj.isRemote) return false;
		if(living instanceof EntityPlayer && ((EntityPlayer) living).capabilities.disableDamage && !source.canHarmInCreative()) return false;
		
		living.entityAge = 0;
		if(living.getHealth() <= 0.0F) return false;
		if(source.isFireDamage() && living.isPotionActive(Potion.fireResistance)) return false;

		living.limbSwingAmount = 1.5F;
		boolean didAttackRegister = true;

		if(living.hurtResistantTime > living.maxHurtResistantTime / 2.0F && !ignoreIFrame) {
			if(amount <= living.lastDamage) { return false; }
			damageEntityNT(living, source, amount - living.lastDamage);
			living.lastDamage = amount;
			didAttackRegister = false;
		} else {
			living.lastDamage = amount;
			living.prevHealth = living.getHealth();
			living.hurtResistantTime = living.maxHurtResistantTime;
			damageEntityNT(living, source, amount);
			living.hurtTime = living.maxHurtTime = 10;
		}

		living.attackedAtYaw = 0.0F;
		Entity entity = source.getEntity();

		if(entity != null) {
			if(entity instanceof EntityLivingBase) {
				living.setRevengeTarget((EntityLivingBase) entity);
			}

			if(entity instanceof EntityPlayer) {
				living.recentlyHit = 100;
				living.attackingPlayer = (EntityPlayer) entity;
			} else if(entity instanceof EntityTameable) {
				EntityTameable entitywolf = (EntityTameable) entity;

				if(entitywolf.isTamed()) {
					living.recentlyHit = 100;
					living.attackingPlayer = null;
				}
			}
		}

		if(didAttackRegister) {
			living.worldObj.setEntityState(living, (byte) 2);

			if(source != DamageSource.drown) setBeenAttacked(living); //#

			if(entity != null) {
				double deltaX = entity.posX - living.posX;
				double deltaZ;

				for(deltaZ = entity.posZ - living.posZ; deltaX * deltaX + deltaZ * deltaZ < 1.0E-4D; deltaZ = (Math.random() - Math.random()) * 0.01D) {
					deltaX = (Math.random() - Math.random()) * 0.01D;
				}

				living.attackedAtYaw = (float) (Math.atan2(deltaZ, deltaX) * 180.0D / Math.PI) - living.rotationYaw;
				if(knockbackMultiplier > 0) knockBack(living, entity, amount, deltaX, deltaZ, knockbackMultiplier);
			} else {
				living.attackedAtYaw = (float) ((int) (Math.random() * 2.0D) * 180);
			}
		}

		String sound;

		if(living.getHealth() <= 0.0F) {
			sound = getDeathSound(living);
			if(didAttackRegister && sound != null) living.playSound(sound, getSoundVolume(living), getSoundPitch(living)); //#
			living.onDeath(source);
		} else {
			sound = getHurtSound(living);
			if(didAttackRegister && sound != null) living.playSound(sound, getSoundVolume(living), getSoundPitch(living)); //#
		}

		return true;
	}

	public static void knockBack(EntityLivingBase living, Entity attacker, float damage, double motionX, double motionZ, double multiplier) {
		if(living.getRNG().nextDouble() >= living.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).getAttributeValue()) {
			living.isAirBorne = true;
			double horizontal = Math.sqrt(motionX * motionX + motionZ * motionZ);
			double magnitude = 0.4D * multiplier;
			living.motionX /= 2.0D;
			living.motionY /= 2.0D;
			living.motionZ /= 2.0D;
			living.motionX -= motionX / horizontal * magnitude;
			living.motionY += (double) magnitude;
			living.motionZ -= motionZ / horizontal * magnitude;

			if(living.motionY > 0.2D) {
				living.motionY = 0.2D * multiplier;
			}
		}
	}

	public static void damageEntityNT(EntityLivingBase living, DamageSource source, float amount) {
		if(!living.isEntityInvulnerable()) {
			amount = ForgeHooks.onLivingHurt(living, source, amount);
			if(amount <= 0) return;
			
			amount = applyArmorCalculationsNT(living, source, amount);
			amount = applyPotionDamageCalculations(living, source, amount);
			
			float originalAmount = amount;
			amount = Math.max(amount - living.getAbsorptionAmount(), 0.0F);
			living.setAbsorptionAmount(living.getAbsorptionAmount() - (originalAmount - amount));

			if(amount != 0.0F) {
				float health = living.getHealth();
				living.setHealth(health - amount);
				living.func_110142_aN().func_94547_a(source, health, amount);
				living.setAbsorptionAmount(living.getAbsorptionAmount() - amount);
			}
		}
	}

	public static float applyArmorCalculationsNT(EntityLivingBase living, DamageSource source, float amount) {
		if(!source.isUnblockable()) {
			float i = 25F - (living.getTotalArmorValue() * (1 - DamageResistanceHandler.currentPDR));
			float armor = amount * (float) i;
			damageArmorNT(living, amount);
			amount = armor / 25.0F;
		}

		return amount;
	}
	
	public static void damageArmorNT(EntityLivingBase living, float amount) {
		
	}
	
	/** Currently just a copy of the vanilla damage code */
	@Deprecated public static boolean attackEntityFromNT(EntityLivingBase living, DamageSource source, float amount) {
		
		if(ForgeHooks.onLivingAttack(living, source, amount))
			return false;
		if(living.isEntityInvulnerable()) {
			return false;
		} else if(living.worldObj.isRemote) {
			return false;
		} else {
			living.entityAge = 0;

			if(living.getHealth() <= 0.0F) {
				return false;
			} else if(source.isFireDamage() && living.isPotionActive(Potion.fireResistance)) {
				return false;
			} else {
				if((source == DamageSource.anvil || source == DamageSource.fallingBlock) && living.getEquipmentInSlot(4) != null) {
					living.getEquipmentInSlot(4).damageItem((int) (amount * 4.0F + living.getRNG().nextFloat() * amount * 2.0F), living);
					amount *= 0.75F;
				}

				living.limbSwingAmount = 1.5F;
				boolean flag = true;

				if((float) living.hurtResistantTime > (float) living.maxHurtResistantTime / 2.0F) {
					if(amount <= living.lastDamage) {
						return false;
					}

					damageEntity(living, source, amount - living.lastDamage); //#
					living.lastDamage = amount;
					flag = false;
				} else {
					living.lastDamage = amount;
					living.prevHealth = living.getHealth();
					living.hurtResistantTime = living.maxHurtResistantTime;
					damageEntity(living, source, amount); //#
					living.hurtTime = living.maxHurtTime = 10;
				}

				living.attackedAtYaw = 0.0F;
				Entity entity = source.getEntity();

				if(entity != null) {
					if(entity instanceof EntityLivingBase) {
						living.setRevengeTarget((EntityLivingBase) entity);
					}

					if(entity instanceof EntityPlayer) {
						living.recentlyHit = 100;
						living.attackingPlayer = (EntityPlayer) entity;
					} else if(entity instanceof EntityTameable) {
						EntityTameable entitywolf = (EntityTameable) entity;

						if(entitywolf.isTamed()) {
							living.recentlyHit = 100;
							living.attackingPlayer = null;
						}
					}
				}

				if(flag) {
					living.worldObj.setEntityState(living, (byte) 2);

					if(source != DamageSource.drown) {
						setBeenAttacked(living); //#
					}

					if(entity != null) {
						double d1 = entity.posX - living.posX;
						double d0;

						for(d0 = entity.posZ - living.posZ; d1 * d1 + d0 * d0 < 1.0E-4D; d0 = (Math.random() - Math.random()) * 0.01D) {
							d1 = (Math.random() - Math.random()) * 0.01D;
						}

						living.attackedAtYaw = (float) (Math.atan2(d0, d1) * 180.0D / Math.PI) - living.rotationYaw;
						living.knockBack(entity, amount, d1, d0);
					} else {
						living.attackedAtYaw = (float) ((int) (Math.random() * 2.0D) * 180);
					}
				}

				String s;

				if(living.getHealth() <= 0.0F) {
					s = getDeathSound(living); //#

					if(flag && s != null) {
						living.playSound(s, getSoundVolume(living), getSoundPitch(living)); //#
					}

					living.onDeath(source);
				} else {
					s = getHurtSound(living); //#

					if(flag && s != null) {
						living.playSound(s, getSoundVolume(living), getSoundPitch(living)); //#
					}
				}

				return true;
			}
		}
	}
	
	// in this household we drink gasoline and sniff glue
	public static String getDeathSound(EntityLivingBase living) {
		Method m = ReflectionHelper.findMethod(EntityLivingBase.class, living, new String[] {"func_70673_aS", "getDeathSound"});
		try { return (String) m.invoke(living); } catch(Exception e) { } return "game.neutral.die";
	}
	
	public static String getHurtSound(EntityLivingBase living) {
		Method m = ReflectionHelper.findMethod(EntityLivingBase.class, living, new String[] {"func_70621_aR", "getHurtSound"});
		try { return (String) m.invoke(living); } catch(Exception e) { } return "game.neutral.hurt";
	}
	
	public static float getSoundVolume(EntityLivingBase living) {
		Method m = ReflectionHelper.findMethod(EntityLivingBase.class, living, new String[] {"func_70599_aP", "getSoundVolume"});
		try { return (float) m.invoke(living); } catch(Exception e) { } return 1F;
	}
	
	public static float getSoundPitch(EntityLivingBase living) {
		Method m = ReflectionHelper.findMethod(EntityLivingBase.class, living, new String[] {"func_70647_i", "getSoundPitch"});
		try { return (float) m.invoke(living); } catch(Exception e) { } return 1F;
	}

	@Deprecated public static void damageEntity(EntityLivingBase living, DamageSource source, float amount) {
		if(!living.isEntityInvulnerable()) {
			amount = ForgeHooks.onLivingHurt(living, source, amount);
			if(amount <= 0)
				return;
			amount = applyArmorCalculations(living, source, amount); //#
			amount = applyPotionDamageCalculations(living, source, amount); //#
			float f1 = amount;
			amount = Math.max(amount - living.getAbsorptionAmount(), 0.0F);
			living.setAbsorptionAmount(living.getAbsorptionAmount() - (f1 - amount));

			if(amount != 0.0F) {
				float f2 = living.getHealth();
				living.setHealth(f2 - amount);
				living.func_110142_aN().func_94547_a(source, f2, amount);
				living.setAbsorptionAmount(living.getAbsorptionAmount() - amount);
			}
		}
	}

	@Deprecated public static float applyArmorCalculations(EntityLivingBase living, DamageSource source, float amount) {
		if(!source.isUnblockable()) {
			int i = 25 - living.getTotalArmorValue();
			float armor = amount * (float) i;
			//living.damageArmor(amount); //unused
			amount = armor / 25.0F;
		}

		return amount;
	}
	
	public static float applyPotionDamageCalculations(EntityLivingBase living, DamageSource source, float amount) {
		if(source.isDamageAbsolute()) {
			return amount;
		} else {

			int resistance;
			int j;
			float f1;

			if(living.isPotionActive(Potion.resistance) && source != DamageSource.outOfWorld) {
				resistance = (living.getActivePotionEffect(Potion.resistance).getAmplifier() + 1) * 5;
				j = 25 - resistance;
				f1 = amount * (float) j;
				amount = f1 / 25.0F;
			}

			if(amount <= 0.0F) {
				return 0.0F;
			} else {
				
				resistance = EnchantmentHelper.getEnchantmentModifierDamage(living.getLastActiveItems(), source);

				if(resistance > 20) {
					resistance = 20;
				}

				if(resistance > 0 && resistance <= 20) {
					j = 25 - resistance;
					f1 = amount * (float) j;
					amount = f1 / 25.0F;
				}

				return amount;
			}
		}
	}

	public static void setBeenAttacked(EntityLivingBase living) {
		living.velocityChanged = living.getRNG().nextDouble() >= living.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).getAttributeValue();
	}

	public static MovingObjectPosition getMouseOver(EntityPlayer attacker, double reach) {

		World world = attacker.worldObj;
		MovingObjectPosition objectMouseOver = null;
		Entity pointedEntity = null;

		objectMouseOver = rayTrace(attacker, reach, 1F);

		Vec3 pos = getPosition(attacker);
		Vec3 look = attacker.getLook(1F);
		Vec3 end = pos.addVector(look.xCoord * reach, look.yCoord * reach, look.zCoord * reach);
		Vec3 hitvec = null;
		float grace = 1.0F;
		List list = world.getEntitiesWithinAABBExcludingEntity(attacker, attacker.boundingBox.addCoord(look.xCoord * reach, look.yCoord * reach, look.zCoord * reach).expand(grace, grace, grace));

		double closest = reach;

		for(int i = 0; i < list.size(); ++i) {
			Entity entity = (Entity) list.get(i);

			if(entity.canBeCollidedWith()) {
				
				float borderSize = entity.getCollisionBorderSize();
				AxisAlignedBB axisalignedbb = entity.boundingBox.expand(borderSize, borderSize, borderSize);
				MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(pos, end);

				if(axisalignedbb.isVecInside(pos)) {
					if(0.0D <= closest) {
						pointedEntity = entity;
						hitvec = movingobjectposition == null ? pos : movingobjectposition.hitVec;
						closest = 0.0D;
					}

				} else if(movingobjectposition != null) {
					double dist = pos.distanceTo(movingobjectposition.hitVec);

					if(dist < closest || closest == 0.0D) {
						if(entity == attacker.ridingEntity && !entity.canRiderInteract()) {
							if(closest == 0.0D) {
								pointedEntity = entity;
								hitvec = movingobjectposition.hitVec;
							}
						} else {
							pointedEntity = entity;
							hitvec = movingobjectposition.hitVec;
							closest = dist;
						}
					}
				}
			}
		}

		if(pointedEntity != null && (closest < reach || objectMouseOver == null)) {
			objectMouseOver = new MovingObjectPosition(pointedEntity, hitvec);
		}
		
		return objectMouseOver;
	}
	
	public static MovingObjectPosition rayTrace(EntityPlayer player, double dist, float interp) {
		Vec3 pos = getPosition(player);
		Vec3 look = player.getLook(interp);
		Vec3 end = pos.addVector(look.xCoord * dist, look.yCoord * dist, look.zCoord * dist);
		return player.worldObj.func_147447_a(pos, end, false, false, true);
	}
	
	public static Vec3 getPosition(EntityPlayer player) {
		return Vec3.createVectorHelper(player.posX, player.posY + player.getEyeHeight(), player.posZ);
	}
}
