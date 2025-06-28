package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ILookOverlay;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityMachineIntake;
import com.hbm.util.BobMathUtil;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class MachineIntake extends BlockDummyable implements ILookOverlay {

	public MachineIntake() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityMachineIntake();
		return new TileEntityProxyCombo().power().fluid();
	}

	@Override public int[] getDimensions() { return new int[] {0, 0, 1, 0, 1, 0}; }
	@Override public int getOffset() { return 0; }

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {

		int[] pos = this.findCore(world, x, y, z);
		if(pos == null) return;
		TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);
		if(!(te instanceof TileEntityMachineIntake)) return;

		TileEntityMachineIntake intake = (TileEntityMachineIntake) te;

		List<String> text = new ArrayList();
		text.add((intake.power < intake.getMaxPower() / 20 ? EnumChatFormatting.RED : EnumChatFormatting.GREEN) + "Power: " + BobMathUtil.getShortNumber(intake.power) + "HE");
		text.add(EnumChatFormatting.RED + "<- " + EnumChatFormatting.RESET + intake.compair.getTankType().getLocalizedName() + ": " + intake.compair.getFill() + "/" + intake.compair.getMaxFill() + "mB");

		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}
}
