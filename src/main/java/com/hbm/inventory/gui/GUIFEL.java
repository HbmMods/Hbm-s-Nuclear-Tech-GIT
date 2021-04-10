package com.hbm.inventory.gui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerFEL;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityFEL;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIFEL extends GuiInfoContainer {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_fel.png");
	private TileEntityFEL fel;
    private GuiTextField field;

	public GUIFEL(InventoryPlayer invPlayer, TileEntityFEL laser) {
		super(new ContainerFEL(invPlayer, laser));
		this.fel = laser;

		this.xSize = 176;
		this.ySize = 168;
	}

	@Override
	public void initGui() {

		super.initGui();

        Keyboard.enableRepeatEvents(true);
        this.field = new GuiTextField(this.fontRendererObj, guiLeft + 57, guiTop + 57, 29, 12);
        this.field.setTextColor(-1);
        this.field.setDisabledTextColour(-1);
        this.field.setEnableBackgroundDrawing(false);
        this.field.setMaxStringLength(3);
        this.field.setText(String.valueOf(fel.watts));
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 133, guiTop + 16, 18, 9, mouseX, mouseY, new String[] {"Microwave"});
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 133, guiTop + 25, 18, 9, mouseX, mouseY, new String[] {"Infrared"});
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 133, guiTop + 34, 18, 9, mouseX, mouseY, new String[] {"Visible Light"});
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 133, guiTop + 43, 18, 9, mouseX, mouseY, new String[] {"UV"});
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 133, guiTop + 52, 18, 9, mouseX, mouseY, new String[] {"X-Ray"});
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 133, guiTop + 61, 18, 9, mouseX, mouseY, new String[] {"Gamma Ray"});
	}

	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);

		this.field.mouseClicked(x, y, i);
		
		for(int k = 0; k < 6; k++) {
			
			if(guiLeft + 133 <= x && guiLeft + 133 + 18 > x && guiTop + 16 + k * 9 < y && guiTop + 16 + k * 9 + 9 >= y) {
				mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
				PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(fel.xCoord, fel.yCoord, fel.zCoord, k, 0));
			}
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.fel.hasCustomInventoryName() ? this.fel.getInventoryName() : I18n.format(this.fel.getInventoryName());

		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		if(field.isFocused())
			drawTexturedModalRect(guiLeft + 53, guiTop + 53, 210, 4, 34, 16);
		
		int mode = fel.mode;
		drawTexturedModalRect(guiLeft + 133, guiTop + 16 + mode * 9, 176, 52 + mode * 9, 18, 9);
		
        this.field.drawTextBox();
	}
}
