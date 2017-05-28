package com.hbm.interfaces;

import java.util.List;

public interface IOilSource {
	
	void fillInit();

	void fill(int x, int y, int z, boolean newTact);

	boolean getTact();
	int getSFill();
	void setSFill(int i);
	List<IOilAcceptor> getList();
	void clearList();

}
