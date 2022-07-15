package com.hbm.potion;

import java.lang.reflect.Field;

import org.apache.logging.log4j.Level;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.bomb.BlockTaint;
import com.hbm.config.GeneralConfig;
import com.hbm.config.PotionConfig;
import com.hbm.entity.mob.EntityTaintCrab;
import com.hbm.entity.mob.EntityTaintedCreeper;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.extprop.HbmLivingProps;
import com.hbm.items.ModItems;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.MainRegistry;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;

import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class HbmPotion extends Potion {

	public static HbmPotion taint;
	public static HbmPotion radiation;
	public static HbmPotion bang;
	public static HbmPotion mutation;
	public static HbmPotion radx;
	public static HbmPotion lead;
	public static HbmPotion radaway;
	public static HbmPotion telekinesis;
	public static HbmPotion phosphorus;
	public static HbmPotion stability;
	public static HbmPotion potionsickness;
	public static HbmPotion paralysis;
	public static HbmPotion fragile;
	public static HbmPotion unconscious;
	public static HbmPotion perforated;
	public static HbmPotion hollow;
	public static HbmPotion death;

	public HbmPotion(int id, boolean isBad, int color) {
		super(id, isBad, color);
	}

	public static void init() {
		taint = registerPotion(PotionConfig.taintID, true, 8388736, "potion.hbm_taint", 0, 0);
		radiation = registerPotion(PotionConfig.radiationID, true, 8700200, "potion.hbm_radiation", 1, 0);
		bang = registerPotion(PotionConfig.bangID, true, 1118481, "potion.hbm_bang", 3, 0);
		mutation = registerPotion(PotionConfig.mutationID, false, 8388736, "potion.hbm_mutation", 2, 0);
		radx = registerPotion(PotionConfig.radxID, false, 0xBB4B00, "potion.hbm_radx", 5, 0);
		lead = registerPotion(PotionConfig.leadID, true, 0x767682, "potion.hbm_lead", 6, 0);
		radaway = registerPotion(PotionConfig.radawayID, false, 0xBB4B00, "potion.hbm_radaway", 7, 0);
		telekinesis = registerPotion(PotionConfig.telekinesisID, true, 0x00F3FF, "potion.hbm_telekinesis", 0, 1);
		phosphorus = registerPotion(PotionConfig.phosphorusID, true, 0xFFFF00, "potion.hbm_phosphorus", 1, 1);
		stability = registerPotion(PotionConfig.stabilityID, false, 0xD0D0D0, "potion.hbm_stability", 2, 1);
		potionsickness = registerPotion(PotionConfig.potionsicknessID, false, 0xff8080, "potion.hbm_potionsickness", 3, 1);
		paralysis = registerPotion(PotionConfig.paralysisID, true, 0x808080, "potion.hbm_paralysis", 7, 1);
		fragile = registerPotion(PotionConfig.fragileID, true, 0x00FFFF, "potion.hbm_fragile", 6, 1);
		unconscious = registerPotion(PotionConfig.unconsciousID, false, 0xFF80ED, "potion.hbm_unconscious", 0, 2);
		perforated = registerPotion(PotionConfig.perforatedID, true, 0xFF0000, "potion.hbm_perforated", 1, 2);
		hollow = registerPotion(PotionConfig.hollowID, true, 0x000000, "potion.hbm_hollow", 2, 2);
		death = registerPotion(PotionConfig.deathID, false, 1118481, "potion.hbm_death", 4, 1);
	}

	public static HbmPotion registerPotion(int id, boolean isBad, int color, String name, int x, int y) {

		if (id >= Potion.potionTypes.length) {

			Potion[] newArray = new Potion[Math.max(256, id)];
			System.arraycopy(Potion.potionTypes, 0, newArray, 0, Potion.potionTypes.length);
			
			Field field = ReflectionHelper.findField(Potion.class, new String[] { "field_76425_a", "potionTypes" });
			field.setAccessible(true);
			
			try {
				
				Field modfield = Field.class.getDeclaredField("modifiers");
				modfield.setAccessible(true);
				modfield.setInt(field, field.getModifiers() & 0xFFFFFFEF);
				field.set(null, newArray);
				
			} catch (Exception e)
			{
				MainRegistry.logger.catching(Level.INFO, e);
			}
		}
		
		HbmPotion effect = new HbmPotion(id, isBad, color);
		effect.setPotionName(name);
		effect.setIconIndex(x, y);
		
		return effect;
	}
	
	public static PotionEffect getPotionNoCure(int id, int dura, int level)
	{
		PotionEffect potion = new PotionEffect(id, dura, level);
		potion.getCurativeItems().clear();
		return potion;
	}
	
	public static PotionEffect getPotionWithCures(int id, int dura, int level, ItemStack...stacks)
	{
		PotionEffect potion = new PotionEffect(id, dura, level);
		for (ItemStack stack : stacks)
			potion.addCurativeItem(stack);
		return potion;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getStatusIconIndex() {
		ResourceLocation loc = new ResourceLocation("hbm","textures/gui/potions.png");
		Minecraft.getMinecraft().renderEngine.bindTexture(loc);
		return super.getStatusIconIndex();
	}

	@Override
	public void performEffect(EntityLivingBase entity, int level) {

		if(this == taint) {
			
			if(!(entity instanceof EntityTaintedCreeper) && !(entity instanceof EntityTaintCrab) && entity.worldObj.rand.nextInt(40) == 0)
				entity.attackEntityFrom(ModDamageSource.taint, (level + 1));
			
			if(GeneralConfig.enableHardcoreTaint && !entity.worldObj.isRemote) {
				
				int x = (int)(entity.posX - 1);
				int y = (int)entity.posY;
				int z = (int)(entity.posZ);
				
				if(entity.worldObj.getBlock(x, y, z)
						.isReplaceable(entity.worldObj, x, y, z) && 
						BlockTaint.hasPosNeightbour(entity.worldObj, x, y, z)) {
					
					entity.worldObj.setBlock(x, y, z, ModBlocks.taint, 14, 2);
				}
			}
		}
		if(this == radiation) {
			ContaminationUtil.contaminate(entity, HazardType.RADIATION, ContaminationType.CREATIVE, (level + 1F) * 0.05F);
		}
		if(this == radaway) {
			
			HbmLivingProps.incrementRadiation(entity, -(level + 1));
			
		}
		if(this == bang) {
			
			entity.attackEntityFrom(ModDamageSource.bang, 1000);
			entity.setHealth(0.0F);

			if (!(entity instanceof EntityPlayer))
				entity.setDead();

			entity.worldObj.playSoundEffect(entity.posX, entity.posY, entity.posZ, "hbm:weapon.laserBang", 100.0F, 1.0F);
			ExplosionLarge.spawnParticles(entity.worldObj, entity.posX, entity.posY, entity.posZ, 10);
			
			if(entity instanceof EntityCow) {
				EntityCow cow = (EntityCow) entity;
				int toDrop = cow.isChild() ? 10 : 3;
				cow.entityDropItem(new ItemStack(ModItems.cheese, toDrop), 1.0F);
			}
		}
		if(this == lead) {
			
			entity.attackEntityFrom(ModDamageSource.lead, (level + 1));
		}
		if(this == telekinesis) {
			
			int remaining = entity.getActivePotionEffect(this).getDuration();
			
			if(remaining > 1) {
				entity.motionY = 0.5;
			} else {
				entity.motionY = -2;
				entity.fallDistance = 50;
			}
		}
		if(this == phosphorus && !entity.worldObj.isRemote) {
			
			entity.setFire(1);
		}
		if (this == paralysis)
		{
			if (entity.getEntityAttribute(SharedMonsterAttributes.attackDamage) != null)
				func_111184_a(SharedMonsterAttributes.attackDamage, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", -100, 2);
			
			func_111184_a(SharedMonsterAttributes.movementSpeed, "7107DE5E-7CE8-4030-940E-514C1F160890", -100, 2);
			
			if (entity.motionY > 0)
				entity.motionY = -2;
		}
		if (this == perforated)
		{
			entity.attackEntityFrom(ModDamageSource.bleed, (level + 3));
		}
		if (this == hollow)
		{
//			if (level > 2)
//				ContaminationUtil.applyDigammaDirect(entity, (level + 1F) * 0.5F);
//			else
//				ContaminationUtil.applyDigammaData(entity, (level + 1F) * 0.25F);
			
			ContaminationUtil.contaminate(entity, HazardType.DIGAMMA, ContaminationType.DIGAMMA, (level + 1F) > 2 ? 0.005F : 0.0025F);
		}
	}

	@Override
	public boolean isReady(int par1, int par2) {

		if(this == taint) {
			return par1 % 2 == 0;
		}
		if(this == radiation || this == hollow || this == radaway || this == telekinesis || this == phosphorus || this == paralysis || this == fragile) {
			
			return true;
		}
		
		if(this == bang) {
			return par1 <= 10;
		}
		
		if(this == lead) {
			int k = 60;
			return k > 0 ? par1 % k == 0 : true;
		}
		if (this == perforated)
		{
			int k = 30;
			return k > 0 ? par1 % k == 0 : true;
		}
		
		return false;
	}
	
	public static boolean getIsBadEffect(Potion potion) {
		
		try {
			Field isBadEffect = ReflectionHelper.findField(Potion.class, "isBadEffect", "field_76418_K");
			boolean ret = isBadEffect.getBoolean(potion);
			return ret;
			
		} catch (Exception x) {
			return false;
		}
	}
}
