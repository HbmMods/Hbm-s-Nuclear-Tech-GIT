package com.hbm.interfaces;

public interface IDoor {

	public void open();
	public void close();
	public DoorState getState();
	public void toggle();
	public default boolean setTexture(String tex) {
		return false;
	}
	public default void setTextureState(byte tex) { };
	
	public enum DoorState {
		CLOSED,
		OPEN,
		CLOSING,
		OPENING;
	}
}
