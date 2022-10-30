package com.hbm.items.tool;

import com.hbm.items.machine.ItemSatChip;
import com.hbm.lib.Library;
import com.hbm.saveddata.SatelliteSavedData;
import com.hbm.saveddata.satellites.Satellite;
import com.hbm.saveddata.satellites.Satellite.Interfaces;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemSatDesignator extends ItemSatChip {

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		if(!world.isRemote) {
			
			Satellite sat = SatelliteSavedData.getData(world).getSatFromFreq(this.getFreq(stack));
			
			if(sat != null) {
				MovingObjectPosition pos = Library.rayTrace(player, 300, 1);
				
				ForgeDirection dir = ForgeDirection.getOrientation(pos.sideHit);
				int x = pos.blockX + dir.offsetX;
				int y = pos.blockY + dir.offsetY;
				int z = pos.blockZ + dir.offsetZ;
				
				if(sat.satIface == Interfaces.SAT_COORD) {
					sat.onCoordAction(world, player, x, y, z);
				} else if(sat.satIface == Interfaces.SAT_PANEL) {
					sat.onClick(world, x, z);
				}
			}
		}
		
		return stack;
	}

}
