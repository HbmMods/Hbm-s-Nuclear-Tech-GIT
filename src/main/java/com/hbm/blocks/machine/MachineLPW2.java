package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ILookOverlay;
import com.hbm.dim.CelestialBody;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityMachineLPW2;
import com.hbm.util.BobMathUtil;
import com.hbm.util.I18nUtil;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineLPW2 extends BlockDummyable implements ILookOverlay {

	public MachineLPW2() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityMachineLPW2();
		if(meta >= 6) return new TileEntityProxyCombo(false, false, true);
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {6, 0, 3, 3, 9, 10};
	}

	@Override
	public int getOffset() {
		return 9;
	}

	@Override
	public ForgeDirection getDirModified(ForgeDirection dir) {
		return dir.getRotation(ForgeDirection.DOWN);
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);

		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		x += dir.offsetX * o;
		z += dir.offsetZ * o;

		this.makeExtra(world, x + dir.offsetX * 3 - rot.offsetX, y + 3, z + dir.offsetZ * 3 - rot.offsetZ);
		this.makeExtra(world, x - dir.offsetX * 3 - rot.offsetX, y + 3, z - dir.offsetZ * 3 - rot.offsetZ);
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		if(!CelestialBody.inOrbit(world)) return;

		int[] pos = this.findCore(world, x, y, z);
		
		if(pos == null) return;
		
		TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);
		
		if(!(te instanceof TileEntityMachineLPW2))
			return;
		
		TileEntityMachineLPW2 thruster = (TileEntityMachineLPW2) te;

		List<String> text = new ArrayList<String>();

		if(!thruster.isFacingPrograde()) {
			text.add("&[" + (BobMathUtil.getBlink() ? 0xff0000 : 0xffff00) + "&]! ! ! " + I18nUtil.resolveKey("atmosphere.engineFacing") + " ! ! !");
		} else {
			for(int i = 0; i < thruster.tanks.length; i++) {
				FluidTank tank = thruster.tanks[i];
				text.add(EnumChatFormatting.GREEN + "-> " + EnumChatFormatting.RESET + tank.getTankType().getLocalizedName() + ": " + tank.getFill() + "/" + tank.getMaxFill() + "mB");
			}
		}

		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}

}
