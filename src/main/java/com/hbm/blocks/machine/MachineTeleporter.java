package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.hbm.blocks.ILookOverlay;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineTeleporter;
import com.hbm.util.i18n.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class MachineTeleporter extends BlockContainer implements ILookOverlay {

	@SideOnly(Side.CLIENT) private IIcon iconTop;
	@SideOnly(Side.CLIENT) private IIcon iconBottom;

	public MachineTeleporter(Material mat) {
		super(mat);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":teleporter_top");
		this.iconBottom = iconRegister.registerIcon(RefStrings.MODID + ":teleporter_bottom");
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":teleporter_side");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return side == 1 ? this.iconTop : (side == 0 ? this.iconBottom : this.blockIcon);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityMachineTeleporter();
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		
		TileEntity tile = world.getTileEntity(x, y, z);
		
		if(!(tile instanceof TileEntityMachineTeleporter)) return;
		
		TileEntityMachineTeleporter tele = (TileEntityMachineTeleporter) tile;
		
		List<String> text = new ArrayList();
		
		if(tele.targetY == -1) {
			text.add(EnumChatFormatting.RED + "No destination set!");
		} else {
			text.add((tele.power >= tele.consumption ? EnumChatFormatting.GREEN : EnumChatFormatting.RED) + String.format(Locale.US, "%,d", tele.power) + " / " + String.format(Locale.US, "%,d", tele.maxPower));
			text.add("Destination: " + tele.targetX + " / " + tele.targetY + " / " + tele.targetZ + " (D: " + tele.targetDim + ")");
		}
		
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}
}
