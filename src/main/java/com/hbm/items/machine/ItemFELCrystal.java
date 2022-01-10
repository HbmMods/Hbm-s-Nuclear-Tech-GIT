package com.hbm.items.machine;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.util.I18nUtil;

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
		list.add(I18nUtil.resolveKey(wavelength.name) + " - " + I18nUtil.resolveKey(this.wavelength.wavelengthRange));
	}
			
	public static enum EnumWavelengths{
		NULL("la creatura", "6 dollar", 0x010101, 0x010101),
			
		IR(EnumChatFormatting.RED + "wavelengths.name.ir", EnumChatFormatting.RED + "wavelengths.waveRange.ir", 0xBB1010, 0xCC4040),
		VISIBLE(EnumChatFormatting.GREEN + "wavelengths.name.visible", EnumChatFormatting.GREEN + "wavelengths.waveRange.visible", 0, 0),
		UV(EnumChatFormatting.AQUA + "wavelengths.name.uv", EnumChatFormatting.AQUA + "wavelengths.waveRange.uv", 0x0A1FC4, 0x00EFFF),
		GAMMA(EnumChatFormatting.LIGHT_PURPLE + "wavelengths.name.gamma", EnumChatFormatting.LIGHT_PURPLE + "wavelengths.waveRange.gamma", 0x150560, 0xEF00FF),
		DRX(EnumChatFormatting.DARK_RED + "wavelengths.name.drx", EnumChatFormatting.DARK_RED + "wavelengths.waveRange.drx", 0xFF0000, 0xFF0000);
		
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
