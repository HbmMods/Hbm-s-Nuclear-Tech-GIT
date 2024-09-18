package com.hbm.items.weapon.sedna;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import com.hbm.handler.CasingEjector;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.LambdaContext;
import com.hbm.items.weapon.sedna.mags.IMagazine;

import net.minecraft.item.ItemStack;

public class Receiver {

	protected float baseDamage;
	protected int delayAfterFire;
	protected int roundsPerCycle = 1;
	protected boolean refireOnHold = false;
	protected CasingEjector ejector = null;
	protected int reloadDuration;
	protected IMagazine magazine;
	protected BiFunction<ItemStack, LambdaContext, Boolean> canFire;
	protected BiConsumer<ItemStack, LambdaContext> onFire;
	
	/* GETTERS */
	public float getBaseDamage(ItemStack stack) {		return this.baseDamage; }
	public int getDelayAfterFire(ItemStack stack) {		return this.delayAfterFire; }
	public int getRoundsPerCycle(ItemStack stack) {		return this.roundsPerCycle; }
	public boolean getRefireOnHold(ItemStack stack) {	return this.refireOnHold; }
	public CasingEjector getEjector(ItemStack stack) {	return this.ejector; }
	public int getReloadDuration(ItemStack stack) {		return this.reloadDuration; }
	public IMagazine getMagazine(ItemStack stack) {		return this.magazine; }
	
	public BiFunction<ItemStack, LambdaContext, Boolean> getCanFire(ItemStack stack) {	return this.canFire; }
	public BiConsumer<ItemStack, LambdaContext> getOnFire(ItemStack stack) {			return this.onFire; }

	/* SETTERS */
	public Receiver dmg(float dmg) {				this.baseDamage = dmg;									return this; }
	public Receiver delay(int delay) {				this.delayAfterFire = delay;							return this; }
	public Receiver rounds(int rounds) {			this.roundsPerCycle = rounds;							return this; }
	public Receiver auto(boolean auto) {			this.refireOnHold = auto;								return this; }
	public Receiver burst(CasingEjector ejector) {	this.ejector = ejector;									return this; }
	public Receiver reload(int delay) {				this.reloadDuration = delay;							return this; }
	public Receiver mag(IMagazine magazine) {		this.magazine = magazine;								return this; }
	
	public Receiver canFire(BiFunction<ItemStack, LambdaContext, Boolean> lambda) {	this.canFire = lambda;	return this; }
	public Receiver fire(BiConsumer<ItemStack, LambdaContext> lambda) {				this.onFire = lambda;	return this; }
}
