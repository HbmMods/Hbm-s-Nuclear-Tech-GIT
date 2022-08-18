package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.render.util.DiamondPronter;
import com.hbm.tileentity.machine.storage.TileEntityMachineFluidTank;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class RenderFluidTank extends TileEntitySpecialRenderer implements IItemRendererProvider {

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		
		switch(tileEntity.getBlockMetadata()) {
		case 2: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(180, 0F, 1F, 0F); break;
		}

		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.tank_tex);
		ResourceManager.fluidtank.renderPart("Frame");

		String s = "NONE";
		if(tileEntity instanceof TileEntityMachineFluidTank)
			s = ((TileEntityMachineFluidTank) tileEntity).tank.getTankType().getName();

		bindTexture(new ResourceLocation(RefStrings.MODID, "textures/models/tank/tank_" + s + ".png"));
		ResourceManager.fluidtank.renderPart("Tank");

		GL11.glShadeModel(GL11.GL_FLAT);

		
		FluidType type = ((TileEntityMachineFluidTank) tileEntity).tank.getTankType();
		
		if(type != null && type != Fluids.NONE) {

			RenderHelper.disableStandardItemLighting();
			GL11.glPushMatrix();
			GL11.glTranslated(-0.25, 0.5, -1.501);
			GL11.glRotated(90, 0, 1, 0);
			GL11.glScalef(1.0F, 0.375F, 0.375F);
			DiamondPronter.pront(type.poison, type.flammability, type.reactivity, type.symbol);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glTranslated(0.25, 0.5, 1.501);
			GL11.glRotated(-90, 0, 1, 0);
			GL11.glScalef(1.0F, 0.375F, 0.375F);
			DiamondPronter.pront(type.poison, type.flammability, type.reactivity, type.symbol);
			GL11.glPopMatrix();
		}
		
		GL11.glPopMatrix();
		RenderHelper.enableStandardItemLighting();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.machine_fluidtank);
	}

	@Override
	public IItemRenderer getRenderer() {
		
		return new ItemRenderBase() {
			
			public void renderInventory() {
				GL11.glTranslated(0, -2, 0);
				GL11.glScaled(3.5, 3.5, 3.5);
			}
			public void renderCommon() {
				GL11.glRotated(90, 0, 1, 0);
				GL11.glScaled(0.75, 0.75, 0.75);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.tank_tex); ResourceManager.fluidtank.renderPart("Frame");
				bindTexture(ResourceManager.tank_label_tex); ResourceManager.fluidtank.renderPart("Tank");
				GL11.glShadeModel(GL11.GL_FLAT);
			}};
	}
}
