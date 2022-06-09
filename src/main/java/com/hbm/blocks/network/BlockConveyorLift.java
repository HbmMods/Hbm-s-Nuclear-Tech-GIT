package com.hbm.blocks.network;

import api.hbm.conveyor.IConveyorBelt;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockConveyorLift extends BlockConveyor {
	
	@Override
	public ForgeDirection getTravelDirection(World world, int x, int y, int z, Vec3 itemPos, double speed) {
		
		if(Math.abs(itemPos.xCoord - (x + 0.5)) < 0.05 && Math.abs(itemPos.zCoord - (z + 0.5)) < 0.05) {
			return ForgeDirection.DOWN;
		}
		
		return ForgeDirection.getOrientation(world.getBlockMetadata(x, y, z));
	}

	@Override
	public Vec3 getClosestSnappingPosition(World world, int x, int y, int z, Vec3 itemPos) {
		
		if(world.getBlock(x, y - 1, z) instanceof IConveyorBelt || itemPos.yCoord > y + 0.25) {
			return Vec3.createVectorHelper(x + 0.5, itemPos.yCoord, z + 0.5);
		} else {
			return super.getClosestSnappingPosition(world, x, y, z, itemPos);
		}
	}

	public static int renderID = RenderingRegistry.getNextAvailableRenderId();

	@Override
	public int getRenderType() {
		return renderID;
	}
}
