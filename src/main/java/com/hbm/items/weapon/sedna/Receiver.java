package com.hbm.items.weapon.sedna;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import com.hbm.handler.CasingEjector;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.LambdaContext;
import com.hbm.items.weapon.sedna.mags.IMagazine;

import net.minecraft.item.ItemStack;

/**
 * Receivers are the gun's "moving parts", i.e. they determine things like base damage, spread, the ejector and the magazine. Think of this class like the
 * barrel, receiver and chamber of the gun, an underbarrel grenade launcher for example would be a separate receiver instance compared to the regular gun it is attached to.
 * 
 * @author hbm
 */
public class Receiver {

	public static final String F_BASEDAMAGE =		"F_BASEDAMAGE";
	public static final String I_DELAYAFTERFIRE =	"I_DELAYAFTERFIRE";
	public static final String I_ROUNDSPERCYCLE =	"I_ROUNDSPERCYCLE";
	public static final String F_SPREADMOD =		"F_SPREADMOD";
	public static final String B_REFIREONHOLD =		"B_REFIREONHOLD";
	public static final String O_EJECTOR =			"O_EJECTOR";
	public static final String I_RELOADDURATION =	"I_RELOADDURATION";
	public static final String S_FIRESOUND =		"S_FIRESOUND";
	public static final String F_FIREVOLUME =		"F_FIREVOLUME";
	public static final String F_FIREPITCH =		"F_FIREPITCH";
	public static final String O_MAGAZINE =			"O_MAGAZINE";
	public static final String FUN_CANFIRE =		"FUN_CANFIRE";
	public static final String CON_ONFIRE =			"CON_ONFIRE";
	
	public Receiver(int index) {
		this.index = index;
	}
	
	protected int index;
	protected float baseDamage_DNA;
	protected int delayAfterFire_DNA;
	protected int roundsPerCycle_DNA = 1;
	protected float spreadModExtra_DNA = 0F;
	protected boolean refireOnHold_DNA = false;
	protected CasingEjector ejector_DNA = null;
	protected int reloadDuration_DNA;
	protected String fireSound_DNA;
	protected float fireVolume_DNA = 1.0F;
	protected float firePitch_DNA = 1.0F;
	protected IMagazine magazine_DNA;
	protected BiFunction<ItemStack, LambdaContext, Boolean> canFire_DNA;
	protected BiConsumer<ItemStack, LambdaContext> onFire_DNA;
	
	/* GETTERS */
	public float getBaseDamage(ItemStack stack) {		return WeaponUpgradeManager.eval(this.baseDamage_DNA, stack, F_BASEDAMAGE, this); }
	public int getDelayAfterFire(ItemStack stack) {		return WeaponUpgradeManager.eval(this.delayAfterFire_DNA, stack, I_DELAYAFTERFIRE, this); }
	public int getRoundsPerCycle(ItemStack stack) {		return WeaponUpgradeManager.eval(this.roundsPerCycle_DNA, stack, I_ROUNDSPERCYCLE, this); }
	public float getSpreadMod(ItemStack stack) {		return WeaponUpgradeManager.eval(this.spreadModExtra_DNA, stack, F_SPREADMOD, this); }
	public boolean getRefireOnHold(ItemStack stack) {	return WeaponUpgradeManager.eval(this.refireOnHold_DNA, stack, B_REFIREONHOLD, this); }
	public CasingEjector getEjector(ItemStack stack) {	return WeaponUpgradeManager.eval(this.ejector_DNA, stack, O_EJECTOR, this); }
	public int getReloadDuration(ItemStack stack) {		return WeaponUpgradeManager.eval(this.reloadDuration_DNA, stack, I_RELOADDURATION, this); }
	public String getFireSound(ItemStack stack) {		return WeaponUpgradeManager.eval(this.fireSound_DNA, stack, S_FIRESOUND, this); }
	public float getFireVolume(ItemStack stack) {		return WeaponUpgradeManager.eval(this.fireVolume_DNA, stack, F_FIREVOLUME, this); }
	public float getFirePitch(ItemStack stack) {		return WeaponUpgradeManager.eval(this.firePitch_DNA, stack, F_FIREPITCH, this); }
	public IMagazine getMagazine(ItemStack stack) {		return WeaponUpgradeManager.eval(this.magazine_DNA, stack, O_MAGAZINE, this); }
	
	public BiFunction<ItemStack, LambdaContext, Boolean> getCanFire(ItemStack stack) {	return WeaponUpgradeManager.eval(this.canFire_DNA, stack, FUN_CANFIRE, this); }
	public BiConsumer<ItemStack, LambdaContext> getOnFire(ItemStack stack) {			return WeaponUpgradeManager.eval(this.onFire_DNA, stack, CON_ONFIRE, this); }

	/* SETTERS */
	public Receiver dmg(float dmg) {				this.baseDamage_DNA = dmg;									return this; }
	public Receiver delay(int delay) {				this.delayAfterFire_DNA = delay;							return this; }
	public Receiver rounds(int rounds) {			this.roundsPerCycle_DNA = rounds;							return this; }
	public Receiver spread(int spread) {			this.spreadModExtra_DNA = spread;								return this; }
	public Receiver auto(boolean auto) {			this.refireOnHold_DNA = auto;								return this; }
	public Receiver burst(CasingEjector ejector) {	this.ejector_DNA = ejector;									return this; }
	public Receiver reload(int delay) {				this.reloadDuration_DNA = delay;							return this; }
	public Receiver mag(IMagazine magazine) {		this.magazine_DNA = magazine;								return this; }
	
	public Receiver canFire(BiFunction<ItemStack, LambdaContext, Boolean> lambda) {	this.canFire_DNA = lambda;	return this; }
	public Receiver fire(BiConsumer<ItemStack, LambdaContext> lambda) {				this.onFire_DNA = lambda;	return this; }

	public Receiver sound(String sound, float volume, float pitch) {
		this.fireSound_DNA = sound;
		this.fireVolume_DNA = volume;
		this.firePitch_DNA = pitch;
		return this;
	}
}
