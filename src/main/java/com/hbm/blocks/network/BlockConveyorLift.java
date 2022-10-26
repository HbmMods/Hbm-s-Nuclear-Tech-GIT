package com.hbm.blocks.network;

import api.hbm.conveyor.IConveyorBelt;
import api.hbm.conveyor.IEnterableBlock;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockConveyorLift extends BlockConveyorBase {
	
	@Override
	public ForgeDirection getTravelDirection(World world, int x, int y, int z, Vec3 itemPos) {

		boolean bottom = !(world.getBlock(x, y - 1, z) instanceof IConveyorBelt);
		boolean top = !(world.getBlock(x, y + 1, z) instanceof IConveyorBelt) && !bottom && !(world.getBlock(x, y + 1, z) instanceof IEnterableBlock);
		
		if(!top) {
			return ForgeDirection.DOWN;
		}
		
		return ForgeDirection.getOrientation(world.getBlockMetadata(x, y, z));
	}

	@Override
	public Vec3 getClosestSnappingPosition(World world, int x, int y, int z, Vec3 itemPos) {

		boolean bottom = !(world.getBlock(x, y - 1, z) instanceof IConveyorBelt);
		boolean top = !(world.getBlock(x, y + 1, z) instanceof IConveyorBelt) && !bottom && !(world.getBlock(x, y + 1, z) instanceof IEnterableBlock);

		if(!top) {
			return Vec3.createVectorHelper(x + 0.5, itemPos.yCoord, z + 0.5);
		} else {
			return super.getClosestSnappingPosition(world, x, y, z, itemPos);
		}
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {

		boolean bottom = !(world.getBlock(x, y - 1, z) instanceof IConveyorBelt);
		boolean top = !(world.getBlock(x, y + 1, z) instanceof IConveyorBelt) && !bottom && !(world.getBlock(x, y + 1, z) instanceof IEnterableBlock);
		
		if(top)
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
		else
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {

		boolean bottom = !(world.getBlock(x, y - 1, z) instanceof IConveyorBelt);
		boolean top = !(world.getBlock(x, y + 1, z) instanceof IConveyorBelt) && !bottom && !(world.getBlock(x, y + 1, z) instanceof IEnterableBlock);

		if(top)
			return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 0.5, z + 1);
		else
			return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1);
	}

	public static int renderID = RenderingRegistry.getNextAvailableRenderId();

	@Override
	public int getRenderType() {
		return renderID;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
		return true;
	}
}
