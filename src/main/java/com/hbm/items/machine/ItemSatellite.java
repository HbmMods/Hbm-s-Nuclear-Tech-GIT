package com.hbm.items.machine;

import java.util.List;

import com.hbm.dim.CelestialBody;
import com.hbm.items.ISatChip;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemCustomMissilePart;
import com.hbm.saveddata.satellites.Satellite;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

public class ItemSatellite extends ItemCustomMissilePart implements ISatChip {
    
	public ItemSatellite() {
		makeWarhead(WarheadType.SATELLITE, 15F, 16_000, PartSize.SIZE_20);
	}

	public ItemSatellite(int mass) {
		makeWarhead(WarheadType.SATELLITE, 15F, mass, PartSize.SIZE_20);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		super.addInformation(itemstack, player, list, bool);

		list.add("Satellite frequency: " + getFreq(itemstack));
		
		if(this == ModItems.sat_foeq)
			list.add("Gives you an achievement. That's it.");
		
		if(this == ModItems.sat_gerald) {
			list.add("Single use.");
			list.add("Requires orbital module.");
			list.add("Melter of CPUs, bane of every server owner.");
		}
		
		if(this == ModItems.sat_laser)
			list.add("Allows to summon lasers with a 15 second cooldown.");
		
		if(this == ModItems.sat_mapper)
			list.add("Displays currently loaded chunks.");
		
		if(this == ModItems.sat_miner)
			list.add("Will deliver ore powders to a cargo landing pad.");
		
		if(this == ModItems.sat_lunar_miner)
			list.add("Mines moon turf to deliver it to a cargo landing pad.");
		
		if(this == ModItems.sat_radar)
			list.add("Shows a map of active entities.");
		
		if(this == ModItems.sat_resonator)
			list.add("Allows for teleportation with no cooldown.");
		
		if(this == ModItems.sat_scanner)
			list.add("Creates a topdown map of underground ores.");

		if(CelestialBody.inOrbit(player.worldObj))
			list.add(EnumChatFormatting.BOLD + "Interact to deploy into orbit");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if(!CelestialBody.inOrbit(world)) return stack;

		if(!world.isRemote) {
			int targetDimensionId = CelestialBody.getTarget(world, (int)player.posX, (int)player.posZ).body.dimensionId;
			WorldServer targetWorld = DimensionManager.getWorld(targetDimensionId);
			if(targetWorld == null) {
				DimensionManager.initDimension(targetDimensionId);
				targetWorld = DimensionManager.getWorld(targetDimensionId);
	
				if(targetWorld == null) return stack;
			}

			Satellite.orbit(targetWorld, Satellite.getIDFromItem(stack.getItem()), getFreq(stack), player.posX, player.posY, player.posZ);

			player.addChatMessage(new ChatComponentText("Satellite launched successfully!"));
		}

		stack.stackSize--;

		return stack;
	}

}
