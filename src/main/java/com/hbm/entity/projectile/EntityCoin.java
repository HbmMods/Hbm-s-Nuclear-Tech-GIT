package com.hbm.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityCoin extends Entity {

    public EntityCoin(World world) {
        super(world);
    }

    @Override
    protected void entityInit() { }

    @Override
    protected void readEntityFromNBT(NBTTagCompound tag) { }

    @Override
    protected void writeEntityToNBT(NBTTagCompound tag) { }

    @Override
    public void onUpdate() {
        super.onUpdate();
    }

    public void setThrower(EntityPlayer player) {
        // Legacy compatibility: this lightweight placeholder entity ignores thrower metadata.
    }
}
