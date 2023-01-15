package com.hbm.render.tileentity;

import java.nio.FloatBuffer;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.util.RenderSparks;
import com.hbm.tileentity.machine.TileEntityCore;

import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class RenderCore extends TileEntitySpecialRenderer {

	private static final ResourceLocation sky = new ResourceLocation("textures/environment/end_sky.png");
	private static final ResourceLocation portal = new ResourceLocation("textures/entity/end_portal.png");
	private static final Random random = new Random(31100L);
	FloatBuffer buffer = GLAllocation.createDirectFloatBuffer(16);

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {

		TileEntityCore core = (TileEntityCore) tileEntity;

		if(core.heat == 0) {
			renderStandby(x, y, z);
		} else {

			GL11.glPushMatrix();
			GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
			
			if(core.meltdownTick)
				renderFlare(core);
			else
				renderOrb(core, 0, 0, 0);
			
			GL11.glPopMatrix();
		}
	}

	public void renderStandby(double x, double y, double z) {

		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_TEXTURE_2D);

		GL11.glScalef(0.25F, 0.25F, 0.25F);
		GL11.glColor3f(0.5F, 0.5F, 0.5F);
		ResourceManager.sphere_uv.renderAll();

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		GL11.glScalef(1.25F, 1.25F, 1.25F);
		GL11.glColor3f(0.1F, 0.1F, 0.1F);
		ResourceManager.sphere_uv.renderAll();
		GL11.glDisable(GL11.GL_BLEND);

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LIGHTING);

		if((System.currentTimeMillis() / 100) % 10 == 0) {
			for(int i = 0; i < 3; i++) {
				RenderSparks.renderSpark((int) System.currentTimeMillis() / 100 + i * 10000, 0, 0, 0, 1.5F, 5, 10, 0xFFFF00, 0xFFFFFF);
				RenderSparks.renderSpark((int) System.currentTimeMillis() / 50 + i * 10000, 0, 0, 0, 1.5F, 5, 10, 0xFFFF00, 0xFFFFFF);
			}
		}

		GL11.glPopMatrix();
	}
    
    public void renderOrb(TileEntityCore tile, double x, double y, double z) {

        GL11.glPushMatrix();
        //GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);

        int color = tile.color;

        float r = ((color & 0xFF0000) >> 16) / 256F;
        float g = ((color & 0x00FF00) >> 8) / 256F;
        float b = ((color & 0x0000FF) >> 0) / 256F;
        float mod = 0.4F;
		GL11.glColor3f(r * mod, g * mod, b * mod);
		
		int tot = tile.tanks[0].getMaxFill() + tile.tanks[1].getMaxFill();
		int fill = tile.tanks[0].getFill() + tile.tanks[1].getFill();
		
		float scale = 4.5F * fill / tot + 0.5F;
		GL11.glScalef(scale, scale, scale);

        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);

		GL11.glScalef(0.25F, 0.25F, 0.25F);
		ResourceManager.sphere_ruv.renderAll();
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		
		double ix = (tile.getWorldObj().getTotalWorldTime() * 0.1D) % (Math.PI * 2D);
		double t = 0.8F;
		float pulse = (float) ((1D / t) * Math.atan((t * Math.sin(ix)) / (1 - t * Math.cos(ix))));
		
		pulse += 1D;
		pulse /= 2D;
		
		for(int i = 0; i <= 16; i++) {

			GL11.glPushMatrix();
			
			float s = 1F + 0.25F * i;
			s += (pulse * (20 - i)) * 0.125F;
			
			GL11.glScalef(s, s, s);
			ResourceManager.sphere_ruv.renderAll();
			GL11.glPopMatrix();
		}
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
	}

	public void renderFlare(TileEntityCore core) {

		int color = core.color;
		float r = ((color & 0xFF0000) >> 16) / 255F;
		float g = ((color & 0x00FF00) >> 8) / 255F;
		float b = ((color & 0x0000FF) >> 0) / 255F;
		
		Tessellator tessellator = Tessellator.instance;
		RenderHelper.disableStandardItemLighting();
		float f1 = (core.getWorldObj().getTotalWorldTime()) / 200.0F;
		float f2 = 0.0F;

		Random random = new Random(432L);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.0F);
		GL11.glDepthMask(false);
		RenderHelper.disableStandardItemLighting();
		GL11.glPushMatrix();
		
		double ix = (core.getWorldObj().getTotalWorldTime() * 0.2D) % (Math.PI * 2D);
		double t = 0.8F;
		float pulse = (float) ((1D / t) * Math.atan((t * Math.sin(ix)) / (1 - t * Math.cos(ix))));
		
		pulse += 1D;
		pulse /= 2D;
		
		float s = 0.875F;
		s += pulse * 0.125F;
		
		GL11.glScalef(s, s, s);

		int count = 150;
		for(int i = 0; i < count; i++) {
			GL11.glRotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(random.nextFloat() * 360.0F + f1 * 90.0F, 0.0F, 0.0F, 1.0F);
			tessellator.startDrawing(6);
			float f3 = random.nextFloat() * 2.0F + 5.0F + f2 * 10F;
			float f4 = random.nextFloat() * 1.0F + 1.0F + f2 * 2.0F;
			tessellator.setColorRGBA_F(r, g, b, 1F);
			tessellator.addVertex(0.0D, 0.0D, 0.0D);
			tessellator.setColorRGBA_F(r, g, b, 0F);
			tessellator.addVertex(-0.866D * f4, f3, -0.5F * f4);
			tessellator.addVertex(0.866D * f4, f3, -0.5F * f4);
			tessellator.addVertex(0.0D, f3, 1.0F * f4);
			tessellator.addVertex(-0.866D * f4, f3, -0.5F * f4);
			GL11.glScalef(0.999F, 0.999F, 0.999F);
			tessellator.draw();
		}

		GL11.glPopMatrix();
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
		RenderHelper.enableStandardItemLighting();
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
	
    public void renderVoid(TileEntity tile, double x, double y, double z) {
        
        TileEntityCore core = (TileEntityCore)tile;

		World world = tile.getWorldObj();
        GL11.glPushMatrix();
        
        GL11.glDisable(GL11.GL_LIGHTING);
        
        random.setSeed(31110L);

		GL11.glDisable(GL11.GL_LIGHTING);

		GL11.glTexGeni(GL11.GL_S, GL11.GL_TEXTURE_GEN_MODE, GL11.GL_EYE_LINEAR);
		GL11.glTexGeni(GL11.GL_T, GL11.GL_TEXTURE_GEN_MODE, GL11.GL_EYE_LINEAR);
		GL11.glTexGeni(GL11.GL_R, GL11.GL_TEXTURE_GEN_MODE, GL11.GL_EYE_LINEAR);
		GL11.glTexGeni(GL11.GL_Q, GL11.GL_TEXTURE_GEN_MODE, GL11.GL_EYE_LINEAR);
		GL11.glEnable(GL11.GL_TEXTURE_GEN_S);
		GL11.glEnable(GL11.GL_TEXTURE_GEN_T);
		GL11.glEnable(GL11.GL_TEXTURE_GEN_R);
		GL11.glEnable(GL11.GL_TEXTURE_GEN_Q);
		GL11.glMatrixMode(GL11.GL_TEXTURE);
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
		float f100 = world.getTotalWorldTime() % 500L / 500F;
		GL11.glTranslatef(random.nextFloat(), f100, random.nextFloat());

		Tessellator tessellator = Tessellator.instance;

		final int end = 10;
		for (int i = 0; i < end; ++i) {
			float f5 = end - i;
			float f7 = 1.0F / (f5 + 1.0F);

			if (i == 0) {
				this.bindTexture(sky);
				f7 = 0.0F;
				f5 = 65.0F;
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			}

			if (i == 1) {
				this.bindTexture(portal);
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
			}

			GL11.glTranslatef(random.nextFloat() * (1 - f7), random.nextFloat() * (1 - f7), random.nextFloat() * (1 - f7));
			float scale = 0.8F;
            GL11.glScalef(scale, scale, scale);
            float ang = 360 / end;
            GL11.glRotatef(ang * i + ang * random.nextFloat(), 0.0F, 0.0F, 1.0F);

			float f11 = (float) random.nextDouble() * 0.5F + 0.4F;
			float f12 = (float) random.nextDouble() * 0.5F + 0.4F;
			float f13 = (float) random.nextDouble() * 0.5F + 2F;
			if (i == 0) {
				f13 = 1.0F;
				f12 = 1.0F;
				f11 = 1.0F;
			}
			f13 *= f7;
			f12 *= f7;
			f11 *= f7;

			GL11.glTexGen(GL11.GL_S, GL11.GL_EYE_PLANE, this.func_147525_a(1, 0, 0, 0));
			GL11.glTexGen(GL11.GL_T, GL11.GL_EYE_PLANE, this.func_147525_a(0, 0, 1, 0));
			GL11.glTexGen(GL11.GL_R, GL11.GL_EYE_PLANE, this.func_147525_a(0, 0, 0, 1));
			GL11.glTexGen(GL11.GL_Q, GL11.GL_EYE_PLANE, this.func_147525_a(0, 1, 0, 0));

			GL11.glRotatef(180, 0, 0, 1);
			
			int tot = core.tanks[0].getMaxFill() + core.tanks[1].getMaxFill();
			int fill = core.tanks[0].getFill() + core.tanks[1].getFill();
			
			float s = 2.25F * fill / tot + 0.5F;

			int count = 32;
			
			for(int j = 0; j < count; j++) {
				
				Vec3 vec = Vec3.createVectorHelper(s, 0, 0);
				
				
				tessellator.startDrawing(GL11.GL_TRIANGLES);
				tessellator.setColorOpaque_F(f11, f12, f13);
				tessellator.setBrightness(0xF000F0);

				vec.rotateAroundY((float) Math.PI * 2F / count * j - 0.0025F);
				
				tessellator.addVertex(x + 0.5 + vec.xCoord, y + 1.0, z + 0.5 + vec.zCoord);
				
				vec.rotateAroundY((float) Math.PI * 2F / count + 0.005F);
				tessellator.addVertex(x + 0.5 + vec.xCoord, y + 1.0, z + 0.5 + vec.zCoord);
				tessellator.addVertex(x + 0.5, y + 1.0, z + 0.5);
				
				tessellator.draw();
			}

			/*GL11.glTexGen(GL11.GL_S, GL11.GL_EYE_PLANE, this.func_147525_a(0, 1, 0, 0));
			GL11.glTexGen(GL11.GL_T, GL11.GL_EYE_PLANE, this.func_147525_a(1, 0, 0, 0));
			GL11.glTexGen(GL11.GL_R, GL11.GL_EYE_PLANE, this.func_147525_a(0, 0, 0, 1));
			GL11.glTexGen(GL11.GL_Q, GL11.GL_EYE_PLANE, this.func_147525_a(0, 0, 1, 0));

			tessellator.startDrawingQuads();
			tessellator.setColorOpaque_F(f11, f12, f13);
			tessellator.setBrightness(0xF000F0);
			tessellator.addVertex(x + 0.0, y + 0.0, z + 0.0);
			tessellator.addVertex(x + 0.0, y + 1.0, z + 0.0);
			tessellator.addVertex(x + 1.0, y + 1.0, z + 0.0);
			tessellator.addVertex(x + 1.0, y + 0.0, z + 0.0);

			tessellator.addVertex(x + 1.0, y + 0.0, z + 1.0);
			tessellator.addVertex(x + 1.0, y + 1.0, z + 1.0);
			tessellator.addVertex(x + 0.0, y + 1.0, z + 1.0);
			tessellator.addVertex(x + 0.0, y + 0.0, z + 1.0);
			tessellator.draw();

			GL11.glTexGen(GL11.GL_S, GL11.GL_EYE_PLANE, this.func_147525_a(0, 1, 0, 0));
			GL11.glTexGen(GL11.GL_T, GL11.GL_EYE_PLANE, this.func_147525_a(0, 0, 1, 0));
			GL11.glTexGen(GL11.GL_R, GL11.GL_EYE_PLANE, this.func_147525_a(0, 0, 0, 1));
			GL11.glTexGen(GL11.GL_Q, GL11.GL_EYE_PLANE, this.func_147525_a(1, 0, 0, 0));

			tessellator.startDrawingQuads();
			tessellator.setColorOpaque_F(f11, f12, f13);
			tessellator.setBrightness(0xF000F0);
			tessellator.addVertex(x + 0.0, y + 0.0, z + 1.0);
			tessellator.addVertex(x + 0.0, y + 1.0, z + 1.0);
			tessellator.addVertex(x + 0.0, y + 1.0, z + 0.0);
			tessellator.addVertex(x + 0.0, y + 0.0, z + 0.0);
			tessellator.draw();

			tessellator.startDrawingQuads();
			tessellator.setColorOpaque_F(f11, f12, f13);
			tessellator.setBrightness(0xF000F0);
			tessellator.addVertex(x + 1.0, y + 0.0, z + 0.0);
			tessellator.addVertex(x + 1.0, y + 1.0, z + 0.0);
			tessellator.addVertex(x + 1.0, y + 1.0, z + 1.0);
			tessellator.addVertex(x + 1.0, y + 0.0, z + 1.0);
			tessellator.draw();*/
		}

		GL11.glPopMatrix();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_GEN_S);
		GL11.glDisable(GL11.GL_TEXTURE_GEN_T);
		GL11.glDisable(GL11.GL_TEXTURE_GEN_R);
		GL11.glDisable(GL11.GL_TEXTURE_GEN_Q);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
    }

    private FloatBuffer func_147525_a(float x, float y, float z, float w) {
    	
        this.buffer.clear();
        this.buffer.put(x).put(y).put(z).put(w);
        this.buffer.flip();
        return this.buffer;
    }

}
