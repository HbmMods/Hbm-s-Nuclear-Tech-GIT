package com.hbm.blocks.test;

import com.hbm.render.block.ct.IBlockCT;
import com.hbm.render.block.ct.IconCT;
import com.hbm.render.block.ct.IconGeneric;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;

public class TestCT extends Block implements IBlockCT {

	public TestCT(Material p_i45394_1_) {
		super(p_i45394_1_);
	}
	
	public static int renderID = RenderingRegistry.getNextAvailableRenderId();

	@Override
	public int getRenderType() {
		return renderID;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return true;
	}

	@Override
	public boolean isOpaqueCube() {
		return true;
	}
	
	public IIcon[] frags = new IIcon[20];

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		this.blockIcon = reg.registerIcon(this.getTextureName());
		this.frags = IBlockCT.registerIcons(reg, this.getTextureName(), this.blockIcon);
	}

	@Override
	public IIcon[] getFragments() {
		return frags;
	}
}
