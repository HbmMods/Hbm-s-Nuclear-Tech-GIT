package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.oil.TileEntityMachinePumpjack;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;

public class RenderPumpjack extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
		
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		GL11.glEnable(GL11.GL_LIGHTING);
		
		switch(tileEntity.getBlockMetadata() - BlockDummyable.offset) {
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		}
		
		TileEntityMachinePumpjack pj = (TileEntityMachinePumpjack) tileEntity;
		
		float rotation = pj.prevRot + (pj.rot - pj.prevRot) * f;

		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		bindTexture(ResourceManager.pumpjack_tex);
		ResourceManager.pumpjack.renderPart("Base");

		GL11.glPushMatrix();
		GL11.glTranslated(0, 1.5, -5.5);
		GL11.glRotatef(rotation - 90, 1, 0, 0);
		GL11.glTranslated(0, -1.5, 5.5);
		ResourceManager.pumpjack.renderPart("Rotor");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslated(0, 3.5, -3.5);
		GL11.glRotated(Math.toDegrees(Math.sin(Math.toRadians(rotation))) * 0.25, 1, 0, 0);
		GL11.glTranslated(0, -3.5, 3.5);
		ResourceManager.pumpjack.renderPart("Head");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslated(0, -Math.sin(Math.toRadians(rotation)), 0);
		ResourceManager.pumpjack.renderPart("Carriage");
		GL11.glPopMatrix();
		
		Vec3 backPos = Vec3.createVectorHelper(0, 0, -2);
		backPos.rotateAroundX(-(float)Math.sin(Math.toRadians(rotation)) * 0.25F);
		
		Vec3 rot = Vec3.createVectorHelper(0, 0.5, 0);
		rot.rotateAroundX(-(float)Math.toRadians(rotation - 90));

		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		tess.setColorRGBA_F(0.5F, 0.5F, 0.5F, 1F);
		
		for(int i = -1; i <= 1; i += 2) {

			tess.addVertex(0.53125 * i, 1.5 + rot.yCoord, -5.5 + rot.zCoord - 0.0625D);
			tess.addVertex(0.53125 * i, 1.5 + rot.yCoord, -5.5 + rot.zCoord + 0.0625D);

			tess.addVertex(0.53125 * i, 3.5 + backPos.yCoord, -3.5 + backPos.zCoord + 0.0625D);
			tess.addVertex(0.53125 * i, 3.5 + backPos.yCoord, -3.5 + backPos.zCoord - 0.0625D);
		}
		
		tess.setColorRGBA_F(0.2F, 0.2F, 0.2F, 1F);
		
		double pd = 0.03125D;
		double width = 0.25D;

		double height = -Math.sin(Math.toRadians(rotation));
		
		for(int i = -1; i <= 1; i += 2) {

			float pRot = -(float)(Math.sin(Math.toRadians(rotation)) * 0.25);
			
			Vec3 frontPos = Vec3.createVectorHelper(0, 0, 1);
			frontPos.rotateAroundX(pRot);

			double dist = 0.03125D;
			Vec3 frontRad = Vec3.createVectorHelper(0, 0, 2.5 + dist);
			double cutlet = 360D / 32D;
			frontRad.rotateAroundX(pRot);
			frontRad.rotateAroundX(-(float)Math.toRadians(cutlet * -3));
			
			for(int j = 0; j < 4; j++) {

				double sumY = frontPos.yCoord + frontRad.yCoord;
				double sumZ = frontPos.zCoord + frontRad.zCoord;
				if(frontRad.yCoord < 0) sumZ = 3.5 + dist * 0.5;
	
				tess.addVertex((width - pd) * i, 3.5 + sumY, -3.5 + sumZ);
				tess.addVertex((width + pd) * i, 3.5 + sumY, -3.5 + sumZ);

				frontRad.rotateAroundX(-(float)Math.toRadians(cutlet));

				sumY = frontPos.yCoord + frontRad.yCoord;
				sumZ = frontPos.zCoord + frontRad.zCoord;
				if(frontRad.yCoord < 0) sumZ = 3.5 + dist * 0.5;
	
				tess.addVertex((width + pd) * i, 3.5 + sumY, -3.5 + sumZ);
				tess.addVertex((width - pd) * i, 3.5 + sumY, -3.5 + sumZ);
			}

			double sumY = frontPos.yCoord + frontRad.yCoord;
			double sumZ = frontPos.zCoord + frontRad.zCoord;
			if(frontRad.yCoord < 0) sumZ = 3.5 + dist * 0.5;
			
			tess.addVertex((width + pd) * i, 3.5 + sumY, -3.5 + sumZ);
			tess.addVertex((width - pd) * i, 3.5 + sumY, -3.5 + sumZ);
			
			tess.addVertex((width - pd) * i, 2 + height, 0);
			tess.addVertex((width + pd) * i, 2 + height, 0);
		}
		
		double p = 0.03125D;
		tess.addVertex(p, height + 1.5, p);
		tess.addVertex(-p, height + 1.5, -p);
		tess.addVertex(-p, 0.75, -p);
		tess.addVertex(p, 0.75,  p);
		tess.addVertex(-p, height + 1.5, p);
		tess.addVertex(p, height + 1.5, -p);
		tess.addVertex(p, 0.75, -p);
		tess.addVertex(-p, 0.75, p);
		
		tess.draw();
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}
}
