package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.hbm.render.util.RenderDecoItem;
import com.hbm.tileentity.machine.storage.TileEntityMassStorage;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

@SideOnly(Side.CLIENT)
public class RenderMassStorage extends TileEntitySpecialRenderer {

	private RenderItem itemRenderer = new RenderDecoItem(this);

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		if(!(tile instanceof TileEntityMassStorage)) return;
		TileEntityMassStorage storage = (TileEntityMassStorage) tile;

		if(storage.type == null) return;

		Minecraft mc = Minecraft.getMinecraft();
		int dir = storage.getBlockMetadata() / 4;

		// fuck this shit, push pop the whole ass lighting state then for all I fucken care
		GL11.glPushAttrib(GL11.GL_ENABLE_BIT | GL11.GL_LIGHTING_BIT | GL11.GL_COLOR_BUFFER_BIT | GL11.GL_TRANSFORM_BIT);

		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glDisable(GL11.GL_NORMALIZE);

		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);

		GL11.glPushMatrix();
		{

			// align to block
			GL11.glTranslated(x, y, z);

			// align item (and flip)
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
			GL11.glRotatef(180.0F, 0, 0, 1);
			switch(dir) {
			case 1: GL11.glRotatef(180.0F, 0, 1, 0); break;
			case 2: GL11.glRotatef(-90.0F, 0, 1, 0); break;
			case 3: GL11.glRotatef(90.0F, 0, 1, 0); break;
			}
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);


			GL11.glTranslatef(0, 0, -0.005F); // offset to prevent z-fighting
			GL11.glScalef(1.0F / 16.0F, 1.0F / 16.0F, -0.0001F); // scale to block size


			GL11.glPushMatrix();
			{

				GL11.glTranslatef(4.0F, 2.5F, 0); // adjust to centered location
				GL11.glScalef(8.0F / 16.0F, 8.0F / 16.0F, 1); // scale to 8 pixels across

				if(mc.gameSettings.fancyGraphics) {
					itemRenderer.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, storage.type, 0, 0);
				} else {
					itemRenderer.renderItemIntoGUI(mc.fontRenderer, mc.renderEngine, storage.type, 0, 0, true);
				}

			}
			GL11.glPopMatrix();

			GL11.glColor3f(1, 1, 1);

			String text = getTextForCount(storage.getStockpile(), mc.fontRenderer.getUnicodeFlag());

			int textX = 32 - mc.fontRenderer.getStringWidth(text) / 2;
			int textY = 44;

			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glPushMatrix();
			{

				GL11.glScalef(4.0F / 16.0F, 4.0F / 16.0F, 4.0F / 16.0F);

				int fontColor = 0x00FF00;

				// funky text shadow rendering with no z-fighting and alpha testing still enabled
				mc.fontRenderer.drawString(text, textX + 1, textY + 1, (fontColor & 16579836) >> 2 | fontColor & -16777216);
				GL11.glTranslatef(0, 0, 1);
				mc.fontRenderer.drawString(text, textX, textY, 0x00FF00);

			}
			GL11.glPopMatrix();

			GL11.glDisable(GL11.GL_TEXTURE_2D);

			double fraction = (double) storage.getStockpile() / (double) storage.getCapacity();

			GL11.glColor3d(1.0 - fraction, fraction, 0.0);

			double bMinX = 2;
			double bMaxX = 2 + fraction * 12;
			double bMinY = 13.5;
			double bMaxY = 14;

			Tessellator tessellator = Tessellator.instance;
			tessellator.startDrawingQuads();
			tessellator.addVertex(bMinX, bMaxY, 0);
			tessellator.addVertex(bMaxX, bMaxY, 0);
			tessellator.addVertex(bMaxX, bMinY, 0);
			tessellator.addVertex(bMinX, bMinY, 0);
			tessellator.draw();

			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_LIGHTING);

		}
		GL11.glPopMatrix();

		GL11.glPopAttrib();
	}

	private String getTextForCount(int stackSize, boolean isUnicode) {
		if(stackSize >= 100000000 || (stackSize >= 1000000 && isUnicode)) return String.format("%.0fM", stackSize / 1000000f);
		if(stackSize >= 1000000) return String.format("%.1fM", stackSize / 1000000f);
		if(stackSize >= 100000 || (stackSize >= 10000 && isUnicode)) return String.format("%.0fK", stackSize / 1000f);
		if(stackSize >= 10000) return String.format("%.1fK", stackSize / 1000f);
		return String.valueOf(stackSize);
	}

}
