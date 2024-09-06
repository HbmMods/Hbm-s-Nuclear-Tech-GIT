package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ILookOverlay;
import com.hbm.dim.CelestialBody;
import com.hbm.dim.SolarSystem;
import com.hbm.dim.orbit.OrbitalStation;
import com.hbm.dim.orbit.OrbitalStation.StationState;
import com.hbm.items.ItemVOTVdrive;
import com.hbm.items.ItemVOTVdrive.Destination;
import com.hbm.tileentity.machine.TileEntityOrbitalStationComputer;
import com.hbm.util.BobMathUtil;
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
		if(!CelestialBody.inOrbit(world)) {
			List<String> text = new ArrayList<String>();
			text.add("&[" + (BobMathUtil.getBlink() ? 0xff0000 : 0xffff00) + "&]! ! ! MUST BE IN ORBIT ! ! !");
			ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
			return;
		}

		OrbitalStation station = OrbitalStation.clientStation;
		double progress = station.getUnscaledProgress(0);
		List<String> text = new ArrayList<>();

		if(station.error != null) {
			text.add(EnumChatFormatting.RED + station.error);
		} else if(progress > 0) {
			if(station.state == StationState.LEAVING) {
				text.add(EnumChatFormatting.AQUA + "Engaging engines for burn to: " + EnumChatFormatting.RESET + I18nUtil.resolveKey("body." + station.target.name));
			} else if(station.state == StationState.ARRIVING) {
				text.add(EnumChatFormatting.AQUA + "Disengaging engines");
			} else {
				text.add(EnumChatFormatting.AQUA + "Travelling to: " + EnumChatFormatting.RESET + I18nUtil.resolveKey("body." + station.target.name));
			}
			text.add(EnumChatFormatting.AQUA + "Progress: " + EnumChatFormatting.RESET + "" + Math.round(progress * 100) + "%");
		}

		if(text.isEmpty()) {
			text.add("Insert a drive to begin journey");
		}
	
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}
	
}
