package com.hbm.interfaces;

import java.util.List;

public interface ISource {
	
	void ffgeuaInit();

	void ffgeua(int x, int y, int z, boolean newTact);

	boolean getTact();
	int getSPower();
	void setSPower(int i);
	List<IConsumer> getList();
	void clearList();
}
