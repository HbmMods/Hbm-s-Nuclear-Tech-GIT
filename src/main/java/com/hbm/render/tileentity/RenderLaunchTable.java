package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.ItemCustomMissilePart;
import com.hbm.items.weapon.ItemCustomMissilePart.PartSize;
import com.hbm.main.ResourceManager;
import com.hbm.render.util.MissileMultipart;
import com.hbm.render.util.MissilePronter;
import com.hbm.tileentity.bomb.TileEntityLaunchTable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;

public class RenderLaunchTable extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float p_147500_8_) {
		
		GL11.glPushMatrix();
		
		GL11.glTranslatef((float) x + 0.5F, (float) y, (float) z + 0.5F);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		switch(tileentity.getBlockMetadata())
		{
		case 2:
			GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 0:
			GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 3:
			GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 1:
			GL11.glRotatef(270, 0F, 1F, 0F); break;
		}
		
		TileEntityLaunchTable launcher = (TileEntityLaunchTable)tileentity;
		
		bindTexture(ResourceManager.launch_table_base_tex);
		ResourceManager.launch_table_base.renderAll();

		if(launcher.padSize == PartSize.SIZE_10 || launcher.padSize == PartSize.SIZE_15) {
			bindTexture(ResourceManager.launch_table_small_pad_tex);
			ResourceManager.launch_table_small_pad.renderAll();
		}
		if(launcher.padSize == PartSize.SIZE_20) {
			bindTexture(ResourceManager.launch_table_large_pad_tex);
			ResourceManager.launch_table_large_pad.renderAll();
		}

		GL11.glPushMatrix();
		
		if(launcher.load != null) {
			MissileMultipart mp = MissileMultipart.loadFromStruct(launcher.load);
			
			if(mp != null && mp.fuselage != null)
				launcher.height = (int) mp.getHeight();
		}
		
		int height = (int) (launcher.height * 0.75);
		ResourceLocation base = ResourceManager.launch_table_large_scaffold_base_tex;
		ResourceLocation connector = ResourceManager.launch_table_large_scaffold_connector_tex;
		IModelCustom baseM = ResourceManager.launch_table_large_scaffold_base;
		IModelCustom connectorM = ResourceManager.launch_table_large_scaffold_connector;
		IModelCustom emptyM = ResourceManager.launch_table_large_scaffold_empty;


		if(launcher.padSize == PartSize.SIZE_10) {
			base = ResourceManager.launch_table_small_scaffold_base_tex;
			connector = ResourceManager.launch_table_small_scaffold_connector_tex;
			baseM = ResourceManager.launch_table_small_scaffold_base;
			connectorM = ResourceManager.launch_table_small_scaffold_connector;
			emptyM = ResourceManager.launch_table_small_scaffold_empty;
			GL11.glTranslatef(0F, 0F, -1F);
		}
		GL11.glTranslatef(0F, 1F, 3.5F);
		for(int i = 0; i < launcher.height + 1; i++) {

			if(i < height) {
				bindTexture(base);
				baseM.renderAll();
			} else if(i > height) {
				bindTexture(base);
				emptyM.renderAll();
			} else {
				
				if(launcher.load != null && launcher.load.fuselage != null && ((ItemCustomMissilePart)launcher.load.fuselage).top == launcher.padSize) {
					bindTexture(connector);
					connectorM.renderAll();
				} else {
					bindTexture(base);
					baseM.renderAll();
				}
			}
			
			GL11.glTranslatef(0F, 1F, 0F);
		}
		GL11.glPopMatrix();

		GL11.glTranslatef(0F, 2.0625F, 0F);
		
		/// DRAW MISSILE START
		GL11.glPushMatrix();
		
		if(launcher.load != null && launcher.load.fuselage != null && launcher.load.fuselage.top == launcher.padSize)
			MissilePronter.prontMissile(MissileMultipart.loadFromStruct(launcher.load), Minecraft.getMinecraft().getTextureManager());
		
		GL11.glPopMatrix();
		/// DRAW MISSILE END

		GL11.glPopMatrix();
	}

}
