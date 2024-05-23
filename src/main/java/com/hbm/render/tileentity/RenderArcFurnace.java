package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.tileentity.machine.TileEntityMachineArcFurnaceLarge;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderArcFurnace extends TileEntitySpecialRenderer implements IItemRendererProvider {
	
	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);

		switch(tile.getBlockMetadata() - BlockDummyable.offset) {
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		}
		
		TileEntityMachineArcFurnaceLarge arc = (TileEntityMachineArcFurnaceLarge) tile;
		float lift = arc.prevLid + (arc.lid - arc.prevLid) * interp;

		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.arc_furnace_tex);
		ResourceManager.arc_furnace.renderPart("Furnace");
		
		if(!arc.liquids.isEmpty()) {
			fullbright(true);
			GL11.glTranslated(0, -1.75 + arc.getStackAmount(arc.liquids) * 1.75 / arc.maxLiquid, 0);
			ResourceManager.arc_furnace.renderPart("ContentsHot");
			fullbright(false);
		} else if(arc.hasMaterial) {
			ResourceManager.arc_furnace.renderPart("ContentsCold");
		}
		
		GL11.glTranslated(0, 2 * lift, 0);
		if(arc.isProgressing) GL11.glTranslated(0, 0, Math.sin((arc.getWorldObj().getTotalWorldTime() + interp)) * 0.005);
		ResourceManager.arc_furnace.renderPart("Lid");
		if(arc.electrodes[0] != arc.ELECTRODE_NONE) ResourceManager.arc_furnace.renderPart("Ring1");
		if(arc.electrodes[1] != arc.ELECTRODE_NONE) ResourceManager.arc_furnace.renderPart("Ring2");
		if(arc.electrodes[2] != arc.ELECTRODE_NONE) ResourceManager.arc_furnace.renderPart("Ring3");
		if(arc.electrodes[0] == arc.ELECTRODE_FRESH) ResourceManager.arc_furnace.renderPart("Electrode1");
		if(arc.electrodes[1] == arc.ELECTRODE_FRESH) ResourceManager.arc_furnace.renderPart("Electrode2");
		if(arc.electrodes[2] == arc.ELECTRODE_FRESH) ResourceManager.arc_furnace.renderPart("Electrode3");
		fullbright(true);
		if(arc.electrodes[0] == arc.ELECTRODE_USED) ResourceManager.arc_furnace.renderPart("Electrode1Hot");
		if(arc.electrodes[1] == arc.ELECTRODE_USED) ResourceManager.arc_furnace.renderPart("Electrode2Hot");
		if(arc.electrodes[2] == arc.ELECTRODE_USED) ResourceManager.arc_furnace.renderPart("Electrode3Hot");
		if(arc.electrodes[0] == arc.ELECTRODE_DEPLETED) ResourceManager.arc_furnace.renderPart("Electrode1Short");
		if(arc.electrodes[1] == arc.ELECTRODE_DEPLETED) ResourceManager.arc_furnace.renderPart("Electrode2Short");
		if(arc.electrodes[2] == arc.ELECTRODE_DEPLETED) ResourceManager.arc_furnace.renderPart("Electrode3Short");
		fullbright(false);
		
		if(arc.electrodes[0] != arc.ELECTRODE_NONE) {
			GL11.glPushMatrix();
			GL11.glTranslated(0, 5.5, 0.5);
			if(arc.isProgressing) GL11.glRotated(Math.sin((arc.getWorldObj().getTotalWorldTime() + interp) / 2) * 30, 1, 0, 0);
			GL11.glTranslated(0, -5.5, -0.5);
			ResourceManager.arc_furnace.renderPart("Cable1");
			GL11.glPopMatrix();
		}
		if(arc.electrodes[1] != arc.ELECTRODE_NONE) {
			GL11.glPushMatrix();
			GL11.glTranslated(0, 5.5, 0);
			if(arc.isProgressing) GL11.glRotated(Math.sin((arc.getWorldObj().getTotalWorldTime() + interp) / 2) * 30, 1, 0, 0);
			GL11.glTranslated(0, -5.5, 0);
			ResourceManager.arc_furnace.renderPart("Cable2");
			GL11.glPopMatrix();
		}
		if(arc.electrodes[2] != arc.ELECTRODE_NONE) {
			GL11.glPushMatrix();
			GL11.glTranslated(0, 5.5, -0.5);
			if(arc.isProgressing) GL11.glRotated(Math.sin((arc.getWorldObj().getTotalWorldTime() + interp) / 2) * 30, 1, 0, 0);
			GL11.glTranslated(0, -5.5, 0.5);
			ResourceManager.arc_furnace.renderPart("Cable3");
			GL11.glPopMatrix();
		}
		
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}
	
	private static float lastX;
	private static float lastY;
	
	public static void fullbright(boolean on) {
		
		if(on) {
			lastX = OpenGlHelper.lastBrightnessX;
			lastY = OpenGlHelper.lastBrightnessY;
			GL11.glPushMatrix();
			GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_CULL_FACE);
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
		} else {
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastX, lastY);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glPopAttrib();
			GL11.glPopMatrix();
		}
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.machine_arc_furnace);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase( ) {
			public void renderInventory() {
				GL11.glTranslated(0, -3, 0);
				GL11.glScaled(3.5, 3.5, 3.5);
			}
			public void renderCommon() {
				GL11.glScaled(0.5, 0.5, 0.5);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.arc_furnace_tex);
				ResourceManager.arc_furnace.renderPart("Furnace");
				ResourceManager.arc_furnace.renderPart("Lid");
				ResourceManager.arc_furnace.renderPart("Ring1");
				ResourceManager.arc_furnace.renderPart("Ring2");
				ResourceManager.arc_furnace.renderPart("Ring3");
				ResourceManager.arc_furnace.renderPart("Electrode1");
				ResourceManager.arc_furnace.renderPart("Electrode2");
				ResourceManager.arc_furnace.renderPart("Electrode3");
				ResourceManager.arc_furnace.renderPart("Cable1");
				ResourceManager.arc_furnace.renderPart("Cable2");
				ResourceManager.arc_furnace.renderPart("Cable3");
				GL11.glShadeModel(GL11.GL_FLAT);
			}};
	}
}
