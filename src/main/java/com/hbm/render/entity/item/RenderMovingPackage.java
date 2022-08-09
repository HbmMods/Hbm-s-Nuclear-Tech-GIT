package com.hbm.render.entity.item;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RenderMovingPackage extends Render {
	
	private ItemStack dummy;

	@Override
	public void doRender(Entity entity, double x, double y, double z, float f1, float f2) {

		GL11.glPushMatrix();
		GL11.glTranslated(x, y - 0.0125, z);

		if(this.dummy == null) {
			this.dummy = new ItemStack(ModBlocks.crate);
		}

		EntityItem dummy = new EntityItem(entity.worldObj, 0, 0, 0, this.dummy);
		dummy.hoverStart = 0.0F;

		RenderItem.renderInFrame = true;
		double scale = 2;
		GL11.glScaled(scale, scale, scale);
		RenderManager.instance.renderEntityWithPosYaw(dummy, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
		RenderItem.renderInFrame = false;

		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return null;
	}
}
