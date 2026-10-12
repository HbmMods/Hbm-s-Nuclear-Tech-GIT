package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.tileentity.network.TileEntityPipelineBase;
import com.hbm.tileentity.network.TileEntityFluidPipeAnchor;
import com.hbm.util.ColorUtil;
import com.hbm.util.Compat;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.IItemRenderer;

public class RenderPipeAnchor extends TileEntitySpecialRenderer implements IItemRendererProvider {
	private Item item;
	private ResourceLocation texture;

	public RenderPipeAnchor(Item item, ResourceLocation texture) {
		this.item = item;
		this.texture = texture;
	}

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y + 0.5D, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glShadeModel(GL11.GL_SMOOTH);

		GL11.glPushMatrix();
		switch(te.getBlockMetadata()) {
		case 0: GL11.glRotated(180, 1, 0, 0); break;
		case 1: break;
		case 2: GL11.glRotated(90, 1, 0, 0); GL11.glRotated(180, 0, 0, 1); break;
		case 3: GL11.glRotated(90, 1, 0, 0); break;
		case 4: GL11.glRotated(90, 1, 0, 0); GL11.glRotated(90, 0, 0, 1); break;
		case 5: GL11.glRotated(90, 1, 0, 0); GL11.glRotated(270, 0, 0, 1); break;
		}

		GL11.glTranslated(0, -0.5F, 0);
		bindTexture(texture);
		ResourceManager.pipe_anchor.renderPart("Anchor");
		GL11.glPopMatrix();

		TileEntityPipelineBase anchor = (TileEntityPipelineBase) te;

		for(int[] pos : anchor.getConnected()) {
			TileEntity tile = Compat.getTileStandard(te.getWorldObj(), pos[0], pos[1], pos[2]);
			if(tile instanceof TileEntityPipelineBase) {
				TileEntityPipelineBase other = (TileEntityPipelineBase) tile;

				Vec3 anchorPoint = anchor.getConnectionPoint();
				Vec3 connectionPoint = other.getConnectionPoint();

				if(isDominant(anchorPoint, connectionPoint)) {
					double dX = connectionPoint.xCoord - anchorPoint.xCoord;
					double dY = connectionPoint.yCoord - anchorPoint.yCoord;
					double dZ = connectionPoint.zCoord - anchorPoint.zCoord;

					double hyp = Math.sqrt(dX * dX + dZ * dZ);
					double yaw = Math.toDegrees(Math.atan2(dX, dZ));
					double pitch = Math.toDegrees(Math.atan2(dY, hyp));
					double length = Math.sqrt(dX * dX + dY * dY + dZ * dZ);

					GL11.glPushMatrix();
					GL11.glRotated(yaw, 0, 1, 0);
					GL11.glRotated(90 - pitch, 1, 0, 0);

					GL11.glPushMatrix();
					GL11.glScaled(1, length, 1);
					GL11.glTranslated(0, -0.5, 0);

					if (anchor instanceof TileEntityFluidPipeAnchor) {
						int color = ((TileEntityFluidPipeAnchor)anchor).getType().getColor();
						color = ColorUtil.lightenColor(color, 0.25D);
						
						GL11.glColor3f(ColorUtil.fr(color), ColorUtil.fg(color), ColorUtil.fb(color));
					} else {
						GL11.glColor3f(1F, 1F, 1F);
					}
					
					ResourceManager.pipe_anchor.renderPart("Pipe");
					GL11.glColor3f(1F, 1F, 1F);
					GL11.glPopMatrix();

					GL11.glPushMatrix();
					GL11.glTranslated(0, length / 2D - 1.5, 0);
					ResourceManager.pipe_anchor.renderPart("Ring");
					GL11.glPopMatrix();

					GL11.glPopMatrix();
				}
			}
		}
		
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}
	
	/** Determines the "dominant" anchor, i.e. the one that should render the pipe (instead of rendering two half segments, gives marginally better performance) */
	public static boolean isDominant(Vec3 first, Vec3 second) {
		if(first.xCoord < second.xCoord) return true;
		if(first.xCoord > second.xCoord) return false;
		if(first.yCoord < second.yCoord) return true;
		if(first.yCoord > second.yCoord) return false;
		if(first.zCoord < second.zCoord) return true;
		if(first.zCoord > second.zCoord) return false;
		return false; // exact same pos? no need to render anything
	}

	@Override
	public Item getItemForRenderer() {
		return item;
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -3.5, 0);
				double scale = 10;
				GL11.glScaled(scale, scale, scale);
			}
			public void renderCommon() {
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(texture);
				ResourceManager.pipe_anchor.renderPart("Anchor");
				GL11.glShadeModel(GL11.GL_FLAT);
			}};
	}
}
