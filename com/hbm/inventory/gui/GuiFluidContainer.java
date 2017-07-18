package com.hbm.inventory.gui;

import java.util.Arrays;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

public abstract class GuiFluidContainer extends GuiContainer {

	public GuiFluidContainer(Container p_i1072_1_) {
		super(p_i1072_1_);
	}
	
	public void drawFluidInfo(String[] text, int x, int y) {
		this.func_146283_a(Arrays.asList(text), x, y);
	}

}
