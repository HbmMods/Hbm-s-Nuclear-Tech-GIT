package com.hbm.items.tool;

import com.hbm.extprop.HbmPlayerProps;
import com.hbm.items.special.ItemCustomLore;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.PlayerInformPacket;
import com.hbm.util.I18nUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ItemChronometer extends ItemCustomLore
{
	public ItemChronometer()
	{
		setMaxStackSize(1);
	}
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World worldIn, EntityPlayer playerIn)
	{
		if (!worldIn.isRemote)
		{
			Double playerAge = HbmPlayerProps.getAge(playerIn);
			byte playerBirthday = HbmPlayerProps.getBirthday(playerIn);
			worldIn.playSoundAtEntity(playerIn, "hbm:item.syringe", 1.0F, 1.0F);
			playerIn.addChatMessage(new ChatComponentText(I18nUtil.resolveKey("desc.player.ageData", playerAge.intValue(), playerBirthday)));
		}
		return stack;
	}
}
