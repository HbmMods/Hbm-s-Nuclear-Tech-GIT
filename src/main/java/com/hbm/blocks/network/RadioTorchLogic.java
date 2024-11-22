package com.hbm.blocks.network;

import com.hbm.blocks.ILookOverlay;
import com.hbm.inventory.gui.GUIScreenRadioTorchLogic;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.network.TileEntityRadioTorchLogic;
import com.hbm.util.I18nUtil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

import java.util.ArrayList;
import java.util.List;

public class RadioTorchLogic extends RadioTorchRWBase {

	public RadioTorchLogic() {
		super();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":rtty_logic_off");
		this.iconOn = iconRegister.registerIcon(RefStrings.MODID + ":rtty_logic_on");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		TileEntityRadioTorchLogic tile = new TileEntityRadioTorchLogic();
		tile.lastUpdate = world.getTotalWorldTime();
		return tile;
	}

	@Override
	public boolean canProvidePower() {
		return true;
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int side) {

		TileEntity tile = world.getTileEntity(x, y, z);

		if(tile instanceof TileEntityRadioTorchLogic) {
			int state = ((TileEntityRadioTorchLogic) tile).lastState;
			return state;
		}

		return 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void printHook(Pre event, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);

		if(te instanceof TileEntityRadioTorchLogic) {
			TileEntityRadioTorchLogic radio = (TileEntityRadioTorchLogic) te;
			List<String> text = new ArrayList();
			if(radio.channel != null && !radio.channel.isEmpty()) text.add(EnumChatFormatting.AQUA + "Freq: " + radio.channel);
			text.add(EnumChatFormatting.RED + "Signal: " + radio.lastState);
			ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);

		if(te instanceof TileEntityRadioTorchLogic)
			return new GUIScreenRadioTorchLogic((TileEntityRadioTorchLogic) te);

		return null;
	}
}
