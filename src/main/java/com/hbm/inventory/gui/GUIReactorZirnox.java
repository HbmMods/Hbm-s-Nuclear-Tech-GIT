package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.FluidTank;
import com.hbm.inventory.container.ContainerReactorZirnox;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityReactorZirnox;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIReactorZirnox extends GuiInfoContainer {
	//fuck you
	private static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID, "textures/gui/reactors/gui_zirnox.png");
	private TileEntityReactorZirnox zirnox;
	
	public GUIReactorZirnox(InventoryPlayer invPlayer, TileEntityReactorZirnox tile) {
		super(new ContainerReactorZirnox(invPlayer, tile));
		zirnox = tile;

		this.xSize = 203;
		this.ySize = 256;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		zirnox.steam.renderTankInfo(this, mouseX, mouseY, guiLeft + 160, guiTop + 108, 18, 12);
		zirnox.carbonDioxide.renderTankInfo(this, mouseX, mouseY, guiLeft + 142, guiTop + 108, 18, 12);
		zirnox.water.renderTankInfo(this, mouseX, mouseY, guiLeft + 178, guiTop + 108, 18, 12);
		this.drawCustomInfo(this, mouseX, mouseY, guiLeft + 160, guiTop + 33, 18, 17, new String[] { "Temperature:", "   " + Math.round((zirnox.heat) * 0.00001 * 480 + 20) + "°C" });
		this.drawCustomInfo(this, mouseX, mouseY, guiLeft + 178, guiTop + 33, 18, 17, new String[] { "Pressure:", "   " + Math.round((zirnox.pressure) * 0.00001 * 30) + " bar" });
		
		String[] info = new String[] { "CO2 transfers heat from the core", "to the water. This will boil", "the water into steam.", "Water Consumption Rate:", "100 mB/t", "2000 mB/s" };
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36, 16, 16, guiLeft - 8, guiTop + 36 + 16, info);
		
		String[] info2 = new String[] { "Pressure can be reduced by",
				"reducing the amount of CO2",
				"in the reactor." };
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36 + 16, 16, 16, guiLeft - 8, guiTop + 36 + 16, info2);

		if(zirnox.water.getFill() <= 0) {
			String[] warn1 = new String[] { "Error: Water is required for",
					"the reactor to function properly!" };
			this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36 + 32, 16, 16, guiLeft - 8, guiTop + 36 + 32 + 16, warn1);
		}

		if(zirnox.carbonDioxide.getFill() <= 12000) {
			String[] warn2 = new String[] { "Error: CO2 is required for",
					"the reactor to function properly!" };
			this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36 + 32 + 16, 16, 16, guiLeft - 8, guiTop + 36 + 32 + 16, warn2);
		}
		
	}
	
	protected void mouseClicked(int x, int y, int i) {
    	super.mouseClicked(x, y, i);
		
    	if(guiLeft + 144 <= x && guiLeft + 144 + 14 > x && guiTop + 35 < y && guiTop + 35 + 14 >= y) {
    		
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(zirnox.xCoord, zirnox.yCoord, zirnox.zCoord, 0, 0));
    	}
    }
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.zirnox.hasCustomInventoryName() ? this.zirnox.getInventoryName() : I18n.format(this.zirnox.getInventoryName());

		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int s = zirnox.getGaugeScaled(6, 0);
		drawTexturedModalRect(guiLeft + 160, guiTop + 108, 238, 0 + 12 * s, 18, 12);
		
		int c = zirnox.getGaugeScaled(6, 1);
        drawTexturedModalRect(guiLeft + 142, guiTop + 108, 238, 0 + 12 * c, 18, 12);
		
		int w = zirnox.getGaugeScaled(6, 2);
        drawTexturedModalRect(guiLeft + 178, guiTop + 108, 238, 0 + 12 * w, 18, 12);
        
        int h = zirnox.getGaugeScaled(12, 3);
        drawTexturedModalRect(guiLeft + 160, guiTop + 33, 220, 0 + 18 * h, 18, 17);
		
		int p = zirnox.getGaugeScaled(12, 4);
        drawTexturedModalRect(guiLeft + 178, guiTop + 33, 220, 0 + 18 * p, 18, 17);
		
		if(zirnox.isOn) {
				for(int x = 0; x < 4; x++)
					for(int y = 0; y < 4; y++)
						drawTexturedModalRect(guiLeft + 7 + 36 * x, guiTop + 15 + 36 * y, 238, 238, 18, 18);
				for(int x = 0; x < 3; x++)
					for(int y = 0; y < 3; y++)
						drawTexturedModalRect(guiLeft + 25 + 36 * x, guiTop + 33 + 36 * y, 238, 238, 18, 18);
				drawTexturedModalRect(guiLeft + 142, guiTop + 15, 220, 238, 18, 18);
		}
		
		this.drawInfoPanel(guiLeft - 16, guiTop + 36, 16, 16, 2);
		this.drawInfoPanel(guiLeft - 16, guiTop + 36 + 16, 16, 16, 3);
		
		if(zirnox.water.getFill() <= 0)
			this.drawInfoPanel(guiLeft - 16, guiTop + 36 + 32, 16, 16, 6);
		
		if(zirnox.carbonDioxide.getFill() <= 12000)
			this.drawInfoPanel(guiLeft - 16, guiTop + 36 + 32 + 16, 16, 16, 7);
	}
	
}
