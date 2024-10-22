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

    private double attackMoveSpeed = 1.0D;

    private int attackTimer = 0;
    private double maxRange = 20;
    
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

        if(canSeeTarget && distanceToTargetSquared < maxRange * maxRange) {
            GunConfig config = gun.getConfig(stack, 0);
            Receiver rec = config.getReceivers(stack)[0];
            if(rec.getMagazine(stack).getAmount(stack) <= 0) {
                gun.handleKeybind(host, null, stack, EnumKeybind.GUN_PRIMARY, false);
                gun.handleKeybind(host, null, stack, EnumKeybind.RELOAD, true);
            } else if(ItemGunBaseNT.getState(stack, 0) == GunState.IDLE) {
                gun.handleKeybind(host, null, stack, EnumKeybind.GUN_PRIMARY, true);
                gun.handleKeybind(host, null, stack, EnumKeybind.RELOAD, false);
            } else {
                gun.handleKeybind(host, null, stack, EnumKeybind.GUN_PRIMARY, false);
                gun.handleKeybind(host, null, stack, EnumKeybind.RELOAD, false);
            }
        } else {
            gun.handleKeybind(host, null, stack, EnumKeybind.GUN_PRIMARY, false);
            gun.handleKeybind(host, null, stack, EnumKeybind.RELOAD, false);
        }
    }

    public ItemGunBaseNT getYerGun() {
        ItemStack stack = host.getHeldItem();

        if(stack == null || !(stack.getItem() instanceof ItemGunBaseNT)) return null;

        return (ItemGunBaseNT) stack.getItem();
    }
    
}
