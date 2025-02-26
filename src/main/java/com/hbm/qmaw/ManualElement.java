package com.hbm.qmaw;

public abstract class ManualElement {
	
	public int x;
	public int y;

	public abstract int getWidth();
	public abstract int getHeight();
	public abstract void render(boolean isMouseOver, int mouseX, int mouseY);
	public abstract void onClick();
}
