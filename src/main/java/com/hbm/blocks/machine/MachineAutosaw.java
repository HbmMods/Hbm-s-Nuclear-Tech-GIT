package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.hbm.blocks.ILookOverlay;
import com.hbm.tileentity.machine.TileEntityMachineAutosaw;
import com.hbm.util.I18nUtil;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class MachineAutosaw extends BlockContainer implements ILookOverlay {

	public MachineAutosaw() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityMachineAutosaw();
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		
		TileEntity te = world.getTileEntity(x, y, z);
		
		if(!(te instanceof TileEntityMachineAutosaw))
			return;
		
		TileEntityMachineAutosaw saw = (TileEntityMachineAutosaw) te;
		
		List<String> text = new ArrayList();
		text.add(I18nUtil.resolveKey("hbmfluid." + saw.tank.getTankType().getName().toLowerCase(Locale.US)) + ": " + saw.tank.getFill() + "/" + saw.tank.getMaxFill() + "mB");
		
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}
}
