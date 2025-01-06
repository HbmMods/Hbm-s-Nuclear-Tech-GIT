package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerFunnel;
import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.NBTControlPacket;
import com.hbm.tileentity.machine.TileEntityMachineFunnel;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class GUIFunnel extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_funnel.png");
	private TileEntityMachineFunnel funnel;

	public GUIFunnel(InventoryPlayer invPlayer, TileEntityMachineFunnel tedf) {
		super(new ContainerFunnel(invPlayer, tedf));
		funnel = tedf;
		
		this.xSize = 176;
		this.ySize = 168;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 159, guiTop + 73, 10, 10, mouseX, mouseY, "Mode: " + (funnel.mode == funnel.MODE_3x3 ? "3x3 only" : funnel.mode == funnel.MODE_2x2 ? "2x2 only" : "3x3 then 2x2"));
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);
		
		if(this.checkClick(x, y, 159, 73, 10, 10)) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("toggle", true);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, funnel.xCoord, funnel.yCoord, funnel.zCoord));
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.funnel.hasCustomInventoryName() ? this.funnel.getInventoryName() : I18n.format(this.funnel.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		drawTexturedModalRect(guiLeft + 159, guiTop + 73, 176, funnel.mode * 10, 10, 10);
	}
}
