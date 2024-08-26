package com.hbm.blocks.generic;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ILookOverlay;
import com.hbm.tileentity.machine.TileEntityAlgaeFilm;
import com.hbm.util.BobMathUtil;
import com.hbm.util.I18nUtil;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class BlockAlgaeFilm extends BlockContainer implements ILookOverlay {

	public BlockAlgaeFilm(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityAlgaeFilm();
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
						
		TileEntity tile = world.getTileEntity(x, y, z);
		
		if(!(tile instanceof TileEntityAlgaeFilm)) return;
		
		TileEntityAlgaeFilm film = (TileEntityAlgaeFilm) tile;

		List<String> text = new ArrayList<>();

		if(!film.canOperate) {
			text.add("&[" + (BobMathUtil.getBlink() ? 0xff0000 : 0xffff00) + "&]! ! ! " + I18nUtil.resolveKey("atmosphere.noGravity") + " ! ! !");
		}

		text.add(EnumChatFormatting.GREEN + "-> " + EnumChatFormatting.RESET + film.tanks[0].getTankType().getLocalizedName() + ": " + film.tanks[0].getFill() + "/" + film.tanks[0].getMaxFill() + "mB");
		text.add(EnumChatFormatting.RED + "<- " + EnumChatFormatting.RESET + film.tanks[1].getTankType().getLocalizedName() + ": " + film.tanks[1].getFill() + "/" + film.tanks[1].getMaxFill() + "mB");
	
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}
	
}
