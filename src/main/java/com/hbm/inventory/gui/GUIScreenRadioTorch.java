package com.hbm.inventory.gui;

import java.util.Arrays;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.NBTControlPacket;
import com.hbm.tileentity.network.TileEntityRadioTorchBase;
import com.hbm.tileentity.network.TileEntityRadioTorchSender;
import com.hbm.util.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.nbt.NBTTagCompound;
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
		
		int oX = 4;
		int oY = 4;
		int in = radio instanceof TileEntityRadioTorchSender ? 18 : 0;

		this.frequency = new GuiTextField(this.fontRendererObj, guiLeft + 25 + oX, guiTop + 17 + oY, 90 - oX * 2, 14);
		this.frequency.setTextColor(0x00ff00);
		this.frequency.setDisabledTextColour(0x00ff00);
		this.frequency.setEnableBackgroundDrawing(false);
		this.frequency.setMaxStringLength(10);
		this.frequency.setText(radio.channel == null ? "" : radio.channel);
		
		this.remap = new GuiTextField[16];
		
		for(int i = 0; i < 16; i++) {
			this.remap[i] = new GuiTextField(this.fontRendererObj, guiLeft + 7 + (130 * (i / 8)) + oX + in, guiTop + 53 + (18 * (i % 8)) + oY, 90 - oX * 2, 14);
			this.remap[i].setTextColor(0x00ff00);
			this.remap[i].setDisabledTextColour(0x00ff00);
			this.remap[i].setEnableBackgroundDrawing(false);
			this.remap[i].setMaxStringLength(15);
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

	private void drawGuiContainerForegroundLayer(int x, int y) {
		String name = I18nUtil.resolveKey(this.title);
		this.fontRendererObj.drawString(name, this.guiLeft + this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, this.guiTop + 6, 4210752);

		if(guiLeft + 137 <= x && guiLeft + 137 + 18 > x && guiTop + 17 < y && guiTop + 17 + 18 >= y) {
			func_146283_a(Arrays.asList(new String[] { radio.customMap ? "Custom Mapping" : "Redstone Passthrough" }), x, y);
		}
		if(guiLeft + 173 <= x && guiLeft + 173 + 18 > x && guiTop + 17 < y && guiTop + 17 + 18 >= y) {
			func_146283_a(Arrays.asList(new String[] { radio.polling ? "Polling" : "State Change" }), x, y);
		}
		if(guiLeft + 209 <= x && guiLeft + 209 + 18 > x && guiTop + 17 < y && guiTop + 17 + 18 >= y) {
			func_146283_a(Arrays.asList(new String[] { "Save Settings" }), x, y);
		}
	}

	private void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		
		if(radio.customMap) {
			drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
			drawTexturedModalRect(guiLeft + 137, guiTop + 17, 0, 204, 18, 18);
			if(radio.polling) drawTexturedModalRect(guiLeft + 173, guiTop + 17, 0, 222, 18, 18);
			for(int j = 0; j < 16; j++) {
				this.remap[j].drawTextBox();
			}
		} else {
			drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, 35);
			drawTexturedModalRect(guiLeft, guiTop + 35, 0, 197, xSize, 7);
			if(radio.polling) drawTexturedModalRect(guiLeft + 173, guiTop + 17, 0, 222, 18, 18);
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
		
		if(guiLeft + 137 <= x && guiLeft + 137 + 18 > x && guiTop + 17 < y && guiTop + 17 + 18 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("m", !radio.customMap);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, radio.xCoord, radio.yCoord, radio.zCoord));
		}
		
		if(guiLeft + 173 <= x && guiLeft + 173 + 18 > x && guiTop + 17 < y && guiTop + 17 + 18 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("p", !radio.polling);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, radio.xCoord, radio.yCoord, radio.zCoord));
		}
		
		if(guiLeft + 209 <= x && guiLeft + 209 + 18 > x && guiTop + 17 < y && guiTop + 17 + 18 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setString("c", this.frequency.getText());
			for(int j = 0; j < 16; j++) data.setString("m" + j, this.remap[j].getText());
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, radio.xCoord, radio.yCoord, radio.zCoord));
		}
	}

	@Override
	protected void keyTyped(char c, int i) {
		
		if(this.frequency.textboxKeyTyped(c, i))
			return;

		if(radio.customMap) {
			for(int j = 0; j < 16; j++) if(this.remap[j].textboxKeyTyped(c, i)) return;
		}
		
		if(i == 1 || i == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
			this.mc.thePlayer.closeScreen();
			this.mc.setIngameFocus();
		}
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}
