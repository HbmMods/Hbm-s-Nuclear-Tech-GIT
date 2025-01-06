package com.hbm.inventory.gui;

import java.util.Arrays;

import org.apache.commons.lang3.math.NumberUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerHeaterHeatex;
import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.NBTControlPacket;
import com.hbm.tileentity.machine.TileEntityHeaterHeatex;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
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
		this.fieldCycles = new GuiTextField(this.fontRendererObj, guiLeft + 73, guiTop + 31, 30, 10);
		initText(this.fieldCycles);
		this.fieldCycles.setText(String.valueOf(heater.amountToCool));
		
		this.fieldDelay = new GuiTextField(this.fontRendererObj, guiLeft + 73, guiTop + 49, 30, 10);
		initText(this.fieldDelay);
		this.fieldDelay.setText(String.valueOf(heater.tickDelay));
	}
	
	protected void initText(GuiTextField field) {
		field.setTextColor(0x00ff00);
		field.setDisabledTextColour(0x00ff00);
		field.setEnableBackgroundDrawing(false);
		field.setMaxStringLength(5);
	}

	@Override
	public void drawScreen(int x, int y, float f) {
		super.drawScreen(x, y, f);

		heater.tanks[0].renderTankInfo(this, x, y, guiLeft + 44, guiTop + 36, 16, 52);
		heater.tanks[1].renderTankInfo(this, x, y, guiLeft + 116, guiTop + 36, 16, 52);

		if(guiLeft + 70 <= x && guiLeft + 70 + 36 > x && guiTop + 26 < y && guiTop + 26 + 18 >= y) {
			func_146283_a(Arrays.asList(new String[] { "Amount per cycle" }), x, y);
		}
		if(guiLeft + 70 <= x && guiLeft + 70 + 36 > x && guiTop + 44 < y && guiTop + 44 + 18 >= y) {
			func_146283_a(Arrays.asList(new String[] { "Cycle tick delay" }), x, y);
		}
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

		this.fieldCycles.drawTextBox();
		this.fieldDelay.drawTextBox();
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);

		this.fieldCycles.mouseClicked(x, y, i);
		this.fieldDelay.mouseClicked(x, y, i);
	}

	@Override
	protected void keyTyped(char c, int i) {

		if(this.fieldCycles.textboxKeyTyped(c, i)) {
			int cyc = Math.max(NumberUtils.toInt(this.fieldCycles.getText()), 1);
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("toCool", cyc);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, heater.xCoord, heater.yCoord, heater.zCoord));
			return;
		}
		if(this.fieldDelay.textboxKeyTyped(c, i)) {
			int delay = Math.max(NumberUtils.toInt(this.fieldDelay.getText()), 1);
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("delay", delay);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, heater.xCoord, heater.yCoord, heater.zCoord));
			return;
		}
		
		super.keyTyped(c, i);
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}
}
