package com.hbm.interfaces;

import java.util.List;

public interface ISource {
	
	void ffgeuaInit();

	void ffgeua(int x, int y, int z, boolean newTact);

	boolean getTact();
	long getSPower();
	void setSPower(long i);
	List<IConsumer> getList();
	void clearList();
}
