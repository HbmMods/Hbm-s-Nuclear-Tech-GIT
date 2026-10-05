package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.IPersistentInfoProvider;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.oil.TileEntityMachineCatalyticReformer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

public class MachineCatalyticReformer extends BlockDummyable implements IPersistentInfoProvider {

	public MachineCatalyticReformer(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityMachineCatalyticReformer();
		if(meta >= 6) return new TileEntityProxyCombo().fluid().power();
		return null;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return standardOpenBehavior(world, x, y, z, player, side);
	}

	@Override
	protected boolean checkRequirement(World world, int x, int y, int z, ForgeDirection dir, int o) {
		return super.checkRequirement(world, x, y, z, dir, o) &&
				MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[]{3, -3, 1, 0, -1, 2}, x, y, z, dir) &&
				MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[]{6, -3, 1, 1, 2, 0}, x, y, z, dir);
	}
	@Override
	public int[][] getAllPorts(ForgeDirection dir) {
		return rotatePorts(new int[][] {
			{2, 0, 0, ForgeDirection.EAST.ordinal()},
			{-2, 0, 0, ForgeDirection.WEST.ordinal()},
			{1, 0, 1, ForgeDirection.SOUTH.ordinal()},
			{-1, 0, 1, ForgeDirection.SOUTH.ordinal()},
			{1, 0, -2, ForgeDirection.SOUTH.ordinal()},
			{-1, 0, -2, ForgeDirection.SOUTH.ordinal()}
		}, dir);
	}
	@Override
	protected void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {3, -3, 1, 0, -1, 2}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {6, -3, 1, 1, 2, 0}, this, dir);

		for(int[] offset : getAllPorts(dir)) {
			this.makeExtra(world, x + offset[0], y + offset[1], z + offset[2]);
		}
	}

	@Override
	public int[] getDimensions() {
		return new int[] {2, 0, 1, 1, 2, 2};
	}

	@Override
	public int getOffset() {
		return 1;
	}
	@Override
	public int[][] getAllDimensions() {
		return new int[][] {
			new int[] {2, 0, 1, 1, 2, 2},
			new int[] {3, -3, 1, 0, -1, 2},
			new int[] {6, -3, 1, 1, 2, 0},
		};
	}
	@Override
	public void addInformation(ItemStack stack, NBTTagCompound persistentTag, EntityPlayer player, List list, boolean ext) {

		for(int i = 0; i < 4; i++) {
			FluidTank tank = new FluidTank(Fluids.NONE, 0);
			tank.readFromNBT(persistentTag, "" + i);
			list.add(EnumChatFormatting.YELLOW + "" + tank.getFill() + "/" + tank.getMaxFill() + "mB " + tank.getTankType().getLocalizedName());
		}
	}
}
