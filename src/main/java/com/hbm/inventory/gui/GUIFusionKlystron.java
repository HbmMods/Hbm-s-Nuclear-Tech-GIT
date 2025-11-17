package com.hbm.inventory.gui;

import org.apache.commons.lang3.math.NumberUtils;
import org.lwjgl.input.Keyboard;

import com.hbm.inventory.container.ContainerFusionKlystron;
import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.NBTControlPacket;
import com.hbm.render.util.GaugeUtil;
import com.hbm.tileentity.machine.fusion.TileEntityFusionKlystron;
import com.hbm.tileentity.machine.fusion.TileEntityFusionTorus;
import com.hbm.util.BobMathUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class GUIFusionKlystron extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/reactors/gui_fusion_klystron.png");
	public TileEntityFusionKlystron klystron;
	private GuiTextField field;
	
	public GUIFusionKlystron(InventoryPlayer invPlayer, TileEntityFusionKlystron klystron) {
		super(new ContainerFusionKlystron(invPlayer, klystron));
		this.klystron = klystron;
		
		this.xSize = 194;
		this.ySize = 200;
	}

	@Override
	public void initGui() {
		super.initGui();
		
		Keyboard.enableRepeatEvents(true);
		this.field = new GuiTextField(this.fontRendererObj, guiLeft + 84, guiTop + 22, 102, 12);
		this.field.setTextColor(0x00FF00);
		this.field.setDisabledTextColour(0x00FF00);
		this.field.setEnableBackgroundDrawing(false);
		this.field.setText(klystron.outputTarget + "");
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float interp) {
		super.drawScreen(mouseX, mouseY, interp);
		
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 8, guiTop + 18, 16, 52, klystron.power, klystron.getMaxPower());
		
		drawCustomInfoStat(mouseX, mouseY, guiLeft + 43, guiTop + 71, 18, 18, mouseX, mouseY, BobMathUtil.getShortNumber(klystron.output) + "KyU / " + BobMathUtil.getShortNumber(klystron.outputTarget) + "KyU");
		klystron.compair.renderTankInfo(this, mouseX, mouseY, guiLeft + 76, guiTop + 71, 18, 18);
		drawCustomInfoStat(mouseX, mouseY, guiLeft + 115, guiTop + 71, 18, 18, mouseX, mouseY, BobMathUtil.getShortNumber(klystron.output) + "HE / " + BobMathUtil.getShortNumber(klystron.outputTarget) + "HE");

	}

	@Override
	protected void mouseClicked(int i, int j, int button) {
		super.mouseClicked(i, j, button);
		
		this.field.mouseClicked(i, j, button);
	}
	
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.klystron.hasCustomInventoryName() ? this.klystron.getInventoryName() : I18n.format(this.klystron.getInventoryName());
		this.fontRendererObj.drawString(name, 115 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 35, this.ySize - 93, 4210752);
		
		String result = "= " + BobMathUtil.getShortNumber(klystron.outputTarget) + "KyU";
		if(klystron.outputTarget == klystron.MAX_OUTPUT) result += " (max)";
		this.fontRendererObj.drawString(result, 183 - this.fontRendererObj.getStringWidth(result), 40, 0x00FF00);
	}

	protected void drawGuiContainerBackgroundLayer(float interp, int x, int y) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		int p = (int) (klystron.power * 52 / klystron.getMaxPower());
		drawTexturedModalRect(guiLeft + 8, guiTop + 70 - p, 194, 52 - p, 16, p);
		
		double outputGauge = klystron.outputTarget <= 0 ? 0 : ((double) klystron.output / (double) klystron.outputTarget);
		double airGauge = (double) klystron.compair.getFill() / (double) klystron.compair.getMaxFill();
		double powerGauge = TileEntityFusionTorus.getSpeedScaled(klystron.maxPower, klystron.power);

		// power LED
		if(powerGauge >= 0.5 && klystron.output > 0) drawTexturedModalRect(guiLeft + 160, guiTop + 71, 210, 8, 8, 8);
		else if(powerGauge > 0) drawTexturedModalRect(guiLeft + 160, guiTop + 71, 210, 0, 8, 8);
		// cooling LED
		if(airGauge >= 0.5 && klystron.output > 0) drawTexturedModalRect(guiLeft + 170, guiTop + 71, 210, 8, 8, 8);
		else if(airGauge > 0) drawTexturedModalRect(guiLeft + 170, guiTop + 71, 210, 0, 8, 8);
		// action LED
		if(klystron.output >= klystron.outputTarget && klystron.output > 0) drawTexturedModalRect(guiLeft + 180, guiTop + 71, 210, 8, 8, 8);
		else if(klystron.output > 0) drawTexturedModalRect(guiLeft + 180, guiTop + 71, 210, 0, 8, 8);
		
		// output energy
		GaugeUtil.drawSmoothGauge(guiLeft + 52, guiTop + 80, this.zLevel, outputGauge, 5, 2, 1, 0xA00000);
		// air cooling
		GaugeUtil.drawSmoothGauge(guiLeft + 88, guiTop + 80, this.zLevel, airGauge, 5, 2, 1, 0xA00000);
		// power consumption
		GaugeUtil.drawSmoothGauge(guiLeft + 124, guiTop + 80, this.zLevel, powerGauge, 5, 2, 1, 0xA00000);
		
		this.field.drawTextBox();
	}

	@Override
	protected void keyTyped(char c, int key) {

		if(this.field.textboxKeyTyped(c, key)) {
			
			String text = this.field.getText();
			if(text.startsWith("0")) this.field.setText(text.substring(1));
			if(this.field.getText().isEmpty()) this.field.setText("0");
			if(NumberUtils.isDigits(this.field.getText())) {
				long num = NumberUtils.toLong(this.field.getText());
				NBTTagCompound data = new NBTTagCompound();
				data.setLong("amount", num);
				PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, klystron.xCoord, klystron.yCoord, klystron.zCoord));
			}
			
			return;
		}
		
		super.keyTyped(c, key);
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		Keyboard.enableRepeatEvents(false);
	}
}
