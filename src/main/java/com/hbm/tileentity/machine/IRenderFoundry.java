package com.hbm.tileentity.machine;

import com.hbm.inventory.material.NTMMaterial;

public interface IRenderFoundry {

	public boolean shouldRender();
	public double getLevel();
	public NTMMaterial getMat();
	public double minX();
	public double maxX();
	public double minZ();
	public double maxZ();
}
