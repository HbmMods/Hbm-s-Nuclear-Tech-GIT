package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ILookOverlay;
import com.hbm.tileentity.machine.TileEntityCondenser;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class MachineCondenser extends BlockContainer implements ILookOverlay {

	public MachineCondenser(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityCondenser();
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		
		TileEntity te = world.getTileEntity(x, y, z);
		
		if(!(te instanceof TileEntityCondenser))
			return;
		
		TileEntityCondenser condenser = (TileEntityCondenser) te;
		
		List<String> text = new ArrayList();

		for(int i = 0; i < condenser.tanks.length; i++)
			text.add((i < 1 ? (EnumChatFormatting.GREEN + "-> ") : (EnumChatFormatting.RED + "<- ")) + EnumChatFormatting.RESET +condenser.tanks[i].getTankType().getLocalizedName() + ": " + condenser.tanks[i].getFill() + "/" + condenser.tanks[i].getMaxFill() + "mB");
		
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}
}
