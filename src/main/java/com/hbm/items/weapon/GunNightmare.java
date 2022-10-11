package com.hbm.items.weapon;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Multimap;
import com.hbm.entity.projectile.EntityBullet;
import com.hbm.entity.projectile.EntityNightmareBlast;
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
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

public class GunNightmare extends Item {
	private int dmgMin;
	private int dmgMax;
	public Item ammo;
	Random rand = new Random();

	public GunNightmare() {
		
        this.maxStackSize = 1;

		if (this == ModItems.gun_revolver_nightmare) {
			this.dmgMin = 1;
			this.dmgMax = 100;
//			this.ammo = ModItems.gun_revolver_nightmare_ammo;
		}
		if (this == ModItems.gun_revolver_nightmare2) {
			this.dmgMin = 25;
			this.dmgMax = 150;
//			this.ammo = ModItems.gun_revolver_nightmare2_ammo;
		}
	}

	@Override
	public EnumRarity getRarity(ItemStack p_77613_1_) {

		return EnumRarity.uncommon;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack p_77615_1_, World p_77615_2_, EntityPlayer p_77615_3_, int p_77615_4_) {
		int j = this.getMaxItemUseDuration(p_77615_1_) - p_77615_4_;

		ArrowLooseEvent event = new ArrowLooseEvent(p_77615_3_, p_77615_1_, j);
		MinecraftForge.EVENT_BUS.post(event);
		j = event.charge;
		float f = j / 20.0F;
		f = (f * f + f * 2.0F) / 3.0F;

		if (j < 10.0D) {
			return;
		}

		if (j > 10.0F) {
			f = 10.0F;
		}

		if (this == ModItems.gun_revolver_nightmare) {
			EntityBullet entityarrow;
			entityarrow = new EntityBullet(p_77615_2_, p_77615_3_, 3.0F, dmgMin, dmgMax, false, false);
			entityarrow.setDamage(1 + rand.nextInt(99));

			if (!p_77615_2_.isRemote) {
				p_77615_2_.spawnEntityInWorld(entityarrow);
			}
		}

		if (this == ModItems.gun_revolver_nightmare2) {
			EntityNightmareBlast entityarrow0;
			EntityNightmareBlast entityarrow1;
			EntityNightmareBlast entityarrow2;
			EntityNightmareBlast entityarrow3;
			EntityNightmareBlast entityarrow4;
			EntityNightmareBlast entityarrow5;
			EntityNightmareBlast entityarrow6;
			EntityNightmareBlast entityarrow7;
			EntityNightmareBlast entityarrow8;
			EntityNightmareBlast entityarrow9;
			entityarrow0 = new EntityNightmareBlast(p_77615_2_, p_77615_3_, 3.0F);
			entityarrow0.setDamage(25 + rand.nextInt(150 - 25));
			entityarrow1 = new EntityNightmareBlast(p_77615_2_, p_77615_3_, 3.0F);
			entityarrow1.setDamage(25 + rand.nextInt(150 - 25));
			entityarrow2 = new EntityNightmareBlast(p_77615_2_, p_77615_3_, 3.0F);
			entityarrow2.setDamage(25 + rand.nextInt(150 - 25));
			entityarrow3 = new EntityNightmareBlast(p_77615_2_, p_77615_3_, 3.0F);
			entityarrow3.setDamage(25 + rand.nextInt(150 - 25));
			entityarrow4 = new EntityNightmareBlast(p_77615_2_, p_77615_3_, 3.0F);
			entityarrow4.setDamage(25 + rand.nextInt(150 - 25));
			entityarrow5 = new EntityNightmareBlast(p_77615_2_, p_77615_3_, 3.0F);
			entityarrow5.setDamage(25 + rand.nextInt(150 - 25));
			entityarrow6 = new EntityNightmareBlast(p_77615_2_, p_77615_3_, 3.0F);
			entityarrow6.setDamage(25 + rand.nextInt(150 - 25));
			entityarrow7 = new EntityNightmareBlast(p_77615_2_, p_77615_3_, 3.0F);
			entityarrow7.setDamage(25 + rand.nextInt(150 - 25));
			entityarrow8 = new EntityNightmareBlast(p_77615_2_, p_77615_3_, 3.0F);
			entityarrow8.setDamage(25 + rand.nextInt(150 - 25));
			entityarrow9 = new EntityNightmareBlast(p_77615_2_, p_77615_3_, 3.0F);
			entityarrow9.setDamage(25 + rand.nextInt(150 - 25));

			if (!p_77615_2_.isRemote) {
				p_77615_2_.spawnEntityInWorld(entityarrow0);
				p_77615_2_.spawnEntityInWorld(entityarrow1);
				p_77615_2_.spawnEntityInWorld(entityarrow2);
				p_77615_2_.spawnEntityInWorld(entityarrow3);
				p_77615_2_.spawnEntityInWorld(entityarrow4);
				p_77615_2_.spawnEntityInWorld(entityarrow5);
				p_77615_2_.spawnEntityInWorld(entityarrow6);
				p_77615_2_.spawnEntityInWorld(entityarrow7);
				p_77615_2_.spawnEntityInWorld(entityarrow8);
				p_77615_2_.spawnEntityInWorld(entityarrow9);
			}
		}

		p_77615_2_.playSoundAtEntity(p_77615_3_, "hbm:weapon.schrabidiumShoot", 1.0F, 1.0F);

		boolean flag = p_77615_3_.capabilities.isCreativeMode
				|| EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, p_77615_1_) > 0;

		if (!flag)
			p_77615_1_.setItemDamage(p_77615_1_.getItemDamage() + 1);
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

		if (!p_77659_3_.isSneaking()) {

			if (p_77659_1_.getItemDamage() < 6) {
				p_77659_3_.setItemInUse(p_77659_1_, this.getMaxItemUseDuration(p_77659_1_));

			} else {
				if(p_77659_2_.isRemote)
					p_77659_3_.addChatMessage(new ChatComponentText("[Nightmare] Out of ammo! Shift right-click to reload!"));
			}
		} else if(p_77659_1_.getItemDamage() > 0) {
			
			int j = 0;
			
//			for(int i = 0; i < 6; i++) {
//				if(p_77659_1_.getItem() == ModItems.gun_revolver_nightmare && p_77659_3_.inventory.consumeInventoryItem(ModItems.gun_revolver_nightmare_ammo)) {
//					p_77659_1_.setItemDamage(p_77659_1_.getItemDamage() - 1);
//					j++;
//				}
//				if(p_77659_1_.getItem() == ModItems.gun_revolver_nightmare2 && p_77659_3_.inventory.consumeInventoryItem(ModItems.gun_revolver_nightmare2_ammo)) {
//					p_77659_1_.setItemDamage(p_77659_1_.getItemDamage() - 1);
//					j++;
//				}
//				if(p_77659_1_.getItemDamage() == 0)
//					break;
//			}
			
			if(j > 0) {
				if(p_77659_2_.isRemote)
					p_77659_3_.addChatMessage(new ChatComponentText("[Nightmare] Reloaded!"));
				p_77659_3_.swingItem();
			}
		}

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

		if (this == ModItems.gun_revolver_nightmare) {
			list.add("Never let a cat doze on your belly when you sleep.");
			list.add("");
			list.add("Ammo: Nightmare Bullets");
			list.add("Damage: 1 - 100");
		}
		if (this == ModItems.gun_revolver_nightmare2) {
			list.add("Ominous references. *shivers*");
			list.add("");
			list.add("Ammo: Laser Buckshot");
			list.add("Damage: 25 - 150");
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