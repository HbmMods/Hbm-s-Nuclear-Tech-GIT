package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.bomb.TileEntityLaunchPad;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class RenderLaunchPadTier1 extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);

		bindTexture(ResourceManager.missile_pad_tex);
		ResourceManager.missile_pad.renderAll();

		GL11.glDisable(GL11.GL_CULL_FACE);

		if(tileEntity instanceof TileEntityLaunchPad) {
			ItemStack toRender = ((TileEntityLaunchPad) tileEntity).toRender;
			
			if(toRender != null) {
				GL11.glTranslated(0, 1, 0);
	
				//TODO: add a registry for missile rendering to be reused here and for the entity renderer
				if(toRender.getItem() == ModItems.missile_generic) {
					GL11.glScalef(1.0F, 1.0F, 1.0F);
					bindTexture(ResourceManager.missileV2_HE_tex);
					GL11.glShadeModel(GL11.GL_SMOOTH);
					ResourceManager.missileV2.renderAll();
					GL11.glShadeModel(GL11.GL_FLAT);
				}
				if(toRender.getItem() == ModItems.missile_decoy) {
					GL11.glScalef(1.0F, 1.0F, 1.0F);
					bindTexture(ResourceManager.missileV2_decoy_tex);
					GL11.glShadeModel(GL11.GL_SMOOTH);
					ResourceManager.missileV2.renderAll();
					GL11.glShadeModel(GL11.GL_FLAT);
				}
				if(toRender.getItem() == ModItems.missile_strong) {
					GL11.glScalef(1.5F, 1.5F, 1.5F);
					bindTexture(ResourceManager.missileStrong_HE_tex);
					GL11.glShadeModel(GL11.GL_SMOOTH);
					ResourceManager.missileStrong.renderAll();
					GL11.glShadeModel(GL11.GL_FLAT);
				}
				if(toRender.getItem() == ModItems.missile_cluster) {
					GL11.glScalef(1.0F, 1.0F, 1.0F);
					bindTexture(ResourceManager.missileV2_CL_tex);
					GL11.glShadeModel(GL11.GL_SMOOTH);
					ResourceManager.missileV2.renderAll();
					GL11.glShadeModel(GL11.GL_FLAT);
				}
				if(toRender.getItem() == ModItems.missile_nuclear) {
					GL11.glScalef(1.5F, 1.5F, 1.5F);
					bindTexture(ResourceManager.missileNuclear_tex);
					ResourceManager.missileNuclear.renderAll();
				}
				if(toRender.getItem() == ModItems.missile_incendiary) {
					GL11.glScalef(1.0F, 1.0F, 1.0F);
					bindTexture(ResourceManager.missileV2_IN_tex);
					GL11.glShadeModel(GL11.GL_SMOOTH);
					ResourceManager.missileV2.renderAll();
					GL11.glShadeModel(GL11.GL_FLAT);
				}
				if(toRender.getItem() == ModItems.missile_buster) {
					GL11.glScalef(1.0F, 1.0F, 1.0F);
					bindTexture(ResourceManager.missileV2_BU_tex);
					GL11.glShadeModel(GL11.GL_SMOOTH);
					ResourceManager.missileV2.renderAll();
					GL11.glShadeModel(GL11.GL_FLAT);
				}
				if(toRender.getItem() == ModItems.missile_incendiary_strong) {
					GL11.glScalef(1.5F, 1.5F, 1.5F);
					bindTexture(ResourceManager.missileStrong_IN_tex);
					GL11.glShadeModel(GL11.GL_SMOOTH);
					ResourceManager.missileStrong.renderAll();
					GL11.glShadeModel(GL11.GL_FLAT);
				}
				if(toRender.getItem() == ModItems.missile_cluster_strong) {
					GL11.glScalef(1.5F, 1.5F, 1.5F);
					bindTexture(ResourceManager.missileStrong_CL_tex);
					GL11.glShadeModel(GL11.GL_SMOOTH);
					ResourceManager.missileStrong.renderAll();
					GL11.glShadeModel(GL11.GL_FLAT);
				}
				if(toRender.getItem() == ModItems.missile_buster_strong) {
					GL11.glScalef(1.5F, 1.5F, 1.5F);
					bindTexture(ResourceManager.missileStrong_BU_tex);
					GL11.glShadeModel(GL11.GL_SMOOTH);
					ResourceManager.missileStrong.renderAll();
					GL11.glShadeModel(GL11.GL_FLAT);
				}
				if(toRender.getItem() == ModItems.missile_burst) {
					bindTexture(ResourceManager.missileHuge_HE_tex);
					GL11.glShadeModel(GL11.GL_SMOOTH);
					ResourceManager.missileHuge.renderAll();
					GL11.glShadeModel(GL11.GL_FLAT);
				}
				if(toRender.getItem() == ModItems.missile_inferno) {
					bindTexture(ResourceManager.missileHuge_IN_tex);
					GL11.glShadeModel(GL11.GL_SMOOTH);
					ResourceManager.missileHuge.renderAll();
					GL11.glShadeModel(GL11.GL_FLAT);
				}
				if(toRender.getItem() == ModItems.missile_rain) {
					bindTexture(ResourceManager.missileHuge_CL_tex);
					GL11.glShadeModel(GL11.GL_SMOOTH);
					ResourceManager.missileHuge.renderAll();
					GL11.glShadeModel(GL11.GL_FLAT);
				}
				if(toRender.getItem() == ModItems.missile_drill) {
					bindTexture(ResourceManager.missileHuge_BU_tex);
					GL11.glShadeModel(GL11.GL_SMOOTH);
					ResourceManager.missileHuge.renderAll();
					GL11.glShadeModel(GL11.GL_FLAT);
				}
				if(toRender.getItem() == ModItems.missile_endo) {
					GL11.glScalef(1.5F, 1.5F, 1.5F);
					bindTexture(ResourceManager.missileEndo_tex);
					ResourceManager.missileThermo.renderAll();
				}
				if(toRender.getItem() == ModItems.missile_exo) {
					GL11.glScalef(1.5F, 1.5F, 1.5F);
					bindTexture(ResourceManager.missileExo_tex);
					ResourceManager.missileThermo.renderAll();
				}
				if(toRender.getItem() == ModItems.missile_nuclear_cluster) {
					GL11.glScalef(1.5F, 1.5F, 1.5F);
					bindTexture(ResourceManager.missileMIRV_tex);
					ResourceManager.missileNuclear.renderAll();
				}
				if(toRender.getItem() == ModItems.missile_doomsday) {
					GL11.glScalef(2F, 2F, 2F);
					bindTexture(ResourceManager.missileDoomsday_tex);
					ResourceManager.missileDoomsday.renderAll();
				}
				if(toRender.getItem() == ModItems.missile_taint) {
					GL11.glScalef(2F, 2F, 2F);
					bindTexture(ResourceManager.missileTaint_tex);
					ResourceManager.missileTaint.renderAll();
				}
				if(toRender.getItem() == ModItems.missile_micro) {
					GL11.glScalef(2F, 2F, 2F);
					bindTexture(ResourceManager.missileMicro_tex);
					ResourceManager.missileTaint.renderAll();
				}
				if(toRender.getItem() == ModItems.missile_carrier) {
					GL11.glScalef(2F, 2F, 2F);
					bindTexture(ResourceManager.missileCarrier_tex);
					ResourceManager.missileCarrier.renderAll();
					GL11.glTranslated(0.0D, 0.5D, 0.0D);
					GL11.glTranslated(1.25D, 0.0D, 0.0D);
					bindTexture(ResourceManager.missileBooster_tex);
					ResourceManager.missileBooster.renderAll();
					GL11.glTranslated(-2.5D, 0.0D, 0.0D);
					ResourceManager.missileBooster.renderAll();
					GL11.glTranslated(1.25D, 0.0D, 0.0D);
					GL11.glTranslated(0.0D, 0.0D, 1.25D);
					ResourceManager.missileBooster.renderAll();
					GL11.glTranslated(0.0D, 0.0D, -2.5D);
					ResourceManager.missileBooster.renderAll();
					GL11.glTranslated(0.0D, 0.0D, 1.25D);
				}
				if(toRender.getItem() == ModItems.missile_anti_ballistic) {
					GL11.glScalef(1.0F, 1.0F, 1.0F);
					bindTexture(ResourceManager.missileAA_tex);
					GL11.glShadeModel(GL11.GL_SMOOTH);
					ResourceManager.missileABM.renderAll();
					GL11.glShadeModel(GL11.GL_FLAT);
				}
				if(toRender.getItem() == ModItems.missile_bhole) {
					GL11.glScalef(2F, 2F, 2F);
					bindTexture(ResourceManager.missileMicroBHole_tex);
					ResourceManager.missileTaint.renderAll();
				}
				if(toRender.getItem() == ModItems.missile_schrabidium) {
					GL11.glScalef(2F, 2F, 2F);
					bindTexture(ResourceManager.missileMicroSchrab_tex);
					ResourceManager.missileTaint.renderAll();
				}
				if(toRender.getItem() == ModItems.missile_emp) {
					GL11.glScalef(2F, 2F, 2F);
					bindTexture(ResourceManager.missileMicroEMP_tex);
					ResourceManager.missileTaint.renderAll();
				}
				if(toRender.getItem() == ModItems.missile_emp_strong) {
					GL11.glScalef(1.5F, 1.5F, 1.5F);
					bindTexture(ResourceManager.missileStrong_EMP_tex);
					GL11.glShadeModel(GL11.GL_SMOOTH);
					ResourceManager.missileStrong.renderAll();
					GL11.glShadeModel(GL11.GL_FLAT);
				}
				if(toRender.getItem() == ModItems.missile_volcano) {
					GL11.glScalef(1.5F, 1.5F, 1.5F);
					bindTexture(ResourceManager.missileVolcano_tex);
					ResourceManager.missileNuclear.renderAll();
				}
				if(toRender.getItem() == ModItems.missile_shuttle) {
					GL11.glScalef(1.0F, 1.0F, 1.0F);
					bindTexture(ResourceManager.missileShuttle_tex);
					ResourceManager.missileShuttle.renderAll();
				}
			}
		}

		GL11.glEnable(GL11.GL_CULL_FACE);

		GL11.glPopMatrix();
	}

}
