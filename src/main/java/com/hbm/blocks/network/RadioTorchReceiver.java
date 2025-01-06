package com.hbm.blocks.network;

import com.hbm.lib.RefStrings;
import com.hbm.tileentity.network.TileEntityRadioTorchReceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class RadioTorchReceiver extends RadioTorchRWBase {

	public RadioTorchReceiver() {
		super();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":rtty_rec_off");
		this.iconOn = iconRegister.registerIcon(RefStrings.MODID + ":rtty_rec_on");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		TileEntityRadioTorchReceiver tile = new TileEntityRadioTorchReceiver();
		tile.lastUpdate = world.getTotalWorldTime();
		return tile;
	}

	@Override
	public boolean canProvidePower() {
		return true;
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int side) {

		TileEntity tile = world.getTileEntity(x, y, z);

		if(tile instanceof TileEntityRadioTorchReceiver) {
			int state = ((TileEntityRadioTorchReceiver) tile).lastState;
			return state;
		}

		return 0;
	}
}
