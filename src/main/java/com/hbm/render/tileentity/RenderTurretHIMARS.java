package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.weapon.ItemAmmoHIMARS;
import com.hbm.items.weapon.ItemAmmoHIMARS.HIMARSRocket;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.tileentity.turret.TileEntityTurretHIMARS;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.IItemRenderer;

public class RenderTurretHIMARS extends TileEntitySpecialRenderer implements IItemRendererProvider {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float interp) {
		
		TileEntityTurretHIMARS turret = (TileEntityTurretHIMARS)te;
		Vec3 pos = turret.getHorizontalOffset();

		GL11.glPushMatrix();
		GL11.glTranslated(x + pos.xCoord, y, z + pos.zCoord);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glShadeModel(GL11.GL_SMOOTH);

		bindTexture(ResourceManager.turret_arty_tex);
		ResourceManager.turret_arty.renderPart("Base");
		double yaw = -Math.toDegrees(turret.lastRotationYaw + (turret.rotationYaw - turret.lastRotationYaw) * interp) - 90D;
		double pitch = Math.toDegrees(turret.lastRotationPitch + (turret.rotationPitch - turret.lastRotationPitch) * interp);

		bindTexture(ResourceManager.turret_himars_tex);
		GL11.glRotated(yaw - 90, 0, 1, 0);
		ResourceManager.turret_himars.renderPart("Carriage");
		
		GL11.glTranslated(0, 2.25, 2);
		GL11.glRotated(pitch, 1, 0, 0);
		GL11.glTranslated(0, -2.25, -2);
		ResourceManager.turret_himars.renderPart("Launcher");
		
		double barrel = turret.lastCrane + (turret.crane - turret.lastCrane) * interp;
		double length = -5D;
		GL11.glTranslated(0, 0, barrel * length);
		ResourceManager.turret_himars.renderPart("Crane");
		
		if(turret.typeLoaded >= 0) {
			HIMARSRocket type = ItemAmmoHIMARS.itemTypes[turret.typeLoaded];
			bindTexture(type.texture);
			
			if(type.modelType == 0) {
				ResourceManager.turret_himars.renderPart("TubeStandard");
				
				for(int i = 0; i < turret.ammo; i++) {
					ResourceManager.turret_himars.renderPart("CapStandard" + (5 - i + 1));
				}
			}
			
			if(type.modelType == 1) {
				ResourceManager.turret_himars.renderPart("TubeSingle");
				
				if(turret.hasAmmo()) {
					ResourceManager.turret_himars.renderPart("CapSingle");
				}
			}
		}

		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.turret_himars);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase( ) {
			public void renderInventory() {
				GL11.glTranslated(0, -2, 0);
				GL11.glScaled(3.5, 3.5, 3.5);
			}
			public void renderCommonWithStack(ItemStack item) {
				GL11.glRotatef(-90, 0F, 1F, 0F);
				GL11.glScaled(0.5, 0.5, 0.5);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.turret_arty_tex);
				ResourceManager.turret_arty.renderPart("Base");
				bindTexture(ResourceManager.turret_himars_tex);
				ResourceManager.turret_himars.renderPart("Carriage");
				ResourceManager.turret_himars.renderPart("Launcher");
				ResourceManager.turret_himars.renderPart("Crane");
				bindTexture(ResourceManager.himars_standard_tex);
				ResourceManager.turret_himars.renderPart("TubeStandard");
				GL11.glShadeModel(GL11.GL_FLAT);
			}};
	}
}
