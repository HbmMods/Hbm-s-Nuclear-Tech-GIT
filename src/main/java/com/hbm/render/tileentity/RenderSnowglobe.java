package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockSnowglobe.SnowglobeType;
import com.hbm.blocks.generic.BlockSnowglobe.TileEntitySnowglobe;
import com.hbm.lib.RefStrings;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.render.loader.HFRWavefrontObject;
import com.hbm.util.EnumUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.IModelCustom;

public class RenderSnowglobe extends TileEntitySpecialRenderer implements IItemRendererProvider {
	
	public static final IModelCustom snowglobe = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/trinkets/snowglobe.obj"), false).asDisplayList();
	public static final ResourceLocation socket = new ResourceLocation(RefStrings.MODID, "textures/models/trinkets/snowglobe.png");
	public static final ResourceLocation features = new ResourceLocation(RefStrings.MODID, "textures/models/trinkets/snowglobe_features.png");
	public static RenderBlocks renderer = new RenderBlocks();

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		
		GL11.glRotated(22.5D * tile.getBlockMetadata() + 90, 0, -1, 0);

		TileEntitySnowglobe te = (TileEntitySnowglobe) tile;
		renderSnowglobe(te.type);
		
		GL11.glPopMatrix();
	}
	
	public static void renderSnowglobe(SnowglobeType type) {
		
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		double scale = 0.0625D;
		GL11.glScaled(scale, scale, scale);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(socket);
		snowglobe.renderPart("Socket");
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(features);
		
		switch(type) {
		case NONE: break;
		case RIVETCITY:		snowglobe.renderPart("RivetCity"); break;
		case TENPENNYTOWER:	snowglobe.renderPart("TenpennyTower"); break;
		case LUCKY38:		snowglobe.renderPart("Lucky38_Plane"); break;
		case SIERRAMADRE:	snowglobe.renderPart("SierraMadre"); break;
		case PRYDWEN:		snowglobe.renderPart("Prydwen"); break;
		default: break;
		}

		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glShadeModel(GL11.GL_FLAT);
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.snowglobe);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -4, 0);
				GL11.glScaled(10, 10, 10);
			}
			public void renderCommonWithStack(ItemStack item) {
				GL11.glTranslated(0, 0.25, 0);
				GL11.glScaled(3, 3, 3);
				SnowglobeType type = EnumUtil.grabEnumSafely(SnowglobeType.class, item.getItemDamage());
				renderSnowglobe(type);
			}};
	}
}
