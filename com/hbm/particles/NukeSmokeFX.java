package com.hbm.particles;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;


public class NukeSmokeFX extends EntityFX{
	
	double gravity, friction, r_e, g_e, b_e, opacity_e;
	
	private static final ResourceLocation texture1 = new ResourceLocation(RefStrings.MODID + ":/textures/particle/shockwave.png");
	
	public NukeSmokeFX(World w, double xp, double yp, double zp, double xs, double ys, double zs, int siz, int lengt,double gravit, double Ra,double Ga,double Ba,double opacita,double frictio){
//		delete double Ra,double Ga,double Ba, and set r_e,g_e,b_e to 1 if you want to have a multicolored texture
        super(w, xp, yp, zp, xs, ys, zs);
        this.motionX =xs;
        this.motionY =ys;
        this.motionZ =zs;
        this.friction=frictio;
        this.particleMaxAge=lengt;
        this.particleScale=siz/10;
        this.gravity=gravit*0.001;
        this.r_e=/*Ra*/1;
        this.g_e=/*Ga*/1;
        this.b_e=/*Ba*/1;
        this.opacity_e=opacita;
    }
	public NukeSmokeFX(World w, double xp, double yp, double zp, double xs, double ys, double zs, int siz, int lengt){
//		use this if you want to use less customization
		super(w, xp, yp, zp, xs, ys, zs);
		this.motionX = xs;
		this.motionY = ys;
		this.motionZ = zs;
		this.particleMaxAge = lengt;
		this.particleScale = siz / 10;
		
		this.friction = 0.98;
		this.gravity = 0;
		this.r_e = 1;
		this.g_e = 1;
		this.b_e = 1;
		this.opacity_e = 1;
	}
	
	public NukeSmokeFX(World w, double xp, double yp, double zp){
		super(w, xp, yp, zp);
	}
	
	@Override
	public void renderParticle(Tessellator tess, float par2, float par3, float par4, float par5, float par6, float par7){
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDepthMask(false);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.5F);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
    	float PScale = 0.01F*this.particleScale;
    	float x=(float)(this.prevPosX+(this.posX-this.prevPosX)*par2-interpPosX);
    	float y=(float)(this.prevPosY+(this.posY-this.prevPosY)*par2-interpPosY);
    	float z=(float)(this.prevPosZ+(this.posZ-this.prevPosZ)*par2-interpPosZ);
        Minecraft.getMinecraft().renderEngine.bindTexture(texture1);
        tess.startDrawingQuads();
		tess.setColorRGBA_F((float)this.r_e, (float)this.g_e, (float)this.b_e, (float)this.opacity_e);
    	tess.setBrightness(/*240*/500);
        tess.addVertexWithUV(x-par3*PScale-par6*PScale, y-par4*PScale, z-par5*PScale-par7*PScale, 0, 0);
        tess.addVertexWithUV(x-par3*PScale+par6*PScale, y+par4*PScale, z-par5*PScale+par7*PScale, 1, 0);
        tess.addVertexWithUV(x+par3*PScale+par6*PScale, y+par4*PScale, z+par5*PScale+par7*PScale, 1, 1);
        tess.addVertexWithUV(x+par3*PScale-par6*PScale, y-par4*PScale, z+par5*PScale-par7*PScale, 0, 1);
		tess.draw();
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDepthMask(true);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.0F);
		GL11.glEnable(GL11.GL_LIGHTING);
	}
	
	@Override
	public int getFXLayer(){return 3;}
	
	@Override
	public void onUpdate(){
		if(particleAge>particleMaxAge)this.setDead();
		if(Minecraft.getMinecraft().gameSettings.particleSetting==2)this.setDead();
		
		if(worldObj.isRemote)this.motionHandeler();
		
		//this.particleScale-=(float)particleMaxAge/10.0;
		this.particleScale += particleMaxAge/2.5;
		
		if(this.isDead){
			
			
			
		}
		
		
		this.particleAge++;
	}
	
	public void motionHandeler(){
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		//this.motionX*=friction;
		//this.motionY*=friction;
		//this.motionZ*=friction;
		
		
		
		
		
		//this.motionY +=this.gravity;
		//this.moveEntity(this.motionX, this.motionY, this.motionZ);
		
	}
	
}