package com.hbm.blocks.network;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ILookOverlay;
import com.hbm.inventory.gui.GUIScreenRadioTorchController;
import com.hbm.tileentity.network.TileEntityRadioTorchController;
import com.hbm.util.Compat;
import com.hbm.util.i18n.I18nUtil;

import api.hbm.redstoneoverradio.IRORInteractive;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.util.ForgeDirection;

public class RadioTorchController extends RadioTorchBase {

	public RadioTorchController() {
		super();
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityRadioTorchController();
	}

	@Override
	public boolean canBlockStay(World world, int x, int y, int z, ForgeDirection dir, Block b) {
		TileEntity tile = Compat.getTileStandard(world, x - dir.offsetX, y - dir.offsetY, z - dir.offsetZ);
		return tile instanceof IRORInteractive;
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);

		if(te instanceof TileEntityRadioTorchController) {
			TileEntityRadioTorchController radio = (TileEntityRadioTorchController) te;
			List<String> text = new ArrayList();
			text.add(EnumChatFormatting.AQUA + "Freq: " + radio.channel);
			ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);
		if(te instanceof TileEntityRadioTorchController) return new GUIScreenRadioTorchController((TileEntityRadioTorchController) te);
		return null;
	}
}
