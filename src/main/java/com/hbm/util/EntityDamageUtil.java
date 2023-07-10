package com.hbm.util;

import java.lang.reflect.Method;

import com.hbm.handler.ArmorModHandler;
import com.hbm.items.ModItems;

import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.common.ForgeHooks;

public class EntityDamageUtil {
	
	public static boolean wasAttackedByV1(DamageSource source) {

		if(source instanceof EntityDamageSource) {
			Entity attacker = ((EntityDamageSource) source).getEntity();
			
			if(attacker instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) attacker;
				ItemStack chestplate = player.inventory.armorInventory[2];
				
				if(chestplate != null && ArmorModHandler.hasMods(chestplate)) {
					ItemStack[] mods = ArmorModHandler.pryMods(chestplate);
					
					if(mods[ArmorModHandler.extra] != null && mods[ArmorModHandler.extra].getItem() == ModItems.v1) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Attacks the given entity twice, based on a piecring percentage. The second hit sets the damage source to bypass armor.
	 * The damage source is modified, so you can't reuse damage source instances.
	 */
	public static boolean attackEntityFromArmorPiercing(Entity victim, DamageSource src, float damage, float piercing) {
		
		if(src.isUnblockable() || piercing == 0) return victim.attackEntityFrom(src, damage);
		
		if(piercing == 1) {
			src.setDamageBypassesArmor();
			return victim.attackEntityFrom(src, damage);
		}
		
		boolean ret = false;
		
		ret |= victim.attackEntityFrom(src, damage * (1F - piercing));
		src.setDamageBypassesArmor();
		ret |= victim.attackEntityFrom(src, damage * piercing);
		return ret;
	}
	
	public static boolean attackEntityFromIgnoreIFrame(Entity victim, DamageSource src, float damage) {

		if(!victim.attackEntityFrom(src, damage)) {
			
			if(victim instanceof EntityLivingBase) {
				damage += ((EntityLivingBase) victim).lastDamage;
			}
			return victim.attackEntityFrom(src, damage);
		} else {
			return true;
		}
	}
	
	/** Currently just a copy of the vanilla damage code */
	public static boolean attackEntityFromNT(EntityLivingBase living, DamageSource source, float amount) {
		
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
					} else if(entity instanceof net.minecraft.entity.passive.EntityTameable) {
						net.minecraft.entity.passive.EntityTameable entitywolf = (net.minecraft.entity.passive.EntityTameable) entity;

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

	public static void damageEntity(EntityLivingBase living, DamageSource source, float amount) {
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

	public static float applyArmorCalculations(EntityLivingBase living, DamageSource source, float amount) {
		if(!source.isUnblockable()) {
			int i = 25 - living.getTotalArmorValue();
			float armor = amount * (float) i;
			//living.damageArmor(p_70655_2_); //unused
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
}
