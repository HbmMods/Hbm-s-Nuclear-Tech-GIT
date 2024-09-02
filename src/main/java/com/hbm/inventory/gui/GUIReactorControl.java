package com.hbm.inventory.gui;

import org.apache.commons.lang3.math.NumberUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerReactorControl;
import com.hbm.lib.RefStrings;
import com.hbm.module.NumberDisplay;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.NBTControlPacket;
import com.hbm.tileentity.machine.TileEntityReactorControl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class GUIReactorControl extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_reactor_control.png");
	private TileEntityReactorControl control;

	private final NumberDisplay[] displays = new NumberDisplay[3];
	private GuiTextField[] fields;

	public GUIReactorControl(InventoryPlayer invPlayer, TileEntityReactorControl tedf) {
		super(new ContainerReactorControl(invPlayer, tedf));
		control = tedf;
		displays[0] = new NumberDisplay(this, 6, 20, 0x08FF00).setDigitLength(3);
		displays[1] = new NumberDisplay(this, 66, 20, 0x08FF00).setDigitLength(4);
		displays[2] = new NumberDisplay(this, 126, 20, 0x08FF00).setDigitLength(3);

		fields = new GuiTextField[4];

		this.xSize = 176;
		this.ySize = 166;
	}

	public void initGui() {
		super.initGui();
		Keyboard.enableRepeatEvents(true);

		// rod extraction fields
		for(byte i = 0; i < 2; i++) {
			this.fields[i] = new GuiTextField(this.fontRendererObj, guiLeft + 35 + 30 * i, guiTop + 38, 26, 7);
			this.fields[i].setTextColor(0x08FF00);
			this.fields[i].setDisabledTextColour(-1);
			this.fields[i].setEnableBackgroundDrawing(false);

			this.fields[i].setMaxStringLength(3);
		}

		// heat fields
		for(byte i = 0; i < 2; i++) {
			this.fields[i + 2] = new GuiTextField(this.fontRendererObj, guiLeft + 35 + 30 * i, guiTop + 49, 26, 7);
			this.fields[i + 2].setTextColor(0x08FF00);
			this.fields[i + 2].setDisabledTextColour(-1);
			this.fields[i + 2].setEnableBackgroundDrawing(false);

			this.fields[i + 2].setMaxStringLength(4);
		}

		this.fields[0].setText(String.valueOf((int) control.levelUpper));
		this.fields[1].setText(String.valueOf((int) control.levelLower));
		this.fields[2].setText(String.valueOf((int) control.heatUpper / 50));
		this.fields[3].setText(String.valueOf((int) control.heatLower / 50));
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
	}

	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);

		for(int j = 0; j < 4; j++) {
			this.fields[j].mouseClicked(x, y, i);
		}

		if(guiLeft + 33 <= x && guiLeft + 33 + 58 > x && guiTop + 59 < y && guiTop + 59 + 10 >= y) {

			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			NBTTagCompound data = new NBTTagCompound();

			double[] vals = new double[] { 0D, 0D, 0D, 0D };

			for(int k = 0; k < 4; k++) {

				double clamp = k < 2 ? 100 : 1000;
				int mod = k < 2 ? 1 : 50;

				if(NumberUtils.isDigits(fields[k].getText())) {
					int j = (int) MathHelper.clamp_double(Double.parseDouble(fields[k].getText()), 0, clamp);
					fields[k].setText(j + "");
					vals[k] = j * mod;
				} else {
					fields[k].setText("0");
				}
			}

			data.setDouble("levelUpper", vals[0]);
			data.setDouble("levelLower", vals[1]);
			data.setDouble("heatUpper", vals[2]);
			data.setDouble("heatLower", vals[3]);

			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, control.xCoord, control.yCoord, control.zCoord));
		}

		for(int k = 0; k < 3; k++) {
			if(guiLeft + 7 <= x && guiLeft + 7 + 22 > x && guiTop + 37 + k * 11 < y && guiTop + 37 + 10 + k * 11 >= y) {

				mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
				NBTTagCompound data = new NBTTagCompound();
				data.setInteger("function", k);
				PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, control.xCoord, control.yCoord, control.zCoord));
			}
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.control.hasCustomInventoryName() ? this.control.getInventoryName() : I18n.format(this.control.getInventoryName());

		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		GL11.glPushMatrix();
		Tessellator tess = Tessellator.instance;
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glLineWidth(3F);
		tess.startDrawing(3);
		tess.setColorOpaque_I(0x08FF00);

		for(int i = 0; i < 40; i++) {
			tess.addVertex(guiLeft + 128 + i, guiTop + 39 + MathHelper.clamp_double(control.getTargetLevel(control.function, i * 1250) / 100 * 28, 0, 28), this.zLevel);
		}

		tess.draw();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glPopMatrix();
		
		for(byte i = 0; i < 3; i++)
			displays[i].drawNumber(control.getDisplayData()[i]);

		for(int i = 0; i < 4; i++)
			this.fields[i].drawTextBox();
	}

	@Override
	protected void keyTyped(char c, int i) {

		for(byte j = 0; j < 4; j++) {
			if(this.fields[j].textboxKeyTyped(c, i))
				return;
		}

		if(i == 1 || i == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
			this.mc.thePlayer.closeScreen();
			return;
		}

		super.keyTyped(c, i);
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}
}
