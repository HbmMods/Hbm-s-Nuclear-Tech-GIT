package com.hbm.items.weapon;

import com.hbm.handler.GunConfiguration;

import api.hbm.item.IDesignatorItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemGunDart extends ItemGunBase implements IDesignatorItem {

	public ItemGunDart(GunConfiguration config) {
		super(config);
	}
	
	public static void writePlayer(ItemStack stack, EntityPlayer player) {
		
		if(!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();

		stack.stackTagCompound.setString("player", player.getDisplayName());
		stack.stackTagCompound.setLong("lease", player.worldObj.getTotalWorldTime() + 60 * 60 * 20);
	}
	
	public static EntityPlayer readPlayer(World world, ItemStack stack) {
		
		if(!stack.hasTagCompound())
			return null;
		
		if(stack.stackTagCompound.getLong("lease") < world.getTotalWorldTime())
			return null;
		
		return MinecraftServer.getServer().getConfigurationManager().func_152612_a(stack.stackTagCompound.getString("player"));
	}
	
	public void startAction(ItemStack stack, World world, EntityPlayer player, boolean main) {
		
		if(main) {
			super.startAction(stack, world, player, main);
		} else {
			
			EntityPlayer target = readPlayer(world, stack);
			
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

	@Override
	public boolean isReady(World world, ItemStack stack, int x, int y, int z) {
		EntityPlayer target = readPlayer(world, stack);
		return target != null && target.dimension == world.provider.dimensionId;
	}

	@Override
	public Vec3 getCoords(World world, ItemStack stack, int x, int y, int z) {
		EntityPlayer target = readPlayer(world, stack);
		return Vec3.createVectorHelper(target.posX, target.posY, target.posZ);
	}
}
