package com.hbm.inventory.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.container.ContainerElectricFurnace;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineElectricFurnace;
import com.hbm.util.i18n.I18nUtil;

public class GUIMachineElectricFurnace extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/GUIElectricFurnace.png");
	private TileEntityMachineElectricFurnace furnace;

	public GUIMachineElectricFurnace(InventoryPlayer invPlayer, TileEntityMachineElectricFurnace furnace) {
		super(new ContainerElectricFurnace(invPlayer, furnace));
		this.furnace = furnace;
		
		this.xSize = 176;
		this.ySize = 186;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 152, guiTop + 52 - 34, 16, 34, furnace.power, furnace.maxPower);
		
		String[] upgradeText = new String[3];
		upgradeText[0] = I18nUtil.resolveKey("desc.gui.upgrade");
		upgradeText[1] = I18nUtil.resolveKey("desc.gui.upgrade.speed");
		upgradeText[2] = I18nUtil.resolveKey("desc.gui.upgrade.power");
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 116, guiTop + 20, 8, 8, mouseX, mouseY, upgradeText);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.furnace.hasCustomInventoryName() ? this.furnace.getInventoryName() : I18n.format(this.furnace.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		//failsafe TE clone
		//if initial TE invalidates, new TE is fetched
		//if initial ZE is still present, it'll be used instead
		//works so that container packets can still be used
		//efficiency!
		if(furnace.isInvalid() && furnace.getWorldObj().getTileEntity(furnace.xCoord, furnace.yCoord, furnace.zCoord) instanceof TileEntityMachineElectricFurnace)
			furnace = (TileEntityMachineElectricFurnace) furnace.getWorldObj().getTileEntity(furnace.xCoord, furnace.yCoord, furnace.zCoord);
		
		if(furnace.hasPower()) {
			int p = (int) furnace.getPowerScaled(34);
			drawTexturedModalRect(guiLeft + 152, guiTop + 52 - p, 176, 64 - p, 16, p);
		}
		
		if(furnace.getWorldObj().getBlock(furnace.xCoord, furnace.yCoord, furnace.zCoord) == ModBlocks.machine_electric_furnace_on) {
			drawTexturedModalRect(guiLeft + 45, guiTop + 20, 192, 12, 18, 16);
			drawTexturedModalRect(guiLeft + 45, guiTop + 47, 192, 36, 18, 16);
		}
		
		int p = furnace.getProgressScaled(28);
		drawTexturedModalRect(guiLeft + 43, guiTop + 36, 176, 0, p, 12);
		
		this.drawInfoPanel(guiLeft + 116, guiTop + 20, 8, 8, 8);
	}

}
