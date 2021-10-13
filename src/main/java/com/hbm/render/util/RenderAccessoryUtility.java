package com.hbm.render.util;

import com.hbm.lib.Library;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.render.model.ModelArmorSolstice;
import com.hbm.render.model.ModelArmorWings;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;

public class RenderAccessoryUtility {

<<<<<<< HEAD
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
=======
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
	private static ResourceLocation blaze2 = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeBlaze2.png");
	private static ResourceLocation wiki = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeWiki.png");
	private static ResourceLocation leftnugget = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeLeftNugget.png");
	private static ResourceLocation rightnugget = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeRightNugget.png");
	private static ResourceLocation tankish = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeTankish.png");
	private static ResourceLocation frizzlefrazzle = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeFrizzleFrazzle.png");
>>>>>>> master
	
	public static ResourceLocation getCloakFromPlayer(EntityPlayer player) {
		
		String uuid = player.getUniqueID().toString();
		String name = player.getDisplayName();

<<<<<<< HEAD
		switch (uuid)
		{
		case Library.HbMinecraft:
			return MainRegistry.isPolaroid11 ? hbm : hbm2;
		case Library.Drillgon:
=======
		if(uuid.equals(Library.HbMinecraft)) {
			return (MainRegistry.polaroidID == 11 ? hbm : hbm2);
		}

		if(uuid.equals(Library.Drillgon)) {
>>>>>>> master
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
<<<<<<< HEAD
		case Library.Dr_Nostalgia:
			return MainRegistry.isPolaroid11 ? nostalgia2 : nostalgia;
		case Library.Samino2:
=======
		}
		if(uuid.equals(Library.Dr_Nostalgia)) {
			return (MainRegistry.polaroidID == 11 ? nostalgia2 : nostalgia);
		}
		if(uuid.equals(Library.Samino2)) {
>>>>>>> master
			return sam;
		case Library.Hoboy03new:
			return hoboy;
		case Library.Dragon59MC:
			return master;
		case Library.Steelcourage:
			return mek;
<<<<<<< HEAD
		case Library.ZippySqrl:
=======
		}
		if(uuid.equals(Library.ZippySqrl)) {
>>>>>>> master
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
<<<<<<< HEAD
=======
		if(uuid.equals(Library.Doctor17) || uuid.equals(Library.Doctor17PH)) {
			return doctor17;
		}
		if(uuid.equals(Library.ShimmeringBlaze)) {
			return (MainRegistry.polaroidID == 11 ? blaze2 : shimmeringblaze);
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
		if(uuid.equals(Library.FrizzleFrazzle)) {
			return frizzlefrazzle;
		}
		if(Library.contributors.contains(uuid)) {
			return wiki;
		}
		if(name.startsWith("Player")) {
			return test;
		}
		
		return null;
>>>>>>> master
	}
	
	private static ModelBiped solModel;
	public static void renderSol(RenderPlayerEvent.SetArmorModel event) {

		if(solModel == null)
			solModel = new ModelArmorSolstice();
		
		RenderPlayer renderer = event.renderer;
		ModelBiped model = renderer.modelArmor;
		EntityPlayer player = event.entityPlayer;

		solModel.isSneak = model.isSneak;
		
		float interp = event.partialRenderTick;
		float yawHead = player.prevRotationYawHead + (player.rotationYawHead - player.prevRotationYawHead) * interp;
		float yawOffset = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * interp;
		float yaw = yawHead - yawOffset;
		float yawWrapped = MathHelper.wrapAngleTo180_float(yawHead - yawOffset);
		float pitch = player.rotationPitch;
		
		solModel.render(event.entityPlayer, 0.0F, 0.0F, yawWrapped, yaw, pitch, 0.0625F);
	}
	
	private static ModelBiped wingModel;
	public static void renderWings(RenderPlayerEvent.SetArmorModel event) {

		if(wingModel == null)
			wingModel = new ModelArmorWings(2);
		
		RenderPlayer renderer = event.renderer;
		ModelBiped model = renderer.modelArmor;
		EntityPlayer player = event.entityPlayer;

		wingModel.isSneak = model.isSneak;
		
		float interp = event.partialRenderTick;
		float yawHead = player.prevRotationYawHead + (player.rotationYawHead - player.prevRotationYawHead) * interp;
		float yawOffset = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * interp;
		float yaw = yawHead - yawOffset;
		float yawWrapped = MathHelper.wrapAngleTo180_float(yawHead - yawOffset);
		float pitch = player.rotationPitch;
		
		wingModel.render(event.entityPlayer, 0.0F, 0.0F, yawWrapped, yaw, pitch, 0.0625F);
	}
}
