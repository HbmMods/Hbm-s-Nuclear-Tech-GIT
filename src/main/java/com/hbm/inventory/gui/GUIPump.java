package com.hbm.inventory.gui;

import org.lwjgl.input.Keyboard;

import com.hbm.blocks.network.FluidPump.TileEntityFluidPump;
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
public class GUIPump extends GuiScreen {

	protected final TileEntityFluidPump pump;

	private GuiTextField textPlacementPriority;
	private GuiButton buttonPressure;
	private GuiButton buttonPriority;
	private int pressure;
	private int priority;

	public GUIPump(TileEntityFluidPump pump) {
		this.pump = pump;
		this.pressure = pump.tank[0].getPressure();
		this.priority = pump.priority.ordinal();
	}

	@Override
	public void initGui() {
		Keyboard.enableRepeatEvents(true);

		textPlacementPriority = new GuiTextField(fontRendererObj, this.width / 2 - 150, 100, 90, 20);
		textPlacementPriority.setText("" + pump.bufferSize);
		textPlacementPriority.setMaxStringLength(5);

		buttonPressure = new GuiButton(0, this.width / 2 - 50, 100, 90, 20, pressure + " PU");

		buttonPriority = new GuiButton(1, this.width / 2 + 50, 100, 90, 20, pump.priority.name());
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();

		drawString(fontRendererObj, "Throughput:", this.width / 2 - 150, 80, 0xA0A0A0);
		drawString(fontRendererObj, "(max. 10,000mB)", this.width / 2 - 150, 90, 0xA0A0A0);
		textPlacementPriority.drawTextBox();

		drawString(fontRendererObj, "Pressure:", this.width / 2 - 50, 80, 0xA0A0A0);
		buttonPressure.drawButton(mc, mouseX, mouseY);

		drawString(fontRendererObj, "Priority:", this.width / 2 + 50, 80, 0xA0A0A0);
		buttonPriority.drawButton(mc, mouseX, mouseY);

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);

		NBTTagCompound data = new NBTTagCompound();

		data.setByte("pressure", (byte) pressure);
		data.setByte("priority", (byte) priority);

		try { data.setInteger("capacity", Integer.parseInt(textPlacementPriority.getText())); } catch(Exception ex) {}

		PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, pump.xCoord, pump.yCoord, pump.zCoord));
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) {
		super.keyTyped(typedChar, keyCode);
		if(textPlacementPriority.textboxKeyTyped(typedChar, keyCode)) return;

		if(keyCode == 1 || keyCode == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
			this.mc.thePlayer.closeScreen();
		}
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		textPlacementPriority.mouseClicked(mouseX, mouseY, mouseButton);

		if(buttonPressure.mousePressed(mc, mouseX, mouseY)) {
			this.pressure++;
			if(pressure > 5) pressure = 0;
			buttonPressure.displayString = pressure + " PU";
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
		}

		if(buttonPriority.mousePressed(mc, mouseX, mouseY)) {
			this.priority++;
			if(priority >= ConnectionPriority.values().length) priority = 0;
			buttonPriority.displayString = EnumUtil.grabEnumSafely(ConnectionPriority.class, priority).name();
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
		}
	}

	@Override public boolean doesGuiPauseGame() { return false; }
}