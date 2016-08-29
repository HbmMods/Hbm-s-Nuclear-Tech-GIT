package com.hbm.items;

import java.util.List;
import java.util.Random;

import com.hbm.entity.EntityBullet;
import com.hbm.entity.EntityCombineBall;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

public class GunPM extends Item {

	Random rand = new Random();

    public GunPM()
    {
        this.maxStackSize = 1;
        this.setMaxDamage(10000);
    }

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.bow;
	}

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

	public void onUsingTick(ItemStack stack, EntityPlayer player, int count)
    {
		World world = player.worldObj;

		boolean flag = player.capabilities.isCreativeMode
				|| EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;
		if ((player.capabilities.isCreativeMode || player.inventory.hasItem(ModItems.gun_osipr_ammo))
				&& count % 3 == 0) {
			EntityBullet entityarrow = new EntityBullet(world, player, 3.0F, 35, 45, false, false);
			entityarrow.setDamage(100 + rand.nextInt(50));

			world.playSoundAtEntity(player, "random.explode", 1.0F, 1.5F + (rand.nextFloat() / 4));

			if (flag) {
				entityarrow.canBePickedUp = 2;
			} else {
				player.inventory.consumeInventoryItem(ModItems.gun_osipr_ammo);
			}

			if (!world.isRemote) {
				world.spawnEntityInWorld(entityarrow);
			}
		}
	}

	@Override
	public int getItemEnchantability() {
		return 0;
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {

		list.add("EVERYBODY HAS TO DIE!");
		list.add("");
		list.add("Ammo: x");
		list.add("Damage: x - x");
	}
}
