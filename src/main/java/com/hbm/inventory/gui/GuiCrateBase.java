package com.hbm.inventory.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

public abstract class GuiCrateBase extends GuiContainer {
	
	public GuiCrateBase(Container container) {
		super(container);
	}
	
	@Override
	protected void mouseClicked(int x, int y, int button) {
		boolean touchScreen = this.mc.gameSettings.touchscreen;
		this.mc.gameSettings.touchscreen = false;
		super.mouseClicked(x, y, button);
		this.mc.gameSettings.touchscreen = touchScreen;
	}
}
