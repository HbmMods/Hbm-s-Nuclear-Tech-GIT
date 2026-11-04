package com.hbm.blocks.network;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ILookOverlay;
import com.hbm.inventory.gui.GUIScreenRadioTorchRelay;
import com.hbm.tileentity.network.TileEntityRadioTorchRelay;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.util.ForgeDirection;

public class RadioTorchRelay extends RadioTorchBase {

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityRadioTorchRelay();
	}

	@Override
	public boolean canBlockStay(World world, int x, int y, int z, ForgeDirection dir, Block b) {
		return b.isSideSolid(world, x - dir.offsetX, y - dir.offsetY, z - dir.offsetZ, dir) || (b.renderAsNormalBlock() && !b.isAir(world, x, y, z));
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);

		if(te instanceof TileEntityRadioTorchRelay) {
			TileEntityRadioTorchRelay radio = (TileEntityRadioTorchRelay) te;
			List<String> text = new ArrayList();
			if(radio.channel != null && !radio.channel.isEmpty()) text.add(EnumChatFormatting.AQUA + "Freq: " + radio.channel);
			text.add(EnumChatFormatting.RED + "Signal: " + radio.lastState);
			ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
		}
	}

	@Override
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIScreenRadioTorchRelay((TileEntityRadioTorchRelay) world.getTileEntity(x, y, z));
	}
}
