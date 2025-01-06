package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineRadGen;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineRadGen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineRadGen extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/reactors/gui_radgen.png");
	private TileEntityMachineRadGen radgen;

	public GUIMachineRadGen(InventoryPlayer invPlayer, TileEntityMachineRadGen tedf) {
		super(new ContainerMachineRadGen(invPlayer, tedf));
		radgen = tedf;
		
		this.xSize = 176;
		this.ySize = 184;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 64, guiTop + 83, 48, 4, radgen.power, radgen.maxPower);
		
		for(int i = 0; i < 12; i++) {
			
			if(radgen.maxProgress[i] <= 0)
				continue;
			
			this.drawCustomInfo(this, mouseX, mouseY, guiLeft + 65, guiTop + 18 + i * 5, 46, 5, new String[] {
					"Slot " + (i + 1) + ":",
					radgen.production[i] + "HE/t for",
					(radgen.maxProgress[i] - radgen.progress[i]) + " ticks (" + ((radgen.maxProgress[i] - radgen.progress[i]) * 100 / radgen.maxProgress[i]) + "%)"
			});
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.radgen.hasCustomInventoryName() ? this.radgen.getInventoryName() : I18n.format(this.radgen.getInventoryName());
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		for(int i = 0; i < 12; i++) {
			
			if(radgen.maxProgress[i] <= 0)
				continue;
			
			int j = radgen.progress[i] * 44 / radgen.maxProgress[i];
			drawTexturedModalRect(guiLeft + 66, guiTop + 19 + i * 5, 176, 0, j, 3);
		}
		
		int j = (int)(radgen.power * 48 / radgen.maxPower);
		drawTexturedModalRect(guiLeft + 64, guiTop + 83, 176, 3, j, 4);
	}
}
