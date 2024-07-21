package com.hbm.render.util;

import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.render.model.ModelArmorTailPeep;
import com.hbm.render.model.ModelArmorWings;
import com.hbm.render.model.ModelArmorWingsPheo;
import com.hbm.util.ShadyUtil;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;

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
	private static ResourceLocation hoboy = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeHoboy_mk3.png");
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
	//private static ResourceLocation pheo = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapePheo.png");
	private static ResourceLocation vaer = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeVaer.png");
	private static ResourceLocation adam = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeAdam.png");
	private static ResourceLocation gwen = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeGwen.png");

	private static ResourceLocation alcater = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeAlcater.png");
	private static ResourceLocation jame = new ResourceLocation(RefStrings.MODID + ":textures/models/capes/CapeJame.png");
	
	public static ResourceLocation getCloakFromPlayer(EntityPlayer player) {
		
		String uuid = player.getUniqueID().toString();
		String name = player.getDisplayName();

		if(uuid.equals(ShadyUtil.HbMinecraft)) {
			return (MainRegistry.polaroidID == 11 ? hbm2 : hbm);
		}

		if(uuid.equals(ShadyUtil.Drillgon)) {
			return drillgon;
		}
		if(uuid.equals(ShadyUtil.Dafnik)) {
			return dafnik;
		}
		if(uuid.equals(ShadyUtil.LPkukin)) {
			return lpkukin;
		}
		if(uuid.equals(ShadyUtil.LordVertice)) {
			return vertice;
		}
		if(uuid.equals(ShadyUtil.CodeRed_)) {
			return red;
		}
		if(uuid.equals(ShadyUtil.dxmaster769)) {
			return ayy;
		}
		if(uuid.equals(ShadyUtil.Dr_Nostalgia)) {
			return (MainRegistry.polaroidID == 11 ? nostalgia2 : nostalgia);
		}
		if(uuid.equals(ShadyUtil.Samino2)) {
			return sam;
		}
		if(uuid.equals(ShadyUtil.Hoboy03new)) {
			return hoboy;
		}
		if(uuid.equals(ShadyUtil.Dragon59MC)) {
			return master;
		}
		if(uuid.equals(ShadyUtil.Steelcourage)) {
			return mek;
		}
		if(uuid.equals(ShadyUtil.ZippySqrl)) {
			return zippy;
		}
		if(uuid.equals(ShadyUtil.Schrabby)) {
			return schrabby;
		}
		if(uuid.equals(ShadyUtil.SweatySwiggs)) {
			return swiggs;
		}
		if(uuid.equals(ShadyUtil.Doctor17) || uuid.equals(ShadyUtil.Doctor17PH)) {
			return doctor17;
		}
		if(uuid.equals(ShadyUtil.ShimmeringBlaze)) {
			return (MainRegistry.polaroidID == 11 ? blaze2 : shimmeringblaze);
		}
		if(uuid.equals(ShadyUtil.FifeMiner)) {
			return leftnugget;
		}
		if(uuid.equals(ShadyUtil.lag_add)) {
			return rightnugget;
		}
		if(uuid.equals(ShadyUtil.Tankish)) {
			return tankish;
		}
		if(uuid.equals(ShadyUtil.FrizzleFrazzle)) {
			return frizzlefrazzle;
		}
		/*if(uuid.equals(ShadyUtil.Barnaby99_x)) {
			return pheo;
		}*/
		if(uuid.equals(ShadyUtil.Ma118)) {
			return vaer;
		}
		if(uuid.equals(ShadyUtil.Adam29Adam29)) {
			return adam;
		}
		if(uuid.equals(ShadyUtil.Alcater)) {
			return alcater;
		}
		if(uuid.equals(ShadyUtil.ege444)) {
			return jame;
		}
		if(ShadyUtil.contributors.contains(uuid)) {
			return wiki;
		}
		if(uuid.equals(ShadyUtil.DUODEC_)) {
			return gwen;
		}
		
		return null;
	}
	
	private static ModelBiped[] wingModels = new ModelBiped[10];
	public static void renderWings(RenderPlayerEvent.SetArmorModel event, int mode) {

		if(wingModels[mode] == null)
			wingModels[mode] = new ModelArmorWings(mode);
		
		RenderPlayer renderer = event.renderer;
		ModelBiped model = renderer.modelArmor;
		EntityPlayer player = event.entityPlayer;

		wingModels[mode].isSneak = model.isSneak;
		
		float interp = event.partialRenderTick;
		float yawHead = player.prevRotationYawHead + (player.rotationYawHead - player.prevRotationYawHead) * interp;
		float yawOffset = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * interp;
		float yaw = yawHead - yawOffset;
		float yawWrapped = MathHelper.wrapAngleTo180_float(yawHead - yawOffset);
		float pitch = player.rotationPitch;
		
		wingModels[mode].render(event.entityPlayer, 0.0F, 0.0F, yawWrapped, yaw, pitch, 0.0625F);
	}
	
	private static ModelBiped axePackModel;
	public static void renderAxePack(RenderPlayerEvent.SetArmorModel event) {

		if(axePackModel == null)
			axePackModel = new ModelArmorWingsPheo();
		
		RenderPlayer renderer = event.renderer;
		ModelBiped model = renderer.modelArmor;
		EntityPlayer player = event.entityPlayer;

		axePackModel.isSneak = model.isSneak;
		
		float interp = event.partialRenderTick;
		float yawHead = player.prevRotationYawHead + (player.rotationYawHead - player.prevRotationYawHead) * interp;
		float yawOffset = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * interp;
		float yaw = yawHead - yawOffset;
		float yawWrapped = MathHelper.wrapAngleTo180_float(yawHead - yawOffset);
		float pitch = player.rotationPitch;
		
		axePackModel.render(event.entityPlayer, 0.0F, 0.0F, yawWrapped, yaw, pitch, 0.0625F);
	}
	
	private static ModelBiped tailModel;
	public static void renderFaggot(RenderPlayerEvent.SetArmorModel event) {

		if(tailModel == null)
			tailModel = new ModelArmorTailPeep();
		
		RenderPlayer renderer = event.renderer;
		ModelBiped model = renderer.modelArmor;
		EntityPlayer player = event.entityPlayer;

		tailModel.isSneak = model.isSneak;
		
		float interp = event.partialRenderTick;
		float yawHead = player.prevRotationYawHead + (player.rotationYawHead - player.prevRotationYawHead) * interp;
		float yawOffset = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * interp;
		float yaw = yawHead - yawOffset;
		float yawWrapped = MathHelper.wrapAngleTo180_float(yawHead - yawOffset);
		float pitch = player.rotationPitch;
		
		tailModel.render(event.entityPlayer, 0.0F, 0.0F, yawWrapped, yaw, pitch, 0.0625F);
	}
}
