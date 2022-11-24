package com.hbm.blocks.network;

import com.hbm.lib.RefStrings;
import com.hbm.tileentity.network.TileEntityRadioTorchReceiver;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class RadioTorchReceiver extends RadioTorchBase {
	
	@SideOnly(Side.CLIENT)
	protected IIcon iconOn;

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
		return new TileEntityRadioTorchReceiver();
	}
}
