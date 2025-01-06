package com.hbm.blocks.network;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.tileentity.TileEntityProxyConductor;
import com.hbm.tileentity.network.TileEntityPylonBase;
import com.hbm.tileentity.network.TileEntitySubstation;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

public class Substation extends BlockDummyable implements ITooltipProvider {

	public Substation(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {

		if(meta >= 12)
			return new TileEntitySubstation();

		if(meta >= 6)
			return new TileEntityProxyConductor();

		return null;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		list.add(EnumChatFormatting.GOLD + "Connection Type: " + EnumChatFormatting.YELLOW + "Quadruple");
		list.add(EnumChatFormatting.GOLD + "Connection Range: " + EnumChatFormatting.YELLOW + "20m");
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
	public int[] getDimensions() {
		return new int[] {4, 0, 1, 1, 2, 2};
	}

	@Override
	public int getOffset() {
		return 1;
	}

	@Override
	protected void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);

		this.makeExtra(world, x + dir.offsetX * o + 1, y, z + dir.offsetZ * o + 1);
		this.makeExtra(world, x + dir.offsetX * o + 1, y, z + dir.offsetZ * o - 1);
		this.makeExtra(world, x + dir.offsetX * o - 1, y, z + dir.offsetZ * o + 1);
		this.makeExtra(world, x + dir.offsetX * o - 1, y, z + dir.offsetZ * o - 1);
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
