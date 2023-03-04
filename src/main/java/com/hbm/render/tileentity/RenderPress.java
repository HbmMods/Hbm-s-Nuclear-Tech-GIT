package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.util.RenderDecoItem;
import com.hbm.tileentity.machine.TileEntityMachinePress;

import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;

public class RenderPress extends TileEntitySpecialRenderer {
	
	private RenderItem itemRenderer;
	private RenderManager renderManager = RenderManager.instance;
	
	public RenderPress() { }
	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {
		GL11.glPushMatrix();
			GL11.glTranslated(x + 0.5D, y, z + 0.5D);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glRotatef(180, 0F, 1F, 0F);
			
			this.bindTexture(ResourceManager.press_body_tex);
			
			ResourceManager.press_body.renderAll();
				
		GL11.glPopMatrix();
		
		renderTileEntityAt2(tileentity, x, y, z, f);
	}

	public void renderTileEntityAt2(TileEntity tileEntity, double x, double y, double z, float f) {
		GL11.glPushMatrix();
			GL11.glTranslated(x + 0.5D, y, z + 0.5D);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glScalef(0.99F, 1, 0.99F);

			TileEntityMachinePress press = (TileEntityMachinePress)tileEntity;
			double p = (press.lastPress + (press.renderPress - press.lastPress) * f) /(double) press.maxPress;
			GL11.glTranslated(0, MathHelper.clamp_double((1D - p), 0D, 1D) * 0.875D, 0);
		
			this.bindTexture(ResourceManager.press_head_tex);
		
			ResourceManager.press_head.renderAll();
			
		GL11.glPopMatrix();
		
		renderTileEntityAt3(tileEntity, x, y, z, f);
	}

	public void renderTileEntityAt3(TileEntity tileEntity, double x, double y, double z, float f) {
		itemRenderer = new RenderDecoItem(this);
		itemRenderer.setRenderManager(renderManager);
		GL11.glPushMatrix();
			GL11.glTranslated(x + 0.5D, y + 1, z - 0.5);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glRotatef(180, 0F, 1F, 0F);
			GL11.glRotatef(-90, 1F, 0F, 0F);
			
			TileEntityMachinePress press = (TileEntityMachinePress)tileEntity;
			if(press.syncStack != null) {
				ItemStack stack = press.syncStack.copy();
				
				EntityItem item = new EntityItem(null, 0.0D, 0.0D, 0.0D, stack);
				item.getEntityItem().stackSize = 1;
				item.hoverStart = 0.0F;
				
				RenderItem.renderInFrame = true;
				GL11.glTranslatef(0.0F, 1.0F - 0.0625F * 165/100, 0.0F);
				this.itemRenderer.doRender(item, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
				RenderItem.renderInFrame = false;
			}
			
		GL11.glPopMatrix();
    }
}