package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ILookOverlay;
import com.hbm.tileentity.network.TileEntityConverterRfHe;
import com.hbm.util.BobMathUtil;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockConverterRfHe extends BlockContainer implements ILookOverlay {

	public BlockConverterRfHe(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityConverterRfHe();
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);

		if(!(tile instanceof TileEntityConverterRfHe)) return;

		TileEntityConverterRfHe converter = (TileEntityConverterRfHe) tile;
		List<String> text = new ArrayList<>();

		text.add(EnumChatFormatting.GREEN + "-> " + EnumChatFormatting.RESET + BobMathUtil.getShortNumber(converter.getEnergyStored(ForgeDirection.UNKNOWN)) + "RF");
		text.add(EnumChatFormatting.RED + "<- " + EnumChatFormatting.RESET + BobMathUtil.getShortNumber(converter.getPower()) + "HE");

		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}
}
