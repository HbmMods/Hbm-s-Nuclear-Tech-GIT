package com.hbm.items.tool;

import com.hbm.tileentity.IRadarCommandReceiver;
import com.hbm.tileentity.machine.TileEntityMachineRadarScreen;
import com.hbm.util.CompatExternal;
import com.hbm.util.fauxpointtwelve.BlockPos;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ItemRadarLinker extends ItemCoordinateBase {

	@Override
	public boolean canGrabCoordinateHere(World world, int x, int y, int z) {
		TileEntity tile = CompatExternal.getCoreFromPos(world, x, y, z);
		return tile instanceof IRadarCommandReceiver || tile instanceof TileEntityMachineRadarScreen;
	}
	
	@Override
	public BlockPos getCoordinates(World world, int x, int y, int z) {
		TileEntity tile = CompatExternal.getCoreFromPos(world, x, y, z);
		return new BlockPos(tile.xCoord, tile.yCoord, tile.zCoord);
	}
	
	@Override
	public void onTargetSet(World world, int x, int y, int z, EntityPlayer player) {
		world.playSoundAtEntity(player, "hbm:item.techBleep", 1.0F, 1.0F);
	}
}
