package com.hbm.items.weapon;

import com.hbm.handler.GunConfiguration;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemGunDart extends ItemGunBase {

	public ItemGunDart(GunConfiguration config) {
		super(config);
	}
	
	public static void writePlayer(ItemStack stack, EntityPlayer player) {
		
		if(!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();
		
		stack.stackTagCompound.setString("player", player.getDisplayName());
	}
	
	public static EntityPlayer readPlayer(ItemStack stack) {
		
		if(!stack.hasTagCompound())
			return null;
		
		return MinecraftServer.getServer().getConfigurationManager().func_152612_a(stack.stackTagCompound.getString("player"));
	}
	
	public void startAction(ItemStack stack, World world, EntityPlayer player, boolean main) {
		
		if(main) {
			super.startAction(stack, world, player, main);
		} else {
			
			EntityPlayer target = readPlayer(stack);
			
			if(target != null) {
				
				int dim = target.worldObj.provider.dimensionId;
				int x = (int)target.posX;
				int y = (int)target.posY;
				int z = (int)target.posZ;
				int dist = (int) target.getDistanceToEntity(player);
				
				player.addChatComponentMessage(new ChatComponentText(target.getDisplayName()).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));
				player.addChatComponentMessage(new ChatComponentText("Dim: " + dim + " X:" + x + " Y:" + y + " Z:" + z + " (" + dist + " blocks away)").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));
			} else {
				
				player.addChatComponentMessage(new ChatComponentText("No Target").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
			}
		}
	}
}
