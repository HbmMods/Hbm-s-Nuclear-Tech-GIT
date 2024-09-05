package com.hbm.items.weapon.sedna;

import java.util.function.BiConsumer;

import com.hbm.render.util.RenderScreenOverlay.Crosshair;

import net.minecraft.item.ItemStack;

public class GunConfig {
	
	/* FIELDS */
	
	/** List of receivers used by the gun, primary and secondary are usually indices 0 and 1 respectively, if applicable */
	protected Receiver[] receivers;
	protected float durability;
	protected int drawDuration = 0;
	protected Crosshair crosshair;
	/** Lambda functions for clicking shit */
	protected BiConsumer<ItemStack, GunConfig> onPressPrimary;
	protected BiConsumer<ItemStack, GunConfig> onPressSecondary;
	protected BiConsumer<ItemStack, GunConfig> onPressTertiary;
	protected BiConsumer<ItemStack, GunConfig> onPressReload;
	/** Lambda functions for releasing the aforementioned shit */
	protected BiConsumer<ItemStack, GunConfig> onReleasePrimary;
	protected BiConsumer<ItemStack, GunConfig> onReleaseSecondary;
	protected BiConsumer<ItemStack, GunConfig> onReleaseTertiary;
	protected BiConsumer<ItemStack, GunConfig> onReleaseReload;
	
	/* GETTERS */

	public Receiver[] getReceivers(ItemStack stack) {	return receivers; }
	public float getDurability(ItemStack stack) {		return durability; }
	public int getDrawDuration(ItemStack stack) {		return drawDuration; }
	public Crosshair getCrosshair(ItemStack stack) {	return crosshair; }

	public BiConsumer<ItemStack, GunConfig> getPressPrimary(ItemStack stack) {		return this.onPressPrimary; }
	public BiConsumer<ItemStack, GunConfig> getPressSecondary(ItemStack stack) {	return this.onPressSecondary; }
	public BiConsumer<ItemStack, GunConfig> getPressTertiary(ItemStack stack) {		return this.onPressTertiary; }
	public BiConsumer<ItemStack, GunConfig> getPressReload(ItemStack stack) {		return this.onPressReload; }

	public BiConsumer<ItemStack, GunConfig> getReleasePrimary(ItemStack stack) {	return this.onReleasePrimary; }
	public BiConsumer<ItemStack, GunConfig> getReleaseSecondary(ItemStack stack) {	return this.onReleaseSecondary; }
	public BiConsumer<ItemStack, GunConfig> getReleaseTertiary(ItemStack stack) {	return this.onReleaseTertiary; }
	public BiConsumer<ItemStack, GunConfig> getReleaseReload(ItemStack stack) {		return this.onReleaseReload; }
	
	/* SETTERS */
	
	public GunConfig rec(Receiver... receivers) {		this.receivers = receivers; return this; }
	public GunConfig dura(float dura) {					this.durability = dura; return this; }
	public GunConfig draw(int draw) {					this.drawDuration = draw; return this; }
	public GunConfig crosshair(Crosshair crosshair) {	this.crosshair = crosshair; return this; }
	
	public GunConfig pp(BiConsumer<ItemStack, GunConfig> lambda) { this.onPressPrimary = lambda;	return this; }
	public GunConfig ps(BiConsumer<ItemStack, GunConfig> lambda) { this.onPressSecondary = lambda;	return this; }
	public GunConfig pt(BiConsumer<ItemStack, GunConfig> lambda) { this.onPressTertiary = lambda;	return this; }
	public GunConfig pr(BiConsumer<ItemStack, GunConfig> lambda) { this.onPressReload = lambda;		return this; }

	public GunConfig rp(BiConsumer<ItemStack, GunConfig> lambda) { this.onReleasePrimary = lambda;		return this; }
	public GunConfig rs(BiConsumer<ItemStack, GunConfig> lambda) { this.onReleaseSecondary = lambda;	return this; }
	public GunConfig rt(BiConsumer<ItemStack, GunConfig> lambda) { this.onReleaseTertiary = lambda;		return this; }
	public GunConfig rr(BiConsumer<ItemStack, GunConfig> lambda) { this.onReleaseReload = lambda;		return this; }
}
