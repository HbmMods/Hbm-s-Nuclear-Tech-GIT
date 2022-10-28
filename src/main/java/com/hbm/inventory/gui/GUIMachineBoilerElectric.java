package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineBoilerElectric;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineBoilerElectric;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineBoilerElectric extends GuiInfoContainer {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_boiler_electric.png");
	private TileEntityMachineBoilerElectric diFurnace;
	
	public GUIMachineBoilerElectric(InventoryPlayer invPlayer, TileEntityMachineBoilerElectric tedf) {
		super(new ContainerMachineBoilerElectric(invPlayer, tedf));
		diFurnace = tedf;

		this.xSize = 176;
		this.ySize = 168;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		diFurnace.tanks[0].renderTankInfo(this, mouseX, mouseY, guiLeft + 62, guiTop + 69 - 52, 16, 52);
		diFurnace.tanks[1].renderTankInfo(this, mouseX, mouseY, guiLeft + 134, guiTop + 69 - 52, 16, 52);

		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 102, guiTop + 16, 8, 18, mouseX, mouseY, new String[] { String.valueOf((int)((double)diFurnace.heat / 100D)) + "°C"});
		
		String[] text = new String[] { "Heat produced:",
				"  1.5°C/t",
				"  or 30°C/s",
				"Heat consumed:",
				"  0.15°C/t",
				"  or 3.0°C/s (base)",
				"  0.25°C/t",
				"  or 5.0°C/t (once boiling point is reached)",
				"  0.4°C/t",
				"  or 8.0°C/t (for every subsequent multiple of boiling point)" };
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36, 16, 16, guiLeft - 8, guiTop + 36 + 16, text);
		
		String[] text1 = new String[] { "Boiling rate:",
				"  Base rate * amount of full multiples",
				"  of boiling points reached" };
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36 + 16, 16, 16, guiLeft - 8, guiTop + 36 + 16, text1);
		
		if(diFurnace.tanks[1].getTankType().name().equals(Fluids.NONE.name())) {
			
			String[] text2 = new String[] { "Error: Liquid can not be boiled!" };
			this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36 + 32, 16, 16, guiLeft - 8, guiTop + 36 + 16 + 32, text2);
		}
		
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 123, guiTop + 69 - 34, 7, 34, diFurnace.power, diFurnace.maxPower);
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
		
		if(diFurnace.isInvalid() && diFurnace.getWorldObj().getTileEntity(diFurnace.xCoord, diFurnace.yCoord, diFurnace.zCoord) instanceof TileEntityMachineBoilerElectric)
			diFurnace = (TileEntityMachineBoilerElectric) diFurnace.getWorldObj().getTileEntity(diFurnace.xCoord, diFurnace.yCoord, diFurnace.zCoord);

		if(diFurnace.power > 0)
			drawTexturedModalRect(guiLeft + 97, guiTop + 34, 176, 0, 18, 18);

		int j = (int)diFurnace.getHeatScaled(17);
		drawTexturedModalRect(guiLeft + 103, guiTop + 33 - j, 194, 16 - j, 6, j);

		int i = (int)diFurnace.getPowerScaled(34);
		drawTexturedModalRect(guiLeft + 123, guiTop + 69 - i, 200, 34 - i, 7, i);

		this.drawInfoPanel(guiLeft - 16, guiTop + 36, 16, 16, 2);
		this.drawInfoPanel(guiLeft - 16, guiTop + 36 + 16, 16, 16, 3);
		
		if(diFurnace.tanks[1].getTankType().name().equals(Fluids.NONE.name())) {
			this.drawInfoPanel(guiLeft - 16, guiTop + 36 + 32, 16, 16, 6);
		}
		
		diFurnace.tanks[0].renderTank(guiLeft + 62, guiTop + 69, this.zLevel, 16, 52);
		diFurnace.tanks[1].renderTank(guiLeft + 134, guiTop + 69, this.zLevel, 16, 52);
	}
}
