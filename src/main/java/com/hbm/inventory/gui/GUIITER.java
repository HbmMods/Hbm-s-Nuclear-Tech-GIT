package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerITER;
import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toserver.AuxButtonPacket;
import com.hbm.tileentity.machine.TileEntityITER;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIITER extends GuiInfoContainer {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/reactors/gui_fusion_multiblock.png");
	private TileEntityITER iter;
	
	public GUIITER(InventoryPlayer invPlayer, TileEntityITER laser) {
		super(new ContainerITER(invPlayer, laser));
		this.iter = laser;

		this.xSize = 176;
		this.ySize = 222;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 71, guiTop + 108, 34, 16, iter.power, iter.maxPower);

		iter.tanks[0].renderTankInfo(this, mouseX, mouseY, guiLeft + 26, guiTop + 54, 16, 52);	//Water
		iter.tanks[1].renderTankInfo(this, mouseX, mouseY, guiLeft + 134, guiTop + 54, 16, 52);	//Steam
		iter.plasma.renderTankInfo(this, mouseX, mouseY, guiLeft + 71, guiTop + 54, 34, 34);	//Plasma

		String text = "Magnets are " + ((iter.isOn && iter.power >= iter.powerReq) ? "ON" : "OFF");
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 76, guiTop + 94, 24, 12, mouseX, mouseY, new String[] { text });
	}

	protected void mouseClicked(int x, int y, int i) {
    	super.mouseClicked(x, y, i);
		
    	if(guiLeft + 52 <= x && guiLeft + 52 + 18 > x && guiTop + 107 < y && guiTop + 107 + 18 >= y) {
    		
    		//toggle the magnets
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(iter.xCoord, iter.yCoord, iter.zCoord, 0, 0));
    	}
    }
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.iter.hasCustomInventoryName() ? this.iter.getInventoryName() : I18n.format(this.iter.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		if(iter.isOn)
			drawTexturedModalRect(guiLeft + 52, guiTop + 107, 176, 0, 18, 18);
		
		if(iter.isOn && iter.power >= iter.powerReq)
			drawTexturedModalRect(guiLeft + 76, guiTop + 94, 194, 0, 24, 12);
		
		if(iter.getShield() >= iter.plasma.getTankType().temperature)
			drawTexturedModalRect(guiLeft + 97, guiTop + 17, 218, 0, 18, 18);
		
		int i = (int)iter.getPowerScaled(34);
		drawTexturedModalRect(guiLeft + 71, guiTop + 108, 176, 25, i, 16);
		
		int j = (int)iter.getProgressScaled(17);
		drawTexturedModalRect(guiLeft + 44, guiTop + 22, 176, 18, j, 7);

		for(int t = 0; t < 2; t++) {
			iter.tanks[t].renderTank(guiLeft + 26 + 108 * t, guiTop + 106, this.zLevel, 16, 52);
		}
		
		iter.plasma.renderTank(guiLeft + 71, guiTop + 88, this.zLevel, 34, 34);
	}
}
