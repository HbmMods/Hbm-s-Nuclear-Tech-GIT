package com.hbm.items.tool;

import java.util.List;
import java.util.Random;

import com.hbm.config.SpaceConfig;
import com.hbm.dim.CelestialBody;
import com.hbm.dim.DebugTeleporter;
import com.hbm.dim.trait.CBT_Atmosphere;
import com.hbm.dim.trait.CBT_Temperature;
import com.hbm.dim.trait.CelestialBodyTrait;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.lib.Library;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemWandD extends Item {

	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		if(world.isRemote)
			return stack;
		
		MovingObjectPosition pos = Library.rayTrace(player, 500, 1, false, true, false);
		
		if(pos != null) {
	
			EntityPlayerMP thePlayer = (EntityPlayerMP) player;
				
			//if(!player.isSneaking())
			//thePlayer.mcServer.getConfigurationManager().transferPlayerToDimension(thePlayer, WorldConfig.ikeDimension, new DebugTeleporter(thePlayer.getServerForPlayer()));
			//else
			//System.out.println(player.dimension);
			
			if(stack.stackTagCompound == null)
			{
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setInteger("building", 0);
			}
			
			boolean up = player.rotationPitch <= 0.5F;
			
			if(!player.isSneaking()) {
				Random rand = new Random();
				
				switch(stack.stackTagCompound.getInteger("dim"))
				{
				case 0:
					DebugTeleporter.teleport(player, SpaceConfig.moonDimension, player.posX, 300, player.posZ);
					//thePlayer.mcServer.getConfigurationManager().transferPlayerToDimension(thePlayer, WorldConfig.moonDimension, new DebugTeleporter(thePlayer.getServerForPlayer()));
					break;
				case 1:
					DebugTeleporter.teleport(player, SpaceConfig.ikeDimension, player.posX, 300, player.posZ);
					//thePlayer.mcServer.getConfigurationManager().transferPlayerToDimension(thePlayer, WorldConfig.ikeDimension, new DebugTeleporter(thePlayer.getServerForPlayer()));
					break;
				case 2:
					DebugTeleporter.teleport(player, SpaceConfig.dunaDimension, player.posX, 300, player.posZ);
					//thePlayer.mcServer.getConfigurationManager().transferPlayerToDimension(thePlayer, WorldConfig.dunaDimension, new DebugTeleporter(thePlayer.getServerForPlayer()));
					break;
				case 3:
					DebugTeleporter.teleport(player, 0, player.posX, 300, player.posZ);
					break;
				case 4:
					DebugTeleporter.teleport(player, SpaceConfig.eveDimension, player.posX, 300, player.posZ);
					//thePlayer.mcServer.getConfigurationManager().transferPlayerToDimension(thePlayer, WorldConfig.dunaDimension, new DebugTeleporter(thePlayer.getServerForPlayer()));
					break;
				case 5:
					DebugTeleporter.teleport(player, SpaceConfig.dresDimension, player.posX, 300, player.posZ);
					//DebugTeleporter.teleport(player, WorldConfig.eveDimension, player.posX, 300, player.posZ);
					//thePlayer.mcServer.getConfigurationManager().transferPlayerToDimension(thePlayer, WorldConfig.dunaDimension, new DebugTeleporter(thePlayer.getServerForPlayer()));
					break;
				case 6:
					DebugTeleporter.teleport(player, SpaceConfig.mohoDimension, player.posX, 300, player.posZ);
					//DebugTeleporter.teleport(player, WorldConfig.eveDimension, player.posX, 300, player.posZ);
					//thePlayer.mcServer.getConfigurationManager().transferPlayerToDimension(thePlayer, WorldConfig.dunaDimension, new DebugTeleporter(thePlayer.getServerForPlayer()));
					break;
				case 7:
					DebugTeleporter.teleport(player, SpaceConfig.minmusDimension, player.posX, 300, player.posZ);
					break;
				case 8:
					DebugTeleporter.teleport(player, SpaceConfig.laytheDimension, player.posX, 300, player.posZ);
					break;
				}
			} else {
				if(stack.stackTagCompound == null) {
					stack.stackTagCompound = new NBTTagCompound();
					stack.stackTagCompound.setInteger("dim", 0);
				} else {
					int i = stack.stackTagCompound.getInteger("dim");
					i++;
					stack.stackTagCompound.setInteger("dim", i);
					if(i >= 9) {
						stack.stackTagCompound.setInteger("dim", 0);
					}
					
					switch(i) {
						case 0:
							player.addChatMessage(new ChatComponentText("Dim: Moon"));
							break;
						case 1:
							player.addChatMessage(new ChatComponentText("Dim: Ike"));
							break;
						case 2:
							player.addChatMessage(new ChatComponentText("Dim: Duna"));
							break;
						case 3:
							player.addChatMessage(new ChatComponentText("Dim: Kerbin"));
							break;
						case 4:
							player.addChatMessage(new ChatComponentText("Dim: Eve"));
							break;
						case 5:
							player.addChatMessage(new ChatComponentText("Dim: Dres"));
							break;
						case 6:
							player.addChatMessage(new ChatComponentText("Dim: Moho"));
							break;
						case 7:
							player.addChatMessage(new ChatComponentText("Dim: Minmus"));
							break;
						case 8:
							player.addChatMessage(new ChatComponentText("Dim: Laythe"));
							break;
						default:
							player.addChatMessage(new ChatComponentText("Dim: Moon"));
							break;
					}
				}
			}
		} else {

			// TESTING: Sets moho and the moon to post-terraformed
			if(world.provider.dimensionId == SpaceConfig.mohoDimension) {
				CelestialBody.setTraits(world, new CBT_Atmosphere(Fluids.AIR, 1F), CelestialBodyTrait.BREATHABLE);

				player.addChatMessage(new ChatComponentText("Made MOHO breathable."));
			}

			if(world.provider.dimensionId == SpaceConfig.moonDimension) {
				CelestialBody.setTraits(world, new CBT_Atmosphere(Fluids.AIR, 1F), CelestialBodyTrait.BREATHABLE,  new CBT_Temperature(10F));

				player.addChatMessage(new ChatComponentText("Made MOON breathable."));
			}
		}

		return stack;
	}
		

	
	



	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		list.add("Used for debugging purposes.");

		if(itemstack.stackTagCompound != null)
		{
			switch(itemstack.stackTagCompound.getInteger("dim"))
			{
			case 0:
				list.add("Dim: Moon");
				break;
			case 1:
				list.add("Dim: Ike");
				break;
			case 2:
				list.add("Dim: Duna");
				break;
			case 3:
				list.add("Dim: Kerbin");
				break;
			case 4:
				list.add("Dim: Eve");
				break;
			case 5:
				list.add("Dim: Dres");
				break;
			case 6:
				list.add("Dim: Moho");
				break;
			case 7:
				list.add("Dim: Minmus");
				break;
	}
}
	}
}