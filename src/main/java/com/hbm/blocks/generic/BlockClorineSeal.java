package com.hbm.blocks.generic;

import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityChlorineSeal;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockClorineSeal extends BlockContainer {

	@SideOnly(Side.CLIENT)
	private IIcon iconTop;

	public BlockClorineSeal(Material p_i45386_1_) {
		super(p_i45386_1_);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		
		this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":vent_chlorine_seal_top");
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":vent_chlorine_seal_side");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		
		return side == 1 ? this.iconTop : this.blockIcon;
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityChlorineSeal();
	}

}
