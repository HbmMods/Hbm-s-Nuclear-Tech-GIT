package com.hbm.items.tool;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.SpaceConfig;
import com.hbm.dim.CelestialBody;
import com.hbm.dim.DebugTeleporter;
import com.hbm.dim.SolarSystem;
import com.hbm.dim.orbit.WorldProviderOrbit;
import com.hbm.dim.trait.CBT_Atmosphere;
import com.hbm.dim.trait.CBT_Atmosphere.FluidEntry;
import com.hbm.dim.trait.CelestialBodyTrait.CBT_Destroyed;
import com.hbm.lib.Library;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
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

				if(targetId == 0) {
					DebugTeleporter.teleport(player, SpaceConfig.orbitDimension, player.posX, 128, player.posZ, false);
					player.addChatMessage(new ChatComponentText("Teleported to: ORBIT"));
				} else {
					SolarSystem.Body target = SolarSystem.Body.values()[targetId];
	
					DebugTeleporter.teleport(player, target.getBody().dimensionId, player.posX, 300, player.posZ, true);
					player.addChatMessage(new ChatComponentText("Teleported to: " + target.getBody().getUnlocalizedName()));
				}

			} else {
				int targetId = stack.stackTagCompound.getInteger("dim");
				targetId++;

				if(targetId >= SolarSystem.Body.values().length) {
					targetId = 0;
				}
				
				stack.stackTagCompound.setInteger("dim", targetId);

				if(targetId == 0) {
					player.addChatMessage(new ChatComponentText("Set teleport target to: ORBIT"));
				} else {
					SolarSystem.Body target = SolarSystem.Body.values()[targetId];
					player.addChatMessage(new ChatComponentText("Set teleport target to: " + target.getBody().getUnlocalizedName()));
				}
			}
		} else if(!(world.provider instanceof WorldProviderOrbit)) {
			if(!player.isSneaking()) {
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
			} else {
				CelestialBody star = CelestialBody.getStar(world);

				if(!star.hasTrait(CBT_Destroyed.class)) {

					// TESTING: END OF TIME
					star.modifyTraits(new CBT_Destroyed());
		
					// TESTING: END OF LIFE
					CelestialBody.degas(world);
		
					// GOD
					// DAMN
					player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "GOD"));
					player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "DAMN"));
					player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "THE"));
					player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "" + EnumChatFormatting.OBFUSCATED + "SUN"));
				} else {

					star.clearTraits();
					CelestialBody.clearTraits(world);
					
					player.addChatMessage(new ChatComponentText("kidding"));
				}
			}
		} else {
			world.setBlock(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY) - 1, MathHelper.floor_double(player.posZ), ModBlocks.concrete);
		}

		return stack;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		list.add("Dimension teleporter and atmosphere debugger.");

		if(stack.stackTagCompound != null) {
			int targetId = stack.stackTagCompound.getInteger("dim");
			if(targetId == 0) {
				list.add("Teleportation target: ORBIT");
			} else {
				SolarSystem.Body target = SolarSystem.Body.values()[targetId];
				list.add("Teleportation target: " + target.getBody().getUnlocalizedName());
			}
		}
	}
}