package com.hbm.render.tileentity;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.tileentity.machine.TileEntityCargoElevator;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

import java.nio.DoubleBuffer;

public class RenderCargoElevator extends TileEntitySpecialRenderer implements IItemRendererProvider {
	private static final DoubleBuffer clipBuf = GLAllocation.createDirectByteBuffer(8 * 4).asDoubleBuffer();

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		bindTexture(ResourceManager.cargo_elevator_tex);
		TileEntityCargoElevator elevator = (TileEntityCargoElevator) tile;
		double extension = elevator.prevExtension + (elevator.extension - elevator.prevExtension) * interp;

		for (int mx = elevator.minX + 1; mx < elevator.maxX; mx += 3)
			for (int mz = elevator.minZ + 1; mz < elevator.maxZ; mz += 3) {
				GL11.glPushMatrix();
				GL11.glTranslated(x + 0.5, y, z + 0.5);
				GL11.glTranslated(mx, 0, mz);
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glEnable(GL11.GL_CULL_FACE);

				boolean west = mx == elevator.minX + 1, east = mx == elevator.maxX - 1,
					north = mz == elevator.minZ + 1, south = mz == elevator.maxZ - 1;

				if (elevator.renderPlatform) {
					ResourceManager.cargo_elevator.renderPart("Base");

					GL11.glPushMatrix();
					{
						GL11.glTranslated(0, extension, 0);
						ResourceManager.cargo_elevator.renderPart("Platform");

						if(!(west && north)) renderFiller(-1, -1);
						if(!(west && south)) renderFiller(-1, 1);
						if(!(east && north)) renderFiller(1, -1);
						if(!(east && south)) renderFiller(1, 1);

						for (int i = 0; i < extension + 1; i++) {
							ResourceManager.cargo_elevator.renderPart("Piston");
							GL11.glTranslated(0, -1, 0);
						}
					}
					GL11.glPopMatrix();
				}

				if (west && north && south && east) renderGuides(elevator);
				else {
					if (west && north) renderGuideCorner(elevator, -1,-1);
					if (west && south) renderGuideCorner(elevator, -1,1);
					if (east && north) renderGuideCorner(elevator, 1,-1);
					if (east && south) renderGuideCorner(elevator, 1,1);
				}

				GL11.glPopMatrix();
			}
	}

	private void renderFiller(int sx, int sz) {
		GL11.glPushMatrix();
		GL11.glScaled(sx, 1, sz);
		GL11.glFrontFace(sx * sz < 0 ? GL11.GL_CW : GL11.GL_CCW);
		ResourceManager.cargo_elevator.renderPart("Filler");
		GL11.glFrontFace(GL11.GL_CCW);
		GL11.glPopMatrix();
	}

	private void renderGuides(TileEntityCargoElevator te) {
		GL11.glPushMatrix();
		{
			for (int i = 0; i <= te.height; i++) {
				ResourceManager.cargo_elevator.renderPart("Guides");
				GL11.glTranslated(0, 1, 0);
			}
		}
		GL11.glPopMatrix();
	}

	private void renderGuideCorner(TileEntityCargoElevator elevator, int sx, int sz) {
		GL11.glEnable(GL11.GL_CLIP_PLANE0);
		clipBuf.put(new double[] { sx, 0, 0, -1 }); clipBuf.rewind();
		GL11.glClipPlane(GL11.GL_CLIP_PLANE0, clipBuf);
		GL11.glEnable(GL11.GL_CLIP_PLANE1);
		clipBuf.put(new double[] { 0, 0, sz, -1 }); clipBuf.rewind();
		GL11.glClipPlane(GL11.GL_CLIP_PLANE1, clipBuf);

		renderGuides(elevator);

		GL11.glDisable(GL11.GL_CLIP_PLANE0);
		GL11.glDisable(GL11.GL_CLIP_PLANE1);
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.cargo_elevator);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -2.75, 0);
				GL11.glScaled(3.25, 3.25, 3.25);
			}
			public void renderCommon() {
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.cargo_elevator_tex);
				ResourceManager.cargo_elevator.renderPart("Base");
				ResourceManager.cargo_elevator.renderPart("Piston");
				ResourceManager.cargo_elevator.renderPart("Guides");
				GL11.glTranslated(0, 1, 0);
				ResourceManager.cargo_elevator.renderPart("Piston");
				ResourceManager.cargo_elevator.renderPart("Guides");
				ResourceManager.cargo_elevator.renderPart("Platform");
				GL11.glTranslated(0, 1, 0);
				ResourceManager.cargo_elevator.renderPart("Guides");
				GL11.glShadeModel(GL11.GL_FLAT);
			}};
	}
}
