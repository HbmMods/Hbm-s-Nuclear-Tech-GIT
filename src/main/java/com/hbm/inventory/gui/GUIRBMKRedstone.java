package com.hbm.inventory.gui;

import org.apache.commons.lang3.math.NumberUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerRBMKGeneric;
import com.hbm.lib.RefStrings;
import com.hbm.packet.NBTControlPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKRedstone;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class GUIRBMKRedstone extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/reactors/gui_rbmk_redstone.png");
	private TileEntityRBMKRedstone rod;
	
	private GuiTextField[] fields;

	public GUIRBMKRedstone(InventoryPlayer invPlayer, TileEntityRBMKRedstone tile) {
		super(new ContainerRBMKGeneric(invPlayer));
		rod = tile;
		
		fields = new GuiTextField[2];
		
		this.xSize = 176;
		this.ySize = 186;
	}

	public void initGui() {
		super.initGui();

		Keyboard.enableRepeatEvents(true);
		this.fields[0] = new GuiTextField(this.fontRendererObj, guiLeft + 56, guiTop + 25, 94, 18);
		this.fields[1] = new GuiTextField(this.fontRendererObj, guiLeft + 29, guiTop + 60, 28, 10);
		for(int i = 0; i < 2; i++) {
			this.fields[i].setTextColor(0x00ff00);
			this.fields[i].setDisabledTextColour(0x00ff00);
			this.fields[i].setEnableBackgroundDrawing(false);
			this.fields[i].setMaxStringLength(4);
			this.fields[i].setText(String.valueOf(rod.threshold));
			if(i == 0) {
				this.fields[i].setText(rod.channel == null ? "" : rod.channel);
				this.fields[i].setMaxStringLength(10);
			}
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		String value = "";
		switch(rod.mode) {
			case 1:
				value = "°C";
			break;
			case 2:
			case 3:
				value = "/cm²/s";
			break;
		}

		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 29, guiTop + 49, 28, 10, mouseX, mouseY, "Value: " + (rod.value) + value);

		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 69, guiTop + 56, 25, 25, mouseX, mouseY, "Monitor slow flux");
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 96, guiTop + 56, 25, 25, mouseX, mouseY, "Monitor fast flux");
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 123, guiTop + 56, 25, 25, mouseX, mouseY, "Monitor heat");

		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 29, guiTop + 60, 28, 10, mouseX, mouseY, "Redstone Threshold");
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 29, guiTop + 71, 28, 10, mouseX, mouseY, "Save threshold");
	}

	@Override
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);

		for(int j = 0; j < 2; j++) {
			this.fields[j].mouseClicked(x, y, i);
		}

		if(guiLeft + 29 <= x && guiLeft + 29 + 28 > x && guiTop + 71 < y && guiTop + 71 + 10 >= y) {

			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			if(NumberUtils.isNumber(fields[1].getText())) {
				data.setString("Ch_set", fields[0].getText() + "");
				data.setInteger("Threshold", Integer.parseInt(fields[1].getText()));
			} else {
				fields[0].setText("");
				fields[1].setText("0");
			}
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, rod.xCoord, rod.yCoord, rod.zCoord));
		}
		for(int k = 0; k < 3; k++) { // button for each mode
			if(guiLeft + 68 + k * 27 <= x && guiLeft + 68 + (k + 1) * 27 > x && guiTop + 55 < y && guiTop + 55 + 27 >= y) {
				mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
				NBTTagCompound data = new NBTTagCompound();
				if(k == 0)
					data.setInteger("Mode", 2);
				if(k == 1)
					data.setInteger("Mode", 3);
				if(k == 2)
					data.setInteger("Mode", 1);
				PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, rod.xCoord, rod.yCoord, rod.zCoord));
			}
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.rod.hasCustomInventoryName() ? this.rod.getInventoryName() : I18n.format(this.rod.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		switch(rod.mode) {
			case 1:
				drawTexturedModalRect(guiLeft + 77, guiTop + 46, 180, 13, 9, 9);
				drawTexturedModalRect(guiLeft + 104, guiTop + 46, 180, 13, 9, 9);
				drawTexturedModalRect(guiLeft + 131, guiTop + 46, 180, 3, 9, 9);
				break;
			case 2:
				drawTexturedModalRect(guiLeft + 77, guiTop + 46, 180, 13, 9, 9);
				drawTexturedModalRect(guiLeft + 104, guiTop + 46, 180, 3, 9, 9);
				drawTexturedModalRect(guiLeft + 131, guiTop + 46, 180, 13, 9, 9);
				break;
			case 3:
				drawTexturedModalRect(guiLeft + 77, guiTop + 46, 180, 3, 9, 9);
				drawTexturedModalRect(guiLeft + 104, guiTop + 46, 180, 13, 9, 9);
				drawTexturedModalRect(guiLeft + 131, guiTop + 46, 180, 13, 9, 9);
				break;
		}
		this.fields[0].drawTextBox();
		this.fields[1].drawTextBox();
		this.fontRendererObj.drawString(rod.trunc_v, 30, 50, 0xFF7F7F);
	}

	@Override
	protected void keyTyped(char c, int i) {
		
		for(int j = 0; j < 2; j++) {
			if(this.fields[j].textboxKeyTyped(c, i))
				return;
		}
		
		super.keyTyped(c, i);
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}
}
