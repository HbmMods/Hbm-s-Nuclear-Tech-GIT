package com.hbm.blocks.network;

import com.hbm.blocks.BlockDummyable;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.network.TileEntityRadioTelex;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class RadioTelex extends BlockDummyable {

	public RadioTelex() {
		super(Material.wood);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityRadioTelex();
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {0, 0, 0, 0, 1, 0};
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
			FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, pos[0], pos[1], pos[2]);
			return true;
		} else {
			return !player.isSneaking();
		}
	}
}
