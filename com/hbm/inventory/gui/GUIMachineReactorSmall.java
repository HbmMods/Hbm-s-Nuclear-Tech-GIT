package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.handler.FluidTypeHandler.FluidType;
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

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_reactor_experimental.png");
	private static ResourceLocation overlay = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_reactor_overlay_experimental.png");
	private TileEntityMachineReactorSmall diFurnace;
	private boolean toggleOverlay = false;

	public GUIMachineReactorSmall(InventoryPlayer invPlayer, TileEntityMachineReactorSmall tedf) {
		super(new ContainerMachineReactorSmall(invPlayer, tedf));
		diFurnace = tedf;
		
		this.xSize = 176;
		this.ySize = 222;
	}
	
	@SuppressWarnings("incomplete-switch")
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		diFurnace.tanks[0].renderTankInfo(this, mouseX, mouseY, guiLeft + 8, guiTop + 36, 16, 52);
		diFurnace.tanks[1].renderTankInfo(this, mouseX, mouseY, guiLeft + 26, guiTop + 36, 16, 52);
		diFurnace.tanks[2].renderTankInfo(this, mouseX, mouseY, guiLeft + 80, guiTop + 108, 88, 4);
		this.drawCustomInfo(this, mouseX, mouseY, guiLeft + 80, guiTop + 114, 88, 4, new String[] { "Hull Temperature:", "   " + Math.round((diFurnace.hullHeat) * 0.00001 * 980 + 20) + "°C" });
		this.drawCustomInfo(this, mouseX, mouseY, guiLeft + 80, guiTop + 120, 88, 4, new String[] { "Core Temperature:", "   " + Math.round((diFurnace.coreHeat) * 0.00002 * 980 + 20) + "°C" });
		
		String[] text = new String[] { "Coolant will move heat from the core to",
				"the hull. Water will use that heat and",
				"generate steam.",
				"Water consumption rate:",
				" 100 mB/t",
				" 2000 mB/s",
				"Coolant consumption rate:",
				" 10 mB/t",
				" 200 mB/s",
				"Water next to the reactor's open",
				"sides will pour into the tank." };
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36, 16, 16, guiLeft - 8, guiTop + 36 + 16, text);
		
		String[] text1 = new String[] { "Raise/lower the control rods",
				"using the button next to the",
				"fluid gauges." };
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36 + 16, 16, 16, guiLeft - 8, guiTop + 36 + 16, text1);

		if(diFurnace.tanks[0].getFill() <= 0) {
			String[] text2 = new String[] { "Error: Water is required for",
					"the reactor to function properly!" };
			this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36 + 32, 16, 16, guiLeft - 8, guiTop + 36 + 32 + 16, text2);
		}

		if(diFurnace.tanks[1].getFill() <= 0) {
			String[] text3 = new String[] { "Error: Coolant is required for",
					"the reactor to function properly!" };
			this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36 + 32 + 16, 16, 16, guiLeft - 8, guiTop + 36 + 32 + 16, text3);
		}
		
		String s = "0";
		
		switch(diFurnace.tanks[2].getTankType()) {
		case STEAM: s = "1x"; break;
		case HOTSTEAM:s = "10x"; break;
		case SUPERHOTSTEAM: s = "100x"; break;
		}
		
		String[] text4 = new String[] { "Steam compression switch",
				"Current compression level: " + s};
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 63, guiTop + 107, 14, 18, mouseX, mouseY, text4);
		
		String[] text5 = new String[] { diFurnace.retracting ? "Raise control rods" : "Lower control rods"};
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 52, guiTop + 53, 18, 18, mouseX, mouseY, text5);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.diFurnace.hasCustomInventoryName() ? this.diFurnace.getInventoryName() : I18n.format(this.diFurnace.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

    @SuppressWarnings("incomplete-switch")
	protected void mouseClicked(int x, int y, int i) {
    	super.mouseClicked(x, y, i);
		
    	if(guiLeft + 52 <= x && guiLeft + 52 + 16 > x && guiTop + 53 < y && guiTop + 53 + 16 >= y) {
    		
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(diFurnace.xCoord, diFurnace.yCoord, diFurnace.zCoord, diFurnace.retracting ? 0 : 1, 0));
    	}
		
    	if(guiLeft + 63 <= x && guiLeft + 63 + 14 > x && guiTop + 107 < y && guiTop + 107 + 18 >= y) {
    		
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			int c = 0;
			
			switch(diFurnace.tanks[2].getTankType()) {
			case STEAM: c = 0; break;
			case HOTSTEAM: c = 1; break;
			case SUPERHOTSTEAM: c = 2; break;
			}
			
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(diFurnace.xCoord, diFurnace.yCoord, diFurnace.zCoord, c, 1));
    	}
    }

	@SuppressWarnings("incomplete-switch")
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		if(toggleOverlay)
			Minecraft.getMinecraft().getTextureManager().bindTexture(overlay);
		else
			Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		if(diFurnace.tanks[2].getFill() > 0) {
			int i = diFurnace.getSteamScaled(88);
			
			int offset = 234;
			
			switch(diFurnace.tanks[2].getTankType()) {
			case HOTSTEAM: offset += 4; break;
			case SUPERHOTSTEAM: offset += 8; break;
			}
			
			drawTexturedModalRect(guiLeft + 80, guiTop + 108, 0, offset, i, 4);
		}
		
		if(diFurnace.hasHullHeat()) {
			int i = diFurnace.getHullHeatScaled(88);
			
			i = (int) Math.min(i, 160);
			
			drawTexturedModalRect(guiLeft + 80, guiTop + 114, 0, 226, i, 4);
		}
		
		if(diFurnace.hasCoreHeat()) {
			int i = diFurnace.getCoreHeatScaled(88);
			
			i = (int) Math.min(i, 160);
			
			drawTexturedModalRect(guiLeft + 80, guiTop + 120, 0, 230, i, 4);
		}
		
		if(!diFurnace.retracting)
			drawTexturedModalRect(guiLeft + 52, guiTop + 53, 212, 0, 18, 18);
		
		if(!toggleOverlay) {
			if(diFurnace.rods >= diFurnace.rodsMax) {
				
				for(int x = 0; x < 3; x++)
					for(int y = 0; y < 3; y++)
						drawTexturedModalRect(guiLeft + 79 + 36 * x, guiTop + 17 + 36 * y, 176, 0, 18, 18);
				
			} else if(diFurnace.rods > 0) {
	
				for(int x = 0; x < 3; x++)
					for(int y = 0; y < 3; y++)
						drawTexturedModalRect(guiLeft + 79 + 36 * x, guiTop + 17 + 36 * y, 194, 0, 18, 18);
				
			}
		}
		
		switch(diFurnace.tanks[2].getTankType()) {
		case STEAM: drawTexturedModalRect(guiLeft + 63, guiTop + 107, 176, 18, 14, 18); break;
		case HOTSTEAM: drawTexturedModalRect(guiLeft + 63, guiTop + 107, 190, 18, 14, 18); break;
		case SUPERHOTSTEAM: drawTexturedModalRect(guiLeft + 63, guiTop + 107, 204, 18, 14, 18); break;
		}
		
		this.drawInfoPanel(guiLeft - 16, guiTop + 36, 16, 16, 2);
		this.drawInfoPanel(guiLeft - 16, guiTop + 36 + 16, 16, 16, 3);
		
		if(diFurnace.tanks[0].getFill() <= 0)
			this.drawInfoPanel(guiLeft - 16, guiTop + 36 + 32, 16, 16, 6);
		
		if(diFurnace.tanks[1].getFill() <= 0)
			this.drawInfoPanel(guiLeft - 16, guiTop + 36 + 32 + 16, 16, 16, 7);

		Minecraft.getMinecraft().getTextureManager().bindTexture(diFurnace.tanks[0].getSheet());
		diFurnace.tanks[0].renderTank(this, guiLeft + 8, guiTop + 88, diFurnace.tanks[0].getTankType().textureX() * FluidTank.x, diFurnace.tanks[0].getTankType().textureY() * FluidTank.y, 16, 52);
		Minecraft.getMinecraft().getTextureManager().bindTexture(diFurnace.tanks[1].getSheet());
		diFurnace.tanks[1].renderTank(this, guiLeft + 26, guiTop + 88, diFurnace.tanks[1].getTankType().textureX() * FluidTank.x, diFurnace.tanks[1].getTankType().textureY() * FluidTank.y, 16, 52);
	}
	
    protected void keyTyped(char p_73869_1_, int p_73869_2_)
    {
        super.keyTyped(p_73869_1_, p_73869_2_);
        
        if (p_73869_2_ == 56)
        {
            this.toggleOverlay = !this.toggleOverlay;
        }
        
    }
}
