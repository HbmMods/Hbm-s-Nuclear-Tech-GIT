package com.hbm.render.entity.item;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockFallingNT;
import com.hbm.entity.item.EntityFallingBlockNT;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class RenderFallingBlockNT extends Render {

	private final RenderBlocks renderBlocks = new RenderBlocks();

	public RenderFallingBlockNT() {
		this.shadowSize = 0.5F;
	}

	public void doRender(EntityFallingBlockNT entity, double x, double y, double z, float f0, float f1) {
		
		World world = entity.getWorldForRender();
		Block block = entity.getBlockForRender();
		
		int iX = MathHelper.floor_double(entity.posX);
		int iY = MathHelper.floor_double(entity.posY);
		int iZ = MathHelper.floor_double(entity.posZ);

		GL11.glPushMatrix();
		try {
			if(block != null && block != world.getBlock(iX, iY, iZ)) {
				GL11.glTranslated(x, y, z);
				this.bindEntityTexture(entity);
				GL11.glDisable(GL11.GL_LIGHTING);
				
				this.renderBlocks.blockAccess = world;
				
				if(block instanceof BlockFallingNT && ((BlockFallingNT) block).shouldOverrideRenderer()) {
					Tessellator tessellator = Tessellator.instance;
					tessellator.startDrawingQuads();
					((BlockFallingNT) block).overrideRenderer(entity, renderBlocks, tessellator);
					tessellator.draw();
				} else {
					this.renderBlocks.setRenderBoundsFromBlock(block);
					this.renderBlocks.renderBlockSandFalling(block, world, iX, iY, iZ, entity.getDataWatcher().getWatchableObjectInt(11));
				}
	
				GL11.glEnable(GL11.GL_LIGHTING);
			}
		} catch(Exception ex) { }
		GL11.glPopMatrix();
	}

	protected ResourceLocation getEntityTexture(EntityFallingBlockNT entity) {
		return TextureMap.locationBlocksTexture;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return this.getEntityTexture((EntityFallingBlockNT) entity);
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float f0, float f1) {
		this.doRender((EntityFallingBlockNT) entity, x, y, z, f0, f1);
	}
}
