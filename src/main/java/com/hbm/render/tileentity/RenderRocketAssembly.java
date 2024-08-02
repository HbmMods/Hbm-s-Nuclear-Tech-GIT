package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.RocketStruct.RocketStage;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.render.util.MissilePronter;
import com.hbm.tileentity.machine.TileEntityMachineRocketAssembly;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderRocketAssembly extends TileEntitySpecialRenderer implements IItemRendererProvider {
	
	public RenderRocketAssembly() { }
	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {
		
		if(!(tileentity instanceof TileEntityMachineRocketAssembly))
			return;
		
		TileEntityMachineRocketAssembly te = (TileEntityMachineRocketAssembly)tileentity;
		
		GL11.glPushMatrix();
		
		GL11.glTranslated(x + 0.5, y - 2.0, z + 0.5D);
		
		switch(te.getBlockMetadata()) {
		case 2: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(90, 0F, 1F, 0F); break;
		}

		bindTexture(ResourceManager.rocket_assembly_tex);
		ResourceManager.rocket_assembly.renderPart("Base");
		
		if(te.rocket != null && te.rocket.extraIssues.size() == 0) {
			GL11.glPushMatrix();
			{

				GL11.glTranslatef(0F, 2.95F, 0F);

				if(te.rocket.stages.size() > 0) {
					RocketStage stage = te.rocket.stages.get(0);
					if(stage.thruster != null) {
						GL11.glTranslated(0, -stage.thruster.height, 0);
					}
				}
	
				MissilePronter.prontRocket(te.rocket, Minecraft.getMinecraft().getTextureManager(), false);

			}
			GL11.glPopMatrix();

			bindTexture(ResourceManager.rocket_assembly_tex);

			for(int i = 0; i < te.rocket.stages.size(); i++) {
				RocketStage stage = te.rocket.stages.get(i);
				RocketStage nextStage = i < te.rocket.stages.size() - 1 ? te.rocket.stages.get(i + 1) : null;

				double targetHeight = 0;
				if(stage.fuselage != null) targetHeight += stage.fuselage.height * stage.getStack();
				if(nextStage != null && nextStage.thruster != null) targetHeight += nextStage.thruster.height;

				while(targetHeight > 1) {
					GL11.glTranslated(0, 1, 0);
					ResourceManager.rocket_assembly.renderPart("Support");
					targetHeight--;
				}
				GL11.glTranslated(0, targetHeight, 0);

				ResourceManager.rocket_assembly.renderPart("Level");
			}
		}
		
		GL11.glPopMatrix();
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -1, 0);
				GL11.glScaled(2.0D, 2.0D, 2.0D);
			}
			public void renderCommon() {
				GL11.glScaled(0.55, 0.55, 0.55);
				GL11.glDisable(GL11.GL_CULL_FACE);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.rocket_assembly_tex);
				ResourceManager.rocket_assembly.renderPart("Base");
				GL11.glShadeModel(GL11.GL_FLAT);
				GL11.glEnable(GL11.GL_CULL_FACE);
			}
		};
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.machine_rocket_assembly);
	}

}
