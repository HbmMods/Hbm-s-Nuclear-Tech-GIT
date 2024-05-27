package com.hbm.particle;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

@SideOnly(Side.CLIENT)
public class ParticleFoundry extends EntityFX {

	protected int color;
	protected ForgeDirection dir;
	/* how far the metal splooshes down from the base point */
	protected double length;
	/* the material coming right out of the faucet, either above or next to the base point */
	protected double base;
	/* how far the base part goes back */
	protected double offset;
	
	public static final ResourceLocation lava = new ResourceLocation(RefStrings.MODID + ":textures/blocks/lava_gray.png");
	private TextureManager theRenderEngine;

	public ParticleFoundry(TextureManager texan, World world, double x, double y, double z, int color, int direction, double length, double base, double offset) {
		super(world, x, y, z);
		this.color = color;
		this.dir = ForgeDirection.getOrientation(direction);
		this.theRenderEngine = texan; //it's a little texan man we enslaved to do the texture binding for us
		this.length = length;
		this.base = base;
		this.offset = offset;
		
		this.particleMaxAge = 20;
	}
	
	@Override
	public void onUpdate() {
		
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		if(this.particleAge++ >= this.particleMaxAge) {
			this.setDead();
		}
	}

	@Override
	public int getFXLayer() {
		return 3;
	}

	@Override
	public void renderParticle(Tessellator tess, float interp, float x, float y, float z, float oX, float oZ) {

		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		double dX = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)interp;
		double dY = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)interp;
		double dZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)interp;
		
		float pX = (float) ((this.prevPosX + (this.posX - this.prevPosX) * (double) interp - dX));
		float pY = (float) ((this.prevPosY + (this.posY - this.prevPosY) * (double) interp - dY));
		float pZ = (float) ((this.prevPosZ + (this.posZ - this.prevPosZ) * (double) interp - dZ));
		
		ForgeDirection rot = this.dir.getRotation(ForgeDirection.UP);
		double width = 0.0625 + ((this.particleAge + interp) / this.particleMaxAge) * 0.0625;
		double girth = 0.125 * (1 - ((this.particleAge + interp) / this.particleMaxAge));
		
		Color color = new Color(this.color).brighter();
		double brightener = 0.7D;
		int r = (int) (255D - (255D - color.getRed()) * brightener);
		int g = (int) (255D - (255D - color.getGreen()) * brightener);
		int b = (int) (255D - (255D - color.getBlue()) * brightener);

		GL11.glColor3f(r / 255F, g / 255F, b / 255F);

		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glTranslatef(pX, pY, pZ);
		this.theRenderEngine.bindTexture(lava);
		
		tess.startDrawingQuads();
		tess.setNormal(0.0F, 1.0F, 0.0F);
		tess.setBrightness(240);

		double dirXG = dir.offsetX * girth;
		double dirZG = dir.offsetZ * girth;
		double rotXW = rot.offsetX * width;
		double rotZW = rot.offsetZ * width;
		
		double uMin = 0.5 - width;
		double uMax = 0.5 + width;
		double vMin = 0;
		double vMax = length;
		
		double add = (int)(System.currentTimeMillis() / 100 % 16) / 16D;

		//lower back
		tess.addVertexWithUV(rotXW,		girth,		rotZW,	uMax,	vMax + add + girth);
		tess.addVertexWithUV(-rotXW,	girth,		-rotZW,	uMin,	vMax + add + girth);
		tess.addVertexWithUV(-rotXW,	-length,	-rotZW,	uMin,	vMin + add);
		tess.addVertexWithUV(rotXW,		-length,	rotZW,	uMax,	vMin + add);

		//lower front
		tess.addVertexWithUV(dirXG + rotXW,	0,			dirZG + rotZW,	uMax,	vMax + add);
		tess.addVertexWithUV(dirXG - rotXW,	0,			dirZG - rotZW,	uMin,	vMax + add);
		tess.addVertexWithUV(dirXG - rotXW,	-length,	dirZG - rotZW,	uMin,	vMin + add);
		tess.addVertexWithUV(dirXG + rotXW,	-length,	dirZG + rotZW,	uMax,	vMin + add);
		
		double wMin = 0;
		double wMax = girth;
		
		//lower left
		tess.addVertexWithUV(rotXW,			girth,		rotZW,			wMin, vMax + add + girth);
		tess.addVertexWithUV(dirXG + rotXW,	0,			dirZG + rotZW,	wMax, vMax + add);
		tess.addVertexWithUV(dirXG + rotXW,	-length,	dirZG + rotZW,	wMax, vMin + add);
		tess.addVertexWithUV(rotXW,			-length,	rotZW,			wMin, vMin + add);
		
		//lower right
		tess.addVertexWithUV(-rotXW,		girth,		-rotZW,			wMin, vMax + add + girth);
		tess.addVertexWithUV(dirXG - rotXW,	0,			dirZG - rotZW,	wMax, vMax + add);
		tess.addVertexWithUV(dirXG - rotXW,	-length,	dirZG - rotZW,	wMax, vMin + add);
		tess.addVertexWithUV(-rotXW,		-length,	-rotZW,			wMin, vMin + add);

		double dirOX = dir.offsetX * offset;
		double dirOZ = dir.offsetZ * offset;
		
		vMax = offset;

		//upper back
		tess.addVertexWithUV(rotXW,				0,		rotZW,			uMax, vMax - add);
		tess.addVertexWithUV(-rotXW,			0,		-rotZW,			uMin, vMax - add);
		tess.addVertexWithUV(-rotXW - dirOX,	base,	-rotZW - dirOZ,	uMin, vMin - add);
		tess.addVertexWithUV(rotXW - dirOX,		base,	rotZW - dirOZ,	uMax, vMin - add);
		
		//upper front
		tess.addVertexWithUV(rotXW,				girth,			rotZW,			uMax, vMax - add + 0.25);
		tess.addVertexWithUV(-rotXW,			girth,			-rotZW,			uMin, vMax - add + 0.25);
		tess.addVertexWithUV(-rotXW - dirOX,	base + girth,	-rotZW - dirOZ,	uMin, vMin - add + 0.25);
		tess.addVertexWithUV(rotXW - dirOX,		base + girth,	rotZW - dirOZ,	uMax, vMin - add + 0.25);
		
		//upper left
		tess.addVertexWithUV(rotXW,			0,				rotZW,			wMax, vMax - add + 0.75);
		tess.addVertexWithUV(rotXW,			girth,			rotZW,			wMin, vMax - add + 0.75);
		tess.addVertexWithUV(rotXW - dirOX,	base + girth,	rotZW - dirOZ,	wMin, vMin - add + 0.75);
		tess.addVertexWithUV(rotXW - dirOX,	base,			rotZW - dirOZ,	wMax, vMin - add + 0.75);
		
		//upper right
		tess.addVertexWithUV(-rotXW,			0,				-rotZW,			wMax, vMax - add + 0.75);
		tess.addVertexWithUV(-rotXW,			girth,			-rotZW,			wMin, vMax - add + 0.75);
		tess.addVertexWithUV(-rotXW - dirOX,	base + girth,	-rotZW - dirOZ,	wMin, vMin - add + 0.75);
		tess.addVertexWithUV(-rotXW - dirOX,	base,			-rotZW - dirOZ, wMax, vMin - add + 0.75);
		
		vMax = 0.125F;
		
		//bend
		tess.addVertexWithUV(dirXG + rotXW,	0,		dirZG + rotZW,	uMax,	vMin + add + 0.75);
		tess.addVertexWithUV(dirXG - rotXW,	0,		dirZG - rotZW,	uMin,	vMin + add + 0.75);
		tess.addVertexWithUV(-rotXW,		girth,	-rotZW,			uMin,	vMax + add + 0.75);
		tess.addVertexWithUV(rotXW,			girth,	rotZW,			uMax,	vMax + add + 0.75);

		tess.draw();
		
		GL11.glColor3f(1F, 1F, 1F);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
	}
}
