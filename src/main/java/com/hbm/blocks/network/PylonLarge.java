package com.hbm.blocks.network;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.tileentity.network.TileEntityPylonBase;
import com.hbm.tileentity.network.TileEntityPylonLarge;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

public class PylonLarge extends BlockDummyable implements ITooltipProvider {

	public PylonLarge(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {

		if(meta >= 12)
			return new TileEntityPylonLarge();
		return null;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		list.add(EnumChatFormatting.GOLD + "Connection Type: " + EnumChatFormatting.YELLOW + "Quadruple");
		list.add(EnumChatFormatting.GOLD + "Connection Range: " + EnumChatFormatting.YELLOW + "100m");
		list.add(EnumChatFormatting.GOLD + "This pylon requires a substation!");
	}

	@Override
	public int[] getDimensions() {
		return new int[] {13, 0, 1, 1, 1, 1};
	}

	@Override
	public int getOffset() {
		return 0;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block b, int m) {

		TileEntity te = world.getTileEntity(x, y, z);

		if(te instanceof TileEntityPylonBase) {
			((TileEntityPylonBase)te).disconnectAll();
		}

		super.breakBlock(world, x, y, z, b, m);
	}

	@Override
	protected int getMetaForCore(World world, int x, int y, int z, EntityPlayer player, int original) {

		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 180.0F + 0.5D) & 3;

		ForgeDirection dir = ForgeDirection.NORTH;

		if(i == 0) {
			dir = ForgeDirection.getOrientation(2);
		}
		if(i == 1) {
			dir = ForgeDirection.getOrientation(5);
		}
		if(i == 2) {
			dir = ForgeDirection.getOrientation(3);
		}
		if(i == 3) {
			dir = ForgeDirection.getOrientation(4);
		}

		return dir.ordinal() + offset;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking()) {
			int[] pos = this.findCore(world, x, y, z);
			TileEntityPylonBase te = (TileEntityPylonBase) world.getTileEntity(pos[0], pos[1], pos[2]);
			return te.setColor(player.getHeldItem());
		} else {
			return false;
		}
	}
}
