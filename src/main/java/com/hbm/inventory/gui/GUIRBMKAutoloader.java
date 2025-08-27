package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerRBMKAutoloader;
import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.NBTControlPacket;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKAutoloader;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class GUIRBMKAutoloader extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_autoloader.png");
	private TileEntityRBMKAutoloader loader;

	public GUIRBMKAutoloader(InventoryPlayer invPlayer, TileEntityRBMKAutoloader tile) {
		super(new ContainerRBMKAutoloader(invPlayer, tile));
		loader = tile;
		
		this.xSize = 176;
		this.ySize = 182;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);

		if(guiLeft + 74 <= x && guiLeft + 74 + 12 > x && guiTop + 36 < y && guiTop + 36 + 12 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("minus", true);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, loader.xCoord, loader.yCoord, loader.zCoord));
		}

		if(guiLeft + 90 <= x && guiLeft + 90 + 12 > x && guiTop + 36 < y && guiTop + 36 + 12 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("plus", true);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, loader.xCoord, loader.yCoord, loader.zCoord));
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.loader.hasCustomInventoryName() ? this.loader.getInventoryName() : I18n.format(this.loader.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 0xFFFFFF);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
		
		String percent = loader.cycle + "%";
		this.fontRendererObj.drawString(percent, this.xSize / 2 - this.fontRendererObj.getStringWidth(percent) / 2, 23, 0x00FF00);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
}
