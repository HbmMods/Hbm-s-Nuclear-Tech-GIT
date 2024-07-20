package com.hbm.render.entity.mob;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.mob.EntityRADBeast;
import com.hbm.lib.RefStrings;
import com.hbm.render.model.ModelM65Blaze;
import com.hbm.render.util.BeamPronter;
import com.hbm.render.util.BeamPronter.EnumBeamType;
import com.hbm.render.util.BeamPronter.EnumWaveType;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBlaze;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

public class RenderRADBeast extends RenderLiving {

	private static final ResourceLocation blazeTextures = new ResourceLocation(RefStrings.MODID, "textures/entity/radbeast.png");
	private static final ResourceLocation mask = new ResourceLocation(RefStrings.MODID, "textures/models/ModelM65Blaze.png");
	private int blazeModel;

	public RenderRADBeast() {
		super(new ModelBlaze(), 0.5F);
		this.blazeModel = ((ModelBlaze) this.mainModel).func_78104_a();
	}

	public void doRender(EntityRADBeast entity, double x, double y, double z, float r0, float r1) {
		int i = ((ModelBlaze) this.mainModel).func_78104_a();

		if(i != this.blazeModel) {
			this.blazeModel = i;
			this.mainModel = new ModelBlaze();
		}

		Entity victim = entity.getUnfortunateSoul();

		if(victim != null && entity.posY > 0.1) {

			GL11.glPushMatrix();

			GL11.glTranslated(x, y + 1.25, z);

			double sx = entity.posX;
			double sy = entity.posY + 1.25;
			double sz = entity.posZ;

			double tX = victim.posX;
			double tY = victim.posY + victim.height / 2;
			double tZ = victim.posZ;

			if(victim == Minecraft.getMinecraft().thePlayer)
				tY -= 1.5;

			double length = Math.sqrt(Math.pow(tX - sx, 2) + Math.pow(tY - sy, 2) + Math.pow(tZ - sz, 2));
			if(length < 200) BeamPronter.prontBeam(Vec3.createVectorHelper(tX - sx, tY - sy, tZ - sz), EnumWaveType.RANDOM, EnumBeamType.SOLID, 0x004000, 0x004000, (int) (entity.worldObj.getTotalWorldTime() % 1000 + 1), (int) (length * 5), 0.125F, 2, 0.03125F, 256);

			GL11.glPopMatrix();
		}

		super.doRender((EntityLiving) entity, x, y, z, r0, r1);
	}

	protected ResourceLocation getEntityTexture(EntityRADBeast p_110775_1_) {
		return blazeTextures;
	}

	public void doRender(EntityLiving p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
		this.doRender((EntityRADBeast) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	public void doRender(EntityLivingBase p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
		this.doRender((EntityRADBeast) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return this.getEntityTexture((EntityRADBeast) p_110775_1_);
	}

	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
		this.doRender((EntityRADBeast) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	private ModelM65Blaze modelM65;

	protected int shouldRenderPass(EntityLivingBase p_77032_1_, int p_77032_2_, float p_77032_3_) {

		if(p_77032_2_ == 0) {
			this.bindTexture(mask);

			if(this.modelM65 == null) {
				this.modelM65 = new ModelM65Blaze();
			}

			this.setRenderPassModel(modelM65);
			return 1;
		}

		return super.shouldRenderPass(p_77032_1_, p_77032_2_, p_77032_3_);
	}
}
