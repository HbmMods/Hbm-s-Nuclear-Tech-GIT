package com.hbm.items.weapon.sedna;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import com.hbm.items.weapon.sedna.ItemGunBaseNT.LambdaContext;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.HbmAnimations.AnimType;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

import net.minecraft.item.ItemStack;

public class GunConfig {
	
	/* FIELDS */
	
	/** List of receivers used by the gun, primary and secondary are usually indices 0 and 1 respectively, if applicable */
	protected Receiver[] receivers;
	protected float durability;
	protected int drawDuration = 0;
	protected Crosshair crosshair;
	protected boolean reloadAnimationsSequential;
	/** Lambda functions for clicking shit */
	protected BiConsumer<ItemStack, LambdaContext> onPressPrimary;
	protected BiConsumer<ItemStack, LambdaContext> onPressSecondary;
	protected BiConsumer<ItemStack, LambdaContext> onPressTertiary;
	protected BiConsumer<ItemStack, LambdaContext> onPressReload;
	/** Lambda functions for releasing the aforementioned shit */
	protected BiConsumer<ItemStack, LambdaContext> onReleasePrimary;
	protected BiConsumer<ItemStack, LambdaContext> onReleaseSecondary;
	protected BiConsumer<ItemStack, LambdaContext> onReleaseTertiary;
	protected BiConsumer<ItemStack, LambdaContext> onReleaseReload;
	/** The engine for the state machine that determines the gun's overall behavior */
	protected BiConsumer<ItemStack, LambdaContext> decider;
	/** Lambda that returns the relevant animation for the given params */
	protected BiFunction<ItemStack, AnimType, BusAnimation> animations;
	
	/* GETTERS */

	public Receiver[] getReceivers(ItemStack stack) {			return receivers; }
	public float getDurability(ItemStack stack) {				return durability; }
	public int getDrawDuration(ItemStack stack) {				return drawDuration; }
	public Crosshair getCrosshair(ItemStack stack) {			return crosshair; }
	public boolean getReloadAnimSequential(ItemStack stack) {	return reloadAnimationsSequential; }

	public BiConsumer<ItemStack, LambdaContext> getPressPrimary(ItemStack stack) {		return this.onPressPrimary; }
	public BiConsumer<ItemStack, LambdaContext> getPressSecondary(ItemStack stack) {	return this.onPressSecondary; }
	public BiConsumer<ItemStack, LambdaContext> getPressTertiary(ItemStack stack) {		return this.onPressTertiary; }
	public BiConsumer<ItemStack, LambdaContext> getPressReload(ItemStack stack) {		return this.onPressReload; }

	public BiConsumer<ItemStack, LambdaContext> getReleasePrimary(ItemStack stack) {	return this.onReleasePrimary; }
	public BiConsumer<ItemStack, LambdaContext> getReleaseSecondary(ItemStack stack) {	return this.onReleaseSecondary; }
	public BiConsumer<ItemStack, LambdaContext> getReleaseTertiary(ItemStack stack) {	return this.onReleaseTertiary; }
	public BiConsumer<ItemStack, LambdaContext> getReleaseReload(ItemStack stack) {		return this.onReleaseReload; }
	
	public BiConsumer<ItemStack, LambdaContext> getDecider(ItemStack stack) {			return this.decider; }
	
	public BiFunction<ItemStack, AnimType, BusAnimation> getAnims(ItemStack stack) {	return this.animations; }
	
	/* SETTERS */
	
	public GunConfig rec(Receiver... receivers) {		this.receivers = receivers; return this; }
	public GunConfig dura(float dura) {					this.durability = dura; return this; }
	public GunConfig draw(int draw) {					this.drawDuration = draw; return this; }
	public GunConfig crosshair(Crosshair crosshair) {	this.crosshair = crosshair; return this; }
	
	//press
	public GunConfig pp(BiConsumer<ItemStack, LambdaContext> lambda) { this.onPressPrimary = lambda;	return this; }
	public GunConfig ps(BiConsumer<ItemStack, LambdaContext> lambda) { this.onPressSecondary = lambda;	return this; }
	public GunConfig pt(BiConsumer<ItemStack, LambdaContext> lambda) { this.onPressTertiary = lambda;	return this; }
	public GunConfig pr(BiConsumer<ItemStack, LambdaContext> lambda) { this.onPressReload = lambda;		return this; }

	//release
	public GunConfig rp(BiConsumer<ItemStack, LambdaContext> lambda) { this.onReleasePrimary = lambda;		return this; }
	public GunConfig rs(BiConsumer<ItemStack, LambdaContext> lambda) { this.onReleaseSecondary = lambda;	return this; }
	public GunConfig rt(BiConsumer<ItemStack, LambdaContext> lambda) { this.onReleaseTertiary = lambda;		return this; }
	public GunConfig rr(BiConsumer<ItemStack, LambdaContext> lambda) { this.onReleaseReload = lambda;		return this; }
	
	//decider
	public GunConfig decider(BiConsumer<ItemStack, LambdaContext> lambda) { this.decider = lambda;	return this; }
	
	//anims
	public GunConfig anim(BiFunction<ItemStack, AnimType, BusAnimation> lambda) { this.animations = lambda;	return this; }
}
