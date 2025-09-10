package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.tileentity.machine.TileEntityMachineAssemblyFactory;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderAssemblyFactory extends TileEntitySpecialRenderer implements IItemRendererProvider {

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
		
		TileEntityMachineAssemblyFactory assemfac = (TileEntityMachineAssemblyFactory) tileEntity;
		
		bindTexture(ResourceManager.assembly_factory_tex);
		ResourceManager.assembly_factory.renderPart("Base");
		if(assemfac.frame) ResourceManager.assembly_factory.renderPart("Frame");

		double[] arm1 = new double[] {-30, -30, -30, -0.125};
		double[] arm2 = new double[] {-30, -30, -30, -0.125};
		double blade = (System.currentTimeMillis() / 1) % 360D;

		GL11.glPushMatrix(); {
			ResourceManager.assembly_factory.renderPart("Slider1");
			
			GL11.glTranslated(0, 1.625, -0.9375);
			GL11.glRotated(arm1[0], 1, 0, 0);
			GL11.glTranslated(0, -1.625, 0.9375);
			ResourceManager.assembly_factory.renderPart("ArmLower1");
	
			GL11.glTranslated(0, 2.375, -0.9375);
			GL11.glRotated(arm1[1], 1, 0, 0);
			GL11.glTranslated(0, -2.375, 0.9375);
			ResourceManager.assembly_factory.renderPart("ArmUpper1");
	
			GL11.glTranslated(0, 2.375, -0.4375);
			GL11.glRotated(arm1[2], 1, 0, 0);
			GL11.glTranslated(0, -2.375, 0.4375);
			ResourceManager.assembly_factory.renderPart("Head1");
			GL11.glTranslated(0, arm1[3], 0);
			ResourceManager.assembly_factory.renderPart("Striker1");
		} GL11.glPopMatrix();

		GL11.glPushMatrix(); {
			ResourceManager.assembly_factory.renderPart("Slider2");
			
			GL11.glTranslated(0, 1.625, 0.9375);
			GL11.glRotated(-arm2[0], 1, 0, 0);
			GL11.glTranslated(0, -1.625, -0.9375);
			ResourceManager.assembly_factory.renderPart("ArmLower2");
	
			GL11.glTranslated(0, 2.375, 0.9375);
			GL11.glRotated(-arm2[1], 1, 0, 0);
			GL11.glTranslated(0, -2.375, -0.9375);
			ResourceManager.assembly_factory.renderPart("ArmUpper2");
	
			GL11.glTranslated(0, 2.375, 0.4375);
			GL11.glRotated(-arm2[2], 1, 0, 0);
			GL11.glTranslated(0, -2.375, -0.4375);
			ResourceManager.assembly_factory.renderPart("Head2");
			GL11.glTranslated(0, arm2[3], 0);
			ResourceManager.assembly_factory.renderPart("Striker2");
			GL11.glTranslated(0, 1.625, 0.3125);
			GL11.glRotated(-blade, 1, 0, 0);
			GL11.glTranslated(0, -1.625, -0.3125);
			ResourceManager.assembly_factory.renderPart("Blade2");
		} GL11.glPopMatrix();

		GL11.glPushMatrix(); {
			ResourceManager.assembly_factory.renderPart("Slider3");
			
			GL11.glTranslated(0, 1.625, 0.9375);
			GL11.glRotated(-arm2[0], 1, 0, 0);
			GL11.glTranslated(0, -1.625, -0.9375);
			ResourceManager.assembly_factory.renderPart("ArmLower3");
	
			GL11.glTranslated(0, 2.375, 0.9375);
			GL11.glRotated(-arm2[1], 1, 0, 0);
			GL11.glTranslated(0, -2.375, -0.9375);
			ResourceManager.assembly_factory.renderPart("ArmUpper3");
	
			GL11.glTranslated(0, 2.375, 0.4375);
			GL11.glRotated(-arm2[2], 1, 0, 0);
			GL11.glTranslated(0, -2.375, -0.4375);
			ResourceManager.assembly_factory.renderPart("Head3");
			GL11.glTranslated(0, arm2[3], 0);
			ResourceManager.assembly_factory.renderPart("Striker3");
		} GL11.glPopMatrix();

		GL11.glPushMatrix(); {
			ResourceManager.assembly_factory.renderPart("Slider4");
			
			GL11.glTranslated(0, 1.625, -0.9375);
			GL11.glRotated(arm1[0], 1, 0, 0);
			GL11.glTranslated(0, -1.625, 0.9375);
			ResourceManager.assembly_factory.renderPart("ArmLower4");
	
			GL11.glTranslated(0, 2.375, -0.9375);
			GL11.glRotated(arm1[1], 1, 0, 0);
			GL11.glTranslated(0, -2.375, 0.9375);
			ResourceManager.assembly_factory.renderPart("ArmUpper4");
	
			GL11.glTranslated(0, 2.375, -0.4375);
			GL11.glRotated(arm1[2], 1, 0, 0);
			GL11.glTranslated(0, -2.375, 0.4375);
			ResourceManager.assembly_factory.renderPart("Head4");
			GL11.glTranslated(0, arm1[3], 0);
			ResourceManager.assembly_factory.renderPart("Striker4");
			GL11.glTranslated(0, 1.625, -0.3125);
			GL11.glRotated(blade, 1, 0, 0);
			GL11.glTranslated(0, -1.625, 0.3125);
			ResourceManager.assembly_factory.renderPart("Blade4");
		} GL11.glPopMatrix();

		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.machine_assembly_factory);
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
				bindTexture(ResourceManager.assembly_factory_tex);
				ResourceManager.assembly_factory.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}};
	}
}
