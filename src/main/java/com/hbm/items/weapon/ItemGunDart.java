package com.hbm.items.weapon;

import com.hbm.handler.GunConfiguration;

<<<<<<< HEAD
=======
import api.hbm.item.IDesignatorItem;
>>>>>>> master
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
<<<<<<< HEAD
import net.minecraft.world.World;

public class ItemGunDart extends ItemGunBase {
=======
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemGunDart extends ItemGunBase implements IDesignatorItem {
>>>>>>> master

	public ItemGunDart(GunConfiguration config) {
		super(config);
	}
	
	public static void writePlayer(ItemStack stack, EntityPlayer player) {
		
		if(!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();
<<<<<<< HEAD
		
		stack.stackTagCompound.setString("player", player.getDisplayName());
	}
	
	public static EntityPlayer readPlayer(ItemStack stack) {
=======

		stack.stackTagCompound.setString("player", player.getDisplayName());
		stack.stackTagCompound.setLong("lease", player.worldObj.getTotalWorldTime() + 60 * 60 * 20);
	}
	
	public static EntityPlayer readPlayer(World world, ItemStack stack) {
>>>>>>> master
		
		if(!stack.hasTagCompound())
			return null;
		
<<<<<<< HEAD
=======
		if(stack.stackTagCompound.getLong("lease") < world.getTotalWorldTime())
			return null;
		
>>>>>>> master
		return MinecraftServer.getServer().getConfigurationManager().func_152612_a(stack.stackTagCompound.getString("player"));
	}
	
	public void startAction(ItemStack stack, World world, EntityPlayer player, boolean main) {
		
		if(main) {
			super.startAction(stack, world, player, main);
		} else {
			
<<<<<<< HEAD
			EntityPlayer target = readPlayer(stack);
=======
			EntityPlayer target = readPlayer(world, stack);
>>>>>>> master
			
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
<<<<<<< HEAD
=======

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
>>>>>>> master
}
