package com.hbm.items.special;

import java.util.List;

import com.hbm.extprop.HbmLivingProps;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemCigarette extends Item  {

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.bow;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 30;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
		return stack;
	}

	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
		stack.stackSize--;

		if(!world.isRemote) {
			HbmLivingProps.incrementBlackLung(player, 2000);
			HbmLivingProps.incrementAsbestos(player, 2000);
			HbmLivingProps.incrementRadiation(player, 100F);
			
			world.playSoundEffect(player.posX, player.posY, player.posZ, "hbm:player.cough", 1.0F, 1.0F);
			
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setString("type", "vomit");
			nbt.setString("mode", "smoke");
			nbt.setInteger("count", 30);
			nbt.setInteger("entity", player.getEntityId());
			PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(nbt, 0, 0, 0),  new TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 25));
		}
		
		return stack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		list.add(EnumChatFormatting.RED + "✓ Asbestos filter");
		list.add(EnumChatFormatting.RED + "✓ High in tar");
		list.add(EnumChatFormatting.RED + "✓ Tobacco contains 100% Polonium-210");
		list.add(EnumChatFormatting.RED + "✓ Yum");
	}
}
