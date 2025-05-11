package com.hbm.tileentity.bomb;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityCrashedBomb extends TileEntity {
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() { return TileEntity.INFINITE_EXTENT_AABB; }
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() { return 65536.0D; }
}
