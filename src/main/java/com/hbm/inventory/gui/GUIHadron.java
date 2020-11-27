package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerHadron;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityHadron;

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
		
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 71, guiTop + 108, 34, 16, hadron.power, hadron.maxPower);

		//String text = "Magnets are " + ((iter.isOn && iter.power >= iter.powerReq) ? "ON" : "OFF");
		//this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 76, guiTop + 94, 24, 12, mouseX, mouseY, new String[] { text });
	}

	protected void mouseClicked(int x, int y, int i) {
    	super.mouseClicked(x, y, i);
		
    	//Toggle hadron
    	if(guiLeft + 19 <= x && guiLeft + 19 + 18 > x && guiTop + 89 < y && guiTop + 89 + 18 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(hadron.xCoord, hadron.yCoord, hadron.zCoord, 0, 0));
    	}
		
    	//Toggle analysis chamber
    	if(guiLeft + 142 <= x && guiLeft + 142 + 18 > x && guiTop + 89 < y && guiTop + 89 + 18 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(hadron.xCoord, hadron.yCoord, hadron.zCoord, 0, 1));
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
	}
}
