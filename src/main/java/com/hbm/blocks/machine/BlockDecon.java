package com.hbm.blocks.machine;

import java.util.Random;

import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityDecon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockDecon extends BlockContainer {

	@SideOnly(Side.CLIENT)
	private IIcon iconTop;
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		
		this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":decon_top");
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":decon_side");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		
		return side == 1 ? this.iconTop : this.blockIcon;
	}

	public BlockDecon(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityDecon();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World p_149734_1_, int x, int y, int z, Random rand) { }

}
