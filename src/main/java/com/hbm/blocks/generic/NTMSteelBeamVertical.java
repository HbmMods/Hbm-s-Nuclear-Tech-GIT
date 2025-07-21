package com.hbm.blocks.generic;

import com.hbm.lib.RefStrings;
import com.hbm.tileentity.deco.TileEntityNTMSteelBeamVertical;

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

public class NTMSteelBeamVertical extends BlockContainer {

	public NTMSteelBeamVertical(Material material) {
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
		this.blockIcon = iconregister.registerIcon(RefStrings.MODID + ":steel_beam_vertical");
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		// Vertical beam doesn't need rotation, but we can store orientation if needed
		// For now, just set a default metadata
		world.setBlockMetadataWithNotify(x, y, z, 0, 2);
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		// Use full 16x16 block bounds
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityNTMSteelBeamVertical();
	}
}
