package com.hbm.items.weapon.sedna.factory;

import java.util.function.BiFunction;

import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.items.weapon.sedna.Crosshair;
import com.hbm.items.weapon.sedna.GunConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.Receiver;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.WeaponQuality;
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumAmmo;
import com.hbm.items.weapon.sedna.impl.ItemGunChemthrower;
import com.hbm.items.weapon.sedna.mags.MagazineFluid;
import com.hbm.items.weapon.sedna.mags.MagazineFullReload;
import com.hbm.main.MainRegistry;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.BusAnimationKeyframe.IType;
import com.hbm.render.anim.HbmAnimations.AnimType;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class XFactoryFlamer {
	
	public static BulletConfig flame_diesel;

	public static void init() {
		flame_diesel = new BulletConfig().setItem(EnumAmmo.FLAME_DIESEL).setLife(100).setVel(1F).setGrav(0.02D).setReloadCount(300)
				.setOnUpdate((bullet) -> {
			if(bullet.worldObj.isRemote && MainRegistry.proxy.me().getDistanceToEntity(bullet) < 100) {
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "flamethrower");
				data.setDouble("posX", bullet.posX);
				data.setDouble("posY", bullet.posY - 0.125);
				data.setDouble("posZ", bullet.posZ);
				MainRegistry.proxy.effectNT(data);
			}
		});
		
		ModItems.gun_flamer = new ItemGunBaseNT(WeaponQuality.A_SIDE, new GunConfig()
				.dura(20_000).draw(10).inspect(17).crosshair(Crosshair.L_CIRCLE).smoke(Lego.LAMBDA_STANDARD_SMOKE)
				.rec(new Receiver(0)
						.dmg(10F).delay(1).auto(true).reload(90).jam(17)
						.mag(new MagazineFullReload(0, 300).addConfigs(flame_diesel))
						.offset(0.75, -0.0625, -0.25D)
						.setupStandardFire().recoil(Lego.LAMBDA_STANDARD_RECOIL))
				.setupStandardConfiguration()
				.anim(LAMBDA_FLAMER_ANIMS).orchestra(Orchestras.ORCHESTRA_FLAMER)
				).setUnlocalizedName("gun_flamer");
		
		ModItems.gun_chemthrower = new ItemGunChemthrower(WeaponQuality.A_SIDE, new GunConfig()
				.dura(90_000).draw(10).inspect(17).crosshair(Crosshair.L_CIRCLE).smoke(Lego.LAMBDA_STANDARD_SMOKE)
				.rec(new Receiver(0)
						.delay(1).auto(true)
						.mag(new MagazineFluid(0, 3_000))
						.offset(0.75, -0.0625, -0.25D)
						.canFire(ItemGunChemthrower.LAMBDA_CAN_FIRE).fire(ItemGunChemthrower.LAMBDA_FIRE))
				.pp(Lego.LAMBDA_STANDARD_CLICK_PRIMARY).decider(GunStateDecider.LAMBDA_STANDARD_DECIDER)
				.anim(LAMBDA_CHEMTHROWER_ANIMS).orchestra(Orchestras.ORCHESTRA_CHEMTHROWER)
				).setUnlocalizedName("gun_chemthrower");
	}

	@SuppressWarnings("incomplete-switch") public static BiFunction<ItemStack, AnimType, BusAnimation> LAMBDA_FLAMER_ANIMS = (stack, type) -> {
		switch(type) {
		case EQUIP: return new BusAnimation()
				.addBus("EQUIP", new BusAnimationSequence().addPos(-45, 0, 0, 0).addPos(0, 0, 0, 500, IType.SIN_DOWN));
		case RELOAD: return ResourceManager.flamethrower_anim.get("Reload");
		case INSPECT:
		case JAMMED: return new BusAnimation()
				.addBus("ROTATE", new BusAnimationSequence().addPos(0, 0, 45, 250, IType.SIN_FULL).addPos(0, 0, 45, 350).addPos(0, 0, -15, 150, IType.SIN_FULL).addPos(0, 0, 0, 100, IType.SIN_FULL));
		}
		
		return null;
	};

	@SuppressWarnings("incomplete-switch") public static BiFunction<ItemStack, AnimType, BusAnimation> LAMBDA_CHEMTHROWER_ANIMS = (stack, type) -> {
		switch(type) {
		case EQUIP: return new BusAnimation()
				.addBus("EQUIP", new BusAnimationSequence().addPos(-45, 0, 0, 0).addPos(0, 0, 0, 500, IType.SIN_DOWN));
		}
		
		return null;
	};
}
