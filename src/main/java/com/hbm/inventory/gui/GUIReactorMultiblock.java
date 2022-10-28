package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerReactorMultiblock;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityMachineReactorLarge;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIReactorMultiblock extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_reactor_large_experimental.png");
	private TileEntityMachineReactorLarge diFurnace;

	public GUIReactorMultiblock(InventoryPlayer invPlayer, TileEntityMachineReactorLarge tedf) {
		super(new ContainerReactorMultiblock(invPlayer, tedf));
		diFurnace = tedf;
		
		this.xSize = 176;
		this.ySize = 222;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		diFurnace.tanks[0].renderTankInfo(this, mouseX, mouseY, guiLeft + 8, guiTop + 88 - 52, 16, 52);
		diFurnace.tanks[1].renderTankInfo(this, mouseX, mouseY, guiLeft + 26, guiTop + 88 - 52, 16, 52);
		diFurnace.tanks[2].renderTankInfo(this, mouseX, mouseY, guiLeft + 80, guiTop + 108, 88, 4);
		this.drawCustomInfo(this, mouseX, mouseY, guiLeft + 80, guiTop + 114, 88, 4, new String[] { "Hull Temperature:", "   " + Math.round((diFurnace.hullHeat) * 0.00001 * 980 + 20) + "°C" });
		this.drawCustomInfo(this, mouseX, mouseY, guiLeft + 80, guiTop + 120, 88, 4, new String[] { "Core Temperature:", "   " + Math.round((diFurnace.coreHeat) * 0.00002 * 980 + 20) + "°C" });

		this.drawCustomInfo(this, mouseX, mouseY, guiLeft + 115, guiTop + 17, 18, 90, new String[] { "Operating Level: " + diFurnace.rods + "%" });
		
		String fuel = "";
		
		switch(diFurnace.type) {
		case URANIUM:
			fuel = "Uranium";
			break;
		case MOX:
			fuel = "MOX";
			break;
		case PLUTONIUM:
			fuel = "Plutonium";
			break;
		case SCHRABIDIUM:
			fuel = "Schrabidium";
			break;
		case THORIUM:
			fuel = "Thorium";
			break;
		default:
			fuel = "ERROR";
			break;
		}

		this.drawCustomInfo(this, mouseX, mouseY, guiLeft + 98, guiTop + 18, 16, 88, new String[] { fuel + ": " + (diFurnace.fuel / diFurnace.fuelMult) + "/" + (diFurnace.maxFuel / diFurnace.fuelMult) + "ng" });
		this.drawCustomInfo(this, mouseX, mouseY, guiLeft + 134, guiTop + 18, 16, 88, new String[] { "Depleted " + fuel + ": " + (diFurnace.waste / diFurnace.fuelMult) + "/" + (diFurnace.maxWaste / diFurnace.fuelMult) + "ng" });
		
		String[] text0 = new String[] { diFurnace.rods > 0 ? "Reactor is ON" : "Reactor is OFF"};
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 52, guiTop + 53, 18, 18, mouseX, mouseY, text0);
		
		String s = "0";
		
		FluidType type = diFurnace.tanks[2].getTankType();
		if(type == Fluids.STEAM) s = "1x";
		if(type == Fluids.HOTSTEAM) s = "10x";
		if(type == Fluids.SUPERHOTSTEAM) s = "100x";
		
		String[] text4 = new String[] { "Steam compression switch",
				"Current compression level: " + s};
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 63, guiTop + 107, 14, 18, mouseX, mouseY, text4);
	}

	protected void mouseClicked(int x, int y, int i) {
    	super.mouseClicked(x, y, i);
    	
    	if(guiLeft + 115 <= x && guiLeft + 115 + 18 > x && guiTop + 17 < y && guiTop + 17 + 90 >= y) {

			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			
			int rods = (y - (guiTop + 24)) * 100 / 76;
			
			if(rods < 0)
				rods = 0;
			
			if(rods > 100)
				rods = 100;
			
			rods = 100 - rods;
			
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(diFurnace.xCoord, diFurnace.yCoord, diFurnace.zCoord, rods, 0));
    	}
		
    	if(guiLeft + 63 <= x && guiLeft + 63 + 14 > x && guiTop + 107 < y && guiTop + 107 + 18 >= y) {
    		
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			int c = 0;
			
			FluidType type = diFurnace.tanks[2].getTankType();
			if(type == Fluids.STEAM) c = 0;
			if(type == Fluids.HOTSTEAM) c = 1;
			if(type == Fluids.SUPERHOTSTEAM) c = 2;
			
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(diFurnace.xCoord, diFurnace.yCoord, diFurnace.zCoord, c, 1));
    	}
    }
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.diFurnace.hasCustomInventoryName() ? this.diFurnace.getInventoryName() : I18n.format(this.diFurnace.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int k = diFurnace.rods;
		drawTexturedModalRect(guiLeft + 115, guiTop + 107 - 14 - (k * 76 / 100), 208, 36, 18, 14);
		
		if(diFurnace.rods > 0)
			drawTexturedModalRect(guiLeft + 52, guiTop + 53, 212, 0, 18, 18);
		
		int q = diFurnace.getFuelScaled(88);
		drawTexturedModalRect(guiLeft + 98, guiTop + 106 - q, 176, 124 - q, 16, q);
		
		int j = diFurnace.getWasteScaled(88);
		drawTexturedModalRect(guiLeft + 134, guiTop + 106 - j, 192, 124 - j, 16, j);
		
		int s = diFurnace.size;
		
		if(s < 8)
			drawTexturedModalRect(guiLeft + 50, guiTop + 17, 208, 50 + s * 18, 22, 18);
		else
			drawTexturedModalRect(guiLeft + 50, guiTop + 17, 230, 50 + (s - 8) * 18, 22, 18);

		
		FluidType type = diFurnace.tanks[2].getTankType();
		if(type == Fluids.STEAM) drawTexturedModalRect(guiLeft + 63, guiTop + 107, 176, 18, 14, 18);
		if(type == Fluids.HOTSTEAM) drawTexturedModalRect(guiLeft + 63, guiTop + 107, 190, 18, 14, 18);
		if(type == Fluids.SUPERHOTSTEAM) drawTexturedModalRect(guiLeft + 63, guiTop + 107, 204, 18, 14, 18);
		
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
		
		if(diFurnace.tanks[2].getFill() > 0) {
			int i = diFurnace.getSteamScaled(88);
			
			//i = (int) Math.min(i, 160);
			
			int offset = 234;
			
			if(type == Fluids.HOTSTEAM) offset += 4;
			if(type == Fluids.SUPERHOTSTEAM) offset += 8;
			
			drawTexturedModalRect(guiLeft + 80, guiTop + 108, 0, offset, i, 4);
		}

		diFurnace.tanks[0].renderTank(guiLeft + 8, guiTop + 88, this.zLevel, 16, 52);
		diFurnace.tanks[1].renderTank(guiLeft + 26, guiTop + 88, this.zLevel, 16, 52);
	}
}
