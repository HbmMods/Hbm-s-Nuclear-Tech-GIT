package com.hbm.blocks.network;

import api.hbm.conveyor.IConveyorBelt;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockConveyorChute extends BlockConveyor {
	
	@Override
	public ForgeDirection getTravelDirection(World world, int x, int y, int z, Vec3 itemPos, double speed) {
		
		if(world.getBlock(x, y - 1, z) instanceof IConveyorBelt && itemPos.yCoord - speed < y) {
			return ForgeDirection.DOWN;
		}
		
		return ForgeDirection.getOrientation(world.getBlockMetadata(x, y, z));
	}

	@Override
	public Vec3 getClosestSnappingPosition(World world, int x, int y, int z, Vec3 itemPos) {
		
		if(world.getBlock(x, y - 1, z) instanceof IConveyorBelt && itemPos.yCoord <= y + 0.25) {
			return super.getClosestSnappingPosition(world, x, y, z, itemPos);
		} else {
			return Vec3.createVectorHelper(x + 0.5, y, z + 0.5);
		}
	}
}
