package com.hbm.blocks.network;

import java.util.List;

import com.hbm.blocks.ITooltipProvider;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.lib.Library;
import com.hbm.tileentity.network.TileEntityPipeAnchor;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class FluidPipeAnchor extends FluidDuctBase implements ITooltipProvider {

	public FluidPipeAnchor() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityPipeAnchor();
	}

	@Override public int getRenderType() { return -1; }
	@Override public boolean isOpaqueCube() { return false; }
	@Override public boolean renderAsNormalBlock() { return false; }

	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float fX, float fY, float fZ, int meta) {
		return side;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		setBlockBounds(world.getBlockMetadata(x, y, z));
		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		setBlockBounds(world.getBlockMetadata(x, y, z));
	}

	private void setBlockBounds(int meta) {

		float pixel = 0.0625F;
		float min = pixel * 4F;
		float max = pixel * 12F;

		ForgeDirection dir = ForgeDirection.getOrientation(meta).getOpposite();

		float minX = dir == Library.NEG_X ? 0F : min;
		float maxX = dir == Library.POS_X ? 1F : max;
		float minY = dir == Library.NEG_Y ? 0F : min;
		float maxY = dir == Library.POS_Y ? 1F : max;
		float minZ = dir == Library.NEG_Z ? 0F : min;
		float maxZ = dir == Library.POS_Z ? 1F : max;

		this.setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		list.add(EnumChatFormatting.GOLD + "Connection Type: " + EnumChatFormatting.YELLOW + "Single");
		list.add(EnumChatFormatting.GOLD + "Connection Range: " + EnumChatFormatting.YELLOW + "10m");
	}

	@Override // didn't think this was overridable, that makes everything so much easier. good job martin
	public void changeTypeRecursively(World world, int x, int y, int z, FluidType prevType, FluidType type, int loopsRemaining) {

		TileEntity te = world.getTileEntity(x, y, z);

		if(te instanceof TileEntityPipeAnchor) {
			TileEntityPipeAnchor pipe = (TileEntityPipeAnchor) te;

			if(pipe.getType() == prevType && pipe.getType() != type) {
				pipe.setType(type);

				if(loopsRemaining > 0) {
					ForgeDirection dir = ForgeDirection.getOrientation(pipe.getBlockMetadata()).getOpposite();
					Block b = world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);

					if(b instanceof IBlockFluidDuct) ((IBlockFluidDuct) b).changeTypeRecursively(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, prevType, type, loopsRemaining - 1);
					
					for(int[] pos : pipe.getConnected()) {
						Block c = world.getBlock(pos[0], pos[1], pos[2]);
						if(c instanceof IBlockFluidDuct) ((IBlockFluidDuct) c).changeTypeRecursively(world, pos[0], pos[1], pos[2], prevType, type, loopsRemaining - 1);
					}
				}
			}
		}
	}
}
