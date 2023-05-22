package com.hbm.blocks.generic;

import java.util.List;

import com.hbm.blocks.ITooltipProvider;
import com.hbm.blocks.ModBlocks;
import com.hbm.lib.RefStrings;
import com.hbm.util.I18nUtil;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockGrate extends Block implements ITooltipProvider {

	@SideOnly(Side.CLIENT)
	private IIcon sideIcon;
	
	public BlockGrate(Material material) {
		super(material);
		
		//this.maxY = 0.999D;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + (this == ModBlocks.steel_grate ? ":grate_top" : ":grate_wide_top"));
		this.sideIcon = iconRegister.registerIcon(RefStrings.MODID + ":grate_side");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return side == 1 ? this.blockIcon : (side == 0 ? this.blockIcon : this.sideIcon);
	}

	public static int renderID = RenderingRegistry.getNextAvailableRenderId();

	@Override
	public int getRenderType() {
		return renderID;
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
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		this.setBlockBounds(0F, meta * 0.125F, 0F, 1F, meta * 0.125F + 0.125F - (this == ModBlocks.steel_grate_wide ? 0.001F : 0), 1F);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		this.setBlockBounds(0F, meta * 0.125F, 0F, 1F, meta * 0.125F + 0.125F - (this == ModBlocks.steel_grate_wide ? 0.001F : 0), 1F);
		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
		int meta = world.getBlockMetadata(x, y, z);
		return (side == ForgeDirection.UP && meta == 7) || (side == ForgeDirection.DOWN && meta == 0);
	}

	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float hX, float hY, float hZ, int meta) {

		if(side == 0)
			return 7;
		
		if(side == 1)
			return 0;
		
		return (int)Math.floor(hY * 8D);
	}

	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB entityBounding, List list, Entity entity) {
		if(this != ModBlocks.steel_grate_wide || !(entity instanceof EntityItem || entity instanceof EntityXPOrb)) {
			super.addCollisionBoxesToList(world, x, y, z, entityBounding, list, entity);
			return;
		}
		
		int meta = world.getBlockMetadata(x, y, z);
		
		if((entity instanceof EntityItem || entity instanceof EntityXPOrb) && entity.posY < y + meta * 0.125D + 0.375) {
			entity.motionX = 0;
			entity.motionY = -0.25;
			entity.motionZ = 0;
			
			entity.setPosition(entity.posX, entity.posY - 0.125, entity.posZ);
		}
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		list.add(I18nUtil.resolveKey(this.getUnlocalizedName() + ".desc"));
	}
}
