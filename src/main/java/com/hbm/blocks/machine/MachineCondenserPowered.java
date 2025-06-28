package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ILookOverlay;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityCondenserPowered;
import com.hbm.util.BobMathUtil;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MachineCondenserPowered extends BlockDummyable implements ILookOverlay {

	public MachineCondenserPowered(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int meta) {
		if(meta >= 12) return new TileEntityCondenserPowered();
		if(meta >= 6) return new TileEntityProxyCombo().power().fluid();
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {2, 0, 1, 1, 3, 3};
	}

	@Override
	public int getOffset() {
		return 1;
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);

		x = x + dir.offsetX * o;
		z = z + dir.offsetZ * o;

		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		this.makeExtra(world, x + rot.offsetX * 3, y + 1, z + rot.offsetZ * 3);
		this.makeExtra(world, x - rot.offsetX * 3, y + 1, z - rot.offsetZ * 3);
		this.makeExtra(world, x + dir.offsetX + rot.offsetX, y + 1, z + dir.offsetZ + rot.offsetZ);
		this.makeExtra(world, x + dir.offsetX - rot.offsetX, y + 1, z + dir.offsetZ - rot.offsetZ);
		this.makeExtra(world, x - dir.offsetX + rot.offsetX, y + 1, z - dir.offsetZ + rot.offsetZ);
		this.makeExtra(world, x - dir.offsetX - rot.offsetX, y + 1, z - dir.offsetZ - rot.offsetZ);
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		int[] pos = this.findCore(world, x, y, z);

		if(pos == null)
			return;

		TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);

		if(!(te instanceof TileEntityCondenserPowered)) return;

		TileEntityCondenserPowered tower = (TileEntityCondenserPowered) te;
		List<String> text = new ArrayList();

		text.add(BobMathUtil.getShortNumber(tower.power) + "HE / " + BobMathUtil.getShortNumber(tower.maxPower) + "HE");

		for(int i = 0; i < tower.tanks.length; i++)
			text.add((i < 1 ? (EnumChatFormatting.GREEN + "-> ") : (EnumChatFormatting.RED + "<- ")) + EnumChatFormatting.RESET + tower.tanks[i].getTankType().getLocalizedName() + ": " + String.format(Locale.US, "%,d", tower.tanks[i].getFill()) + "/" + String.format(Locale.US, "%,d", tower.tanks[i].getMaxFill()) + "mB");

		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}
}
