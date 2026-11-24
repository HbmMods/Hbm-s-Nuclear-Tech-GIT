package com.hbm.inventory.gui;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerTrackingRadar;
import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.NBTControlPacket;
import com.hbm.saveddata.satellites.XSatelliteRegistry;
import com.hbm.tileentity.machine.TileEntityMachineTrackingRadar;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class GUITrackingRadar extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_tracking_radar.png");
	private TileEntityMachineTrackingRadar tracking;
	
	protected int scrollIndex = 0;
	protected int scrollBounds = 1;
	protected boolean wasClicking = false;
	protected boolean draggingScroll = false;

	public GUITrackingRadar(InventoryPlayer invPlayer, TileEntityMachineTrackingRadar tedf) {
		super(new ContainerTrackingRadar(invPlayer, tedf));
		tracking = tedf;
		
		this.xSize = 256;
		this.ySize = 240;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 232, guiTop + 18, 16, 88, tracking.power, tracking.maxPower);
		
		this.scrollBounds = tracking.knownSats.size() - 7; // supports 7 lines
		if(this.scrollBounds < 1) this.scrollBounds = 1;
		if(this.scrollIndex < 0) this.setScroll(0);
		if(this.scrollIndex > scrollBounds) this.setScroll(scrollBounds);

		boolean isClicking = Mouse.isButtonDown(0);
		if(!isClicking) this.draggingScroll = false;
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);
		
		NBTTagCompound data = null;

		for(int j = 0; j < 3; j++) {
			if(this.checkClick(x, y, 7, 17 + 9 * j, 18, 9)) {
				data = new NBTTagCompound();
				data.setByte("b", (byte) j);
				break;
			}
		}
		
		if(data != null) {
			this.click();
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, tracking.xCoord, tracking.yCoord, tracking.zCoord));
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.tracking.hasCustomInventoryName() ? this.tracking.getInventoryName() : I18n.format(this.tracking.getInventoryName());
		
		this.fontRendererObj.drawString(name, 110 - this.fontRendererObj.getStringWidth(name) / 2, 6, 0xffffff);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 48, this.ySize - 96 + 2, 4210752);
		
		for(int k = 0; k < 7; k++) {
			int index = k + scrollIndex;
			if(index >= tracking.knownSats.size()) break;
			int[] sat = tracking.knownSats.get(index);
			String label = sat[1] + "-" + sat[2] + "-" + sat[3];
			ItemStack icon = XSatelliteRegistry.idToIcon.get(sat[0]);
			this.fontRendererObj.drawString(label, 48, 22 + k * 12, 0x00ff00);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float interp, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, 132);
		drawTexturedModalRect(guiLeft + 40, guiTop + 140, 40, 140, 176, 100);

		if(tracking.mode == 0) {
			drawTexturedModalRect(guiLeft + 7, guiTop + 17, 232, 155, 18, 9);
			drawTexturedModalRect(guiLeft + 19, guiTop + 56, 9, 140, 9, 24);
		} else if(tracking.mode == 1) {
			drawTexturedModalRect(guiLeft + 7, guiTop + 26, 232, 164, 18, 9);
			drawTexturedModalRect(guiLeft + 10, guiTop + 81, 0, 165, 14, 35);
		} else if(tracking.mode == 2) {
			drawTexturedModalRect(guiLeft + 7, guiTop + 35, 232, 173, 18, 9);
			drawTexturedModalRect(guiLeft + 26, guiTop + 81, 16, 165, 11, 35);
		}
		
		// scroll
		drawTexturedModalRect(guiLeft + getScrollBarXPos(), guiTop + getScrollBarYPos(), draggingScroll ? 244 : 216, 0, 12, 15);
		
		if(tracking.progress > 0) {
			int p = (int) Math.ceil(tracking.progress * 168);
			drawTexturedModalRect(guiLeft + 44, guiTop + 117, 0, 240, p, 9);
		}
	}
	
	public int getScroll() {
		return MathHelper.clamp_int(scrollIndex, 0, scrollBounds);
	}
	
	public void setScroll(int scroll) {
		this.scrollIndex = MathHelper.clamp_int(scroll, 0, scrollBounds);
	}

	public int getScrollBarXPos() {
		return 200;
	}
	
	public int getScrollBarYPos() {
		int scrollArea = 88 - 15; // bar height minus the scroll knob's height
		double scrollProgress = (double) scrollIndex / (double) scrollBounds;
		if(scrollProgress > 1) {
			scrollProgress = 1;
			this.setScroll(scrollBounds);
		}
		int scrollYPos = 17 + (int) (scrollProgress * scrollArea);
		return scrollYPos;
	}
}
