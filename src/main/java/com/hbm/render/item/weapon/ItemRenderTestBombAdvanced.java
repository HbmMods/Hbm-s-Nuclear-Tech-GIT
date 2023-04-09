package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.tileentity.bomb.TileEntityTestBombAdvanced;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderTestBombAdvanced implements IItemRenderer {
	
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
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		//TileEntityRendererDispatcher.instance.renderTileEntityAt(new TileEntityTestBombAdvanced(), 0.0D, 0.0D, 0.0D, 0.0F);

		switch(type) {
		case INVENTORY:
			GL11.glPushMatrix();
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/NukeTestBomb.png"));
				GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
				GL11.glScalef(0.65F, 0.65F, 0.65F);
				GL11.glTranslatef(0.0F, -0.75F, 0.0F);
				//bombModel.render((Entity)data[0], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				TileEntityRendererDispatcher.instance.renderTileEntityAt(new TileEntityTestBombAdvanced(), 0.0D, 0.0D, 0.0D, 0.0F);
			GL11.glPopMatrix();
			break;
		case EQUIPPED:
			GL11.glPushMatrix();
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/NukeTestBomb.png"));
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
				GL11.glTranslatef(-1.5F, 0.0F, 0.0F);
				//bombModel.render((Entity)data[0], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				TileEntityRendererDispatcher.instance.renderTileEntityAt(new TileEntityTestBombAdvanced(), 0.0D, 0.0D, 0.0D, 0.0F);
			GL11.glPopMatrix();
			break;
		case EQUIPPED_FIRST_PERSON:
		case ENTITY:
			TileEntityRendererDispatcher.instance.renderTileEntityAt(new TileEntityTestBombAdvanced(), 0.0D, 0.0D, 0.0D, 0.0F);
		default: break;
		}
	}
}
