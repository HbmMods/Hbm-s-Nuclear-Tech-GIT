package com.hbm.inventory.gui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerHeaterHeatex;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityHeaterHeatex;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIHeaterHeatex extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_heatex.png");
	private TileEntityHeaterHeatex heater;
	private GuiTextField fieldCycles;
	private GuiTextField fieldDelay;

	public GUIHeaterHeatex(InventoryPlayer invPlayer, TileEntityHeaterHeatex tedf) {
		super(new ContainerHeaterHeatex(invPlayer, tedf));
		heater = tedf;
		
		this.xSize = 176;
		this.ySize = 204;
	}

	public void initGui() {

		super.initGui();

		Keyboard.enableRepeatEvents(true);
		this.fieldCycles = new GuiTextField(this.fontRendererObj, guiLeft + 74, guiTop + 21, 28, 10);
		initText(this.fieldCycles);
		this.fieldCycles.setText(String.valueOf(heater.amountToCool));
		
		this.fieldDelay = new GuiTextField(this.fontRendererObj, guiLeft + 74, guiTop + 39, 28, 10);
		initText(this.fieldDelay);
		this.fieldDelay.setText(String.valueOf(heater.tickDelay));
	}
	
	protected void initText(GuiTextField field) {
		field.setTextColor(0x00ff00);
		field.setDisabledTextColour(0x00ff00);
		field.setEnableBackgroundDrawing(false);
		field.setMaxStringLength(4);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		heater.tanks[0].renderTankInfo(this, mouseX, mouseY, guiLeft + 44, guiTop + 36, 16, 52);
		heater.tanks[1].renderTankInfo(this, mouseX, mouseY, guiLeft + 116, guiTop + 36, 16, 52);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = I18n.format(this.heater.getInventoryName());
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float interp, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		heater.tanks[0].renderTank(guiLeft + 44, guiTop + 88, this.zLevel, 16, 52);
		heater.tanks[1].renderTank(guiLeft + 116, guiTop + 88, this.zLevel, 16, 52);
	}

	@Override
	protected void keyTyped(char c, int i) {

		if(this.fieldCycles.textboxKeyTyped(c, i)) return;
		if(this.fieldDelay.textboxKeyTyped(c, i)) return;
		
		super.keyTyped(c, i);
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}
}
