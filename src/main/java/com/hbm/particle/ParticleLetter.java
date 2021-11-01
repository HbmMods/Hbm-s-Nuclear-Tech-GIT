package com.hbm.particle;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

public class ParticleLetter extends EntityFX {
	
	int color;
	char c;
	
	public ParticleLetter(World world, double x, double y, double z, int color, char c) {
		super(world, x, y, z);
		this.particleMaxAge = 30;
		this.color = color;
		this.c = c;
	}

	public int getFXLayer() {
		return 3;
	}

	public void renderParticle(Tessellator tess, float interp, float x, float y, float z, float tx, float tz) {

	    GL11.glPushMatrix();
	    
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0);
		GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 0.0F);
		RenderHelper.disableStandardItemLighting();
		
		Minecraft mc = Minecraft.getMinecraft();
	    FontRenderer font = mc.fontRenderer;
	    
		this.rotationYaw = -mc.thePlayer.rotationYaw;
		this.rotationPitch = mc.thePlayer.rotationPitch;
		
	    float pX = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double)interp - interpPosX);
	    float pY = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double)interp - interpPosY);
	    float pZ = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double)interp - interpPosZ);
	    
	    GL11.glTranslatef(pX, pY, pZ);
	    GL11.glRotatef(this.rotationYaw, 0.0F, 1.0F, 0.0F);
	    GL11.glRotatef(this.rotationPitch, 1.0F, 0.0F, 0.0F);
	    GL11.glScalef(-1.0F, -1.0F, 1.0F);
	    
	    float time = (this.particleAge + interp) * 4F / this.particleMaxAge;
	    
	    double scale = 1 - (1D / Math.pow(Math.E, time));
	    
		this.particleAlpha = 1 - (((float)this.particleAge + interp) / (float)this.particleMaxAge);
		
		if(particleAlpha < 0)
			particleAlpha = 0;
		
		int alpha = (int) (particleAlpha * 255);
		
		if(alpha > 255)
			alpha = 255;
		
		if(alpha < 10)
			alpha = 10;
		
		int col = color + (alpha << 24);
	    
	    GL11.glScaled(scale, scale, scale);
	    
	    font.drawString(String.valueOf(c), -(int)(font.getStringWidth(String.valueOf(c)) * 0.5F), -(int)(font.FONT_HEIGHT * 0.5F), col);
	    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		GL11.glPolygonOffset(0.0F, 0.0F);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
		GL11.glEnable(GL11.GL_LIGHTING);
		
	    GL11.glPopMatrix();
	}

	/*public void func_70539_a(Tessellator p_renderParticle_1_, float par2, float par3, float par4, float par5, float par6, float par7)
	  {
	    this.field_70177_z = (-Minecraft.func_71410_x().field_71439_g.field_70177_z);
	    this.field_70125_A = Minecraft.func_71410_x().field_71439_g.field_70125_A;
	    float size = 0.1F * this.field_70544_f;
	    try
	    {
	      this.locX = ((float)(this.field_70169_q + (this.field_70165_t - this.field_70169_q) * par2 - field_70556_an));
	      this.locY = ((float)(this.field_70167_r + (this.field_70163_u - this.field_70167_r) * par2 - field_70554_ao));
	      this.locZ = ((float)(this.field_70166_s + (this.field_70161_v - this.field_70166_s) * par2 - field_70555_ap));
	      par3 *= size;
	      par4 *= size;
	      par5 *= size;
	      par6 *= size;
	      par7 *= size;
	    }
	    catch (Throwable ex) {}
	    GL11.glPushMatrix();
	    if (this.shouldOnTop) {
	      GL11.glDepthFunc(519);
	    } else {
	      GL11.glDepthFunc(515);
	    }
	    GL11.glTranslatef(this.locX, this.locY, this.locZ);
	    GL11.glRotatef(this.field_70177_z, 0.0F, 1.0F, 0.0F);
	    GL11.glRotatef(this.field_70125_A, 1.0F, 0.0F, 0.0F);
	    
	    GL11.glScalef(-1.0F, -1.0F, 1.0F);
	    GL11.glScaled(this.field_70544_f * 0.008D, this.field_70544_f * 0.008D, this.field_70544_f * 0.008D);
	    if (this.criticalhit) {
	      GL11.glScaled(0.5D, 0.5D, 0.5D);
	    }
	    FontRenderer fontRenderer = Minecraft.func_71410_x().field_71466_p;
	    OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 240.0F, 0.003662109F);
	    GL11.glEnable(3553);
	    GL11.glDisable(3042);
	    GL11.glDepthMask(true);
	    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	    GL11.glEnable(3553);
	    GL11.glEnable(2929);
	    GL11.glDisable(2896);
	    GL11.glBlendFunc(770, 771);
	    GL11.glEnable(3042);
	    GL11.glEnable(3008);
	    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	    if ((this.criticalhit) && (DIConfig.mainInstance().showCriticalStrikes))
	    {
	      if (DIConfig.mainInstance().useDropShadows) {
	        fontRenderer.func_78276_b(this.critical, -MathHelper.func_76141_d(fontRenderer.func_78256_a(this.critical) / 2.0F) + 1, -MathHelper.func_76141_d(fontRenderer.field_78288_b / 2.0F) + 1, 0);
	      }
	      fontRenderer.func_78276_b(this.critical, -MathHelper.func_76141_d(fontRenderer.func_78256_a(this.critical) / 2.0F), -MathHelper.func_76141_d(fontRenderer.field_78288_b / 2.0F), -7600622);
	    }
	    else if (!this.criticalhit)
	    {
	      int color = this.heal ? DIConfig.mainInstance().healColor : DIConfig.mainInstance().DIColor;
	      Color c_Color = new Color(color);
	      c_Color = new Color(c_Color.getRed() / 5.0F / 255.0F, c_Color.getGreen() / 5.0F / 255.0F, c_Color.getBlue() / 5.0F / 255.0F);
	      if (DIConfig.mainInstance().useDropShadows) {
	        fontRenderer.func_78276_b(String.valueOf(this.Damage), -MathHelper.func_76141_d(fontRenderer.func_78256_a(this.Damage + "") / 2.0F) + 1, -MathHelper.func_76141_d(fontRenderer.field_78288_b / 2.0F) + 1, c_Color.getRGB());
	      }
	      fontRenderer.func_78276_b(String.valueOf(this.Damage), -MathHelper.func_76141_d(fontRenderer.func_78256_a(this.Damage + "") / 2.0F), -MathHelper.func_76141_d(fontRenderer.field_78288_b / 2.0F), color);
	    }
	    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	    GL11.glDepthFunc(515);
	    
	    GL11.glPopMatrix();
	    if (this.grow)
	    {
	      this.field_70544_f *= 1.08F;
	      if (this.field_70544_f > diConfig.Size * 3.0D) {
	        this.grow = false;
	      }
	    }
	    else
	    {
	      this.field_70544_f *= 0.96F;
	    }
	  }*/
}
