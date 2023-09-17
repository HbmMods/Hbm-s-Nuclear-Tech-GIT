package com.hbm.blocks.network;

import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class DroneCrate extends BlockContainer {

	@SideOnly(Side.CLIENT) private IIcon iconTop;
	@SideOnly(Side.CLIENT) private IIcon iconBottom;

	public DroneCrate() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		this.blockIcon = reg.registerIcon(RefStrings.MODID + ":drone_crate_side");
		this.iconTop = reg.registerIcon(RefStrings.MODID + ":drone_crate_top");
		this.iconBottom = reg.registerIcon(RefStrings.MODID + ":drone_crate_bottom");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return side == 1 ? this.iconTop : (side == 0 ? this.iconBottom : this.blockIcon);
	}
}
