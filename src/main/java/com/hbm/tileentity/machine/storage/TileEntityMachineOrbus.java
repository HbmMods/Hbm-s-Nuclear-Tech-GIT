package com.hbm.tileentity.machine.storage;

import com.hbm.blocks.BlockDummyable;
import com.hbm.util.fauxpointtwelve.DirPos;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineOrbus extends TileEntityBarrel {

	public TileEntityMachineOrbus() {
		super(512000);
	}
	
	@Override
	public String getName() {
		return "container.orbus";
	}
	
	@Override
	public void checkFluidInteraction() { } //NO!

	protected DirPos[] conPos;
	
	@Override
	protected DirPos[] getConPos() {
		
		if(conPos != null)
			return conPos;
		
		conPos = new DirPos[8];
		
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset).getOpposite();
		ForgeDirection rot = dir.getRotation(ForgeDirection.DOWN);

		for(int i = -1; i < 6; i += 6) {
			ForgeDirection out = i == -1 ? ForgeDirection.DOWN : ForgeDirection.UP;
			int index = i == -1 ? 0 : 4;
			conPos[index + 0] = new DirPos(xCoord,								yCoord + i,	zCoord,								out);
			conPos[index + 1] = new DirPos(xCoord + dir.offsetX,				yCoord + i,	zCoord + dir.offsetZ,				out);
			conPos[index + 2] = new DirPos(xCoord + rot.offsetX,				yCoord + i,	zCoord + rot.offsetZ,				out);
			conPos[index + 3] = new DirPos(xCoord + dir.offsetX + rot.offsetX,	yCoord + i,	zCoord + dir.offsetZ + rot.offsetZ,	out);
		}
		
		return conPos;
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 2,
					yCoord,
					zCoord - 2,
					xCoord + 2,
					yCoord + 5,
					zCoord + 2
					);
		}
		
		return bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
}
