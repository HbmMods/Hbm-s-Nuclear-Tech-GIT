package com.hbm.items.weapon;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Multimap;
import com.hbm.entity.projectile.EntityFire;
import com.hbm.entity.projectile.EntityPlasmaBeam;
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
import net.minecraftforge.event.entity.player.ArrowNockEvent;

public class GunImmolator extends Item {

	Random rand = new Random();

	public GunImmolator() {
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
		new ArrowNockEvent(p_77659_3_, p_77659_1_);
		{
			p_77659_3_.setItemInUse(p_77659_1_, this.getMaxItemUseDuration(p_77659_1_));
		}

		return p_77659_1_;
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
		World world = player.worldObj;

		if (!player.isSneaking()) {
			boolean flag = player.capabilities.isCreativeMode
					|| EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;
			if ((player.capabilities.isCreativeMode || player.inventory.hasItem(ModItems.gun_immolator_ammo))) {
				EntityFire entityarrow = new EntityFire(world, player, 3.0F);
				entityarrow.setDamage(6 + rand.nextInt(5));

				if (flag) {
					entityarrow.canBePickedUp = 2;
				} else {
					if(count % 10 == 0)
						player.inventory.consumeInventoryItem(ModItems.gun_immolator_ammo);
				}

				if(count == this.getMaxItemUseDuration(stack))
					world.playSoundAtEntity(player, "hbm:weapon.flamethrowerIgnite", 1.0F, 1F);
				if(count % 5 == 0)
					world.playSoundAtEntity(player, "hbm:weapon.flamethrowerShoot", 1.0F, 1F);

				if (!world.isRemote) {
					world.spawnEntityInWorld(entityarrow);
				}
			}
		} else {
			boolean flag = player.capabilities.isCreativeMode
					|| EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;
			if ((player.capabilities.isCreativeMode || player.inventory.hasItem(ModItems.gun_immolator_ammo))) {

				EntityPlasmaBeam plasma = new EntityPlasmaBeam(world, player, 1F);
				
				if (flag) {
					plasma.canBePickedUp = 2;
				} else {
					if(count % 4 == 0)
						player.inventory.consumeInventoryItem(ModItems.gun_immolator_ammo);
				}

				if(count == this.getMaxItemUseDuration(stack))
					world.playSoundAtEntity(player, "hbm:weapon.immolatorIgnite", 1.0F, 1F);
				if(count % 10 == 0)
					world.playSoundAtEntity(player, "hbm:weapon.immolatorShoot", 1.0F, 1F);
				
				if (!world.isRemote)
					world.spawnEntityInWorld(plasma);
			}
		}
	}

	@Override
	public int getItemEnchantability() {
		return 0;
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {

		list.add("Hold right mouse button");
		list.add("to shoot fire,");
		list.add("sneak to shoot");
		list.add("plasma beams!");
		list.add("");
		list.add("Ammo: Immolator Fuel");
		list.add("Damage: 5");
		list.add("Secondary Damage: 25 - 45");
	}

	@Override
	public Multimap getItemAttributeModifiers() {
		Multimap multimap = super.getItemAttributeModifiers();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
				new AttributeModifier(field_111210_e, "Weapon modifier", 4, 0));
		return multimap;
	}
}
