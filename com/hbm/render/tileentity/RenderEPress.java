package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.misc.RenderDecoItem;
import com.hbm.tileentity.machine.TileEntityMachineEPress;

import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class RenderEPress extends TileEntitySpecialRenderer {
	
	private RenderItem itemRenderer;
	private RenderManager renderManager = RenderManager.instance;
	
	public RenderEPress() { }
	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {
		GL11.glPushMatrix();
			GL11.glTranslated(x + 0.5D, y, z + 0.5D);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glRotatef(180, 0F, 1F, 0F);
			
			switch(tileentity.getBlockMetadata()) {
			case 2:
				GL11.glRotatef(270, 0F, 1F, 0F); break;
			case 4:
				GL11.glRotatef(0, 0F, 1F, 0F); break;
			case 3:
				GL11.glRotatef(90, 0F, 1F, 0F); break;
			case 5:
				GL11.glRotatef(180, 0F, 1F, 0F); break;
			}
			
			this.bindTexture(ResourceManager.epress_body_tex);
			
			ResourceManager.epress_body.renderAll();
				
		GL11.glPopMatrix();
		
        renderTileEntityAt2(tileentity, x, y, z, f);
	}
    
	public void renderTileEntityAt2(TileEntity tileentity, double x, double y, double z, float f) {
		GL11.glPushMatrix();
			GL11.glTranslated(x + 0.5D, y + 1 + 1 - 0.125, z + 0.5D);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glRotatef(180, 0F, 1F, 0F);
			
			switch(tileentity.getBlockMetadata()) {
			case 2:
				GL11.glRotatef(270, 0F, 1F, 0F); break;
			case 4:
				GL11.glRotatef(0, 0F, 1F, 0F); break;
			case 3:
				GL11.glRotatef(90, 0F, 1F, 0F); break;
			case 5:
				GL11.glRotatef(180, 0F, 1F, 0F); break;
			}

			TileEntityMachineEPress press = (TileEntityMachineEPress)tileentity;
			float f1 = press.progress * (1 - 0.125F) / press.maxProgress;
			GL11.glTranslated(0, -f1, 0);
		
			this.bindTexture(ResourceManager.epress_head_tex);
		
			ResourceManager.epress_head.renderAll();
			
		GL11.glPopMatrix();
		
        renderTileEntityAt3(tileentity, x, y, z, f);
    }
    
	public void renderTileEntityAt3(TileEntity tileentity, double x, double y, double z, float f) {
		itemRenderer = new RenderDecoItem(this);
		itemRenderer.setRenderManager(renderManager);
		GL11.glPushMatrix();
			GL11.glTranslated(x + 0.5D, y + 1, z + 0.5);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glRotatef(180, 0F, 1F, 0F);
			
			switch(tileentity.getBlockMetadata()) {
			case 2:
				GL11.glRotatef(270, 0F, 1F, 0F); break;
			case 4:
				GL11.glRotatef(0, 0F, 1F, 0F); break;
			case 3:
				GL11.glRotatef(90, 0F, 1F, 0F); break;
			case 5:
				GL11.glRotatef(180, 0F, 1F, 0F); break;
			}

			GL11.glRotatef(90, 0F, 1F, 0F);
			GL11.glRotatef(-90, 1F, 0F, 0F);
			GL11.glTranslatef(1.0F, 1.0F - 0.0625F * 165/100, 0.0F);
			GL11.glTranslatef(-1, -1.15F, 0);
			
			TileEntityMachineEPress press = (TileEntityMachineEPress)tileentity;
			ItemStack stack = new ItemStack(Item.getItemById(press.item), 1, press.meta);
			
			if(!(stack.getItem() instanceof ItemBlock)) {
				EntityItem item = new EntityItem(null, 0.0D, 0.0D, 0.0D, stack);
			
				RenderItem.renderInFrame = true;
				this.itemRenderer.doRender(item, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
				RenderItem.renderInFrame = false;
			}
			
		GL11.glPopMatrix();
    }
}
