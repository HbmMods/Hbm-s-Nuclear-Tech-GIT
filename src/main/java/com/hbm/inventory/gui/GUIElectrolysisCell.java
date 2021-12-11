package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.FluidTank;
import com.hbm.inventory.container.ContainerElectrolysisCell;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityElectrolysisCell;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIElectrolysisCell extends GuiInfoContainer {
	
	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_electrolysis_cell.png");
	private TileEntityElectrolysisCell cell;

	public GUIElectrolysisCell(InventoryPlayer invPlayer, TileEntityElectrolysisCell cell) {
		super(new ContainerElectrolysisCell(invPlayer, cell));
		this.cell = cell;
		
		this.xSize = 204;
		this.ySize = 229;
		
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		
		cell.tanks[0].renderTankInfo(this, mouseX, mouseY, guiLeft + 102, guiTop + 18, 26, 16);
		cell.tanks[1].renderTankInfo(this, mouseX, mouseY, guiLeft + 151, guiTop + 43, 26, 16);
		cell.tanks[2].renderTankInfo(this, mouseX, mouseY, guiLeft + 151, guiTop + 61, 26, 16);
		cell.tanks[3].renderTankInfo(this, mouseX, mouseY, guiLeft + 69, guiTop + 46, 34, 7);
		
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 5, guiTop + 5, 16, 199, cell.power, cell.maxPower);
		
		String niterText = "Niter:";
		String niterText2 = cell.niterTank+"/"+cell.maxNiter+"mB";
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 87, guiTop + 74, 16, 16, mouseX, mouseY, new String[] { niterText, niterText2 });
	}

	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);
		
		if(guiLeft + 66 <= x && guiLeft + 66 + 13 > x && guiTop + 14 < y && guiTop + 14 + 24 >= y) {
			
			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(cell.xCoord, cell.yCoord, cell.zCoord, 0, 0));
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int j = (int)cell.getPowerScaled(199);
		drawTexturedModalRect(guiLeft + 5, guiTop + 5 + (199 - j), 204, 0 + (199 - j), 16, j);
		
		if(!cell.casting) {
			drawTexturedModalRect(guiLeft + 66, guiTop + 14, 220, 0, 13, 24);
			int hydroProgress = (int)cell.getHydroProgressScaled(27);
			drawTexturedModalRect(guiLeft + 110, guiTop + 43, 220, 24, 10, hydroProgress);
		}
		
		drawTexturedModalRect(guiLeft + 87, guiTop + 74 + 16 - (int)(((double)cell.niterTank / (double)cell.maxNiter) * 16), 233, 16 - (int)(((double)cell.niterTank / (double)cell.maxNiter) * 16), 16, (int)(((double)cell.niterTank / (double)cell.maxNiter) * 16));
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(cell.tanks[0].getSheet());
		cell.tanks[0].renderTankRot(this, guiLeft + 103, guiTop + 34, cell.tanks[0].getTankType().textureX() * FluidTank.x, cell.tanks[0].getTankType().textureY() * FluidTank.y, 26, 16, this.zLevel);
		
		for(int i = 1; i < 3; i++) {
			Minecraft.getMinecraft().getTextureManager().bindTexture(cell.tanks[i].getSheet());
			cell.tanks[i].renderTankRot(this, guiLeft + 151, guiTop + 61 + (18 * (i-1)), cell.tanks[i].getTankType().textureX() * FluidTank.x, cell.tanks[i].getTankType().textureY() * FluidTank.y, 26, 16, this.zLevel);
		}
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(cell.tanks[3].getSheet());
		cell.tanks[3].renderTankRot(this, guiLeft + 69, guiTop + 53, cell.tanks[3].getTankType().textureX() * FluidTank.x, cell.tanks[3].getTankType().textureY() * FluidTank.y, 34, 7, this.zLevel);
		
		
	}
	

}
