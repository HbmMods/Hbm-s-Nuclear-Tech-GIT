package com.hbm.inventory.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemTransporterLinker.TransporterInfo;
import com.hbm.lib.RefStrings;
import com.hbm.packet.toserver.NBTControlPacket;
import com.hbm.packet.PacketDispatcher;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class GUITransporterLinker extends GuiScreen {
	
	protected static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/tool/gui_transport_linker.png");

	protected int xSize = 218;
	protected int ySize = 201;
	protected int guiLeft;
	protected int guiTop;

	private GuiTextField search;

	private int index = 0;

	protected TransporterInfo linkFromTransporter;
	protected TransporterInfo linkToTransporter;
	protected List<TransporterInfo> transporters;

	private List<TransporterInfo> visibleTransporters;
	
	private final EntityPlayer player;

	public GUITransporterLinker(EntityPlayer player, List<TransporterInfo> transporters, TransporterInfo linkFromTransporter) {
		this.player = player;
		this.linkFromTransporter = linkFromTransporter;
		if(linkFromTransporter != null) this.linkToTransporter = linkFromTransporter.linkedTo;
		this.transporters = transporters;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		drawDefaultBackground();
		drawGuiContainerBackgroundLayer(mouseX, mouseY);
		drawGuiContainerForegroundLayer(mouseX, mouseY);

		if(!Mouse.isButtonDown(0) && !Mouse.isButtonDown(1) && Mouse.next()) {
			int scroll = Mouse.getEventDWheel();
			
			if(scroll > 0 && index > 0) index--;
			if(scroll < 0 && index < visibleTransporters.size() - 5) index++;
		}
	}

	@Override
	public void updateScreen() {
		if(player.getHeldItem() == null || player.getHeldItem().getItem() != ModItems.transporter_linker)
			player.closeScreen();
	}

	@Override
	public void initGui() {
		guiLeft = (width - xSize) / 2;
		guiTop = (height - ySize) / 2;
		
		Keyboard.enableRepeatEvents(true);
		search = new GuiTextField(fontRendererObj, guiLeft + 59, guiTop + 46, 86, 12);
		search.setTextColor(-1);
		search.setDisabledTextColour(-1);
		search.setEnableBackgroundDrawing(false);
		search.setFocused(true);

		updateSearch();
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	protected void drawGuiContainerBackgroundLayer(int mouseX, int mouseY) {
		int hideScroll = visibleTransporters.size() <= 5 ? 20 : 0;

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize - hideScroll, ySize);

		// Draw search box
		if(search.isFocused())
			drawTexturedModalRect(guiLeft + 55, guiTop + 41, 0, ySize, 107, 18);

		search.drawTextBox();


		// Draw current transporter
		int x = guiLeft + 37;
		int textLeftX = guiLeft + 62;
		int textRightX = guiLeft + 188;
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(linkFromTransporter.planet);
		func_146110_a(x, guiTop + 14, 0, 0, 16, 16, 16, 16);

		String coordinates = "x: " + linkFromTransporter.x + ", z: " + linkFromTransporter.z;
		int width = fontRendererObj.getStringWidth(coordinates);
		fontRendererObj.drawStringWithShadow(linkFromTransporter.name, textLeftX, guiTop + 13, 0x00ff00);
		fontRendererObj.drawStringWithShadow(coordinates, textRightX - width, guiTop + 23, 0x00ff00);
		
		// Draw linkable transporters
		for(int i = index; i < Math.min(index + 5, visibleTransporters.size()); i++) {
			TransporterInfo transporter = visibleTransporters.get(i);
			int y = guiTop + 70 + (i-index) * 26;

			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			Minecraft.getMinecraft().getTextureManager().bindTexture(transporter.planet);
			func_146110_a(x, y, 0, 0, 16, 16, 16, 16);

			coordinates = "x: " + transporter.x + ", z: " + transporter.z;
			width = fontRendererObj.getStringWidth(coordinates);
			fontRendererObj.drawStringWithShadow(transporter.name, textLeftX, y - 1, 0x00ff00);
			fontRendererObj.drawStringWithShadow(coordinates, textRightX - width, y + 9, 0x00ff00);
		}
	}
	
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);

		int x = guiLeft + 10;

		if(isInAABB(mouseX, mouseY, x, guiTop + 13, 18, 18)) {
			drawTexturedModalRect(x, guiTop + 13, xSize + 18, 0, 18, 18);
		}

		for(int i = index; i < Math.min(index + 5, visibleTransporters.size()); i++) {
			TransporterInfo transporter = visibleTransporters.get(i);

			int y = guiTop + 69 + (i-index) * 26;
			if(linkToTransporter != null && linkToTransporter.equals(transporter)) {
				drawTexturedModalRect(x, y, xSize, 18, 18, 18);

				if(isInAABB(mouseX, mouseY, x, y, 18, 18)) {
					drawTexturedModalRect(x, y, xSize + 18, 0, 18, 18);
				}
			} else {
				if(isInAABB(mouseX, mouseY, x, y, 18, 18)) {
					drawTexturedModalRect(x, y, xSize, 0, 18, 18);
				}
			}

		}

		// Draw scrollbar
		if(visibleTransporters.size() > 5) {
			x = guiLeft + 202;
			int y = guiTop + 68 + MathHelper.floor_float((float)index / (float)(visibleTransporters.size() - 5) * 114.0F);
			drawTexturedModalRect(x, y, xSize, 54, 14, 15);
		}
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int button) {
		search.mouseClicked(mouseX, mouseY, button);

		int x = guiLeft + 10;

		if(isInAABB(mouseX, mouseY, x, guiTop + 13, 18, 18)) {
			if(selectTransporter(null)) {
				player.closeScreen();
			}
		}

		for(int i = index; i < Math.min(index + 5, visibleTransporters.size()); i++) {
			int y = guiTop + 69 + (i-index) * 26;
			if(isInAABB(mouseX, mouseY, x, y, 18, 18)) {
				if(selectTransporter(visibleTransporters.get(i))) {
					player.closeScreen();
				}
			}
		}
	}
	
	@Override
	protected void mouseClickMove(int mouseX, int mouseY, int lastButtonClicked, long timeSinceLastClick) {
		super.mouseClickMove(mouseX, mouseY, lastButtonClicked, timeSinceLastClick);

		if(isInAABB(mouseX, mouseY, guiLeft + 198, guiTop + 64, 22, 137)) {
			index = MathHelper.floor_float((mouseY - (guiTop + 68)) / 114.0F * (float)(visibleTransporters.size() - 5));
			index = MathHelper.clamp_int(index, 0, visibleTransporters.size() - 5);
		}
	}

	// Links commutatively
	private boolean selectTransporter(TransporterInfo transporter) {
		if(linkFromTransporter.equals(transporter)) return false;

		if(linkToTransporter != null && linkToTransporter.equals(transporter)) {
			linkTransporters(linkFromTransporter, null);
		} else {
			linkTransporters(linkFromTransporter, transporter);
		}

		return true;
	}

	
	private void linkTransporters(TransporterInfo from, TransporterInfo to) {
		if(from == null && to == null) return;

		if(to == null) {
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("unlink", true);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, from.x, from.y, from.z));
			return;
		} else if(from == null) {
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("unlink", true);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, to.x, to.y, to.z));
			return;
		}

		NBTTagCompound data = new NBTTagCompound();
		data.setInteger("dimensionId", to.dimensionId);
		data.setIntArray("linkedTo", new int[] { to.x, to.y, to.z });
		PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, from.x, from.y, from.z));
	}

	@Override
	protected void keyTyped(char c, int key) {

		if(this.search.textboxKeyTyped(c, key)) {
			updateSearch();
		} else {
			super.keyTyped(c, key);
		}

		if(key == 1) {
			mc.thePlayer.closeScreen();
		}
	}
	
	private void updateSearch() {
		visibleTransporters = new ArrayList<>();
		index = 0;
		
		String subs = search.getText().toLowerCase(Locale.US);

		for(TransporterInfo transporter : transporters) {
			if(transporter.equals(linkFromTransporter)) continue;
			if(transporter.linkedTo != null && !transporter.linkedTo.equals(linkFromTransporter)) continue;
			if(transporter.name.toLowerCase(Locale.US).contains(subs)) {
				visibleTransporters.add(transporter);
			}
		}
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	private boolean isInAABB(int mouseX, int mouseY, int x, int y, int width, int height) {
		return x <= mouseX && x + width > mouseX && y <= mouseY && y + height > mouseY;
	}

}
