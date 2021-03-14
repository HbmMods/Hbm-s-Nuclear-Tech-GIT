package com.hbm.extprop;

import java.util.UUID;

import com.hbm.lib.ModDamageSource;
import com.hbm.main.MainRegistry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class HbmLivingProps implements IExtendedEntityProperties {
	
	public static final String key = "NTM_EXT_LIVING";
	public static final UUID digamma_UUID = UUID.fromString("2a3d8aec-5ab9-4218-9b8b-ca812bdf378b");
	public EntityLivingBase entity;
	
	/// VALS ///
	private float radiation;
	private float digamma;
	private int asbestos;
	
	public HbmLivingProps(EntityLivingBase entity) {
		this.entity = entity;
	}
	
	/// DATA ///
	public static HbmLivingProps registerData(EntityLivingBase entity) {
		
		entity.registerExtendedProperties(key, new HbmLivingProps(entity));
		return (HbmLivingProps) entity.getExtendedProperties(key);
	}
	
	public static HbmLivingProps getData(EntityLivingBase entity) {
		
		HbmLivingProps props = (HbmLivingProps) entity.getExtendedProperties(key);
		return props != null ? props : registerData(entity);
	}
	
	/// RADIATION ///
	public static float getRadiation(EntityLivingBase entity) {
		return getData(entity).radiation;
	}
	
	public static void setRadiation(EntityLivingBase entity, float rad) {
		getData(entity).radiation = rad;
	}
	
	public static void incrementRadiation(EntityLivingBase entity, float rad) {
		HbmLivingProps data = getData(entity);
		float radiation = getData(entity).radiation + rad;
		
		if(radiation > 2500)
			radiation = 2500;
		if(radiation < 0)
			radiation = 0;
		
		data.setRadiation(entity, radiation);
	}
	
	/// DIGAMA ///
	public static float getDigamma(EntityLivingBase entity) {
		return getData(entity).digamma;
	}
	
	public static void setDigamma(EntityLivingBase entity, float digamma) {
		
		getData(entity).digamma = digamma;
		
		float healthMod = (float)Math.pow(0.5, digamma) - 1F;
		
		IAttributeInstance attributeinstance = entity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.maxHealth);
		
		try {
			attributeinstance.removeModifier(attributeinstance.getModifier(digamma_UUID));
		} catch(Exception ex) { }
		
		attributeinstance.applyModifier(new AttributeModifier(digamma_UUID, "digamma", healthMod, 2));
		
		if(entity.getHealth() > entity.getMaxHealth()) {
			entity.setHealth(entity.getMaxHealth());
		}
		
		if((entity.getMaxHealth() <= 0 || digamma >= 10.0F) && entity.isEntityAlive()) {
			entity.setAbsorptionAmount(0);
			entity.attackEntityFrom(ModDamageSource.digamma, 500F);
			entity.setHealth(0);
			entity.onDeath(ModDamageSource.digamma);
		}
		
		if(entity instanceof EntityPlayer) {
			
			float di = getData(entity).digamma;

			if(di > 0F)
				((EntityPlayer) entity).triggerAchievement(MainRegistry.digammaSee);
			if(di >= 2F)
				((EntityPlayer) entity).triggerAchievement(MainRegistry.digammaFeel);
			if(di >= 10F)
				((EntityPlayer) entity).triggerAchievement(MainRegistry.digammaKnow);
		}
	}
	
	public static void incrementDigamma(EntityLivingBase entity, float digamma) {
		HbmLivingProps data = getData(entity);
		float dRad = getDigamma(entity) + digamma;
		
		if(dRad > 10)
			dRad = 10;
		if(dRad < 0)
			dRad = 0;
		
		data.setDigamma(entity, dRad);
	}
	
	
	/// ASBESTOS ///
	public static int getAsbestos(EntityLivingBase entity) {
		return getData(entity).asbestos;
	}
	
	public static void setAsbestos(EntityLivingBase entity, int asbestos) {
		getData(entity).asbestos = asbestos;
		
		if(asbestos >= 30 * 60 * 20) {
			getData(entity).asbestos = 0;
			entity.attackEntityFrom(ModDamageSource.asbestos, 1000);
		}
	}
	
	public static void incrementAsbestos(EntityLivingBase entity, int asbestos) {
		setAsbestos(entity, getAsbestos(entity) + asbestos);
	}

	@Override
	public void init(Entity entity, World world) { }

	@Override
	public void saveNBTData(NBTTagCompound nbt) {
		
		NBTTagCompound props = new NBTTagCompound();
		
		props.setFloat("hfr_radiation", radiation);
		props.setFloat("hfr_digamma", digamma);
		props.setInteger("hfr_asbestos", asbestos);
		
		nbt.setTag("HbmLivingProps", props);
	}

	@Override
	public void loadNBTData(NBTTagCompound nbt) {
		
		NBTTagCompound props = (NBTTagCompound) nbt.getTag("HbmLivingProps");
		
		if(props != null) {
			radiation = props.getFloat("hfr_radiation");
			digamma = props.getFloat("hfr_digamma");
			asbestos = props.getInteger("hfr_asbestos");
		}
	}
	
	public static class ContaminationEffect {
		
		public float maxRad;
		public int maxTime;
		public int time;
		public boolean ignoreArmor;
		
		public ContaminationEffect(float rad, int time, boolean ignoreArmor) {
			this.maxRad = rad;
			this.maxTime = this.time = time;
			this.ignoreArmor = ignoreArmor;
		}
		
		public float getRad() {
			return maxRad * ((float)time / (float)maxTime);
		}
	}
}
