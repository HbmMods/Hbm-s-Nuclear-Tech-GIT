package com.hbm.blocks.machine;

import com.hbm.blocks.ILookOverlay;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityDeuteriumExtractor;
import com.hbm.util.BobMathUtil;
import com.hbm.util.I18nUtil;
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

import java.util.ArrayList;
import java.util.List;

public class MachineDeuteriumExtractor extends BlockContainer implements ILookOverlay {

	public MachineDeuteriumExtractor(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		meta = 0;
		return new TileEntityDeuteriumExtractor();
	}

	@SideOnly(Side.CLIENT)
	private IIcon iconTopH2O;

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":deuterium_extractor_side");
		this.iconTopH2O = iconRegister.registerIcon(RefStrings.MODID + ":deuterium_extractor_top_water");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		if(side == 0 || side == 1) {
			return iconTopH2O;
		} else {
			return blockIcon;
		}
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {

		TileEntity te = world.getTileEntity(x, y, z);

		if(!(te instanceof TileEntityDeuteriumExtractor))
			return;

		TileEntityDeuteriumExtractor extractor = (TileEntityDeuteriumExtractor) te;

		List<String> text = new ArrayList();
		text.add((extractor.power < extractor.getMaxPower() / 20 ? EnumChatFormatting.RED : EnumChatFormatting.GREEN) + "Power: " + BobMathUtil.getShortNumber(extractor.power) + "HE");

		for(int i = 0; i < extractor.tanks.length; i++)
			text.add((i < 1 ? (EnumChatFormatting.GREEN + "-> ") : (EnumChatFormatting.RED + "<- ")) + EnumChatFormatting.RESET + extractor.tanks[i].getTankType().getLocalizedName() + ": " + extractor.tanks[i].getFill() + "/" + extractor.tanks[i].getMaxFill() + "mB");

		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}
}
