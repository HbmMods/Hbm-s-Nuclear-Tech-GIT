package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.tileentity.machine.TileEntityMachineHephaestus;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderHephaestus extends TileEntitySpecialRenderer implements IItemRendererProvider {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);

		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.hephaestus_tex);
		ResourceManager.hephaestus.renderPart("Main");
		
		TileEntityMachineHephaestus geo = (TileEntityMachineHephaestus) tile;
		float movement = geo.prevRot + (geo.rot - geo.prevRot) * interp;
		boolean isOn = geo.bufferedHeat > 0;
		GL11.glPushMatrix();
		GL11.glRotatef(movement, 0, 1, 0);
		
		for(int i = 0; i < 3; i++) {
			ResourceManager.hephaestus.renderPart("Rotor");
			GL11.glRotated(120, 0, 1, 0);
		}
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
		

		GL11.glPushMatrix();
		
		if(isOn) {
			bindTexture(RenderCrucible.lava);
			GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
			GL11.glDisable(GL11.GL_LIGHTING);
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
		} else {
			bindTexture(RenderExcavator.cobble);
			GL11.glColor3f(0.5F, 0.5F, 0.5F);
		}
		
		GL11.glMatrixMode(GL11.GL_TEXTURE);
		GL11.glLoadIdentity();
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		GL11.glTranslatef(0, movement / 10F, 0);
		ResourceManager.hephaestus.renderPart("Core");
		GL11.glMatrixMode(GL11.GL_TEXTURE);
		GL11.glLoadIdentity();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		if(isOn) {
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glPopAttrib();
		} else {
			GL11.glColor3f(1F, 1F, 1F);
		}
		GL11.glPopMatrix();
		
		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.machine_hephaestus);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -4.5, 0);
				GL11.glScaled(2.25, 2.25, 2.25);
			}
			public void renderCommon() {
				GL11.glScaled(0.5, 0.5, 0.5);
				GL11.glDisable(GL11.GL_CULL_FACE);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.hephaestus_tex);
				ResourceManager.hephaestus.renderPart("Main");
				
				GL11.glPushMatrix();
				
				for(int i = 0; i < 3; i++) {
					ResourceManager.hephaestus.renderPart("Rotor");
					GL11.glRotated(120, 0, 1, 0);
				}
				GL11.glEnable(GL11.GL_CULL_FACE);
				GL11.glShadeModel(GL11.GL_FLAT);
				GL11.glPopMatrix();
				bindTexture(RenderExcavator.cobble);
				ResourceManager.hephaestus.renderPart("Core");
			}};
	}
}
