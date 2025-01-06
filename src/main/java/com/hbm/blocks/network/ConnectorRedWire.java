package com.hbm.blocks.network;

import com.hbm.lib.Library;
import com.hbm.tileentity.network.TileEntityConnector;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

public class ConnectorRedWire extends PylonBase {

	public ConnectorRedWire(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityConnector();
	}

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
		float min = pixel * 5F;
		float max = pixel * 11F;

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
}
