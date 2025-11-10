package com.hbm.tileentity.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.fusion.MachineFusionTorus;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityFusionTorusStruct extends TileEntity {

	@Override
	public void updateEntity() {
		
		if(worldObj.isRemote) return;
		if(worldObj.getTotalWorldTime() % 20 != 0) return;
		
		for(int y = 0; y < 5; y++) {
			for(int x = 0; x < MachineFusionTorus.layout[0].length; x++) {
				for(int z = 0; z < MachineFusionTorus.layout[0][0].length; z++) {
					
					int ly = y > 2 ? 4 - y : y;
					int i = MachineFusionTorus.layout[ly][x][z];
					
					if(i == 0) continue; // ignore air
					if(x == 7 && y == 0 && z == 7) continue; // ignore core component position
					if(!cbr(ModBlocks.fusion_component, i, x - 7, y, z - 7)) return;
				}
			}
		}
		
		MachineFusionTorus block = (MachineFusionTorus) ModBlocks.fusion_torus;
		BlockDummyable.safeRem = true;
		worldObj.setBlock(xCoord, yCoord, zCoord, ModBlocks.fusion_torus, 12, 3);
		block.fillSpace(worldObj, xCoord, yCoord, zCoord, ForgeDirection.NORTH, 0);
		BlockDummyable.safeRem = false;
	}
	
	/** [G]et [B]lock at [R]elative position */
	private Block gbr(int x, int y, int z) {
		return worldObj.getBlock(xCoord + x, yCoord + y, zCoord + z);
	}
	
	/** [G]et [M]eta at [R]elative position */
	private int gmr(int x, int y, int z) {
		return worldObj.getBlockMetadata(xCoord + x, yCoord + y, zCoord + z);
	}
	
	/** [C]heck [B]lock at [R]elative position */
	private boolean cbr(Block b, int meta, int x, int y, int z) {
		return b == gbr(x, y, z) && meta == gmr(x, y, z);
	}

	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 7,
					yCoord,
					zCoord - 7,
					xCoord + 8,
					yCoord + 5,
					zCoord + 8
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
