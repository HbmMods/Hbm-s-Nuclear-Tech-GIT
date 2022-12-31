package com.hbm.blocks.network;

import api.hbm.conveyor.IConveyorBelt;
import api.hbm.conveyor.IEnterableBlock;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockConveyorChute extends BlockConveyorBase {

	@Override
	public Vec3 getTravelLocation(World world, int x, int y, int z, Vec3 itemPos, double speed) {
		
		Block below = world.getBlock(x, y - 1, z);
		if(below instanceof IConveyorBelt || below instanceof IEnterableBlock) {
			speed *= 5;
		} else if(itemPos.yCoord > y + 0.25) {
			speed *= 3;
		}
		
		return super.getTravelLocation(world, x, y, z, itemPos, speed);
	}
	
	@Override
	public ForgeDirection getTravelDirection(World world, int x, int y, int z, Vec3 itemPos) {

		Block below = world.getBlock(x, y - 1, z);
		if(below instanceof IConveyorBelt || below instanceof IEnterableBlock || itemPos.yCoord > y + 0.25) {
			return ForgeDirection.UP;
		}
		
		return ForgeDirection.getOrientation(world.getBlockMetadata(x, y, z));
	}

	@Override
	public Vec3 getClosestSnappingPosition(World world, int x, int y, int z, Vec3 itemPos) {
		
		Block below = world.getBlock(x, y - 1, z);
		if(below instanceof IConveyorBelt || below instanceof IEnterableBlock || itemPos.yCoord > y + 0.25) {
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

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) { }

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
		return true;
	}
}
