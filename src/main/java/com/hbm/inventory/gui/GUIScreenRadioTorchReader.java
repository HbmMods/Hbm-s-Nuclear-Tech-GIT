package com.hbm.inventory.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.NBTControlPacket;
import com.hbm.tileentity.network.TileEntityRadioTorchReader;
import com.hbm.util.Compat;
import com.hbm.util.i18n.I18nUtil;

import api.hbm.redstoneoverradio.IRORValueProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;

public class GUIScreenRadioTorchReader extends GuiScreen {
	
	protected static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_rtty_reader.png");
	public TileEntityRadioTorchReader rtty;
	protected int xSize = 256;
	protected int ySize = 204;
	protected int guiLeft;
	protected int guiTop;

	protected GuiTextField[] frequencies;
	protected GuiTextField[] names;
	
	public GUIScreenRadioTorchReader(TileEntityRadioTorchReader rtty) {
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
		this.frequencies = new GuiTextField[8];
		this.names = new GuiTextField[8];
		
		for(int i = 0; i < 8; i++) {
			this.frequencies[i] = new GuiTextField(this.fontRendererObj, guiLeft + 25 + oX, guiTop + 53 + i * 18 + oY, 72 - oX * 2, 14);
			this.frequencies[i].setTextColor(0x00ff00);
			this.frequencies[i].setDisabledTextColour(0x00ff00);
			this.frequencies[i].setEnableBackgroundDrawing(false);
			this.frequencies[i].setMaxStringLength(15);
			this.frequencies[i].setText(rtty.channels[i] == null ? "" : rtty.channels[i]);

			this.names[i] = new GuiTextField(this.fontRendererObj, guiLeft + 119 + oX, guiTop + 53 + i * 18 + oY, 126 - oX * 2, 14);
			this.names[i].setTextColor(0x00ff00);
			this.names[i].setDisabledTextColour(0x00ff00);
			this.names[i].setEnableBackgroundDrawing(false);
			this.names[i].setMaxStringLength(25);
			this.names[i].setText(rtty.names[i] == null ? "" : rtty.names[i]);
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
		String name = I18nUtil.resolveKey("container.rttyReader");
		this.fontRendererObj.drawString(name, this.guiLeft + this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, this.guiTop + 6, 4210752);

		if(guiLeft + 173 <= x && guiLeft + 173 + 18 > x && guiTop + 17 < y && guiTop + 17 + 18 >= y) {
			func_146283_a(Arrays.asList(new String[] { rtty.polling ? "Polling" : "State Change" }), x, y);
		}
		if(guiLeft + 209 <= x && guiLeft + 209 + 18 > x && guiTop + 17 < y && guiTop + 17 + 18 >= y) {
			func_146283_a(Arrays.asList(new String[] { "Save Settings" }), x, y);
		}
		if(guiLeft + 29 <= x && guiLeft + 29 + 18 > x && guiTop + 17 < y && guiTop + 17 + 18 >= y) {
			ForgeDirection dir = ForgeDirection.getOrientation(rtty.getBlockMetadata()).getOpposite();
			TileEntity tile = Compat.getTileStandard(rtty.getWorldObj(), rtty.xCoord + dir.offsetX, rtty.yCoord + dir.offsetY, rtty.zCoord + dir.offsetZ);
			if(tile instanceof IRORValueProvider) {
				IRORValueProvider prov = (IRORValueProvider) tile;
				String[] info = prov.getFunctionInfo();
				List<String> lines = new ArrayList();
				lines.add("Readable values:");
				for(String s : info) {
					if(s.startsWith(IRORValueProvider.PREFIX_VALUE))
					lines.add(EnumChatFormatting.LIGHT_PURPLE + s.substring(4));
				}
				func_146283_a(lines, x, y);
			}
		}
	}

	private void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		if(rtty.polling) drawTexturedModalRect(guiLeft + 173, guiTop + 17, 0, 204, 18, 18);

		for(GuiTextField field : frequencies) field.drawTextBox();
		for(GuiTextField field : names) field.drawTextBox();
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);

		for(GuiTextField field : frequencies) field.mouseClicked(x, y, i);
		for(GuiTextField field : names) field.mouseClicked(x, y, i);
		
		if(guiLeft + 173 <= x && guiLeft + 173 + 18 > x && guiTop + 17 < y && guiTop + 17 + 18 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("p", !rtty.polling);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, rtty.xCoord, rtty.yCoord, rtty.zCoord));
		}
		
		if(guiLeft + 209 <= x && guiLeft + 209 + 18 > x && guiTop + 17 < y && guiTop + 17 + 18 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			for(int j = 0; j < 8; j++) data.setString("c" + j, this.frequencies[j].getText());
			for(int j = 0; j < 8; j++) data.setString("n" + j, this.names[j].getText());
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, rtty.xCoord, rtty.yCoord, rtty.zCoord));
		}
	}

	@Override
	protected void keyTyped(char c, int i) {

		for(GuiTextField field : frequencies) if(field.textboxKeyTyped(c, i)) return;
		for(GuiTextField field : names) if(field.textboxKeyTyped(c, i)) return;
		
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
