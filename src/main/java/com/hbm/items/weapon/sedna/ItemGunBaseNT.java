package com.hbm.items.weapon.sedna;

import com.hbm.handler.HbmKeybinds.EnumKeybind;
import com.hbm.interfaces.IItemHUD;
import com.hbm.items.IEquipReceiver;
import com.hbm.items.IKeybindReceiver;
import com.hbm.main.MainRegistry;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.GunAnimationPacket;
import com.hbm.render.anim.HbmAnimations.AnimType;
import com.hbm.render.util.RenderScreenOverlay;
import com.hbm.util.EnumUtil;

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

	public static final String O_GUNCONFIG = "O_GUNCONFIG";
	
	public static final String KEY_DRAWN = "drawn";
	public static final String KEY_AIMING = "aiming";
	public static final String KEY_TIMER = "timer";
	public static final String KEY_STATE = "state";
	public static final String KEY_PRIMARY = "mouse1";
	public static final String KEY_SECONDARY = "mouse2";
	public static final String KEY_TERTIARY = "mouse3";
	public static final String KEY_RELOAD = "reload";

	public static float prevAimingProgress;
	public static float aimingProgress;
	
	/** NEVER ACCESS DIRECTLY - USE GETTER */
	private GunConfig config_DNA;
	
	public GunConfig getConfig(ItemStack stack) {
		return WeaponUpgradeManager.eval(config_DNA, stack, O_GUNCONFIG, this);
	}
	
	public ItemGunBaseNT(GunConfig cfg) {
		this.setMaxStackSize(1);
		this.config_DNA = cfg;
	}

	public static enum GunState {
		DRAWING,	//initial delay after selecting
		IDLE,		//gun can be fired or reloaded
		WINDUP,		//fire button is down, added delay before fire
		COOLDOWN,	//gun has been fired, cooldown
		RELOADING	//gun is currently reloading
	}
	
	@Override
	public boolean canHandleKeybind(EntityPlayer player, ItemStack stack, EnumKeybind keybind) {
		return keybind == EnumKeybind.GUN_PRIMARY || keybind == EnumKeybind.GUN_SECONDARY || keybind == EnumKeybind.GUN_TERTIARY || keybind == EnumKeybind.RELOAD;
	}
	
	@Override
	public void handleKeybind(EntityPlayer player, ItemStack stack, EnumKeybind keybind, boolean newState) {
		
		GunConfig config = getConfig(stack);
		LambdaContext ctx = new LambdaContext(config, player);

		if(keybind == EnumKeybind.GUN_PRIMARY &&	newState && !getPrimary(stack)) {	if(config.getPressPrimary(stack) != null)		config.getPressPrimary(stack).accept(stack, ctx);		this.setPrimary(stack, newState);	return; }
		if(keybind == EnumKeybind.GUN_PRIMARY &&	!newState && getPrimary(stack)) {	if(config.getReleasePrimary(stack) != null)		config.getReleasePrimary(stack).accept(stack, ctx);		this.setPrimary(stack, newState);	return; }
		if(keybind == EnumKeybind.GUN_SECONDARY &&	newState && !getSecondary(stack)) {	if(config.getPressSecondary(stack) != null)		config.getPressSecondary(stack).accept(stack, ctx);		this.setSecondary(stack, newState);	return; }
		if(keybind == EnumKeybind.GUN_SECONDARY &&	!newState && getSecondary(stack)) {	if(config.getReleaseSecondary(stack) != null)	config.getReleaseSecondary(stack).accept(stack, ctx);	this.setSecondary(stack, newState);	return; }
		if(keybind == EnumKeybind.GUN_TERTIARY &&	newState && !getTertiary(stack)) {	if(config.getPressTertiary(stack) != null)		config.getPressTertiary(stack).accept(stack, ctx);		this.setTertiary(stack, newState);	return; }
		if(keybind == EnumKeybind.GUN_TERTIARY &&	!newState && getTertiary(stack)) {	if(config.getReleaseTertiary(stack) != null)	config.getReleaseTertiary(stack).accept(stack, ctx);	this.setTertiary(stack, newState);	return; }
		if(keybind == EnumKeybind.RELOAD &&			newState && !getReloadKey(stack)) {	if(config.getPressReload(stack) != null)		config.getPressReload(stack).accept(stack, ctx);		this.setReloadKey(stack, newState);	return; }
		if(keybind == EnumKeybind.RELOAD &&			!newState && getReloadKey(stack)) {	if(config.getReleaseReload(stack) != null)		config.getReleaseReload(stack).accept(stack, ctx);		this.setReloadKey(stack, newState);	return; }
	}

	@Override
	public void onEquip(EntityPlayer player, ItemStack stack) {
		if(player instanceof EntityPlayerMP) PacketDispatcher.wrapper.sendTo(new GunAnimationPacket(AnimType.EQUIP.ordinal()), (EntityPlayerMP) player);
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isHeld) {
		
		if(!(entity instanceof EntityPlayer)) return;
		EntityPlayer player = (EntityPlayer) entity;
		
		if(world.isRemote) {
			
			if(isHeld && player == MainRegistry.proxy.me()) {
				
				/// DEBUG ///
				/*Vec3 offset = Vec3.createVectorHelper(-0.2, -0.1, 0.75);
				offset.rotateAroundX(-entity.rotationPitch / 180F * (float) Math.PI);
				offset.rotateAroundY(-entity.rotationYaw / 180F * (float) Math.PI);
				world.spawnParticle("flame", entity.posX + offset.xCoord, entity.posY + entity.getEyeHeight() + offset.yCoord, entity.posZ + offset.zCoord, 0, 0, 0);*/
				
				prevAimingProgress = aimingProgress;
				boolean aiming = this.getIsAiming(stack);
				float aimSpeed = 0.25F;
				if(aiming && aimingProgress < 1F) aimingProgress += aimSpeed;
				if(!aiming && aimingProgress > 0F) aimingProgress -= aimSpeed;
				aimingProgress = MathHelper.clamp_float(aimingProgress, 0F, 1F);
			}
			return;
		}
		
		GunConfig config = this.getConfig(stack);
		
		if(!isHeld) {
			this.setState(stack, GunState.DRAWING);
			this.setTimer(stack, config.getDrawDuration(stack));
			this.setIsAiming(stack, false);
			return;
		}
		
		int timer = this.getTimer(stack);
		if(timer > 0) this.setTimer(stack, timer - 1);
		if(timer <= 1) nextState(player, stack);
	}
	
	public void nextState(EntityPlayer player, ItemStack stack) {
		GunConfig cfg = this.getConfig(stack);
		cfg.getDecider(stack).accept(stack, new LambdaContext(cfg, player));
	}

	// GUN DRAWN //
	public static boolean getIsDrawn(ItemStack stack) { return getValueBool(stack, KEY_DRAWN); }
	public static void setIsDrawn(ItemStack stack, boolean value) { setValueBool(stack, KEY_DRAWN, value); }

	// GUN STATE TIMER //
	public static int getTimer(ItemStack stack) { return getValueInt(stack, KEY_TIMER); }
	public static void setTimer(ItemStack stack, int value) { setValueInt(stack, KEY_TIMER, value); }

	// GUN STATE //
	public static GunState getState(ItemStack stack) { return EnumUtil.grabEnumSafely(GunState.class, getValueByte(stack, KEY_STATE)); }
	public static void setState(ItemStack stack, GunState value) { setValueByte(stack, KEY_STATE, (byte) value.ordinal()); }

	// GUN AIMING //
	public static boolean getIsAiming(ItemStack stack) { return getValueBool(stack, KEY_AIMING); }
	public static void setIsAiming(ItemStack stack, boolean value) { setValueBool(stack, KEY_AIMING, value); }
	
	// BUTTON STATES //
	public static boolean getPrimary(ItemStack stack) { return getValueBool(stack, KEY_PRIMARY); }
	public static void setPrimary(ItemStack stack, boolean value) { setValueBool(stack, KEY_PRIMARY, value); }
	public static boolean getSecondary(ItemStack stack) { return getValueBool(stack, KEY_SECONDARY); }
	public static void setSecondary(ItemStack stack, boolean value) { setValueBool(stack, KEY_SECONDARY, value); }
	public static boolean getTertiary(ItemStack stack) { return getValueBool(stack, KEY_TERTIARY); }
	public static void setTertiary(ItemStack stack, boolean value) { setValueBool(stack, KEY_TERTIARY, value); }
	public static boolean getReloadKey(ItemStack stack) { return getValueBool(stack, KEY_RELOAD); }
	public static void setReloadKey(ItemStack stack, boolean value) { setValueBool(stack, KEY_RELOAD, value); }
	
	
	/// UTIL ///
	public static int getValueInt(ItemStack stack, String name) { if(stack.hasTagCompound()) return stack.getTagCompound().getInteger(name); return 0; }
	public static void setValueInt(ItemStack stack, String name, int value) { if(!stack.hasTagCompound()) stack.stackTagCompound = new NBTTagCompound(); stack.getTagCompound().setInteger(name, value); }
	
	public static byte getValueByte(ItemStack stack, String name) { if(stack.hasTagCompound()) return stack.getTagCompound().getByte(name); return 0; }
	public static void setValueByte(ItemStack stack, String name, byte value) { if(!stack.hasTagCompound()) stack.stackTagCompound = new NBTTagCompound(); stack.getTagCompound().setByte(name, value); }
	
	public static boolean getValueBool(ItemStack stack, String name) { if(stack.hasTagCompound()) return stack.getTagCompound().getBoolean(name); return false; }
	public static void setValueBool(ItemStack stack, String name, boolean value) { if(!stack.hasTagCompound()) stack.stackTagCompound = new NBTTagCompound(); stack.getTagCompound().setBoolean(name, value); }
	
	/** Wrapper for extra context used in most Consumer lambdas which are part of the guncfg */
	public static class LambdaContext {
		public final GunConfig config;
		public final EntityPlayer player;
		
		public LambdaContext(GunConfig config, EntityPlayer player) {
			this.config = config;
			this.player = player;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderHUD(Pre event, ElementType type, EntityPlayer player, ItemStack stack) {
		if(type == ElementType.CROSSHAIRS) {
			event.setCanceled(true);
			if(aimingProgress >= 1F) return;
			ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
			RenderScreenOverlay.renderCustomCrosshairs(event.resolution, Minecraft.getMinecraft().ingameGUI, gun.getConfig(stack).getCrosshair(stack));
		}
	}
}
