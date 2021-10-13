package com.hbm.render.entity.projectile;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.render.model.ModelRubble;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderRubble extends Render {
	
	ModelRubble mine;

	public RenderRubble() {
		mine = new ModelRubble();
	}

	@Override
	public void doRender(Entity rocket, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) p_76986_2_, (float) p_76986_4_, (float) p_76986_6_);
		GL11.glScalef(1.0F, 1.0F, 1.0F);
		GL11.glRotatef(180, 1, 0, 0);
		GL11.glRotatef(((rocket.ticksExisted + p_76986_9_) % 360) * 10, 1, 1, 1);

		try {
			int block = rocket.getDataWatcher().getWatchableObjectInt(16);
			int meta = rocket.getDataWatcher().getWatchableObjectInt(17);
			
			Block b = Block.getBlockById(block);
	
			RenderBlocks rb = RenderBlocks.getInstance();
			String s = rb.getBlockIconFromSideAndMetadata(b, 0, meta).getIconName();
			
			if(s == null || s.isEmpty())
				s = "minecraft:stone";
	
			String[] split = s.split(":");
			
			String prefix = "";
			String suffix = "";
			
			if(split.length == 2) {
				prefix = split[0];
				suffix = split[1];
			} else {
				prefix = "minecraft";
				suffix = s;
			}
			
			bindTexture(new ResourceLocation(prefix + ":textures/blocks/" + suffix + ".png"));
			
			mine.renderAll(0.0625F);
		} catch(Exception ex) { }
		
		GL11.glPopMatrix();
	}
	
	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return new ResourceLocation(RefStrings.MODID + ":textures/models/ModelRubbleScrap.png");
	}
}
