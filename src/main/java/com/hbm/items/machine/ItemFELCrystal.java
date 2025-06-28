package com.hbm.items.machine;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.util.i18n.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemFELCrystal extends Item {

	public EnumWavelengths wavelength = EnumWavelengths.NULL;

	public ItemFELCrystal(EnumWavelengths wavelength)
	{
		this.wavelength = wavelength;
		this.setMaxStackSize(1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		String desc = (stack.getItem() == ModItems.laser_crystal_digamma) ? (EnumChatFormatting.OBFUSCATED + "THERADIANCEOFATHOUSANDSUNS") : (this.getUnlocalizedNameInefficiently(stack) + ".desc");
		list.add(I18nUtil.resolveKey(desc));
		list.add(wavelength.textColor + I18nUtil.resolveKey(wavelength.name) + " - " + wavelength.textColor + I18nUtil.resolveKey(this.wavelength.wavelengthRange));
	}

	public static enum EnumWavelengths{
		NULL("la creatura", "6 dollar", 0x010101, 0x010101, EnumChatFormatting.WHITE), //why do you exist?

		IR("wavelengths.name.ir", "wavelengths.waveRange.ir", 0xBB1010, 0xCC4040, EnumChatFormatting.RED),
		VISIBLE("wavelengths.name.visible", "wavelengths.waveRange.visible", 0, 0, EnumChatFormatting.GREEN),
		UV("wavelengths.name.uv", "wavelengths.waveRange.uv", 0x0A1FC4, 0x00EFFF, EnumChatFormatting.AQUA),
		GAMMA("wavelengths.name.gamma", "wavelengths.waveRange.gamma", 0x150560, 0xEF00FF, EnumChatFormatting.LIGHT_PURPLE),
		DRX("wavelengths.name.drx", "wavelengths.waveRange.drx", 0xFF0000, 0xFF0000, EnumChatFormatting.DARK_RED);

		public String name = "";
		public String wavelengthRange = "";
		public int renderedBeamColor;
		public int guiColor;
		public EnumChatFormatting textColor;

		private EnumWavelengths(String name, String wavelength, int color, int guiColor, EnumChatFormatting textColor) {
			this.name = name;
			this.wavelengthRange = wavelength;
			this.renderedBeamColor = color;
			this.guiColor = guiColor;
			this.textColor = textColor;
		}
	}
}