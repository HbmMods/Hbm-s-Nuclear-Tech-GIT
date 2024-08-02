package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.tileentity.machine.TileEntityMachineStardar;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderStardar extends TileEntitySpecialRenderer implements IItemRendererProvider {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float interp) {
		if(!(te instanceof TileEntityMachineStardar)) return;
		TileEntityMachineStardar stardar = (TileEntityMachineStardar) te;

		GL11.glPushMatrix();
		{

			GL11.glTranslated(x + 0.5D, y - 3D, z + 0.5D);
			GL11.glEnable(GL11.GL_LIGHTING);
	
			GL11.glRotatef(180, 0F, 1F, 0F);
	
			switch(te.getBlockMetadata() - BlockDummyable.offset) {
			case 2: GL11.glRotatef(0, 0F, 1F, 0F); break;
			case 4: GL11.glRotatef(90, 0F, 1F, 0F); break;
			case 3: GL11.glRotatef(180, 0F, 1F, 0F); break;
			case 5: GL11.glRotatef(270, 0F, 1F, 0F); break;
			}
	
			bindTexture(ResourceManager.stardar_tex);
	
			GL11.glShadeModel(GL11.GL_SMOOTH);
			ResourceManager.stardar.renderPart("base");

			float dishYaw = stardar.prevDishYaw + (stardar.dishYaw - stardar.prevDishYaw) * interp;
			float dishPitch = stardar.prevDishPitch + (stardar.dishPitch - stardar.prevDishPitch) * interp;
			float dishOffset = 10.6F;

			GL11.glRotatef(dishYaw, 0, 1, 0);
			ResourceManager.stardar.renderPart("rotation");

	
			GL11.glShadeModel(GL11.GL_FLAT);
			GL11.glTranslatef(0, dishOffset, 0);
			GL11.glRotatef(dishPitch, 1, 0, 0);
			GL11.glTranslatef(0, -dishOffset, 0);
			ResourceManager.stardar.renderPart("pitch");

		}
		GL11.glPopMatrix();
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -5, 0);
				GL11.glScaled(1.5D, 1.5D, 1.5D);
			}
			public void renderCommon() {
				GL11.glScaled(0.55, 0.55, 0.55);
				GL11.glDisable(GL11.GL_CULL_FACE);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.stardar_tex);
				ResourceManager.stardar.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
				GL11.glEnable(GL11.GL_CULL_FACE);
			}
		};
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.machine_stardar);
	}

}
