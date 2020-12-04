package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerHadron;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityHadron;
import com.hbm.util.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIHadron extends GuiInfoContainer {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_hadron.png");
	private TileEntityHadron hadron;
	
	public GUIHadron(InventoryPlayer invPlayer, TileEntityHadron laser) {
		super(new ContainerHadron(invPlayer, laser));
		this.hadron = laser;

		this.xSize = 176;
		this.ySize = 222;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 62, guiTop + 108, 70, 16, hadron.power, hadron.maxPower);

		if(hadron.hopperMode)
			this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 142, guiTop + 89, 18, 18, mouseX, mouseY, I18nUtil.resolveKeyArray("hadron.hopper1"));
		else
			this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 142, guiTop + 89, 18, 18, mouseX, mouseY, I18nUtil.resolveKeyArray("hadron.hopper0"));
		
		if(hadron.analysisOnly)
			this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 142, guiTop + 107, 18, 18, mouseX, mouseY, I18nUtil.resolveKeyArray("hadron.modeLine"));
		else
			this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 142, guiTop + 107, 18, 18, mouseX, mouseY, I18nUtil.resolveKeyArray("hadron.modeCircular"));
	}

	protected void mouseClicked(int x, int y, int i) {
    	super.mouseClicked(x, y, i);
		
    	//Toggle hadron
    	if(guiLeft + 19 <= x && guiLeft + 19 + 18 > x && guiTop + 89 < y && guiTop + 89 + 18 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(hadron.xCoord, hadron.yCoord, hadron.zCoord, 0, 0));
    	}
		
    	//Toggle analysis chamber
    	if(guiLeft + 142 <= x && guiLeft + 142 + 18 > x && guiTop + 107 < y && guiTop + 107 + 18 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(hadron.xCoord, hadron.yCoord, hadron.zCoord, 0, 1));
    	}
		
    	//Toggle hopper mode
    	if(guiLeft + 142 <= x && guiLeft + 142 + 18 > x && guiTop + 89 < y && guiTop + 89 + 18 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(hadron.xCoord, hadron.yCoord, hadron.zCoord, 0, 2));
    	}
    }
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.hadron.hasCustomInventoryName() ? this.hadron.getInventoryName() : I18n.format(this.hadron.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		if(hadron.isOn)
			drawTexturedModalRect(guiLeft + 16, guiTop + 89, 206, 0, 18, 18);
		
		if(hadron.analysisOnly)
			drawTexturedModalRect(guiLeft + 142, guiTop + 107, 206, 18, 18, 18);
		
		if(hadron.hopperMode)
			drawTexturedModalRect(guiLeft + 142, guiTop + 89, 206, 36, 18, 18);
		
		int i = hadron.getPowerScaled(70);
		drawTexturedModalRect(guiLeft + 62, guiTop + 108, 176, 60, i, 16);
	}
}
