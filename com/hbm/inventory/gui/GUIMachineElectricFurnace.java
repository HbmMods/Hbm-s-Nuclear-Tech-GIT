package com.hbm.inventory.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerElectricFurnace;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineElectricFurnace;

public class GUIMachineElectricFurnace extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/GUIElectricFurnace.png");
	private TileEntityMachineElectricFurnace diFurnace;

	public GUIMachineElectricFurnace(InventoryPlayer invPlayer, TileEntityMachineElectricFurnace tedf) {
		super(new ContainerElectricFurnace(invPlayer, tedf));
		diFurnace = tedf;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 20, guiTop + 69 - 52, 16, 52, diFurnace.power, diFurnace.maxPower);
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
		
		//failsafe TE clone
		//if initial TE invalidates, new TE is fetched
		//if initial ZE is still present, it'll be used instead
		//works so that container packets can still be used
		//efficiency!
		TileEntityMachineElectricFurnace fs = null;
		
		if(diFurnace.isInvalid() && diFurnace.getWorldObj().getTileEntity(diFurnace.xCoord, diFurnace.yCoord, diFurnace.zCoord) instanceof TileEntityMachineElectricFurnace)
			fs = (TileEntityMachineElectricFurnace) diFurnace.getWorldObj().getTileEntity(diFurnace.xCoord, diFurnace.yCoord, diFurnace.zCoord);
		else
			fs = diFurnace;
		
		if(fs.hasPower()) {
			int i = (int)diFurnace.getPowerRemainingScaled(52);
			drawTexturedModalRect(guiLeft + 20, guiTop + 69 - i, 200, 52 - i, 16, i);
		}
		
		if(diFurnace.canProcess() && diFurnace.hasPower())
		{
			drawTexturedModalRect(guiLeft + 56, guiTop + 36, 176, 0, 15, 16);
		}
		
		int j1 = fs.getDiFurnaceProgressScaled(24);
		drawTexturedModalRect(guiLeft + 79, guiTop + 34, 176, 14, j1 + 1, 17);
	}

}
