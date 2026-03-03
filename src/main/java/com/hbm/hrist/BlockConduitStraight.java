package com.hbm.hrist;

import com.hbm.hrist.ConduitPiece.ConnectionDefinition;
import com.hbm.util.fauxpointtwelve.DirPos;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockConduitStraight extends BlockConduitBase {
	
	@Override public int[] getDimensions() { return new int[] {0, 0, 2, 2, 1, 0}; }
	@Override public int getOffset() { return 2; }
	
	@Override
	public ConduitPiece getPiece(World world, int x, int y, int z, int meta) {
		ForgeDirection dir = ForgeDirection.getOrientation(meta - 10);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		DirPos d0 = new DirPos(
				x + 0.5 + rot.offsetX * 0.5 + dir.offsetX * 2.5, y,
				z + 0.5 + rot.offsetZ * 0.5 + dir.offsetZ * 2.5, dir);
		DirPos d1 = new DirPos(
				x + 0.5 + rot.offsetX * 0.5 - dir.offsetX * 2.5, y,
				z + 0.5 + rot.offsetZ * 0.5 - dir.offsetZ * 2.5, dir.getOpposite());
		return new ConduitPiece(new ConnectionDefinition(d0, d1));
	} 
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, Object renderBlocks) {
		int meta = world.getBlockMetadata(x, y, z);
		if(meta >= 12) {
			double angle = 0;
			ForgeDirection dir = ForgeDirection.getOrientation(meta - 10);
			ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
			if(dir == ForgeDirection.NORTH) angle += 0;
			if(dir == ForgeDirection.WEST) angle += 90;
			if(dir == ForgeDirection.SOUTH) angle += 180;
			if(dir == ForgeDirection.EAST) angle += 270;
			
			for(int i = -2; i <= 2; i++)
				ConduitRenderUtil.renderSupport(Tessellator.instance, blockIcon, x + 0.5 + dir.offsetX * i + rot.offsetX * 0.5, y, z + 0.5 + dir.offsetZ * i + rot.offsetZ * 0.5, angle);

			ConduitRenderUtil.renderSteel(Tessellator.instance, blockIcon,
					x + 0.5 - dir.offsetX * 2.5 - rot.offsetX * 0.28125, y, z + 0.5 - dir.offsetZ * 2.5 - rot.offsetZ * 0.28125, angle,
					x + 0.5 + dir.offsetX * 2.5 - rot.offsetX * 0.28125, y, z + 0.5 + dir.offsetZ * 2.5 - rot.offsetZ * 0.28125, angle);
			ConduitRenderUtil.renderSteel(Tessellator.instance, blockIcon,
					x + 0.5 - dir.offsetX * 2.5 - rot.offsetX * -1.28125, y, z + 0.5 - dir.offsetZ * 2.5 - rot.offsetZ * -1.28125, angle,
					x + 0.5 + dir.offsetX * 2.5 - rot.offsetX * -1.28125, y, z + 0.5 + dir.offsetZ * 2.5 - rot.offsetZ * -1.28125, angle);
		}
		return false;
	}
}
