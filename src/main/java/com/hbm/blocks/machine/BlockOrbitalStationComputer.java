package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ILookOverlay;
import com.hbm.dim.CelestialBody;
import com.hbm.dim.SolarSystem;
import com.hbm.dim.orbit.OrbitalStation;
import com.hbm.items.ItemVOTVdrive;
import com.hbm.items.ItemVOTVdrive.Destination;
import com.hbm.tileentity.machine.TileEntityOrbitalStationComputer;
import com.hbm.util.I18nUtil;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class BlockOrbitalStationComputer extends BlockDummyable implements ILookOverlay {

	public BlockOrbitalStationComputer(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityOrbitalStationComputer();
	}

	@Override
	public int[] getDimensions() {
		return new int[] {0, 0, 0, 0, 0, 0};
	}

	@Override
	public int getOffset() {
		return 0;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(!CelestialBody.inOrbit(world)) return false;

		ItemStack heldStack = player.getHeldItem();
		if(heldStack == null || !(heldStack.getItem() instanceof ItemVOTVdrive))
			return false;

		Destination destination = ItemVOTVdrive.getDestination(heldStack);

		if(destination.body == SolarSystem.Body.ORBIT)
			return false;

		if(world.isRemote) {
			return true;
		} else {
			int[] pos = this.findCore(world, x, y, z);
	
			if(pos == null)
				return false;

			TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);

			if(!(te instanceof TileEntityOrbitalStationComputer))
				return false;

			((TileEntityOrbitalStationComputer)te).travelTo(destination.body.getBody());
			
			return true;
		}
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		if(!CelestialBody.inOrbit(world)) return;

		List<String> text = new ArrayList<>();

		OrbitalStation station = OrbitalStation.clientStation;
		double progress = station.getUnscaledProgress(0);
		if(progress == 0) return;

		text.add(EnumChatFormatting.AQUA + "Travelling to: " + EnumChatFormatting.RESET + I18nUtil.resolveKey("body." + station.target.name));
		text.add(EnumChatFormatting.AQUA + "Progress: " + EnumChatFormatting.RESET + "" + Math.round(progress * 100) + "%");
	
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}
	
}
