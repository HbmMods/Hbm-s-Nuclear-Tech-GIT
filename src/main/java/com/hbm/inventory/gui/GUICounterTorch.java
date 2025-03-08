package com.hbm.inventory.gui;

import java.util.Arrays;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerCounterTorch;
import com.hbm.lib.RefStrings;
import com.hbm.module.ModulePatternMatcher;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.NBTControlPacket;
import com.hbm.tileentity.network.TileEntityRadioTorchCounter;
import com.hbm.util.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GUICounterTorch extends GuiInfoContainer {

	protected static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_rtty_counter.png");
	
	protected TileEntityRadioTorchCounter counter;
	protected GuiTextField[] frequency;

	public GUICounterTorch(InventoryPlayer invPlayer, TileEntityRadioTorchCounter counter) {
		super(new ContainerCounterTorch(invPlayer, counter));
		this.counter = counter;
		
		this.xSize = 218;
		this.ySize = 238;
	}

	@Override
	public void initGui() {
		super.initGui();

		Keyboard.enableRepeatEvents(true);
		
		this.frequency = new GuiTextField[3];
		
		for(int i = 0; i < 3; i++) {

			this.frequency[i] = new GuiTextField(this.fontRendererObj, guiLeft + 29, guiTop + 21 + 44 * i, 86, 14);
			this.frequency[i].setTextColor(0x00ff00);
			this.frequency[i].setDisabledTextColour(0x00ff00);
			this.frequency[i].setEnableBackgroundDrawing(false);
			this.frequency[i].setMaxStringLength(10);
			this.frequency[i].setText(counter.channel[i] == null ? "" : counter.channel[i]);
		}
	}
	
	@Override
	public void drawScreen(int x, int y, float interp) {
		super.drawScreen(x, y, interp);

		if(guiLeft + 193 <= x && guiLeft + 193 + 18 > x && guiTop + 8 < y && guiTop + 8 + 18 >= y) {
			func_146283_a(Arrays.asList(new String[] { counter.polling ? "Polling" : "State Change" }), x, y);
		}
		if(guiLeft + 193 <= x && guiLeft + 193 + 18 > x && guiTop + 30 < y && guiTop + 30 + 18 >= y) {
			func_146283_a(Arrays.asList(new String[] { "Save Settings" }), x, y);
		}

		if(this.mc.thePlayer.inventory.getItemStack() == null) {
			for(int i = 0; i < 3; ++i) {
				Slot slot = (Slot) this.inventorySlots.inventorySlots.get(i);
	
				if(this.isMouseOverSlot(slot, x, y) && counter.matcher.modes[i] != null) {
					this.func_146283_a(Arrays.asList(new String[] { EnumChatFormatting.RED + "Right click to change", ModulePatternMatcher.getLabel(counter.matcher.modes[i]) }), x, y - 30);
				}
			}
		}
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);
		
		for(int j = 0; j < 3; j++) this.frequency[j].mouseClicked(x, y, i);

		if(guiLeft + 193 <= x && guiLeft + 193 + 18 > x && guiTop + 8 < y && guiTop + 8 + 18 >= y) {

			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("polling", true);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, counter.xCoord, counter.yCoord, counter.zCoord));
		}
		
		if(guiLeft + 193 <= x && guiLeft + 193 + 18 > x && guiTop + 30 < y && guiTop + 30 + 18 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			
			for(int j = 0; j < 3; j++) data.setString("c" + j, this.frequency[j].getText());
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, counter.xCoord, counter.yCoord, counter.zCoord));
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		String name = I18nUtil.resolveKey(this.counter.getInventoryName());
		this.fontRendererObj.drawString(name, 184 / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 16, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float interp, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		if(counter.polling) {
			drawTexturedModalRect(guiLeft + 193, guiTop + 8, 218, 0, 18, 18);
		}
		
		for(int i = 0; i < 3; i++) this.frequency[i].drawTextBox();
	}

	@Override
	protected void keyTyped(char c, int i) {
		
		for(int j = 0; j < 3; j++) if(this.frequency[j].textboxKeyTyped(c, i)) return;
		
		super.keyTyped(c, i);
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		Keyboard.enableRepeatEvents(false);
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}
