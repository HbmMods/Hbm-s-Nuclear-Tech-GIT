package com.hbm.items.weapon;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Multimap;
import com.hbm.entity.projectile.EntityBullet;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ItemAmmoEnums.Ammo20Gauge;
import com.hbm.items.ModItems;
import com.hbm.util.InventoryUtil;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

public class GunBoltAction extends Item {

	Random rand = new Random();
	
	public int dmgMin = 16;
	public int dmgMax = 28;

	public GunBoltAction() {
		
		this.maxStackSize = 1;

		if(this == ModItems.gun_bolt_action)
			this.setMaxDamage(750);
		if(this == ModItems.gun_bolt_action_green)
			this.setMaxDamage(500);
		if(this == ModItems.gun_bolt_action_saturnite) {
			this.setMaxDamage(2500);
			dmgMin = 24;
			dmgMax = 36;
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

		if (flag || InventoryUtil.doesPlayerHaveAStack(p_77615_3_, new ComparableStack(ModItems.ammo_20gauge, 1, Ammo20Gauge.SLUG.ordinal()))) {
			float f = j / 20.0F;
			f = (f * f + f * 2.0F) / 3.0F;

			if (j < 10.0D) {
				return;
			}

			if (j > 10.0F) {
				f = 10.0F;
			}
			EntityBullet entityarrow1;
			entityarrow1 = new EntityBullet(p_77615_2_, p_77615_3_, 3.0F, dmgMin, dmgMax, false, false);
			entityarrow1.setDamage(dmgMin + rand.nextInt(dmgMax - dmgMin));
			
			if(this == ModItems.gun_bolt_action_saturnite)
				entityarrow1.fire = true;

			p_77615_1_.damageItem(1, p_77615_3_);

			p_77615_2_.playSoundAtEntity(p_77615_3_, "hbm:weapon.revolverShoot", 5.0F, 0.75F);

			if (flag) { } else {
				InventoryUtil.doesPlayerHaveAStack(p_77615_3_, new ComparableStack(ModItems.ammo_20gauge, 1, Ammo20Gauge.SLUG.ordinal()), true);
			}

			if (!p_77615_2_.isRemote) {
				p_77615_2_.spawnEntityInWorld(entityarrow1);
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

		if(this.getAnim(p_77659_1_) == 0)
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

		if(this == ModItems.gun_bolt_action) {
			list.add("-Star in a movie");
			list.add("-Have a laugh with a horse");
			list.add("-Ride a tipping train");
			list.add("-Lose friend to native americans");
		}
		if(this == ModItems.gun_bolt_action_green) {
			list.add("Floppy disks and pink, flashy orbs.");
		}
		if(this == ModItems.gun_bolt_action_saturnite) {
			list.add("Shiny shooter made from D-25A alloy.");
		}
		list.add("");
		list.add("Ammo: 12x74 Slug");
		
		if(this == ModItems.gun_bolt_action_saturnite) {
			list.add("Damage: 24 - 36");
			list.add("Sets enemy on fire.");
		} else {
			list.add("Damage: 16 - 28");
		}
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
	
	public static float getLevRotationFromAnim(ItemStack stack) {
		float rad = 0.0174533F;
		rad *= 10F;
		int i = getAnim(stack);
		
		if(i < 10)
			return 0;
		i -= 10;
		
		if(i < 6)
			return rad * i;
		if(i > 14)
			return rad * (5 - (i - 15));
		return rad * 5;
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
	
	public static float getTransFromAnim(ItemStack stack) {
		float i = getAnim(stack);
		
		if(i < 10)
			return 0;
		i -= 10;

		if(i > 4 && i < 10)
			return (i - 5) * 0.1F;
		
		if(i > 9 && i < 15)
			return (10 * 0.1F) - ((i - 5) * 0.1F);
					
		return 0;
	}

	@Override
	public EnumRarity getRarity(ItemStack p_77613_1_) {

		if(this == ModItems.gun_bolt_action_saturnite)
			return EnumRarity.rare;
		
		return EnumRarity.uncommon;
	}
}
