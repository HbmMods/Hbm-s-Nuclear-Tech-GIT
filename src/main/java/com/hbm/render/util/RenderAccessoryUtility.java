package com.hbm.render.util;

import com.hbm.lib.Library;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class RenderAccessoryUtility {

	private static final ResourceLocation hbm = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeHbm3.png");
	private static final ResourceLocation hbm2 = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeHbm2.png");
	private static final ResourceLocation drillgon = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeDrillgon.png");
	private static final ResourceLocation dafnik = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeDafnik.png");
	private static final ResourceLocation lpkukin = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeShield.png");
	private static final ResourceLocation vertice = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeVertice_2.png");
	private static final ResourceLocation red = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeRed.png");
	private static final ResourceLocation ayy = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeAyy.png");
	private static final ResourceLocation nostalgia = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeNostalgia.png");
	private static final ResourceLocation nostalgia2 = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeNostalgia2.png");
	private static final ResourceLocation sam = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeSam.png");
	private static final ResourceLocation hoboy = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeHoboy.png");
	private static final ResourceLocation master = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeMaster.png");
	private static final ResourceLocation mek = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeMek.png");
	private static final ResourceLocation zippy = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeZippySqrl.png");
	private static final ResourceLocation test = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeTest.png");
	private static final ResourceLocation schrabby = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeSchrabbyAlt.png");
	private static final ResourceLocation swiggs = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeSweatySwiggs.png");
	private static final ResourceLocation doctor17 = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeDoctor17.png");
	private static final ResourceLocation shimmeringblaze = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeBlaze.png");
	private static final ResourceLocation wiki = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeWiki.png");
	private static final ResourceLocation leftnugget = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeLeftNugget.png");
	private static final ResourceLocation rightnugget = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeRightNugget.png");
	private static final ResourceLocation tankish = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeTankish.png");
	
	public static ResourceLocation getCloakFromPlayer(EntityPlayer player) {
		
		String uuid = player.getUniqueID().toString();
		String name = player.getDisplayName();

		switch (uuid)
		{
		case Library.HbMinecraft:
			return MainRegistry.isPolaroid11 ? hbm : hbm2;
		case Library.Drillgon:
			return drillgon;
		case Library.Dafnik:
			return dafnik;
		case Library.LPkukin:
			return lpkukin;
		case Library.LordVertice:
			return vertice;
		case Library.CodeRed_:
			return red;
		case Library.dxmaster769:
			return ayy;
		case Library.Dr_Nostalgia:
			return MainRegistry.isPolaroid11 ? nostalgia2 : nostalgia;
		case Library.Samino2:
			return sam;
		case Library.Hoboy03new:
			return hoboy;
		case Library.Dragon59MC:
			return master;
		case Library.Steelcourage:
			return mek;
		case Library.ZippySqrl:
			return zippy;
		case Library.Schrabby:
			return schrabby;
		case Library.SweatySwiggs:
			return swiggs;
		case Library.Doctor17:
		case Library.Doctor17PH:
			return doctor17;
		case Library.ShimmeringBlaze:
			return shimmeringblaze;
		case Library.FifeMiner:
			return leftnugget;
		case Library.lag_add:
			return rightnugget;
		case Library.Tankish:
			return tankish;
		default:
			if (Library.contributors.contains(uuid))
				return wiki;
			else if (name.startsWith("Player"))
				return test;
			else
				return null;
		}
	}

}
