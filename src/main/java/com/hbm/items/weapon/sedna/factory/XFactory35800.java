package com.hbm.items.weapon.sedna.factory;

import java.util.function.BiFunction;

import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.items.weapon.sedna.Crosshair;
import com.hbm.items.weapon.sedna.GunConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.Receiver;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.WeaponQuality;
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumAmmoSecret;
import com.hbm.items.weapon.sedna.mags.MagazineFullReload;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.HbmAnimations.AnimType;
import com.hbm.util.DamageResistanceHandler.DamageClass;

import net.minecraft.item.ItemStack;

public class XFactory35800 {

	public static BulletConfig p35800;
	
	public static void init() {
		
		p35800 = new BulletConfig().setItem(EnumAmmoSecret.P35_800).setupDamageClass(DamageClass.LASER).setBeam().setSpread(0.0F).setLife(5).setRenderRotations(false).setOnBeamImpact(BulletConfig.LAMBDA_STANDARD_BEAM_HIT);

		ModItems.gun_aberrator = new ItemGunBaseNT(WeaponQuality.SECRET, new GunConfig()
				.dura(2_000).draw(10).inspect(26).reloadSequential(true).crosshair(Crosshair.CIRCLE)
				.rec(new Receiver(0)
						.dmg(50F).delay(8).reload(44).jam(36).sound("hbm:weapon.fire.laser", 1.0F, 1.0F)
						.mag(new MagazineFullReload(0, 24).addConfigs(p35800))
						.offset(0.75, -0.0625 * 1.5, -0.1875)
						.setupStandardFire())
				.setupStandardConfiguration()
				.anim(LAMBDA_ABERRATOR).orchestra(Orchestras.ORCHESTRA_ABERRATOR)
				).setUnlocalizedName("gun_aberrator");
	}

	@SuppressWarnings("incomplete-switch") public static BiFunction<ItemStack, AnimType, BusAnimation> LAMBDA_ABERRATOR = (stack, type) -> {
		return null;
	};
}
