package com.hbm.render.tileentity;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.recipes.ChemicalPlantRecipes;
import com.hbm.inventory.recipes.loader.GenericRecipe;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.tileentity.machine.TileEntityMachineChemicalPlant;
import com.hbm.util.BobMathUtil;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderChemicalPlant extends TileEntitySpecialRenderer implements IItemRendererProvider {

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
		
		TileEntityMachineChemicalPlant chemplant = (TileEntityMachineChemicalPlant) tileEntity;
		float anim = chemplant.prevAnim + (chemplant.anim - chemplant.prevAnim) * interp;
		GenericRecipe recipe = ChemicalPlantRecipes.INSTANCE.recipeNameMap.get(chemplant.chemplantModule.recipe);
		
		bindTexture(ResourceManager.chemical_plant_tex);
		ResourceManager.chemical_plant.renderPart("Base");
		if(chemplant.frame) ResourceManager.chemical_plant.renderPart("Frame");
		
		GL11.glPushMatrix();
		GL11.glTranslated(BobMathUtil.sps(anim * 0.125) * 0.375, 0, 0);
		ResourceManager.chemical_plant.renderPart("Slider");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslated(0.5, 0, 0.5);
		GL11.glRotated((anim * 15) % 360D, 0, 1, 0);
		GL11.glTranslated(-0.5, 0, -0.5);
		ResourceManager.chemical_plant.renderPart("Spinner");
		GL11.glPopMatrix();

		if(chemplant.didProcess && recipe != null) {
			int colors = 0;
			int r = 0;
			int g = 0;
			int b = 0;
			if(recipe.outputFluid != null) for(FluidStack stack : recipe.outputFluid) {
				Color color = new Color(stack.type.getColor());
				r += color.getRed();
				g += color.getGreen();
				b += color.getBlue();
				colors++;
			}
			
			if(colors == 0 && recipe.inputFluid != null) for(FluidStack stack : recipe.inputFluid) {
				Color color = new Color(stack.type.getColor());
				r += color.getRed();
				g += color.getGreen();
				b += color.getBlue();
				colors++;
			}
			
			if(colors > 0) {
				bindTexture(ResourceManager.chemical_plant_fluid_tex);
				
				GL11.glPushMatrix();
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glAlphaFunc(GL11.GL_GREATER, 0);
				OpenGlHelper.glBlendFunc(770, 771, 1, 0);
				GL11.glColor4f(r / 255F / colors, g / 255F / colors, b / 255F / colors, 0.5F);
				GL11.glDepthMask(false);
				
				GL11.glMatrixMode(GL11.GL_TEXTURE);
				GL11.glLoadIdentity();
				GL11.glTranslated(-anim / 100F, BobMathUtil.sps(anim * 0.1) * 0.1 - 0.25, 0);
				ResourceManager.chemical_plant.renderPart("Fluid");
				GL11.glMatrixMode(GL11.GL_TEXTURE);
				GL11.glLoadIdentity();
				GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
				GL11.glDepthMask(true);
				GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				
				GL11.glPopMatrix();
			}
		}
		
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.machine_chemical_plant);
	}

	@Override
	public IItemRenderer getRenderer() {
		
		return new ItemRenderBase() {
			
			public void renderInventory() {
				GL11.glTranslated(0, -2.75, 0);
				GL11.glScaled(4.5, 4.5, 4.5);
			}
			public void renderCommonWithStack(ItemStack item) {
				GL11.glRotated(90, 0, 1, 0);
				GL11.glScaled(0.75, 0.75, 0.75);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.chemical_plant_tex);
				ResourceManager.chemical_plant.renderPart("Base");
				ResourceManager.chemical_plant.renderPart("Slider");
				ResourceManager.chemical_plant.renderPart("Spinner");
				ResourceManager.chemical_plant.renderPart("Frame");
				GL11.glShadeModel(GL11.GL_FLAT);
			}};
	}
}
