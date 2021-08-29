package com.hbm.items.special;

import com.google.common.annotations.Beta;
import com.hbm.interfaces.Untested;
import com.hbm.potion.HbmPotion;
import com.hbm.util.ContaminationUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
@Untested
@Beta
public class Eye extends ItemCustomLore
{

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World worldIn, EntityPlayer playerIn)
	{
		playerIn.addPotionEffect(new PotionEffect(HbmPotion.unconscious.id, 30));
		ContaminationUtil.applyDigammaDirect(playerIn, 1.0F);
		return stack;
	}

}
