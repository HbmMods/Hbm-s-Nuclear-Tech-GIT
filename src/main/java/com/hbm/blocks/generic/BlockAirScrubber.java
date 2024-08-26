package com.hbm.blocks.generic;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ILookOverlay;
import com.hbm.handler.atmosphere.IBlockSealable;
import com.hbm.tileentity.machine.TileEntityAirScrubber;
import com.hbm.util.I18nUtil;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class BlockAirScrubber extends BlockContainer implements ILookOverlay, IBlockSealable {

	public BlockAirScrubber(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityAirScrubber();
	}

	@Override
	public boolean isSealed(World world, int x, int y, int z) {
		return false;
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
				
		TileEntity tile = world.getTileEntity(x, y, z);
		
		if(!(tile instanceof TileEntityAirScrubber)) return;
		
		TileEntityAirScrubber scrubber = (TileEntityAirScrubber) tile;

		List<String> text = new ArrayList<>();

		text.add(EnumChatFormatting.RED + "<- " + EnumChatFormatting.RESET + scrubber.tank.getTankType().getLocalizedName() + ": " + scrubber.tank.getFill() + "/" + scrubber.tank.getMaxFill() + "mB");
	
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}
	
}
