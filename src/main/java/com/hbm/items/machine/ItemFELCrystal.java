package com.hbm.items.machine;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.util.I18nUtil;

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
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		String desc = (stack.getItem() == ModItems.laser_crystal_digamma) ? (EnumChatFormatting.OBFUSCATED + "THERADIANCEOFATHOUSANDSUNS") : (this.getUnlocalizedNameInefficiently(stack) + ".desc");
		list.add(I18nUtil.resolveKey(desc));
		list.add(wavelength.name + " - " + this.wavelength.wavelengthRange);
	}
			
	public static enum EnumWavelengths{
		NULL("la creatura", "6 dollar", 0x010101, 0x010101),
			
		IR(EnumChatFormatting.RED + I18nUtil.resolveKey("wavelengths.name.ir"), EnumChatFormatting.RED + I18nUtil.resolveKey("wavelengths.waveRange.ir"), 0xBB1010, 0xCC4040),
		VISIBLE(EnumChatFormatting.GREEN + I18nUtil.resolveKey("wavelengths.name.visible"), EnumChatFormatting.GREEN + I18nUtil.resolveKey("wavelengths.waveRange.visible"), 0, 0),
		UV(EnumChatFormatting.AQUA + I18nUtil.resolveKey("wavelengths.name.uv"), EnumChatFormatting.AQUA + I18nUtil.resolveKey("wavelengths.waveRange.uv"), 0x0A1FC4, 0x00EFFF),
		GAMMA(EnumChatFormatting.LIGHT_PURPLE + I18nUtil.resolveKey("wavelengths.name.gamma"), EnumChatFormatting.LIGHT_PURPLE + I18nUtil.resolveKey("wavelengths.waveRange.gamma"), 0x150560, 0xEF00FF),
		DRX(EnumChatFormatting.DARK_RED + I18nUtil.resolveKey("wavelengths.name.drx"), EnumChatFormatting.DARK_RED + I18nUtil.resolveKey("wavelengths.waveRange.drx"), 0xFF0000, 0xFF0000);
		
		public String name = "";
		public String wavelengthRange = "";
		public int color;
		public int guiColor;
		
		private EnumWavelengths(String name, String wavelength, int color, int guiColor) {
			this.name = name;
			this.wavelengthRange = wavelength;
			this.color = color;
			this.guiColor = guiColor;
		}
	}
}
