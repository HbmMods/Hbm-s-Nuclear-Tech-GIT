package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.Floodlight.TileEntityFloodlight;
import com.hbm.lib.RefStrings;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.render.loader.HFRWavefrontObject;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.IModelCustom;

public class RenderFloodlight extends TileEntitySpecialRenderer implements IItemRendererProvider {
	
	public static final IModelCustom floodlight = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/blocks/floodlight.obj"));
	public static final ResourceLocation tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/floodlight.png");

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {

		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y + 0.5D, z + 0.5D);
		
		int meta = tileEntity.getBlockMetadata();
		switch(meta) {
		case 0: case 6: GL11.glRotated(180, 1, 0, 0); break;
		case 1: case 7: break;
		case 2: GL11.glRotated(90, 1, 0, 0); GL11.glRotated(180, 0, 0, 1); break;
		case 3: GL11.glRotated(90, 1, 0, 0); break;
		case 4: GL11.glRotated(90, 1, 0, 0); GL11.glRotated(90, 0, 0, 1); break;
		case 5: GL11.glRotated(90, 1, 0, 0); GL11.glRotated(270, 0, 0, 1); break;
		}
		
		GL11.glTranslated(0, -0.5, 0);
		
		if(meta != 0 && meta != 1) GL11.glRotated(90, 0, 1, 0);
		
		bindTexture(tex);
		floodlight.renderPart("Base");
		
		TileEntityFloodlight floodtile = (TileEntityFloodlight) tileEntity;
		float rotation = floodtile.rotation;
		if(meta == 0 || meta == 6) rotation -= 90;
		if(meta == 1 || meta == 7) rotation += 90;
		GL11.glTranslated(0, 0.5, 0);
		GL11.glRotatef(rotation, 0, 0, 1);
		GL11.glTranslated(0, -0.5, 0);
		
		floodlight.renderPart("Lights");
		
		if(floodtile.isOn) {
			RenderArcFurnace.fullbright(true);
			floodlight.renderPart("Lamps");
			RenderArcFurnace.fullbright(false);
		} else {
			GL11.glColor4f(0.25F, 0.25F, 0.25F, 1F);
			floodlight.renderPart("Lamps");
			GL11.glColor4f(1F, 1F, 1F, 1F);
		}
		
		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.floodlight);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase( ) {
			public void renderInventory() {
				GL11.glTranslated(0, -1.5, 0);
				GL11.glScaled(6.5, 6.5, 6.5);
			}
			public void renderCommon() {
				bindTexture(tex);
				floodlight.renderPart("Base");
				GL11.glTranslated(0, 0.5, 0);
				GL11.glRotatef(-30, 0, 0, 1);
				GL11.glTranslated(0, -0.5, 0);
				floodlight.renderPart("Lights");
				floodlight.renderPart("Lamps");
			}};
	}
}
