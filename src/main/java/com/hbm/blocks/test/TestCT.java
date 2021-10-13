package com.hbm.blocks.test;

import com.hbm.render.block.ct.CT;
import com.hbm.render.block.ct.CTStitchReceiver;
import com.hbm.render.block.ct.IBlockCT;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class TestCT extends Block implements IBlockCT {

	public TestCT(Material p_i45394_1_) {
		super(p_i45394_1_);
	}

	@Override
	public int getRenderType() {
		return CT.renderID;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return true;
	}

	@Override
	public boolean isOpaqueCube() {
		return true;
	}

	@SideOnly(Side.CLIENT)
	public CTStitchReceiver rec;

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		this.blockIcon = reg.registerIcon(this.getTextureName());
		this.rec = IBlockCT.primeReceiver(reg, this.getTextureName(), this.blockIcon);
	}

	@Override
	public IIcon[] getFragments() {
		return rec.fragCache;
	}
}
