package com.hbm.blocks.machine.pile;

import com.hbm.blocks.generic.BlockFlammable;
import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class BlockGraphiteDrilled extends BlockFlammable {

	@SideOnly(Side.CLIENT)
	private IIcon holeIcon;

	public BlockGraphiteDrilled(Material mat, int en, int flam) {
		super(mat, en, flam);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		super.registerBlockIcons(iconRegister);
		this.holeIcon = iconRegister.registerIcon(RefStrings.MODID + ":block_graphite_drilled");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		
		int cfg = metadata & 4;
		
		if(side == cfg * 2 || side == cfg * 2 + 1)
			return this.holeIcon;
		
		return this.blockIcon;
	}
}
