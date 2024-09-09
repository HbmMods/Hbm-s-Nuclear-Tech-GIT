package com.hbm.inventory.gui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerTransporterRocket;
import com.hbm.lib.RefStrings;
import com.hbm.packet.toserver.NBTControlPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityTransporterRocket;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class GUITransporterRocket extends GuiInfoContainer {
	
	protected static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_transporter.png");

	private GuiTextField transporterName;

	private TileEntityTransporterRocket transporter;

	public GUITransporterRocket(InventoryPlayer invPlayer, TileEntityTransporterRocket transporter) {
		super(new ContainerTransporterRocket(invPlayer, transporter));

		this.transporter = transporter;
	
		xSize = 230;
		ySize = 236;
	}

	@Override
	public void initGui() {
		super.initGui();
		
		Keyboard.enableRepeatEvents(true);
		transporterName = new GuiTextField(this.fontRendererObj, guiLeft + 8, guiTop + 12, 122, 12);
		transporterName.setTextColor(0x00ff00);
		transporterName.setDisabledTextColour(0x00ff00);
		transporterName.setEnableBackgroundDrawing(false);
		transporterName.setText(transporter.getTransporterName());
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		for(int i = 0; i < 4; i++) {
			int x = guiLeft + 8 + i * 18;
			int y = guiTop + 70;
			transporter.tanks[i].renderTank(x, y + 34, zLevel, 16, 34);
		}

		for(int i = 0; i < 4; i++) {
			int x = guiLeft + 98 + i * 18;
			int y = guiTop + 70;
			transporter.tanks[i+4].renderTank(x, y + 34, zLevel, 16, 34);
		}

		for(int i = 0; i < 2; i++) {
			int x = guiLeft + 188 + i * 18;
			int y = guiTop + 18;
			transporter.tanks[i+8].renderTank(x, y + 70, zLevel, 16, 70);
		}

		for(int i = 0; i < 4; i++) {
			int x = guiLeft + 8 + i * 18;
			int y = guiTop + 70;
			transporter.tanks[i].renderTankInfo(this, mouseX, mouseY, x, y, 16, 34);
		}

		for(int i = 0; i < 4; i++) {
			int x = guiLeft + 98 + i * 18;
			int y = guiTop + 70;
			transporter.tanks[i+4].renderTankInfo(this, mouseX, mouseY, x, y, 16, 34);
		}

		for(int i = 0; i < 2; i++) {
			int x = guiLeft + 188 + i * 18;
			int y = guiTop + 18;
			transporter.tanks[i+8].renderTankInfo(this, mouseX, mouseY, x, y, 16, 70);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		drawTexturedModalRect(guiLeft + 100 + (int)(transporter.threshold * 6.78), guiTop + 122, xSize, 0, 4, 15);

		int threshold = transporter.getThreshold();
		int width = fontRendererObj.getStringWidth("x" + threshold);

		fontRendererObj.drawStringWithShadow("x" + threshold, guiLeft + 167 - width, guiTop + 12, -1);
		
		transporterName.drawTextBox();
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int button) {
		super.mouseClicked(mouseX, mouseY, button);
		transporterName.mouseClicked(mouseX, mouseY, button);
	}
	
	@Override
	protected void mouseClickMove(int mouseX, int mouseY, int lastButtonClicked, long timeSinceLastClick) {
		super.mouseClickMove(mouseX, mouseY, lastButtonClicked, timeSinceLastClick);
	
		int slidPos = transporter.threshold;

		if(isInAABB(mouseX, mouseY, guiLeft + 98, guiTop + 120, 74, 20)) {
			slidPos = (int)((mouseX - (guiLeft + 98)) / 6.78);
			slidPos = MathHelper.clamp_int(slidPos, 0, 10); // 2^0 - 2^9 | 0 - 512
			
			NBTTagCompound data = new NBTTagCompound();
			data.setDouble("threshold", slidPos);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, transporter.xCoord, transporter.yCoord, transporter.zCoord));
		}
	}

	@Override
	protected void keyTyped(char c, int key) {
		if(transporterName.textboxKeyTyped(c, key)) {
			setName();
		} else {
			super.keyTyped(c, key);
		}

		if(key == 1) {
			mc.thePlayer.closeScreen();
		}
	}

	private void setName() {
		transporter.setTransporterName(transporterName.getText());
	}

	private boolean isInAABB(int mouseX, int mouseY, int x, int y, int width, int height) {
		return x <= mouseX && x + width > mouseX && y <= mouseY && y + height > mouseY;
	}

}
