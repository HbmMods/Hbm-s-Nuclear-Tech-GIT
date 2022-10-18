package com.hbm.util;

import java.util.ArrayList;

import com.hbm.blocks.BlockDummyable;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.tileentity.machine.TileEntityDummy;

import api.hbm.energy.IEnergyUser;
import api.hbm.fluid.IFluidUser;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * EXTERNAL COMPATIBILITY CLASS - DO NOT CHANGE METHOD NAMES/PARAMS ONCE CREATED
 * Is there a smarter way to do this? Most likely. Is there an easier one? Probably not.
 * @author hbm
 */
public class CompatExternal {

	/**
	 * Gets the tile entity at that pos. If the tile entity is an mk1 or mk2 dummy, it will return the core instead.
	 * This method will be updated in the event that other multiblock systems or dummies are added to retrain the intended functionality.
	 * @return the core tile entity if the given position holds a dummy, the tile entity at that position if it doesn't or null if there is no tile entity
	 */
	public static TileEntity getCoreFromPos(World world, int x, int y, int z) {
		
		Block b = world.getBlock(x, y, z);
		
		//if the block at that pos is a Dummyable, use the mk2's system to find the core
		if(b instanceof BlockDummyable) {
			BlockDummyable dummy = (BlockDummyable) b;
			int[] pos = dummy.findCore(world, x, y, z);
			
			if(pos != null) {
				return world.getTileEntity(pos[0], pos[1], pos[2]);
			}
		}
		
		TileEntity tile = world.getTileEntity(x, y, z);
		
		//if the tile at that pos is an old dummy tile, use mk1
		if(tile instanceof TileEntityDummy) {
			TileEntityDummy dummy = (TileEntityDummy) tile;
			return world.getTileEntity(dummy.targetX, dummy.targetY, dummy.targetZ);
		}
		
		//otherwise, return the tile at that position whihc could be null
		return tile;
	}
	
	/**
	 * Returns the numeric value of the buffered energy held by that tile entity. Current implementation relies on IEnergyUser.
	 * @param tile
	 * @return power
	 */
	public static long getBufferedPowerFromTile(TileEntity tile) {
		
		if(tile instanceof IEnergyUser) {
			return ((IEnergyUser) tile).getPower();
		}
		
		return 0L;
	}
	
	/**
	 * Returns the numeric value of the energy capacity of this tile entity. Current implementation relies on IEnergyUser.
	 * @param tile
	 * @return max power
	 */
	public static long getMaxPowerFromTile(TileEntity tile) {
		
		if(tile instanceof IEnergyUser) {
			return ((IEnergyUser) tile).getMaxPower();
		}
		
		return 0L;
	}
	
	/**
	 * Returns the ordinal of the energy priority from the supplied tile entity. 0 = low, 1 = normal, 2 = high. Returns -1 if not applicable.
	 * @param tile
	 * @return priority
	 */
	public static int getEnergyPriorityFromTile(TileEntity tile) {
		
		if(tile instanceof IEnergyUser) {
			return ((IEnergyUser) tile).getPriority().ordinal();
		}
		
		return -1;
	}
	
	/**
	 * Returns a list of tank definitions from the supplied tile entity. Uses IFluidUser, if the tile is incompatible it returns an empty list.
	 * @param tile
	 * @return an ArrayList of Object arrays with each array representing a fluid tank.<br>
	 * [0]: STRING - unlocalized name of the fluid, simply use I18n to get the translated name<br>
	 * [1]: INT - the unique ID of this fluid<br>
	 * [2]: INT - the hexadecimal color of this fluid<br>
	 * [3]: INT - the amount of fluid in this tank in millibuckets<br>
	 * [4]: INT - the capacity of this tank in millibuckets
	 */
	public static ArrayList<Object[]> getFluidInfoFromTile(TileEntity tile) {
		ArrayList<Object[]> list = new ArrayList();
		
		if(!(tile instanceof IFluidUser)) {
			return list;
		}
		
		IFluidUser container = (IFluidUser) tile;
		
		for(FluidTank tank : container.getAllTanks()) {
			FluidType type = tank.getTankType();
			list.add(new Object[] {
					type.getName(),
					type.getID(),
					type.getColor(),
					tank.getFill(),
					tank.getMaxFill()
			});
		}
		
		return list;
	}
}
