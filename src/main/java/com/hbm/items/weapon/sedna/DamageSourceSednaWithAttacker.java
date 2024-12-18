package com.hbm.items.weapon.sedna;

import java.util.Locale;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;

public class DamageSourceSednaWithAttacker extends DamageSourceSednaNoAttacker {

	public Entity projectile;
	public Entity shooter;
	
	public DamageSourceSednaWithAttacker(String type, Entity projectile, Entity shooter) {
		super(type.toLowerCase(Locale.US));
		this.projectile = projectile;
		this.shooter = shooter;
	}

	@Override public Entity getSourceOfDamage() { return this.projectile; } //what even uses this, except for the wackass "shot by bullet" death messages?
	@Override public Entity getEntity() { return this.shooter; }

	@Override
	public IChatComponent func_151519_b(EntityLivingBase died) {
		IChatComponent diedName = died.func_145748_c_();
		IChatComponent shooterName = shooter != null ? shooter.func_145748_c_() : new ChatComponentText("Unknown").setChatStyle(new ChatStyle().setObfuscated(true));
		return new ChatComponentTranslation("death.sedna." + this.damageType + ".attacker", diedName, shooterName);
	}
}
