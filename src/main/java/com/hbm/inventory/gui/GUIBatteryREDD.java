package com.hbm.inventory.gui;

import java.math.BigInteger;
import java.util.Locale;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.hbm.inventory.container.ContainerBatteryREDD;
import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.NBTControlPacket;
import com.hbm.tileentity.machine.storage.TileEntityBatteryREDD;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GUIBatteryREDD extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/storage/gui_battery_redd.png");
	private TileEntityBatteryREDD battery;

	public GUIBatteryREDD(InventoryPlayer invPlayer, TileEntityBatteryREDD tedf) {
		super(new ContainerBatteryREDD(invPlayer, tedf));
		battery = tedf;

		this.xSize = 176;
		this.ySize = 181;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
	}

	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);
		
		NBTTagCompound data = new NBTTagCompound();
		
		if(this.checkClick(x, y, 133, 16, 18, 18)) { this.click(); data.setBoolean("low", true); }
		if(this.checkClick(x, y, 133, 52, 18, 18)) { this.click(); data.setBoolean("high", true); }
		if(this.checkClick(x, y, 152, 35, 16, 16)) { this.click(); data.setBoolean("priority", true); }
		
		if(!data.hasNoTags()) PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, battery.xCoord, battery.yCoord, battery.zCoord));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.battery.hasCustomInventoryName() ? this.battery.getInventoryName() : I18n.format(this.battery.getInventoryName());
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
		
		GL11.glPushMatrix();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glScaled(0.5, 0.5, 1);
		
		String label = String.format(Locale.US, "%,d", battery.power) + " HE";
		this.fontRendererObj.drawString(label, 242 - this.fontRendererObj.getStringWidth(label), 45, 0x00ff00);
		
		String deltaText = String.format(Locale.US, "%,d", battery.delta) + " HE/s";

		int comp = battery.delta.compareTo(BigInteger.ZERO);
		if(comp > 0) deltaText = EnumChatFormatting.GREEN + "+" + deltaText;
		else if(comp < 0) deltaText = EnumChatFormatting.RED + deltaText;
		else deltaText = EnumChatFormatting.YELLOW + "+" + deltaText;
		
		this.fontRendererObj.drawString(deltaText, 242 - this.fontRendererObj.getStringWidth(deltaText), 65, 0x00ff00);
		
		GL11.glPopMatrix();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		drawTexturedModalRect(guiLeft + 133, guiTop + 16, 176, 52 + battery.redLow * 18, 18, 18);
		drawTexturedModalRect(guiLeft + 133, guiTop + 52, 176, 52 + battery.redHigh * 18, 18, 18);
		drawTexturedModalRect(guiLeft + 152, guiTop + 35, 194, 52 + battery.priority.ordinal() * 16 - 16, 16, 16);
	}
}
