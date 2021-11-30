package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.render.loader.HFRWavefrontObject;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.model.IModelCustom;

public class RenderDemonLamp extends TileEntitySpecialRenderer {
	
	public static final IModelCustom demon_lamp = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/blocks/demon_lamp.obj"));
	public static final ResourceLocation tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/demon_lamp.png");

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {

		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y + 0.5D, z + 0.5D);
		
		switch(tileEntity.getBlockMetadata()) {
		case 0: GL11.glRotated(180, 1, 0, 0); break;
		case 1: break;
		case 2: GL11.glRotated(90, 1, 0, 0); GL11.glRotated(180, 0, 0, 1); break;
		case 3: GL11.glRotated(90, 1, 0, 0); break;
		case 4: GL11.glRotated(90, 1, 0, 0); GL11.glRotated(90, 0, 0, 1); break;
		case 5: GL11.glRotated(90, 1, 0, 0); GL11.glRotated(270, 0, 0, 1); break;
		}

		GL11.glTranslated(0, -0.5F, 0);
		
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);

		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(tex);
		demon_lamp.renderAll();
		
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		Vec3 vec = Vec3.createVectorHelper(1, 0, 0);

		GL11.glDepthMask(false);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.0F);

		double near = 0.375D;
		double far = 15D;
		//whereeeeeeever you are
		
		for(int j = 0; j < 2; j++) {
			
			double h = 0.5;
			double height = j == 0 ? -h : h;
			
			for(int i = 0; i < 16; i++) {
				
				tess.setColorRGBA_F(0F, 0.75F, 1F, 0.25F);
				tess.addVertex(vec.xCoord * near, 0.5D + j * 0.125D, vec.zCoord * near);
				tess.setColorRGBA_F(0F, 0.75F, 1F, 0F);
				tess.addVertex(vec.xCoord * far, 0.5D + j * 0.125D + height, vec.zCoord * far);
				
				vec.rotateAroundY((float)(Math.PI * 2D / 16D));
	
				tess.addVertex(vec.xCoord * far, 0.5D + j * 0.125D + height, vec.zCoord * far);
				tess.setColorRGBA_F(0F, 0.75F, 1F, 0.25F);
				tess.addVertex(vec.xCoord * near, 0.5D + j * 0.125D, vec.zCoord * near);
			}
		}
		tess.draw();

		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glDepthMask(true);

		GL11.glPopMatrix();
	}
}