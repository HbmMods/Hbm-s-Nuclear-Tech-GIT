package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.tileentity.machine.TileEntityMachineChemicalFactory;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderChemicalFactory extends TileEntitySpecialRenderer implements IItemRendererProvider {

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glShadeModel(GL11.GL_SMOOTH);

		switch(tileEntity.getBlockMetadata() - BlockDummyable.offset) {
		case 2: GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(270, 0F, 1F, 0F); break;
		}
		
		TileEntityMachineChemicalFactory chemplant = (TileEntityMachineChemicalFactory) tileEntity;
		float anim = chemplant.prevAnim + (chemplant.anim - chemplant.prevAnim) * interp;
		
		bindTexture(ResourceManager.chemical_factory_tex);
		ResourceManager.chemical_factory.renderPart("Base");
		if(chemplant.frame) ResourceManager.chemical_factory.renderPart("Frame");

		GL11.glPushMatrix();
		GL11.glTranslated(1, 0, 0);
		GL11.glRotated(-anim * 45 % 360D, 0, 1, 0);
		GL11.glTranslated(-1, 0, 0);
		ResourceManager.chemical_factory.renderPart("Fan1");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(-1, 0, 0);
		GL11.glRotated(-anim * 45 % 360D, 0, 1, 0);
		GL11.glTranslated(1, 0, 0);
		ResourceManager.chemical_factory.renderPart("Fan2");
		GL11.glPopMatrix();

		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.machine_chemical_factory);
	}

	@Override
	public IItemRenderer getRenderer() {
		
		return new ItemRenderBase() {
			
			public void renderInventory() {
				GL11.glTranslated(0, -1.5, 0);
				GL11.glScaled(3, 3, 3);
			}
			public void renderCommonWithStack(ItemStack item) {
				GL11.glScaled(0.75, 0.75, 0.75);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.chemical_factory_tex);
				ResourceManager.chemical_factory.renderPart("Base");
				ResourceManager.chemical_factory.renderPart("Frame");
				ResourceManager.chemical_factory.renderPart("Fan1");
				ResourceManager.chemical_factory.renderPart("Fan2");
				GL11.glShadeModel(GL11.GL_FLAT);
			}};
	}
}
