package com.hbm.items.weapon.sedna;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Function;

import com.hbm.config.GeneralConfig;
import com.hbm.handler.HbmKeybinds.EnumKeybind;
import com.hbm.interfaces.IItemHUD;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.gui.GUIWeaponTable;
import com.hbm.items.IEquipReceiver;
import com.hbm.items.IKeybindReceiver;
import com.hbm.items.armor.ArmorTrenchmaster;
import com.hbm.items.weapon.sedna.hud.IHUDComponent;
import com.hbm.items.weapon.sedna.mags.IMagazine;
import com.hbm.items.weapon.sedna.mods.WeaponModManager;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.GunAnimationPacket;
import com.hbm.render.anim.HbmAnimations.AnimType;
import com.hbm.render.util.RenderScreenOverlay;
import com.hbm.sound.AudioWrapper;
import com.hbm.util.BobMathUtil;
import com.hbm.util.EnumUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class ItemGunBaseNT extends Item implements IKeybindReceiver, IItemHUD, IEquipReceiver {

	/** Timestamp for rendering smoke nodes and muzzle flashes */
	public long[] lastShot;
	/** [0;1] randomized every shot for various rendering applications */
	public double shotRand = 0D;
	
	public static List<Item> secrets = new ArrayList();
	public List<ComparableStack> recognizedMods = new ArrayList();
	
	public static final DecimalFormatSymbols SYMBOLS_US = new DecimalFormatSymbols(Locale.US);
	public static final DecimalFormat FORMAT_DMG = new DecimalFormat("#.##", SYMBOLS_US);

	public static float recoilVertical = 0;
	public static float recoilHorizontal = 0;
	public static float recoilDecay = 0.75F;
	public static float recoilRebound = 0.25F;
	public static float offsetVertical = 0;
	public static float offsetHorizontal = 0;
	
	public static void setupRecoil(float vertical, float horizontal, float decay, float rebound) {
		recoilVertical += vertical;
		recoilHorizontal += horizontal;
		recoilDecay = decay;
		recoilRebound = rebound;
	}
	
	public static void setupRecoil(float vertical, float horizontal) {
		setupRecoil(vertical, horizontal, 0.75F, 0.25F);
	}

	public static final String O_GUNCONFIG = "O_GUNCONFIG_";
	
	public static final String KEY_DRAWN = "drawn";
	public static final String KEY_AIMING = "aiming";
	public static final String KEY_MODE = "mode_";
	public static final String KEY_WEAR = "wear_";
	public static final String KEY_TIMER = "timer_";
	public static final String KEY_STATE = "state_";
	public static final String KEY_PRIMARY = "mouse1_";
	public static final String KEY_SECONDARY = "mouse2_";
	public static final String KEY_TERTIARY = "mouse3_";
	public static final String KEY_RELOAD = "reload_";
	public static final String KEY_LASTANIM = "lastanim_";
	public static final String KEY_ANIMTIMER = "animtimer_";
	public static final String KEY_LOCKONTARGET = "lockontarget";
	public static final String KEY_LOCKEDON = "lockedon";
	public static final String KEY_CANCELRELOAD = "cancel";
	public static final String KEY_EQUIPPED = "eqipped";
	
	public static ConcurrentHashMap<EntityLivingBase, AudioWrapper> loopedSounds = new ConcurrentHashMap();

	public static float prevAimingProgress;
	public static float aimingProgress;
	
	/** NEVER ACCESS DIRECTLY - USE GETTER */
	protected GunConfig[] configs_DNA;
	
	public Function<ItemStack, String> LAMBDA_NAME_MUTATOR;
	public WeaponQuality quality;
	
	public GunConfig getConfig(ItemStack stack, int index) {
		GunConfig cfg = configs_DNA[index];
		if(stack == null) return cfg;
		return WeaponModManager.eval(cfg, stack, O_GUNCONFIG + index, this, index);
	}
	
	public int getConfigCount() {
		return configs_DNA.length;
	}
	
	public ItemGunBaseNT(WeaponQuality quality, GunConfig... cfg) {
		this.setMaxStackSize(1);
		this.configs_DNA = cfg;
		this.quality = quality;
		this.lastShot = new long[cfg.length];
		for(int i = 0; i < cfg.length; i++) cfg[i].index = i;
		if(quality == WeaponQuality.A_SIDE || quality == WeaponQuality.SPECIAL || quality == WeaponQuality.UTILITY) this.setCreativeTab(MainRegistry.weaponTab);
		if(quality == WeaponQuality.LEGENDARY || quality == WeaponQuality.SECRET) this.secrets.add(this);
		this.setTextureName(RefStrings.MODID + ":gun_darter");
	}
	
	public static enum WeaponQuality {
		A_SIDE,
		B_SIDE,
		LEGENDARY,
		SPECIAL,
		UTILITY,
		SECRET,
		DEBUG
	}

	public static enum GunState {
		DRAWING,	//forced delay where nothing can be done
		IDLE,		//the gun is ready to fire or reload
		COOLDOWN,	//forced delay, but with option for refire
		RELOADING,	//forced delay after which a reload action happens, may be canceled (TBI)
		JAMMED,		//forced delay due to jamming
	}
	
	public ItemGunBaseNT setNameMutator(Function<ItemStack, String> lambda) {
		this.LAMBDA_NAME_MUTATOR = lambda;
		return this;
	}

	public String getItemStackDisplayName(ItemStack stack) {
		
		if(this.LAMBDA_NAME_MUTATOR != null) {
			String unloc = this.LAMBDA_NAME_MUTATOR.apply(stack);
			if(unloc != null) return (StatCollector.translateToLocal(unloc + ".name")).trim();
		}
		
		return super.getItemStackDisplayName(stack);
	}
	
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		
		int configs = this.configs_DNA.length;
		for(int i = 0; i < configs; i++) {
			GunConfig config = getConfig(stack, i);
			for(Receiver rec : config.getReceivers(stack)) {
				IMagazine mag = rec.getMagazine(stack);
				list.add("Ammo: " + mag.getIconForHUD(stack, player).getDisplayName() + " " + mag.reportAmmoStateForHUD(stack, player));
				float dmg = rec.getBaseDamage(stack);
				list.add("Base Damage: " + FORMAT_DMG.format(dmg));
				if(mag.getType(stack, player.inventory) instanceof BulletConfig) {
					BulletConfig bullet = (BulletConfig) mag.getType(stack, player.inventory);
					int min = (int) (bullet.projectilesMin * rec.getSplitProjectiles(stack));
					int max = (int) (bullet.projectilesMax * rec.getSplitProjectiles(stack));
					list.add("Damage with current ammo: " + FORMAT_DMG.format(dmg * bullet.damageMult) + (min > 1 ? (" x" + (min != max ? (min + "-" + max) : min)) : ""));
				}
			}
			
			float maxDura = config.getDurability(stack);
			if(maxDura > 0) {
				int dura = MathHelper.clamp_int((int)((maxDura - this.getWear(stack, i)) * 100 / maxDura), 0, 100);
				list.add("Condition: " + dura + "%");
			}
			
			for(ItemStack upgrade : WeaponModManager.getUpgradeItems(stack, i)) {
				list.add(EnumChatFormatting.YELLOW + upgrade.getDisplayName());
			}
		}
		
		switch(this.quality) {
		case A_SIDE: list.add(EnumChatFormatting.YELLOW + "Standard Arsenal"); break;
		case B_SIDE: list.add(EnumChatFormatting.GOLD + "B-Side"); break;
		case LEGENDARY: list.add(EnumChatFormatting.RED + "Legendary Weapon"); break;
		case SPECIAL: list.add(EnumChatFormatting.AQUA + "Special Weapon"); break;
		case UTILITY: list.add(EnumChatFormatting.GREEN + "Utility"); break;
		case SECRET: list.add((BobMathUtil.getBlink() ? EnumChatFormatting.DARK_RED : EnumChatFormatting.RED) + "SECRET"); break;
		case DEBUG: list.add((BobMathUtil.getBlink() ? EnumChatFormatting.YELLOW : EnumChatFormatting.GOLD) + "DEBUG"); break;
		}
		
		if(Minecraft.getMinecraft().currentScreen instanceof GUIWeaponTable && !this.recognizedMods.isEmpty()) {
			list.add(EnumChatFormatting.RED + "Accepts:");
			for(ComparableStack comp : this.recognizedMods) list.add(EnumChatFormatting.RED + "  " + comp.toStack().getDisplayName());
		}
	}
	
	@Override
	public boolean canHandleKeybind(EntityPlayer player, ItemStack stack, EnumKeybind keybind) {
		return keybind == EnumKeybind.GUN_PRIMARY || keybind == EnumKeybind.GUN_SECONDARY || keybind == EnumKeybind.GUN_TERTIARY || keybind == EnumKeybind.RELOAD;
	}
	
	@Override
	public void handleKeybind(EntityPlayer player, ItemStack stack, EnumKeybind keybind, boolean newState) {
		handleKeybind(player, player.inventory, stack, keybind, newState);
	}

	public void handleKeybind(EntityLivingBase entity, IInventory inventory, ItemStack stack, EnumKeybind keybind, boolean newState) {
		if(!GeneralConfig.enableGuns) return;
		
		int configs = this.configs_DNA.length;
		
		for(int i = 0; i < configs; i++) {
			GunConfig config = getConfig(stack, i);
			LambdaContext ctx = new LambdaContext(config, entity, inventory, i);
	
			if(keybind == EnumKeybind.GUN_PRIMARY &&	newState && !getPrimary(stack, i)) {	if(config.getPressPrimary(stack) != null)		config.getPressPrimary(stack).accept(stack, ctx);		this.setPrimary(stack, i, newState);	continue; }
			if(keybind == EnumKeybind.GUN_PRIMARY &&	!newState && getPrimary(stack, i)) {	if(config.getReleasePrimary(stack) != null)		config.getReleasePrimary(stack).accept(stack, ctx);		this.setPrimary(stack, i, newState);	continue; }
			if(keybind == EnumKeybind.GUN_SECONDARY &&	newState && !getSecondary(stack, i)) {	if(config.getPressSecondary(stack) != null)		config.getPressSecondary(stack).accept(stack, ctx);		this.setSecondary(stack, i, newState);	continue; }
			if(keybind == EnumKeybind.GUN_SECONDARY &&	!newState && getSecondary(stack, i)) {	if(config.getReleaseSecondary(stack) != null)	config.getReleaseSecondary(stack).accept(stack, ctx);	this.setSecondary(stack, i, newState);	continue; }
			if(keybind == EnumKeybind.GUN_TERTIARY &&	newState && !getTertiary(stack, i)) {	if(config.getPressTertiary(stack) != null)		config.getPressTertiary(stack).accept(stack, ctx);		this.setTertiary(stack, i, newState);	continue; }
			if(keybind == EnumKeybind.GUN_TERTIARY &&	!newState && getTertiary(stack, i)) {	if(config.getReleaseTertiary(stack) != null)	config.getReleaseTertiary(stack).accept(stack, ctx);	this.setTertiary(stack, i, newState);	continue; }
			if(keybind == EnumKeybind.RELOAD &&			newState && !getReloadKey(stack, i)) {	if(config.getPressReload(stack) != null)		config.getPressReload(stack).accept(stack, ctx);		this.setReloadKey(stack, i, newState);	continue; }
			if(keybind == EnumKeybind.RELOAD &&			!newState && getReloadKey(stack, i)) {	if(config.getReleaseReload(stack) != null)		config.getReleaseReload(stack).accept(stack, ctx);		this.setReloadKey(stack, i, newState);	continue; }
		}
	}

	@Override
	public void onEquip(EntityPlayer player, ItemStack stack) {
		for(int i = 0; i < this.configs_DNA.length; i++) {
			if(this.getLastAnim(stack, i) == AnimType.EQUIP && this.getAnimTimer(stack, i) < 5) continue; 
			playAnimation(player, stack, AnimType.EQUIP, i);
			this.setPrimary(stack, i, false);
			this.setSecondary(stack, i, false);
			this.setTertiary(stack, i, false);
			this.setReloadKey(stack, i, false);
		}
	}
	
	public static void playAnimation(EntityPlayer player, ItemStack stack, AnimType type, int index) {
		if(player instanceof EntityPlayerMP) {
			PacketDispatcher.wrapper.sendTo(new GunAnimationPacket(type.ordinal(), 0, index), (EntityPlayerMP) player);
			setLastAnim(stack, index, type);
			setAnimTimer(stack, index, 0);
		}
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isHeld) {
		
		if(!(entity instanceof EntityLivingBase)) return;
		EntityPlayer player = entity instanceof EntityPlayer ? (EntityPlayer) entity : null;
		int confNo = this.configs_DNA.length;
		GunConfig[] configs = new GunConfig[confNo];
		LambdaContext[] ctx = new LambdaContext[confNo];
		for(int i = 0; i < confNo; i++) {
			configs[i] = this.getConfig(stack, i);
			ctx[i] = new LambdaContext(configs[i], (EntityLivingBase) entity, player != null ? player.inventory : null, i);
		}
		
		if(world.isRemote) {
			
			if(isHeld && player == MainRegistry.proxy.me()) {
				
				/// DEBUG ///
				/*Vec3 offset = Vec3.createVectorHelper(-0.2, -0.1, 0.75);
				offset.rotateAroundX(-entity.rotationPitch / 180F * (float) Math.PI);
				offset.rotateAroundY(-entity.rotationYaw / 180F * (float) Math.PI);
				world.spawnParticle("flame", entity.posX + offset.xCoord, entity.posY + entity.getEyeHeight() + offset.yCoord, entity.posZ + offset.zCoord, 0, 0, 0);*/
				
				/// AIMING ///
				prevAimingProgress = aimingProgress;
				boolean aiming = this.getIsAiming(stack);
				float aimSpeed = 0.25F;
				if(aiming && aimingProgress < 1F) aimingProgress += aimSpeed;
				if(!aiming && aimingProgress > 0F) aimingProgress -= aimSpeed;
				aimingProgress = MathHelper.clamp_float(aimingProgress, 0F, 1F);
				
				/// SMOKE NODES ///
				for(int i = 0; i < confNo; i++) if(configs[i].getSmokeHandler(stack) != null) {
					configs[i].getSmokeHandler(stack).accept(stack, ctx[i]);
				}
				
				for(int i = 0; i < confNo; i++) {
					BiConsumer<ItemStack, LambdaContext> orchestra = configs[i].getOrchestra(stack);
					if(orchestra != null) orchestra.accept(stack, ctx[i]);
				}
			}
			return;
		}
		
		/// ON EQUIP ///
		if(player != null) {
			boolean wasHeld = this.getIsEquipped(stack);
			
			if(!wasHeld && isHeld && player != null) {
				this.onEquip(player, stack);
			}
		}
		
		this.setIsEquipped(stack, isHeld);
		
		/// RESET WHEN NOT EQUIPPED ///
		if(!isHeld) {
			for(int i = 0; i < confNo; i++) {
				GunState current = this.getState(stack, i);
				if(current != GunState.JAMMED) {
					this.setState(stack, i, GunState.DRAWING);
					this.setTimer(stack, i, configs[i].getDrawDuration(stack));
				}
				this.setLastAnim(stack, i, AnimType.CYCLE); //prevents new guns from initializing with DRAWING, 0
			}
			this.setIsAiming(stack, false);
			this.setReloadCancel(stack, false);
			return;
		}
		
		for(int i = 0; i < confNo; i++) for(int k = 0; k == 0 || (k < 2 && ArmorTrenchmaster.isTrenchMaster(player) && this.getState(stack, i) == GunState.RELOADING); k++) {
			BiConsumer<ItemStack, LambdaContext> orchestra = configs[i].getOrchestra(stack);
			if(orchestra != null) orchestra.accept(stack, ctx[i]);
			
			setAnimTimer(stack, i, getAnimTimer(stack, i) + 1);
			
			/// STTATE MACHINE ///
			int timer = this.getTimer(stack, i);
			if(timer > 0) this.setTimer(stack, i, timer - 1);
			if(timer <= 1) configs[i].getDecider(stack).accept(stack, ctx[i]);
		}
	}

	// GUN DRAWN //
	public static boolean getIsDrawn(ItemStack stack) { return getValueBool(stack, KEY_DRAWN); }
	public static void setIsDrawn(ItemStack stack, boolean value) { setValueBool(stack, KEY_DRAWN, value); }
	// GUN STATE TIMER //
	public static int getTimer(ItemStack stack, int index) { return getValueInt(stack, KEY_TIMER + index); }
	public static void setTimer(ItemStack stack, int index, int value) { setValueInt(stack, KEY_TIMER + index, value); }
	// GUN STATE //
	public static GunState getState(ItemStack stack, int index) { return EnumUtil.grabEnumSafely(GunState.class, getValueByte(stack, KEY_STATE + index)); }
	public static void setState(ItemStack stack, int index, GunState value) { setValueByte(stack, KEY_STATE + index, (byte) value.ordinal()); }
	// GUN MODE //
	public static int getMode(ItemStack stack, int index) { return getValueInt(stack, KEY_MODE + index); }
	public static void setMode(ItemStack stack, int index, int value) { setValueInt(stack, KEY_MODE + index, value); }
	// GUN AIMING //
	public static boolean getIsAiming(ItemStack stack) { return getValueBool(stack, KEY_AIMING); }
	public static void setIsAiming(ItemStack stack, boolean value) { setValueBool(stack, KEY_AIMING, value); }
	// GUN AIMING //
	public static float getWear(ItemStack stack, int index) { return getValueFloat(stack, KEY_WEAR + index); }
	public static void setWear(ItemStack stack, int index, float value) { setValueFloat(stack, KEY_WEAR + index, value); }
	// LOCKON //
	public static int getLockonTarget(ItemStack stack) { return getValueInt(stack, KEY_LOCKONTARGET); }
	public static void setLockonTarget(ItemStack stack, int value) { setValueInt(stack, KEY_LOCKONTARGET, value); }
	public static boolean getIsLockedOn(ItemStack stack) { return getValueBool(stack, KEY_LOCKEDON); }
	public static void setIsLockedOn(ItemStack stack, boolean value) { setValueBool(stack, KEY_LOCKEDON, value); }
	// ANIM TRACKING //
	public static AnimType getLastAnim(ItemStack stack, int index) { return EnumUtil.grabEnumSafely(AnimType.class, getValueInt(stack, KEY_LASTANIM + index)); }
	public static void setLastAnim(ItemStack stack, int index, AnimType value) { setValueInt(stack, KEY_LASTANIM + index, value.ordinal()); }
	public static int getAnimTimer(ItemStack stack, int index) { return getValueInt(stack, KEY_ANIMTIMER + index); }
	public static void setAnimTimer(ItemStack stack, int index, int value) { setValueInt(stack, KEY_ANIMTIMER + index, value); }
	
	// BUTTON STATES //
	public static boolean getPrimary(ItemStack stack, int index) { return getValueBool(stack, KEY_PRIMARY + index); }
	public static void setPrimary(ItemStack stack, int index, boolean value) { setValueBool(stack, KEY_PRIMARY + index, value); }
	public static boolean getSecondary(ItemStack stack, int index) { return getValueBool(stack, KEY_SECONDARY + index); }
	public static void setSecondary(ItemStack stack, int index, boolean value) { setValueBool(stack, KEY_SECONDARY + index, value); }
	public static boolean getTertiary(ItemStack stack, int index) { return getValueBool(stack, KEY_TERTIARY + index); }
	public static void setTertiary(ItemStack stack, int index, boolean value) { setValueBool(stack, KEY_TERTIARY + index, value); }
	public static boolean getReloadKey(ItemStack stack, int index) { return getValueBool(stack, KEY_RELOAD + index); }
	public static void setReloadKey(ItemStack stack, int index, boolean value) { setValueBool(stack, KEY_RELOAD + index, value); }
	// RELOAD CANCEL //
	public static boolean getReloadCancel(ItemStack stack) { return getValueBool(stack, KEY_CANCELRELOAD); }
	public static void setReloadCancel(ItemStack stack, boolean value) { setValueBool(stack, KEY_CANCELRELOAD, value); }
	// EQUIPPED //
	public static boolean getIsEquipped(ItemStack stack) { return getValueBool(stack, KEY_EQUIPPED); }
	public static void setIsEquipped(ItemStack stack, boolean value) { setValueBool(stack, KEY_EQUIPPED, value); }
	
	
	/// UTIL ///
	public static int getValueInt(ItemStack stack, String name) { if(stack.hasTagCompound()) return stack.getTagCompound().getInteger(name); return 0; }
	public static void setValueInt(ItemStack stack, String name, int value) { if(!stack.hasTagCompound()) stack.stackTagCompound = new NBTTagCompound(); stack.getTagCompound().setInteger(name, value); }
	
	public static float getValueFloat(ItemStack stack, String name) { if(stack.hasTagCompound()) return stack.getTagCompound().getFloat(name); return 0; }
	public static void setValueFloat(ItemStack stack, String name, float value) { if(!stack.hasTagCompound()) stack.stackTagCompound = new NBTTagCompound(); stack.getTagCompound().setFloat(name, value); }
	
	public static byte getValueByte(ItemStack stack, String name) { if(stack.hasTagCompound()) return stack.getTagCompound().getByte(name); return 0; }
	public static void setValueByte(ItemStack stack, String name, byte value) { if(!stack.hasTagCompound()) stack.stackTagCompound = new NBTTagCompound(); stack.getTagCompound().setByte(name, value); }
	
	public static boolean getValueBool(ItemStack stack, String name) { if(stack.hasTagCompound()) return stack.getTagCompound().getBoolean(name); return false; }
	public static void setValueBool(ItemStack stack, String name, boolean value) { if(!stack.hasTagCompound()) stack.stackTagCompound = new NBTTagCompound(); stack.getTagCompound().setBoolean(name, value); }
	
	/** Wrapper for extra context used in most Consumer lambdas which are part of the guncfg */
	public static class LambdaContext {
		public final GunConfig config;
		public final EntityLivingBase entity;
		public final IInventory inventory;
		public final int configIndex;
		
		public LambdaContext(GunConfig config, EntityLivingBase player, IInventory inventory, int configIndex) {
			this.config = config;
			this.entity = player;
			this.inventory = inventory;
			this.configIndex = configIndex;
		}

		public EntityPlayer getPlayer() {
			if(!(entity instanceof EntityPlayer)) return null;
			return (EntityPlayer) entity;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderHUD(Pre event, ElementType type, EntityPlayer player, ItemStack stack) {
		
		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		
		if(type == ElementType.CROSSHAIRS) {
			event.setCanceled(true);
			GunConfig config = gun.getConfig(stack, 0);
			if(config.getHideCrosshair(stack) && aimingProgress >= 1F) return;
			RenderScreenOverlay.renderCustomCrosshairs(event.resolution, Minecraft.getMinecraft().ingameGUI, config.getCrosshair(stack));
		}
		
		int confNo = this.configs_DNA.length;
		
		for(int i = 0; i < confNo; i++) {
			IHUDComponent[] components = gun.getConfig(stack, i).getHUDComponents(stack);
			
			if(components != null) for(IHUDComponent component : components) {
				int bottomOffset = 0;
				component.renderHUDComponent(event, type, player, stack, bottomOffset, i);
				bottomOffset += component.getComponentHeight(player, stack);
			}
		}
		
		Minecraft.getMinecraft().renderEngine.bindTexture(Gui.icons);
	}
	
	/*@Override
	public boolean getShareTag() { return false; }*/ // nbt sync dupe fix, didn't work
	
	public static class SmokeNode {
		
		public double forward = 0D;
		public double side = 0D;
		public double lift = 0D;
		public double alpha;
		public double width = 1D;
		
		public SmokeNode(double alpha) { this.alpha = alpha; }
	}
}
