package com.hbm.blocks.network;

import api.hbm.conveyor.IConveyorBelt;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockConveyorChute extends BlockConveyor {

	@Override
	public Vec3 getTravelLocation(World world, int x, int y, int z, Vec3 itemPos, double speed) {
		
		if(world.getBlock(x, y - 1, z) instanceof IConveyorBelt) {
			speed *= 5;
		} else if(itemPos.yCoord > y + 0.25) {
			speed *= 3;
		}
		
		return super.getTravelLocation(world, x, y, z, itemPos, speed);
	}
	
	@Override
	public ForgeDirection getTravelDirection(World world, int x, int y, int z, Vec3 itemPos, double speed) {
		
		if(world.getBlock(x, y - 1, z) instanceof IConveyorBelt || itemPos.yCoord > y + 0.25) {
			return ForgeDirection.UP;
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
