package com.hbm.particle;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class ParticleMukeCloud extends EntityFX {

	public ParticleMukeCloud(World world, double x, double y, double z, double mx, double my, double mz) {
		super(world, x, y, z, mx, my, mz);
	}
}
