package com.hbm.blocks.machine;

<<<<<<< HEAD
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockHadronCoil extends Block {
=======
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

public class BlockHadronCoil extends Block implements IBlockCT {
>>>>>>> master
	
	public int factor;

	public BlockHadronCoil(Material mat, int factor) {
		super(mat);
		this.factor = factor;
	}
<<<<<<< HEAD
=======

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
	public IIcon[] getFragments() {
		return rec.fragCache;
	}
	
	@Override
	public boolean canConnect(IBlockAccess world, int x, int y, int z, IBlockCT block) {
		return block instanceof BlockHadronCoil;
	}
>>>>>>> master
}
