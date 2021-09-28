package com.hbm.blocks.test;

import com.hbm.render.block.ct.IconCT;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;

public class TestCT extends Block {

	public TestCT(Material p_i45394_1_) {
		super(p_i45394_1_);
	}
	
	public static int renderID = RenderingRegistry.getNextAvailableRenderId();

	@Override
	public int getRenderType() {
		return renderID;
	}
	
	public IIcon[] frags = new IIcon[20];

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		this.blockIcon = reg.registerIcon(this.getTextureName());
		
		IIcon ct = new SevenUp(this.getTextureName() + "_ct");
		reg.registerIcon(this.getTextureName() + "_ct");
		
		for(int i = 0; i < frags.length; i++) {
			frags[i] = new IconCT(i < 4 ? this.blockIcon : ct, i);
		}
	}

	@SideOnly(Side.CLIENT)
	public static class SevenUp extends TextureAtlasSprite {

		protected SevenUp(String tex) {
			super(tex);
		}
	}
}
