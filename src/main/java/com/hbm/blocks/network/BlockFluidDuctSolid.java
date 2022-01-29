package com.hbm.blocks.network;

import com.hbm.blocks.IBlockMultiPass;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.render.block.RenderBlockMultipass;
import com.hbm.tileentity.conductor.TileEntityFluidDuctSimple;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFluidDuctSolid extends BlockContainer implements IBlockMultiPass {

	public BlockFluidDuctSolid(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityFluidDuctSimple();
	}

	@SideOnly(Side.CLIENT)
	private IIcon overlay;

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister p_149651_1_) {
		this.blockIcon = p_149651_1_.registerIcon(this.getTextureName());
		this.overlay = p_149651_1_.registerIcon(this.getTextureName() + "_overlay");
	}

	@Override
	public int getPasses() {
		return 2;
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return RenderBlockMultipass.currentPass == 0 ? this.blockIcon : this.overlay;
	}
	
	@Override
	public int getRenderType(){
		return IBlockMultiPass.getRenderType();
	}

	@Override
	public int getColorFromPass(IBlockAccess world, int x, int y, int z, boolean inv) {
		
		if(RenderBlockMultipass.currentPass == 0)
			return 0xffffff;
		
		if(inv)
			return Fluids.NONE.getColor();
		
		TileEntityFluidDuctSimple te = (TileEntityFluidDuctSimple) world.getTileEntity(x, y, z);
		
		if(te != null) {
			return te.getType().getColor();
		}
		
		return 0xffffff;
	}
}
