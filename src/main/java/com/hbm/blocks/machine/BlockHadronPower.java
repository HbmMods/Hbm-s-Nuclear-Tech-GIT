package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ILookOverlay;
import com.hbm.tileentity.machine.TileEntityHadronPower;
import com.hbm.util.BobMathUtil;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class BlockHadronPower extends BlockContainer implements ILookOverlay {
	
	public long power;

	public BlockHadronPower(Material mat, long power) {
		super(mat);
		this.power = power;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityHadronPower();
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		
		TileEntity te = world.getTileEntity(x, y, z);
		
		if(!(te instanceof TileEntityHadronPower))
			return;
		
		TileEntityHadronPower battery = (TileEntityHadronPower) te;
		
		List<String> text = new ArrayList();
		text.add(BobMathUtil.getShortNumber(battery.getPower()) + " / " + BobMathUtil.getShortNumber(battery.getMaxPower()) + "HE");
		
		double percent = (double) battery.getPower() / (double) battery.getMaxPower();
		int charge = (int) Math.floor(percent * 10_000D);
		int color = ((int) (0xFF - 0xFF * percent)) << 16 | ((int)(0xFF * percent) << 8);
		
		text.add("&[" + color + "&]" + (charge / 100D) + "%");
		
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}
}
