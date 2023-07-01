package com.hbm.blocks.network;

import com.hbm.lib.RefStrings;
import com.hbm.tileentity.network.TileEntityCraneGrabber;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class CraneGrabber extends BlockCraneBase {

	public CraneGrabber() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityCraneGrabber();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		super.registerBlockIcons(iconRegister);
		this.iconDirectional = iconRegister.registerIcon(RefStrings.MODID + ":crane_grabber_top");
		this.iconDirectionalUp = iconRegister.registerIcon(RefStrings.MODID + ":crane_grabber_side_up");
		this.iconDirectionalDown = iconRegister.registerIcon(RefStrings.MODID + ":crane_grabber_side_down");
		this.iconOut = iconRegister.registerIcon(RefStrings.MODID + ":crane_pull");
		this.iconSideOut = iconRegister.registerIcon(RefStrings.MODID + ":crane_side_pull");
	}

	@Override
	public int getRotationFromSide(IBlockAccess world, int x, int y, int z, int side) {
		int meta = world.getBlockMetadata(x, y, z);
		
		if(meta > 1 && side == 1) {
			if(meta == 2) return 3;
			if(meta == 3) return 0;
			if(meta == 4) return 1;
			if(meta == 5) return 2;
		}
		
		return 0;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		this.dropContents(world, x, y, z, block, meta, 9, 11);
		super.breakBlock(world, x, y, z, block, meta);
	}
}
