package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.tileentity.machine.TileEntityMachineIntake;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderIntake extends TileEntitySpecialRenderer implements IItemRendererProvider {

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
		
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		
		switch(tileEntity.getBlockMetadata() - 10) {
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		}
		
		GL11.glTranslated(-0.5, 0, 0.5);
		
		TileEntityMachineIntake compressor = (TileEntityMachineIntake) tileEntity;
		
		bindTexture(ResourceManager.intake_tex);
		ResourceManager.intake.renderPart("Base");

		float rot = compressor.prevFan + (compressor.fan - compressor.prevFan) * f;
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, 0);
		GL11.glRotatef(-rot, 0, 1, 0);
		GL11.glTranslated(0, 0, 0);
		ResourceManager.intake.renderPart("Fan");
		GL11.glPopMatrix();
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.machine_intake);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase( ) {
			public void renderInventory() {
				GL11.glScaled(5, 5, 5);
			}
			public void renderCommon() {
				bindTexture(ResourceManager.intake_tex); ResourceManager.intake.renderAll();
			}
		};
	}
}
