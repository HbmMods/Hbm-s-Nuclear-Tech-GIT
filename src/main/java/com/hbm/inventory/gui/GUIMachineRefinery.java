package com.hbm.inventory.gui;

import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.recipes.RefineryRecipes;
import com.hbm.inventory.recipes.RefineryRecipes.RefineryRecipe;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineRefinery;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.oil.TileEntityMachineRefinery;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import java.awt.Color;

public class GUIMachineRefinery extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_refinery.png");
	private TileEntityMachineRefinery refinery;

	public GUIMachineRefinery(InventoryPlayer invPlayer, TileEntityMachineRefinery tedf) {
		super(new ContainerMachineRefinery(invPlayer, tedf));
		refinery = tedf;
		
		this.xSize = 184;
		this.ySize = 222;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		refinery.tanks[0].renderTankInfo(this, mouseX, mouseY, guiLeft + 12, guiTop + 17, 16, 52); // Render tooltip for column.
		refinery.tanks[1].renderTankInfo(this, mouseX, mouseY, guiLeft + 66, guiTop + 17, 16, 52);
		refinery.tanks[2].renderTankInfo(this, mouseX, mouseY, guiLeft + 84, guiTop + 17, 16, 52);
		refinery.tanks[3].renderTankInfo(this, mouseX, mouseY, guiLeft + 102, guiTop + 17, 16, 52);
		refinery.tanks[4].renderTankInfo(this, mouseX, mouseY, guiLeft + 120, guiTop + 17, 16, 52);
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 160, guiTop + 18, 16, 70, refinery.power, refinery.maxPower);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.refinery.hasCustomInventoryName() ? this.refinery.getInventoryName() : I18n.format(this.refinery.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - 36 / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 12, this.ySize - 96 + 4, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		// power
		int j = (int)refinery.getPowerScaled(70);
		drawTexturedModalRect(guiLeft + 160, guiTop + 88 - j, 184, 70 - j, 16, j);

		OpenGlHelper.glBlendFunc(770, 771, 1, 0); // default

		// input tank
		FluidTank inputOil = refinery.tanks[0];
		if (inputOil.getFill() != 0) {
			refinery.tanks[0].renderTank(guiLeft + 12, guiTop + 70, this.zLevel, 16, 52);
			Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		}

		// fucking kgjhgdfjgdhjfg
		// func_146110_a lets you set the resolution of the source texture !!!!
		// 350x256 texture by behated (the pipes wouldn't fit)

		// pipes
		
		RefineryRecipe recipe = RefineryRecipes.getRefinery(inputOil.getTankType());

		if(recipe == null) {
			func_146110_a(guiLeft + 60, guiTop + 54, 184, 104, 6, 2, 256, 256);
			func_146110_a(guiLeft + 60, guiTop + 47, 184, 97, 24, 2, 256, 256);
			func_146110_a(guiLeft + 60, guiTop + 40, 184, 90, 42, 2, 256, 256);
			func_146110_a(guiLeft + 60, guiTop + 33, 184, 83, 60, 2, 256, 256);
		} else {

			// Heavy Oil Products
			Color color = new Color(recipe.outputs[0].type.getColor());

			GL11.glEnable(GL11.GL_BLEND);
			GL11.glColor4f(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, 1F);
			func_146110_a(guiLeft + 60, guiTop + 54, 184, 104, 6, 2, 256, 256);

			// Naphtha Oil Products
			color = new Color(recipe.outputs[1].type.getColor());
			GL11.glColor4f(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, 1F);
			func_146110_a(guiLeft + 60, guiTop + 47, 184, 97, 24, 2, 256, 256);

			// Light Oil Products
			color = new Color(recipe.outputs[2].type.getColor());
			GL11.glColor4f(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, 1F);
			func_146110_a(guiLeft + 60, guiTop + 40, 184, 90, 42, 2, 256, 256);

			// Gaseous Products
			color = new Color(recipe.outputs[3].type.getColor());
			GL11.glColor4f(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, 1F);
			func_146110_a(guiLeft + 60, guiTop + 33, 184, 83, 60, 2, 256, 256);
			
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glColor4f(1F, 1F, 1F, 1F);
		}

		// output tanks
		refinery.tanks[1].renderTank(guiLeft + 66, guiTop + 70, this.zLevel, 16, 52);
		refinery.tanks[2].renderTank(guiLeft + 84, guiTop + 70, this.zLevel, 16, 52);
		refinery.tanks[3].renderTank(guiLeft + 102, guiTop + 70, this.zLevel, 16, 52);
		refinery.tanks[4].renderTank(guiLeft + 120, guiTop + 70, this.zLevel, 16, 52);
	}
}
