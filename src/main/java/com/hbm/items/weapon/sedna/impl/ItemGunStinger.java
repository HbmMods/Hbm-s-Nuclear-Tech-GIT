package com.hbm.items.weapon.sedna.impl;

import java.util.List;

import com.hbm.items.weapon.sedna.GunConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.hud.IHUDComponent;
import com.hbm.render.util.RenderScreenOverlay;
import com.hbm.util.Vec3NT;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class ItemGunStinger extends ItemGunBaseNT {

	public static final String KEY_LOCKINGON = "lockingon";
	public static final String KEY_LOCKONPROGRESS = "lockonprogress";

	public static float prevLockon;
	public static float lockon;
	
	public ItemGunStinger(WeaponQuality quality, GunConfig... cfg) {
		super(quality, cfg);
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isHeld) {
		super.onUpdate(stack, world, entity, slot, isHeld);
		
		if(entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			if(!world.isRemote && !isHeld && this.getIsLockingOn(stack)) {
				this.setIsLockingOn(stack, false);
			}
			
			this.prevLockon = this.lockon;
			
			if(!world.isRemote) {
				int prevTarget = this.getLockonTarget(stack);
				if(isHeld && this.getIsLockingOn(stack) && this.getIsAiming(stack) && this.getConfig(stack, 0).getReceivers(stack)[0].getMagazine(stack).getAmount(stack, player.inventory) > 0) {
					int newLockonTarget = this.getLockonTarget(player, 150D, 10D);
					
					if(newLockonTarget == -1) {
						if(!this.getIsLockedOn(stack)) resetLockon(world, stack);
					} else {
						if(!this.getIsLockedOn(stack) && newLockonTarget != prevTarget) {
							resetLockon(world, stack);
							this.setLockonTarget(stack, newLockonTarget);
						}
						progressLockon(world, stack);
						
						if(this.getLockonProgress(stack) >= 60 && !this.getIsLockedOn(stack)) {
							player.worldObj.playSoundAtEntity(player, "hbm:item.techBleep", 1F, 1F);
							this.setIsLockedOn(stack, true);
						}
					}
				} else {
					resetLockon(world, stack);
				}
			} else {
				if(this.getLockonProgress(stack) > 1) {
					this.lockon += (1F / 60F);
				} else {
					this.lockon = 0;
				}
			}
		}
	}
	
	public void resetLockon(World world, ItemStack stack) {
		this.setLockonProgress(stack, 0);
		this.setIsLockedOn(stack, false);
	}
	
	public void progressLockon(World world, ItemStack stack) {
		this.setLockonProgress(stack, this.getLockonProgress(stack) + 1);
	}
	
	public static int getLockonTarget(EntityPlayer player, double distance, double angleThreshold) {
		
		if(player == null) return -1;

		double x = player.posX;
		double y = player.posY + player.getEyeHeight();
		double z = player.posZ;

		Vec3NT delta = new Vec3NT(player.getLook(1F)).multiply(distance);
		Vec3NT look = new Vec3NT(delta).add(x, y, z);
		Vec3NT left = new Vec3NT(delta).add(x, y, z).rotateAroundYDeg(-angleThreshold).add(0, 10, 0);
		Vec3NT right = new Vec3NT(delta).add(x, y, z).rotateAroundYDeg(angleThreshold).add(0, -10, 0);
		Vec3NT pos = new Vec3NT(x, y, z);
		
		AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(Vec3NT.getMinX(look, left, right, pos), Vec3NT.getMinY(look, left, right, pos), Vec3NT.getMinZ(look, left, right, pos),
				Vec3NT.getMaxX(look, left, right, pos), Vec3NT.getMaxY(look, left, right, pos), Vec3NT.getMaxZ(look, left, right, pos));
		List<Entity> entities = player.worldObj.getEntitiesWithinAABBExcludingEntity(player, aabb);
		Entity closestEntity = null;
		double closestAngle = 360D;
		
		Vec3NT toEntity = new Vec3NT(0, 0, 0);
		
		for(Entity entity : entities) {
			if(entity.height < 0.5F || !entity.canBeCollidedWith()) continue;
			toEntity.setComponents(entity.posX - x, entity.posY + entity.height / 2D - y, entity.posZ - z);
			
			double vecProd = toEntity.xCoord * delta.xCoord + toEntity.yCoord * delta.yCoord + toEntity.zCoord * delta.zCoord;
			double bot = toEntity.lengthVector() * delta.lengthVector();
			double angle = Math.abs(Math.acos(vecProd / bot) * 180 / Math.PI);
			
			if(angle < closestAngle && angle < angleThreshold) {
				closestAngle = angle;
				closestEntity = entity;
			}
		}
		
		return closestEntity == null ? - 1 : closestEntity.getEntityId();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderHUD(Pre event, ElementType type, EntityPlayer player, ItemStack stack) {
		
		ItemGunBaseNT gun = (ItemGunBaseNT) stack.getItem();
		
		if(type == ElementType.CROSSHAIRS) {
			event.setCanceled(true);
			if(aimingProgress < 1F) return;
			RenderScreenOverlay.renderCustomCrosshairs(event.resolution, Minecraft.getMinecraft().ingameGUI, gun.getConfig(stack, 0).getCrosshair(stack));
			RenderScreenOverlay.renderStingerLockon(event.resolution, Minecraft.getMinecraft().ingameGUI);
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

	public static boolean getIsLockingOn(ItemStack stack) { return getValueBool(stack, KEY_LOCKINGON); }
	public static void setIsLockingOn(ItemStack stack, boolean value) { setValueBool(stack, KEY_LOCKINGON, value); }
	public static int getLockonProgress(ItemStack stack) { return getValueInt(stack, KEY_LOCKONPROGRESS); }
	public static void setLockonProgress(ItemStack stack, int value) { setValueInt(stack, KEY_LOCKONPROGRESS, value); }
}
