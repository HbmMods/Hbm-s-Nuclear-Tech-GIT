package com.hbm.items.weapon.sedna.factory;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import com.hbm.config.ServerConfig;
import com.hbm.config.RunningConfig.ConfigWrapper;
import com.hbm.items.ModItems;
import com.hbm.items.armor.IPAMelee;
import com.hbm.items.armor.IPARanged;
import com.hbm.items.armor.IPAWeaponsProvider;
import com.hbm.items.weapon.sedna.Crosshair;
import com.hbm.items.weapon.sedna.GunConfig;
import com.hbm.items.weapon.sedna.Receiver;
import com.hbm.lib.RefStrings;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.GunState;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.LambdaContext;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.WeaponQuality;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.AnimationEnums.GunAnimation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/** Power-armor conditional weapons (melee controller and ranged attack remote) */
public class XFactoryPA {

	public static ConfigWrapper<Float> MELEE_RPA_SWINGS_DAMAGE =	new ConfigWrapper(15F);
	public static ConfigWrapper<Float> MELEE_RPA_SLAP_DAMAGE =		new ConfigWrapper(35F);
	public static ConfigWrapper<Float> MELEE_RPA_LARGE_MULT =		new ConfigWrapper(2.5F);
	public static ConfigWrapper<Float> MELEE_NCRPA_SWINGS_DAMAGE =	new ConfigWrapper(15F);
	public static ConfigWrapper<Float> MELEE_NCRPA_SWEEP_DAMAGE =	new ConfigWrapper(35F);
	public static ConfigWrapper<Float> MELEE_NCRPA_LARGE_MULT =		new ConfigWrapper(2.5F);
	public static ConfigWrapper<Float> RANGED_NCRPA_DAMAGE =		new ConfigWrapper(25F);
	
	public static void initConfig() {
		ServerConfig.configMap.put("MELEE_RPA_SWINGS_DAMAGE", MELEE_RPA_SWINGS_DAMAGE);
		ServerConfig.configMap.put("MELEE_RPA_SLAP_DAMAGE", MELEE_RPA_SLAP_DAMAGE);
		ServerConfig.configMap.put("MELEE_RPA_LARGE_MULT", MELEE_RPA_LARGE_MULT);
		ServerConfig.configMap.put("MELEE_NCRPA_SWINGS_DAMAGE", MELEE_NCRPA_SWINGS_DAMAGE);
		ServerConfig.configMap.put("MELEE_NCRPA_SWEEP_DAMAGE", MELEE_NCRPA_SWEEP_DAMAGE);
		ServerConfig.configMap.put("MELEE_NCRPA_LARGE_MULT", MELEE_NCRPA_LARGE_MULT);
		ServerConfig.configMap.put("RANGED_NCRPA_DAMAGE", RANGED_NCRPA_DAMAGE);
	}

	public static void init() {

		ModItems.gun_pa_melee = new ItemGunPA(WeaponQuality.UTILITY, new GunConfig()
				.draw(10).crosshair(Crosshair.NONE)
				.rec(new Receiver(0))
				.pp(LAMBDA_CLICK_MELEE_PRIMARY).ps(LAMBDA_CLICK_MELEE_SENONDARY).decider(GunStateDecider.LAMBDA_STANDARD_DECIDER)
				.anim(LAMBDA_MELEE_ANIMS).orchestra(ORCHESTRA)
				).setUnlocalizedName("gun_pa_melee");

		ModItems.gun_pa_ranged = new ItemGunPA(WeaponQuality.UTILITY, new GunConfig()
				.draw(0).crosshair(Crosshair.CROSS)
				.rec(new Receiver(0))
				.pp(LAMBDA_CLICK_RANGED_PRIMARY).ps(LAMBDA_CLICK_RANGED_SENONDARY).decider(GunStateDecider.LAMBDA_STANDARD_DECIDER)
				).setUnlocalizedName("gun_pa_ranged").setFull3D().setTextureName(RefStrings.MODID + ":gun_pa_ranged");
	}

	public static BiConsumer<ItemStack, LambdaContext> ORCHESTRA = (stack, ctx) -> {
		IPAMelee component = IPAWeaponsProvider.getMeleeComponentCommon(ctx.getPlayer());
		if(component != null) component.orchestra(stack, ctx);
	};
	
	public static BiFunction<ItemStack, GunAnimation, BusAnimation> LAMBDA_MELEE_ANIMS = (stack, type) -> {
		IPAMelee component = IPAWeaponsProvider.getMeleeComponentClient();
		if(component != null) return component.playAnim(stack, type);
		return null;
	};
	
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_CLICK_MELEE_PRIMARY = (stack, ctx) -> {
		IPAMelee component = IPAWeaponsProvider.getMeleeComponentCommon(ctx.getPlayer());
		if(component != null) component.clickPrimary(stack, ctx);
	};
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_CLICK_MELEE_SENONDARY = (stack, ctx) -> {
		IPAMelee component = IPAWeaponsProvider.getMeleeComponentCommon(ctx.getPlayer());
		if(component != null) component.clickSecondary(stack, ctx);
	};
	
	public static void doSwing(ItemStack stack, LambdaContext ctx, GunAnimation anim, int cooldown) {

		EntityPlayer player = ctx.getPlayer();
		int index = ctx.configIndex;
		GunState state = ItemGunBaseNT.getState(stack, index);

		if(state == GunState.IDLE) {
			ItemGunBaseNT.playAnimation(player, stack, anim, ctx.configIndex);
			ItemGunBaseNT.setState(stack, index, GunState.COOLDOWN);
			ItemGunBaseNT.setTimer(stack, index, cooldown);
		}
	}
	
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_CLICK_RANGED_PRIMARY = (stack, ctx) -> {
		IPARanged component = IPAWeaponsProvider.getRangedComponentCommon(ctx.getPlayer());
		if(component != null) component.clickPrimary(stack, ctx);
	};
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_CLICK_RANGED_SENONDARY = (stack, ctx) -> {
		IPARanged component = IPAWeaponsProvider.getRangedComponentCommon(ctx.getPlayer());
		if(component != null) component.clickSecondary(stack, ctx);
	};
	
	public static class ItemGunPA extends ItemGunBaseNT {

		public ItemGunPA(WeaponQuality quality, GunConfig... cfg) {
			super(quality, cfg);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) { }
	}
}
