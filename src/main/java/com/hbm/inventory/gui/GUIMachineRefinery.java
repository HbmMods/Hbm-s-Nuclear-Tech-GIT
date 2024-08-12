package com.hbm.inventory.gui;

import com.hbm.inventory.FluidStack;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.recipes.RefineryRecipes;
import com.hbm.util.Tuple;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.item.ItemStack;
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
		
		this.xSize = 209;
		this.ySize = 231;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		refinery.tanks[0].renderTankInfo(this, mouseX, mouseY, guiLeft + 30, guiTop + 27, 21, 104);    // Render tooltip for column.
		refinery.tanks[1].renderTankInfo(this, mouseX, mouseY, guiLeft + 86, guiTop + 42, 16, 52);
		refinery.tanks[2].renderTankInfo(this, mouseX, mouseY, guiLeft + 106, guiTop + 42, 16, 52);
		refinery.tanks[3].renderTankInfo(this, mouseX, mouseY, guiLeft + 126, guiTop + 42, 16, 52);
		refinery.tanks[4].renderTankInfo(this, mouseX, mouseY, guiLeft + 146, guiTop + 42, 16, 52);
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 186, guiTop + 18, 16, 52, refinery.power, refinery.maxPower);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.refinery.hasCustomInventoryName() ? this.refinery.getInventoryName() : I18n.format(this.refinery.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - 34/2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 4, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		func_146110_a(guiLeft, guiTop, 0, 0, xSize, ySize, 350, 256);

		// power
		int j = (int)refinery.getPowerScaled(50);
		func_146110_a(guiLeft + 186, guiTop + 69 - j, 210, 52 - j, 16, j, 350, 256);

		OpenGlHelper.glBlendFunc(770, 771, 1, 0); // default

		// input tank
		FluidTank inputOil = refinery.tanks[0];
		if (inputOil.getFill() != 0) {

			int targetHeight = inputOil.getFill() * 101 / inputOil.getMaxFill();
			Color color = new Color(inputOil.getTankType().getColor());

			GL11.glEnable(GL11.GL_BLEND);
			GL11.glColor4f(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, 1F);
			func_146110_a(guiLeft + 33, guiTop + 130 - targetHeight, 226, 101 - targetHeight, 16, targetHeight, 350, 256);
			GL11.glDisable(GL11.GL_BLEND);
		}

		// fucking kgjhgdfjgdhjfg
		// func_146110_a lets you set the resolution of the source texture !!!!
		// 350x256 texture by behated (the pipes wouldn't fit)

		// pipes

		Tuple.Quintet<FluidStack, FluidStack, FluidStack, FluidStack, ItemStack> recipe = RefineryRecipes.getRefinery(inputOil.getTankType());

		// Heavy Oil Products
		Color color = new Color(recipe.getV().type.getColor());

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, 1F);
		func_146110_a(guiLeft + 52, guiTop + 63, 247, 1, 33, 48, 350, 256);
		GL11.glDisable(GL11.GL_BLEND);

		// Naphtha Oil Products
		color = new Color(recipe.getW().type.getColor());

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, 1F);
		func_146110_a(guiLeft + 52, guiTop + 32, 247, 50, 66, 52, 350, 256);
		GL11.glDisable(GL11.GL_BLEND);

		// Light Oil Products
		color = new Color(recipe.getX().type.getColor());

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, 1F);
		func_146110_a(guiLeft + 52, guiTop + 24, 247, 145, 86, 35, 350, 256);
		GL11.glDisable(GL11.GL_BLEND);

		// Gaseous Products
		color = new Color(recipe.getY().type.getColor());

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, 1F);
		func_146110_a(guiLeft + 36, guiTop + 16, 211, 119, 122, 25, 350, 256);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glColor4f(1F, 1F, 1F, 1F);

		// output tanks
		refinery.tanks[1].renderTank(guiLeft + 86, guiTop + 95, this.zLevel, 16, 52);
		refinery.tanks[2].renderTank(guiLeft + 106, guiTop + 95, this.zLevel, 16, 52);
		refinery.tanks[3].renderTank(guiLeft + 126, guiTop + 95, this.zLevel, 16, 52);
		refinery.tanks[4].renderTank(guiLeft + 146, guiTop + 95, this.zLevel, 16, 52);
	}
}
