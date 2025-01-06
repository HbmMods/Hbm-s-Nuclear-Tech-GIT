package com.hbm.entity.mob.ai;

import com.hbm.handler.HbmKeybinds.EnumKeybind;
import com.hbm.items.weapon.sedna.GunConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.Receiver;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.GunState;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;

public class EntityAIFireGun extends EntityAIBase {

	private final EntityLiving host;

	private double attackMoveSpeed = 1.0D; // how fast we move while in this state
	private double maxRange = 20; // how far our target can be before we stop shooting
	private int burstTime = 10; // maximum number of ticks in a burst (for automatic weapons)
	private int minWait = 10; // minimum number of ticks to wait between bursts/shots
	private int maxWait = 40; // maximum number of ticks to wait between bursts/shots
	private float inaccuracy = 30; // how many degrees of inaccuracy does the AI have

	// state timers
	private int attackTimer = 0;
	private FireState state = FireState.IDLE;
	private int stateTimer = 0;

	private static enum FireState {
		IDLE,
		WAIT,
		FIRING,
		RELOADING,
	}
	
	public EntityAIFireGun(EntityLiving host) {
		this.host = host;
	}

	@Override
	public boolean shouldExecute() {
		return host.getAttackTarget() != null && getYerGun() != null;
	}

	@Override
	public void updateTask() {
		EntityLivingBase target = host.getAttackTarget();
		ItemStack stack = host.getHeldItem();
		ItemGunBaseNT gun = getYerGun();

		gun.onUpdate(stack, host.worldObj, host, 0, true);

		double distanceToTargetSquared = host.getDistanceSq(target.posX, target.posY, target.posZ);
		boolean canSeeTarget = host.getEntitySenses().canSee(target);

		if(canSeeTarget) {
			attackTimer++;
		} else {
			attackTimer = 0;
		}

		if(distanceToTargetSquared < maxRange * maxRange && attackTimer > 20) {
			host.getNavigator().clearPathEntity();
		} else {
			host.getNavigator().tryMoveToEntityLiving(target, attackMoveSpeed);
		}

		host.getLookHelper().setLookPositionWithEntity(target, 30.0F, 30.0F);

		stateTimer--;
		if(stateTimer < 0) {
			stateTimer = 0;

			if(state == FireState.WAIT) {
				updateState(FireState.IDLE, 0, gun, stack);
			} else if(state != FireState.IDLE) {
				updateState(FireState.WAIT, host.worldObj.rand.nextInt(maxWait - minWait) + minWait, gun, stack);
			}
		} else if(state == FireState.FIRING) {
			// Keep the trigger held throughout the duration of firing
			updateKeybind(gun, stack, EnumKeybind.GUN_PRIMARY);
		}

		if(canSeeTarget && distanceToTargetSquared < maxRange * maxRange) {
			if(state == FireState.IDLE) {
				GunConfig config = gun.getConfig(stack, 0);
				Receiver rec = config.getReceivers(stack)[0];
				if(rec.getMagazine(stack).getAmount(stack, null) <= 0) {
					updateState(FireState.RELOADING, 20, gun, stack);
				} else if(ItemGunBaseNT.getState(stack, 0) == GunState.IDLE) {
					updateState(FireState.FIRING, host.worldObj.rand.nextInt(burstTime), gun, stack);
				}
			}
		}
	}

	private void updateState(FireState toState, int time, ItemGunBaseNT gun, ItemStack stack) {
		state = toState;
		stateTimer = time;

		switch(state) {
		case FIRING: updateKeybind(gun, stack, EnumKeybind.GUN_PRIMARY);
		case RELOADING: updateKeybind(gun, stack, EnumKeybind.RELOAD);
		default: clearKeybinds(gun, stack); break;
		}
	}

	private void clearKeybinds(ItemGunBaseNT gun, ItemStack stack) {
		updateKeybind(gun, stack, null);
	}

	private void updateKeybind(ItemGunBaseNT gun, ItemStack stack, EnumKeybind bind) {
		// Turn body to face firing direction, since the gun is attached to that, not the head
		// Also apply accuracy debuff just before firing
		if(bind != null && bind != EnumKeybind.RELOAD) {
			host.rotationYawHead += (host.worldObj.rand.nextFloat() - 0.5F) * inaccuracy;
			host.rotationPitch += (host.worldObj.rand.nextFloat() - 0.5F) * inaccuracy;
			host.rotationYaw = host.rotationYawHead;
		}

		gun.handleKeybind(host, null, stack, EnumKeybind.GUN_PRIMARY, bind == EnumKeybind.GUN_PRIMARY);
		gun.handleKeybind(host, null, stack, EnumKeybind.GUN_SECONDARY, bind == EnumKeybind.GUN_SECONDARY);
		gun.handleKeybind(host, null, stack, EnumKeybind.GUN_TERTIARY, bind == EnumKeybind.GUN_TERTIARY);
		gun.handleKeybind(host, null, stack, EnumKeybind.RELOAD, bind == EnumKeybind.RELOAD);
	}

	public ItemGunBaseNT getYerGun() {
		ItemStack stack = host.getHeldItem();

		if(stack == null || !(stack.getItem() instanceof ItemGunBaseNT)) return null;

		return (ItemGunBaseNT) stack.getItem();
	}
	
}
