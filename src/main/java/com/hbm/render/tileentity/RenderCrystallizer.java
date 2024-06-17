package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.tileentity.machine.TileEntityMachineCrystallizer;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderCrystallizer extends TileEntitySpecialRenderer implements IItemRendererProvider {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float inter) {

		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);

		switch(te.getBlockMetadata() - 10) {
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		}

		TileEntityMachineCrystallizer crys = (TileEntityMachineCrystallizer) te;

		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.crystallizer_tex);
		ResourceManager.crystallizer.renderPart("Body");

		GL11.glPushMatrix();
		GL11.glRotatef(crys.prevAngle + (crys.angle - crys.prevAngle) * inter, 0, 1, 0);
		ResourceManager.crystallizer.renderPart("Spinner");
		GL11.glPopMatrix();
		
		if(crys.prevAngle != crys.angle) {
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDepthMask(false);
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			bindTexture(crys.tank.getTankType().getTexture());
			ResourceManager.crystallizer.renderPart("Fluid");
			GL11.glDepthMask(true);
			GL11.glDisable(GL11.GL_BLEND);
		}

		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.machine_crystallizer);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase() {
			public void renderNonInv() {
				GL11.glScaled(0.5, 0.5, 0.5);
			}
			public void renderInventory() {
				GL11.glTranslated(0, -4, 0);
				GL11.glScaled(2, 2, 2);
			}
			public void renderCommon() {
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.crystallizer_tex);
				ResourceManager.crystallizer.renderPart("Body");
				ResourceManager.crystallizer.renderPart("Spinner");
				GL11.glShadeModel(GL11.GL_FLAT);
			}};
	}

}
