package com.hbm.qmaw;

public interface IManualElement {

	public int getWidth();
	public int getHeight();
	public void render(boolean isMouseOver, int mouseX, int mouseY);
	public void onClick();
}
