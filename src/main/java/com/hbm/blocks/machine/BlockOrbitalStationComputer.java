package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ILookOverlay;
import com.hbm.dim.CelestialBody;
import com.hbm.dim.SolarSystem;
import com.hbm.dim.orbit.OrbitalStation;
import com.hbm.dim.orbit.OrbitalStation.StationState;
import com.hbm.handler.ThreeInts;
import com.hbm.items.ItemVOTVdrive;
import com.hbm.items.ItemVOTVdrive.Destination;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityOrbitalStationComputer;
import com.hbm.util.BobMathUtil;
import com.hbm.util.I18nUtil;

import api.hbm.tile.IPropulsion;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
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

		if(world.isRemote) {
			return true;
		} else {
			int[] pos = this.findCore(world, x, y, z);
	
			if(pos == null)
				return false;

			TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);

			if(!(te instanceof TileEntityOrbitalStationComputer))
				return false;

			TileEntityOrbitalStationComputer computer = (TileEntityOrbitalStationComputer) te;

			if(computer.isTravelling())
				return false;

			ItemStack heldStack = player.getHeldItem();

			if(heldStack != null && heldStack.getItem() instanceof ItemVOTVdrive) {
				if(computer.slots[0] != null)
					return false;

				Destination destination = ItemVOTVdrive.getDestination(heldStack);
		
				if(destination.body == SolarSystem.Body.ORBIT)
					return false;

				if(computer.travelTo(destination.body.getBody(), heldStack.copy())) {
					heldStack.stackSize = 0;
					world.playSoundEffect(x, y, z, "hbm:item.upgradePlug", 1.0F, 1.0F);
				} else {
					return false;
				}
			} else if(heldStack == null && computer.slots[0] != null) {
				if(!player.inventory.addItemStackToInventory(computer.slots[0].copy())) {
					player.dropPlayerItemWithRandomChoice(computer.slots[0].copy(), false);
				}
				computer.slots[0] = null;
				computer.markChanged();
			} else {
				FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, pos[0], pos[1], pos[2]);
			}

			
			return true;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void printHook(Pre event, World world, int x, int y, int z) {
		if(!CelestialBody.inOrbit(world)) {
			List<String> text = new ArrayList<String>();
			text.add("&[" + (BobMathUtil.getBlink() ? 0xff0000 : 0xffff00) + "&]! ! ! " + I18nUtil.resolveKey("atmosphere.noOrbit") + " ! ! !");
			ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
			return;
		}

		int[] pos = this.findCore(world, x, y, z);

		if(pos == null) return;

		TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);

		if(!(te instanceof TileEntityOrbitalStationComputer)) return;

		TileEntityOrbitalStationComputer computer = (TileEntityOrbitalStationComputer) te;

		OrbitalStation station = OrbitalStation.clientStation;
		double progress = station.getUnscaledProgress(0);
		List<String> text = new ArrayList<>();

		if(!station.hasEngines) {
			text.add(EnumChatFormatting.RED + "No engines available");
		} else if(station.errorsAt.size() > 0) {
			for(ThreeInts errorAt : station.errorsAt) {
				TileEntity error = world.getTileEntity(errorAt.x, errorAt.y, errorAt.z);
				if(error == null || !(error instanceof IPropulsion)) continue;
				((IPropulsion) error).addErrors(text);
			}
		} else if(progress > 0) {
			if(station.state == StationState.LEAVING) {
				text.add(EnumChatFormatting.AQUA + I18nUtil.resolveKey("station.engage") + ": " + EnumChatFormatting.RESET + I18nUtil.resolveKey("body." + station.target.name));
			} else if(station.state == StationState.ARRIVING) {
				text.add(EnumChatFormatting.AQUA + I18nUtil.resolveKey("station.disengage"));
			} else {
				text.add(EnumChatFormatting.AQUA + I18nUtil.resolveKey("station.travelling") + ": " + EnumChatFormatting.RESET + I18nUtil.resolveKey("body." + station.target.name));
			}
			text.add(EnumChatFormatting.AQUA + I18nUtil.resolveKey("station.progress") + ": " + EnumChatFormatting.RESET + "" + Math.round(progress * 100) + "%");
		} else if(computer.hasDrive) {
			if(Minecraft.getMinecraft().thePlayer.getHeldItem() != null) {
				text.add(I18nUtil.resolveKey("station.removeDrive"));
			} else {
				text.add(I18nUtil.resolveKey("station.interactDrive"));
			}
		} else {
			text.add(I18nUtil.resolveKey("station.insertDrive"));
		}
	
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}
	
}
