package com.hbm.inventory.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerBatterySocket;
import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.NBTControlPacket;
import com.hbm.tileentity.machine.storage.TileEntityBatterySocket;
import com.hbm.util.BobMathUtil;
import com.hbm.util.i18n.I18nUtil;

import api.hbm.energymk2.IBatteryItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GUIBatterySocket extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/storage/gui_battery_socket.png");
	private TileEntityBatterySocket battery;

	public GUIBatterySocket(InventoryPlayer invPlayer, TileEntityBatterySocket tedf) {
		super(new ContainerBatterySocket(invPlayer, tedf));
		battery = tedf;

		this.xSize = 176;
		this.ySize = 181;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		if(battery.slots[0] != null && battery.slots[0].getItem() instanceof IBatteryItem) {
			//this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 62, guiTop + 69 - 52, 52, 52, battery.power, battery.getMaxPower());
			
			IBatteryItem item = (IBatteryItem) battery.slots[0].getItem();

			String deltaText = BobMathUtil.getShortNumber(Math.abs(battery.delta)) + "HE/s";

			if(battery.delta > 0) deltaText = EnumChatFormatting.GREEN + "+" + deltaText;
			else if(battery.delta < 0) deltaText = EnumChatFormatting.RED + "-" + deltaText;
			else deltaText = EnumChatFormatting.YELLOW + "+" + deltaText;

			String[] info = { BobMathUtil.getShortNumber(item.getCharge(battery.slots[0])) + "/" + BobMathUtil.getShortNumber(item.getMaxCharge(battery.slots[0])) + "HE", deltaText };

			this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 62, guiTop + 69 - 52, 34, 52, mouseX, mouseY, info);
		}
		
		String lang = null;
		switch(battery.priority) {
			case LOW: lang = "low"; break;
			case HIGH: lang = "high"; break;
			default: lang = "normal"; break;
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
		
		NBTTagCompound data = new NBTTagCompound();
		
		if(this.checkClick(x, y, 106, 16, 18, 18)) { this.click(); data.setBoolean("low", true); }
		if(this.checkClick(x, y, 106, 52, 18, 18)) { this.click(); data.setBoolean("high", true); }
		if(this.checkClick(x, y, 125, 35, 16, 16)) { this.click(); data.setBoolean("priority", true); }
		
		if(!data.hasNoTags()) PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, battery.xCoord, battery.yCoord, battery.zCoord));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.battery.hasCustomInventoryName() ? this.battery.getInventoryName() : I18n.format(this.battery.getInventoryName());
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		if(battery.slots[0] != null && battery.slots[0].getItem() instanceof IBatteryItem) {
			IBatteryItem item = (IBatteryItem) battery.slots[0].getItem();
			long power = item.getCharge(battery.slots[0]);
			long maxPower = item.getMaxCharge(battery.slots[0]);
			
			if(power > Long.MAX_VALUE / 100) {
				power /= 100;
				maxPower /= 100;
			}
			if(maxPower <= 1) maxPower = 1;
			int p = (int) (power * 52 / maxPower); // won't work then flying too close to the sun (the limits of the LONG data type)
			drawTexturedModalRect(guiLeft + 62, guiTop + 69 - p, 176, 52 - p, 34, p);
		}

		drawTexturedModalRect(guiLeft + 106, guiTop + 16, 176, 52 + battery.redLow * 18, 18, 18);
		drawTexturedModalRect(guiLeft + 106, guiTop + 52, 176, 52 + battery.redHigh * 18, 18, 18);
		drawTexturedModalRect(guiLeft + 125, guiTop + 35, 194, 52 + battery.priority.ordinal() * 16 - 16, 16, 16);
	}
}
