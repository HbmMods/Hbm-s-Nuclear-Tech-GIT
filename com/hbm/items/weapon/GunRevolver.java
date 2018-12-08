package com.hbm.items.weapon;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Multimap;
import com.hbm.entity.projectile.EntityBullet;
import com.hbm.items.ModItems;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GunRevolver extends Item {

	private Item ammo;
	private int dmgMin;
	private int dmgMax;
	private boolean instakill = false;
	private boolean rad = false;
	Random rand = new Random();

	public GunRevolver(Item ammo, int dmgMin, int dmgMax, boolean instakill, boolean rad) {
		this.maxStackSize = 1;
		if (this == ModItems.gun_revolver_iron) {
			this.setMaxDamage(100);
		}
		if (this == ModItems.gun_revolver) {
			this.setMaxDamage(500);
		}
		if (this == ModItems.gun_revolver_saturnite) {
			this.setMaxDamage(2500);
		}
		if (this == ModItems.gun_revolver_gold) {
			this.setMaxDamage(1000);
		}
		if (this == ModItems.gun_revolver_lead) {
			this.setMaxDamage(250);
		}
		if (this == ModItems.gun_revolver_schrabidium) {
			this.setMaxDamage(100000);
		}
		if (this == ModItems.gun_revolver_cursed) {
			this.setMaxDamage(5000);
		}
		if (this == ModItems.gun_revolver_pip) {
			this.setMaxDamage(1000);
		}

		this.ammo = ammo;
		this.dmgMin = dmgMin;
		this.dmgMax = dmgMax;
		this.instakill = instakill;
		this.rad = rad;
	}

	@Override
	public EnumRarity getRarity(ItemStack p_77613_1_) {

		if (this == ModItems.gun_revolver_schrabidium || this == ModItems.gun_revolver_saturnite) {
			return EnumRarity.rare;
		}

		if (this == ModItems.gun_revolver_cursed) {
			return EnumRarity.uncommon;
		}

		if (this == ModItems.gun_revolver_pip) {
			return EnumRarity.uncommon;
		}

		return EnumRarity.common;
	}

	/**
	 * called when the player releases the use item button. Args: itemstack,
	 * world, entityplayer, itemInUseCount
	 */
	@Override
	public void onPlayerStoppedUsing(ItemStack p_77615_1_, World p_77615_2_, EntityPlayer p_77615_3_, int p_77615_4_) {
		int j = this.getMaxItemUseDuration(p_77615_1_) - p_77615_4_;

		ArrowLooseEvent event = new ArrowLooseEvent(p_77615_3_, p_77615_1_, j);
		MinecraftForge.EVENT_BUS.post(event);
		j = event.charge;

		boolean flag = p_77615_3_.capabilities.isCreativeMode
				|| EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, p_77615_1_) > 0;

		if (flag || p_77615_3_.inventory.hasItem(ammo)) {
			float f = j / 20.0F;
			f = (f * f + f * 2.0F) / 3.0F;

			if (j < 10.0D) {
				return;
			}

			if (j > 10.0F) {
				f = 10.0F;
			}

			EntityBullet entityarrow = new EntityBullet(p_77615_2_, p_77615_3_, 3.0F, dmgMin, dmgMax, instakill, rad);
			entityarrow.setDamage(dmgMin + rand.nextInt(dmgMax - dmgMin));
			if(this == ModItems.gun_revolver_pip && p_77615_3_.isSneaking()) {
				entityarrow.pip = true;
				entityarrow.setDamage(1);
			}
			if(this == ModItems.gun_revolver_saturnite || EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, p_77615_1_) > 0) {
				entityarrow.fire = true;
			}

			p_77615_1_.damageItem(1, p_77615_3_);
			if (this == ModItems.gun_revolver || this == ModItems.gun_revolver_iron
					|| this == ModItems.gun_revolver_gold || this == ModItems.gun_revolver_lead
					|| this == ModItems.gun_revolver_saturnite) {
				p_77615_2_.playSoundAtEntity(p_77615_3_, "hbm:weapon.revolverShoot", 1.0F, 1.0F);
			}
			if (this == ModItems.gun_revolver_cursed) {
				p_77615_2_.playSoundAtEntity(p_77615_3_, "hbm:weapon.heavyShoot", 3.0F, 1.0F);
			}
			if (this == ModItems.gun_revolver_schrabidium) {
				p_77615_2_.playSoundAtEntity(p_77615_3_, "hbm:weapon.schrabidiumShoot", 1.0F, 1.0F);
			}
			if(this == ModItems.gun_revolver_pip ||
					this == ModItems.gun_revolver_nopip ||
					this == ModItems.gun_revolver_red) {
				p_77615_2_.playSoundAtEntity(p_77615_3_, "hbm:weapon.revolverShootAlt", 1.0F, 1.0F);
			}
			if (this == ModItems.gun_revolver_blackjack) {
				p_77615_2_.playSoundAtEntity(p_77615_3_, "hbm:weapon.revolverShootAlt", 1.0F, 0.75F);
			}

			if (flag) {
				entityarrow.canBePickedUp = 2;
			} else {
				p_77615_3_.inventory.consumeInventoryItem(ammo);
			}

			if (!p_77615_2_.isRemote) {
				p_77615_2_.spawnEntityInWorld(entityarrow);

				if (this == ModItems.gun_revolver_cursed && rand.nextInt(3) == 0) {
					p_77615_3_.addPotionEffect(new PotionEffect(Potion.wither.id, 5 * 20, 0));
				}
			}
		}
	}

	@Override
	public ItemStack onEaten(ItemStack p_77654_1_, World p_77654_2_, EntityPlayer p_77654_3_) {
		return p_77654_1_;
	}

	/**
	 * How long it takes to use or consume an item
	 */
	@Override
	public int getMaxItemUseDuration(ItemStack p_77626_1_) {
		return 72000;
	}

	/**
	 * returns the action that specifies what animation to play when the items
	 * is being used
	 */
	@Override
	public EnumAction getItemUseAction(ItemStack p_77661_1_) {
		return EnumAction.bow;
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_) {
		ArrowNockEvent event = new ArrowNockEvent(p_77659_3_, p_77659_1_);
		MinecraftForge.EVENT_BUS.post(event);

		p_77659_3_.setItemInUse(p_77659_1_, this.getMaxItemUseDuration(p_77659_1_));

		return p_77659_1_;
	}

	/**
	 * Return the enchantability factor of the item, most of the time is based
	 * on material.
	 */
	@Override
	public int getItemEnchantability() {
		return 1;
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {

		if (this == ModItems.gun_revolver_iron) {
			list.add("Cheap.");
			list.add("");
			list.add("Ammo: Iron Bullets");
			list.add("Damage: 5 - 15");
		}
		if (this == ModItems.gun_revolver) {
			list.add("I feel like a cowboy!");
			list.add("");
			list.add("Ammo: Lead Bullets");
			list.add("Damage: 10 - 25");
		}
		if (this == ModItems.gun_revolver_saturnite) {
			list.add("Woooo - shiny!");
			list.add("");
			list.add("Ammo: Lead Bullets");
			list.add("Damage: 20 - 35");
			list.add("Sets enemy on fire.");
		}
		if (this == ModItems.gun_revolver_gold) {
			list.add("GoldenEye would be proud!");
			list.add("(GoldenEye isn't a person but");
			list.add("rather a satellite, taht's teh joek.");
			list.add("");
			list.add("Ammo: Golden Bullets");
			list.add("Damage: 20 - 30");
		}
		if (this == ModItems.gun_revolver_lead) {
			list.add("Made from lead for your safety!");
			list.add("");
			list.add("Ammo: Atomic Bullets");
			list.add("Damage: 5 - 15");
			list.add("Bullets are radioactive.");
		}
		if (this == ModItems.gun_revolver_schrabidium) {
			list.add("Kills everyone and everything.");
			list.add("");
			list.add("Ammo: Schrabidium Bullets");
			list.add("Damage: 10000 - 100000");
			list.add("Sets enemy's health to zero.");
			list.add("");
			list.add("[LEGENDARY WEAPON]");
		}
		if (this == ModItems.gun_revolver_cursed) {
			list.add("You're dead.");
			list.add("");
			list.add("Ammo: Steel Bullets");
			list.add("Damage: 25 - 40");
			list.add("33% chance of user being withered.");
		}
		if (this == ModItems.gun_revolver_pip) {
			list.add("In loving memory of the eldritch");
			list.add("creature which got smushed by a");
			list.add("falling freight wagon.");
			list.add("");
			list.add("Ammo: Tainted Bullets");
			list.add("Damage: 25 - 35");
			list.add("Secondary Damage: 1");
			list.add("Enemy is hit by boxcar.");
			list.add("");
			list.add("[LEGENDARY WEAPON]");
		}
		if (this == ModItems.gun_revolver_nopip) {
			list.add("The 'No' stands for 'NoSQL'");
			list.add("");
			list.add("Ammo: .44 Magnum");
			list.add("Damage: 25 - 35");
		}
		if (this == ModItems.gun_revolver_blackjack) {
			list.add("Hol on-");
			list.add("");
			list.add("Ammo: .44 Magnum");
			list.add("Damage: 35 - 45");
		}
		if (this == ModItems.gun_revolver_red) {
			list.add("Explore the other-what?");
			list.add("");
			list.add("Ammo: .44 Magnum");
			list.add("Damage: 100 - 105");
		}
	}

	@Override
	public Multimap getItemAttributeModifiers() {
		Multimap multimap = super.getItemAttributeModifiers();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
				new AttributeModifier(field_111210_e, "Weapon modifier", 2.5, 0));
		return multimap;
	}
}
