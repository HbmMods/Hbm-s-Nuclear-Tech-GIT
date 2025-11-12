package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ILookOverlay;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntitySolarBoiler;
import com.hbm.util.BobMathUtil;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;

public class MachineSolarBoiler extends BlockDummyable implements ILookOverlay {

	public MachineSolarBoiler(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {

		if(meta >= 12)
			return new TileEntitySolarBoiler();
		if(meta >= extra)
			return new TileEntityProxyCombo(false, false, true);

		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {2, 0, 1, 1, 1, 1};
	}

	@Override
	public int getOffset() {
		return 1;
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);

		x = x + dir.offsetX * o;
		z = z + dir.offsetZ * o;

		this.makeExtra(world, x, y + 2, z);
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		int[] pos = findCore(world, x, y, z);

		if(pos == null)
			return;

		TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);

		if(!(te instanceof TileEntitySolarBoiler))
			return;

		TileEntitySolarBoiler boiler = (TileEntitySolarBoiler) te;

		List<String> text = new ArrayList<>();

		FluidTank[] tanks = boiler.getAllTanks();

		for(int i = 0; i < tanks.length; i++)
			text.add((i < 1 ? (EnumChatFormatting.GREEN + "-> ") : (EnumChatFormatting.RED + "<- ")) + EnumChatFormatting.RESET + tanks[i].getTankType().getLocalizedName() + ": " + tanks[i].getFill() + "/" + tanks[i].getMaxFill() + "mB");

		if(boiler.display < 1) {
			text.add("&[" + (BobMathUtil.getBlink() ? 0xff0000 : 0xffff00) + "&]Too cold!");
		}
		
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}
}
