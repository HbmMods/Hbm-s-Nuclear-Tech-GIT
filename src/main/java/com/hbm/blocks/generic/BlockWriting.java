package com.hbm.blocks.generic;

import com.hbm.blocks.machine.BlockPillar;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class BlockWriting extends BlockPillar {

	public BlockWriting(Material mat, String top) {
		super(mat, top);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {
			return true;
			
		} else if(!player.isSneaking()) {
			
			ChatStyle red = new ChatStyle().setColor(EnumChatFormatting.RED);
			player.addChatMessage(new ChatComponentText("You should not have come here.").setChatStyle(red));
			player.addChatMessage(new ChatComponentText("This is not a place of honor. No great deed is commemorated here.").setChatStyle(red));
			player.addChatMessage(new ChatComponentText("Nothing of value is here.").setChatStyle(red));
			player.addChatMessage(new ChatComponentText("What is here is dangerous and repulsive.").setChatStyle(red));
			player.addChatMessage(new ChatComponentText("We considered ourselves a powerful culture. We harnessed the hidden fire, and used it for our own purposes.").setChatStyle(red));
			player.addChatMessage(new ChatComponentText("Then we saw the fire could burn within living things, unnoticed until it destroyed them.").setChatStyle(red));
			player.addChatMessage(new ChatComponentText("And we were afraid.").setChatStyle(red));
			player.addChatMessage(new ChatComponentText("We built great tombs to hold the fire for one hundred thousand years, after which it would no longer kill.").setChatStyle(red));
			player.addChatMessage(new ChatComponentText("If this place is opened, the fire will not be isolated from the world, and we will have failed to protect you.").setChatStyle(red));
			player.addChatMessage(new ChatComponentText("Leave this place and never come back.").setChatStyle(red));
			return true;
			
		} else {
			return false;
		}
	}
}
