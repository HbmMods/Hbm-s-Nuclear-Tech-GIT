package com.hbm.blocks.test;

import com.hbm.render.block.ct.CT;
import com.hbm.render.block.ct.CTStitchReceiver;
import com.hbm.render.block.ct.IBlockCT;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

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

	@SideOnly(Side.CLIENT) public CTStitchReceiver rec;
	@SideOnly(Side.CLIENT) protected IIcon secondIcon;
	@SideOnly(Side.CLIENT) public CTStitchReceiver secondrec;

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		this.blockIcon = reg.registerIcon(this.getTextureName());
		this.secondIcon = reg.registerIcon(this.getTextureName() + ".1");
		this.rec = IBlockCT.primeReceiver(reg, this.getTextureName(), this.blockIcon);
		this.secondrec = IBlockCT.primeReceiver(reg, this.getTextureName() + ".1", this.secondIcon);
	}

	@Override
	public IIcon[] getFragments(IBlockAccess world, int x, int y, int z) {
		if(world.getBlockMetadata(x, y, z) != 0)
			return secondrec.fragCache;
		return rec.fragCache;
	}
}
