package com.hbm.items.weapon.sedna;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import com.hbm.items.weapon.sedna.ItemGunBaseNT.LambdaContext;
import com.hbm.items.weapon.sedna.factory.Lego;
import com.hbm.items.weapon.sedna.mags.IMagazine;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;

/**
 * Receivers are the gun's "moving parts", i.e. they determine things like base damage, spread, the ejector and the magazine. Think of this class like the
 * barrel, receiver and chamber of the gun, an underbarrel grenade launcher for example would be a separate receiver instance compared to the regular gun it is attached to.
 * 
 * @author hbm
 */
public class Receiver {

	public static final String F_BASEDAMAGE =				"F_BASEDAMAGE";
	public static final String I_DELAYAFTERFIRE =			"I_DELAYAFTERFIRE";
	public static final String I_DELAYAFTERDRYFIRE =		"I_DELAYAFTERDRYFIRE";
	public static final String I_ROUNDSPERCYCLE =			"I_ROUNDSPERCYCLE";
	public static final String F_SPRADINNATE =				"F_SPRADINNATE";
	public static final String F_SPREADAMMO =				"F_SPREADAMMO";
	public static final String F_SPREADHIPFIRE =			"F_SPREADHIPFIRE";
	public static final String F_SPREADDURABILITY =			"F_SPREADDURABILITY";
	public static final String B_REFIREONHOLD =				"B_REFIREONHOLD";
	public static final String B_REFIREAFTERDRY =			"B_REFIREAFTERDRY";
	public static final String B_DOESDRYFIRE =				"B_DOESDRYFIRE";
	public static final String B_DOESDRYFIREAFTERAUTO =		"B_DOESDRYFIREAFTERAUTO";
	public static final String B_EJECTONFIRE =				"B_EJECTONFIRE";
	public static final String B_RELOADONEMPTY =			"B_RELOADONEMPTY";
	public static final String I_RELOADBEGINDURATION =		"I_RELOADBEGINDURATION";
	public static final String I_RELOADCYCLEDURATION =		"I_RELOADCYCLEDURATION";
	public static final String I_RELOADENDDURATION =		"I_RELOADENDDURATION";
	public static final String I_RELOADCOCKONEMPTYPRE =		"I_RELOADCOCKONEMPTYPRE";
	public static final String I_RELOADCOCKONEMPTYPOST =	"I_RELOADCOCKONEMPTYPOST";
	public static final String I_JAMDURATION =				"I_JAMDURATION";
	public static final String S_FIRESOUND =				"S_FIRESOUND";
	public static final String F_FIREVOLUME =				"F_FIREVOLUME";
	public static final String F_FIREPITCH =				"F_FIREPITCH";
	public static final String O_MAGAZINE =					"O_MAGAZINE";
	public static final String O_PROJECTILEOFFSET =			"O_PROJECTILEOFFSET";
	public static final String O_PROJECTILEOFFSETSCOPED =	"O_PROJECTILEOFFSETSCOPED";
	public static final String FUN_CANFIRE =				"FUN_CANFIRE";
	public static final String CON_ONFIRE =					"CON_ONFIRE";
	public static final String CON_ONRECOIL =				"CON_ONRECOIL";
	
	public Receiver(int index) {
		this.index = index;
	}
	
	protected int index;
	protected float baseDamage_DNA;
	protected int delayAfterFire_DNA;
	protected int delayAfterDryFire_DNA;
	protected int roundsPerCycle_DNA = 1;
	protected float spreadInnate_DNA = 0F;
	protected float spreadMultAmmo_DNA = 1F;
	protected float spreadPenaltyHipfire_DNA = 0.025F;
	protected float spreadDurability_DNA = 0.125F;
	protected boolean refireOnHold_DNA = false;
	protected boolean refireAfterDry_DNA = false;
	protected boolean doesDryFire_DNA = true;
	protected boolean doesDryFireAfterAuto_DNA = false;
	protected boolean ejectOnFire_DNA = true;
	protected boolean reloadOnEmpty_DNA = false;
	protected int reloadBeginDuration_DNA;
	protected int reloadCycleDuration_DNA;
	protected int reloadEndDuration_DNA;
	protected int reloadCockOnEmptyPre_DNA;
	protected int reloadCockOnEmptyPost_DNA;
	protected int jamDuration_DNA = 0;
	protected String fireSound_DNA;
	protected float fireVolume_DNA = 1.0F;
	protected float firePitch_DNA = 1.0F;
	protected IMagazine magazine_DNA;
	protected Vec3 projectileOffset_DNA = Vec3.createVectorHelper(0, 0, 0);
	protected Vec3 projectileOffsetScoped_DNA = Vec3.createVectorHelper(0, 0, 0);
	protected BiFunction<ItemStack, LambdaContext, Boolean> canFire_DNA;
	protected BiConsumer<ItemStack, LambdaContext> onFire_DNA;
	protected BiConsumer<ItemStack, LambdaContext> onRecoil_DNA;
	
	/* GETTERS */
	public float getBaseDamage(ItemStack stack) {				return WeaponUpgradeManager.eval(this.baseDamage_DNA, stack, F_BASEDAMAGE, this); }
	public int getDelayAfterFire(ItemStack stack) {				return WeaponUpgradeManager.eval(this.delayAfterFire_DNA, stack, I_DELAYAFTERFIRE, this); }
	public int getDelayAfterDryFire(ItemStack stack) {			return WeaponUpgradeManager.eval(this.delayAfterDryFire_DNA, stack, I_DELAYAFTERDRYFIRE, this); }
	public int getRoundsPerCycle(ItemStack stack) {				return WeaponUpgradeManager.eval(this.roundsPerCycle_DNA, stack, I_ROUNDSPERCYCLE, this); }
	public float getInnateSpread(ItemStack stack) {				return WeaponUpgradeManager.eval(this.spreadInnate_DNA, stack, F_SPRADINNATE, this); }
	public float getAmmoSpread(ItemStack stack) {				return WeaponUpgradeManager.eval(this.spreadMultAmmo_DNA, stack, F_SPREADAMMO, this); }
	public float getHipfireSpread(ItemStack stack) {			return WeaponUpgradeManager.eval(this.spreadPenaltyHipfire_DNA, stack, F_SPREADHIPFIRE, this); }
	public float getDurabilitySpread(ItemStack stack) {			return WeaponUpgradeManager.eval(this.spreadDurability_DNA, stack, F_SPREADDURABILITY, this); }
	public boolean getRefireOnHold(ItemStack stack) {			return WeaponUpgradeManager.eval(this.refireOnHold_DNA, stack, B_REFIREONHOLD, this); }
	public boolean getRefireAfterDry(ItemStack stack) {			return WeaponUpgradeManager.eval(this.refireAfterDry_DNA, stack, B_REFIREAFTERDRY, this); }
	public boolean getDoesDryFire(ItemStack stack) {			return WeaponUpgradeManager.eval(this.doesDryFire_DNA, stack, B_DOESDRYFIRE, this); }
	public boolean getDoesDryFireAfterAuto(ItemStack stack) {	return WeaponUpgradeManager.eval(this.doesDryFireAfterAuto_DNA, stack, B_DOESDRYFIREAFTERAUTO, this); }
	public boolean getEjectOnFire(ItemStack stack) {			return WeaponUpgradeManager.eval(this.ejectOnFire_DNA, stack, B_EJECTONFIRE, this); }
	public boolean getReloadOnEmpty(ItemStack stack) {			return WeaponUpgradeManager.eval(this.reloadOnEmpty_DNA, stack, B_RELOADONEMPTY, this); }
	public int getReloadBeginDuration(ItemStack stack) {		return WeaponUpgradeManager.eval(this.reloadBeginDuration_DNA, stack, I_RELOADBEGINDURATION, this); }
	public int getReloadCycleDuration(ItemStack stack) {		return WeaponUpgradeManager.eval(this.reloadCycleDuration_DNA, stack, I_RELOADCYCLEDURATION, this); }
	public int getReloadEndDuration(ItemStack stack) {			return WeaponUpgradeManager.eval(this.reloadEndDuration_DNA, stack, I_RELOADENDDURATION, this); }
	public int getReloadCockOnEmptyPre(ItemStack stack) {		return WeaponUpgradeManager.eval(this.reloadCockOnEmptyPre_DNA, stack, I_RELOADCOCKONEMPTYPRE, this); }
	public int getReloadCockOnEmptyPost(ItemStack stack) {		return WeaponUpgradeManager.eval(this.reloadCockOnEmptyPost_DNA, stack, I_RELOADCOCKONEMPTYPOST, this); }
	public int getJamDuration(ItemStack stack) {				return WeaponUpgradeManager.eval(this.jamDuration_DNA, stack, I_JAMDURATION, this); }
	public String getFireSound(ItemStack stack) {				return WeaponUpgradeManager.eval(this.fireSound_DNA, stack, S_FIRESOUND, this); }
	public float getFireVolume(ItemStack stack) {				return WeaponUpgradeManager.eval(this.fireVolume_DNA, stack, F_FIREVOLUME, this); }
	public float getFirePitch(ItemStack stack) {				return WeaponUpgradeManager.eval(this.firePitch_DNA, stack, F_FIREPITCH, this); }
	public IMagazine getMagazine(ItemStack stack) {				return WeaponUpgradeManager.eval(this.magazine_DNA, stack, O_MAGAZINE, this); }
	public Vec3 getProjectileOffset(ItemStack stack) {			return WeaponUpgradeManager.eval(this.projectileOffset_DNA, stack, O_PROJECTILEOFFSET, this); }
	public Vec3 getProjectileOffsetScoped(ItemStack stack) {	return WeaponUpgradeManager.eval(this.projectileOffsetScoped_DNA, stack, O_PROJECTILEOFFSETSCOPED, this); }
	
	public BiFunction<ItemStack, LambdaContext, Boolean> getCanFire(ItemStack stack) {	return WeaponUpgradeManager.eval(this.canFire_DNA, stack, FUN_CANFIRE, this); }
	public BiConsumer<ItemStack, LambdaContext> getOnFire(ItemStack stack) {			return WeaponUpgradeManager.eval(this.onFire_DNA, stack, CON_ONFIRE, this); }
	public BiConsumer<ItemStack, LambdaContext> getRecoil(ItemStack stack) {			return WeaponUpgradeManager.eval(this.onRecoil_DNA, stack, CON_ONRECOIL, this); }

	/* SETTERS */
	public Receiver dmg(float dmg) {								this.baseDamage_DNA = dmg;											return this; }
	public Receiver delay(int delay) {								this.delayAfterFire_DNA = this.delayAfterDryFire_DNA = delay;		return this; }
	public Receiver dry(int delay) {								this.delayAfterDryFire_DNA = delay;									return this; }
	public Receiver rounds(int rounds) {							this.roundsPerCycle_DNA = rounds;									return this; }
	public Receiver spread(float spread) {							this.spreadInnate_DNA = spread;										return this; }
	public Receiver spreadAmmo(float spread) {						this.spreadMultAmmo_DNA = spread;									return this; }
	public Receiver spreadHipfire(float spread) {					this.spreadPenaltyHipfire_DNA = spread;								return this; }
	public Receiver spreadDurability(float spread) {				this.spreadDurability_DNA = spread;									return this; }
	public Receiver auto(boolean auto) {							this.refireOnHold_DNA = auto;										return this; }
	public Receiver autoAfterDry(boolean auto) {					this.refireAfterDry_DNA = auto;										return this; }
	public Receiver dryfire(boolean dryfire) {						this.doesDryFire_DNA = dryfire;										return this; }
	public Receiver dryfireAfterAuto(boolean dryfire) {				this.doesDryFireAfterAuto_DNA = dryfire;							return this; }
	public Receiver ejectOnFire(boolean eject) {					this.ejectOnFire_DNA = eject;										return this; }
	public Receiver reloadOnEmpty(boolean reload) {					this.reloadOnEmpty_DNA = reload;									return this; }
	public Receiver mag(IMagazine magazine) {						this.magazine_DNA = magazine;										return this; }
	public Receiver offset(double f, double u, double s) {			this.projectileOffset_DNA = Vec3.createVectorHelper(f, u, s); this.projectileOffsetScoped_DNA = Vec3.createVectorHelper(f, u, 0);	return this; }
	public Receiver offsetScoped(double f, double u, double s) {	this.projectileOffsetScoped_DNA = Vec3.createVectorHelper(f, u, s);	return this; }
	public Receiver jam(int jam) {									this.jamDuration_DNA = jam;											return this; }
	
	public Receiver reload(int delay) { return reload(0, delay, delay, 0, 0); }
	public Receiver reload(int begin, int cycle, int end, int cock) { return reload(0, begin, cycle, end, cock); }
	public Receiver reload(int pre, int begin, int cycle, int end, int post) {
		this.reloadBeginDuration_DNA = begin;
		this.reloadCycleDuration_DNA = cycle;
		this.reloadEndDuration_DNA = end;
		this.reloadCockOnEmptyPre_DNA = pre;
		this.reloadCockOnEmptyPost_DNA = post;
		return this;
	}

	public Receiver canFire(BiFunction<ItemStack, LambdaContext, Boolean> lambda) {	this.canFire_DNA = lambda;	return this; }
	public Receiver fire(BiConsumer<ItemStack, LambdaContext> lambda) {				this.onFire_DNA = lambda;	return this; }
	public Receiver recoil(BiConsumer<ItemStack, LambdaContext> lambda) {			this.onRecoil_DNA = lambda;	return this; }

	public Receiver sound(String sound, float volume, float pitch) {
		this.fireSound_DNA = sound;
		this.fireVolume_DNA = volume;
		this.firePitch_DNA = pitch;
		return this;
	}
	
	public Receiver setupStandardFire() {	return this.canFire(Lego.LAMBDA_STANDARD_CAN_FIRE)	.fire(Lego.LAMBDA_STANDARD_FIRE); }
	public Receiver setupLockonFire() {		return this.canFire(Lego.LAMBDA_LOCKON_CAN_FIRE)	.fire(Lego.LAMBDA_STANDARD_FIRE); }
}
