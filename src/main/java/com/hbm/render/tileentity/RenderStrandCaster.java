package com.hbm.render.tileentity;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.tileentity.machine.TileEntityMachineStrandCaster;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

import java.nio.DoubleBuffer;

public class RenderStrandCaster extends TileEntitySpecialRenderer implements IItemRendererProvider {
	public static final ResourceLocation lava = new ResourceLocation(RefStrings.MODID, "textures/models/machines/lava_gray.png");
	private static DoubleBuffer buf = null;

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float interp) {
		TileEntityMachineStrandCaster caster = (TileEntityMachineStrandCaster) te;

		if(buf == null){
			buf = GLAllocation.createDirectByteBuffer(8*4).asDoubleBuffer();
		}

		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		switch(te.getBlockMetadata() - BlockDummyable.offset) {
		case 4: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 2: GL11.glRotatef(0, 0F, 1F, 0F); break;
		}
		GL11.glTranslated(  0.5, 0, 0.5);
		GL11.glRotated(180, 0, 1, 0);

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		bindTexture(ResourceManager.strand_caster_tex);
		ResourceManager.strand_caster.renderPart("caster");


		if (caster.amount != 0 && caster.getInstalledMold() != null) {

			double level = ((double) caster.amount / (double) caster.getCapacity()) * 0.675D;
			double offset = ((double) caster.amount / (double) caster.getInstalledMold().getCost()) * 0.375D;

			int color = caster.type.moltenColor;

			int r = color >> 16 & 0xFF;
			int g = color >> 8 & 0xFF;
			int b = color & 0xFF;

			GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
			GL11.glDisable(GL11.GL_LIGHTING);

			GL11.glPushMatrix();
			GL11.glColor3f( r/ 255F,  g/ 255F, b/ 255F);
			GL11.glEnable(GL11.GL_CLIP_PLANE0);
			buf.put(new double[] { 0, 0, -1, 0.5} );
			buf.rewind();
			GL11.glClipPlane(GL11.GL_CLIP_PLANE0, buf);
			GL11.glTranslated(0,0,Math.max(-offset + 3.4, 0));
			ResourceManager.strand_caster.renderPart("plate");
			GL11.glDisable(GL11.GL_CLIP_PLANE0);
			GL11.glPopMatrix();

			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_CULL_FACE);
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
			Tessellator tess = Tessellator.instance;
			tess.setNormal(0F, 1F, 0F);
			tess.setColorOpaque_F(r / 255F, g / 255F, b / 255F);
			bindTexture(lava);
			tess.startDrawingQuads();
			tess.addVertexWithUV(-0.9, 2.3 + level, -0.999, 0, 0);
			tess.addVertexWithUV(-0.9, 2.3 + level, 0.999, 0, 1);
			tess.addVertexWithUV(0.9, 2.3 + level, 0.999, 1, 1);
			tess.addVertexWithUV(0.9, 2.3 + level, -0.999, 1, 0);
			tess.draw();

			GL11.glPopMatrix();
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glPopAttrib();
		}


		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
		
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.machine_strand_caster);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase( ) {
			public void renderInventory() {
				GL11.glTranslated(2, 0, 2);
				GL11.glScaled( 2, 2, 2);
			}
			public void renderCommon() {
				GL11.glScaled(1, 1, 1);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.strand_caster_tex); ResourceManager.strand_caster.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}
		};
	}

}
