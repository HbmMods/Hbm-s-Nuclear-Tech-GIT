package com.hbm.qmaw;

public abstract class ManualElement {

	public abstract int getWidth();
	public abstract int getHeight();
	public abstract void render(boolean isMouseOver, int x, int y, int mouseX, int mouseY);
	public abstract void onClick(GuiQMAW gui);
}
