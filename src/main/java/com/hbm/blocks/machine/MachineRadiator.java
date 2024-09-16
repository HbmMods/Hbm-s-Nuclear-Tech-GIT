package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ILookOverlay;
import com.hbm.dim.CelestialBody;
import com.hbm.dim.trait.CBT_Atmosphere;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityRadiator;
import com.hbm.util.BobMathUtil;
import com.hbm.util.I18nUtil;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineRadiator extends BlockDummyable implements ILookOverlay {

	public MachineRadiator(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityRadiator();
		if(meta >= 6) return new TileEntityProxyCombo(false, false, true);
		return null;
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);

		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		
		x = x + dir.offsetX * o;
		z = z + dir.offsetZ * o;
		
		makeExtra(world, x + rot.offsetX, y + 1, z + rot.offsetZ);
		makeExtra(world, x + rot.offsetX, y - 1, z + rot.offsetZ);
		makeExtra(world, x - rot.offsetX, y + 1, z - rot.offsetZ);
		makeExtra(world, x - rot.offsetX, y - 1, z - rot.offsetZ);
	}

	@Override
	public int[] getDimensions() {
		return new int[] {1, 1, 0, 0, 1, 1};
	}

	@Override
	public int getOffset() {
		return 0;
	}

	@Override
	public int getHeightOffset() {
		return 1;
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		int[] pos = this.findCore(world, x, y, z);

		if(pos == null)
			return;

		TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);

		if(!(te instanceof TileEntityRadiator))
			return;

		TileEntityRadiator tower = (TileEntityRadiator) te;

		List<String> text = new ArrayList<>();

		if(!tower.vacuumOptimised) {
			CBT_Atmosphere atmosphere = CelestialBody.getTrait(world, CBT_Atmosphere.class);
			if(CelestialBody.inOrbit(world) || atmosphere == null || atmosphere.getPressure() < 0.01) {
				text.add("&[" + (BobMathUtil.getBlink() ? 0xff0000 : 0xffff00) + "&]! ! ! " + I18nUtil.resolveKey("atmosphere.noVacuum") + " ! ! !");
			}
		}

		for(int i = 0; i < tower.tanks.length; i++)
			text.add((i < 1 ? (EnumChatFormatting.GREEN + "-> ") : (EnumChatFormatting.RED + "<- ")) + EnumChatFormatting.RESET + tower.tanks[i].getTankType().getLocalizedName() + ": " + tower.tanks[i].getFill() + "/" + tower.tanks[i].getMaxFill() + "mB");

		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}
	
}
