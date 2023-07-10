package com.hbm.blocks.network;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ILookOverlay;
import com.hbm.inventory.container.ContainerCounterTorch;
import com.hbm.inventory.gui.GUICounterTorch;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.network.TileEntityRadioTorchCounter;
import com.hbm.util.I18nUtil;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class RadioTorchCounter extends RadioTorchBase {
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(!world.isRemote && !player.isSneaking()) {
			FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, x, y, z);
			return true;
		} else {
			return !player.isSneaking();
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityRadioTorchCounter();
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerCounterTorch(player.inventory, (TileEntityRadioTorchCounter) world.getTileEntity(x, y, z));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUICounterTorch(player.inventory, (TileEntityRadioTorchCounter) world.getTileEntity(x, y, z));
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);
		
		if(te instanceof TileEntityRadioTorchCounter) {
			TileEntityRadioTorchCounter radio = (TileEntityRadioTorchCounter) te;
			List<String> text = new ArrayList();
			
			for(int i = 0; i < 3; i++) {
				if(!radio.channel[i].isEmpty()) {
					text.add(EnumChatFormatting.AQUA + "Freq " + (i + 1) + ": " + radio.channel[i]);
					text.add(EnumChatFormatting.RED + "Signal " + (i + 1) + ": " + radio.lastCount[i]);
				}
			}
			
			ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
		}
	}
}
