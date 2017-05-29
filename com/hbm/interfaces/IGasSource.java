package com.hbm.interfaces;

import java.util.List;

public interface IGasSource {
	
	void fillGasInit();

	void fillGas(int x, int y, int z, boolean newTact);

	boolean getTact();
	int getGasFill();
	void setGasFill(int i);
	List<IGasAcceptor> getGasList();
	void clearGasList();

}
