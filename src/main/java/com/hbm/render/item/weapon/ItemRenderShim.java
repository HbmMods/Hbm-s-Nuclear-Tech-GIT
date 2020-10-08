package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.main.ResourceManager;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderShim implements IItemRenderer {
	
	public ItemRenderShim() { }

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		switch(type) {
		case EQUIPPED:
		case EQUIPPED_FIRST_PERSON:
		case ENTITY:
			return true;
		default: return false;
		}
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return type == ItemRenderType.ENTITY && (helper == ItemRendererHelper.ENTITY_BOBBING || helper == ItemRendererHelper.ENTITY_ROTATION);
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		GL11.glPushMatrix();
		switch(type) {
		case EQUIPPED_FIRST_PERSON:
			if(item.getItem() == ModItems.stopsign || item.getItem() == ModItems.sopsign || item.getItem() == ModItems.chernobylsign) {
				GL11.glRotatef(180, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
				GL11.glTranslatef(-1.0F, -1.5F, 0.0F);
			}
		case EQUIPPED:
		case ENTITY:
				if(item.getItem() == ModItems.shimmer_sledge)
					Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.shimmer_sledge_tex);
				if(item.getItem() == ModItems.shimmer_axe)
					Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.shimmer_axe_tex);
				if(item.getItem() == ModItems.stopsign)
					Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.stopsign_tex);
				if(item.getItem() == ModItems.sopsign)
					Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.sopsign_tex);
				if(item.getItem() == ModItems.chernobylsign)
					Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.chernobylsign_tex);
				
				if(item.getItem() == ModItems.shimmer_sledge || item.getItem() == ModItems.shimmer_axe) {
					GL11.glRotatef(-135.0F, 0.0F, 0.0F, 1.0F);
					GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
					GL11.glScalef(1.5F, 1.5F, 1.5F);
					GL11.glTranslatef(0.45F, -0.3F, 0.0F);
				}
				
				if(item.getItem() == ModItems.stopsign || item.getItem() == ModItems.sopsign || item.getItem() == ModItems.chernobylsign) {
					GL11.glRotatef(45.0F, 0.0F, 0.0F, 1.0F);
					GL11.glScalef(0.35F, 0.35F, 0.35F);
					GL11.glTranslatef(2.0F, -2.0F, 0.0F);
					GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
				}
				
				if(item.getItem() == ModItems.shimmer_sledge)
					ResourceManager.shimmer_sledge.renderAll();
				if(item.getItem() == ModItems.shimmer_axe)
					ResourceManager.shimmer_axe.renderAll();
				if(item.getItem() == ModItems.stopsign || item.getItem() == ModItems.sopsign || item.getItem() == ModItems.chernobylsign)
					ResourceManager.stopsign.renderAll();
				
		default: break;
		}
		GL11.glPopMatrix();
	}
}
