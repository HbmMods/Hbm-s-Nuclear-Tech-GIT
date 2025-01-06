package com.hbm.tileentity.machine;

import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.inventory.material.NTMMaterial;

import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityFoundryBasin extends TileEntityFoundryCastingBase implements IRenderFoundry {

	public TileEntityFoundryBasin() {
		super(2);
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
	}

	@Override
	public int getMoldSize() {
		return 1;
	}

	/* Basin can't accept sideways flowing */
	@Override public boolean canAcceptPartialFlow(World world, int x, int y, int z, ForgeDirection side, MaterialStack stack) { return false; }
	@Override public MaterialStack flow(World world, int x, int y, int z, ForgeDirection side, MaterialStack stack) { return stack; }

	@Override
	public boolean shouldRender() {
		return this.type != null && this.amount > 0;
	}

	@Override
	public double getLevel() {
		return 0.125 + this.amount * 0.75D / this.getCapacity();
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
	@Override public double outHeight() { return 0.875D; }
}
