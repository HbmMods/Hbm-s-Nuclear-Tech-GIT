package com.hbm.tileentity.machine;

import java.io.IOException;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.lib.Library;
import com.hbm.tileentity.IConfigurableMachine;
import com.hbm.util.fauxpointtwelve.DirPos;

import net.minecraft.util.AxisAlignedBB;

public class TileEntityMachineRadarLarge extends TileEntityMachineRadarNT {
	
	public static int radarLargeRange = 3_000;

	@Override
	public String getConfigName() {
		return "radar_large";
	}

	@Override
	public void readIfPresent(JsonObject obj) {
		radarLargeRange = IConfigurableMachine.grab(obj, "I:radarLargeRange", radarLargeRange);
	}

	@Override
	public void writeConfig(JsonWriter writer) throws IOException {
		writer.name("I:radarLargeRange").value(radarLargeRange);
	}
	
	@Override
	public int getRange() {
		return radarLargeRange;
	}

	@Override
	public DirPos[] getConPos() {
		return new DirPos[] {
				new DirPos(xCoord + 2, yCoord, zCoord, Library.POS_X),
				new DirPos(xCoord - 2, yCoord, zCoord, Library.NEG_X),
				new DirPos(xCoord, yCoord, zCoord + 2, Library.POS_Z),
				new DirPos(xCoord, yCoord, zCoord - 2, Library.NEG_Z),
		};
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 5,
					yCoord,
					zCoord - 5,
					xCoord + 6,
					yCoord + 10,
					zCoord + 6
					);
		}
		
		return bb;
	}
}
