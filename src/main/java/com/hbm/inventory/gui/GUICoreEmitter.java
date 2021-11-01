package com.hbm.inventory.gui;

import org.apache.commons.lang3.math.NumberUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.inventory.FluidTank;
import com.hbm.inventory.container.ContainerCoreEmitter;
import com.hbm.lib.Library;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityCoreEmitter;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class GUICoreEmitter extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/dfc/gui_emitter.png");
	private TileEntityCoreEmitter emitter;
    private GuiTextField field;
	
	public GUICoreEmitter(InventoryPlayer invPlayer, TileEntityCoreEmitter tedf) {
		super(new ContainerCoreEmitter(invPlayer, tedf));
		emitter = tedf;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	public void initGui() {

		super.initGui();

        Keyboard.enableRepeatEvents(true);
        this.field = new GuiTextField(this.fontRendererObj, guiLeft + 57, guiTop + 57, 29, 12);
        this.field.setTextColor(-1);
        this.field.setDisabledTextColour(-1);
        this.field.setEnableBackgroundDrawing(false);
        this.field.setMaxStringLength(3);
        this.field.setText(String.valueOf(emitter.watts));
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		emitter.tank.renderTankInfo(this, mouseX, mouseY, guiLeft + 8, guiTop + 17, 16, 52);
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 26, guiTop + 17, 16, 52, emitter.power, emitter.maxPower);
	}
	
	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);

		this.field.mouseClicked(x, y, i);

    	if(guiLeft + 97 <= x && guiLeft + 97 + 18 > x && guiTop + 52 < y && guiTop + 52 + 18 >= y) {
    		
    		if(NumberUtils.isNumber(field.getText())) {
    			int j = MathHelper.clamp_int(Integer.parseInt(field.getText()), 1, 100);
    			field.setText(j + "");
				mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
	    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(emitter.xCoord, emitter.yCoord, emitter.zCoord, j, 0));
    		}
    	}

    	if(guiLeft + 133 <= x && guiLeft + 133 + 18 > x && guiTop + 52 < y && guiTop + 52 + 18 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(emitter.xCoord, emitter.yCoord, emitter.zCoord, 0, 1));
    	}
	}

	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		String name = I18n.format(this.emitter.getInventoryName());
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		
		this.fontRendererObj.drawString("Output: " + Library.getShortNumber(emitter.prev) + "Spk", 50, 30, 0xFF7F7F);
		
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		if(field.isFocused())
			drawTexturedModalRect(guiLeft + 53, guiTop + 53, 210, 4, 34, 16);

		if(emitter.isOn)
			drawTexturedModalRect(guiLeft + 133, guiTop + 52, 192, 0, 18, 18);

		drawTexturedModalRect(guiLeft + 53, guiTop + 45, 210, 0, emitter.watts * 34 / 100, 4);
		
		int i = (int) emitter.getPowerScaled(52);
		drawTexturedModalRect(guiLeft + 26, guiTop + 69 - i, 176, 52 - i, 16, i);
		
        this.field.drawTextBox();

		Minecraft.getMinecraft().getTextureManager().bindTexture(emitter.tank.getSheet());
		emitter.tank.renderTank(this, guiLeft + 8, guiTop + 69, emitter.tank.getTankType().textureX() * FluidTank.x, emitter.tank.getTankType().textureY() * FluidTank.y, 16, 52);
	}
	
    protected void keyTyped(char p_73869_1_, int p_73869_2_)
    {
        if (this.field.textboxKeyTyped(p_73869_1_, p_73869_2_)) { }
        else {
            super.keyTyped(p_73869_1_, p_73869_2_);
        }
    }
}
