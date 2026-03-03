package com.hbm.hrist;

import com.hbm.hrist.ConduitPiece.ConnectionDefinition;
import com.hbm.util.Vec3NT;
import com.hbm.util.fauxpointtwelve.DirPos;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
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
		int meta = world.getBlockMetadata(x, y, z);
		if(meta >= 12) {
			double angle = 0;
			ForgeDirection dir = ForgeDirection.getOrientation(meta - 10);
			ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
			if(dir == ForgeDirection.NORTH) angle += 0;
			if(dir == ForgeDirection.WEST) angle += 90;
			if(dir == ForgeDirection.SOUTH) angle += 180;
			if(dir == ForgeDirection.EAST) angle += 270;
			
			double diff = 90D / 5D;

			Vec3NT sv0 = new Vec3NT(dir.offsetX, 0, dir.offsetZ).rotateAroundYDeg(90);
			Vec3NT sv1 = new Vec3NT(dir.offsetX, 0, dir.offsetZ).rotateAroundYDeg(90 + diff);
			Vec3NT sv2 = new Vec3NT(dir.offsetX, 0, dir.offsetZ).rotateAroundYDeg(-90);
			Vec3NT sv3 = new Vec3NT(dir.offsetX, 0, dir.offsetZ).rotateAroundYDeg(-90 - diff);
			Vec3NT supVec0 = new Vec3NT(3.9375 * dir.offsetX, 0, 3.9375 * dir.offsetZ).rotateAroundYDeg(90 + diff / 2);
			Vec3NT supVec1 = new Vec3NT(3.9375 * dir.offsetX, 0, 3.9375 * dir.offsetZ).rotateAroundYDeg(-90 - diff / 2);
			double outer = 4.75D + 0.03125D;
			double inner = 3.25D - 0.03125D;
			double dx0 = x + 0.5 + dir.offsetX * 0.5 + rot.offsetX * 4.5;
			double dz0 = z + 0.5 + dir.offsetZ * 0.5 + rot.offsetZ * 4.5;
			double dx1 = x + 0.5 + dir.offsetX * 0.5 - rot.offsetX * 3.5;
			double dz1 = z + 0.5 + dir.offsetZ * 0.5 - rot.offsetZ * 3.5;

			ConduitRenderUtil.renderSupport(Tessellator.instance, blockIcon, dx0 + supVec0.xCoord, y, dz0 + supVec0.zCoord, angle);
			
			for(int i = 0; i < 5; i++) {
				if(i > 1) {
					ConduitRenderUtil.renderSupport(Tessellator.instance, blockIcon, dx0 + supVec0.xCoord, y, dz0 + supVec0.zCoord, angle + diff / 2);
					ConduitRenderUtil.renderSupport(Tessellator.instance, blockIcon, dx1 + supVec1.xCoord, y, dz1 + supVec1.zCoord, -angle - diff / 2);
				}
				ConduitRenderUtil.renderSteel(Tessellator.instance, blockIcon, dx0 + sv1.xCoord * outer, y, dz0 + sv1.zCoord * outer, angle + diff, dx0 + sv0.xCoord * outer, y, dz0 + sv0.zCoord * outer, angle);
				ConduitRenderUtil.renderSteel(Tessellator.instance, blockIcon, dx0 + sv1.xCoord * inner, y, dz0 + sv1.zCoord * inner, angle + diff, dx0 + sv0.xCoord * inner, y, dz0 + sv0.zCoord * inner, angle);
				ConduitRenderUtil.renderSteel(Tessellator.instance, blockIcon, dx1 + sv3.xCoord * outer, y, dz1 + sv3.zCoord * outer, -angle - diff, dx1 + sv2.xCoord * outer, y, dz1 + sv2.zCoord * outer, -angle);
				ConduitRenderUtil.renderSteel(Tessellator.instance, blockIcon, dx1 + sv3.xCoord * inner, y, dz1 + sv3.zCoord * inner, -angle - diff, dx1 + sv2.xCoord * inner, y, dz1 + sv2.zCoord * inner, -angle);
				supVec0.rotateAroundYDeg(diff);
				supVec1.rotateAroundYDeg(-diff);
				sv0.rotateAroundYDeg(diff);
				sv1.rotateAroundYDeg(diff);
				sv2.rotateAroundYDeg(-diff);
				sv3.rotateAroundYDeg(-diff);
				angle += diff;
			}
		}
		return false;
	}
}
