package com.hbm.inventory.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineBattery;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.storage.TileEntityMachineBattery;
import com.hbm.util.BobMathUtil;
import com.hbm.util.I18nUtil;

public class GUIMachineBattery extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/storage/gui_battery.png");
	private TileEntityMachineBattery battery;

	public GUIMachineBattery(InventoryPlayer invPlayer, TileEntityMachineBattery tedf) {
		super(new ContainerMachineBattery(invPlayer, tedf));
		battery = tedf;

		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 62, guiTop + 69 - 52, 52, 52, battery.power, battery.getMaxPower());

		String deltaText = BobMathUtil.getShortNumber(Math.abs(battery.delta)) + "HE/s";

		if(battery.delta > 0) deltaText = EnumChatFormatting.GREEN + "+" + deltaText;
		else if(battery.delta < 0) deltaText = EnumChatFormatting.RED + "-" + deltaText;
		else deltaText = EnumChatFormatting.YELLOW + "+" + deltaText;

		String[] info = { BobMathUtil.getShortNumber(battery.power) + "/" + BobMathUtil.getShortNumber(battery.getMaxPower()) + "HE", deltaText };

		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 62, guiTop + 69 - 52, 52, 52, mouseX, mouseY, info);
		
		String lang = null;
		switch(battery.priority) {
		case LOW: lang = "low"; break;
		case NORMAL: lang = "normal"; break;
		case HIGH: lang = "high"; break;
		}
		
		List<String> priority = new ArrayList();
		priority.add(I18nUtil.resolveKey("battery.priority." + lang));
		priority.add(I18nUtil.resolveKey("battery.priority.recommended"));
		String[] desc = I18nUtil.resolveKeyArray("battery.priority." + lang + ".desc");
		for(String s : desc) priority.add(s);
		
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 152, guiTop + 35, 16, 16, mouseX, mouseY, priority);
	}

	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);

		if(guiLeft + 133 <= x && guiLeft + 133 + 18 > x && guiTop + 16 < y && guiTop + 16 + 18 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(battery.xCoord, battery.yCoord, battery.zCoord, 0, 0));
		}

		if(guiLeft + 133 <= x && guiLeft + 133 + 18 > x && guiTop + 52 < y && guiTop + 52 + 18 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(battery.xCoord, battery.yCoord, battery.zCoord, 0, 1));
		}

		if(guiLeft + 152 <= x && guiLeft + 152 + 16 > x && guiTop + 35 < y && guiTop + 35 + 16 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(battery.xCoord, battery.yCoord, battery.zCoord, 0, 2));
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.battery.hasCustomInventoryName() ? this.battery.getInventoryName() : I18n.format(this.battery.getInventoryName());
		name += (" (" + this.battery.power + " HE)");

		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		if(battery.power > 0) {
			int i = (int) battery.getPowerRemainingScaled(52);
			drawTexturedModalRect(guiLeft + 62, guiTop + 69 - i, 176, 52 - i, 52, i);
		}

		int i = battery.redLow;
		drawTexturedModalRect(guiLeft + 133, guiTop + 16, 176, 52 + i * 18, 18, 18);

		int j = battery.redHigh;
		drawTexturedModalRect(guiLeft + 133, guiTop + 52, 176, 52 + j * 18, 18, 18);
		
		drawTexturedModalRect(guiLeft + 152, guiTop + 35, 194, 52 + battery.priority.ordinal() * 16, 16, 16);
	}
}
