package com.hbm.inventory.gui;

import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.turret.TileEntityTurretBaseNT;
import com.hbm.tileentity.turret.TileEntityTurretFritz;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUITurretFritz extends GUITurretBase {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/weapon/gui_turret_fritz.png");

	public GUITurretFritz(InventoryPlayer invPlayer, TileEntityTurretBaseNT tedf) {
		super(invPlayer, tedf);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		((TileEntityTurretFritz)this.turret).tank.renderTankInfo(this, mouseX, mouseY, guiLeft + 134, guiTop + 63, 7, 52);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int mX, int mY) {
		super.drawGuiContainerBackgroundLayer(p_146976_1_, mX, mY);
		
		FluidTank tank = ((TileEntityTurretFritz)this.turret).tank;
		tank.renderTank(guiLeft + 134, guiTop + 115, this.zLevel, 7, 52);
	}
	
	protected ResourceLocation getTexture() {
		return texture;
	}
}
