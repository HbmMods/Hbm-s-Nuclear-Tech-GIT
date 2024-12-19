package com.hbm.blocks.network;

import com.hbm.lib.RefStrings;
import com.hbm.tileentity.network.TileEntityCableSwitch;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class CableDetector extends BlockContainer {

	@SideOnly(Side.CLIENT)
	private IIcon iconOn;

	public CableDetector(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.iconOn = iconRegister.registerIcon(RefStrings.MODID + ":cable_detector_on");
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":cable_detector_off");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return metadata == 1 ? iconOn : blockIcon;
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityCableSwitch();
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {

		boolean on = world.isBlockIndirectlyGettingPowered(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);

		boolean update = false;

		if(on && meta == 0) {
			world.setBlockMetadataWithNotify(x, y, z, 1, 2);
			world.playSoundEffect(x, y, z, "hbm:block.reactorStart", 1.0F, 1.0F);
			update = true;
		}

		if(!on && meta == 1) {
			world.setBlockMetadataWithNotify(x, y, z, 0, 2);
			world.playSoundEffect(x, y, z, "hbm:block.reactorStart", 1.0F, 0.85F);
			update = true;
		}

		if(update) {
			TileEntityCableSwitch te = (TileEntityCableSwitch) world.getTileEntity(x, y, z);
			te.updateState();
		}
	}
}
