package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.FluidTank;
import com.hbm.inventory.container.ContainerMachineReactorSmall;
import com.hbm.inventory.container.ContainerMachineSelenium;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityMachineReactorSmall;
import com.hbm.tileentity.machine.TileEntityMachineSeleniumEngine;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineReactorSmall extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_reactor_small.png");
	private TileEntityMachineReactorSmall diFurnace;

	public GUIMachineReactorSmall(InventoryPlayer invPlayer, TileEntityMachineReactorSmall tedf) {
		super(new ContainerMachineReactorSmall(invPlayer, tedf));
		diFurnace = tedf;
		
		this.xSize = 176;
		this.ySize = 222;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		diFurnace.tanks[0].renderTankInfo(this, mouseX, mouseY, guiLeft + 8, guiTop + 36, 16, 52);
		diFurnace.tanks[1].renderTankInfo(this, mouseX, mouseY, guiLeft + 26, guiTop + 36, 16, 52);
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 80, guiTop + 108, 88, 7, diFurnace.power, diFurnace.powerMax);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.diFurnace.hasCustomInventoryName() ? this.diFurnace.getInventoryName() : I18n.format(this.diFurnace.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

    protected void mouseClicked(int x, int y, int i) {
    	super.mouseClicked(x, y, i);
		
    	if(guiLeft + 53 <= x && guiLeft + 53 + 16 > x && guiTop + 54 < y && guiTop + 54 + 16 >= y) {
    		
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(diFurnace.xCoord, diFurnace.yCoord, diFurnace.zCoord, diFurnace.retracting ? 0 : 1, 0));
    	}
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		if(diFurnace.power > 0) {
			int i = (int)diFurnace.getPowerScaled(88);
			
			i = (int) Math.min(i, 88);
			
			drawTexturedModalRect(guiLeft + 80, guiTop + 108, 0, 222, i, 7);
		}
		if(diFurnace.heat > 0) {
			int i = diFurnace.getHeatScaled(88);
			
			i = (int) Math.min(i, 160);
			
			drawTexturedModalRect(guiLeft + 80, guiTop + 117, 0, 229, i, 7);
		}
		
		if(!diFurnace.retracting)
			drawTexturedModalRect(guiLeft + 52, guiTop + 53, 212, 0, 18, 18);
		
		if(diFurnace.rods >= diFurnace.rodsMax) {
			
			for(int x = 0; x < 3; x++)
				for(int y = 0; y < 3; y++)
					drawTexturedModalRect(guiLeft + 79 + 36 * x, guiTop + 17 + 36 * y, 176, 0, 18, 18);
			
		} else if(diFurnace.rods > 0) {

			for(int x = 0; x < 3; x++)
				for(int y = 0; y < 3; y++)
					drawTexturedModalRect(guiLeft + 79 + 36 * x, guiTop + 17 + 36 * y, 194, 0, 18, 18);
			
		}

		Minecraft.getMinecraft().getTextureManager().bindTexture(diFurnace.tanks[0].getSheet());
		diFurnace.tanks[0].renderTank(this, guiLeft + 8, guiTop + 88, diFurnace.tanks[0].getTankType().textureX() * FluidTank.x, diFurnace.tanks[0].getTankType().textureY() * FluidTank.y, 16, 52);
		Minecraft.getMinecraft().getTextureManager().bindTexture(diFurnace.tanks[1].getSheet());
		diFurnace.tanks[1].renderTank(this, guiLeft + 26, guiTop + 88, diFurnace.tanks[1].getTankType().textureX() * FluidTank.x, diFurnace.tanks[1].getTankType().textureY() * FluidTank.y, 16, 52);
	}
}
