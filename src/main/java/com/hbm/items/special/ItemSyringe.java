package com.hbm.items.special;

import java.util.List;
import java.util.Random;

import com.hbm.config.VersatileConfig;
import com.hbm.extprop.HbmLivingProps;
import com.hbm.handler.ArmorModHandler;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemGunBase;
import com.hbm.lib.ModDamageSource;
import com.hbm.potion.HbmPotion;

import api.hbm.fluid.IFillableItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemSyringe extends Item {

	Random rand = new Random();

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {

		if(this == ModItems.syringe_antidote && !VersatileConfig.hasPotionSickness(player)) {
			if(!world.isRemote) {
				player.clearActivePotions();

				stack.stackSize--;
				world.playSoundAtEntity(player, "hbm:item.syringe", 1.0F, 1.0F);

				if(stack.stackSize <= 0) {
					return new ItemStack(ModItems.syringe_empty);
				}

				if(!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_empty))) {
					player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.syringe_empty, 1, 0), false);
				}

				VersatileConfig.applyPotionSickness(player, 5);
			}
		}

		if(this == ModItems.syringe_awesome && !VersatileConfig.hasPotionSickness(player)) {
			if(!world.isRemote) {
				player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 50 * 20, 9));
				player.addPotionEffect(new PotionEffect(Potion.resistance.id, 50 * 20, 9));
				player.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 50 * 20, 0));
				player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 50 * 20, 24));
				player.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 50 * 20, 9));
				player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 50 * 20, 6));
				player.addPotionEffect(new PotionEffect(Potion.jump.id, 50 * 20, 9));
				player.addPotionEffect(new PotionEffect(Potion.field_76434_w.id, 50 * 20, 9));
				player.addPotionEffect(new PotionEffect(Potion.field_76444_x.id, 50 * 20, 4));
				player.addPotionEffect(new PotionEffect(Potion.confusion.id, 5 * 20, 4));
				player.addPotionEffect(new PotionEffect(HbmPotion.radx.id, 50 * 20, 9));

				stack.stackSize--;
				world.playSoundAtEntity(player, "hbm:item.syringe", 1.0F, 1.0F);

				if(stack.stackSize <= 0) {
					return new ItemStack(ModItems.syringe_empty);
				}

				if(!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_empty))) {
					player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.syringe_empty, 1, 0), false);
				}

				VersatileConfig.applyPotionSickness(player, 50);
			}
		}

		if(this == ModItems.syringe_poison) {
			if(!world.isRemote) {
				if(rand.nextInt(2) == 0)
					player.attackEntityFrom(ModDamageSource.euthanizedSelf, 30);
				else
					player.attackEntityFrom(ModDamageSource.euthanizedSelf2, 30);

				stack.stackSize--;
				world.playSoundAtEntity(player, "hbm:item.syringe", 1.0F, 1.0F);

				if(stack.stackSize <= 0) {
					return new ItemStack(ModItems.syringe_empty);
				}

				if(!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_empty))) {
					player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.syringe_empty, 1, 0), false);
				}
			}
		}

		if(this == ModItems.syringe_metal_stimpak && !VersatileConfig.hasPotionSickness(player)) {
			if(!world.isRemote) {
				player.heal(5);

				stack.stackSize--;
				world.playSoundAtEntity(player, "hbm:item.syringe", 1.0F, 1.0F);

				if(stack.stackSize <= 0) {
					return new ItemStack(ModItems.syringe_metal_empty);
				}

				if(!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_metal_empty))) {
					player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.syringe_metal_empty, 1, 0), false);
				}

				VersatileConfig.applyPotionSickness(player, 5);
			}
		}

		if(this == ModItems.syringe_metal_medx && !VersatileConfig.hasPotionSickness(player)) {
			if(!world.isRemote) {
				player.addPotionEffect(new PotionEffect(Potion.resistance.id, 4 * 60 * 20, 2));

				stack.stackSize--;
				world.playSoundAtEntity(player, "hbm:item.syringe", 1.0F, 1.0F);

				if(stack.stackSize <= 0) {
					return new ItemStack(ModItems.syringe_metal_empty);
				}

				if(!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_metal_empty))) {
					player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.syringe_metal_empty, 1, 0), false);
				}

				VersatileConfig.applyPotionSickness(player, 5);
			}
		}

		if(this == ModItems.syringe_metal_psycho && !VersatileConfig.hasPotionSickness(player)) {
			if(!world.isRemote) {
				player.addPotionEffect(new PotionEffect(Potion.resistance.id, 2 * 60 * 20, 0));
				player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 2 * 60 * 20, 0));

				stack.stackSize--;
				world.playSoundAtEntity(player, "hbm:item.syringe", 1.0F, 1.0F);

				if(stack.stackSize <= 0) {
					return new ItemStack(ModItems.syringe_metal_empty);
				}

				if(!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_metal_empty))) {
					player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.syringe_metal_empty, 1, 0), false);
				}

				VersatileConfig.applyPotionSickness(player, 5);
			}
		}

		if(this == ModItems.syringe_metal_super && !VersatileConfig.hasPotionSickness(player)) {
			if(!world.isRemote) {
				player.heal(25);
				player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 10 * 20, 0));

				stack.stackSize--;
				world.playSoundAtEntity(player, "hbm:item.syringe", 1.0F, 1.0F);

				if(stack.stackSize <= 0) {
					return new ItemStack(ModItems.syringe_metal_empty);
				}

				if(!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_metal_empty))) {
					player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.syringe_metal_empty, 1, 0), false);
				}

				VersatileConfig.applyPotionSickness(player, 15);
			}
		}

		if(this == ModItems.med_bag && !VersatileConfig.hasPotionSickness(player)) {
			if(!world.isRemote) {
				player.setHealth(player.getMaxHealth());

				player.removePotionEffect(Potion.blindness.id);
				player.removePotionEffect(Potion.confusion.id);
				player.removePotionEffect(Potion.digSlowdown.id);
				player.removePotionEffect(Potion.hunger.id);
				player.removePotionEffect(Potion.moveSlowdown.id);
				player.removePotionEffect(Potion.poison.id);
				player.removePotionEffect(Potion.weakness.id);
				player.removePotionEffect(Potion.wither.id);
				player.removePotionEffect(HbmPotion.radiation.id);

				VersatileConfig.applyPotionSickness(player, 15);

				stack.stackSize--;
			}
		}

		if(this == ModItems.radaway) {
			if(!world.isRemote) {
				player.addPotionEffect(new PotionEffect(HbmPotion.radaway.id, 14, 9));

				stack.stackSize--;
				world.playSoundAtEntity(player, "hbm:item.radaway", 1.0F, 1.0F);
			}
		}

		if(this == ModItems.radaway_strong) {
			if(!world.isRemote) {
				int duration = 35;
				int level = 9;

				if(!player.isPotionActive(HbmPotion.radaway)) {
					player.addPotionEffect(new PotionEffect(HbmPotion.radaway.id, duration, level));
				} else {

					int d = player.getActivePotionEffect(HbmPotion.radaway).getDuration() + duration;
					player.addPotionEffect(new PotionEffect(HbmPotion.radaway.id, d, level));
				}

				stack.stackSize--;
				world.playSoundAtEntity(player, "hbm:item.radaway", 1.0F, 1.0F);
			}
		}

		if(this == ModItems.radaway_flush) {
			if(!world.isRemote) {
				player.addPotionEffect(new PotionEffect(HbmPotion.radaway.id, 50, 19));

				stack.stackSize--;
				world.playSoundAtEntity(player, "hbm:item.radaway", 1.0F, 1.0F);
			}
		}

		if(this == ModItems.syringe_taint) {
			if(!world.isRemote) {
				player.addPotionEffect(new PotionEffect(HbmPotion.taint.id, 60 * 20, 0));
				player.addPotionEffect(new PotionEffect(Potion.confusion.id, 5 * 20, 0));

				stack.stackSize--;
				world.playSoundAtEntity(player, "hbm:item.syringe", 1.0F, 1.0F);
			}

			if(!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_metal_empty))) {
				player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.syringe_metal_empty, 1, 0), false);
			}

			if(!player.inventory.addItemStackToInventory(new ItemStack(ModItems.bottle2_empty))) {
				player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.bottle2_empty, 1, 0), false);
			}
		}

		if(this == ModItems.gas_mask_filter_mono && player.inventory.armorInventory[3] != null && player.inventory.armorInventory[3].getItem() == ModItems.gas_mask_mono) {
			if(!world.isRemote) {
				if(player.inventory.armorInventory[3].getItemDamage() == 0)
					return stack;

				player.inventory.armorInventory[3].setItemDamage(0);

				world.playSoundAtEntity(player, "hbm:item.gasmaskScrew", 1.0F, 1.0F);
				stack.stackSize--;
			}
		}

		if(this == ModItems.jetpack_tank && player.inventory.armorInventory[2] != null) {

			if(!world.isRemote) {

				ItemStack jetpack = player.inventory.armorInventory[2];

				if(jetpack == null)
					return stack;

				if(jetpack.getItem() instanceof ItemArmor && ArmorModHandler.hasMods(jetpack)) {
					jetpack = ArmorModHandler.pryMods(jetpack)[ArmorModHandler.plate_only];
				}

				if(jetpack == null || !(jetpack.getItem() instanceof IFillableItem))
					return stack;

				IFillableItem fillable = (IFillableItem) jetpack.getItem();

				if(!fillable.acceptsFluid(Fluids.KEROSENE, jetpack))
					return stack;

				if(fillable.tryFill(Fluids.KEROSENE, 1000, jetpack) < 1000) {
					world.playSoundAtEntity(player, "hbm:item.jetpackTank", 1.0F, 1.0F);
					stack.stackSize--;
				}

				if(jetpack.getItem() != player.inventory.armorInventory[2].getItem())
					ArmorModHandler.applyMod(player.inventory.armorInventory[2], jetpack);
			}
		}

		if(this == ModItems.gun_kit_1 || this == ModItems.gun_kit_2) {
			if(!world.isRemote) {
				float repair = 0;

				if(this == ModItems.gun_kit_1) {
					repair = 0.1F;
					world.playSoundAtEntity(player, "hbm:item.spray", 1.0F, 1.0F);
				}
				if(this == ModItems.gun_kit_2) {
					repair = 0.5F;
					world.playSoundAtEntity(player, "hbm:item.repair", 1.0F, 1.0F);
				}

				for(int i = 0; i < 9; i++) {

					ItemStack gun = player.inventory.mainInventory[i];

					if(gun != null && gun.getItem() instanceof ItemGunBase) {

						int full = ((ItemGunBase) gun.getItem()).mainConfig.durability;
						int wear = ItemGunBase.getItemWear(gun);

						int nWear = (int) (wear - (full * repair));

						if(nWear < 0)
							nWear = 0;

						ItemGunBase.setItemWear(gun, nWear);
					}
				}

				stack.stackSize--;
			}
		}

		if(this == ModItems.cbt_device) {
			if(!world.isRemote) {
				player.addPotionEffect(new PotionEffect(HbmPotion.bang.id, 30, 0));

				stack.stackSize--;
				world.playSoundAtEntity(player, "hbm:item.vice", 1.0F, 1.0F);
			}
		}

		return stack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack p_77636_1_) {
		if(this == ModItems.syringe_awesome) {
			return true;
		}

		return false;
	}

	@Override
	public EnumRarity getRarity(ItemStack p_77613_1_) {
		if(this == ModItems.syringe_awesome) {
			return EnumRarity.uncommon;
		}
		return EnumRarity.common;
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase entity, EntityLivingBase entityPlayer) {
		World world = entity.worldObj;

		if(this == ModItems.syringe_antidote && !VersatileConfig.hasPotionSickness(entity)) {
			if(!world.isRemote) {
				entity.clearActivePotions();
				VersatileConfig.applyPotionSickness(entity, 5);

				stack.stackSize--;
				world.playSoundAtEntity(entity, "hbm:item.syringe", 1.0F, 1.0F);

				if(entityPlayer instanceof EntityPlayer) {
					EntityPlayer player = (EntityPlayer) entityPlayer;
					if(!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_empty))) {
						player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.syringe_empty, 1, 0), false);
					}
				}
			}
		}

		if(this == ModItems.syringe_awesome && !VersatileConfig.hasPotionSickness(entity)) {
			if(!world.isRemote) {

				if(entity instanceof EntityCow) {

					entity.addPotionEffect(new PotionEffect(HbmPotion.bang.id, 40, 0));

				} else  {
					entity.addPotionEffect(new PotionEffect(Potion.regeneration.id, 50 * 20, 9));
					entity.addPotionEffect(new PotionEffect(Potion.resistance.id, 50 * 20, 9));
					entity.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 50 * 20, 0));
					entity.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 50 * 20, 24));
					entity.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 50 * 20, 9));
					entity.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 50 * 20, 6));
					entity.addPotionEffect(new PotionEffect(Potion.jump.id, 50 * 20, 9));
					entity.addPotionEffect(new PotionEffect(Potion.field_76434_w.id, 50 * 20, 9));
					entity.addPotionEffect(new PotionEffect(Potion.field_76444_x.id, 50 * 20, 4));
					entity.addPotionEffect(new PotionEffect(Potion.confusion.id, 5 * 20, 4));
					VersatileConfig.applyPotionSickness(entity, 50);
				}

				stack.stackSize--;
				world.playSoundAtEntity(entity, "hbm:item.syringe", 1.0F, 1.0F);

				if(entityPlayer instanceof EntityPlayer) {
					EntityPlayer player = (EntityPlayer) entityPlayer;
					if(!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_empty))) {
						player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.syringe_empty, 1, 0), false);
					}
				}
			}
		}

		if(this == ModItems.syringe_poison) {
			if(!world.isRemote) {
				entity.attackEntityFrom(ModDamageSource.euthanized(entityPlayer, entityPlayer), 30);

				stack.stackSize--;
				world.playSoundAtEntity(entity, "hbm:item.syringe", 1.0F, 1.0F);

				if(entityPlayer instanceof EntityPlayer) {
					EntityPlayer player = (EntityPlayer) entityPlayer;
					if(!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_empty))) {
						player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.syringe_empty, 1, 0), false);
					}
				}
			}
		}

		if(this == ModItems.syringe_metal_stimpak && !VersatileConfig.hasPotionSickness(entity)) {
			if(!world.isRemote) {
				entity.heal(5);
				VersatileConfig.applyPotionSickness(entity, 5);

				stack.stackSize--;
				world.playSoundAtEntity(entity, "hbm:item.syringe", 1.0F, 1.0F);

				if(entityPlayer instanceof EntityPlayer) {
					EntityPlayer player = (EntityPlayer) entityPlayer;
					if(!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_metal_empty))) {
						player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.syringe_metal_empty, 1, 0), false);
					}
				}
			}
		}

		if(this == ModItems.syringe_metal_medx && !VersatileConfig.hasPotionSickness(entity)) {
			if(!world.isRemote) {
				entity.addPotionEffect(new PotionEffect(Potion.resistance.id, 4 * 60 * 20, 2));
				VersatileConfig.applyPotionSickness(entity, 5);

				stack.stackSize--;
				world.playSoundAtEntity(entity, "hbm:item.syringe", 1.0F, 1.0F);

				if(entityPlayer instanceof EntityPlayer) {
					EntityPlayer player = (EntityPlayer) entityPlayer;
					if(!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_metal_empty))) {
						player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.syringe_metal_empty, 1, 0), false);
					}
				}
			}
		}

		if(this == ModItems.syringe_metal_psycho && !VersatileConfig.hasPotionSickness(entity)) {
			if(!world.isRemote) {
				entity.addPotionEffect(new PotionEffect(Potion.resistance.id, 2 * 60 * 20, 0));
				entity.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 2 * 60 * 20, 0));
				VersatileConfig.applyPotionSickness(entity, 5);

				stack.stackSize--;
				world.playSoundAtEntity(entity, "hbm:item.syringe", 1.0F, 1.0F);

				if(entityPlayer instanceof EntityPlayer) {
					EntityPlayer player = (EntityPlayer) entityPlayer;
					if(!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_metal_empty))) {
						player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.syringe_metal_empty, 1, 0), false);
					}
				}
			}
		}

		if(this == ModItems.syringe_metal_super && !VersatileConfig.hasPotionSickness(entity)) {
			if(!world.isRemote) {
				entity.heal(25);
				entity.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 10 * 20, 0));
				VersatileConfig.applyPotionSickness(entity, 15);

				stack.stackSize--;
				world.playSoundAtEntity(entity, "hbm:item.syringe", 1.0F, 1.0F);

				if(entityPlayer instanceof EntityPlayer) {
					EntityPlayer player = (EntityPlayer) entityPlayer;
					if(!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_metal_empty))) {
						player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.syringe_metal_empty, 1, 0), false);
					}
				}
			}
		}

		if(this == ModItems.syringe_taint) {
			if(!world.isRemote) {
				entity.addPotionEffect(new PotionEffect(HbmPotion.taint.id, 60 * 20, 0));
				entity.addPotionEffect(new PotionEffect(Potion.confusion.id, 5 * 20, 0));

				stack.stackSize--;
				world.playSoundAtEntity(entity, "hbm:item.syringe", 1.0F, 1.0F);

				if(entityPlayer instanceof EntityPlayer) {
					EntityPlayer player = (EntityPlayer) entityPlayer;
					if(!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_metal_empty))) {
						player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.syringe_metal_empty, 1, 0), false);
					}
					if(!player.inventory.addItemStackToInventory(new ItemStack(ModItems.bottle2_empty))) {
						player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.bottle2_empty, 1, 0), false);
					}
				}
			}
		}

		if(this == ModItems.syringe_mkunicorn) {
			if(!world.isRemote) {
				HbmLivingProps.setContagion(entity, 3 * 60 * 60 * 20);
				world.playSoundAtEntity(entity, "hbm:item.syringe", 1.0F, 1.0F);
				stack.stackSize--;
			}
		}

		return false;
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		if(this == ModItems.syringe_antidote) {
			list.add("Removes all potion effects");
		}
		if(this == ModItems.syringe_awesome) {
			list.add("Every good effect for 50 seconds");
		}
		if(this == ModItems.syringe_metal_medx) {
			list.add("Resistance III for 4 minutes");
		}
		if(this == ModItems.syringe_metal_psycho) {
			list.add("Resistance I for 2 minutes");
			list.add("Strength I for 2 minutes");
		}
		if(this == ModItems.syringe_metal_stimpak) {
			list.add("Heals 2.5 hearts");
		}
		if(this == ModItems.syringe_metal_super) {
			list.add("Heals 25 hearts");
			list.add("Slowness I for 10 seconds");
		}
		if(this == ModItems.syringe_poison) {
			list.add("Deadly");
		}
		if(this == ModItems.med_bag) {
			list.add("Full heal, regardless of max health");
			list.add("Removes negative effects");
		}
		if(this == ModItems.radaway) {
			list.add("Removes 140 RAD");
		}
		if(this == ModItems.radaway_strong) {
			list.add("Removes 350 RAD");
		}
		if(this == ModItems.radaway_flush) {
			list.add("Removes 1000 RAD");
		}
		if(this == ModItems.syringe_taint) {
			list.add("Tainted I for 60 seconds");
			list.add("Nausea I for 5 seconds");
			list.add("Cloud damage + taint = tainted heart effect");
		}
		if(this == ModItems.gas_mask_filter) {
			list.add("Repairs worn gas mask");
		}
		if(this == ModItems.gas_mask_filter_mono) {
			list.add("Repairs worn monoxide mask");
		}
		if(this == ModItems.jetpack_tank) {
			list.add("Fills worn jetpack with up to 1000mB of kerosene");
		}
		if(this == ModItems.gun_kit_1) {
			list.add("Repairs all weapons in hotbar by 10%");
		}
		if(this == ModItems.gun_kit_2) {
			list.add("Repairs all weapons in hotbar by 50%");
		}

		if(this == ModItems.syringe_mkunicorn) {
			list.add(EnumChatFormatting.RED + "?");
		}
	}
}
