package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.tileentity.machine.TileEntityMachineTurbofan;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderTurbofan extends TileEntitySpecialRenderer implements IItemRendererProvider {

	public RenderTurbofan() {
	}

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		switch(tileEntity.getBlockMetadata() - 10) {
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		}
		
		TileEntityMachineTurbofan turbo = (TileEntityMachineTurbofan) tileEntity;
		
		float spin = turbo.lastSpin + (turbo.spin - turbo.lastSpin) * interp; 

		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.turbofan_tex);
		ResourceManager.turbofan.renderPart("Body");
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 1.5, 0);
		GL11.glRotated(spin, 0, 0, -1);
		GL11.glTranslated(0, -1.5, 0);
		ResourceManager.turbofan.renderPart("Blades");
		GL11.glPopMatrix();
		
		if(turbo.afterburner == 0)
			bindTexture(ResourceManager.turbofan_back_tex);
		else
			bindTexture(ResourceManager.turbofan_afterburner_tex);
		
		ResourceManager.turbofan.renderPart("Afterburner");
		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.machine_turbofan);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase() {
			public void renderInventory() {
				GL11.glRotated(90, 0, 1, 0);
				GL11.glScaled(2.25, 2.25, 2.25);
			}
			public void renderCommon() {
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.turbofan_tex);
				ResourceManager.turbofan.renderPart("Body");
				ResourceManager.turbofan.renderPart("Blades");
				bindTexture(ResourceManager.turbofan_back_tex);
				ResourceManager.turbofan.renderPart("Afterburner");
				GL11.glShadeModel(GL11.GL_FLAT);
			}};
	}
}
