package com.hbm.blocks.network;

import com.hbm.lib.RefStrings;
import com.hbm.tileentity.network.TileEntityCraneExtractor;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class CraneExtractor extends BlockCraneBase {

	public CraneExtractor() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityCraneExtractor();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		super.registerBlockIcons(iconRegister);
		this.iconDirectional = iconRegister.registerIcon(RefStrings.MODID + ":crane_out_top");
		this.iconDirectionalUp = iconRegister.registerIcon(RefStrings.MODID + ":crane_out_side_down");
		this.iconDirectionalDown = iconRegister.registerIcon(RefStrings.MODID + ":crane_out_side_up");
	}

	@Override
	public int getRotationFromSide(IBlockAccess world, int x, int y, int z, int side) {
		int meta = world.getBlockMetadata(x, y, z);
		
		if(meta > 1 && side == 1) {
			if(meta == 2) return 0;
			if(meta == 3) return 3;
			if(meta == 4) return 2;
			if(meta == 5) return 1;
		}
		
		return 0;
	}
	
	@Override
	public boolean canConnectRedstone(IBlockAccess world, int x, int y, int z, int side) {
		return true;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		this.dropContents(world, x, y, z, block, meta, 9, 20);
		super.breakBlock(world, x, y, z, block, meta);
	}
}
