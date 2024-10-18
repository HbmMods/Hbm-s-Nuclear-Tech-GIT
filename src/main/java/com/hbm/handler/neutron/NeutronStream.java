package com.hbm.handler.neutron;

import com.hbm.util.fauxpointtwelve.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import com.hbm.handler.neutron.NeutronNodeWorld.StreamWorld;

import java.util.ArrayList;
import java.util.List;

public abstract class NeutronStream {

	public enum NeutronType {
		DUMMY, // Dummy streams for testing
		RBMK,  // RBMK neutron streams
		PILE   // Chicago pile streams
	}

	public NeutronNode origin;

	// doubles!!
	public double fluxQuantity;
	// Hey, new implementation! Basically a ratio for slow flux to fast flux
	// 0 = all slow flux
	// 1 = all fast flux
	public double fluxRatio;

	public NeutronType type = NeutronType.DUMMY;

	// Vector for direction of neutron flow.
	public Vec3 vector;

	// Primarily used as a "dummy stream", not to be added to the streams list.
	public NeutronStream(NeutronNode origin, Vec3 vector) {
		this.origin = origin;
		this.vector = vector;
	}

	public NeutronStream(NeutronNode origin, Vec3 vector, double flux, double ratio, NeutronType type) {
		this.origin = origin;
		this.vector = vector;
		this.fluxQuantity = flux;
		this.fluxRatio = ratio;
		this.type = type;
		World worldObj = origin.tile.getWorldObj();
		if (NeutronNodeWorld.streamWorlds.get(worldObj) == null) {
			StreamWorld world = new StreamWorld();
			world.addStream(this);
			NeutronNodeWorld.streamWorlds.put(worldObj, world);
		} else
			NeutronNodeWorld.streamWorlds.get(worldObj).addStream(this);
	}

	// USES THE CACHE!!!
	public List<BlockPos> getBlocks(int range) {
		List<BlockPos> positions = new ArrayList<>();

		for (int i = 1; i <= range; i++) {
			int x = (int) Math.floor(0.5 + vector.xCoord * i);
			int z = (int) Math.floor(0.5 + vector.zCoord * i);

			BlockPos pos = new BlockPos(origin.tile).add(x, 0, z);
			positions.add(pos);
		}
		return positions;
	}

	public abstract void runStreamInteraction(World worldObj);
}