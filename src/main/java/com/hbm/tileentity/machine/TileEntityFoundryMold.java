package com.hbm.tileentity.machine;

import com.hbm.inventory.material.NTMMaterial;

public class TileEntityFoundryMold extends TileEntityFoundryCastingBase implements IRenderFoundry {

	public TileEntityFoundryMold() {
		super(2);
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
	}

	@Override
	public int getMoldSize() {
		return 0;
	}

	@Override
	public boolean shouldRender() {
		return this.type != null && this.amount > 0;
	}

	@Override
	public double getLevel() {
		return 0.125 + this.amount * 0.25D / this.getCapacity();
	}

	@Override
	public NTMMaterial getMat() {
		return this.type;
	}

	@Override public double minX() { return 0.125D; }
	@Override public double maxX() { return 0.875D; }
	@Override public double minZ() { return 0.125D; }
	@Override public double maxZ() { return 0.875D; }
	@Override public double moldHeight() { return 0.13D; }
	@Override public double outHeight() { return 0.25D; }
}
