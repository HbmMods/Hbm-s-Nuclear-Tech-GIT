package com.hbm.render.entity.mob;

import com.hbm.entity.mob.EntityUndeadSoldier;
import com.hbm.render.model.ModelSkeletonNT;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class RenderUndeadSoldier extends RenderBiped {

	public static ResourceLocation textureZombie = new ResourceLocation("textures/entity/zombie/zombie.png");
	public static ResourceLocation textureSkeleton = new ResourceLocation("textures/entity/skeleton/skeleton.png");

	public static ModelBiped modelZombie = new ModelZombie();
	public static ModelBiped modelSkeleton = new ModelSkeletonNT();

	public RenderUndeadSoldier() {
		super(modelZombie, 0.5F);
	}

	@Override
	protected void preRenderCallback(EntityLivingBase living, float interp) {
		byte type = living.getDataWatcher().getWatchableObjectByte(EntityUndeadSoldier.DW_TYPE);
		if(type == EntityUndeadSoldier.TYPE_ZOMBIE) this.mainModel = this.modelBipedMain = modelZombie;
		if(type == EntityUndeadSoldier.TYPE_SKELETON) this.mainModel = this.modelBipedMain = modelSkeleton;
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityLiving living) {
		byte type = living.getDataWatcher().getWatchableObjectByte(EntityUndeadSoldier.DW_TYPE);
		if(type == EntityUndeadSoldier.TYPE_ZOMBIE) return textureZombie;
		if(type == EntityUndeadSoldier.TYPE_SKELETON) return textureSkeleton;
		return null;
	}
}
