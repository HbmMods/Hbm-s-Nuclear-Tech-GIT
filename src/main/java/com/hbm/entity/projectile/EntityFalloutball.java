package com.hbm.entity.projectile;

import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntityBreakingFX;
import com.hbm.items.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import com.hbm.potion.HbmPotion;
import java.util.ArrayList;

public class EntityFalloutball extends EntityThrowable {
    public EntityFalloutball(World p_i1773_1_) {
        super(p_i1773_1_);
    }

    public EntityFalloutball(World p_i1774_1_, EntityLivingBase p_i1774_2_) {
        super(p_i1774_1_, p_i1774_2_);
    }

    public EntityFalloutball(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_) {
        super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
    }

    protected void onImpact(MovingObjectPosition p_70184_1_) {
        if (p_70184_1_.entityHit != null) {
            p_70184_1_.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0 /* damage */);

			Entity entity = p_70184_1_.entityHit;

			if(!this.worldObj.isRemote && entity instanceof EntityLivingBase) {
				if(entity instanceof EntityPlayer && ((EntityPlayer)entity).capabilities.isCreativeMode) return;
				PotionEffect effect = new PotionEffect(HbmPotion.radiation.id, 10 * 60 * 20, 0);
				effect.setCurativeItems(new ArrayList());
				((EntityLivingBase) entity).addPotionEffect(effect);
			}
        }

		if(this.worldObj != null && Minecraft.getMinecraft() != null && this.worldObj.isRemote) {
			Minecraft mc = Minecraft.getMinecraft();

			int i = mc.gameSettings.particleSetting;

			if (i == 1 && this.worldObj.rand.nextInt(3) == 0) {
				i = 2;
			}

			if(i <= 1) {
				for (int j = 0; j < 8; ++j) {
					mc.effectRenderer.addEffect((EntityFX) new EntityBreakingFX(this.worldObj, this.posX, this.posY, this.posZ, ModItems.falloutball));
				}
			}
		}

        if (!this.worldObj.isRemote) {
            this.setDead();
        }
    }
}
