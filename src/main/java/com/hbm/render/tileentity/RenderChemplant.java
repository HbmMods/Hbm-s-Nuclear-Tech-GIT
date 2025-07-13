package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.main.ResourceManager;
import com.hbm.util.Clock;
import com.hbm.render.loader.HmfController;
import com.hbm.tileentity.machine.TileEntityMachineChemplant;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

@Deprecated
public class RenderChemplant extends TileEntitySpecialRenderer {

	public RenderChemplant() {
	}

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glRotatef(180, 0F, 1F, 0F);
		switch(tileEntity.getBlockMetadata() - BlockDummyable.offset) {
		case 5:
			GL11.glRotatef(180, 0F, 1F, 0F);
			break;
		case 2:
			GL11.glRotatef(270, 0F, 1F, 0F);
			break;
		case 4:
			GL11.glRotatef(0, 0F, 1F, 0F);
			break;
		case 3:
			GL11.glRotatef(90, 0F, 1F, 0F);
			break;
		}
		
		GL11.glTranslated(-0.5D, 0.0D, 0.5D);

		bindTexture(ResourceManager.chemplant_body_tex);

		GL11.glShadeModel(GL11.GL_SMOOTH);
		ResourceManager.chemplant_body.renderAll();
		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPopMatrix();

		renderExtras(tileEntity, x, y, z, f);
	}

	public void renderExtras(TileEntity tileEntity, double x, double y, double z, float f) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glRotatef(180, 0F, 1F, 0F);
		TileEntityMachineChemplant chem = (TileEntityMachineChemplant) tileEntity;
		switch(tileEntity.getBlockMetadata() - BlockDummyable.offset) {
		case 5:
			GL11.glRotatef(180, 0F, 1F, 0F);
			break;
		case 2:
			GL11.glRotatef(270, 0F, 1F, 0F);
			break;
		case 4:
			GL11.glRotatef(0, 0F, 1F, 0F);
			break;
		case 3:
			GL11.glRotatef(90, 0F, 1F, 0F);
			break;
		}
		
		GL11.glTranslated(-0.5D, 0.0D, 0.5D);
		
		bindTexture(ResourceManager.chemplant_spinner_tex);

		int rotation = (int) (Clock.get_ms() % (360 * 5)) / 5;

		GL11.glPushMatrix();
		GL11.glTranslated(-0.625, 0, 0.625);

		if(chem.tanks[0].getTankType() != Fluids.NONE && chem.isProgressing)
			GL11.glRotatef(-rotation, 0F, 1F, 0F);
		else
			GL11.glRotatef(-45, 0F, 1F, 0F);

		ResourceManager.chemplant_spinner.renderAll();
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslated(0.625, 0, 0.625);

		if(chem.tanks[1].getTankType() != Fluids.NONE && chem.isProgressing)
			GL11.glRotatef(rotation, 0F, 1F, 0F);
		else
			GL11.glRotatef(45, 0F, 1F, 0F);

		ResourceManager.chemplant_spinner.renderAll();
		GL11.glPopMatrix();

		double push = Math.sin((Clock.get_ms() % 2000) / 1000D * Math.PI) * 0.25 - 0.25;

		bindTexture(ResourceManager.chemplant_piston_tex);

		GL11.glPushMatrix();

		if(chem.isProgressing)
			GL11.glTranslated(0, push, 0);
		else
			GL11.glTranslated(0, -0.25, 0);

		ResourceManager.chemplant_piston.renderAll();
		GL11.glPopMatrix();

		bindTexture(ResourceManager.chemplant_fluid_tex);
		int color = 0;

		GL11.glDisable(GL11.GL_LIGHTING);
		if(chem.tanks[0].getTankType() != Fluids.NONE) {
			GL11.glPushMatrix();

			if(chem.isProgressing)
				HmfController.setMod(50000D, -250D);
			else
				HmfController.setMod(50000D, -50000D);

			color = chem.tanks[0].getTankType().getColor();
			GL11.glColor3ub((byte) ((color & 0xFF0000) >> 16), (byte) ((color & 0x00FF00) >> 8), (byte) ((color & 0x0000FF) >> 0));
			GL11.glTranslated(-0.625, 0, 0.625);

			int count = chem.tanks[0].getFill() * 16 / 24000;
			for(int i = 0; i < count; i++) {

				if(i < count - 1)
					ResourceManager.chemplant_fluid.renderAll();
				else
					ResourceManager.chemplant_fluidcap.renderAll();
				GL11.glTranslated(0, 0.125, 0);
			}
			GL11.glPopMatrix();
		}

		if(chem.tanks[1].getTankType() != Fluids.NONE) {
			GL11.glPushMatrix();

			if(chem.isProgressing)
				HmfController.setMod(50000D, 250D);
			else
				HmfController.setMod(50000D, 50000D);

			color = chem.tanks[1].getTankType().getColor();
			GL11.glColor3ub((byte) ((color & 0xFF0000) >> 16), (byte) ((color & 0x00FF00) >> 8), (byte) ((color & 0x0000FF) >> 0));
			GL11.glTranslated(0.625, 0, 0.625);

			int count = chem.tanks[1].getFill() * 16 / 24000;
			for(int i = 0; i < count; i++) {

				if(i < count - 1)
					ResourceManager.chemplant_fluid.renderAll();
				else
					ResourceManager.chemplant_fluidcap.renderAll();
				GL11.glTranslated(0, 0.125, 0);
			}
			GL11.glPopMatrix();
		}
		GL11.glEnable(GL11.GL_LIGHTING);

		HmfController.resetMod();

		GL11.glPopMatrix();
	}
}
