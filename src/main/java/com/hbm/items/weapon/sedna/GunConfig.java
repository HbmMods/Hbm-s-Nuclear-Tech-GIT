package com.hbm.items.weapon.sedna;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import com.hbm.items.weapon.sedna.ItemGunBaseNT.LambdaContext;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.HbmAnimations.AnimType;

import net.minecraft.item.ItemStack;

/**
 * Despite how complicated the GunConfig looks, it actually only exists to hold together a bunch of fields. Everything else is infrastructure for getting and setting.
 * The gun config determines general gun specific stats like durability, crosshair, animations, receivers, click handling and the decider.
 * 
 * @author hbm
 * */
public class GunConfig {

	public static final String O_RECEIVERS =					"O_RECEIVERS";
	public static final String F_DURABILITY =					"F_DURABILITY";
	public static final String I_DRAWDURATION =					"I_DRAWDURATION";
	public static final String O_CROSSHAIR =					"O_CROSSHAIR";
	public static final String B_RELOADANIMATIONSEQUENTIAL =	"B_RELOADANIMATIONSEQUENTIAL";
	public static final String CON_ONPRESSPRIMARY =				"CON_ONPRESSPRIMARY";
	public static final String CON_ONPRESSSECONDARY =			"CON_ONPRESSSECONDARY";
	public static final String CON_ONPRESSTERTIARY =			"CON_ONPRESSTERTIARY";
	public static final String CON_ONPRESSRELOAD =				"CON_ONPRESSRELOAD";
	public static final String CON_ONRELEASEPRIMARY =			"CON_ONRELEASEPRIMARY";
	public static final String CON_ONRELEASESECONDARY =			"CON_ONRELEASESECONDARY";
	public static final String CON_ONRELEASETERTIARY =			"CON_ONRELEASETERTIARY";
	public static final String CON_ONRELEASERELOAD =			"CON_ONRELEASERELOAD";
	public static final String CON_DECIDER =					"CON_DECIDER";
	public static final String FUN_ANIMNATIONS =				"FUN_ANIMNATIONS";
	
	/* FIELDS */
	
	/** List of receivers used by the gun, primary and secondary are usually indices 0 and 1 respectively, if applicable */
	protected Receiver[] receivers_DNA;
	protected float durability_DNA;
	protected int drawDuration_DNA = 0;
	protected Crosshair crosshair_DNA;
	protected boolean reloadAnimationsSequential_DNA;
	/** Lambda functions for clicking shit */
	protected BiConsumer<ItemStack, LambdaContext> onPressPrimary_DNA;
	protected BiConsumer<ItemStack, LambdaContext> onPressSecondary_DNA;
	protected BiConsumer<ItemStack, LambdaContext> onPressTertiary_DNA;
	protected BiConsumer<ItemStack, LambdaContext> onPressReload_DNA;
	/** Lambda functions for releasing the aforementioned shit */
	protected BiConsumer<ItemStack, LambdaContext> onReleasePrimary_DNA;
	protected BiConsumer<ItemStack, LambdaContext> onReleaseSecondary_DNA;
	protected BiConsumer<ItemStack, LambdaContext> onReleaseTertiary_DNA;
	protected BiConsumer<ItemStack, LambdaContext> onReleaseReload_DNA;
	/** The engine for the state machine that determines the gun's overall behavior */
	protected BiConsumer<ItemStack, LambdaContext> decider_DNA;
	/** Lambda that returns the relevant animation for the given params */
	protected BiFunction<ItemStack, AnimType, BusAnimation> animations_DNA;
	
	/* GETTERS */

	public Receiver[] getReceivers(ItemStack stack) {			return WeaponUpgradeManager.eval(receivers_DNA, stack, O_RECEIVERS, this); }
	public float getDurability(ItemStack stack) {				return WeaponUpgradeManager.eval(durability_DNA, stack, F_DURABILITY, this); }
	public int getDrawDuration(ItemStack stack) {				return WeaponUpgradeManager.eval(drawDuration_DNA, stack, I_DRAWDURATION, this); }
	public Crosshair getCrosshair(ItemStack stack) {			return WeaponUpgradeManager.eval(crosshair_DNA, stack, O_CROSSHAIR, this); }
	public boolean getReloadAnimSequential(ItemStack stack) {	return WeaponUpgradeManager.eval(reloadAnimationsSequential_DNA, stack, B_RELOADANIMATIONSEQUENTIAL, this); }

	public BiConsumer<ItemStack, LambdaContext> getPressPrimary(ItemStack stack) {		return WeaponUpgradeManager.eval(this.onPressPrimary_DNA, stack, CON_ONPRESSPRIMARY, this); }
	public BiConsumer<ItemStack, LambdaContext> getPressSecondary(ItemStack stack) {	return WeaponUpgradeManager.eval(this.onPressSecondary_DNA, stack, CON_ONPRESSSECONDARY, this); }
	public BiConsumer<ItemStack, LambdaContext> getPressTertiary(ItemStack stack) {		return WeaponUpgradeManager.eval(this.onPressTertiary_DNA, stack, CON_ONPRESSTERTIARY, this); }
	public BiConsumer<ItemStack, LambdaContext> getPressReload(ItemStack stack) {		return WeaponUpgradeManager.eval(this.onPressReload_DNA, stack, CON_ONPRESSRELOAD, this); }

	public BiConsumer<ItemStack, LambdaContext> getReleasePrimary(ItemStack stack) {	return WeaponUpgradeManager.eval(this.onReleasePrimary_DNA, stack, CON_ONRELEASEPRIMARY, this); }
	public BiConsumer<ItemStack, LambdaContext> getReleaseSecondary(ItemStack stack) {	return WeaponUpgradeManager.eval(this.onReleaseSecondary_DNA, stack, CON_ONRELEASESECONDARY, this); }
	public BiConsumer<ItemStack, LambdaContext> getReleaseTertiary(ItemStack stack) {	return WeaponUpgradeManager.eval(this.onReleaseTertiary_DNA, stack, CON_ONRELEASETERTIARY, this); }
	public BiConsumer<ItemStack, LambdaContext> getReleaseReload(ItemStack stack) {		return WeaponUpgradeManager.eval(this.onReleaseReload_DNA, stack, CON_ONRELEASERELOAD, this); }
	
	public BiConsumer<ItemStack, LambdaContext> getDecider(ItemStack stack) {			return WeaponUpgradeManager.eval(this.decider_DNA, stack, CON_DECIDER, this); }
	
	public BiFunction<ItemStack, AnimType, BusAnimation> getAnims(ItemStack stack) {	return WeaponUpgradeManager.eval(this.animations_DNA, stack, FUN_ANIMNATIONS, this); }
	
	/* SETTERS */
	
	public GunConfig rec(Receiver... receivers) {		this.receivers_DNA = receivers; return this; }
	public GunConfig dura(float dura) {					this.durability_DNA = dura; return this; }
	public GunConfig draw(int draw) {					this.drawDuration_DNA = draw; return this; }
	public GunConfig crosshair(Crosshair crosshair) {	this.crosshair_DNA = crosshair; return this; }
	
	//press
	public GunConfig pp(BiConsumer<ItemStack, LambdaContext> lambda) { this.onPressPrimary_DNA = lambda;	return this; }
	public GunConfig ps(BiConsumer<ItemStack, LambdaContext> lambda) { this.onPressSecondary_DNA = lambda;	return this; }
	public GunConfig pt(BiConsumer<ItemStack, LambdaContext> lambda) { this.onPressTertiary_DNA = lambda;	return this; }
	public GunConfig pr(BiConsumer<ItemStack, LambdaContext> lambda) { this.onPressReload_DNA = lambda;		return this; }

	//release
	public GunConfig rp(BiConsumer<ItemStack, LambdaContext> lambda) { this.onReleasePrimary_DNA = lambda;		return this; }
	public GunConfig rs(BiConsumer<ItemStack, LambdaContext> lambda) { this.onReleaseSecondary_DNA = lambda;	return this; }
	public GunConfig rt(BiConsumer<ItemStack, LambdaContext> lambda) { this.onReleaseTertiary_DNA = lambda;		return this; }
	public GunConfig rr(BiConsumer<ItemStack, LambdaContext> lambda) { this.onReleaseReload_DNA = lambda;		return this; }
	
	//decider
	public GunConfig decider(BiConsumer<ItemStack, LambdaContext> lambda) { this.decider_DNA = lambda;	return this; }
	
	//anims
	public GunConfig anim(BiFunction<ItemStack, AnimType, BusAnimation> lambda) { this.animations_DNA = lambda;	return this; }
}
