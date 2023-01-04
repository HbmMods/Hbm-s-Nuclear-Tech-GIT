package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.tileentity.machine.TileEntityMachineExcavator;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class RenderExcavator extends TileEntitySpecialRenderer implements IItemRendererProvider {
	
	public static final ResourceLocation cobble = new ResourceLocation(RefStrings.MODID, "textures/models/machines/cobblestone.png");
	public static final ResourceLocation gravel = new ResourceLocation(RefStrings.MODID, "textures/models/machines/gravel.png");

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		switch(tile.getBlockMetadata() - BlockDummyable.offset) {
		case 3: GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 2: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(270, 0F, 1F, 0F); break;
		}
		
		GL11.glTranslated(0, -((BlockDummyable) ModBlocks.machine_excavator).getHeightOffset(), 0);
		
		TileEntityMachineExcavator drill = (TileEntityMachineExcavator) tile;
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.mining_drill_tex);
		ResourceManager.mining_drill.renderPart("Main");
		
		float crusher = drill.prevCrusherRotation + (drill.crusherRotation - drill.prevCrusherRotation) * interp;
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, 2.0F, 2.8125F);
		GL11.glRotatef(-crusher, 1, 0, 0);
		GL11.glTranslatef(0.0F, -2.0F, -2.8125F);
		ResourceManager.mining_drill.renderPart("Crusher1");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, 2.0F, 2.1875F);
		GL11.glRotatef(crusher, 1, 0, 0);
		GL11.glTranslatef(0.0F, -2.0F, -2.1875F);
		ResourceManager.mining_drill.renderPart("Crusher2");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glRotatef(drill.prevDrillRotation + (drill.drillRotation - drill.prevDrillRotation) * interp, 0F, -1F, 0F);
		float ext = drill.prevDrillExtension + (drill.drillExtension - drill.prevDrillExtension) * interp;
		GL11.glTranslatef(0.0F, -ext, 0.0F);
		ResourceManager.mining_drill.renderPart("Drillbit");
		
		while(ext >= -1.5) {
			ResourceManager.mining_drill.renderPart("Shaft");
			GL11.glTranslated(0.0D, 2.0D, 0.0D);
			ext -= 2;
		}
		GL11.glPopMatrix();
		
		GL11.glShadeModel(GL11.GL_FLAT);
		
		if(drill.chuteTimer > 0) {
			bindTexture(cobble);
			double widthX = 0.125;
			double widthZ = 0.125;
			double speed = 250D;
			double dropU = -System.currentTimeMillis() % speed / speed;
			double dropL = dropU + 4;
			Tessellator tess = Tessellator.instance;
			tess.startDrawingQuads();
			tess.setNormal(0F, 0F, 1F);
			tess.addVertexWithUV(widthX, 3, 2.5 + widthZ, 0, dropU);
			tess.addVertexWithUV(-widthX, 3, 2.5 + widthZ, 1, dropU);
			tess.addVertexWithUV(-widthX, 2, 2.5 + widthZ, 1, dropL);
			tess.addVertexWithUV(widthX, 2, 2.5 + widthZ, 0, dropL);

			tess.setNormal(0F, 0F, -1F);
			tess.addVertexWithUV(-widthX, 3, 2.5 - widthZ, 1, dropU);
			tess.addVertexWithUV(widthX, 3, 2.5 - widthZ, 0, dropU);
			tess.addVertexWithUV(widthX, 2, 2.5 - widthZ, 0, dropL);
			tess.addVertexWithUV(-widthX, 2, 2.5 - widthZ, 1, dropL);

			tess.setNormal(-1F, 0F, 0F);
			tess.addVertexWithUV(-widthX, 3, 2.5 + widthZ, 0, dropU);
			tess.addVertexWithUV(-widthX, 3, 2.5 - widthZ, 1, dropU);
			tess.addVertexWithUV(-widthX, 2, 2.5 - widthZ, 1, dropL);
			tess.addVertexWithUV(-widthX, 2, 2.5 + widthZ, 0, dropL);

			tess.setNormal(1F, 0F, 0F);
			tess.addVertexWithUV(widthX, 3, 2.5 - widthZ, 1, dropU);
			tess.addVertexWithUV(widthX, 3, 2.5 + widthZ, 0, dropU);
			tess.addVertexWithUV(widthX, 2, 2.5 + widthZ, 0, dropL);
			tess.addVertexWithUV(widthX, 2, 2.5 - widthZ, 1, dropL);
			tess.draw();

			boolean smoosh = drill.enableCrusher;
			widthX = smoosh ? 0.5 : 0.25;
			widthZ = 0.0625;
			double uU = smoosh ? 4 : 2;
			double uL = 0.5;
			bindTexture(smoosh ? gravel : cobble);
			tess.startDrawingQuads();
			tess.setNormal(0F, 0F, 1F);
			tess.addVertexWithUV(widthX, 2, 2.5 + widthZ, 0, dropU);
			tess.addVertexWithUV(-widthX, 2, 2.5 + widthZ, uU, dropU);
			tess.addVertexWithUV(-widthX, 1, 2.5 + widthZ, uU, dropL);
			tess.addVertexWithUV(widthX, 1, 2.5 + widthZ, 0, dropL);

			tess.setNormal(0F, 0F, -1F);
			tess.addVertexWithUV(-widthX, 2, 2.5 - widthZ, uU, dropU);
			tess.addVertexWithUV(widthX, 2, 2.5 - widthZ, 0, dropU);
			tess.addVertexWithUV(widthX, 1, 2.5 - widthZ, 0, dropL);
			tess.addVertexWithUV(-widthX, 1, 2.5 - widthZ, uU, dropL);

			tess.setNormal(-1F, 0F, 0F);
			tess.addVertexWithUV(-widthX, 2, 2.5 + widthZ, 0, dropU);
			tess.addVertexWithUV(-widthX, 2, 2.5 - widthZ, uL, dropU);
			tess.addVertexWithUV(-widthX, 1, 2.5 - widthZ, uL, dropL);
			tess.addVertexWithUV(-widthX, 1, 2.5 + widthZ, 0, dropL);

			tess.setNormal(1F, 0F, 0F);
			tess.addVertexWithUV(widthX, 2, 2.5 - widthZ, uL, dropU);
			tess.addVertexWithUV(widthX, 2, 2.5 + widthZ, 0, dropU);
			tess.addVertexWithUV(widthX, 1, 2.5 + widthZ, 0, dropL);
			tess.addVertexWithUV(widthX, 1, 2.5 - widthZ, uL, dropL);
			tess.draw();
		}
		
		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.machine_excavator);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase( ) {
			public void renderInventory() {
				GL11.glTranslated(0, -2, 0);
				GL11.glScaled(3, 3, 3);
			}
			public void renderCommon() {
				GL11.glRotatef(90, 0F, 1F, 0F);
				GL11.glScaled(0.5, 0.5, 0.5);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.mining_drill_tex); ResourceManager.mining_drill.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}};
	}
}
