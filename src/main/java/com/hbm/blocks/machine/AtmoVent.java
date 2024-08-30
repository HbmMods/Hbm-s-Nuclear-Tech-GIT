package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ILookOverlay;
import com.hbm.dim.CelestialBody;
import com.hbm.dim.trait.CBT_Atmosphere;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityAtmoExtractor;
import com.hbm.util.BobMathUtil;
import com.hbm.util.I18nUtil;

import api.hbm.block.IToolable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.util.ForgeDirection;

public class AtmoVent extends BlockDummyable implements ILookOverlay, IToolable {

	public AtmoVent(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int meta) {
		if(meta >= 12) return new TileEntityAtmoExtractor();
		if(meta >= 8) return new TileEntityProxyCombo(false, true, true);
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] { 3, 0, 1, 0, 0, 1 };
	}
	

	@Override
	public int getOffset() {
		return 0;
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);

		x = x + dir.offsetX * o;
		z = z + dir.offsetZ * o;

		ForgeDirection dr2 = dir.getRotation(ForgeDirection.UP);

		this.makeExtra(world, x - dir.offsetX - dr2.offsetX, y, z - dir.offsetZ - dr2.offsetZ);
		this.makeExtra(world, x, y, z - dir.offsetZ - dr2.offsetZ);
		this.makeExtra(world, x - dir.offsetX - dr2.offsetX, y, z);
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		int[] pos = this.findCore(world, x, y, z);

		if(pos == null)
			return;

		TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);

		if(!(te instanceof TileEntityAtmoExtractor))
			return;

		TileEntityAtmoExtractor tower = (TileEntityAtmoExtractor) te;

		List<String> text = new ArrayList<String>();
		if(!CelestialBody.hasTrait(world, CBT_Atmosphere.class)) {
			text.add(((EnumChatFormatting.RED + "ERROR: ")) + EnumChatFormatting.RESET + I18nUtil.resolveKey("CANNOT COLLECT IN VACUUM"));
		} else {
			text.add((tower.power < tower.getMaxPower() / 20 ? EnumChatFormatting.RED : EnumChatFormatting.GREEN) + "Power: " + BobMathUtil.getShortNumber(tower.power) + "HE");
			text.add(((EnumChatFormatting.RED + "<- ")) + EnumChatFormatting.RESET + I18nUtil.resolveKey("hbmfluid." + tower.tank.getTankType().getName().toLowerCase()) + ": " + tower.tank.getFill() + "/" + tower.tank.getMaxFill() + "mB");
		}
		
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}

	@Override
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ, ToolType tool) {
		
		if(tool != ToolType.SCREWDRIVER)
			return false;
		
		if(world.isRemote) return true;
		
		int[] pos = this.findCore(world, x, y, z);
		
		if(pos == null) return false;
		
		TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);
		
		if(!(te instanceof TileEntityAtmoExtractor)) return false;
		
		TileEntityAtmoExtractor tile = (TileEntityAtmoExtractor) te;
		tile.cycleGas();
		tile.markDirty();
		
		return true;
	}
}