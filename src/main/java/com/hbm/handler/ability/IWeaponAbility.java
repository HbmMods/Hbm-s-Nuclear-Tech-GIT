package com.hbm.handler.ability;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockBobble.BobbleType;
import com.hbm.handler.threading.PacketThreading;
import com.hbm.items.ModItems;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.potion.HbmPotion;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public interface IWeaponAbility extends IBaseAbility {
	// Note: tool is currently unused in weapon abilities
	public void onHit(int level, World world, EntityPlayer player, Entity victim, Item tool);

	public final static int SORT_ORDER_BASE = 200;

	// region handlers
	public static final IWeaponAbility NONE = new IWeaponAbility() {
		@Override
		public String getName() {
			return "";
		}

		@Override
		public int sortOrder() {
			return SORT_ORDER_BASE + 0;
		}

		@Override
		public void onHit(int level, World world, EntityPlayer player, Entity victim, Item tool) {
		}
	};

	public static final IWeaponAbility RADIATION = new IWeaponAbility() {
		@Override
		public String getName() {
			return "weapon.ability.radiation";
		}

		public final float[] radAtLevel = { 15F, 50F, 500F };

		@Override
		public int levels() {
			return radAtLevel.length;
		}

		@Override
		public String getExtension(int level) {
			return " (" + radAtLevel[level] + ")";
		}

		@Override
		public int sortOrder() {
			return SORT_ORDER_BASE + 1;
		}

		@Override
		public void onHit(int level, World world, EntityPlayer player, Entity victim, Item tool) {
			if(victim instanceof EntityLivingBase)
				ContaminationUtil.contaminate((EntityLivingBase) victim, HazardType.RADIATION, ContaminationType.CREATIVE, radAtLevel[level]);
		}
	};

	public static final IWeaponAbility VAMPIRE = new IWeaponAbility() {
		@Override
		public String getName() {
			return "weapon.ability.vampire";
		}

		public final float[] amountAtLevel = { 2F, 3F, 5F, 10F, 50F };

		@Override
		public int levels() {
			return amountAtLevel.length;
		}

		@Override
		public String getExtension(int level) {
			return " (" + amountAtLevel[level] + ")";
		}

		@Override
		public int sortOrder() {
			return SORT_ORDER_BASE + 2;
		}

		@Override
		public void onHit(int level, World world, EntityPlayer player, Entity victim, Item tool) {
			float amount = amountAtLevel[level];

			if(victim instanceof EntityLivingBase) {
				EntityLivingBase living = (EntityLivingBase) victim;
				if(living.getHealth() <= 0)
					return;
				living.setHealth(living.getHealth() - amount);
				if(living.getHealth() <= 0)
					living.onDeath(DamageSource.magic);
				player.heal(amount);
			}
		}
	};

	public static final IWeaponAbility STUN = new IWeaponAbility() {
		@Override
		public String getName() {
			return "weapon.ability.stun";
		}

		public final int[] durationAtLevel = { 2, 3, 5, 10, 15 };

		@Override
		public int levels() {
			return durationAtLevel.length;
		}

		@Override
		public String getExtension(int level) {
			return " (" + durationAtLevel[level] + ")";
		}

		@Override
		public int sortOrder() {
			return SORT_ORDER_BASE + 3;
		}

		@Override
		public void onHit(int level, World world, EntityPlayer player, Entity victim, Item tool) {
			int duration = durationAtLevel[level];

			if(victim instanceof EntityLivingBase) {
				EntityLivingBase living = (EntityLivingBase) victim;

				living.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, duration * 20, 4));
				living.addPotionEffect(new PotionEffect(Potion.weakness.id, duration * 20, 4));
			}
		}
	};

	public static final IWeaponAbility PHOSPHORUS = new IWeaponAbility() {
		@Override
		public String getName() {
			return "weapon.ability.phosphorus";
		}

		public final int[] durationAtLevel = { 60, 90 };

		@Override
		public int levels() {
			return durationAtLevel.length;
		}

		@Override
		public String getExtension(int level) {
			return " (" + durationAtLevel[level] + ")";
		}

		@Override
		public int sortOrder() {
			return SORT_ORDER_BASE + 4;
		}

		@Override
		public void onHit(int level, World world, EntityPlayer player, Entity victim, Item tool) {
			int duration = durationAtLevel[level];

			if(victim instanceof EntityLivingBase) {
				EntityLivingBase living = (EntityLivingBase) victim;

				living.addPotionEffect(new PotionEffect(HbmPotion.phosphorus.id, duration * 20, 4));
			}
		}
	};

	public static final IWeaponAbility FIRE = new IWeaponAbility() {
		@Override
		public String getName() {
			return "weapon.ability.fire";
		}

		public final int[] durationAtLevel = { 5, 10 };

		@Override
		public int levels() {
			return durationAtLevel.length;
		}

		@Override
		public String getExtension(int level) {
			return " (" + durationAtLevel[level] + ")";
		}

		@Override
		public int sortOrder() {
			return SORT_ORDER_BASE + 6;
		}

		@Override
		public void onHit(int level, World world, EntityPlayer player, Entity victim, Item tool) {
			if(victim instanceof EntityLivingBase) {
				victim.setFire(durationAtLevel[level]);
			}
		}
	};

	public static final IWeaponAbility CHAINSAW = new IWeaponAbility() {
		@Override
		public String getName() {
			return "weapon.ability.chainsaw";
		}

		public final int[] dividerAtLevel = { 15, 10 };

		@Override
		public int levels() {
			return dividerAtLevel.length;
		}

		@Override
		public String getExtension(int level) {
			return " (1:" + dividerAtLevel[level] + ")";
		}

		@Override
		public int sortOrder() {
			return SORT_ORDER_BASE + 7;
		}

		@Override
		public void onHit(int level, World world, EntityPlayer player, Entity victim, Item tool) {
			int divider = dividerAtLevel[level];

			if(victim instanceof EntityLivingBase) {
				EntityLivingBase living = (EntityLivingBase) victim;

				if(living.getHealth() <= 0.0F) {
					int count = Math.min((int) Math.ceil(living.getMaxHealth() / divider), 250); // safeguard to prevent funnies from bosses with obscene  health

					for(int i = 0; i < count; i++) {
						living.entityDropItem(new ItemStack(ModItems.nitra_small), 1);
						world.spawnEntityInWorld(new EntityXPOrb(world, living.posX, living.posY, living.posZ, 1));
					}

					if(player instanceof EntityPlayerMP) {
						NBTTagCompound data = new NBTTagCompound();
						data.setString("type", "vanillaburst");
						data.setInteger("count", count * 4);
						data.setDouble("motion", 0.1D);
						data.setString("mode", "blockdust");
						data.setInteger("block", Block.getIdFromBlock(Blocks.redstone_block));
						PacketThreading.createAllAroundThreadedPacket(new AuxParticlePacketNT(data, living.posX, living.posY + living.height * 0.5, living.posZ),
								new TargetPoint(living.dimension, living.posX, living.posY, living.posZ, 50));
					}

					world.playSoundEffect(living.posX, living.posY + living.height * 0.5, living.posZ, "hbm:weapon.chainsaw", 0.5F, 1.0F);
				}
			}
		}
	};

	public static final IWeaponAbility BEHEADER = new IWeaponAbility() {
		@Override
		public String getName() {
			return "weapon.ability.beheader";
		}

		@Override
		public int sortOrder() {
			return SORT_ORDER_BASE + 8;
		}

		@Override
		public void onHit(int level, World world, EntityPlayer player, Entity victim, Item tool) {
			if(victim instanceof EntityLivingBase && ((EntityLivingBase) victim).getHealth() <= 0.0F) {
				EntityLivingBase living = (EntityLivingBase) victim;

				if(living instanceof EntitySkeleton) {
					if(((EntitySkeleton) living).getSkeletonType() == 0) {
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
				} else if(living instanceof EntityMagmaCube) {
					living.entityDropItem(new ItemStack(Items.magma_cream, 3), 0.0F);
				} else if(living instanceof EntitySlime) {
					living.entityDropItem(new ItemStack(Items.slime_ball, 3), 0.0F);
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
	};

	public static final IWeaponAbility BOBBLE = new IWeaponAbility() {
		@Override
		public String getName() {
			return "weapon.ability.bobble";
		}

		@Override
		public int sortOrder() {
			return SORT_ORDER_BASE + 9;
		}

		@Override
		public void onHit(int level, World world, EntityPlayer player, Entity victim, Item tool) {
			if(victim instanceof EntityMob && ((EntityMob) victim).getHealth() <= 0.0F) {
				EntityMob mob = (EntityMob) victim;

				int chance = 1000;

				if(mob.getMaxHealth() > 20) {
					chance = 750;
				}

				if(world.rand.nextInt(chance) == 0)
					mob.entityDropItem(new ItemStack(ModBlocks.bobblehead, 1, world.rand.nextInt(BobbleType.values().length - 1) + 1), 0.0F);
			}
		}
	};
	// endregion handlers

	static final IWeaponAbility[] abilities = { NONE, RADIATION, VAMPIRE, STUN, PHOSPHORUS, FIRE, CHAINSAW, BEHEADER, BOBBLE };

	static IWeaponAbility getByName(String name) {
		for(IWeaponAbility ability : abilities) {
			if(ability.getName().equals(name))
				return ability;
		}

		return NONE;
	}
}
