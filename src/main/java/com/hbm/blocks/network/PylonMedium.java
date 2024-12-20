package com.hbm.blocks.network;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.tileentity.network.TileEntityPylonBase;
import com.hbm.tileentity.network.TileEntityPylonMedium;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import java.util.List;

public class PylonMedium extends BlockDummyable implements ITooltipProvider {

	public PylonMedium(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {

		if(meta >= 12) return new TileEntityPylonMedium();
		return null;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		list.add(EnumChatFormatting.GOLD + "Connection Type: " + EnumChatFormatting.YELLOW + "Triple");
		list.add(EnumChatFormatting.GOLD + "Connection Range: " + EnumChatFormatting.YELLOW + "45m");
	}

	@Override
	public int[] getDimensions() {
		return new int[] {6, 0, 0, 0, 0, 0};
	}

	@Override
	public int getOffset() {
		return 0;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block b, int m) {
		TileEntity te = world.getTileEntity(x, y, z);
		if(te instanceof TileEntityPylonBase) ((TileEntityPylonBase)te).disconnectAll();
		super.breakBlock(world, x, y, z, b, m);
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
