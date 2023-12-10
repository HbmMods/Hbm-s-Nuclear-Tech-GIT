package com.hbm.blocks.network;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ILookOverlay;
import com.hbm.tileentity.conductor.TileEntityFluidDuct;
import com.hbm.tileentity.conductor.TileEntityFluidDuctSimple;
import com.hbm.util.I18nUtil;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class BlockFluidDuct extends BlockContainer implements ILookOverlay {

	public BlockFluidDuct(Material p_i45386_1_) {
		super(p_i45386_1_);
		float p = 1F/16F;
		this.setBlockBounds(11 * p / 2, 11 * p / 2, 11 * p / 2, 1 - 11 * p / 2, 1 - 11 * p / 2, 1 - 11 * p / 2);
		this.useNeighborBrightness = true;
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		if(world.getTileEntity(x, y, z) instanceof TileEntityFluidDuct) {
			TileEntityFluidDuct cable = (TileEntityFluidDuct)world.getTileEntity(x, y, z);

		if(cable != null)
		{
			float p = 1F/16F;
			float minX = 11 * p / 2 - (cable.connections[5] != null ? (11 * p / 2) : 0);
			float minY = 11 * p / 2 - (cable.connections[1] != null ? (11 * p / 2) : 0);
			float minZ = 11 * p / 2 - (cable.connections[2] != null ? (11 * p / 2) : 0);
			float maxX = 1 - 11 * p / 2 + (cable.connections[3] != null ? (11 * p / 2) : 0);
			float maxY = 1 - 11 * p / 2 + (cable.connections[0] != null ? (11 * p / 2) : 0);
			float maxZ = 1 - 11 * p / 2 + (cable.connections[4] != null ? (11 * p / 2) : 0);
			
			this.setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
		}
		}
		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		if(world.getTileEntity(x, y, z) instanceof TileEntityFluidDuct) {
			TileEntityFluidDuct cable = (TileEntityFluidDuct)world.getTileEntity(x, y, z);

		if(cable != null)
		{
			float p = 1F/16F;
			float minX = 11 * p / 2 - (cable.connections[5] != null ? (11 * p / 2) : 0);
			float minY = 11 * p / 2 - (cable.connections[1] != null ? (11 * p / 2) : 0);
			float minZ = 11 * p / 2 - (cable.connections[2] != null ? (11 * p / 2) : 0);
			float maxX = 1 - 11 * p / 2 + (cable.connections[3] != null ? (11 * p / 2) : 0);
			float maxY = 1 - 11 * p / 2 + (cable.connections[0] != null ? (11 * p / 2) : 0);
			float maxZ = 1 - 11 * p / 2 + (cable.connections[4] != null ? (11 * p / 2) : 0);
			
			this.setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
		}
		}
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityFluidDuct();
	}
	
	@Override
	public int getRenderType(){
		return -1;
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
	public void printHook(Pre event, World world, int x, int y, int z) {
		
		TileEntity te = world.getTileEntity(x, y, z);
		
		if(!(te instanceof TileEntityFluidDuctSimple))
			return;
		
		TileEntityFluidDuctSimple duct = (TileEntityFluidDuctSimple) te;
		
		List<String> text = new ArrayList();
		text.add("&[" + duct.getType().getColor() + "&]" + duct.getType().getLocalizedName());
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}
}
