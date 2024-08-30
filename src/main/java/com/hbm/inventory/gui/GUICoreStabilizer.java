package com.hbm.inventory.gui;

import org.apache.commons.lang3.math.NumberUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerCoreStabilizer;
import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.AuxButtonPacket;
import com.hbm.tileentity.machine.TileEntityCoreStabilizer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class GUICoreStabilizer extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/dfc/gui_stabilizer.png");
	private TileEntityCoreStabilizer stabilizer;
    private GuiTextField field;
	
	public GUICoreStabilizer(InventoryPlayer invPlayer, TileEntityCoreStabilizer tedf) {
		super(new ContainerCoreStabilizer(invPlayer, tedf));
		stabilizer = tedf;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	public void initGui() {

		super.initGui();

        Keyboard.enableRepeatEvents(true);
        this.field = new GuiTextField(this.fontRendererObj, guiLeft + 75, guiTop + 57, 29, 12);
        this.field.setTextColor(-1);
        this.field.setDisabledTextColour(-1);
        this.field.setEnableBackgroundDrawing(false);
        this.field.setMaxStringLength(3);
        this.field.setText(String.valueOf(stabilizer.watts));
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 35, guiTop + 17, 16, 52, stabilizer.power, stabilizer.maxPower);
	}
	
	protected void mouseClicked(int x, int y, int i) {
    	super.mouseClicked(x, y, i);
        this.field.mouseClicked(x, y, i);

    	if(guiLeft + 124 <= x && guiLeft + 124 + 18 > x && guiTop + 52 < y && guiTop + 52 + 18 >= y) {
    		
    		if(NumberUtils.isNumber(field.getText())) {
    			int j = MathHelper.clamp_int(Integer.parseInt(field.getText()), 1, 100);
    			field.setText(j + "");
				mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
	    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(stabilizer.xCoord, stabilizer.yCoord, stabilizer.zCoord, j, 0));
    		}
    	}
	}

	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		String name = this.stabilizer.hasCustomInventoryName() ? this.stabilizer.getInventoryName() : I18n.format(this.stabilizer.getInventoryName());
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		if(field.isFocused())
			drawTexturedModalRect(guiLeft + 71, guiTop + 53, 192, 4, 34, 16);

		drawTexturedModalRect(guiLeft + 71, guiTop + 45, 192, 0, stabilizer.watts * 34 / 100, 4);
		
		int i = (int) stabilizer.getPowerScaled(52);
		drawTexturedModalRect(guiLeft + 35, guiTop + 69 - i, 176, 52 - i, 16, i);
		
        this.field.drawTextBox();
	}
	
    protected void keyTyped(char p_73869_1_, int p_73869_2_)
    {
        if (this.field.textboxKeyTyped(p_73869_1_, p_73869_2_)) { }
        else {
            super.keyTyped(p_73869_1_, p_73869_2_);
        }
    }

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}
}
