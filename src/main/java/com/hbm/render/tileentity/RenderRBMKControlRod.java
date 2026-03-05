package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKControl;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKControlManual;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class RenderRBMKControlRod extends TileEntitySpecialRenderer {

	private ResourceLocation[] textures = new ResourceLocation[] {
			new ResourceLocation(RefStrings.MODID + ":textures/blocks/rbmk/rbmk_control_red.png"),
			new ResourceLocation(RefStrings.MODID + ":textures/blocks/rbmk/rbmk_control_yellow.png"),
			new ResourceLocation(RefStrings.MODID + ":textures/blocks/rbmk/rbmk_control_green.png"),
			new ResourceLocation(RefStrings.MODID + ":textures/blocks/rbmk/rbmk_control_blue.png"),
			new ResourceLocation(RefStrings.MODID + ":textures/blocks/rbmk/rbmk_control_purple.png"),
	};
	private ResourceLocation textureStandard = new ResourceLocation(RefStrings.MODID + ":textures/blocks/rbmk/rbmk_control.png");
	private ResourceLocation textureAuto = new ResourceLocation(RefStrings.MODID + ":textures/blocks/rbmk/rbmk_control_auto.png");

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float i) {

		GL11.glPushMatrix();
		
		TileEntityRBMKControl control = (TileEntityRBMKControl) te;
		
		int offset = 1;
		
		for(int o = 1; o < 16; o++) {
			
			if(te.getWorldObj().getBlock(te.xCoord, te.yCoord + o, te.zCoord) == te.getBlockType()) {
				offset = o;
			} else {
				break;
			}
		}
		
		GL11.glTranslated(x + 0.5, y + offset, z + 0.5);
		
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		int brightness = control.getWorldObj().getLightBrightnessForSkyBlocks(control.xCoord, control.yCoord + offset + 1, control.zCoord, 0);
		int lX = brightness % 65536;
		int lY = brightness / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)lX / 1.0F, (float)lY / 1.0F);
		
		ResourceLocation texture = textureAuto;
		
		if(control instanceof TileEntityRBMKControlManual) {
			TileEntityRBMKControlManual crm = (TileEntityRBMKControlManual) control;
			if(crm.color == null) texture = textureStandard;
			else texture = textures[crm.color.ordinal()];
		}
		
		bindTexture(texture);
		
		double level = control.lastLevel + (control.level - control.lastLevel) * i;
		
		GL11.glTranslated(0, level, 0);
		ResourceManager.rbmk_rods_vbo.renderPart("Lid");

		GL11.glPopMatrix();
	}
}
