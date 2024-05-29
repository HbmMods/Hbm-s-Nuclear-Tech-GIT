package com.hbm.inventory.gui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerTransporterRocket;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityTransporterBase;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUITransporterRocket extends GuiContainer {
	
	protected static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_transporter.png");

	private GuiTextField transporterName;

	private TileEntityTransporterBase transporter;

	public GUITransporterRocket(InventoryPlayer invPlayer, TileEntityTransporterBase transporter) {
		super(new ContainerTransporterRocket(invPlayer, transporter));

		this.transporter = transporter;
	
		xSize = 230;
		ySize = 236;
	}

	@Override
	public void initGui() {
		super.initGui();
		
		Keyboard.enableRepeatEvents(true);
		transporterName = new GuiTextField(this.fontRendererObj, guiLeft + 28, guiTop + 12, 122, 12);
		transporterName.setTextColor(0x00ff00);
		transporterName.setDisabledTextColour(0x00ff00);
		transporterName.setEnableBackgroundDrawing(false);
		transporterName.setText(transporter.getTransporterName());
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		transporterName.drawTextBox();
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int button) {
		super.mouseClicked(mouseX, mouseY, button);
		transporterName.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	protected void keyTyped(char c, int key) {
		if(transporterName.textboxKeyTyped(c, key)) {
			setName();
		} else {
			super.keyTyped(c, key);
		}

		if(key == 1) {
			mc.thePlayer.closeScreen();
		}
	}

	private void setName() {
		transporter.setTransporterName(transporterName.getText());
	}

}
