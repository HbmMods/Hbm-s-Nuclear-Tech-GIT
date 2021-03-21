package com.hbm.render.util;

import com.hbm.lib.Library;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class RenderAccessoryUtility {

	private static ResourceLocation hbm = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeHbm3.png");
	private static ResourceLocation hbm2 = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeHbm2.png");
	private static ResourceLocation drillgon = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeDrillgon.png");
	private static ResourceLocation dafnik = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeDafnik.png");
	private static ResourceLocation lpkukin = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeShield.png");
	private static ResourceLocation vertice = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeVertice_2.png");
	private static ResourceLocation red = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeRed.png");
	private static ResourceLocation ayy = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeAyy.png");
	private static ResourceLocation nostalgia = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeNostalgia.png");
	private static ResourceLocation nostalgia2 = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeNostalgia2.png");
	private static ResourceLocation sam = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeSam.png");
	private static ResourceLocation hoboy = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeHoboy.png");
	private static ResourceLocation master = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeMaster.png");
	private static ResourceLocation mek = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeMek.png");
	private static ResourceLocation zippy = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeZippySqrl.png");
	private static ResourceLocation test = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeTest.png");
	private static ResourceLocation schrabby = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeSchrabbyAlt.png");
	private static ResourceLocation swiggs = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeSweatySwiggs.png");
	private static ResourceLocation doctor17 = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeDoctor17.png");
	private static ResourceLocation shimmeringblaze = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeBlaze.png");
	private static ResourceLocation wiki = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeWiki.png");
	private static ResourceLocation leftnugget = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeLeftNugget.png");
	private static ResourceLocation rightnugget = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeRightNugget.png");
	private static ResourceLocation tankish = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeTankish.png");
	
	public static ResourceLocation getCloakFromPlayer(EntityPlayer player) {
		
		String uuid = player.getUniqueID().toString();
		String name = player.getDisplayName();

		if(uuid.equals(Library.HbMinecraft)) {
			
			if(MainRegistry.polaroidID == 11)
				return hbm;
			else
				return hbm2;
		}

		if(uuid.equals(Library.Drillgon)) {
			return drillgon;
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
		if(uuid.equals(Library.Dr_Nostalgia)) {
			
			if(MainRegistry.polaroidID == 11)
				return nostalgia2;
			else
				return nostalgia;
		}
		if(uuid.equals(Library.Samino2)) {
			return sam;
		}
		if(uuid.equals(Library.Hoboy03new)) {
			return hoboy;
		}
		if(uuid.equals(Library.Dragon59MC)) {
			return master;
		}
		if(uuid.equals(Library.Steelcourage)) {
			return mek;
		}
		if(uuid.equals(Library.ZippySqrl)) {
			return zippy;
		}
		if(uuid.equals(Library.Schrabby)) {
			return schrabby;
		}
		if(uuid.equals(Library.SweatySwiggs)) {
			return swiggs;
		}
		if(uuid.equals(Library.Doctor17) || uuid.equals(Library.Doctor17PH)) {
			return doctor17;
		}
		if(uuid.equals(Library.ShimmeringBlaze)) {
			return shimmeringblaze;
		}
		if(uuid.equals(Library.FifeMiner)) {
			return leftnugget;
		}
		if(uuid.equals(Library.lag_add)) {
			return rightnugget;
		}
		if(uuid.equals(Library.Tankish)) {
			return tankish;
		}
		if(Library.contributors.contains(uuid)) {
			return wiki;
		}
		if(name.startsWith("Player")) {
			return test;
		}
		
		return null;
	}

}
