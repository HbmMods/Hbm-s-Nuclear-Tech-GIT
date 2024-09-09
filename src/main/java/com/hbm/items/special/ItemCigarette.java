package com.hbm.items.special;

import java.util.List;

import com.hbm.extprop.HbmLivingProps;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.AuxParticlePacketNT;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
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
			
			if(this == ModItems.cigarette) {
				HbmLivingProps.incrementBlackLung(player, 2000);
				HbmLivingProps.incrementAsbestos(player, 2000);
				HbmLivingProps.incrementRadiation(player, 100F);
				
				ItemStack helmet = player.getEquipmentInSlot(4);
				if(helmet != null && helmet.getItem() == ModItems.no9) {
					player.triggerAchievement(MainRegistry.achNo9);
				}
			}
			
			if(this == ModItems.crackpipe) {
				HbmLivingProps.incrementBlackLung(player, 500);
				player.addPotionEffect(new PotionEffect(Potion.confusion.id, 200, 0));
				player.heal(10F);
			}
			
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
		
		if(this == ModItems.cigarette) {
			list.add(EnumChatFormatting.RED + "✓ Asbestos filter");
			list.add(EnumChatFormatting.RED + "✓ High in tar");
			list.add(EnumChatFormatting.RED + "✓ Tobacco contains 100% Polonium-210");
			list.add(EnumChatFormatting.RED + "✓ Yum");
		} else {
			String[] colors = new String[] {
					EnumChatFormatting.RED + "",
					EnumChatFormatting.GOLD + "",
					EnumChatFormatting.YELLOW + "",
					EnumChatFormatting.GREEN + "",
					EnumChatFormatting.AQUA + "",
					EnumChatFormatting.BLUE + "",
					EnumChatFormatting.DARK_PURPLE + "",
					EnumChatFormatting.LIGHT_PURPLE + "",
			};
			int len = 2000;
			list.add("This can't be good for me, but I feel " + colors[(int)(System.currentTimeMillis() % len * colors.length / len)] + "GREAT");
		}
	}
}
