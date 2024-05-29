package com.hbm.inventory.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemTransporterLinker;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityTransporterBase;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
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

	protected TileEntityTransporterBase linkFromTransporter;
	protected TileEntityTransporterBase linkToTransporter;
	protected List<TileEntityTransporterBase> transporters;

	private List<TileEntityTransporterBase> visibleTransporters;
	
	private final EntityPlayer player;

	public GUITransporterLinker(EntityPlayer player, TileEntityTransporterBase linkFromTransporter) {
		this.player = player;
		this.linkFromTransporter = linkFromTransporter;

		transporters = ItemTransporterLinker.getTransporters(player.getHeldItem(), linkFromTransporter);
		linkToTransporter = linkFromTransporter.getLinkedTransporter();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		drawDefaultBackground();
		drawGuiContainerBackgroundLayer(mouseX, mouseY);
		drawGuiContainerForegroundLayer(mouseX, mouseY);

		if(!Mouse.isButtonDown(0) && !Mouse.isButtonDown(1) && Mouse.next()) {
			int scroll = Mouse.getEventDWheel();
			
			if(scroll > 0 && index > 0) index--;
			if(scroll < 0 && index < transporters.size() - 5) index++;
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

		visibleTransporters = transporters;
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
		Minecraft.getMinecraft().getTextureManager().bindTexture(linkFromTransporter.dimensionImage);
		func_146110_a(x, guiTop + 14, 0, 0, 16, 16, 16, 16);

		String coordinates = "x: " + linkFromTransporter.xCoord + ", z: " + linkFromTransporter.zCoord;
		int width = fontRendererObj.getStringWidth(coordinates);
		fontRendererObj.drawStringWithShadow(linkFromTransporter.getTransporterName(), textLeftX, guiTop + 13, 0x00ff00);
		fontRendererObj.drawStringWithShadow(coordinates, textRightX - width, guiTop + 23, 0x00ff00);
		
		// Draw linkable transporters
		for(int i = index; i < Math.min(index + 5, visibleTransporters.size()); i++) {
			TileEntityTransporterBase transporter = visibleTransporters.get(i);
			int y = guiTop + 70 + (i-index) * 26;

			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			Minecraft.getMinecraft().getTextureManager().bindTexture(transporter.dimensionImage);
			func_146110_a(x, y, 0, 0, 16, 16, 16, 16);

			coordinates = "x: " + transporter.xCoord + ", z: " + transporter.zCoord;
			width = fontRendererObj.getStringWidth(coordinates);
			fontRendererObj.drawStringWithShadow(transporter.getTransporterName(), textLeftX, y - 1, 0x00ff00);
			fontRendererObj.drawStringWithShadow(coordinates, textRightX - width, y + 9, 0x00ff00);
		}
	}
	
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		for(int i = index; i < Math.min(index + 5, visibleTransporters.size()); i++) {
			TileEntityTransporterBase transporter = visibleTransporters.get(i);

			int x = guiLeft + 10;
			int y = guiTop + 69 + (i-index) * 26;
			if(linkToTransporter != null && ItemTransporterLinker.matches(linkToTransporter, transporter)) {
				drawTexturedModalRect(x, y, xSize, 18, 18, 18);
			}

			if(isInAABB(mouseX, mouseY, x, y, 18, 18)) {
				drawTexturedModalRect(x, y, xSize, 0, 18, 18);
			}
		}

		// Draw scrollbar
		if(visibleTransporters.size() > 5) {
			int x = guiLeft + 202;
			int y = guiTop + 68 + MathHelper.floor_float(index / (visibleTransporters.size() - 5) * 114);
			drawTexturedModalRect(x, y, xSize, 36, 14, 15);
		}
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int button) {
		search.mouseClicked(mouseX, mouseY, button);

		for(int i = index; i < Math.min(index + 5, visibleTransporters.size()); i++) {
			int x = guiLeft + 10;
			int y = guiTop + 69 + (i-index) * 26;
			if(isInAABB(mouseX, mouseY, x, y, 18, 18)) {
				if(selectTransporter(visibleTransporters.get(i))) {
					player.closeScreen();
				}
			}
		}
	}

	// Links commutatively
	private boolean selectTransporter(TileEntityTransporterBase transporter) {
		if(linkFromTransporter == transporter) return false;
		
		TileEntityTransporterBase previouslyLinked = transporter.getLinkedTransporter();

		if(previouslyLinked != null && !ItemTransporterLinker.matches(linkFromTransporter, previouslyLinked)) {
			previouslyLinked.setLinkedTransporter(null);
		}
		
		transporter.setLinkedTransporter(linkFromTransporter);
		linkFromTransporter.setLinkedTransporter(transporter);

		return true;
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

		for(TileEntityTransporterBase transporter : transporters) {
			if(transporter.getTransporterName().toLowerCase(Locale.US).contains(subs)) {
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
