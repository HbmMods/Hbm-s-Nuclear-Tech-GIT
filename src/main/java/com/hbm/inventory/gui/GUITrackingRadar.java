package com.hbm.inventory.gui;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.hbm.inventory.container.ContainerTrackingRadar;
import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.NBTControlPacket;
import com.hbm.saveddata.satellites.XSatelliteRegistry;
import com.hbm.tileentity.machine.TileEntityMachineTrackingRadar;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
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
	protected boolean wasMouseinGUI = false;

	public GUITrackingRadar(InventoryPlayer invPlayer, TileEntityMachineTrackingRadar tedf) {
		super(new ContainerTrackingRadar(invPlayer, tedf));
		tracking = tedf;
		
		this.xSize = 256;
		this.ySize = 240;
	}
	
	@Override
	public void drawScreen(int x, int y, float f) {
		super.drawScreen(x, y, f);
		this.drawElectricityInfo(this, x, y, guiLeft + 232, guiTop + 18, 16, 88, tracking.power, tracking.maxPower);
		
		this.wasMouseinGUI = this.checkClick(x, y, 0, 0, xSize, ySize);
		
		this.scrollBounds = tracking.knownSats.size() - 7; // supports 7 lines
		if(this.scrollBounds < 0) this.scrollBounds = 0;
		if(this.scrollIndex < 0) this.setScroll(0);
		if(this.scrollIndex > scrollBounds) this.setScroll(scrollBounds);

		boolean isClicking = Mouse.isButtonDown(0);
		if(!isClicking) this.draggingScroll = false;
		
		if(!wasClicking && isClicking && guiLeft + 200 <= x && guiLeft + 200 + 34 + 14 > x && guiTop + 18 < y && guiTop + 18 + 88 >= y) {
			draggingScroll = true;
		}
		
		if(draggingScroll) {
			int range = 74; // 106 scroll bar size, -7 pixels on top and bottom
			int sY = MathHelper.clamp_int(y - guiTop - 24, 0, 92);
			double scrollFrac = (double) sY / (double) range;
			int row = (int) Math.round(scrollBounds * scrollFrac);
			this.setScroll(row);
		}
		
		this.wasClicking = isClicking;
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
	public void handleMouseInput() {
		
		int scrollDir = Mouse.getEventDWheel();

		if(scrollDir != 0 && wasMouseinGUI) {

			if(scrollDir > 0) scrollDir = 1;
			if(scrollDir < 0) scrollDir = -1;
			this.setScroll(this.getScroll() - scrollDir);
			return;
		}
		
		super.handleMouseInput();
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.tracking.hasCustomInventoryName() ? this.tracking.getInventoryName() : I18n.format(this.tracking.getInventoryName());
		
		this.fontRendererObj.drawString(name, 110 - this.fontRendererObj.getStringWidth(name) / 2, 6, 0xffffff);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 48, this.ySize - 96 + 2, 4210752);
		
		// no mode
		if(tracking.mode < 0) {
			this.fontRendererObj.drawString("No mode selected", 48, 22, 0x00ff00);
			
		// primary
		} else if(tracking.mode == 0) {
			
			if(tracking.knownSats.isEmpty()) {
				this.fontRendererObj.drawString("No known satellites", 48, 22, 0x00ff00);
				
			} else for(int k = 0; k < 7; k++) {
				int index = k + scrollIndex;
				if(index >= tracking.knownSats.size()) break;
				int[] sat = tracking.knownSats.get(index);
				String label = sat[1] + "-" + sat[2] + "-" + sat[3];
				ItemStack icon = XSatelliteRegistry.idToIcon.get(sat[0]);
				this.fontRendererObj.drawString(label, 58, 22 + k * 12, 0x00ff00);
				
				if(icon != null) {
					GL11.glPushMatrix();
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					RenderHelper.enableGUIStandardItemLighting();
					GL11.glDisable(GL11.GL_LIGHTING);
					GL11.glEnable(GL12.GL_RESCALE_NORMAL);
					GL11.glScaled(0.5, 0.5, 0.5);
					itemRender.zLevel = 100.0F;
					itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), icon, 48 * 2, (22 + k * 12) * 2);
					itemRender.zLevel = 0.0F;
					GL11.glEnable(GL11.GL_ALPHA_TEST);
					GL11.glDisable(GL11.GL_LIGHTING);
					GL11.glPopMatrix();
				}
			}
		// secondary
		} else if(tracking.mode == 1) {
			this.fontRendererObj.drawString("Secondary tracking radar", 48, 22, 0x00ff00);
			
		// tertiary
		} else if(tracking.mode == 2) {
			this.fontRendererObj.drawString("Tertiary tracking radar", 48, 22, 0x00ff00);
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
			
			if(tracking.progress > 0) {
				int p = (int) Math.ceil(tracking.progress * 168);
				drawTexturedModalRect(guiLeft + 44, guiTop + 117, 0, 240, p, 9);
			}
		} else if(tracking.mode == 1) {
			drawTexturedModalRect(guiLeft + 7, guiTop + 26, 232, 164, 18, 9);
			drawTexturedModalRect(guiLeft + 10, guiTop + 81, 0, 165, 14, 35);
		} else if(tracking.mode == 2) {
			drawTexturedModalRect(guiLeft + 7, guiTop + 35, 232, 173, 18, 9);
			drawTexturedModalRect(guiLeft + 26, guiTop + 81, 16, 165, 11, 35);
		}
		
		// scroll
		drawTexturedModalRect(guiLeft + getScrollBarXPos(), guiTop + getScrollBarYPos(), draggingScroll ? 244 : 232, 140, 12, 15);
		
		// power
		int j = (int) (tracking.power * 88 / tracking.maxPower);
		drawTexturedModalRect(guiLeft + 232, guiTop + 106 - j, 216, 228 - j, 16, j);
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
		double scrollProgress = (double) scrollIndex / (double) (scrollBounds < 1 ? 1 : scrollBounds);
		if(scrollProgress > 1) {
			scrollProgress = 1;
			this.setScroll(scrollBounds);
		}
		int scrollYPos = 18 + (int) (scrollProgress * scrollArea);
		return scrollYPos;
	}
}
