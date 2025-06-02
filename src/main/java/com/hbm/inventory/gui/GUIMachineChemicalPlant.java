package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineChemicalPlant;
import com.hbm.inventory.recipes.ChemicalPlantRecipes;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineChemicalPlant;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineChemicalPlant extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_chemplant.png");
	private TileEntityMachineChemicalPlant chemplant;
	
	public GUIMachineChemicalPlant(InventoryPlayer invPlayer, TileEntityMachineChemicalPlant tedf) {
		super(new ContainerMachineChemicalPlant(invPlayer, tedf));
		chemplant = tedf;
		
		this.xSize = 176;
		this.ySize = 256;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		for(int i = 0; i < 3; i++) {
			chemplant.inputTanks[i].renderTankInfo(this, mouseX, mouseY, guiLeft + 8 + i * 18, guiTop + 18, 16, 34);
			chemplant.outputTanks[i].renderTankInfo(this, mouseX, mouseY, guiLeft + 80 + i * 18, guiTop + 18, 16, 34);
		}
		
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 152, guiTop + 18, 16, 61, chemplant.power, chemplant.maxPower);
	}
	
	@Override
	protected void mouseClicked(int x, int y, int button) {
		super.mouseClicked(x, y, button);
		
		if(this.checkClick(x, y, 7, 125, 18, 18)) GUIScreenRecipeSelector.openSelector(ChemicalPlantRecipes.INSTANCE, chemplant, chemplant.chemplantModule.recipe, 0, this);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.chemplant.hasCustomInventoryName() ? this.chemplant.getInventoryName() : I18n.format(this.chemplant.getInventoryName());
		
		this.fontRendererObj.drawString(name, 70 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int p = (int) (chemplant.power * 61 / chemplant.maxPower);
		drawTexturedModalRect(guiLeft + 152, guiTop + 79 - p, 176, 61 - p, 16, p);

		if(chemplant.maxProgress > 0) {
			int j = chemplant.progress * 70 / chemplant.maxProgress;
			drawTexturedModalRect(guiLeft + 62, guiTop + 126, 176, 61, j, 16);
		}

		for(int i = 0; i < 3; i++) {
			chemplant.inputTanks[i].renderTank(guiLeft + 8, guiTop + 52, this.zLevel, 16, 34);
			chemplant.outputTanks[i].renderTank(guiLeft + 80, guiTop + 52, this.zLevel, 16, 34);
		}
	}
}
