package com.hbm.items.weapon;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Multimap;
import com.hbm.entity.effect.EntityEMPBlast;
import com.hbm.entity.projectile.EntityDischarge;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.items.ModItems;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

public class GunEMPRay extends Item {

	Random rand = new Random();

	public GunEMPRay() {
		this.maxStackSize = 1;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.bow;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack p_77626_1_) {
		return 72000;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_) {
		ArrowNockEvent event = new ArrowNockEvent(p_77659_3_, p_77659_1_);
		{
			p_77659_3_.setItemInUse(p_77659_1_, this.getMaxItemUseDuration(p_77659_1_));
		}

		return p_77659_1_;
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
		if (event.isCanceled()) {
			return;
		}
		j = event.charge;

		boolean flag = p_77615_3_.capabilities.isCreativeMode
				|| EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, p_77615_1_) > 0;

		if (!p_77615_3_.isSneaking()) {
			if (flag || p_77615_3_.inventory.hasItem(ModItems.gun_emp_ammo)) {
				float f = j / 20.0F;
				f = (f * f + f * 2.0F) / 3.0F;

				if (j < 25.0D) {
					return;
				}

				if (j > 25.0F) {
					f = 25.0F;
				}

				EntityDischarge entityarrow = new EntityDischarge(p_77615_2_, p_77615_3_, 1.0F);

				entityarrow.setIsCritical(true);

				p_77615_1_.damageItem(1, p_77615_3_);
				// p_77615_2_.playSoundAtEntity(p_77615_3_, "tile.piston.out",
				// 1.0F, 0.5F);
				p_77615_2_.playSoundAtEntity(p_77615_3_, "hbm:weapon.fatmanShoot", 1.0F, 1F);

				if (!flag) {
					p_77615_3_.inventory.consumeInventoryItem(ModItems.gun_emp_ammo);
				}

				if (!p_77615_2_.isRemote) {
					p_77615_2_.spawnEntityInWorld(entityarrow);
				}
			}
		} else {
			if (flag || p_77615_3_.inventory.hasItem(ModItems.gun_emp_ammo)) {

				if (j < 25.0D) {
					return;
				}
				
				if (!flag) {
					p_77615_3_.inventory.consumeInventoryItem(ModItems.gun_emp_ammo);
				}
	    		
	    		EntityEMPBlast cloud = new EntityEMPBlast(p_77615_3_.worldObj, 25);
	    		cloud.posX = p_77615_3_.posX;
	    		cloud.posY = p_77615_3_.posY + 1.0F;
	    		cloud.posZ = p_77615_3_.posZ;
				if (!p_77615_2_.isRemote) {
					p_77615_2_.spawnEntityInWorld(cloud);
				}
				
				ExplosionNukeGeneric.empBlast(p_77615_3_.worldObj, (int)p_77615_3_.posX, (int)p_77615_3_.posY, (int)p_77615_3_.posZ, 25);
			}
		}
	}

	@Override
	public ItemStack onEaten(ItemStack p_77654_1_, World p_77654_2_, EntityPlayer p_77654_3_) {
		return p_77654_1_;
	}

	@Override
	public int getItemEnchantability() {
		return 0;
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {

		list.add("Hold right mouse button");
		list.add("to shoot ball lightning,");
		list.add("sneak to create EMP wave!");
		list.add("");
		list.add("Ammo: Energy Cell");
		list.add("Damage: 25 - 35");
	}

	@Override
	public Multimap getItemAttributeModifiers() {
		Multimap multimap = super.getItemAttributeModifiers();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
				new AttributeModifier(field_111210_e, "Weapon modifier", 4, 0));
		return multimap;
	}
}
