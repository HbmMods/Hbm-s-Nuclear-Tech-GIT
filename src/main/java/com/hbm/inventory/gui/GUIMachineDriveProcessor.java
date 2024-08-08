package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerDriveProcessor;
import com.hbm.lib.RefStrings;
import com.hbm.packet.NBTControlPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityMachineDriveProcessor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class GUIMachineDriveProcessor extends GuiInfoContainer {

	private static final ResourceLocation texture = new ResourceLocation( RefStrings.MODID + ":textures/gui/processing/gui_drive_processor.png");

	private TileEntityMachineDriveProcessor machine;

	public GUIMachineDriveProcessor(InventoryPlayer invPlayer, TileEntityMachineDriveProcessor machine) {
		super(new ContainerDriveProcessor(invPlayer, machine));
		this.machine = machine;

		this.xSize = 176;
		this.ySize = 207;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float interp, int mX, int mY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}

	
	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);

		// Process drive
		if(checkClick(x, y, 38, 61, 18, 18)) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));

			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("process", true);

			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, machine.xCoord, machine.yCoord, machine.zCoord));
		}

		// Clone drive
		if(checkClick(x, y, 49, 17, 18, 18)) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));

			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("clone", true);

			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, machine.xCoord, machine.yCoord, machine.zCoord));
		}
	}
	
}
