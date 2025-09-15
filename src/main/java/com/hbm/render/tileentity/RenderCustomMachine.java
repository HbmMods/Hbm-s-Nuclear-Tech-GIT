package com.hbm.render.tileentity;

import com.hbm.config.CustomMachineConfigJSON;
import com.hbm.lib.RefStrings;
import com.hbm.render.util.SmallBlockPronter;
import com.hbm.tileentity.machine.TileEntityCustomMachine;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

import static com.hbm.config.CustomMachineConfigJSON.customModels;

public class RenderCustomMachine extends TileEntitySpecialRenderer {
	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {

		TileEntityCustomMachine custom = (TileEntityCustomMachine) tile;
		CustomMachineConfigJSON.MachineConfiguration config = custom.config;

		ForgeDirection dir = ForgeDirection.getOrientation(tile.getBlockMetadata());
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		if(config != null) {

			if(!custom.structureOK){
				GL11.glPushMatrix();
				GL11.glTranslated(x, y, z);

				bindTexture(TextureMap.locationBlocksTexture);
				SmallBlockPronter.startDrawing();
				for (CustomMachineConfigJSON.MachineConfiguration.ComponentDefinition comp : config.components) {
					int rx = -dir.offsetX * comp.x + rot.offsetX * comp.x;
					int ry = +comp.y;
					int rz = -dir.offsetZ * comp.z + rot.offsetZ * comp.z;
					if (dir == ForgeDirection.EAST || dir == ForgeDirection.WEST) {
						rx = +dir.offsetZ * comp.z - rot.offsetZ * comp.z;
						rz = +dir.offsetX * comp.x - rot.offsetX * comp.x;
					}

					int index = (int) ((System.currentTimeMillis() / 1000) % comp.metas.size());
					SmallBlockPronter.drawSmolBlockAt(comp.block, comp.metas.get(index).getAsInt(), rx, ry, rz);

				}

				SmallBlockPronter.draw();

				GL11.glPopMatrix();
			}
			else if(config.customModel!=null){
				try{
					IModelCustom customModel = customModels.get(config.unlocalizedName);
					ResourceLocation modelTexture = new ResourceLocation(RefStrings.MODID, config.customModel.modelTexture);
					GL11.glPushMatrix();
					double rx = -dir.offsetX * (config.customModel.model_x) + 0.5;
					double ry = +(config.customModel.model_y);
					double rz = -dir.offsetZ * (config.customModel.model_z) + 0.5;
					if (dir == ForgeDirection.EAST || dir == ForgeDirection.WEST) {
						rx = -dir.offsetX * (config.customModel.model_z) + 0.5;
						rz = -dir.offsetZ * (config.customModel.model_x) + 0.5;
					}
					GL11.glTranslated(x + rx, y + ry, z + rz);

					switch (tile.getBlockMetadata()) {
						case 3:
							GL11.glRotatef(0, 0F, 1F, 0F);
							break;
						case 5:
							GL11.glRotatef(90, 0F, 1F, 0F);
							break;
						case 2:
							GL11.glRotatef(180, 0F, 1F, 0F);
							break;
						case 4:
							GL11.glRotatef(270, 0F, 1F, 0F);
							break;
					}
					GL11.glEnable(GL11.GL_LIGHTING);
					GL11.glEnable(GL11.GL_CULL_FACE);

					GL11.glShadeModel(GL11.GL_SMOOTH);
					bindTexture(modelTexture);
					customModel.renderAll();
					GL11.glShadeModel(GL11.GL_FLAT);

					GL11.glPopMatrix();
				}
				catch (Exception ignored){

				}
			}
		}
	}
}
