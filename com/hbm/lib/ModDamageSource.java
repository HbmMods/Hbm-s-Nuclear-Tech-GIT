package com.hbm.lib;

import com.hbm.entity.EntityBullet;

import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;

public class ModDamageSource extends DamageSource {
	
	public static DamageSource nuclearBlast = (new DamageSource("nuclearBlast")).setExplosion();

	public ModDamageSource(String p_i1566_1_) {
		super(p_i1566_1_);
	}
    public static DamageSource causeBulletDamage(EntityBullet p_76353_0_, Entity p_76353_1_)
    {
        return (new EntityDamageSourceIndirect("revolverBullet", p_76353_0_, p_76353_1_)).setProjectile();
    }

}
