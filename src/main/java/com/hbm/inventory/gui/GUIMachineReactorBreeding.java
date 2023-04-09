package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineReactorBreeding;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineReactorBreeding;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineReactorBreeding extends GuiInfoContainer {
	
	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_breeder.png");
	private TileEntityMachineReactorBreeding breeder;

	public GUIMachineReactorBreeding(InventoryPlayer invPlayer, TileEntityMachineReactorBreeding tedf) {
		super(new ContainerMachineReactorBreeding(invPlayer, tedf));
		breeder = tedf;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		
		String[] text = new String[] {
				"The reactor has to recieve",
				"neutron flux from adjacent",
				"research reactors to breed."
		};
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 16, 16, 16, guiLeft - 8, guiTop + 16 + 16, text);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.breeder.hasCustomInventoryName() ? this.breeder.getInventoryName() : I18n.format(this.breeder.getInventoryName());
		
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
		this.fontRendererObj.drawString(breeder.flux + "", 88 - this.fontRendererObj.getStringWidth(breeder.flux + "") / 2, 21, 0x08FF00);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		/*
		 * A dud is a tile entity which did not survive a block state change (i.e. a furnace becoming lit) on the client.
		 * Usually, most functionality is preserved since vanilla interacts with the open GUI screen rather than the TE
		 * itself, though this does not apply to NTM packets. The client will think the TE bound to the GUI is invalid,
		 * and therefore miss out on NTM status packets, but it will still require the old TE for slot changes. The refreshed
		 * "dud" is only used for status bars, it will not replace the actual invalid TE instance in the GUI screen.
		 * 
		 * what?
		 */
				
		int i = breeder.getProgressScaled(70);
		drawTexturedModalRect(guiLeft + 53, guiTop + 32, 176, 0, i, 20);
		
		this.drawInfoPanel(guiLeft - 16, guiTop + 16, 16, 16, 3);
	}

}