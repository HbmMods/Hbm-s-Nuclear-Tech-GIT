package com.hbm.blocks.machine;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.RefStrings;
import com.hbm.render.block.ct.CT;
import com.hbm.render.block.ct.CTStitchReceiver;
import com.hbm.render.block.ct.IBlockCT;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockICF extends BlockContainer implements IBlockCT {
	
	@SideOnly(Side.CLIENT) protected IIcon iconPort;
	
	public BlockICF(Material mat) {
		super(mat);
	}

	@Override
	public int getRenderType() {
		return CT.renderID;
	}
	
	@Override
	public Item getItemDropped(int i, Random rand, int j)  {
		return null;
	}

	@SideOnly(Side.CLIENT) public CTStitchReceiver rec;
	@SideOnly(Side.CLIENT) public CTStitchReceiver recPort;

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		super.registerBlockIcons(reg);
		this.iconPort = reg.registerIcon(RefStrings.MODID + ":icf_casing_port");
		this.rec = IBlockCT.primeReceiver(reg, this.blockIcon.getIconName(), this.blockIcon);
		this.recPort = IBlockCT.primeReceiver(reg, this.iconPort.getIconName(), this.iconPort);
	}

	@Override
	public IIcon[] getFragments(IBlockAccess world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		if(meta == 1) return recPort.fragCache;
		return rec.fragCache;
	}

	@Override
	public boolean canConnect(IBlockAccess world, int x, int y, int z, Block block) {
		return block == ModBlocks.icf_block || block == ModBlocks.icf_controller;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityBlockICF();
	}
	
	public static class TileEntityBlockICF extends TileEntity {
		
	}
}
