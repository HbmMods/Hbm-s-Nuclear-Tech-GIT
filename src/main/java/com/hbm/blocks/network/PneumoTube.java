package com.hbm.blocks.network;

import java.util.ArrayList;
import java.util.List;

import com.hbm.lib.Library;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.network.TileEntityPneumoTube;
import com.hbm.util.Compat;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class PneumoTube extends BlockContainer {

	@SideOnly(Side.CLIENT) public IIcon iconIn;
	@SideOnly(Side.CLIENT) public IIcon iconOut;
	@SideOnly(Side.CLIENT) public IIcon iconConnector;
	
	public boolean[] renderSides = new boolean[] {true, true, true, true, true, true};

	public PneumoTube() {
		super(Material.iron);
	}

	public static int renderID = RenderingRegistry.getNextAvailableRenderId();

	@Override public int getRenderType() { return renderID; }
	@Override public boolean isOpaqueCube() { return false; }
	@Override public boolean renderAsNormalBlock() { return false; }

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityPneumoTube();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		super.registerBlockIcons(reg);

		iconIn = reg.registerIcon(RefStrings.MODID + ":pneumatic_tube_in");
		iconOut = reg.registerIcon(RefStrings.MODID + ":pneumatic_tube_out");
		iconConnector = reg.registerIcon(RefStrings.MODID + ":pneumatic_tube_connector");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
		return renderSides[side % 6];
	}
	
	public void resetRenderSides() {
		for(int i = 0; i < 6; i++) renderSides[i] = true;
	}

	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB entityBounding, List list, Entity entity) {

		List<AxisAlignedBB> bbs = new ArrayList();
		
		double lower = 0.3125D;
		double upper = 0.6875D;
		
		bbs.add(AxisAlignedBB.getBoundingBox(x + lower, y + lower, z + lower, x + upper, y + upper, z + upper));

		if(canConnectTo(world, x, y, z, Library.POS_X)) bbs.add(AxisAlignedBB.getBoundingBox(x + upper, y + lower, z + lower, x + 1, y + upper, z + upper));
		if(canConnectTo(world, x, y, z, Library.NEG_X)) bbs.add(AxisAlignedBB.getBoundingBox(x, y + lower, z + lower, x + lower, y + upper, z + upper));
		if(canConnectTo(world, x, y, z, Library.POS_Y)) bbs.add(AxisAlignedBB.getBoundingBox(x + lower, y + upper, z + lower, x + upper, y + 1, z + upper));
		if(canConnectTo(world, x, y, z, Library.NEG_Y)) bbs.add(AxisAlignedBB.getBoundingBox(x + lower, y, z + lower, x + upper, y + lower, z + upper));
		if(canConnectTo(world, x, y, z, Library.POS_Z)) bbs.add(AxisAlignedBB.getBoundingBox(x + lower, y + lower, z + upper, x + upper, y + upper, z + 1));
		if(canConnectTo(world, x, y, z, Library.NEG_Z)) bbs.add(AxisAlignedBB.getBoundingBox(x + lower, y + lower, z, x + upper, y + upper, z + lower));

		for(AxisAlignedBB bb : bbs) {
			if(entityBounding.intersectsWith(bb)) {
				list.add(bb);
			}
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		setBlockBoundsBasedOnState(world, x, y, z);
		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
		setBlockBoundsBasedOnState(world, x, y, z);
		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {

		float lower = 0.3125F;
		float upper = 0.6875F;

		boolean nX = canConnectTo(world, x, y, z, Library.NEG_X);
		boolean pX = canConnectTo(world, x, y, z, Library.POS_X);
		boolean nY = canConnectTo(world, x, y, z, Library.NEG_Y);
		boolean pY = canConnectTo(world, x, y, z, Library.POS_Y);
		boolean nZ = canConnectTo(world, x, y, z, Library.NEG_Z);
		boolean pZ = canConnectTo(world, x, y, z, Library.POS_Z);
		
		this.setBlockBounds(
				nX ? 0F : lower,
				nY ? 0F : lower,
				nZ ? 0F : lower,
				pX ? 1F : upper,
				pY ? 1F : upper,
				pZ ? 1F : upper);
	}

	public boolean canConnectTo(IBlockAccess world, int x, int y, int z, ForgeDirection dir) {
		TileEntity tile = world instanceof World ? Compat.getTileStandard((World) world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ) : world.getTileEntity(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
		return tile instanceof TileEntityPneumoTube;
	}
}
