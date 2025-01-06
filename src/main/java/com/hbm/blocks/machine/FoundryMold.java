package com.hbm.blocks.machine;

import java.util.List;

import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityFoundryMold;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class FoundryMold extends FoundryCastingBase {

	@SideOnly(Side.CLIENT) public IIcon iconTop;
	@SideOnly(Side.CLIENT) public IIcon iconSide;
	@SideOnly(Side.CLIENT) public IIcon iconBottom;
	@SideOnly(Side.CLIENT) public IIcon iconInner;

	public FoundryMold() {
		super();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		super.registerBlockIcons(iconRegister);
		this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":foundry_mold_top");
		this.iconSide = iconRegister.registerIcon(RefStrings.MODID + ":foundry_mold_side");
		this.iconBottom = iconRegister.registerIcon(RefStrings.MODID + ":foundry_mold_bottom");
		this.iconInner = iconRegister.registerIcon(RefStrings.MODID + ":foundry_mold_inner");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityFoundryMold();
	}

	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB entityBounding, List list, Entity entity) {
		
		AxisAlignedBB[] bbs = new AxisAlignedBB[] {
				AxisAlignedBB.getBoundingBox(x, y, z, x + 1D, y + 0.125D, z + 1D),
				AxisAlignedBB.getBoundingBox(x, y, z, x + 1D, y + 0.5D, z + 0.125D),
				AxisAlignedBB.getBoundingBox(x, y, z, x + 0.125D, y + 0.5D, z + 1D),
				AxisAlignedBB.getBoundingBox(x + 0.875D, y, z, x + 1D, y + 0.5D, z + 1D),
				AxisAlignedBB.getBoundingBox(x, y, z + 0.875, x + 1D, y + 0.5D, z + 1D),
		};
		
		for(AxisAlignedBB bb : bbs) {
			if(entityBounding.intersectsWith(bb)) {
				list.add(bb);
			}
		}
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return AxisAlignedBB.getBoundingBox(x, y, z, x + 1D, y + 0.5D, z + 1D);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
		return AxisAlignedBB.getBoundingBox(x, y, z, x + 1D, y + 0.5D, z + 1D);
	}

	public static int renderID = RenderingRegistry.getNextAvailableRenderId();

	@Override
	public int getRenderType() {
		return renderID;
	}
	
	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
		return side == ForgeDirection.DOWN;
	}
}
