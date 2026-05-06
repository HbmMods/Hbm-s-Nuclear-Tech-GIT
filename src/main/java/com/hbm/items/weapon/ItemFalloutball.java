package com.hbm.items.weapon;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import com.hbm.entity.projectile.EntityFalloutball;
import net.minecraft.world.World;

public class ItemFalloutball extends Item {
	public ItemFalloutball(){}

	public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_) {
		if (!p_77659_3_.capabilities.isCreativeMode) {
			--p_77659_1_.stackSize;
		}

		p_77659_2_.playSoundAtEntity(p_77659_3_, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

		if (!p_77659_2_.isRemote) {
			p_77659_2_.spawnEntityInWorld(new EntityFalloutball(p_77659_2_, p_77659_3_));
		}

		return p_77659_1_;
	}
}
