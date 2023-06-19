package com.hbm.potion;

import java.lang.reflect.Field;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.bomb.BlockTaint;
import com.hbm.config.GeneralConfig;
import com.hbm.config.PotionConfig;
import com.hbm.entity.mob.EntityRADBeast;
import com.hbm.entity.mob.EntityTaintCrab;
import com.hbm.entity.mob.EntityCreeperTainted;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.extprop.HbmLivingProps;
import com.hbm.extprop.HbmPlayerProps;
import com.hbm.items.ModItems;
import com.hbm.lib.ModDamageSource;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;

import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class HbmPotion extends Potion {

	public static HbmPotion taint;
	public static HbmPotion radiation;
	public static HbmPotion bang;
	public static HbmPotion mutation;
	public static HbmPotion radx;
	public static HbmPotion lead;
	public static HbmPotion radaway;
	//public static HbmPotion telekinesis;
	public static HbmPotion phosphorus;
	public static HbmPotion stability;
	public static HbmPotion potionsickness;
	public static HbmPotion death;

	public static HbmPotion run;

	public static HbmPotion nitan;
	public static HbmPotion flashbang;
	


	public HbmPotion(int id, boolean isBad, int color) {
		super(id, isBad, color);
	}

	public static void init() {
		taint = registerPotion(PotionConfig.taintID, true, 0x800080, "potion.hbm_taint", 0, 0);
		radiation = registerPotion(PotionConfig.radiationID, true, 0x84C128, "potion.hbm_radiation", 1, 0);
		bang = registerPotion(PotionConfig.bangID, true, 0x111111, "potion.hbm_bang", 3, 0);
		mutation = registerPotion(PotionConfig.mutationID, false, 0x800080, "potion.hbm_mutation", 2, 0);
		radx = registerPotion(PotionConfig.radxID, false, 0xBB4B00, "potion.hbm_radx", 5, 0);
		lead = registerPotion(PotionConfig.leadID, true, 0x767682, "potion.hbm_lead", 6, 0);
		radaway = registerPotion(PotionConfig.radawayID, false, 0xBB4B00, "potion.hbm_radaway", 7, 0);
		//telekinesis = registerPotion(PotionConfig.telekinesisID, true, 0x00F3FF, "potion.hbm_telekinesis", 0, 1);
		phosphorus = registerPotion(PotionConfig.phosphorusID, true, 0xFFFF00, "potion.hbm_phosphorus", 1, 1);
		stability = registerPotion(PotionConfig.stabilityID, false, 0xD0D0D0, "potion.hbm_stability", 2, 1);
		potionsickness = registerPotion(PotionConfig.potionsicknessID, false, 0xff8080, "potion.hbm_potionsickness", 3, 1);
		death = registerPotion(PotionConfig.deathID, false, 1118481, "potion.hbm_death", 4, 1);
		run = registerPotion(PotionConfig.runID, true, 1118481, "potion.hbm_run", 14, 0);
		nitan = registerPotion(PotionConfig.nitanID, false, 8388736, "potion.hbm_nitan", 3, 1);
		flashbang = registerPotion(PotionConfig.flashbangID, false, 0xD0D0D0, "potion.hbm_flashbang", 15, 1);

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
				
			} catch (Exception e) {
				
			}
		}
		
		HbmPotion effect = new HbmPotion(id, isBad, color);
		effect.setPotionName(name);
		effect.setIconIndex(x, y);
		
		return effect;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getStatusIconIndex() {
		ResourceLocation loc = new ResourceLocation("hbm","textures/gui/potions.png");
		Minecraft.getMinecraft().renderEngine.bindTexture(loc);
		return super.getStatusIconIndex();
	}

	public void performEffect(EntityLivingBase entity, int level) {
		
		if(entity.worldObj.isRemote) return;

		if(this == taint) {
			
			if(!(entity instanceof EntityCreeperTainted) && !(entity instanceof EntityTaintCrab) && entity.worldObj.rand.nextInt(40) == 0)
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
			ContaminationUtil.contaminate(entity, HazardType.RADIATION, ContaminationType.CREATIVE, (float)(level + 1F) * 0.05F);
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
		if(this == run) {
			
			entity.attackEntityFrom(ModDamageSource.run, 1000);
			entity.setHealth(0.0F);
			//World world = Minecraft.getMinecraft().theWorld;
	
			new ExplosionVNT(entity.worldObj, entity.posX, entity.posY, entity.posZ, 12).makeAmat().explode();
			entity.worldObj.playSoundEffect(entity.posX, entity.posY, entity.posZ, "hbm:weapon.mukeExplosion", 100.0F, 1.0F);

			if (!(entity instanceof EntityPlayer))
				entity.setDead();
		}
		if(this == lead) {
			entity.attackEntityFrom(ModDamageSource.lead, (level + 1));
		}
		if(this == phosphorus) {
			entity.setFire(1);
		}
        if(this == nitan && !entity.worldObj.isRemote) {
        	if(entity instanceof EntityPlayer) {
				HbmPlayerProps props = HbmPlayerProps.getData((EntityPlayer) entity);

				if (props.nitanCount == 3) {
					entity.attackEntityFrom(ModDamageSource.nitan, 1000);
				}
			}

		}
		if(this == flashbang && !entity.worldObj.isRemote){
			if(entity instanceof EntityZombie || entity instanceof EntitySkeleton){
				entity.setFire(20);
				}
			entity.addPotionEffect(new PotionEffect(moveSlowdown.id,5,10));
		}
	}

	public boolean isReady(int par1, int par2) {
        
		if(this == taint) {
			return par1 % 2 == 0;
		}
		

		if(this == radiation || this == radaway || this == phosphorus || this == nitan) {

			return true;
		}
		
		if(this == bang) {
			return par1 <= 10;
		}
		if(this == run) {
			return par1 <= 10;
		}
		
		if(this == lead) {
			int k = 60;
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
