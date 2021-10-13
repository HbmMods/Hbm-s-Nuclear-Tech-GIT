package com.hbm.tileentity.machine.rbmk;

import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole.ColumnType;

import net.minecraft.util.Vec3;

public class TileEntityRBMKRodReaSim extends TileEntityRBMKRod {
	
	public TileEntityRBMKRodReaSim() {
		super();
	}

	@Override
	public String getName() {
		return "container.rbmkReaSim";
	}
	
	@Override
	protected void spreadFlux(NType type, double fluxOut) {

		int range = RBMKDials.getReaSimRange(worldObj);
		int count = RBMKDials.getReaSimCount(worldObj);
		
		Vec3 dir = Vec3.createVectorHelper(1, 0, 0);
		
		for(int i = 1; i < count; i++) {
			
			stream = type;
			double flux = fluxOut * RBMKDials.getReaSimOutputMod(worldObj);
			
			dir.rotateAroundY((float)(Math.PI * 2D * worldObj.rand.nextDouble()));
			
			for(int j = 1; j <= range; j++) {
				
				//skip if the position is on the rod itself
				if((int)Math.floor(dir.xCoord * j) == 0 && (int)Math.floor(dir.zCoord * j) == 0)
					continue;
				
				//skip if the current position is equal to the last position
				if((int)Math.floor(dir.xCoord * j) == (int)Math.floor(dir.xCoord * (j - 1)) && (int)Math.floor(dir.zCoord * j) == (int)Math.floor(dir.zCoord * (j - 1)))
					continue;
				
				flux = runInteraction(xCoord + (int)Math.floor(dir.xCoord * j), yCoord, zCoord + (int)Math.floor(dir.zCoord * j), flux);
				
				if(flux <= 0)
					break;
			}
		}
	}

	@Override
	public ColumnType getConsoleType() {
		return ColumnType.FUEL_SIM;
	}
}
