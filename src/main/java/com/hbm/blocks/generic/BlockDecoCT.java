package com.hbm.blocks.generic;

import com.hbm.render.block.ct.CT;
import com.hbm.render.block.ct.CTStitchReceiver;
import com.hbm.render.block.ct.IBlockCT;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class BlockDecoCT extends BlockOre implements IBlockCT{

	public BlockDecoCT(Material mat) {
		super(mat);
	}

	@Override
	public int getRenderType() {
		return CT.renderID;
	}

	@SideOnly(Side.CLIENT)
	public CTStitchReceiver rec;

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		this.blockIcon = reg.registerIcon(this.getTextureName());
		this.rec = IBlockCT.primeReceiver(reg, this.getTextureName(), this.blockIcon);
	}

	@Override
	public IIcon[] getFragments(IBlockAccess world, int x, int y, int z) {
		return rec.fragCache;
	}
}
