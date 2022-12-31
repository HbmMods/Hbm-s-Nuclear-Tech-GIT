package com.hbm.render.tileentity;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.IRenderFoundry;
import com.hbm.wiaj.WorldInAJar;
import com.hbm.wiaj.actors.ITileActorRenderer;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;

public class RenderFoundry extends TileEntitySpecialRenderer implements ITileActorRenderer {
	
	public static final ResourceLocation lava = new ResourceLocation(RefStrings.MODID, "textures/models/machines/lava_gray.png");
	
	private void drawItem(ItemStack stack, double height) {
		GL11.glPushMatrix();
		RenderItem render = new RenderItem();
		GL11.glTranslated(0.125D, height, 0.125D);
		
		double scale = 0.0625D * 12D / 16D;
		GL11.glScaled(scale, scale, scale);
		
		GL11.glRotated(90, 1, 0, 0);
		RenderHelper.enableGUIStandardItemLighting();
		if(!ForgeHooksClient.renderInventoryItem(RenderBlocks.getInstance(), Minecraft.getMinecraft().getTextureManager(), stack, true, 0.0F, 1.0F, 0.0F)) {
			render.renderItemIntoGUI(Minecraft.getMinecraft().fontRenderer, Minecraft.getMinecraft().getTextureManager(), stack, 0, 0);
		}
		GL11.glPopMatrix();
		RenderHelper.enableStandardItemLighting();
		GL11.glEnable(GL11.GL_ALPHA_TEST);
	}
	
	private void drawBlock(ItemStack stack, IRenderFoundry foundry) {
		Tessellator tess = Tessellator.instance;
		Block b = ((ItemBlock)stack.getItem()).field_150939_a;
		IIcon icon = b.getIcon(1, stack.getItemDamage());
		ITileActorRenderer.bindTexture(TextureMap.locationBlocksTexture);
		
		tess.startDrawingQuads();
		tess.setNormal(0F, 1F, 0F);
		tess.setColorRGBA_F(1F, 1F, 1F, 0.3F);
		double h = foundry.outHeight();
		tess.addVertexWithUV(foundry.minX(), h, foundry.minZ(), icon.getMinU(), icon.getMaxV());
		tess.addVertexWithUV(foundry.minX(), h, foundry.maxZ(), icon.getMaxU(), icon.getMaxV());
		tess.addVertexWithUV(foundry.maxX(), h, foundry.maxZ(), icon.getMaxU(), icon.getMinV());
		tess.addVertexWithUV(foundry.maxX(), h, foundry.minZ(), icon.getMinU(), icon.getMinV());
		tess.draw();
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		IRenderFoundry foundry = (IRenderFoundry) tile;
		Tessellator tess = Tessellator.instance;
		
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		
		if(foundry instanceof IInventory) {
			IInventory inv = (IInventory) foundry;
			ItemStack mold = inv.getStackInSlot(0);
			
			if(mold != null) {
				drawItem(mold, foundry.moldHeight());
			}
			ItemStack out = inv.getStackInSlot(1);
			
			if(out != null) {
				if(out.getItem() instanceof ItemBlock) {
					drawBlock(out, foundry);
				} else {
					drawItem(out, foundry.outHeight());
				}
			}
		}
		
		if(foundry.shouldRender()) {
			
			ITileActorRenderer.bindTexture(lava);
			
			int hex = foundry.getMat().moltenColor;
			Color color = new Color(hex);
	
			GL11.glPushMatrix();
			GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
	
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_CULL_FACE);
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
	
			tess.startDrawingQuads();
			tess.setNormal(0F, 1F, 0F);
			tess.setColorRGBA_F(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, 1F);
			tess.addVertexWithUV(foundry.minX(), foundry.getLevel(), foundry.minZ(), foundry.minZ(), foundry.maxX());
			tess.addVertexWithUV(foundry.minX(), foundry.getLevel(), foundry.maxZ(), foundry.maxZ(), foundry.maxX());
			tess.addVertexWithUV(foundry.maxX(), foundry.getLevel(), foundry.maxZ(), foundry.maxZ(), foundry.minX());
			tess.addVertexWithUV(foundry.maxX(), foundry.getLevel(), foundry.minZ(), foundry.minZ(), foundry.minX());
			tess.draw();
			
			GL11.glDepthMask(false);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
			tess.startDrawingQuads();
			tess.setNormal(0F, 1F, 0F);
			tess.setColorRGBA_F(1F, 1F, 1F, 0.3F);
			tess.addVertexWithUV(foundry.minX(), foundry.getLevel(), foundry.minZ(), foundry.minZ(), foundry.maxX());
			tess.addVertexWithUV(foundry.minX(), foundry.getLevel(), foundry.maxZ(), foundry.maxZ(), foundry.maxX());
			tess.addVertexWithUV(foundry.maxX(), foundry.getLevel(), foundry.maxZ(), foundry.maxZ(), foundry.minX());
			tess.addVertexWithUV(foundry.maxX(), foundry.getLevel(), foundry.minZ(), foundry.minZ(), foundry.minX());
			tess.draw();
			
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glPopAttrib();
			GL11.glPopMatrix();
			
			GL11.glDepthMask(true);
		}
		
		GL11.glPopMatrix();
	}

	@Override
	public void renderActor(WorldInAJar world, int ticks, float interp, NBTTagCompound data) {
		int x = data.getInteger("x");
		int y = data.getInteger("y");
		int z = data.getInteger("z");
		renderTileEntityAt(world.getTileEntity(x, y, z), x, y, z, interp);
	}

	@Override
	public void updateActor(int ticks, NBTTagCompound data) { }
}
