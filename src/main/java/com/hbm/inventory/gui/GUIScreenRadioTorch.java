package com.hbm.inventory.gui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.tileentity.network.TileEntityRadioTorchBase;
import com.hbm.tileentity.network.TileEntityRadioTorchSender;
import com.hbm.util.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.ResourceLocation;

public class GUIScreenRadioTorch extends GuiScreen {

	protected ResourceLocation texture;
    protected static final ResourceLocation textureSender = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_rtty_sender.png");
    protected static final ResourceLocation textureReceiver = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_rtty_receiver.png");
	protected TileEntityRadioTorchBase radio;
	protected String title = "";
	protected int xSize = 256;
	protected int ySize = 204;
	protected int guiLeft;
	protected int guiTop;
	protected GuiTextField frequency;
	protected GuiTextField[] remap;
	
	public GUIScreenRadioTorch(TileEntityRadioTorchBase radio) {
		this.radio = radio;
		
		if(radio instanceof TileEntityRadioTorchSender) {
			this.texture = textureSender;
			this.title = "container.rttySender";
		} else {
			this.texture = textureReceiver;
			this.title = "container.rttyReceiver";
		}
	}

	@Override
	public void initGui() {
		super.initGui();
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;

		Keyboard.enableRepeatEvents(true);
		
		int oX = 2;
		int oY = 2;
		int in = radio instanceof TileEntityRadioTorchSender ? 18 : 0;

		this.frequency = new GuiTextField(this.fontRendererObj, guiLeft + 25 + oX, guiTop + 17 + oY, 90, 14);
		this.frequency.setTextColor(0x00ff00);
		this.frequency.setDisabledTextColour(0x00ff00);
		this.frequency.setEnableBackgroundDrawing(false);
		this.frequency.setMaxStringLength(25);
		this.frequency.setText(radio.channel == null ? "" : radio.channel);
		
		this.remap = new GuiTextField[16];
		
		for(int i = 0; i < 16; i++) {
			this.remap[i] = new GuiTextField(this.fontRendererObj, guiLeft + 7 + (130 * (i / 8)) + oX + in, guiTop + 53 + (18 * (i % 8)) + oY, 50, 14);
			this.remap[i].setTextColor(0x00ff00);
			this.remap[i].setDisabledTextColour(0x00ff00);
			this.remap[i].setEnableBackgroundDrawing(false);
			this.remap[i].setMaxStringLength(25);
			this.remap[i].setText(radio.mapping[i] == null ? "" : radio.mapping[i]);
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		this.drawDefaultBackground();
		this.drawGuiContainerBackgroundLayer(f, mouseX, mouseY);
		GL11.glDisable(GL11.GL_LIGHTING);
		this.drawGuiContainerForegroundLayer(mouseX, mouseY);
		GL11.glEnable(GL11.GL_LIGHTING);
	}


	private void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String name = I18nUtil.resolveKey(this.title);
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
	}


	private void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		
		if(radio.customMap) {
			drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
			for(int j = 0; j < 16; j++) {
				this.remap[j].drawTextBox();
			}
		} else {
			drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, 35);
			drawTexturedModalRect(guiLeft, guiTop + 35, 0, 197, xSize, 7);
		}
		
		this.frequency.drawTextBox();
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);
		
		this.frequency.mouseClicked(x, y, i);
		
		if(radio.customMap) {
			for(int j = 0; j < 16; j++) this.remap[j].mouseClicked(x, y, i);
		}
	}

	@Override
	protected void keyTyped(char c, int i) {
		
		if(this.frequency.textboxKeyTyped(c, i))
			return;

		if(radio.customMap) {
			for(int j = 0; j < 16; j++) if(this.remap[j].textboxKeyTyped(c, i)) return;
		}
		
		super.keyTyped(c, i);
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}
}
