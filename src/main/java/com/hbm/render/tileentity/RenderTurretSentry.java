package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.tileentity.turret.TileEntityTurretSentry;
import com.hbm.tileentity.turret.TileEntityTurretSentryDamaged;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.IItemRenderer;

public class RenderTurretSentry extends TileEntitySpecialRenderer implements IItemRendererProvider {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float interp) {
		
		TileEntityTurretSentry turret = (TileEntityTurretSentry)te;
		Vec3 pos = turret.getHorizontalOffset();

		GL11.glPushMatrix();
		GL11.glTranslated(x + pos.xCoord, y, z + pos.zCoord);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		boolean damaged = te instanceof TileEntityTurretSentryDamaged;

		if(damaged)
			bindTexture(ResourceManager.turret_sentry_damaged_tex);
		else
			bindTexture(ResourceManager.turret_sentry_tex);
		
		ResourceManager.turret_sentry.renderPart("Base");
		
		double yaw = -Math.toDegrees(turret.lastRotationYaw + (turret.rotationYaw - turret.lastRotationYaw) * interp);
		double pitch = Math.toDegrees(turret.lastRotationPitch + (turret.rotationPitch - turret.lastRotationPitch) * interp);
		
		GL11.glRotated(yaw, 0, 1, 0);
		ResourceManager.turret_sentry.renderPart("Pivot");
		
		GL11.glTranslated(0, 1.25, 0);
		GL11.glRotated(-pitch, 1, 0, 0);
		GL11.glTranslated(0, -1.25, 0);
		ResourceManager.turret_sentry.renderPart("Body");
		ResourceManager.turret_sentry.renderPart("Drum");

		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, (turret.lastBarrelLeftPos + (turret.barrelLeftPos - turret.lastBarrelLeftPos) * interp) * -0.5);
		ResourceManager.turret_sentry.renderPart("BarrelL");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		if(damaged) {
			GL11.glTranslated(0, 1.5, 0.5);
			GL11.glRotated(25, 1, 0, 0);
			GL11.glTranslated(0, -1.5, -0.5);
		} else {
			GL11.glTranslated(0, 0, (turret.lastBarrelRightPos + (turret.barrelRightPos - turret.lastBarrelRightPos) * interp) * -0.5);
		}
		ResourceManager.turret_sentry.renderPart("BarrelR");
		GL11.glPopMatrix();

		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.turret_sentry);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -4, 0);
				GL11.glScaled(7, 7, 7);
			}
			public void renderCommon() {
				GL11.glRotated(90, 0, 1, 0);
				bindTexture(ResourceManager.turret_sentry_tex);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				ResourceManager.turret_sentry.renderPart("Base");
				ResourceManager.turret_sentry.renderPart("Pivot");
				ResourceManager.turret_sentry.renderPart("Body");
				ResourceManager.turret_sentry.renderPart("Drum");
				ResourceManager.turret_sentry.renderPart("BarrelL");
				ResourceManager.turret_sentry.renderPart("BarrelR");
				GL11.glShadeModel(GL11.GL_FLAT);
			}};
	}
}
