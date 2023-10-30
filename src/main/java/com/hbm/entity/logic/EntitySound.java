package com.hbm.entity.logic;

import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class EntitySound extends MovingSound {
    private final Entity entity;

    public EntitySound(Entity entity, String soundName) {
        super(new ResourceLocation(soundName));
        this.entity = entity;
        this.repeat = true;
        this.field_147665_h = 0;  // repeatDelay
    }

    public void update() {
        if (this.entity.isDead) {
            this.donePlaying = true;
        } else {
            this.xPosF = (float) this.entity.posX;
            this.yPosF = (float) this.entity.posY;
            this.zPosF = (float) this.entity.posZ;
        }
    }
}
