package com.hbm.items.tool;

import java.util.List;

import com.hbm.lib.Library;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemDiscord extends Item {

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {

		MovingObjectPosition pos = Library.rayTrace(player, 100, 1);

		if(pos != null && pos.typeOfHit == MovingObjectType.BLOCK) {

			if(!world.isRemote) {

				if(player.isRiding())
					player.mountEntity(null);

				ForgeDirection dir = ForgeDirection.getOrientation(pos.sideHit);

				world.playSoundEffect(player.posX, player.posY, player.posZ, "mob.endermen.portal", 1.0F, 1.0F);

				player.setPositionAndUpdate(pos.hitVec.xCoord + dir.offsetX, pos.hitVec.yCoord + dir.offsetY - 1, pos.hitVec.zCoord + dir.offsetZ);

				world.playSoundEffect(player.posX, player.posY, player.posZ, "mob.endermen.portal", 1.0F, 1.0F);
				player.fallDistance = 0.0F;
			}

			for(int i = 0; i < 32; ++i)
				world.spawnParticle("portal", player.posX, player.posY + player.getRNG().nextDouble() * 2.0D, player.posZ, player.getRNG().nextGaussian(), 0.0D, player.getRNG().nextGaussian());
		}

		return stack;
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {

		list.add(EnumChatFormatting.YELLOW + "" + EnumChatFormatting.ITALIC + "I've seen the Rod of Discord and honestly");
		list.add(EnumChatFormatting.YELLOW + "" + EnumChatFormatting.ITALIC + "it's not as amazing as people say.");
		list.add("");
		list.add(EnumChatFormatting.RED + "" + EnumChatFormatting.ITALIC + "Rod of Discord is crucial in so many boss fights.");
		list.add(EnumChatFormatting.RED + "" + EnumChatFormatting.ITALIC + "Imagine getting coiled by worm bosses.");
		list.add("");
		list.add(EnumChatFormatting.YELLOW + "" + EnumChatFormatting.ITALIC + "Oh, you mean the Terraria item.");
		list.add(EnumChatFormatting.YELLOW + "" + EnumChatFormatting.ITALIC + "Idk about that thing.");
	}
}
