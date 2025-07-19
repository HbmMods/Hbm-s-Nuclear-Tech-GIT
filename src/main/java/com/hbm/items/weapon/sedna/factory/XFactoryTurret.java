package com.hbm.items.weapon.sedna.factory;

import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.standard.BlockAllocatorStandard;
import com.hbm.explosion.vanillant.standard.BlockProcessorStandard;
import com.hbm.explosion.vanillant.standard.EntityProcessorCrossSmooth;
import com.hbm.explosion.vanillant.standard.ExplosionEffectWeapon;
import com.hbm.explosion.vanillant.standard.PlayerProcessorStandard;
import com.hbm.items.ModItems;
import com.hbm.items.ItemAmmoEnums.Ammo240Shell;
import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.particle.SpentCasing;
import com.hbm.particle.SpentCasing.CasingType;

import net.minecraft.item.ItemStack;

public class XFactoryTurret {

	public static BulletConfig dgk_normal;
	
	public static SpentCasing CASINNG240MM = new SpentCasing(CasingType.BOTTLENECK).setScale(7.5F).setBounceMotion(0.02F, 0.05F).setColor(SpentCasing.COLOR_CASE_BRASS).setupSmoke(1F, 0.5D, 60, 20);
	public static BulletConfig shell_normal;
	public static BulletConfig shell_explosive;
	public static BulletConfig shell_ap;
	public static BulletConfig shell_du;
	public static BulletConfig shell_w9;
	
	public static void init() {
		dgk_normal = new BulletConfig().setItem(new ItemStack(ModItems.ammo_dgk));
		
		shell_normal = new BulletConfig().setItem(ModItems.ammo_shell.stackFromEnum(Ammo240Shell.STOCK)).setDamage(1F).setCasing(CASINNG240MM.clone().register("240standard")).setOnImpact((bullet, mop) -> {
			Lego.standardExplode(bullet, mop, 10F); bullet.setDead();
		});
		shell_explosive = new BulletConfig().setItem(ModItems.ammo_shell.stackFromEnum(Ammo240Shell.EXPLOSIVE)).setDamage(1.5F).setCasing(CASINNG240MM.clone().register("240ext")).setOnImpact((bullet, mop) -> {
			ExplosionVNT vnt = new ExplosionVNT(bullet.worldObj, mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord, 10F);
			vnt.setBlockAllocator(new BlockAllocatorStandard());
			vnt.setBlockProcessor(new BlockProcessorStandard());
			vnt.setEntityProcessor(new EntityProcessorCrossSmooth(1, bullet.damage));
			vnt.setPlayerProcessor(new PlayerProcessorStandard());
			vnt.setSFX(new ExplosionEffectWeapon(10, 2.5F, 1F));
			vnt.explode();
			bullet.setDead();
		});
		shell_ap = new BulletConfig().setItem(ModItems.ammo_shell.stackFromEnum(Ammo240Shell.APFSDS_T)).setDamage(2F).setDoesPenetrate(true).setCasing(CASINNG240MM.clone().register("240w"));
		shell_du = new BulletConfig().setItem(ModItems.ammo_shell.stackFromEnum(Ammo240Shell.APFSDS_DU)).setDamage(2.5F).setDoesPenetrate(true).setDamageFalloffByPen(false).setCasing(CASINNG240MM.clone().register("240u"));
		shell_w9 = new BulletConfig().setItem(ModItems.ammo_shell.stackFromEnum(Ammo240Shell.W9)).setDamage(2.5F).setCasing(CASINNG240MM.clone().register("240n")).setOnImpact(XFactoryCatapult.LAMBDA_NUKE_STANDARD);
	}
}
