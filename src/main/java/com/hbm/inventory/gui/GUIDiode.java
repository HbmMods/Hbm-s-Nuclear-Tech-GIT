package com.hbm.inventory.gui;

import org.lwjgl.input.Keyboard;

import com.hbm.blocks.network.CableDiode.TileEntityDiode;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.NBTControlPacket;
import com.hbm.util.EnumUtil;

import api.hbm.energymk2.IEnergyReceiverMK2.ConnectionPriority;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GUIDiode extends GuiScreen {

	protected final TileEntityDiode diode;

	private GuiTextField textThroughput;
	private GuiButton buttonPriority;
	private int priority;

	public GUIDiode(TileEntityDiode diode) {
		this.diode = diode;
		this.priority = diode.priority.ordinal();
	}

	@Override
	public void initGui() {
		Keyboard.enableRepeatEvents(true);

		textThroughput = new GuiTextField(fontRendererObj, this.width / 2 - 150, 100, 90, 20);
		textThroughput.setText("" + diode.limit);
		textThroughput.setMaxStringLength(11);

		buttonPriority = new GuiButton(0, this.width / 2 + 20, 100, 90, 20, diode.priority.name());
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();

		drawString(fontRendererObj, "Throughput:", this.width / 2 - 150, 80, 0xA0A0A0);
		drawString(fontRendererObj, "(max. 10,000,000,000 HE)", this.width / 2 - 150, 90, 0xA0A0A0);
		textThroughput.drawTextBox();

		drawString(fontRendererObj, "Priority:", this.width / 2 + 20, 80, 0xA0A0A0);
		buttonPriority.drawButton(mc, mouseX, mouseY);

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);

		NBTTagCompound data = new NBTTagCompound();
		data.setByte("priority", (byte) priority);

		try { data.setLong("limit", Long.parseLong(textThroughput.getText())); } catch(Exception ex) {}

		PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, diode.xCoord, diode.yCoord, diode.zCoord));
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) {
		super.keyTyped(typedChar, keyCode);
		if(textThroughput.textboxKeyTyped(typedChar, keyCode)) return;

		if(keyCode == 1 || keyCode == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
			this.mc.thePlayer.closeScreen();
		}
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		textThroughput.mouseClicked(mouseX, mouseY, mouseButton);

		if(buttonPriority.mousePressed(mc, mouseX, mouseY)) {
			this.priority++;
			if(priority >= ConnectionPriority.values().length) priority = 0;
			buttonPriority.displayString = EnumUtil.grabEnumSafely(ConnectionPriority.class, priority).name();
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
		}
	}

	@Override public boolean doesGuiPauseGame() { return false; }
}
