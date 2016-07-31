package com.hbm.lib;

import com.hbm.entity.EntityBullet;
import com.hbm.entity.EntityCombineBall;
import com.hbm.entity.EntityRainbow;

import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;

public class ModDamageSource extends DamageSource {
	
	public static DamageSource nuclearBlast = (new DamageSource("nuclearBlast")).setExplosion();
	public static DamageSource mudPoisoning = (new DamageSource("mudPoisoning")).setDamageBypassesArmor();
	public static DamageSource euthanizedSelf = (new DamageSource("euthanizedSelf")).setDamageBypassesArmor();
	public static DamageSource euthanizedSelf2 = (new DamageSource("euthanizedSelf2")).setDamageBypassesArmor();
	public static DamageSource tauBlast = (new DamageSource("tauBlast")).setDamageBypassesArmor();

	public ModDamageSource(String p_i1566_1_) {
		super(p_i1566_1_);
	}
    public static DamageSource causeBulletDamage(EntityBullet p_76353_0_, Entity p_76353_1_)
    {
        return (new EntityDamageSourceIndirect("revolverBullet", p_76353_0_, p_76353_1_)).setProjectile();
    }
    public static DamageSource causeDisplacementDamage(EntityBullet p_76353_0_, Entity p_76353_1_)
    {
        return (new EntityDamageSourceIndirect("chopperBullet", p_76353_0_, p_76353_1_)).setProjectile();
    }
    public static DamageSource causeTauDamage(EntityBullet p_76353_0_, Entity p_76353_1_)
    {
        return (new EntityDamageSourceIndirect("tau", p_76353_0_, p_76353_1_)).setProjectile().setDamageBypassesArmor();
    }
    public static DamageSource causeCombineDamage(EntityCombineBall p_76353_0_, Entity p_76353_1_)
    {
        return (new EntityDamageSourceIndirect("cmb", p_76353_0_, p_76353_1_)).setProjectile().setDamageBypassesArmor();
    }
    public static DamageSource causeSubatomicDamage(EntityRainbow p_76353_0_, Entity p_76353_1_)
    {
        return (new EntityDamageSourceIndirect("subAtomic", p_76353_0_, p_76353_1_)).setProjectile().setDamageBypassesArmor();
    }
    public static DamageSource euthanized(Entity p_76353_0_, Entity p_76353_1_)
    {
        return (new EntityDamageSourceIndirect("euthanized", p_76353_0_, p_76353_1_)).setDamageBypassesArmor();
    }
    
    public static boolean getIsBullet(DamageSource source) {
    	if(source instanceof EntityDamageSourceIndirect)
    	{
    		return ((EntityDamageSourceIndirect)source).damageType.equals("revolverBullet");
    	}
    	return false;
    }
    
    public static boolean getIsEmplacer(DamageSource source) {
    	if(source instanceof EntityDamageSourceIndirect)
    	{
    		return ((EntityDamageSourceIndirect)source).damageType.equals("chopperBullet");
    	}
    	return false;
    }
    
    public static boolean getIsTau(DamageSource source) {
    	if(source instanceof EntityDamageSourceIndirect)
    	{
    		return ((EntityDamageSourceIndirect)source).damageType.equals("tau");
    	}
    	return false;
    }
    
    public static boolean getIsPoison(DamageSource source) {
    	if(source instanceof EntityDamageSourceIndirect)
    	{
    		return ((EntityDamageSourceIndirect)source).damageType.equals("euthanized");
    	}
    	return false;
    }
    
    public static boolean getIsCmb(DamageSource source) {
    	if(source instanceof EntityDamageSourceIndirect)
    	{
    		return ((EntityDamageSourceIndirect)source).damageType.equals("cmb");
    	}
    	return false;
    }
    
    public static boolean getIsSubatomic(DamageSource source) {
    	if(source instanceof EntityDamageSourceIndirect)
    	{
    		return ((EntityDamageSourceIndirect)source).damageType.equals("subAtomic");
    	}
    	return false;
    }

}
