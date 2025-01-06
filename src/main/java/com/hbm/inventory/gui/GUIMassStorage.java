package com.hbm.inventory.gui;

import java.util.Locale;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMassStorage;
import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.NBTControlPacket;
import com.hbm.tileentity.machine.storage.TileEntityMassStorage;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class GUIMassStorage extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/storage/gui_mass_storage.png");
	private TileEntityMassStorage storage;

	public GUIMassStorage(InventoryPlayer invPlayer, TileEntityMassStorage tile) {
		super(new ContainerMassStorage(invPlayer, tile));
		storage = tile;
		
		this.xSize = 176;
		this.ySize = 221;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		
		String percent = (((int) (storage.getStockpile() * 1000D / (double) storage.getCapacity())) / 10D) + "%";
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 96, guiTop + 16, 18, 90, mouseX, mouseY, new String[]
				{ String.format(Locale.US, "%,d", storage.getStockpile()) + " / " + String.format(Locale.US, "%,d", storage.getCapacity()), percent });

		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 62, guiTop + 72, 14, 14, mouseX, mouseY, new String[] { "Click: Provide one", "Shift-click: Provide stack" });
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 80, guiTop + 72, 14, 14, mouseX, mouseY, new String[] { "Toggle output" });
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);

		if(guiLeft + 62 <= x && guiLeft + 62 + 14 > x && guiTop + 72 < y && guiTop + 72 + 14 >= y) {

			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("provide", Keyboard.isKeyDown(Keyboard.KEY_LSHIFT));
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, storage.xCoord, storage.yCoord, storage.zCoord));
		}

		if(guiLeft + 80 <= x && guiLeft + 80 + 14 > x && guiTop + 72 < y && guiTop + 72 + 14 >= y) {

			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("toggle", false);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, storage.xCoord, storage.yCoord, storage.zCoord));
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.storage.hasCustomInventoryName() ? this.storage.getInventoryName() : I18n.format(this.storage.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int gauge = storage.getStockpile() * 88 / storage.getCapacity();
		drawTexturedModalRect(guiLeft + 97, guiTop + 105 - gauge, 176, 88 - gauge, 16, gauge);
		
		if(storage.output)
			drawTexturedModalRect(guiLeft + 80, guiTop + 72, 192, 0, 14, 14);
	}
}
