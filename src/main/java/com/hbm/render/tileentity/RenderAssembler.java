package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.recipes.AssemblerRecipes;
import com.hbm.main.ResourceManager;
import com.hbm.render.util.RenderDecoItem;
import com.hbm.tileentity.machine.TileEntityMachineAssembler;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class RenderAssembler extends TileEntitySpecialRenderer {
	
	private RenderItem itemRenderer;
	private RenderManager renderManager = RenderManager.instance;
	
	public RenderAssembler() { }

    @Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glRotatef(180, 0F, 1F, 0F);
		switch(tileEntity.getBlockMetadata())
		{
		case 14:
			GL11.glRotatef(180, 0F, 1F, 0F);
	        GL11.glTranslated(0.5D, 0.0D, -0.5D); break;
		case 13:
			GL11.glRotatef(270, 0F, 1F, 0F);
	        GL11.glTranslated(0.5D, 0.0D, -0.5D); break;
		case 15:
			GL11.glRotatef(0, 0F, 1F, 0F);
	        GL11.glTranslated(0.5D, 0.0D, -0.5D); break;
		case 12:
			GL11.glRotatef(90, 0F, 1F, 0F);
	        GL11.glTranslated(0.5D, 0.0D, -0.5D); break;
		}

        bindTexture(ResourceManager.assembler_body_tex);
        ResourceManager.assembler_body.renderAll();
        
        TileEntityMachineAssembler assembler = (TileEntityMachineAssembler) tileEntity;

        if(assembler.recipe != -1) {
			itemRenderer = new RenderDecoItem(this);
			itemRenderer.setRenderManager(renderManager);
			GL11.glPushMatrix();
				GL11.glTranslated(-1, 0.875, 0);
	        	
	        	try {
					ItemStack stack = AssemblerRecipes.recipeList.get(assembler.recipe).toStack();
					
					RenderHelper.enableStandardItemLighting();
					GL11.glTranslated(1, 0, 1);
					if(!(stack.getItem() instanceof ItemBlock)) {
						GL11.glRotatef(-90, 1F, 0F, 0F);
					} else {
						GL11.glScaled(0.5, 0.5, 0.5);
						GL11.glTranslated(0, -0.875, -2);
					}
					
					EntityItem item = new EntityItem(null, 0.0D, 0.0D, 0.0D, stack);
					item.getEntityItem().stackSize = 1;
					item.hoverStart = 0.0F;
					
					RenderItem.renderInFrame = true;
					GL11.glTranslatef(0.0F, 1.0F - 0.0625F * 165/100, 0.0F);
					this.itemRenderer.doRender(item, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
					RenderItem.renderInFrame = false;
					
	        	} catch(Exception ex) { }
				
			GL11.glPopMatrix();
        }
		
    	GL11.glPopMatrix();
		RenderHelper.enableStandardItemLighting();
        
        renderSlider(tileEntity, x, y, z, f);
    }
    
	public void renderSlider(TileEntity tileEntity, double x, double y, double z, float f)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glRotatef(180, 0F, 1F, 0F);
		switch(tileEntity.getBlockMetadata())
		{
		case 14:
			GL11.glTranslated(-1, 0, 0);
			GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 13:
			GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 15:
			GL11.glTranslated(0, 0, -1);
			GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 12:
			GL11.glTranslated(-1, 0, -1);
			GL11.glRotatef(90, 0F, 1F, 0F); break;
		}
		

        bindTexture(ResourceManager.assembler_slider_tex);
        
        int offset = (int) (System.currentTimeMillis() % 5000) / 5;
        
        if(offset > 500)
        	offset = 500 - (offset - 500);
        
        TileEntityMachineAssembler assembler = (TileEntityMachineAssembler) tileEntity;
        
        if(assembler.isProgressing)
        	GL11.glTranslated(offset * 0.003 - 0.75, 0, 0);
		
        ResourceManager.assembler_slider.renderAll();

        bindTexture(ResourceManager.assembler_arm_tex);
        
        double sway = (System.currentTimeMillis() % 2000) / 2;

        sway = Math.sin(sway / Math.PI / 50);

        if(assembler.isProgressing)
        	GL11.glTranslated(0, 0, sway * 0.3);
        ResourceManager.assembler_arm.renderAll();

        GL11.glPopMatrix();
        
        renderCogs(tileEntity, x, y, z, f);
    }
	
	public void renderCogs(TileEntity tileEntity, double x, double y, double z, float f) {
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glRotatef(180, 0F, 1F, 0F);
		switch(tileEntity.getBlockMetadata())
		{
		case 14:
			GL11.glTranslated(-1, 0, 0);
			GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 13:
			GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 15:
			GL11.glTranslated(0, 0, -1);
			GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 12:
			GL11.glTranslated(-1, 0, -1);
			GL11.glRotatef(90, 0F, 1F, 0F); break;
		}
		

        bindTexture(ResourceManager.assembler_cog_tex);

        int rotation = (int) (System.currentTimeMillis() % (360 * 5)) / 5;
        
        TileEntityMachineAssembler assembler = (TileEntityMachineAssembler) tileEntity;

        if(!assembler.isProgressing)
        	rotation = 0;
        
        GL11.glPushMatrix();
		GL11.glTranslated(-0.6, 0.75, 1.0625);
		GL11.glRotatef(-rotation, 0F, 0F, 1F);
		ResourceManager.assembler_cog.renderAll();
        GL11.glPopMatrix();
        
        GL11.glPushMatrix();
		GL11.glTranslated(0.6, 0.75, 1.0625);
		GL11.glRotatef(rotation, 0F, 0F, 1F);
		ResourceManager.assembler_cog.renderAll();
        GL11.glPopMatrix();
        
        GL11.glPushMatrix();
		GL11.glTranslated(-0.6, 0.75, -1.0625);
		GL11.glRotatef(-rotation, 0F, 0F, 1F);
		ResourceManager.assembler_cog.renderAll();
        GL11.glPopMatrix();
        
        GL11.glPushMatrix();
		GL11.glTranslated(0.6, 0.75, -1.0625);
		GL11.glRotatef(rotation, 0F, 0F, 1F);
		ResourceManager.assembler_cog.renderAll();
        GL11.glPopMatrix();

        GL11.glPopMatrix();
	}
}
