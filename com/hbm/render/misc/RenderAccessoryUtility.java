package com.hbm.render.misc;

import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class RenderAccessoryUtility {

	private static ResourceLocation hbm = new ResourceLocation(RefStrings.MODID + ":textures/models/CapeHbm.png");
	private static ResourceLocation hbm2 = new ResourceLocation(RefStrings.MODID + ":textures/models/CapeHbm2.png");
	private static ResourceLocation dafnik = new ResourceLocation(RefStrings.MODID + ":textures/models/CapeDafnik.png");
	private static ResourceLocation lpkukin = new ResourceLocation(RefStrings.MODID + ":textures/models/CapeShield.png");
	private static ResourceLocation vertice = new ResourceLocation(RefStrings.MODID + ":textures/models/CapeVertice_2.png");
	private static ResourceLocation red = new ResourceLocation(RefStrings.MODID + ":textures/models/CapeRed.png");
	private static ResourceLocation ayy = new ResourceLocation(RefStrings.MODID + ":textures/models/CapeAyy.png");
	private static ResourceLocation nostalgia = new ResourceLocation(RefStrings.MODID + ":textures/models/CapeNostalgia.png");
	private static ResourceLocation sam = new ResourceLocation(RefStrings.MODID + ":textures/models/CapeSam.png");
	private static ResourceLocation test = new ResourceLocation(RefStrings.MODID + ":textures/models/CapeTest.png");
	
	public static ResourceLocation getCloakFromPlayer(EntityPlayer player) {
		
		String uuid = player.getUniqueID().toString();
		String name = player.getDisplayName();

		if(uuid.equals(Library.HbMinecraft)) {
			
			if(MainRegistry.polaroidID == 11)
				return hbm;
			else
				return hbm2;
		}
		if(uuid.equals(Library.Dafnik)) {
			return dafnik;
		}
		if(uuid.equals(Library.LPkukin)) {
			return lpkukin;
		}
		if(uuid.equals(Library.LordVertice)) {
			return vertice;
		}
		if(uuid.equals(Library.CodeRed_)) {
			return red;
		}
		if(uuid.equals(Library.dxmaster769)) {
			return ayy;
		}
		if(uuid.equals(Library.nostalgia)) {
			return nostalgia;
		}
		if(uuid.equals(Library.samino2)) {
			return sam;
		}
		if(name.startsWith("Player")) {
			return test;
		}
		
		return null;
	}

}
