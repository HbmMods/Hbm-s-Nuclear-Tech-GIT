package com.hbm.util;

import com.hbm.entity.mob.EntityNuclearCreeper;
import com.hbm.entity.mob.EntityQuackos;
import com.hbm.extprop.HbmLivingProps;
import com.hbm.handler.HazmatRegistry;
import com.hbm.lib.ModDamageSource;
import com.hbm.potion.HbmPotion;
import com.hbm.saveddata.RadiationSavedData;

import api.hbm.entity.IRadiationImmune;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class ContaminationUtil {
	
	/**
	 * Calculates how much radiation can be applied to this entity by calculating resistance
	 * @param entity
	 * @return
	 */
	public static float calculateRadiationMod(EntityLivingBase entity) {
		
		if(entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)entity;
			
			float koeff = 10.0F;
			return (float) Math.pow(koeff, -HazmatRegistry.getResistance(player));
		}
		
		return 1;
	}
	
	/// RADIATION ///
	public static void applyRadData(Entity e, float f) {

		if(!(e instanceof EntityLivingBase))
			return;

		if(isRadImmune(e))
			return;
		
		if(e instanceof EntityPlayer && ((EntityPlayer)e).capabilities.isCreativeMode)
			return;
		
		if(e instanceof EntityPlayer && e.ticksExisted < 200)
			return;
		
		EntityLivingBase entity = (EntityLivingBase)e;
		
		f *= calculateRadiationMod(entity);

		HbmLivingProps.incrementRadiation(entity, f);
	}
	
	public static void applyRadDirect(Entity e, float f) {

		if(!(e instanceof EntityLivingBase))
			return;

		if(isRadImmune(e))
			return;
		
		if(e instanceof EntityPlayer && ((EntityPlayer)e).capabilities.isCreativeMode)
			return;
		
		EntityLivingBase entity = (EntityLivingBase)e;

		HbmLivingProps.incrementRadiation(entity, f);
	}
	
	public static float getRads(Entity e) {

		if(!(e instanceof EntityLivingBase))
			return 0.0F;

		if(isRadImmune(e))
			return 0.0F;
		
		EntityLivingBase entity = (EntityLivingBase)e;
		
		return HbmLivingProps.getRadiation(entity);
	}
	
	public static boolean isRadImmune(Entity e) {

		if(e instanceof EntityLivingBase && ((EntityLivingBase)e).isPotionActive(HbmPotion.mutation))
			return true;
		
		return e instanceof EntityNuclearCreeper ||
				e instanceof EntityMooshroom ||
				e instanceof EntityZombie ||
				e instanceof EntitySkeleton ||
				e instanceof EntityQuackos ||
				e instanceof EntityOcelot ||
				e instanceof IRadiationImmune;
	}
	
	/// ASBESTOS ///
	public static void applyAsbestos(Entity e, int i) {

		if(!(e instanceof EntityLivingBase))
			return;
		
		if(e instanceof EntityPlayer && ((EntityPlayer)e).capabilities.isCreativeMode)
			return;
		
		if(e instanceof EntityPlayer && e.ticksExisted < 200)
			return;
		
		EntityLivingBase entity = (EntityLivingBase)e;
		
		if(!(entity instanceof EntityPlayer && ArmorUtil.checkForGasMask((EntityPlayer) entity)))
			HbmLivingProps.incrementAsbestos(entity, i);
	}
	
	/// DIGAMMA ///
	public static void applyDigammaData(Entity e, float f) {

		if(!(e instanceof EntityLivingBase))
			return;
		
		if(e instanceof EntityPlayer && ((EntityPlayer)e).capabilities.isCreativeMode)
			return;
		
		if(e instanceof EntityPlayer && e.ticksExisted < 200)
			return;
		
		EntityLivingBase entity = (EntityLivingBase)e;
		
		if(entity.isPotionActive(HbmPotion.stability.id))
			return;
		
		if(!(entity instanceof EntityPlayer && ArmorUtil.checkForDigamma((EntityPlayer) entity)))
			HbmLivingProps.incrementDigamma(entity, f);
	}
	
	public static void applyDigammaDirect(Entity e, float f) {

		if(!(e instanceof EntityLivingBase))
			return;

		if(e instanceof IRadiationImmune)
			return;
		
		if(e instanceof EntityPlayer && ((EntityPlayer)e).capabilities.isCreativeMode)
			return;
		
		EntityLivingBase entity = (EntityLivingBase)e;
		HbmLivingProps.incrementDigamma(entity, f);
	}
	
	public static float getDigamma(Entity e) {

		if(!(e instanceof EntityLivingBase))
			return 0.0F;
		
		EntityLivingBase entity = (EntityLivingBase)e;
		return HbmLivingProps.getDigamma(entity);
	}
	
	public static void printGeigerData(EntityPlayer player) {

		World world = player.worldObj;

		double eRad = ((int)(HbmLivingProps.getRadiation(player) * 10)) / 10D;

		RadiationSavedData data = RadiationSavedData.getData(player.worldObj);
		Chunk chunk = world.getChunkFromBlockCoords((int)player.posX, (int)player.posZ);
		double rads = ((int)(data.getRadNumFromCoord(chunk.xPosition, chunk.zPosition) * 10)) / 10D;
		
		double res = 100.0D - ((int)(ContaminationUtil.calculateRadiationMod(player) * 10000)) / 100D;
		double resKoeff = ((int)(HazmatRegistry.getResistance(player) * 100)) / 100D;
		
		String chunkPrefix = "";
		String radPrefix = "";
		String resPrefix = "" + EnumChatFormatting.WHITE;
		
		if(rads == 0)
			chunkPrefix += EnumChatFormatting.GREEN;
		else if(rads < 1)
			chunkPrefix += EnumChatFormatting.YELLOW;
		else if(rads < 10)
			chunkPrefix += EnumChatFormatting.GOLD;
		else if(rads < 100)
			chunkPrefix += EnumChatFormatting.RED;
		else if(rads < 1000)
			chunkPrefix += EnumChatFormatting.DARK_RED;
		else
			chunkPrefix += EnumChatFormatting.DARK_GRAY;
		
		if(eRad < 200)
			radPrefix += EnumChatFormatting.GREEN;
		else if(eRad < 400)
			radPrefix += EnumChatFormatting.YELLOW;
		else if(eRad < 600)
			radPrefix += EnumChatFormatting.GOLD;
		else if(eRad < 800)
			radPrefix += EnumChatFormatting.RED;
		else if(eRad < 1000)
			radPrefix += EnumChatFormatting.DARK_RED;
		else
			radPrefix += EnumChatFormatting.DARK_GRAY;
		
		if(resKoeff > 0)
			resPrefix += EnumChatFormatting.GREEN;

		//localization and server-side restrictions have turned this into a painful mess
		//a *functioning* painful mess, nonetheless
		player.addChatMessage(new ChatComponentText("===== ☢ ").appendSibling(new ChatComponentTranslation("geiger.title")).appendSibling(new ChatComponentText(" ☢ =====")).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GOLD)));
		player.addChatMessage(new ChatComponentTranslation("geiger.chunkRad").appendSibling(new ChatComponentText(" " + chunkPrefix + rads + " RAD/s")).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));
		player.addChatMessage(new ChatComponentTranslation("geiger.playerRad").appendSibling(new ChatComponentText(" " + radPrefix + eRad + " RAD")).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));
		player.addChatMessage(new ChatComponentTranslation("geiger.playerRes").appendSibling(new ChatComponentText(" " + resPrefix + res + "% (" + resKoeff + ")")).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));
	}
	
	public static void printDiagnosticData(EntityPlayer player) {

		double digamma = ((int)(HbmLivingProps.getDigamma(player) * 100)) / 100D;
		double halflife = ((int)((1D - Math.pow(0.5, digamma)) * 100)) / 100D;
		
		player.addChatMessage(new ChatComponentText("===== Ϝ ").appendSibling(new ChatComponentTranslation("digamma.title")).appendSibling(new ChatComponentText(" Ϝ =====")).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_PURPLE)));
		player.addChatMessage(new ChatComponentTranslation("digamma.playerDigamma").appendSibling(new ChatComponentText(EnumChatFormatting.RED + " " + digamma + " DRX")).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.LIGHT_PURPLE)));
		player.addChatMessage(new ChatComponentTranslation("digamma.playerHealth").appendSibling(new ChatComponentText(EnumChatFormatting.RED + " " + halflife + "%")).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.LIGHT_PURPLE)));
		player.addChatMessage(new ChatComponentTranslation("digamma.playerRes").appendSibling(new ChatComponentText(EnumChatFormatting.BLUE + " " + "N/A")).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.LIGHT_PURPLE)));
	}
	
	public static enum HazardType {
		MONOXIDE,
		RADIATION,
		ASBESTOS,
		DIGAMMA
	}
	
	public static enum ContaminationType {
		GAS,				//filterable by gas mask
		GAS_NON_REACTIVE,	//not filterable by gas mask
		GOGGLES,			//preventable by goggles
		FARADAY,			//preventable by metal armor
		HAZMAT,				//preventable by hazmat
		HAZMAT2,			//preventable by heavy hazmat
		DIGAMMA,			//preventable by fau armor or stability
		DIGAMMA2,			//preventable by robes
		CREATIVE,			//preventable by creative mode
		NONE				//not preventable
	}
	
	@SuppressWarnings("incomplete-switch") //just shut up
	public static boolean contaminate(EntityLivingBase entity, HazardType hazard, ContaminationType cont, float amount) {
		
		if(entity instanceof EntityPlayer) {
			
			EntityPlayer player = (EntityPlayer)entity;
			
			switch(cont) {
			case GAS:				if(ArmorUtil.checkForGasMask(player))	return false; break;
			case GAS_NON_REACTIVE:	if(ArmorUtil.checkForMonoMask(player))	return false; break;
			case GOGGLES:			if(ArmorUtil.checkForGoggles(player))	return false; break;
			case FARADAY:			if(ArmorUtil.checkForFaraday(player))	return false; break;
			case HAZMAT:			if(ArmorUtil.checkForHazmat(player))	return false; break;
			case HAZMAT2:			if(ArmorUtil.checkForHaz2(player))		return false; break;
			case DIGAMMA:			if(ArmorUtil.checkForDigamma(player))	return false; break;
			case DIGAMMA2: break;
			}
			
			if(player.capabilities.isCreativeMode && cont != ContaminationType.NONE)
				return false;
			
			if(player.ticksExisted < 200)
				return false;
		}
		
		if(hazard == HazardType.RADIATION && isRadImmune(entity))
			return false;
		
		switch(hazard) {
		case MONOXIDE: entity.attackEntityFrom(ModDamageSource.monoxide, amount); break;
		case RADIATION: HbmLivingProps.incrementRadiation(entity, amount * calculateRadiationMod(entity)); break;
		case ASBESTOS: HbmLivingProps.incrementAsbestos(entity, (int)amount); break;
		case DIGAMMA: HbmLivingProps.incrementDigamma(entity, amount); break;
		}
		
		return true;
	}
}
