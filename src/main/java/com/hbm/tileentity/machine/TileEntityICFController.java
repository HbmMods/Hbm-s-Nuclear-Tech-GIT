package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.hbm.tileentity.TileEntityTickingBase;
import com.hbm.util.fauxpointtwelve.BlockPos;

import api.hbm.energymk2.IEnergyReceiverMK2;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityICFController extends TileEntityTickingBase implements IEnergyReceiverMK2 {
	
	public long power;
	
	public int cellCount;
	public int emitterCount;
	public int capacitorCount;
	public int turbochargerCount;

	protected List<BlockPos> ports = new ArrayList();
	
	public boolean assembled;
	
	public void setup(HashSet<BlockPos> ports, HashSet<BlockPos> cells, HashSet<BlockPos> emitters, HashSet<BlockPos> capacitors, HashSet<BlockPos> turbochargers) {

		this.cellCount = 0;
		this.emitterCount = 0;
		this.capacitorCount = 0;
		this.turbochargerCount = 0;
		
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata()).getOpposite();
		BlockPos pos = new BlockPos(0, 0, 0);
		
		for(int i = 0; i < cells.size(); i++) {
			int j = i + 1;
			
			if(cells.contains(pos.mutate(xCoord + dir.offsetX * j, yCoord, zCoord + dir.offsetZ * j))) {
				this.cellCount++;
			} else {
				break;
			}
		}
		
		for(BlockPos emitter : emitters) { for(ForgeDirection offset : ForgeDirection.VALID_DIRECTIONS) {
				pos.mutate(emitter.getX() + offset.offsetX, emitter.getY() + offset.offsetY, emitter.getZ() + offset.offsetZ);
				if(cells.contains(pos)) { this.emitterCount++; break; }
			}
		}
		
		for(BlockPos capacitor : capacitors) { for(ForgeDirection offset : ForgeDirection.VALID_DIRECTIONS) {
				pos.mutate(capacitor.getX() + offset.offsetX, capacitor.getY() + offset.offsetY, capacitor.getZ() + offset.offsetZ);
				if(emitters.contains(pos)) { this.emitterCount++; break; }
			}
		}
		
		for(BlockPos turbo : turbochargers) { for(ForgeDirection offset : ForgeDirection.VALID_DIRECTIONS) {
				pos.mutate(turbo.getX() + offset.offsetX, turbo.getY() + offset.offsetY, turbo.getZ() + offset.offsetZ);
				if(capacitors.contains(pos)) { this.emitterCount++; break; }
			}
		}
		
		this.ports.addAll(ports);
	}

	@Override
	public String getInventoryName() {
		return "container.icfController";
	}

	@Override
	public void updateEntity() {
		
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}

	@Override
	public long getMaxPower() {
		return capacitorCount * 1_000_000 + turbochargerCount * 2_500_000; //TEMP
	}
}
