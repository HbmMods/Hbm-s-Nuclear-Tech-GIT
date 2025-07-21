package com.hbm.render.item;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.obj.WavefrontObject;

public class ItemRenderNTMSteelBeamVertical implements IItemRenderer {

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		switch(type) {
		case ENTITY:
		case EQUIPPED:
		case EQUIPPED_FIRST_PERSON:
		case INVENTORY:
			return true;
		default:
			return false;
		}
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {

		GL11.glPushMatrix();

		switch(type) {
		case ENTITY:
			// When dropped as an entity in the world
			GL11.glTranslatef(0.0F, 0.0F, 0.0F);
			GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F); // Make it vertical
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			break;
		case EQUIPPED:
			// When held in third person
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
			GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F); // Make it vertical
			GL11.glRotatef(45.0F, 0.0F, 0.0F, 1.0F); // Rotate around Z for better view
			GL11.glScalef(0.8F, 0.8F, 0.8F);
			break;
		case EQUIPPED_FIRST_PERSON:
			// When held in first person
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
			GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F); // Make it vertical
			GL11.glRotatef(45.0F, 0.0F, 0.0F, 1.0F); // Rotate around Z for better view
			GL11.glScalef(0.8F, 0.8F, 0.8F);
			break;
		case INVENTORY:
			// When in inventory/GUI - vertical beam (standing upright)
			GL11.glTranslatef(0.5F, 0.4F, 0.0F);
			GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);  // Rotate around Y for angled view
			GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);  // Rotate to make it stand vertically
			GL11.glScalef(1F, 1F, 1F);
			break;
		default:
			break;
		}

		// Bind texture
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);

		// Use the vertical beam's texture
		ResourceLocation texture = new ResourceLocation("hbm", "textures/models/ntm_steel_beam.png");
		net.minecraft.client.Minecraft.getMinecraft().getTextureManager().bindTexture(texture);

		// Render the OBJ model if available (same model, just rotated)
		if(ResourceManager.ntm_steel_beam != null) {
			((WavefrontObject) ResourceManager.ntm_steel_beam).renderAll();
		}

		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
	}
}
