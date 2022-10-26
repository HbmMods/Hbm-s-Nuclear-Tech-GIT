package com.hbm.tileentity.machine;

import com.hbm.inventory.material.NTMMaterial;

public interface IRenderFoundry {

	/** Returns whether a molten metal layer should be rendered in the TESR */
	public boolean shouldRender();
	/** Returns the Y-offset of the molten metal layer */
	public double getLevel();
	/** Returns the NTM Mat used, mainly for the color */
	public NTMMaterial getMat();
	
	/* Return size constraints for the rectangle */
	public double minX();
	public double maxX();
	public double minZ();
	public double maxZ();
	public double moldHeight();
	public double outHeight();
}
