package com.hbm.inventory.gui;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerBlastFurnace;
import com.hbm.inventory.gui.element.GUIElements;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineBlastFurnace;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

public class GUIBlastFurnace extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_blast_furnace.png");
	private TileEntityMachineBlastFurnace furnace;

	public GUIBlastFurnace(InventoryPlayer invPlayer, TileEntityMachineBlastFurnace tedf) {
		super(new ContainerBlastFurnace(invPlayer, tedf));
		furnace = tedf;
		
		this.xSize = 176;
		this.ySize = 222;
	}
	
	@Override
	public void drawScreen(int x, int y, float interp) {
		super.drawScreen(x, y, interp);

		if(this.mc.thePlayer.inventory.getItemStack() == null) {
			Slot slot = (Slot) this.inventorySlots.inventorySlots.get(0);
			if(this.isMouseOverSlot(slot, x, y) && !slot.getHasStack()) {
				List<String> bonuses = this.furnace.burnModule.getHeatDesc();
				if(!bonuses.isEmpty()) this.func_146283_a(bonuses, x, y);
			}
		}
		
		String label = "Speed: " + (int) (furnace.speed * 100) + "%";
		drawCustomInfoStat(x, y, guiLeft + 79, guiTop + 62, 18, 18, x, y, label);

		furnace.tanks[0].renderTankInfo(this, x, y, guiLeft + 25, guiTop + 71, 18, 18);
		furnace.tanks[1].renderTankInfo(this, x, y, guiLeft + 25, guiTop + 17, 18, 18);
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
		
		int prog = (int) Math.round(furnace.progress * 88D);
		drawTexturedModalRect(guiLeft + 62, guiTop + 106 - prog, 176, 102 - prog, 56, prog);
		
		int fuel = (int) Math.round((double) furnace.fuel * 26D / (double) furnace.MAX_FUEL);
		drawTexturedModalRect(guiLeft + 62, guiTop + 106 - fuel, 176, 128 - fuel, 56, fuel);
		
		if(furnace.isProgressing) {
			drawTexturedModalRect(guiLeft + 81, guiTop + 64, 176, 0, 14, 14);
		}

		GUIElements.drawSmoothGauge(guiLeft + 34, guiTop + 80, this.zLevel, (double) furnace.tanks[0].getFill() / (double) furnace.tanks[0].getMaxFill(), 5, 2, 1, 0x800000);
		GUIElements.drawSmoothGauge(guiLeft + 34, guiTop + 26, this.zLevel, (double) furnace.tanks[1].getFill() / (double) furnace.tanks[1].getMaxFill(), 5, 2, 1, 0x800000);
	}
}
