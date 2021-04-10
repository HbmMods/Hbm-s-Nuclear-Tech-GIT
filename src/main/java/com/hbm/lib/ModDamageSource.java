package com.hbm.lib;

import com.hbm.entity.projectile.EntityBullet;
import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.entity.projectile.EntityCombineBall;
import com.hbm.entity.projectile.EntityDischarge;
import com.hbm.entity.projectile.EntityFire;
import com.hbm.entity.projectile.EntityLN2;
import com.hbm.entity.projectile.EntityLaserBeam;
import com.hbm.entity.projectile.EntityMinerBeam;
import com.hbm.entity.projectile.EntityPlasmaBeam;
import com.hbm.entity.projectile.EntityRainbow;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;

public class ModDamageSource extends DamageSource {
	
	public static DamageSource nuclearBlast = (new DamageSource("nuclearBlast")).setExplosion();
	public static DamageSource mudPoisoning = (new DamageSource("mudPoisoning")).setDamageBypassesArmor();
	public static DamageSource acid = (new DamageSource("acid")).setDamageBypassesArmor();
	public static DamageSource euthanizedSelf = (new DamageSource("euthanizedSelf")).setDamageBypassesArmor();
	public static DamageSource euthanizedSelf2 = (new DamageSource("euthanizedSelf2")).setDamageBypassesArmor();
	public static DamageSource tauBlast = (new DamageSource("tauBlast")).setDamageBypassesArmor();
	public static DamageSource radiation = (new DamageSource("radiation")).setDamageBypassesArmor();
	public static DamageSource digamma = (new DamageSource("digamma")).setDamageIsAbsolute().setDamageBypassesArmor().setDamageAllowedInCreativeMode();
	public static DamageSource suicide = (new DamageSource("suicide")).setProjectile();
	public static DamageSource teleporter = (new DamageSource("teleporter")).setDamageIsAbsolute();
	public static DamageSource cheater = (new DamageSource("cheater")).setDamageIsAbsolute().setDamageBypassesArmor().setDamageAllowedInCreativeMode();
	public static DamageSource rubble = (new DamageSource("rubble")).setProjectile();
	public static DamageSource shrapnel = (new DamageSource("shrapnel")).setProjectile();
	public static DamageSource blackhole = (new DamageSource("blackhole")).setDamageIsAbsolute().setDamageBypassesArmor();
	public static DamageSource turbofan = (new DamageSource("blender")).setDamageIsAbsolute().setDamageBypassesArmor();
	public static DamageSource meteorite = (new DamageSource("meteorite")).setDamageIsAbsolute().setDamageBypassesArmor();
	public static DamageSource boxcar = (new DamageSource("boxcar")).setDamageIsAbsolute().setDamageBypassesArmor();
	public static DamageSource boat = (new DamageSource("boat")).setDamageIsAbsolute().setDamageBypassesArmor();
	public static DamageSource building = (new DamageSource("building")).setDamageIsAbsolute().setDamageBypassesArmor();
	public static DamageSource taint = (new DamageSource("taint")).setDamageIsAbsolute().setDamageBypassesArmor();
	public static DamageSource ams = (new DamageSource("ams")).setDamageIsAbsolute().setDamageBypassesArmor();
	public static DamageSource amsCore = (new DamageSource("amsCore")).setDamageIsAbsolute().setDamageBypassesArmor();
	public static DamageSource broadcast = (new DamageSource("broadcast")).setDamageIsAbsolute().setDamageBypassesArmor();
	public static DamageSource bang = (new DamageSource("bang")).setDamageIsAbsolute().setDamageBypassesArmor();
	public static DamageSource pc = (new DamageSource("pc")).setDamageIsAbsolute().setDamageBypassesArmor();
	public static DamageSource cloud = (new DamageSource("cloud")).setDamageIsAbsolute().setDamageBypassesArmor();
	public static DamageSource lead = (new DamageSource("lead")).setDamageIsAbsolute().setDamageBypassesArmor();
	public static DamageSource enervation = (new DamageSource("enervation")).setDamageIsAbsolute().setDamageBypassesArmor();
	public static DamageSource electricity = (new DamageSource("electricity")).setDamageIsAbsolute().setDamageBypassesArmor();
	public static DamageSource exhaust = (new DamageSource("exhaust")).setDamageIsAbsolute().setDamageBypassesArmor();
	public static DamageSource spikes = (new DamageSource("spikes")).setDamageBypassesArmor();
	public static DamageSource lunar = (new DamageSource("lunar")).setDamageIsAbsolute().setDamageBypassesArmor();
	public static DamageSource monoxide = (new DamageSource("monoxide")).setDamageIsAbsolute().setDamageBypassesArmor();
	public static DamageSource asbestos = (new DamageSource("asbestos")).setDamageIsAbsolute().setDamageBypassesArmor();

	public ModDamageSource(String p_i1566_1_) {
		super(p_i1566_1_);
	}
    public static DamageSource causeBulletDamage(EntityBullet p_76353_0_, Entity p_76353_1_)
    {
        return (new EntityDamageSourceIndirect("revolverBullet", p_76353_0_, p_76353_1_)).setProjectile();
    }
    public static DamageSource causeBulletDamage(EntityBulletBase base, Entity ent)
    {
        return (new EntityDamageSourceIndirect("revolverBullet", base, ent)).setProjectile();
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
    public static DamageSource causeSubatomicDamage2(EntityRainbow p_76353_0_, Entity p_76353_1_)
    {
        return (new EntityDamageSourceIndirect("subAtomic2", p_76353_0_, p_76353_1_)).setProjectile().setDamageBypassesArmor();
    }
    public static DamageSource causeSubatomicDamage3(EntityRainbow p_76353_0_, Entity p_76353_1_)
    {
        return (new EntityDamageSourceIndirect("subAtomic3", p_76353_0_, p_76353_1_)).setProjectile().setDamageBypassesArmor();
    }
    public static DamageSource causeSubatomicDamage4(EntityRainbow p_76353_0_, Entity p_76353_1_)
    {
        return (new EntityDamageSourceIndirect("subAtomic4", p_76353_0_, p_76353_1_)).setProjectile().setDamageBypassesArmor();
    }
    public static DamageSource causeSubatomicDamage5(EntityRainbow p_76353_0_, Entity p_76353_1_)
    {
        return (new EntityDamageSourceIndirect("subAtomic5", p_76353_0_, p_76353_1_)).setProjectile().setDamageBypassesArmor();
    }
    public static DamageSource euthanized(Entity p_76353_0_, Entity p_76353_1_)
    {
        return (new EntityDamageSourceIndirect("euthanized", p_76353_0_, p_76353_1_)).setDamageBypassesArmor();
    }
    public static DamageSource causeDischargeDamage(EntityDischarge p_76353_0_, Entity p_76353_1_)
    {
        return (new EntityDamageSourceIndirect("electrified", p_76353_0_, p_76353_1_)).setDamageBypassesArmor();
    }
    public static DamageSource causeFireDamage(EntityFire p_76353_0_, Entity p_76353_1_)
    {
        return (new EntityDamageSourceIndirect("flamethrower", p_76353_0_, p_76353_1_)).setFireDamage().setDamageBypassesArmor();
    }
    public static DamageSource causePlasmaDamage(EntityPlasmaBeam p_76353_0_, Entity p_76353_1_)
    {
        return (new EntityDamageSourceIndirect("plasma", p_76353_0_, p_76353_1_)).setDamageBypassesArmor();
    }
    public static DamageSource causeIceDamage(EntityLN2 p_76353_0_, Entity p_76353_1_)
    {
        return (new EntityDamageSourceIndirect("ice", p_76353_0_, p_76353_1_)).setDamageBypassesArmor();
    }
    public static DamageSource causeLaserDamage(EntityLaserBeam p_76353_0_, Entity p_76353_1_)
    {
        return (new EntityDamageSourceIndirect("laser", p_76353_0_, p_76353_1_)).setDamageBypassesArmor();
    }
    public static DamageSource causeLaserDamage(EntityMinerBeam p_76353_0_, Entity p_76353_1_)
    {
        return (new EntityDamageSourceIndirect("laser", p_76353_0_, p_76353_1_)).setDamageBypassesArmor();
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
    		String s = ((EntityDamageSourceIndirect)source).damageType;
    		return s.equals("subAtomic") || s.equals("subAtomic2") || s.equals("subAtomic3") || s.equals("subAtomic4") || s.equals("subAtomic5");
    	}
    	return false;
    }
    
    public static boolean getIsDischarge(DamageSource source) {
    	if(source instanceof EntityDamageSourceIndirect)
    	{
    		return ((EntityDamageSourceIndirect)source).damageType.equals("electrified");
    	}
    	return false;
    }
    
    public static boolean getIsFire(DamageSource source) {
    	if(source instanceof EntityDamageSourceIndirect)
    	{
    		return ((EntityDamageSourceIndirect)source).damageType.equals("flamethrower");
    	}
    	return false;
    }
    
    public static boolean getIsPlasma(DamageSource source) {
    	if(source instanceof EntityDamageSourceIndirect)
    	{
    		return ((EntityDamageSourceIndirect)source).damageType.equals("plasma");
    	}
    	return false;
    }
    
    public static boolean getIsLiquidNitrogen(DamageSource source) {
    	if(source instanceof EntityDamageSourceIndirect)
    	{
    		return ((EntityDamageSourceIndirect)source).damageType.equals("ice");
    	}
    	return false;
    }
    
    public static boolean getIsLaser(DamageSource source) {
    	if(source instanceof EntityDamageSourceIndirect)
    	{
    		return ((EntityDamageSourceIndirect)source).damageType.equals("laser");
    	}
    	return false;
    }

}
