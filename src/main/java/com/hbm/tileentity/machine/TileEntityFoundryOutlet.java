package com.hbm.tileentity.machine;

import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.util.CrucibleUtil;

import api.hbm.block.ICrucibleAcceptor;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityFoundryOutlet extends TileEntityFoundryBase {

	@Override public boolean canAcceptPartialPour(World world, int x, int y, int z, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack) { return false; }
	@Override public MaterialStack pour(World world, int x, int y, int z, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack) { return stack; }
	
	@Override
	public boolean canAcceptPartialFlow(World world, int x, int y, int z, ForgeDirection side, MaterialStack stack) {
		
		Vec3 start = Vec3.createVectorHelper(x + 0.5, y - 0.125, z + 0.5);
		Vec3 end = Vec3.createVectorHelper(x + 0.5, y + 0.125 - 4, z + 0.5);
		
		MovingObjectPosition[] mop = new MovingObjectPosition[1];
		ICrucibleAcceptor acc = CrucibleUtil.getPouringTarget(world, start, end, mop);
		
		if(acc == null) {
			return false;
		}
		
		return acc.canAcceptPartialPour(world, mop[0].blockX, mop[0].blockY, mop[0].blockZ, mop[0].hitVec.xCoord, mop[0].hitVec.yCoord, mop[0].hitVec.zCoord, ForgeDirection.UP, stack);
	}
	
	@Override
	public MaterialStack flow(World world, int x, int y, int z, ForgeDirection side, MaterialStack stack) {
		
		Vec3 start = Vec3.createVectorHelper(x + 0.5, y - 0.125, z + 0.5);
		Vec3 end = Vec3.createVectorHelper(x + 0.5, y + 0.125 - 4, z + 0.5);
		
		MovingObjectPosition[] mop = new MovingObjectPosition[1];
		ICrucibleAcceptor acc = CrucibleUtil.getPouringTarget(world, start, end, mop);
		
		if(acc == null)
			return stack;
		
		return acc.pour(world, mop[0].blockX, mop[0].blockY, mop[0].blockZ, mop[0].hitVec.xCoord, mop[0].hitVec.yCoord, mop[0].hitVec.zCoord, ForgeDirection.UP, stack);
	}

	@Override
	public int getCapacity() {
		return 0;
	}
}
