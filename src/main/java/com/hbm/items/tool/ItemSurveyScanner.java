package com.hbm.items.tool;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockBedrockOreTE.TileEntityBedrockOre;
import com.hbm.handler.threading.PacketThreading;
import com.hbm.items.ModItems;
import com.hbm.packet.toclient.AuxParticlePacketNT;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemSurveyScanner extends Item {

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		if(!world.isRemote) {
			
			int x = (int) Math.floor(player.posX);
			int y = (int) Math.floor(player.posY);
			int z = (int) Math.floor(player.posZ);
			
			boolean hasOil = false;
			boolean hasColtan = false;
			boolean hasBedrockOil = false;
			boolean hasDepth = false;
			boolean hasSchist = false;
			boolean hasAussie = false;
			TileEntityBedrockOre tile = null;
			
			for(int a = -5; a <= 5; a++) {
				for(int b = -5; b <= 5; b++) {
					for(int i =  y + 15; i > 1; i -= 2) {
						
						Block block = world.getBlock(x + a * 5, i, z + b * 5);
						
						//wow, this sucks!
						if(block == ModBlocks.ore_oil) hasOil = true;
						else if(block == ModBlocks.ore_coltan) hasColtan = true;
						else if(block == ModBlocks.stone_depth) hasDepth = true;
						else if(block == ModBlocks.stone_depth_nether) hasDepth = true;
						else if(block == ModBlocks.stone_gneiss) hasSchist = true;
						else if(block == ModBlocks.ore_australium) hasAussie = true;
						else if(block == ModBlocks.ore_bedrock_oil) {
							hasBedrockOil = true;
							
							NBTTagCompound data = new NBTTagCompound();
							data.setString("type", "marker");
							data.setInteger("color", 0x000000);
							data.setInteger("expires", 15_000);
							data.setDouble("dist", 300D);
							data.setString("label", "Bedrock Oil");
							PacketThreading.createSendToThreadedPacket(new AuxParticlePacketNT(data, x + a * 5, i, z + b * 5), (EntityPlayerMP) player);
							
							break;
						}
					}
				}
			}
			
			int cX = ((x >> 4) << 4) + 8;
			int cZ = ((z >> 4) << 4) + 8;
			
			for(int i = -2; i <= 2; i++) for(int j = -2; j <= 2; j++) {
				Block center = world.getBlock(cX + i, 0, cZ + j);
				
				System.out.println(cX + " " + cZ);
				
				if(center == ModBlocks.ore_bedrock) {
					
					NBTTagCompound data = new NBTTagCompound();
					data.setString("type", "marker");
					data.setInteger("color", 0xff0000);
					data.setInteger("expires", 15_000);
					data.setDouble("dist", 300D);
					data.setString("label", "Bedrock Ore");
					PacketThreading.createSendToThreadedPacket(new AuxParticlePacketNT(data, cX + i, 0, cZ + j), (EntityPlayerMP) player);
					
					tile = (TileEntityBedrockOre) world.getTileEntity(cX + i, 0, cZ + j);
				}
			}

			if(hasOil) player.addChatComponentMessage(new ChatComponentText("Found OIL!").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.BLACK)));
			if(hasBedrockOil) player.addChatComponentMessage(new ChatComponentText("Found BEDROCK OIL!").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.BLACK)));
			if(hasColtan) player.addChatComponentMessage(new ChatComponentText("Found COLTAN!").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GOLD)));
			if(hasDepth) player.addChatComponentMessage(new ChatComponentText("Found DEPTH ROCK!").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GRAY)));
			if(hasSchist) player.addChatComponentMessage(new ChatComponentText("Found SCHIST!").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_AQUA)));
			if(hasAussie) player.addChatComponentMessage(new ChatComponentText("Found AUSTRALIUM!").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));
			if(tile != null && tile.resource != null) player.addChatComponentMessage(new ChatComponentText("Found BEDROCK ORE for " + tile.resource.getDisplayName() + "!").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
		}
		
		player.swingItem();
		
		return stack;
		
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int i, float f0, float f1, float f2) {
		if(world.getBlock(x, y, z) == ModBlocks.block_beryllium && player.inventory.hasItem(ModItems.entanglement_kit)) {
			player.travelToDimension(1);
			return true;
		}

		return false;
	}
}