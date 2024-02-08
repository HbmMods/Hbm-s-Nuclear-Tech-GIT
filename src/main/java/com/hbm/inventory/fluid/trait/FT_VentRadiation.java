package com.hbm.inventory.fluid.trait;

import java.io.IOException;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.inventory.fluid.tank.FluidTank;

import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class FT_VentRadiation extends FluidTrait {
	
	float radPerMB = 0;
	
	public FT_VentRadiation() { }
	
	public FT_VentRadiation(float rad) {
		this.radPerMB = rad;
	}
	
	public float getRadPerMB() {
		return this.radPerMB;
	}
	
	@Override
	public void onFluidRelease(World world, int x, int y, int z, FluidTank tank, int overflowAmount, FluidReleaseType type) {
		ChunkRadiationManager.proxy.incrementRad(world, x, y, z, overflowAmount * radPerMB);
	}
	
	@Override
	public void addInfo(List<String> info) {
		info.add(EnumChatFormatting.YELLOW + "[Radioactive]");
	}

	@Override
	public void serializeJSON(JsonWriter writer) throws IOException {
		writer.name("radiation").value(radPerMB);
	}
	
	@Override
	public void deserializeJSON(JsonObject obj) {
		this.radPerMB = obj.get("radiation").getAsFloat();
	}
}
