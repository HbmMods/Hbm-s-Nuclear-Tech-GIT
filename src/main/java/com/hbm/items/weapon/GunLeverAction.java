package com.hbm.items.weapon;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Multimap;
import com.hbm.entity.projectile.EntityBullet;
import com.hbm.items.ModItems;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

public class GunLeverAction extends Item {

	Random rand = new Random();
	
	public int dmgMin = 8;
	public int dmgMax = 16;

	public GunLeverAction() {
		
		this.maxStackSize = 1;

		if(this == ModItems.gun_lever_action)
			this.setMaxDamage(500);
		if(this == ModItems.gun_lever_action_dark)
			this.setMaxDamage(750);
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

		if (flag || p_77615_3_.inventory.hasItem(ModItems.gun_lever_action_ammo)) {
			float f = j / 20.0F;
			f = (f * f + f * 2.0F) / 3.0F;

			if (j < 10.0D) {
				return;
			}

			if (j > 10.0F) {
				f = 10.0F;
			}
			EntityBullet entityarrow1;
			EntityBullet entityarrow2;
			EntityBullet entityarrow3;
			EntityBullet entityarrow4;
			EntityBullet entityarrow5;
			EntityBullet entityarrow6;
			EntityBullet entityarrow7;
			EntityBullet entityarrow8;
			EntityBullet entityarrow9;
			EntityBullet entityarrow10;
			
			if (!p_77615_3_.isSneaking()) {
				entityarrow1 = new EntityBullet(p_77615_2_, p_77615_3_, 3.0F);
				entityarrow1.setDamage(dmgMin + rand.nextInt(dmgMax - dmgMin));
				entityarrow2 = new EntityBullet(p_77615_2_, p_77615_3_, 3.0F);
				entityarrow2.setDamage(dmgMin + rand.nextInt(dmgMax - dmgMin));
				entityarrow3 = new EntityBullet(p_77615_2_, p_77615_3_, 3.0F);
				entityarrow3.setDamage(dmgMin + rand.nextInt(dmgMax - dmgMin));
				entityarrow4 = new EntityBullet(p_77615_2_, p_77615_3_, 3.0F);
				entityarrow4.setDamage(dmgMin + rand.nextInt(dmgMax - dmgMin));
				entityarrow5 = new EntityBullet(p_77615_2_, p_77615_3_, 3.0F);
				entityarrow5.setDamage(dmgMin + rand.nextInt(dmgMax - dmgMin));
				entityarrow6 = new EntityBullet(p_77615_2_, p_77615_3_, 3.0F);
				entityarrow6.setDamage(dmgMin + rand.nextInt(dmgMax - dmgMin));
				entityarrow7 = new EntityBullet(p_77615_2_, p_77615_3_, 3.0F);
				entityarrow7.setDamage(dmgMin + rand.nextInt(dmgMax - dmgMin));
				entityarrow8 = new EntityBullet(p_77615_2_, p_77615_3_, 3.0F);
				entityarrow8.setDamage(dmgMin + rand.nextInt(dmgMax - dmgMin));
				entityarrow9 = new EntityBullet(p_77615_2_, p_77615_3_, 3.0F);
				entityarrow9.setDamage(dmgMin + rand.nextInt(dmgMax - dmgMin));
				entityarrow10 = new EntityBullet(p_77615_2_, p_77615_3_, 3.0F);
				entityarrow10.setDamage(dmgMin + rand.nextInt(dmgMax - dmgMin));
			} else {
				entityarrow1 = new EntityBullet(p_77615_2_, p_77615_3_, 3.0F, dmgMin, dmgMax, false, false);
				entityarrow1.setDamage(dmgMin + rand.nextInt(dmgMax - dmgMin));
				entityarrow2 = new EntityBullet(p_77615_2_, p_77615_3_, 3.0F, dmgMin, dmgMax, false, false);
				entityarrow2.setDamage(dmgMin + rand.nextInt(dmgMax - dmgMin));
				entityarrow3 = new EntityBullet(p_77615_2_, p_77615_3_, 3.0F, dmgMin, dmgMax, false, false);
				entityarrow3.setDamage(dmgMin + rand.nextInt(dmgMax - dmgMin));
				entityarrow4 = new EntityBullet(p_77615_2_, p_77615_3_, 3.0F, dmgMin, dmgMax, false, false);
				entityarrow4.setDamage(dmgMin + rand.nextInt(dmgMax - dmgMin));
				entityarrow5 = new EntityBullet(p_77615_2_, p_77615_3_, 3.0F, dmgMin, dmgMax, false, false);
				entityarrow5.setDamage(dmgMin + rand.nextInt(dmgMax - dmgMin));
				entityarrow6 = new EntityBullet(p_77615_2_, p_77615_3_, 3.0F, dmgMin, dmgMax, false, false);
				entityarrow6.setDamage(dmgMin + rand.nextInt(dmgMax - dmgMin));
				entityarrow7 = new EntityBullet(p_77615_2_, p_77615_3_, 3.0F, dmgMin, dmgMax, false, false);
				entityarrow7.setDamage(dmgMin + rand.nextInt(dmgMax - dmgMin));
				entityarrow8 = new EntityBullet(p_77615_2_, p_77615_3_, 3.0F, dmgMin, dmgMax, false, false);
				entityarrow8.setDamage(dmgMin + rand.nextInt(dmgMax - dmgMin));
				entityarrow9 = new EntityBullet(p_77615_2_, p_77615_3_, 3.0F, dmgMin, dmgMax, false, false);
				entityarrow9.setDamage(dmgMin + rand.nextInt(dmgMax - dmgMin));
				entityarrow10 = new EntityBullet(p_77615_2_, p_77615_3_, 3.0F, dmgMin, dmgMax, false, false);
				entityarrow10.setDamage(dmgMin + rand.nextInt(dmgMax - dmgMin));
			}

			p_77615_1_.damageItem(1, p_77615_3_);

			p_77615_2_.playSoundAtEntity(p_77615_3_, "hbm:weapon.revolverShootAlt", 5.0F, 0.75F);

			if (flag) { } else {
				p_77615_3_.inventory.consumeInventoryItem(ModItems.gun_lever_action_ammo);
			}

			if (!p_77615_2_.isRemote) {
				p_77615_2_.spawnEntityInWorld(entityarrow1);
				p_77615_2_.spawnEntityInWorld(entityarrow2);
				p_77615_2_.spawnEntityInWorld(entityarrow3);
				p_77615_2_.spawnEntityInWorld(entityarrow4);
				
				if (!p_77615_3_.isSneaking()) {
					
					p_77615_2_.spawnEntityInWorld(entityarrow5);
					p_77615_2_.spawnEntityInWorld(entityarrow6);
					
					int i = rand.nextInt(5);
	
					if(i >= 1)
						p_77615_2_.spawnEntityInWorld(entityarrow7);
					if(i >= 2)
						p_77615_2_.spawnEntityInWorld(entityarrow8);
					if(i >= 3)
						p_77615_2_.spawnEntityInWorld(entityarrow9);
					if(i >= 4)
						p_77615_2_.spawnEntityInWorld(entityarrow10);
				}
			}
			
			setAnim(p_77615_1_, 1);
		}
	}

	
    @Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int i, boolean b) {
    	int j = getAnim(stack);
    	
    	if(j > 0) {
    		if(j < 30)
    			setAnim(stack, j + 1);
    		else
    			setAnim(stack, 0);
    		
        	if(j == 15)
        		world.playSoundAtEntity(entity, "hbm:weapon.leverActionReload", 2F, 0.85F);
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

		if (p_77659_3_.capabilities.isCreativeMode || p_77659_3_.inventory.hasItem(ModItems.gun_lever_action_ammo)) {
			if(this.getAnim(p_77659_1_) == 0)
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

		if(this == ModItems.gun_lever_action)
			list.add("Universal head-to-spaghetti-sauce converter.");
		if(this == ModItems.gun_lever_action_dark)
			list.add("Blow your legs off!");
		list.add("");
		list.add("Ammo: 12x74 Buckshot");
		list.add("Damage: 8 - 16");
		list.add("Projectiles: 6 - 10");
	}

	@Override
	public Multimap getItemAttributeModifiers() {
		Multimap multimap = super.getItemAttributeModifiers();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
				new AttributeModifier(field_111210_e, "Weapon modifier", 3.5, 0));
		return multimap;
	}
	
	private static int getAnim(ItemStack stack) {
		if(stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
			return 0;
		}
		
		return stack.stackTagCompound.getInteger("animation");
		
	}
	
	private static void setAnim(ItemStack stack, int i) {
		if(stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
		}
		
		stack.stackTagCompound.setInteger("animation", i);
		
	}
	
	public static float getRotationFromAnim(ItemStack stack) {
		float rad = 0.0174533F;
		rad *= 7.5F;
		int i = getAnim(stack);
		
		if(i < 10)
			return 0;
		i -= 10;
		
		if(i < 10)
			return rad * i;
		else
			return (rad * 10) - (rad * (i - 10));
	}
	
	public static float getOffsetFromAnim(ItemStack stack) {
		float i = getAnim(stack);
		
		if(i < 10)
			return 0;
		i -= 10;
		
		if(i < 10)
			return i / 10;
		else
			return 2 - (i / 10);
	}

}
