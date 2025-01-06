package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityMachineRadarNT;
import com.hbm.tileentity.machine.TileEntityMachineRadarScreen;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MachineRadarScreen extends BlockDummyable {

	public MachineRadarScreen(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return meta >= 12 ? new TileEntityMachineRadarScreen() : null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {1, 0, 0, 0, 1, 0};
	}

	@Override
	public int getOffset() {
		return 0;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {

		if(world.isRemote && !player.isSneaking()) {
			int[] pos = this.findCore(world, x, y, z);

			if(pos == null) return false;

			TileEntityMachineRadarScreen screen = (TileEntityMachineRadarScreen) world.getTileEntity(pos[0], pos[1], pos[2]);

			if(screen.linked && world.getTileEntity(screen.refX, screen.refY, screen.refZ) instanceof TileEntityMachineRadarNT) {
				FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, screen.refX, screen.refY, screen.refZ);
			}

			return false;
		} else if(!player.isSneaking()) {
			return true;
		} else {
			return false;
		}
	}
}
