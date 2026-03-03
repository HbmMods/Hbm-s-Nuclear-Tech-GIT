package com.hbm.hrist;

import com.hbm.hrist.ConduitPiece.ConnectionDefinition;
import com.hbm.util.fauxpointtwelve.DirPos;

import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockConduitSwitch extends BlockConduitBase {

	@Override public int[] getDimensions() { return new int[] {0, 0, 4, 0, 4, 3}; }
	@Override public int getOffset() { return 0; }

	@Override
	public ConduitPiece getPiece(World world, int x, int y, int z, int meta) {
		ForgeDirection dir = ForgeDirection.getOrientation(meta - 10);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		DirPos d0 = new DirPos(
				x + 0.5 + rot.offsetX * 0.5 + dir.offsetX * 0.5, y,
				z + 0.5 + rot.offsetZ * 0.5 + dir.offsetZ * 0.5, dir);
		DirPos d1 = new DirPos(
				x + 0.5 + rot.offsetX * 4.5 - dir.offsetX * 3.5, y,
				z + 0.5 + rot.offsetZ * 4.5 - dir.offsetZ * 3.5, rot);
		DirPos d2 = new DirPos(
				x + 0.5 + rot.offsetX * 0.5 + dir.offsetX * 0.5, y,
				z + 0.5 + rot.offsetZ * 0.5 + dir.offsetZ * 0.5, dir);
		DirPos d3 = new DirPos(
				x + 0.5 - rot.offsetX * 3.5 - dir.offsetX * 3.5, y,
				z + 0.5 - rot.offsetZ * 3.5 - dir.offsetZ * 3.5, rot.getOpposite());
		return new ConduitPiece(new ConnectionDefinition(d0, d1), new ConnectionDefinition(d2, d3));
	}
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, Object renderBlocks) {
		return false;
	}
}
