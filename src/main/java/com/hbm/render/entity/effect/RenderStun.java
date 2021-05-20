package com.hbm.render.entity.effect;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.effect.EntityStun;
import com.hbm.entity.grenade.EntityGrenadeStunning;
import com.hbm.lib.RefStrings;
import com.hbm.render.util.BeamPronter;
import com.hbm.render.util.BeamPronter.EnumBeamType;
import com.hbm.render.util.BeamPronter.EnumWaveType;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class RenderStun extends Render
{

	private static final ResourceLocation ringModelRL = new ResourceLocation(/*"/assets/" + */RefStrings.MODID, "models/Ring.obj");
	private IModelCustom ringModel;
    private ResourceLocation ringTexture;
    
    public RenderStun()
    {
    	ringModel = AdvancedModelLoader.loadModel(ringModelRL);
    	ringTexture = new ResourceLocation(RefStrings.MODID, "textures/models/EMPBlast.png");
    }

	@Override
	public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float p_76986_9_)
	{
		GL11.glPushMatrix();
        GL11.glTranslated(x, y + 1, z);

        double sx = entity.posX;
        double sy = entity.posY + 1;
        double sz = entity.posZ;
        
        for(double[] target : ((EntityGrenadeStunning)entity).targets)
        {
        	
        	double length = Math.sqrt(Math.pow(target[0] - sx, 2) + Math.pow(target[1] - sy, 2) + Math.pow(target[2] - sz, 2));
        	
	        BeamPronter.prontBeam(Vec3.createVectorHelper(target[0] - sx, target[1] - sy, target[2] - sz), EnumWaveType.RANDOM, EnumBeamType.SOLID, 0x404040, 0x404040, (int) (entity.worldObj.getTotalWorldTime() % 1000 + 1), (int) (length * 5), 0.125F, 2, 0.03125F);
        }
        
		GL11.glPopMatrix();
		this.renderRing((EntityStun)entity, x, y, z, p_76986_8_, p_76986_9_);
	}
	
	public void renderRing(EntityStun p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_,
			float p_76986_8_, float p_76986_9_)
	{
		GL11.glPushMatrix();
        GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glScalef(p_76986_1_.scale, 1F, p_76986_1_.scale);
        
        bindTexture(ringTexture);
        ringModel.renderAll();
        GL11.glPopMatrix();
	}
	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_)
	{
		return null;
	}

}
