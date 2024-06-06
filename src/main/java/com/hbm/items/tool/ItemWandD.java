package com.hbm.items.tool;

import java.util.List;
import java.util.Random;

import com.hbm.config.SpaceConfig;
import com.hbm.dim.CelestialBody;
import com.hbm.dim.DebugTeleporter;
import com.hbm.dim.SolarSystem;
import com.hbm.dim.trait.CBT_Atmosphere;
import com.hbm.dim.trait.CBT_Atmosphere.FluidEntry;
import com.hbm.entity.mob.EntityWarBehemoth;
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

			if(stack.stackTagCompound == null)
				stack.stackTagCompound = new NBTTagCompound();
			
			if(!player.isSneaking()) {
				int targetId = stack.stackTagCompound.getInteger("dim");
				if(targetId == 0) targetId++; // skip blank

				SolarSystem.Body target = SolarSystem.Body.values()[targetId];

				DebugTeleporter.teleport(player, target.getBody().dimensionId, player.posX, 300, player.posZ, true);
				player.addChatMessage(new ChatComponentText("Teleported to: " + target.getBody().getUnlocalizedName()));

			} else {
				int targetId = stack.stackTagCompound.getInteger("dim");
				targetId++;

				if(targetId >= SolarSystem.Body.values().length) {
					targetId = 1;
				}
				
				stack.stackTagCompound.setInteger("dim", targetId);

				SolarSystem.Body target = SolarSystem.Body.values()[targetId];

				player.addChatMessage(new ChatComponentText("Set teleport target to: " + target.getBody().getUnlocalizedName()));
			}
		} else {
			// TESTING: View atmospheric data
			CBT_Atmosphere atmosphere = CelestialBody.getTrait(world, CBT_Atmosphere.class);

			boolean isVacuum = true;
			if(atmosphere != null) {
				for(FluidEntry entry : atmosphere.fluids) {
					// if(entry.pressure > 0.001) {
						player.addChatMessage(new ChatComponentText("Atmosphere: " + entry.fluid.getUnlocalizedName() + " - " + entry.pressure + "bar"));
						isVacuum = false;
					// }
				}
			}

			if(isVacuum)
				player.addChatMessage(new ChatComponentText("Atmosphere: NEAR VACUUM"));

			// // TESTING: Sets moho and the moon to post-terraformed
			// if(world.provider.dimensionId == SpaceConfig.mohoDimension) {
			// 	if(CelestialBody.hasTrait(world, CBT_Atmosphere.class)) {
			// 		CelestialBody.clearTraits(world);
			// 		player.addChatMessage(new ChatComponentText("RETURN TO MEAN"));
			// 	} else {
			// 		CelestialBody.setTraits(world, new CBT_Atmosphere(Fluids.COALCREOSOTE, 1D), new CBT_Temperature(10F));
			// 		player.addChatMessage(new ChatComponentText("Made MOHO HORRIBLE, why did you do this."));
			// 	}
			// }

			// if(world.provider.dimensionId == SpaceConfig.moonDimension) {
			// 	if(CelestialBody.hasTrait(world, CBT_Atmosphere.class)) {
			// 		CelestialBody.clearTraits(world);
			// 		player.addChatMessage(new ChatComponentText("ONE MILLION DEAD WORLDS"));
			// 	} else {
			// 		CelestialBody.setTraits(world, new CBT_Atmosphere(Fluids.AIR, 1D), new CBT_Temperature(10F));
			// 		player.addChatMessage(new ChatComponentText("Made MOON breathable."));
			// 	}
			// }
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