package com.hbm.items.tool;

import java.util.List;

import com.hbm.extprop.HbmLivingProps;
import com.hbm.extprop.HbmPlayerProps;
import com.hbm.interfaces.IItemHazard;
import com.hbm.items.special.ItemCustomLore;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.PlayerInformPacket;
import com.hbm.util.I18nUtil;

import net.minecraft.entity.Entity;
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
			final long playerAge = HbmPlayerProps.getAge(playerIn);
			final byte playerBirthday = HbmPlayerProps.getBirthday(playerIn);
			final long playerBirthyear = HbmPlayerProps.getBirthyear(playerIn); 
			worldIn.playSoundAtEntity(playerIn, "hbm:item.syringe", 1.0F, 1.0F);
//			playerIn.addChatMessage(new ChatComponentText(I18nUtil.resolveKey("desc.player.ageData", playerAge, playerBirthday, playerBirthyear)));
			PacketDispatcher.wrapper.sendTo(new PlayerInformPacket(I18nUtil.resolveKey("desc.player.ageData", playerAge, playerBirthday, playerBirthyear)), (EntityPlayerMP) playerIn);
		}
		return stack;
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
	{
		if (entity instanceof EntityPlayer)
		{
			final long pAge = HbmPlayerProps.getAge((EntityPlayer) entity);
			final byte pBDay = HbmPlayerProps.getBirthday((EntityPlayer) entity);
			final long pBYear = HbmPlayerProps.getBirthyear((EntityPlayer) entity);
			player.playSound("hbm:item.syringe", 1.0F, 1.0F);
			PacketDispatcher.wrapper.sendTo(new PlayerInformPacket(I18nUtil.resolveKey("desc.player.ageData", pAge, pBDay, pBYear)), (EntityPlayerMP) player);
		}
		return true;
	}
}
