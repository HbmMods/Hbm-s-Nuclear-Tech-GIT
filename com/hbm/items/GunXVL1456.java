package com.hbm.items;

import java.util.Random;

import com.hbm.entity.EntityBullet;
import com.hbm.entity.EntityMiniNuke;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

public class GunXVL1456 extends Item {

	Random rand = new Random();

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.bow;
	}

	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int i) {

		int j = this.getMaxItemUseDuration(stack) - i;
		ArrowLooseEvent event = new ArrowLooseEvent(player, stack, j);
		MinecraftForge.EVENT_BUS.post(event);
		// if (event.isCanceled()) {
		// return;
		// }
        j = event.charge;

		if (player.isSneaking() && j >= 20) {
			boolean flag = player.capabilities.isCreativeMode
					|| EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;

			if ((flag || player.inventory.hasItem(ModItems.gun_xvl1456_ammo) && stack.hasTagCompound())) {
				EntityBullet entitybullet = new EntityBullet(world, player, 3.0F, j, j + 5, false, "tauDay");

				entitybullet.setDamage(j + rand.nextInt(6));

				world.playSoundAtEntity(player, "fireworks.blast", 1.0F,
						1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) * 0.75F);

				if (flag) {
					entitybullet.canBePickedUp = 2;
				} else {
					player.inventory.consumeInventoryItem(ModItems.gun_xvl1456_ammo);
				}

				entitybullet.setIsCritical(true);

				if (!world.isRemote) {
					world.spawnEntityInWorld(entitybullet);
				}
			}
		}
	}

	public int getMaxItemUseDuration(ItemStack p_77626_1_) {
		return 72000;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_) {
		ArrowNockEvent event = new ArrowNockEvent(p_77659_3_, p_77659_1_);
		MinecraftForge.EVENT_BUS.post(event);
		// if (event.isCanceled()) {
		// return event.result;
		// }
		// Made uncancelable to prevent intermod idiocy
		{
			p_77659_3_.setItemInUse(p_77659_1_, this.getMaxItemUseDuration(p_77659_1_));
			}

		return p_77659_1_;
	}

	public void onUpdate(ItemStack p_77663_1_, World p_77663_2_, Entity p_77663_3_, int p_77663_4_,
			boolean p_77663_5_) {
		if(p_77663_3_ instanceof EntityPlayer)
		{
			if(((EntityPlayer)p_77663_3_).getItemInUseCount() < getMaxItemUseDuration(p_77663_1_) - 200 && p_77663_3_.isSneaking() && ((EntityPlayer)p_77663_3_).getItemInUseCount() != 0)
			{
				if(!p_77663_2_.isRemote)
				{
					p_77663_2_.createExplosion(p_77663_3_, p_77663_3_.posX, p_77663_3_.posY, p_77663_3_.posZ, 10.0F, true);
					((EntityPlayer)p_77663_3_).dropOneItem(false);
				}
			}
		}
	}

	public void onUsingTick(ItemStack stack, EntityPlayer player, int count)
    {
		World world = player.worldObj;
		
		if (!player.isSneaking()) {
			boolean flag = player.capabilities.isCreativeMode
					|| EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;
			if ((player.capabilities.isCreativeMode || player.inventory.hasItem(ModItems.gun_xvl1456_ammo)) && count % 5 == 0) {

				EntityBullet entityarrow = new EntityBullet(world, player, 3.0F, 35, 45, false, "eyyOk");
				entityarrow.setDamage(35 + rand.nextInt(45 - 35));

				world.playSoundAtEntity(player, "fireworks.blast", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 0.5F);

				if (flag) {
					entityarrow.canBePickedUp = 2;
				} else {
					player.inventory.consumeInventoryItem(ModItems.gun_xvl1456_ammo);
				}
				
				if (!world.isRemote) {
					world.spawnEntityInWorld(entityarrow);
				}
			}
		} else {
			if (count % 20 == 0 && this.getMaxItemUseDuration(stack) - count != 0) {
				boolean flag = player.capabilities.isCreativeMode
						|| EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;
				if ((player.capabilities.isCreativeMode || player.inventory.hasItem(ModItems.gun_xvl1456_ammo))) {
					if (!flag) {
						player.inventory.consumeInventoryItem(ModItems.gun_xvl1456_ammo);
					}
				}
			}
		}
	}

	@Override
	public int getItemEnchantability() {
		return 1;
	}

}
