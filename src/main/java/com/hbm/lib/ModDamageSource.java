package com.hbm.lib;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;

public class ModDamageSource extends DamageSource {

	public static DamageSource nuclearBlast = (new DamageSource("nuclearBlast")).setExplosion();
	public static DamageSource mudPoisoning = (new DamageSource("mudPoisoning")).setDamageBypassesArmor();
	public static DamageSource acid = (new DamageSource("acid"));
	public static DamageSource euthanizedSelf = (new DamageSource("euthanizedSelf")).setDamageBypassesArmor();
	public static DamageSource euthanizedSelf2 = (new DamageSource("euthanizedSelf2")).setDamageBypassesArmor();
	public static DamageSource tauBlast = (new DamageSource("tauBlast")).setDamageBypassesArmor().setExplosion();
	public static DamageSource radiation = (new DamageSource("radiation")).setDamageBypassesArmor();
	public static DamageSource digamma = (new DamageSource("digamma")).setDamageIsAbsolute().setDamageBypassesArmor().setDamageAllowedInCreativeMode();
	public static DamageSource suicide = (new DamageSource("suicide")).setProjectile();
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
	public static DamageSource blacklung = (new DamageSource("blacklung")).setDamageIsAbsolute().setDamageBypassesArmor();
	public static DamageSource mku = (new DamageSource("mku")).setDamageIsAbsolute().setDamageBypassesArmor();
	public static DamageSource vacuum = (new DamageSource("vacuum")).setDamageIsAbsolute().setDamageBypassesArmor();
	public static DamageSource overdose = (new DamageSource("overdose")).setDamageIsAbsolute().setDamageBypassesArmor();
	public static DamageSource microwave = (new DamageSource("microwave")).setDamageIsAbsolute().setDamageBypassesArmor();
	public static DamageSource nitan = (new DamageSource("nitan")).setDamageIsAbsolute().setDamageBypassesArmor().setDamageAllowedInCreativeMode();;

	public static final String s_bullet = "revolverBullet";
	public static final String s_emplacer = "chopperBullet";
	public static final String s_tau = "tau";
	public static final String s_combineball = "cmb";
	public static final String s_zomg_prefix = "subAtomic";
	public static final String s_euthanized = "euthanized";
	public static final String s_emp = "electrified";
	public static final String s_flamethrower = "flamethrower";
	public static final String s_immolator = "plasma";
	public static final String s_cryolator = "ice";
	public static final String s_laser = "laser";
	public static final String s_boil = "boil";
	public static final String s_acid = "acidPlayer";

	public ModDamageSource(String type) { super(type); }

	public static DamageSource causeBulletDamage(Entity base, Entity ent) {
		return (new EntityDamageSourceIndirect(s_bullet, base, ent)).setProjectile();
	}

	public static DamageSource causeDisplacementDamage(Entity ent, Entity hit) {
		return (new EntityDamageSourceIndirect(s_emplacer, ent, hit)).setProjectile();
	}

	public static DamageSource causeTauDamage(Entity ent, Entity hit) {
		return (new EntityDamageSourceIndirect(s_tau, ent, hit)).setProjectile().setDamageBypassesArmor();
	}

	public static DamageSource causeCombineDamage(Entity ent, Entity hit) {
		return (new EntityDamageSourceIndirect(s_combineball, ent, hit)).setProjectile().setDamageBypassesArmor();
	}

	public static DamageSource causeSubatomicDamage(Entity ent, Entity hit) {
		return (new EntityDamageSourceIndirect(s_zomg_prefix + (ent.worldObj.rand.nextInt(5) + 1), ent, hit)).setProjectile().setDamageBypassesArmor();
	}

	public static DamageSource euthanized(Entity ent, Entity hit) {
		return (new EntityDamageSourceIndirect(s_euthanized, ent, hit)).setDamageBypassesArmor();
	}

	public static boolean getIsTau(DamageSource source) {
		if(source instanceof EntityDamageSourceIndirect) {
			return ((EntityDamageSourceIndirect) source).damageType.equals("tau");
		}
		return false;
	}

	public static boolean getIsSubatomic(DamageSource source) {
		if(source instanceof EntityDamageSourceIndirect) {
			String s = ((EntityDamageSourceIndirect) source).damageType;
			return s.startsWith("subAtomic");
		}
		return false;
	}
}
