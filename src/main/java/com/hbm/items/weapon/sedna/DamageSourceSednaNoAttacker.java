package com.hbm.items.weapon.sedna;

import java.util.Locale;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;

public class DamageSourceSednaNoAttacker extends DamageSource {

	public DamageSourceSednaNoAttacker(String type) {
		super(type.toLowerCase(Locale.US));
	}

	@Override
	public IChatComponent func_151519_b(EntityLivingBase died) {
		IChatComponent diedName = died.func_145748_c_();
		return new ChatComponentTranslation("death.sedna." + this.damageType, diedName);
	}
}
