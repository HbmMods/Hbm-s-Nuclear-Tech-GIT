package com.hbm.entity.grenade;

import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemGrenade;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.world.World;

public class EntityGrenadeCats extends EntityGrenadeBouncyBase {

    public EntityGrenadeCats(World world) {
        super(world);
    }

    public EntityGrenadeCats(World world, EntityLivingBase living) {
        super(world, living);
    }

    public EntityGrenadeCats(World world, double posX, double posY, double posZ) {
        super(world, posX, posY, posZ);
    }

    @Override
    public void explode() {
        if (!this.worldObj.isRemote) {
            this.setDead();

            for(int i = 0; i < 5; i++) {
                EntityOcelot cat = new EntityOcelot(worldObj);
                cat.setPosition(posX, posY, posZ);
                cat.motionX = rand.nextGaussian() * 0.1D;
                cat.motionY = -0.25D;
                cat.motionZ = rand.nextGaussian() * 0.1D;

                worldObj.spawnEntityInWorld(cat);
                cat.onSpawnWithEgg(null);
            }
        }
    }

    @Override
    protected int getMaxTimer() {
        return ItemGrenade.getFuseTicks(ModItems.grenate_cats);
    }

    @Override
    protected double getBounceMod() {
        return 0.25D;
    }
}
