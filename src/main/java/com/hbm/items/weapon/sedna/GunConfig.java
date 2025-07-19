package com.hbm.items.weapon.sedna;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import com.hbm.items.weapon.sedna.ItemGunBaseNT.LambdaContext;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.SmokeNode;
import com.hbm.items.weapon.sedna.factory.GunStateDecider;
import com.hbm.items.weapon.sedna.factory.Lego;
import com.hbm.items.weapon.sedna.hud.IHUDComponent;
import com.hbm.items.weapon.sedna.mods.WeaponModManager;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.HbmAnimations.AnimType;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/**
 * Despite how complicated the GunConfig looks, it actually only exists to hold together a bunch of fields. Everything else is infrastructure for getting and setting.
 * The gun config determines general gun specific stats like durability, crosshair, animations, receivers, click handling and the decider.
 * 
 * @author hbm
 * */
public class GunConfig {

	public List<SmokeNode> smokeNodes = new ArrayList();
	
	public static final String O_RECEIVERS =					"O_RECEIVERS";
	public static final String F_DURABILITY =					"F_DURABILITY";
	public static final String I_DRAWDURATION =					"I_DRAWDURATION";
	public static final String I_INSPECTDURATION =				"I_INSPECTDURATION";
	public static final String I_INSPECTCANCEL =				"I_INSPECTCANCEL";
	public static final String O_CROSSHAIR =					"O_CROSSHAIR";
	public static final String B_HIDECROSSHAIR =				"B_HIDECROSSHAIR";
	public static final String B_RELOADANIMATIONSEQUENTIAL =	"B_RELOADANIMATIONSEQUENTIAL";
	public static final String O_SCOPETEXTURE =					"O_SCOPETEXTURE";
	public static final String CON_SMOKE =						"CON_SMOKE";
	public static final String CON_ORCHESTRA =					"CON_ORCHESTRA";
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
	public static final String O_HUDCOMPONENTS =				"O_HUDCOMPONENTS";
	
	/* FIELDS */
	
	public int index;
	/** List of receivers used by the gun, primary and secondary are usually indices 0 and 1 respectively, if applicable */
	protected Receiver[] receivers_DNA;
	protected float durability_DNA;
	protected int drawDuration_DNA = 0;
	protected int inspectDuration_DNA = 0;
	protected boolean inspectCancel_DNA = true;
	protected Crosshair crosshair_DNA;
	protected boolean hideCrosshair_DNA = true;
	protected boolean reloadAnimationsSequential_DNA;
	protected ResourceLocation scopeTexture_DNA;
	/** Handles smoke clientside */
	protected BiConsumer<ItemStack, LambdaContext> smokeHandler_DNA;
	/** This piece only triggers during reloads, playing sounds depending on the reload's progress making reload sounds easier and synced to animations */
	protected BiConsumer<ItemStack, LambdaContext> orchestra_DNA;
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
	protected IHUDComponent[] hudComponents_DNA;
	
	/* GETTERS */

	public Receiver[] getReceivers(ItemStack stack) {								return WeaponModManager.eval(receivers_DNA, stack, O_RECEIVERS, this, this.index); }
	public float getDurability(ItemStack stack) {									return WeaponModManager.eval(durability_DNA, stack, F_DURABILITY, this, this.index); }
	public int getDrawDuration(ItemStack stack) {									return WeaponModManager.eval(drawDuration_DNA, stack, I_DRAWDURATION, this, this.index); }
	public int getInspectDuration(ItemStack stack) {								return WeaponModManager.eval(inspectDuration_DNA, stack, I_INSPECTDURATION, this, this.index); }
	public boolean getInspectCancel(ItemStack stack) {								return WeaponModManager.eval(inspectCancel_DNA, stack, I_INSPECTCANCEL, this, this.index); }
	public Crosshair getCrosshair(ItemStack stack) {								return WeaponModManager.eval(crosshair_DNA, stack, O_CROSSHAIR, this, this.index); }
	public boolean getHideCrosshair(ItemStack stack) {								return WeaponModManager.eval(hideCrosshair_DNA, stack, B_HIDECROSSHAIR, this, this.index); }
	public boolean getReloadAnimSequential(ItemStack stack) {						return WeaponModManager.eval(reloadAnimationsSequential_DNA, stack, B_RELOADANIMATIONSEQUENTIAL, this, this.index); }
	public ResourceLocation getScopeTexture(ItemStack stack) {						return WeaponModManager.eval(scopeTexture_DNA, stack, O_SCOPETEXTURE, this, this.index); }
	public BiConsumer<ItemStack, LambdaContext> getSmokeHandler(ItemStack stack) {	return WeaponModManager.eval(smokeHandler_DNA, stack, CON_SMOKE, this, this.index); }
	public BiConsumer<ItemStack, LambdaContext> getOrchestra(ItemStack stack) {		return WeaponModManager.eval(this.orchestra_DNA, stack, CON_ORCHESTRA, this, this.index); }

	public BiConsumer<ItemStack, LambdaContext> getPressPrimary(ItemStack stack) {		return WeaponModManager.eval(this.onPressPrimary_DNA, stack, CON_ONPRESSPRIMARY, this, this.index); }
	public BiConsumer<ItemStack, LambdaContext> getPressSecondary(ItemStack stack) {	return WeaponModManager.eval(this.onPressSecondary_DNA, stack, CON_ONPRESSSECONDARY, this, this.index); }
	public BiConsumer<ItemStack, LambdaContext> getPressTertiary(ItemStack stack) {		return WeaponModManager.eval(this.onPressTertiary_DNA, stack, CON_ONPRESSTERTIARY, this, this.index); }
	public BiConsumer<ItemStack, LambdaContext> getPressReload(ItemStack stack) {		return WeaponModManager.eval(this.onPressReload_DNA, stack, CON_ONPRESSRELOAD, this, this.index); }

	public BiConsumer<ItemStack, LambdaContext> getReleasePrimary(ItemStack stack) {	return WeaponModManager.eval(this.onReleasePrimary_DNA, stack, CON_ONRELEASEPRIMARY, this, this.index); }
	public BiConsumer<ItemStack, LambdaContext> getReleaseSecondary(ItemStack stack) {	return WeaponModManager.eval(this.onReleaseSecondary_DNA, stack, CON_ONRELEASESECONDARY, this, this.index); }
	public BiConsumer<ItemStack, LambdaContext> getReleaseTertiary(ItemStack stack) {	return WeaponModManager.eval(this.onReleaseTertiary_DNA, stack, CON_ONRELEASETERTIARY, this, this.index); }
	public BiConsumer<ItemStack, LambdaContext> getReleaseReload(ItemStack stack) {		return WeaponModManager.eval(this.onReleaseReload_DNA, stack, CON_ONRELEASERELOAD, this, this.index); }
	
	public BiConsumer<ItemStack, LambdaContext> getDecider(ItemStack stack) {			return WeaponModManager.eval(this.decider_DNA, stack, CON_DECIDER, this, this.index); }
	
	public BiFunction<ItemStack, AnimType, BusAnimation> getAnims(ItemStack stack) {	return WeaponModManager.eval(this.animations_DNA, stack, FUN_ANIMNATIONS, this, this.index); }
	public IHUDComponent[] getHUDComponents(ItemStack stack) {							return WeaponModManager.eval(this.hudComponents_DNA, stack, O_HUDCOMPONENTS, this, this.index); }
	
	/* SETTERS */
	
	public GunConfig rec(Receiver... receivers) {			this.receivers_DNA = receivers; for(Receiver r : receivers_DNA) r.parent = this; return this; }
	public GunConfig dura(float dura) {						this.durability_DNA = dura; return this; }
	public GunConfig draw(int draw) {						this.drawDuration_DNA = draw; return this; }
	public GunConfig inspect(int inspect) {					this.inspectDuration_DNA = inspect; return this; }
	public GunConfig inspectCancel(boolean flag) {			this.inspectCancel_DNA = flag; return this; }
	public GunConfig crosshair(Crosshair crosshair) {		this.crosshair_DNA = crosshair; return this; }
	public GunConfig hideCrosshair(boolean flag) {			this.hideCrosshair_DNA = flag; return this; }
	public GunConfig reloadSequential(boolean flag) {		this.reloadAnimationsSequential_DNA = flag; return this; }
	public GunConfig scopeTexture(ResourceLocation tex) {	this.scopeTexture_DNA = tex; return this; }

	public GunConfig smoke(BiConsumer<ItemStack, LambdaContext> smoke) {			this.smokeHandler_DNA = smoke; return this; }
	public GunConfig orchestra(BiConsumer<ItemStack, LambdaContext> orchestra) {	this.orchestra_DNA = orchestra; return this; }
	
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
	
	//client
	public GunConfig anim(BiFunction<ItemStack, AnimType, BusAnimation> lambda) {	this.animations_DNA = lambda;			return this; }
	public GunConfig hud(IHUDComponent... components) {								this.hudComponents_DNA = components;	return this; }
	
	/** Standard package for keybind handling and decider using LEGO prefabs: Primary fire on LMB,
	 * reload on R, aiming on MMB and the standard decider which includes jamming and auto fire handling*/
	public GunConfig setupStandardConfiguration() {
		this.pp(Lego.LAMBDA_STANDARD_CLICK_PRIMARY);
		this.pr(Lego.LAMBDA_STANDARD_RELOAD);
		this.pt(Lego.LAMBDA_TOGGLE_AIM);
		this.decider(GunStateDecider.LAMBDA_STANDARD_DECIDER);
		return this;
	}
}
