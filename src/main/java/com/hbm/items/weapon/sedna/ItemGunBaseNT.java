package com.hbm.items.weapon.sedna;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

import com.hbm.handler.CasingEjector;
import com.hbm.handler.HbmKeybinds.EnumKeybind;
import com.hbm.interfaces.IItemHUD;
import com.hbm.items.IEquipReceiver;
import com.hbm.items.IKeybindReceiver;
import com.hbm.items.weapon.sedna.hud.IHUDComponent;
import com.hbm.main.MainRegistry;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.packet.toclient.GunAnimationPacket;
import com.hbm.render.anim.HbmAnimations.AnimType;
import com.hbm.render.util.RenderScreenOverlay;
import com.hbm.sound.AudioWrapper;
import com.hbm.util.EnumUtil;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class ItemGunBaseNT extends Item implements IKeybindReceiver, IEquipReceiver, IItemHUD {

	/** Timestamp for rendering smoke nodes and muzzle flashes */
	public long[] lastShot;
	/** [0;1] randomized every shot for various rendering applications */
	public double shotRand = 0D;

	public static float recoilVertical = 0;
	public static float recoilHorizontal = 0;
	public static float offsetVertical = 0;
	public static float offsetHorizontal = 0;

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
	
	public static ConcurrentHashMap<EntityPlayer, AudioWrapper> loopedSounds = new ConcurrentHashMap();

	public static float prevAimingProgress;
	public static float aimingProgress;
	
	/** NEVER ACCESS DIRECTLY - USE GETTER */
	protected GunConfig[] configs_DNA;
	
	public GunConfig getConfig(ItemStack stack, int index) {
		GunConfig cfg = configs_DNA[index];
		return WeaponUpgradeManager.eval(cfg, stack, O_GUNCONFIG + index, this);
	}
	
	public ItemGunBaseNT(GunConfig... cfg) {
		this.setMaxStackSize(1);
		this.configs_DNA = cfg;
		this.lastShot = new long[cfg.length];
		this.setCreativeTab(MainRegistry.weaponTab);
	}

	public static enum GunState {
		DRAWING,	//initial delay after selecting
		IDLE,		//gun can be fired or reloaded
		WINDUP,		//fire button is down, added delay before fire
		COOLDOWN,	//gun has been fired, cooldown
		RELOADING,	//gun is currently reloading
		JAMMED,		//gun is jammed, either after reloading or while firing
	}
	
	@Override
	public boolean canHandleKeybind(EntityPlayer player, ItemStack stack, EnumKeybind keybind) {
		return keybind == EnumKeybind.GUN_PRIMARY || keybind == EnumKeybind.GUN_SECONDARY || keybind == EnumKeybind.GUN_TERTIARY || keybind == EnumKeybind.RELOAD;
	}
	
	@Override
	public void handleKeybind(EntityPlayer player, ItemStack stack, EnumKeybind keybind, boolean newState) {
		
		int configs = this.configs_DNA.length;
		
		for(int i = 0; i < configs; i++) {
			GunConfig config = getConfig(stack, i);
			LambdaContext ctx = new LambdaContext(config, player, i);
	
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
		for(int i = 0; i < this.configs_DNA.length; i++) playAnimation(player, stack, AnimType.EQUIP, i);
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
		
		if(!(entity instanceof EntityPlayer)) return;
		EntityPlayer player = entity instanceof EntityPlayer ? (EntityPlayer) entity : null;
		int confNo = this.configs_DNA.length;
		GunConfig[] configs = new GunConfig[confNo];
		LambdaContext[] ctx = new LambdaContext[confNo];
		for(int i = 0; i < confNo; i++) {
			configs[i] = this.getConfig(stack, i);
			ctx[i] = new LambdaContext(configs[i], player, i);
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
		
		/// RESET WHEN NOT EQUIPPED ///
		if(!isHeld) {
			for(int i = 0; i < confNo; i++) {
				GunState current = this.getState(stack, i);
				if(current != GunState.JAMMED) {
					this.setState(stack, i, GunState.DRAWING);
					this.setTimer(stack, i, configs[i].getDrawDuration(stack));
				}
			}
			this.setIsAiming(stack, false);
			return;
		}
		
		for(int i = 0; i < confNo; i++) {
			BiConsumer<ItemStack, LambdaContext> orchestra = configs[i].getOrchestra(stack);
			if(orchestra != null) orchestra.accept(stack, ctx[i]);
			
			setAnimTimer(stack, i, getAnimTimer(stack, i) + 1);
			
			/// STTATE MACHINE ///
			int timer = this.getTimer(stack, i);
			if(timer > 0) this.setTimer(stack, i, timer - 1);
			if(timer <= 1) configs[i].getDecider(stack).accept(stack, ctx[i]);
		}
	}
	
	public static void trySpawnCasing(Entity entity, CasingEjector ejector, BulletConfig bullet, ItemStack stack) {
		
		if(ejector == null) return; //abort if the gun can't eject bullets at all
		if(bullet == null) return; //abort if there's no valid bullet cfg
		if(bullet.casing == null) return; //abort if the bullet is caseless
		
		NBTTagCompound data = new NBTTagCompound();
		data.setString("type", "casing");
		data.setFloat("pitch", (float) Math.toRadians(entity.rotationPitch));
		data.setFloat("yaw", (float) Math.toRadians(entity.rotationYaw));
		data.setBoolean("crouched", entity.isSneaking());
		data.setString("name", bullet.casing.getName());
		data.setInteger("ej", ejector.getId());
		PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ), new TargetPoint(entity.dimension, entity.posX, entity.posY, entity.posZ, 50));
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
		public final EntityPlayer player;
		public final int configIndex;
		
		public LambdaContext(GunConfig config, EntityPlayer player, int configIndex) {
			this.config = config;
			this.player = player;
			this.configIndex = configIndex;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderHUD(Pre event, ElementType type, EntityPlayer player, ItemStack stack) {
		
		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		
		if(type == ElementType.CROSSHAIRS) {
			event.setCanceled(true);
			if(aimingProgress >= 1F) return;
			RenderScreenOverlay.renderCustomCrosshairs(event.resolution, Minecraft.getMinecraft().ingameGUI, gun.getConfig(stack, 0).getCrosshair(stack));
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
	}
	
	public static class SmokeNode {
		
		public double forward = 0D;
		public double side = 0D;
		public double lift = 0D;
		public double alpha;
		public double width = 1D;
		
		public SmokeNode(double alpha) { this.alpha = alpha; }
	}
}
