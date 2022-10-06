package com.hbm.inventory.gui;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerFirebox;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityHeaterFirebox;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

public class GUIFirebox extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_firebox.png");
	private TileEntityHeaterFirebox firebox;

	public GUIFirebox(InventoryPlayer invPlayer, TileEntityHeaterFirebox tedf) {
		super(new ContainerFirebox(invPlayer, tedf));
		firebox = tedf;
		
		this.xSize = 176;
		this.ySize = 168;
	}
	
	@Override
	public void drawScreen(int x, int y, float interp) {
		super.drawScreen(x, y, interp);

		if(this.mc.thePlayer.inventory.getItemStack() == null) {
			
			for(int i = 0; i < 2; ++i) {
				Slot slot = (Slot) this.inventorySlots.inventorySlots.get(i);
				
				if(this.isMouseOverSlot(slot, x, y) && !slot.getHasStack()) {
					
					List<String> bonuses = this.firebox.burnModule.getDesc();
					
					if(!bonuses.isEmpty()) {
						this.func_146283_a(bonuses, x, y);
					}
				}
			}
		}

		this.drawCustomInfoStat(x, y, guiLeft + 80, guiTop + 27, 71, 7, x, y, new String[] { String.format("%,d", firebox.heatEnergy) + " / " + String.format("%,d", firebox.maxHeatEnergy) + "TU" });
		this.drawCustomInfoStat(x, y, guiLeft + 80, guiTop + 36, 71, 7, x, y, new String[] { firebox.burnHeat + "TU/s", (firebox.burnTime / 20) + "s" });
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.firebox.hasCustomInventoryName() ? this.firebox.getInventoryName() : I18n.format(this.firebox.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int i = firebox.heatEnergy * 69 / firebox.maxHeatEnergy;
		drawTexturedModalRect(guiLeft + 81, guiTop + 28, 176, 0, i, 5);
		
		int j = firebox.burnTime * 70 / Math.max(firebox.maxBurnTime, 1);
		drawTexturedModalRect(guiLeft + 81, guiTop + 37, 176, 5, j, 5);
		
		if(firebox.wasOn) {
			drawTexturedModalRect(guiLeft + 25, guiTop + 26, 176, 10, 18, 18);
		}
	}
}
