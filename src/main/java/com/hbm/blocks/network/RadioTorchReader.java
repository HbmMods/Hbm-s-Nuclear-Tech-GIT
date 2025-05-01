package com.hbm.blocks.network;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ILookOverlay;
import com.hbm.inventory.gui.GUIScreenRadioTorchReader;
import com.hbm.tileentity.network.TileEntityRadioTorchReader;
import com.hbm.util.Compat;
import com.hbm.util.I18nUtil;

import api.hbm.redstoneoverradio.IRORValueProvider;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.util.ForgeDirection;

public class RadioTorchReader extends RadioTorchBase {

	public RadioTorchReader() {
		super();
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityRadioTorchReader();
	}

	@Override
	public boolean canBlockStay(World world, int x, int y, int z, ForgeDirection dir, Block b) {
		TileEntity tile = Compat.getTileStandard(world, x - dir.offsetX, y - dir.offsetY, z - dir.offsetZ);
		return tile instanceof IRORValueProvider;
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);

		if(te instanceof TileEntityRadioTorchReader) {
			TileEntityRadioTorchReader radio = (TileEntityRadioTorchReader) te;
			List<String> text = new ArrayList();
			for(int i = 0; i < 8; i++) {
				if(radio.channels[i] == null || radio.channels[i].isEmpty()) continue;
				if(radio.names[i] == null || radio.names[i].isEmpty()) continue;
				text.add(EnumChatFormatting.AQUA + radio.channels[i] + ": " + radio.names[i]);
			}
			ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
		}
	}

	@Override public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) { return null; }

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);
		if(te instanceof TileEntityRadioTorchReader) return new GUIScreenRadioTorchReader((TileEntityRadioTorchReader) te);
		return null;
	}
}
