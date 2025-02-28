package com.hbm.handler.neutron;

import com.hbm.handler.neutron.NeutronNodeWorld.StreamWorld;
import com.hbm.util.fauxpointtwelve.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.Iterator;

public abstract class NeutronStream {

	public enum NeutronType {
		DUMMY, // Dummy streams for node decaying
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
		posInstance = origin.pos.clone();
	}

	public NeutronStream(NeutronNode origin, Vec3 vector, double flux, double ratio, NeutronType type) {
		this.origin = origin;
		this.vector = vector;
		posInstance = origin.pos.clone();
		this.fluxQuantity = flux;
		this.fluxRatio = ratio;
		this.type = type;

		NeutronNodeWorld.getOrAddWorld(origin.tile.getWorldObj()).addStream(this);
	}

	protected BlockPos posInstance;

	private int i;

	// USES THE CACHE!!!
	public Iterator<BlockPos> getBlocks(int range) {

		i = 1;

		return new Iterator<BlockPos>() {
			@Override
			public boolean hasNext() {
				return i <= range;
			}

			@Override
			public BlockPos next() {
				int x = (int) Math.floor(0.5 + vector.xCoord * i);
				int z = (int) Math.floor(0.5 + vector.zCoord * i);

				i++;
				return posInstance.mutate(origin.tile.xCoord + x, origin.tile.yCoord, origin.tile.zCoord + z);
			}
		};
	}

	public abstract void runStreamInteraction(World worldObj, StreamWorld streamWorld);
}
