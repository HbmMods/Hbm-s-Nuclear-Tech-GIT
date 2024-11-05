package com.hbm.handler.neutron;

import com.hbm.handler.neutron.NeutronStream.NeutronType;
import com.hbm.util.fauxpointtwelve.BlockPos;
import net.minecraft.tileentity.TileEntity;

import java.util.HashMap;
import java.util.Map;

public abstract class NeutronNode {

	protected NeutronType type;

	protected BlockPos pos;

	protected TileEntity tile;

	// like NBT but less fucking CANCER
	// Holds things like cached RBMK lid values.
	protected Map<String, Object> data = new HashMap<>();

	public NeutronNode(TileEntity tile, NeutronType type) {
		this.type = type;
		this.tile = tile;
		this.pos = new BlockPos(tile);
	}
}
