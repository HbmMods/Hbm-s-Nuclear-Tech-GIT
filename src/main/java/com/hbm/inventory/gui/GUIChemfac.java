package com.hbm.inventory.gui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerChemfac;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineChemfac;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

public class GUIChemfac extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_chemfac.png");
	private TileEntityMachineChemfac chemfac;

	public GUIChemfac(InventoryPlayer invPlayer, TileEntityMachineChemfac tedf) {
		super(new ContainerChemfac(invPlayer, tedf));
		chemfac = tedf;
		
		this.xSize = 256;
		this.ySize = 256;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 234, guiTop + 25, 16, 52, chemfac.power, chemfac.getMaxPower());
		
		for(int i = 0; i < 8; i ++) {

			int offX = guiLeft + 110 * (i % 2);
			int offY = guiTop + 38 * (i / 2);
			chemfac.tanks[i * 4 + 0].renderTankInfo(this, mouseX, mouseY, offX + 40, offY + 45 - 32, 5, 34);
			chemfac.tanks[i * 4 + 1].renderTankInfo(this, mouseX, mouseY, offX + 45, offY + 45 - 32, 5, 34);
			chemfac.tanks[i * 4 + 2].renderTankInfo(this, mouseX, mouseY, offX + 102, offY + 45 - 32, 5, 34);
			chemfac.tanks[i * 4 + 3].renderTankInfo(this, mouseX, mouseY, offX + 107, offY + 45 - 32, 5, 34);
		}

		chemfac.water.renderTankInfo(this, mouseX, mouseY, guiLeft + 233, guiTop + 108, 9, 54);
		chemfac.steam.renderTankInfo(this, mouseX, mouseY, guiLeft + 242, guiTop + 108, 9, 54);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) { }

	@Override
	protected void drawGuiContainerBackgroundLayer(float interp, int mX, int mY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, 167);
		drawTexturedModalRect(guiLeft + 26, guiTop + 167, 26, 167, 230, 44);
		drawTexturedModalRect(guiLeft + 26, guiTop + 211, 26, 211, 176, 45);
		
		int p = (int) (chemfac.power * 52 / chemfac.getMaxPower());
		drawTexturedModalRect(guiLeft + 234, guiTop + 77 - p, 0, 219 - p, 16, p);
		
		if(chemfac.power > 0)
			drawTexturedModalRect(guiLeft + 238, guiTop + 11, 0, 219, 9, 12);
		
		for(int i = 0; i < 8; i ++) {

			int offX = guiLeft + 110 * (i % 2);
			int offY = guiTop + 38 * (i / 2);
			
			int prog = chemfac.progress[i];
			int j = prog * 17 / Math.max(chemfac.maxProgress[i], 1);
			Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
			drawTexturedModalRect(offX + 51, offY + 16, 202, 247, j, 11);
			
			chemfac.tanks[i * 4 + 0].renderTank(offX + 41, offY + 46, this.zLevel, 3, 32);
			chemfac.tanks[i * 4 + 1].renderTank(offX + 46, offY + 46, this.zLevel, 3, 32);
			chemfac.tanks[i * 4 + 2].renderTank(offX + 103, offY + 46, this.zLevel, 3, 32);
			chemfac.tanks[i * 4 + 3].renderTank(offX + 108, offY + 46, this.zLevel, 3, 32);
		}

		chemfac.water.renderTank(guiLeft + 234, guiTop + 161, this.zLevel, 7, 52);
		chemfac.steam.renderTank(guiLeft + 243, guiTop + 161, this.zLevel, 7, 52);
		
		if(Keyboard.isKeyDown(Keyboard.KEY_LMENU))
		for(int i = 0; i < this.inventorySlots.inventorySlots.size(); i++) {
			Slot s = this.inventorySlots.getSlot(i);

			this.fontRendererObj.drawStringWithShadow(i + "", guiLeft + s.xDisplayPosition + 2, guiTop + s.yDisplayPosition, 0xffffff);
			this.fontRendererObj.drawStringWithShadow(s.getSlotIndex() + "", guiLeft + s.xDisplayPosition + 2, guiTop + s.yDisplayPosition + 8, 0xff8080);
		}
	}
}
