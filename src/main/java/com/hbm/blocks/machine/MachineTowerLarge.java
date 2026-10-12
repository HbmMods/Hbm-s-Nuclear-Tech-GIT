package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ILookOverlay;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityTowerLarge;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineTowerLarge extends BlockDummyable implements ILookOverlay {

	public MachineTowerLarge(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int meta) {
		
		if(meta >= 12)
			return new TileEntityTowerLarge();
		
		if(meta >= 8)
			return new TileEntityProxyCombo(false, false, true);
		
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {12, 0, 4, 4, 4, 4};
	}

	@Override
	public int getOffset() {
		return 4;
	}
	@Override
	public int[][] getAllPorts(ForgeDirection dir) {
		return rotatePorts(new int[][] {
			{4, 0, 0, ForgeDirection.EAST.ordinal()},
			{-4, 0, 0, ForgeDirection.WEST.ordinal()},
			{0, 0, -4, ForgeDirection.NORTH.ordinal()},
			{0, 0, 4, ForgeDirection.SOUTH.ordinal()},
			{3, 0, -4, ForgeDirection.NORTH.ordinal()},
			{-3, 0, -4, ForgeDirection.NORTH.ordinal()},
			{3, 0, 4, ForgeDirection.SOUTH.ordinal()},
			{-3, 0, 4, ForgeDirection.SOUTH.ordinal()},
			{4, 0, 3, ForgeDirection.EAST.ordinal()},
			{4, 0, -3, ForgeDirection.EAST.ordinal()},
			{-4, 0, 3, ForgeDirection.WEST.ordinal()},
			{-4, 0, -3, ForgeDirection.WEST.ordinal()},
			{0, 0, -4, ForgeDirection.NORTH.ordinal()},
			{0, 0, 4, ForgeDirection.SOUTH.ordinal()},
		}, dir);
	}
	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);
		
		
		for(int[] offset : getAllPorts(dir)) {
			this.makeExtra(world, x + offset[0], y + offset[1], z + offset[2]);
		}
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		int[] pos = this.findCore(world, x, y, z);

		if(pos == null)
			return;

		TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);

		if(!(te instanceof TileEntityTowerLarge))
			return;

		TileEntityTowerLarge tower = (TileEntityTowerLarge) te;

		List<String> text = new ArrayList();

		for(int i = 0; i < tower.tanks.length; i++)
			text.add((i < 1 ? (EnumChatFormatting.GREEN + "-> ") : (EnumChatFormatting.RED + "<- ")) + EnumChatFormatting.RESET + tower.tanks[i].getTankType().getLocalizedName() + ": " + tower.tanks[i].getFill() + "/" + tower.tanks[i].getMaxFill() + "mB");

		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}
}
