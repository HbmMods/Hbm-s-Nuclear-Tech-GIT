package com.hbm.inventory.gui;

import java.util.Arrays;

import com.hbm.lib.Library;
import com.hbm.lib.RefStrings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public abstract class GuiInfoContainer extends GuiContainer {
	
	ResourceLocation guiUtil =  new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_utility.png");

	public GuiInfoContainer(Container p_i1072_1_) {
		super(p_i1072_1_);
	}
	
	public void drawFluidInfo(String[] text, int x, int y) {
		this.func_146283_a(Arrays.asList(text), x, y);
	}
	
	public void drawElectricityInfo(GuiInfoContainer gui, int mouseX, int mouseY, int x, int y, int width, int height, long power, long maxPower) {
		if(x <= mouseX && x + width > mouseX && y < mouseY && y + height >= mouseY)
			gui.drawFluidInfo(new String[] { Library.getShortNumber(power) + "/" + Library.getShortNumber(maxPower) + "HE" }, mouseX, mouseY);
	}
	
	public void drawCustomInfo(GuiInfoContainer gui, int mouseX, int mouseY, int x, int y, int width, int height, String[] text) {
		if(x <= mouseX && x + width > mouseX && y < mouseY && y + height >= mouseY)
			this.func_146283_a(Arrays.asList(text), mouseX, mouseY);
	}
	
	public void drawCustomInfoStat(int mouseX, int mouseY, int x, int y, int width, int height, int tPosX, int tPosY, String[] text) {
		
		if(x <= mouseX && x + width > mouseX && y < mouseY && y + height >= mouseY)
			this.func_146283_a(Arrays.asList(text), tPosX, tPosY);
	}
	
	public void drawInfoPanel(int x, int y, int width, int height, int type) {

		Minecraft.getMinecraft().getTextureManager().bindTexture(guiUtil);
		
		switch(type) {
		case 0:
			//Small blue I
			drawTexturedModalRect(x, y, 0, 0, 8, 8); break;
		case 1:
			//Small green I
			drawTexturedModalRect(x, y, 0, 8, 8, 8); break;
		case 2:
			//Large blue I
			drawTexturedModalRect(x, y, 8, 0, 16, 16); break;
		case 3:
			//Large green I
			drawTexturedModalRect(x, y, 24, 0, 16, 16); break;
		case 4:
			//Small red !
			drawTexturedModalRect(x, y, 0, 16, 8, 8); break;
		case 5:
			//Small yellow !
			drawTexturedModalRect(x, y, 0, 24, 8, 8); break;
		case 6:
			//Large red !
			drawTexturedModalRect(x, y, 8, 16, 16, 16); break;
		case 7:
			//Large yellow !
			drawTexturedModalRect(x, y, 24, 16, 16, 16); break;
		case 8:
			//Small blue *
			drawTexturedModalRect(x, y, 0, 32, 8, 8); break;
		case 9:
			//Small grey *
			drawTexturedModalRect(x, y, 0, 40, 8, 8); break;
		case 10:
			//Large blue *
			drawTexturedModalRect(x, y, 8, 32, 16, 16); break;
		case 11:
			//Large grey *
			drawTexturedModalRect(x, y, 24, 32, 16, 16); break;
		}
	}

}
