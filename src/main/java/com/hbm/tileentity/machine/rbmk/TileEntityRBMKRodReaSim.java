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
		
		for(int i = 0; i < count; i++) {
			
			stream = type;
			double flux = fluxOut * RBMKDials.getReaSimOutputMod(worldObj);
			
			dir.rotateAroundY((float)(Math.PI * 2D * worldObj.rand.nextDouble()));
			
			for(int j = 1; j <= range; j++) {

				int x = (int)Math.floor(0.5 + dir.xCoord * j);
				int z = (int)Math.floor(0.5 + dir.zCoord * j);
				int lastX = (int)Math.floor(0.5 + dir.xCoord * (j - 1));
				int lastZ = (int)Math.floor(0.5 + dir.zCoord * (j - 1));
				
				//skip if the position is on the rod itself
				if(x == 0 && z == 0)
					continue;
				
				//skip if the current position is equal to the last position
				if(x == lastX && z == lastZ)
					continue;
				
				flux = runInteraction(xCoord + x, yCoord, zCoord + z, flux);
				
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
