package com.hbm.items.special;

import com.google.common.annotations.Beta;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.PlayerInformPacket;
import com.hbm.potion.HbmPotion;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;
import com.hbm.util.I18nUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
@Beta
public class Eye extends ItemCustomLore
{
	public Eye()
	{
		setMaxStackSize(1);
		setHasEffect();
	}
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World worldIn, EntityPlayer playerIn)
	{
		if (!worldIn.isRemote)
		{
			PacketDispatcher.wrapper.sendTo(new PlayerInformPacket(I18nUtil.resolveKey("eye.speakTo", I18nUtil.resolveKey("eye.speakTo.remember"))), (EntityPlayerMP) playerIn);
			PacketDispatcher.wrapper.sendTo(new PlayerInformPacket(I18nUtil.resolveKey("eye.respond.nothing")), (EntityPlayerMP) playerIn);
		}
		playerIn.addPotionEffect(new PotionEffect(HbmPotion.unconscious.id, 60*20));
//		ContaminationUtil.applyDigammaDirect(playerIn, 0.5F);
		ContaminationUtil.contaminate(playerIn, HazardType.DIGAMMA, ContaminationType.CREATIVE, 0.5f);
		return stack;
	}
	
	@Override
	public EnumRarity getRarity(ItemStack p_77613_1_)
	{
		return EnumRarity.epic;
	}

}
