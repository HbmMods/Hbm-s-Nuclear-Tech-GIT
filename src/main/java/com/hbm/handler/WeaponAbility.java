package com.hbm.handler;

import com.hbm.items.ModItems;
import com.hbm.items.tool.IItemAbility;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.potion.HbmPotion;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.WeightedRandomObject;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;

public abstract class WeaponAbility {
	
	public abstract void onHit(World world, EntityPlayer player, Entity victim, IItemAbility tool);
	public abstract String getName();
	public abstract String getFullName();
	
	public static class RadiationAbility extends WeaponAbility {
		
		float rad;
		
		public RadiationAbility(float rad) {
			this.rad = rad;
		}

		@Override
		public void onHit(World world, EntityPlayer player, Entity victim, IItemAbility tool) {
			
			ContaminationUtil.applyRadData(victim, rad);
		}

		@Override
		public String getName() {
			return "weapon.ability.radiation";
		}

		@Override
		public String getFullName() {
			return I18n.format(getName()) + " (" + rad + ")";
		}
	}
	
	public static class VampireAbility extends WeaponAbility {
		
		float amount;
		
		public VampireAbility(float amount) {
			this.amount = amount;
		}

		@Override
		public void onHit(World world, EntityPlayer player, Entity victim, IItemAbility tool) {
			
			if(victim instanceof EntityLivingBase) {
				
				EntityLivingBase living = (EntityLivingBase) victim;
				
				living.setHealth(living.getHealth() - amount);
				player.heal(amount);
			}
		}

		@Override
		public String getName() {
			return "weapon.ability.vampire";
		}

		@Override
		public String getFullName() {
			return I18n.format(getName()) + " (" + amount + ")";
		}
	}
	
	public static class StunAbility extends WeaponAbility {
		
		int duration;
		
		public StunAbility(int duration) {
			this.duration = duration;
		}

		@Override
		public void onHit(World world, EntityPlayer player, Entity victim, IItemAbility tool) {
			
			if(victim instanceof EntityLivingBase) {
				
				EntityLivingBase living = (EntityLivingBase) victim;

				living.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, duration * 20, 4));
				living.addPotionEffect(new PotionEffect(Potion.weakness.id, duration * 20, 4));
			}
		}

		@Override
		public String getName() {
			return "weapon.ability.stun";
		}

		@Override
		public String getFullName() {
			return I18n.format(getName()) + " (" + duration + ")";
		}
	}
	
	public static class PhosphorusAbility extends WeaponAbility {
		
		int duration;
		
		public PhosphorusAbility(int duration) {
			this.duration = duration;
		}

		@Override
		public void onHit(World world, EntityPlayer player, Entity victim, IItemAbility tool) {
			
			if(victim instanceof EntityLivingBase) {
				
				EntityLivingBase living = (EntityLivingBase) victim;

				living.addPotionEffect(new PotionEffect(HbmPotion.phosphorus.id, duration * 20, 4));
			}
		}

		@Override
		public String getName() {
			return "weapon.ability.phosphorus";
		}

		@Override
		public String getFullName() {
			return I18n.format(getName()) + " (" + duration + ")";
		}
	}
	
	public static class ChainsawAbility extends WeaponAbility {
		
		int divider;
		
		public ChainsawAbility(int divider) {
			this.divider = divider;
		}

		@Override
		public void onHit(World world, EntityPlayer player, Entity victim, IItemAbility tool) {
			
			if(victim instanceof EntityLivingBase) {
				
				EntityLivingBase living = (EntityLivingBase) victim;
				
				if(living.getHealth() <= 0.0F) {
					
					WeightedRandomObject[] ammo = new WeightedRandomObject[] {
							new WeightedRandomObject(ModItems.ammo_12gauge, 10),
							new WeightedRandomObject(ModItems.ammo_12gauge_shrapnel, 5),
							new WeightedRandomObject(ModItems.ammo_12gauge_du, 3),
							new WeightedRandomObject(ModItems.ammo_20gauge, 10),
							new WeightedRandomObject(ModItems.ammo_20gauge_flechette, 5),
							new WeightedRandomObject(ModItems.ammo_20gauge_slug, 5),
							new WeightedRandomObject(ModItems.ammo_9mm, 10),
							new WeightedRandomObject(ModItems.ammo_9mm_ap, 5),
							new WeightedRandomObject(ModItems.ammo_5mm, 10),
							new WeightedRandomObject(ModItems.ammo_5mm_du, 3),
							new WeightedRandomObject(ModItems.ammo_556, 10),
							new WeightedRandomObject(ModItems.ammo_556_phosphorus, 5),
							new WeightedRandomObject(ModItems.ammo_556_flechette, 10),
							new WeightedRandomObject(ModItems.ammo_556_flechette_phosphorus, 5),
							new WeightedRandomObject(ModItems.ammo_50bmg, 10),
							new WeightedRandomObject(ModItems.ammo_50bmg_incendiary, 5),
							new WeightedRandomObject(ModItems.ammo_50bmg_ap, 5),
							new WeightedRandomObject(ModItems.ammo_grenade, 5),
							new WeightedRandomObject(ModItems.ammo_grenade_concussion, 3),
							new WeightedRandomObject(ModItems.ammo_grenade_phosphorus, 3),
							new WeightedRandomObject(ModItems.ammo_rocket, 5),
							new WeightedRandomObject(ModItems.ammo_rocket_glare, 5),
							new WeightedRandomObject(ModItems.ammo_rocket_phosphorus, 5),
							new WeightedRandomObject(ModItems.ammo_rocket_rpc, 1),
							new WeightedRandomObject(ModItems.syringe_metal_stimpak, 25),
					};
					
					int count = Math.min((int)Math.ceil(living.getMaxHealth() / divider), 250); //safeguard to prevent funnies from bosses with obscene health
					
					for(int i = 0; i < count; i++) {
						
						living.dropItem(((WeightedRandomObject)WeightedRandom.getRandomItem(living.getRNG(), ammo)).asItem(), 1);
						world.spawnEntityInWorld(new EntityXPOrb(world, living.posX, living.posY, living.posZ, 1));
					}
					
					if(player instanceof EntityPlayerMP) {
						NBTTagCompound data = new NBTTagCompound();
						data.setString("type", "vanillaburst");
						data.setInteger("count", count * 4);
						data.setDouble("motion", 0.1D);
						data.setString("mode", "blockdust");
						data.setInteger("block", Block.getIdFromBlock(Blocks.redstone_block));
						PacketDispatcher.wrapper.sendTo(new AuxParticlePacketNT(data, living.posX, living.posY + living.height * 0.5, living.posZ), (EntityPlayerMP)player);
					}
					
					world.playSoundEffect(living.posX, living.posY + living.height * 0.5, living.posZ, "hbm:weapon.chainsaw", 0.5F, 1.0F);
				}
			}
		}

		@Override
		public String getName() {
			return "weapon.ability.chainsaw";
		}

		@Override
		public String getFullName() {
			return I18n.format(getName()) + " (1:" + divider + ")";
		}
	}
	
	public static class BeheaderAbility extends WeaponAbility {

		@Override
		public void onHit(World world, EntityPlayer player, Entity victim, IItemAbility tool) {
			
			if(victim instanceof EntityLivingBase && ((EntityLivingBase) victim).getHealth() <= 0.0F) {
				
				EntityLivingBase living = (EntityLivingBase) victim;
				
				if(living instanceof EntitySkeleton) {
					
					if(((EntitySkeleton)living).getSkeletonType() == 0) {
						living.entityDropItem(new ItemStack(Items.skull, 1, 0), 0.0F);
					} else {
						
						if(world.rand.nextInt(20) == 0)
							living.entityDropItem(new ItemStack(Items.skull, 1, 1), 0.0F);
						else
							living.entityDropItem(new ItemStack(Items.coal, 3), 0.0F);
					}
					
				} else if(living instanceof EntityZombie) {
					living.entityDropItem(new ItemStack(Items.skull, 1, 2), 0.0F);
				} else if(living instanceof EntityCreeper) {
					living.entityDropItem(new ItemStack(Items.skull, 1, 4), 0.0F);
				} else if(living instanceof EntityPlayer) {
					
					ItemStack head = new ItemStack(Items.skull, 1, 3);
					head.stackTagCompound = new NBTTagCompound();
					head.stackTagCompound.setString("SkullOwner", ((EntityPlayer) living).getDisplayName());
					living.entityDropItem(head, 0.0F);
				} else {
					living.entityDropItem(new ItemStack(Items.rotten_flesh, 3, 0), 0.0F);
					living.entityDropItem(new ItemStack(Items.bone, 2, 0), 0.0F);
				}
			}
		}

		@Override
		public String getName() {
			return "weapon.ability.beheader";
		}

		@Override
		public String getFullName() {
			return I18n.format(getName());
		}
	}
}
