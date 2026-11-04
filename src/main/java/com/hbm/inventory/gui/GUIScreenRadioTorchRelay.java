package com.hbm.inventory.gui;

import java.util.Arrays;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.NBTControlPacket;
import com.hbm.tileentity.network.TileEntityRadioTorchRelay;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class GUIScreenRadioTorchRelay extends GuiScreen {
	
	protected static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_rtty_relay.png");
	public TileEntityRadioTorchRelay rtty;
	protected int xSize = 256;
	protected int ySize = 78;
	protected int guiLeft;
	protected int guiTop;
	
	protected GuiTextField frequency;
	protected GuiTextField expression;
	
	public GUIScreenRadioTorchRelay(TileEntityRadioTorchRelay rtty) {
		this.rtty = rtty;
	}

	@Override
	public void initGui() {
		super.initGui();
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;

		Keyboard.enableRepeatEvents(true);
		
		int oX = 4;
		int oY = 4;

		this.frequency = new GuiTextField(this.fontRendererObj, guiLeft + 25 + oX, guiTop + 17 + oY, 90 - oX * 2, 14);
		this.frequency.setTextColor(0x00ff00);
		this.frequency.setDisabledTextColour(0x00ff00);
		this.frequency.setEnableBackgroundDrawing(false);
		this.frequency.setMaxStringLength(GUIScreenRadioTorch.MAX_CHAN_LENGTH);
		this.frequency.setText(rtty.channel == null ? "" : rtty.channel);

		this.expression = new GuiTextField(this.fontRendererObj, guiLeft + 7 + oX, guiTop + 53 + oY, 220 - oX * 2, 14);
		this.expression.setTextColor(0x00ff00);
		this.expression.setDisabledTextColour(0x00ff00);
		this.expression.setEnableBackgroundDrawing(false);
		this.expression.setMaxStringLength(100);
		this.expression.setText(rtty.expression == null ? "" : rtty.expression);
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
		String name = I18nUtil.resolveKey("container.rttyRelay");
		this.fontRendererObj.drawString(name, this.guiLeft + this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, this.guiTop + 6, 4210752);

		if(guiLeft + 173 <= x && guiLeft + 173 + 18 > x && guiTop + 17 < y && guiTop + 17 + 18 >= y) {
			func_146283_a(Arrays.asList(new String[] { rtty.polling ? "Polling" : "State Change" }), x, y);
		}
		if(guiLeft + 209 <= x && guiLeft + 209 + 18 > x && guiTop + 17 < y && guiTop + 17 + 18 >= y) {
			func_146283_a(Arrays.asList(new String[] { "Save Settings" }), x, y);
		}
	}

	private void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		if(rtty.polling) drawTexturedModalRect(guiLeft + 173, guiTop + 17, 0, 78, 18, 18);

		frequency.drawTextBox();
		expression.drawTextBox();
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);

		frequency.mouseClicked(x, y, i);
		expression.mouseClicked(x, y, i);
		
		if(guiLeft + 173 <= x && guiLeft + 173 + 18 > x && guiTop + 17 < y && guiTop + 17 + 18 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("p", !rtty.polling);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, rtty.xCoord, rtty.yCoord, rtty.zCoord));
		}
		
		if(guiLeft + 209 <= x && guiLeft + 209 + 18 > x && guiTop + 17 < y && guiTop + 17 + 18 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setString("c", this.frequency.getText());
			data.setString("e", this.expression.getText());
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, rtty.xCoord, rtty.yCoord, rtty.zCoord));
		}
	}

	@Override
	protected void keyTyped(char c, int i) {

		if(frequency.textboxKeyTyped(c, i)) return;
		if(expression.textboxKeyTyped(c, i)) return;
		
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
