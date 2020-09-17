package com.hbm.render.item.block;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.render.model.ModelBroadcaster;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class ItemRendererMachine implements IItemRenderer {
	
	///// THIS IS A TEST CLASS. CARVE THIS INTO A BASE CLASS FOR LESS CRAPPY BLOCK ITEM RENDERERS IN THE FUTURE ////
	double scale = 1.0D;
	private ModelBroadcaster broadcaster;
	private static final ResourceLocation broadcasterTex = new ResourceLocation(RefStrings.MODID + ":" + "textures/models/ModelRadioReceiver.png");
	
	public ItemRendererMachine(double scale) {
		this.scale = scale;
		this.broadcaster = new ModelBroadcaster();
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		switch(type) {
		case EQUIPPED:
		case EQUIPPED_FIRST_PERSON:
		case ENTITY:
		case INVENTORY:
			return true;
		default: return false;
		}
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		
		return type == ItemRenderType.ENTITY && (helper == ItemRendererHelper.ENTITY_ROTATION || helper == ItemRendererHelper.ENTITY_BOBBING);
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		
		GL11.glPushMatrix();
		
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		
		switch(type) {
		
		case EQUIPPED_FIRST_PERSON:
			
			GL11.glScaled(0.5, 0.5, 0.5);
			GL11.glTranslated(1, 0.5, 0);
			GL11.glRotated(180, 0, 1, 0);
			
			break;
			
		case EQUIPPED:

			GL11.glTranslated(0.5, 0, 0);
			GL11.glRotated(90, 0, 1, 0);
			double scaleEq = 0.5;
			GL11.glScaled(scaleEq, scaleEq, scaleEq);
			
			break;
			
		case ENTITY:

			GL11.glScaled(0.5, 0.5, 0.5);
			
			break;
			
		case INVENTORY:
			
			GL11.glRotated(180, 1, 0, 0);
			int scale = 8;
			GL11.glTranslated(8, -16, 0);
			GL11.glScaled(scale, scale, scale);

			//GL11.glRotated(25, 1, 0, 0);
			//GL11.glRotated(45, 0, 1, 0);
			
			break;
			
		default: break;
			
		}
		
		if(item.getItem() == Item.getItemFromBlock(ModBlocks.machine_selenium)) {

            GL11.glDisable(GL11.GL_CULL_FACE);
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.selenium_body_tex);
	        ResourceManager.selenium_body.renderAll();
	        
	        GL11.glTranslated(0.0D, 1.0D, 0.0D);
	        
	        Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.selenium_piston_tex);
	        for(int i = 0; i < 7; i++) {
	            ResourceManager.selenium_piston.renderAll();
	    		GL11.glRotatef(360F/7F, 0, 0, 1);
	        }
	        
            Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.selenium_rotor_tex);
            ResourceManager.selenium_rotor.renderAll();
            GL11.glEnable(GL11.GL_CULL_FACE);
		}
		
		if(item.getItem() == Item.getItemFromBlock(ModBlocks.radiorec)) {
			GL11.glTranslated(0, 1.5, 0);
			GL11.glRotated(180, 1, 0, 0);
            Minecraft.getMinecraft().renderEngine.bindTexture(broadcasterTex);
			broadcaster.renderModel(0.0625F);
		}
		
		GL11.glPopMatrix();
	}

}
