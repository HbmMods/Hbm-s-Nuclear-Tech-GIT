package com.hbm.render.util;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

/**
 * For small items as part of a TESR, e.g. items in a press
 * @author hbm
 */
public class RenderDecoItem extends RenderItem {

	public RenderDecoItem(TileEntitySpecialRenderer render) {
		this.setRenderManager(RenderManager.instance);
	}

	@Override
	public byte getMiniBlockCount(ItemStack stack, byte original) {
		return 1;
	}

	@Override
	public byte getMiniItemCount(ItemStack stack, byte original) {
		return 1;
	}

	@Override
	public boolean shouldBob() {
		return false;
	}

	@Override
	public boolean shouldSpreadItems() {
		return false;
	}


	/**
	 * Fixes z-fighting issues with item glints when using GUI (flat) rendering in world, from StorageDrawers 1.7.10
	 */

	private static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
	private RenderBlocks renderBlocksRi = new RenderBlocks();

	@Override
	public void renderItemIntoGUI(FontRenderer fontRenderer, TextureManager texManager, ItemStack itemStack, int x, int y, boolean renderEffect) {
		if(itemStack.getItemSpriteNumber() == 0 && RenderBlocks.renderItemIn3d(Block.getBlockFromItem(itemStack.getItem()).getRenderType())) {
			renderItemIntoGUIBlock(fontRenderer, texManager, itemStack, x, y, renderEffect);
			return;
		}

		Item item = itemStack.getItem();
		int meta = itemStack.getItemDamage();

		ResourceLocation loc = itemStack.getItem().requiresMultipleRenderPasses()
			? (item.getSpriteNumber() == 0 ? TextureMap.locationBlocksTexture : TextureMap.locationItemsTexture)
			: (texManager.getResourceLocation(itemStack.getItemSpriteNumber()));

		for(int i = 0; i < item.getRenderPasses(meta); ++i) {
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			texManager.bindTexture(loc);

			IIcon icon = itemStack.getItem().requiresMultipleRenderPasses()
				? item.getIcon(itemStack, i)
				: itemStack.getIconIndex();

			if(icon == null) continue;

			int color = itemStack.getItem().getColorFromItemStack(itemStack, i);
			float r = (float)(color >> 16 & 255) / 255.0F;
			float g = (float)(color >> 8 & 255) / 255.0F;
			float b = (float)(color & 255) / 255.0F;

			if(renderWithColor)
				GL11.glColor4f(r, g, b, 1.0F);

			GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
			GL11.glPolygonOffset(-1f, -1);

			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_ALPHA_TEST);

			renderIcon(x, y, icon, 16, 16);

			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_LIGHTING);

			if(renderEffect && itemStack.hasEffect(i))
				renderEffect(texManager, x, y);

			GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
		}
	}

	@Override
	public void renderEffect(TextureManager manager, int x, int y) {
		GL11.glDepthFunc(GL11.GL_EQUAL);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDepthMask(false);
		manager.bindTexture(RES_ITEM_GLINT);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(0.5F, 0.25F, 0.8F, 1.0F);
		renderGlint(x, y, 16, 16);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
	}

	private void renderGlint(int x, int y, int w, int h) {
		for(int i = 0; i < 2; ++i) {
			OpenGlHelper.glBlendFunc(772, 1, 0, 0);
			float uScale = 0.00390625F;
			float vScale = 0.00390625F;
			float u = (Minecraft.getSystemTime() % (3000 + i * 1873)) / (3000.0F + i * 1873) * 256.0F;
			float v = 0.0F;

			float hScale = (i < 1) ? 4.0F : -1.0F;

			Tessellator tessellator = Tessellator.instance;
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(x + 0, y + h, 0, (u + (float)h * hScale) * uScale, (v + (float)h) * vScale);
			tessellator.addVertexWithUV(x + w, y + h, 0, (u + (float)w + (float)h * hScale) * uScale, (v + (float)h) * vScale);
			tessellator.addVertexWithUV(x + w, y + 0, 0, (u + (float)w) * uScale, (v + 0.0F) * vScale);
			tessellator.addVertexWithUV(x + 0, y + 0, 0, (u + 0.0F) * uScale, (v + 0.0F) * vScale);
			tessellator.draw();
		}
	}

	private void renderItemIntoGUIBlock(FontRenderer fontRenderer, TextureManager texManager, ItemStack itemStack, int x, int y, boolean renderEffect) {
		texManager.bindTexture(TextureMap.locationBlocksTexture);
		Block block = Block.getBlockFromItem(itemStack.getItem());

		if(block.getRenderBlockPass() != 0) {
			GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
			GL11.glEnable(GL11.GL_BLEND);
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		} else {
			GL11.glAlphaFunc(GL11.GL_GREATER, 0.5F);
			GL11.glDisable(GL11.GL_BLEND);
		}

		GL11.glPushMatrix();
		GL11.glTranslatef(x - 2, y + 3, zLevel - 3);
		GL11.glScalef(10, 10, 10);
		GL11.glTranslatef(1, 0.5f, 1);
		GL11.glScalef(1, 1, -1);
		GL11.glRotatef(210, 1, 0, 0);
		GL11.glRotatef(45, 0, 1, 0);

		int color = itemStack.getItem().getColorFromItemStack(itemStack, 0);
		float r = (float)(color >> 16 & 255) / 255.0F;
		float g = (float)(color >> 8 & 255) / 255.0F;
		float b = (float)(color & 255) / 255.0F;

		if(this.renderWithColor)
			GL11.glColor4f(r * 1, g * 1, b * 1, 1.0F);

		GL11.glRotatef(-90, 0, 1, 0);
		GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
		GL11.glPolygonOffset(-1f, -1f);

		this.renderBlocksRi.useInventoryTint = this.renderWithColor;
		this.renderBlocksRi.renderBlockAsItem(block, itemStack.getItemDamage(), 1);
		this.renderBlocksRi.useInventoryTint = true;

		GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);

		if(block.getRenderBlockPass() == 0)
			GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);

		GL11.glPopMatrix();
	}

}
