package com.hbm.blocks.generic;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityAirScrubber;
import com.hbm.util.BobMathUtil;
import com.hbm.util.I18nUtil;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class BlockAirScrubber extends BlockDummyable implements ILookOverlay, ITooltipProvider {

	public BlockAirScrubber(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityAirScrubber();
		if(meta >= 6) return new TileEntityProxyCombo(false, true, true);
		return null;
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		int[] pos = this.findCore(world, x, y, z);
		if(pos == null) return;
				
		TileEntity tile = world.getTileEntity(pos[0], pos[1], pos[2]);
		if(!(tile instanceof TileEntityAirScrubber)) return;
		
		TileEntityAirScrubber scrubber = (TileEntityAirScrubber) tile;

		List<String> text = new ArrayList<>();

		text.add((scrubber.getPower() <= 200 ? EnumChatFormatting.RED : EnumChatFormatting.GREEN) + "Power: " + BobMathUtil.getShortNumber(scrubber.getPower()) + "HE");
		text.add(EnumChatFormatting.RED + "<- " + EnumChatFormatting.RESET + scrubber.tank.getTankType().getLocalizedName() + ": " + scrubber.tank.getFill() + "/" + scrubber.tank.getMaxFill() + "mB");
	
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		addStandardInfo(stack, player, list, ext);
	}

	@Override
	public int[] getDimensions() {
		return new int[] {1, 0, 0, 0, 0, 0};
	}

	@Override
	public int getOffset() {
		return 0;
	}
	
}
