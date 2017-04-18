package com.hbm.items.weapon;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Multimap;
import com.hbm.entity.projectile.EntityBullet;
import com.hbm.items.ModItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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

public class GunShotgun extends Item {
	Random rand = new Random();
	
	public int dmgMin = 3;
	public int dmgMax = 7;

	public GunShotgun() {
		
		this.maxStackSize = 1;
		
		if (this == ModItems.gun_revolver) {
			this.setMaxDamage(500);
		}
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

		if (flag || p_77615_3_.inventory.hasItem(ModItems.gun_uboinik_ammo)) {
			float f = j / 20.0F;
			f = (f * f + f * 2.0F) / 3.0F;

			if (j < 10.0D) {
				return;
			}

			if (j > 10.0F) {
				f = 10.0F;
			}

			EntityBullet entityarrow1 = new EntityBullet(p_77615_2_, p_77615_3_, 3.0F);
			entityarrow1.setDamage(dmgMin + rand.nextInt(dmgMax - dmgMin));
			EntityBullet entityarrow2 = new EntityBullet(p_77615_2_, p_77615_3_, 3.0F);
			entityarrow2.setDamage(dmgMin + rand.nextInt(dmgMax - dmgMin));
			EntityBullet entityarrow3 = new EntityBullet(p_77615_2_, p_77615_3_, 3.0F);
			entityarrow3.setDamage(dmgMin + rand.nextInt(dmgMax - dmgMin));
			EntityBullet entityarrow4 = new EntityBullet(p_77615_2_, p_77615_3_, 3.0F);
			entityarrow4.setDamage(dmgMin + rand.nextInt(dmgMax - dmgMin));
			EntityBullet entityarrow5 = new EntityBullet(p_77615_2_, p_77615_3_, 3.0F);
			entityarrow5.setDamage(dmgMin + rand.nextInt(dmgMax - dmgMin));
			EntityBullet entityarrow6 = new EntityBullet(p_77615_2_, p_77615_3_, 3.0F);
			entityarrow6.setDamage(dmgMin + rand.nextInt(dmgMax - dmgMin));
			EntityBullet entityarrow7 = new EntityBullet(p_77615_2_, p_77615_3_, 3.0F);
			entityarrow7.setDamage(dmgMin + rand.nextInt(dmgMax - dmgMin));
			EntityBullet entityarrow8 = new EntityBullet(p_77615_2_, p_77615_3_, 3.0F);
			entityarrow8.setDamage(dmgMin + rand.nextInt(dmgMax - dmgMin));
			EntityBullet entityarrow9 = new EntityBullet(p_77615_2_, p_77615_3_, 3.0F);
			entityarrow9.setDamage(dmgMin + rand.nextInt(dmgMax - dmgMin));
			EntityBullet entityarrow10 = new EntityBullet(p_77615_2_, p_77615_3_, 3.0F);
			entityarrow10.setDamage(dmgMin + rand.nextInt(dmgMax - dmgMin));
			EntityBullet entityarrow11 = new EntityBullet(p_77615_2_, p_77615_3_, 3.0F);
			entityarrow11.setDamage(dmgMin + rand.nextInt(dmgMax - dmgMin));
			EntityBullet entityarrow12 = new EntityBullet(p_77615_2_, p_77615_3_, 3.0F);
			entityarrow12.setDamage(dmgMin + rand.nextInt(dmgMax - dmgMin));

			p_77615_1_.damageItem(1, p_77615_3_);

			p_77615_2_.playSoundAtEntity(p_77615_3_, "hbm:weapon.shotgunShoot", 1.0F, 1.0F);

			if (flag) { } else {
				p_77615_3_.inventory.consumeInventoryItem(ModItems.gun_uboinik_ammo);
			}

			if (!p_77615_2_.isRemote) {
				p_77615_2_.spawnEntityInWorld(entityarrow1);
				p_77615_2_.spawnEntityInWorld(entityarrow2);
				p_77615_2_.spawnEntityInWorld(entityarrow3);
				p_77615_2_.spawnEntityInWorld(entityarrow4);
				p_77615_2_.spawnEntityInWorld(entityarrow5);
				p_77615_2_.spawnEntityInWorld(entityarrow6);
				
				int i = rand.nextInt(7);

				if(i >= 1)
					p_77615_2_.spawnEntityInWorld(entityarrow7);
				if(i >= 2)
					p_77615_2_.spawnEntityInWorld(entityarrow8);
				if(i >= 3)
					p_77615_2_.spawnEntityInWorld(entityarrow9);
				if(i >= 4)
					p_77615_2_.spawnEntityInWorld(entityarrow10);
				if(i >= 5)
					p_77615_2_.spawnEntityInWorld(entityarrow11);
				if(i >= 6)
					p_77615_2_.spawnEntityInWorld(entityarrow12);
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

		if (p_77659_3_.capabilities.isCreativeMode || p_77659_3_.inventory.hasItem(ModItems.gun_uboinik_ammo)) {
			p_77659_3_.setItemInUse(p_77659_1_, this.getMaxItemUseDuration(p_77659_1_));
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

		//list.add("POW! Haha!");
		list.add("Abracadabra Tomanakara!");
		list.add("");
		list.add("Ammo: Shotgun Shells");
		list.add("Damage: 3 - 7");
		list.add("Projectiles: 6 - 12");
	}

	public Multimap getItemAttributeModifiers() {
		Multimap multimap = super.getItemAttributeModifiers();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
				new AttributeModifier(field_111210_e, "Weapon modifier", (double) 3.5, 0));
		return multimap;
	}
}
