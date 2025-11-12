package com.hbm.blocks.generic;

import com.hbm.lib.RefStrings;
import com.hbm.tileentity.deco.TileEntityNTMSteelBeam;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class NTMSteelBeam extends BlockContainer {

	public NTMSteelBeam(Material material) {
		super(material);
	}
	
	@Override
	public int getRenderType() {
		return -1; // Use TESR (TileEntitySpecialRenderer)
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconregister) {
		this.blockIcon = iconregister.registerIcon(RefStrings.MODID + ":steel_beam");
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		
		if(i == 0) {
			world.setBlockMetadataWithNotify(x, y, z, 2, 2); // North
		}
		if(i == 1) {
			world.setBlockMetadataWithNotify(x, y, z, 5, 2); // East
		}
		if(i == 2) {
			world.setBlockMetadataWithNotify(x, y, z, 3, 2); // South
		}
		if(i == 3) {
			world.setBlockMetadataWithNotify(x, y, z, 4, 2); // West
		}
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		// Use full 16x16 block bounds
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityNTMSteelBeam();
	}
}
